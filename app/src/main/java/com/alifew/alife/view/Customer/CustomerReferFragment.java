package com.alifew.alife.view.Customer;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alifew.alife.BuildConfig;
import com.alifew.alife.R;
import com.alifew.alife.model.Customer_response;
import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.viewmodel.Customer_details;

public class CustomerReferFragment extends Fragment {

    SessionManagement sessionManagement;
    String userID;
    Customer_details customer_details;
    TextView myReferralCode, friendReferralCode;
    AppCompatButton shareReferCode;
    String myReferCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_refer, container, false);

        initView(view);

        loadProfile();

        myReferralCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("alife_refer_code", myReferCode);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getActivity(), "Text Copied", Toast.LENGTH_SHORT).show();
            }
        });

        shareReferCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareReferCode();
            }
        });

        return view;
    }

    private void loadProfile() {
        customer_details.getdata(userID).observe(getViewLifecycleOwner(), new Observer<Customer_response>() {
            @Override
            public void onChanged(Customer_response customerResponse) {
                myReferCode = customerResponse.myCode;
                myReferralCode.setText(customerResponse.myCode);
                friendReferralCode.setText(customerResponse.referralCode);
            }
        });
    }

    private void initView(View view) {
        customer_details = new ViewModelProvider(getActivity()).get(Customer_details.class);

        sessionManagement = new SessionManagement(getActivity());
        userID = String.valueOf(sessionManagement.getSession());

        myReferralCode = view.findViewById(R.id.myReferralCode);
        friendReferralCode = view.findViewById(R.id.friendReferralCode);

        shareReferCode = view.findViewById(R.id.shareReferCode);
    }


    public void shareReferCode() {
        String message = "Use this refer code- " + myReferCode;

        if(!myReferCode.isEmpty()){
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getActivity().getApplicationContext().getResources().getString(R.string.app_name));
                shareIntent.putExtra(Intent.EXTRA_TEXT, message);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                Log.d("dataxx", "appShare: " + e.getMessage());
            }
        }else {
            Toast.makeText(getActivity(), "No refer code to share", Toast.LENGTH_SHORT).show();
        }
    }
}