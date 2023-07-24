package com.alifew.alife.EarningApp.View.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alifew.alife.EarningApp.ViewModel.RefferViewModel;
import com.alifew.alife.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Refer_fragment extends Fragment {

    ImageView backButton;
    AppCompatButton referButton;
    String userID;
    TextInputLayout referError;
    TextInputEditText referText;
    RefferViewModel refferViewModel;

    public Refer_fragment(String userID) {
        this.userID = userID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.earning_refer_fragment, container, false);

        refferViewModel = new ViewModelProvider(this).get(RefferViewModel.class);

        backButton = (ImageView) view.findViewById(R.id.backButtonID);
        backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.fade_in,  // enter
                R.anim.fade_out// popExit
        ).replace(R.id.frame_container, new Home_fragment(userID)).commit());

        referError = (TextInputLayout) view.findViewById(R.id.referErrorID);
        referText = (TextInputEditText) view.findViewById(R.id.referTextID);

        referButton = (AppCompatButton) view.findViewById(R.id.referButtonID);

        referButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String referID = referText.getText().toString().trim();
                referError.setErrorEnabled(false);

                if (TextUtils.isEmpty(referID)) {
                    referError.setError(" ");
                } else {
                    refferViewModel.getData(userID, referID).observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("1")){
                                Toast.makeText(getActivity(), "Refer completed", Toast.LENGTH_SHORT).show();
                                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                                        R.anim.fade_in,  // enter
                                        R.anim.fade_out// popExit
                                ).replace(R.id.frame_container, new Home_fragment(userID)).addToBackStack(null).commit();

                            }else {
                                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        return view;
    }
}