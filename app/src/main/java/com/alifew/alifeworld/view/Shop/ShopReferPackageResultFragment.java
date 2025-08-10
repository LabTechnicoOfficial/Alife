package com.alifew.alifeworld.view.Shop;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.adapter.refer.ShopReferCustomerResultAdapter;
import com.alifew.alifeworld.databinding.FragmentShopReferPackageResultBinding;
import com.alifew.alifeworld.model.CommonResponse;
import com.alifew.alifeworld.model.refer.ReferResultCustomerResponse;
import com.alifew.alifeworld.session.SessionManagement;
import com.alifew.alifeworld.viewmodel.refer.ShopReferViewModel;

import java.util.List;

public class ShopReferPackageResultFragment extends Fragment implements ShopReferCustomerResultAdapter.OnItemDeleteListener, ShopReferCustomerResultAdapter.OnStatusButtonClick {
    FragmentShopReferPackageResultBinding binding;
    SessionManagement sessionManagement;
    int shopID;
    String packageID;
    ShopReferCustomerResultAdapter adapter;

    ShopReferViewModel shopReferViewModel;

    Dialog loader;
    List<ReferResultCustomerResponse.Customer> resultCustomerList;

    public ShopReferPackageResultFragment(String packageID) {
        this.packageID = packageID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShopReferPackageResultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        initView(view);

        load_data();

        return view;
    }

    private void load_data() {
        loader.dismiss();
        shopReferViewModel.getResultCustomerList(packageID).observe(getViewLifecycleOwner(), new Observer<ReferResultCustomerResponse>() {
            @Override
            public void onChanged(ReferResultCustomerResponse referResultCustomerResponse) {
                loader.dismiss();
                resultCustomerList = referResultCustomerResponse.customerList;
                adapter = new ShopReferCustomerResultAdapter(resultCustomerList);
                adapter.setOnItemClickListener(ShopReferPackageResultFragment.this::OnItemDeleteClick, ShopReferPackageResultFragment.this::OnStatusClick);
                binding.itemView.setAdapter(adapter);
            }
        });


    }

    private void initView(View view) {
        sessionManagement = new SessionManagement(getActivity());
        shopID = sessionManagement.getUserID();

        binding.itemView.setHasFixedSize(true);
        binding.itemView.setLayoutManager(new LinearLayoutManager(getActivity()));

        shopReferViewModel = new ViewModelProvider(getActivity()).get(ShopReferViewModel.class);

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);
    }

    @Override
    public void OnItemDeleteClick(int position) {
        ReferResultCustomerResponse.Customer response = resultCustomerList.get(position);

        Dialog alertDialog = new Dialog(getActivity());
        alertDialog.setContentView(R.layout.confirm_alert);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        TextView yesButton = alertDialog.findViewById(R.id.yesButton);
        TextView noButton = alertDialog.findViewById(R.id.noButton);
        TextView titleText = alertDialog.findViewById(R.id.titleText);

        titleText.setText("Are you sure about deleting this item?");

        yesButton.setOnClickListener(v -> {
            loader.show();

            shopReferViewModel.deleteReferCustomerResult(response.id).observe(getViewLifecycleOwner(), new Observer<CommonResponse>() {
                @Override
                public void onChanged(CommonResponse commonResponse) {
                    loader.dismiss();
                    Toast.makeText(getActivity(), commonResponse.message, Toast.LENGTH_SHORT).show();

                    if (commonResponse.message.equals("deleted successfully")) {
                        alertDialog.dismiss();
                        load_data();
                    }


                }
            });
        });

        noButton.setOnClickListener(v -> alertDialog.cancel());


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnStatusClick(int position) {
        ReferResultCustomerResponse.Customer response = resultCustomerList.get(position);

        Dialog alertDialog = new Dialog(getActivity());
        alertDialog.setContentView(R.layout.confirm_alert);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        TextView yesButton = alertDialog.findViewById(R.id.yesButton);
        TextView noButton = alertDialog.findViewById(R.id.noButton);
        TextView titleText = alertDialog.findViewById(R.id.titleText);

        titleText.setText("Are you sure about changing status?");

        yesButton.setOnClickListener(v -> {
            loader.show();

            shopReferViewModel.updateReferCustomerResultStatus(response.id, response.status.equals("pending") ? "done": "pending").observe(getViewLifecycleOwner(), new Observer<CommonResponse>() {
                @Override
                public void onChanged(CommonResponse commonResponse) {
                    loader.dismiss();
                    Toast.makeText(getActivity(), commonResponse.message, Toast.LENGTH_SHORT).show();

                    if (commonResponse.message.toLowerCase().equals("update successfully")) {
                        alertDialog.dismiss();
                        load_data();
                    }


                }
            });
        });

        noButton.setOnClickListener(v -> alertDialog.cancel());
    }
}