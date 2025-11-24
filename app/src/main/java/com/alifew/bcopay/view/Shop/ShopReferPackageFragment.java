package com.alifew.bcopay.view.Shop;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alifew.bcopay.R;
import com.alifew.bcopay.adapter.refer.ShopReferPackageAdapter;
import com.alifew.bcopay.model.CommonResponse;
import com.alifew.bcopay.model.refer.ReferPackageResponse;
import com.alifew.bcopay.session.SessionManagement;
import com.alifew.bcopay.viewmodel.refer.ShopReferViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


public class ShopReferPackageFragment extends Fragment implements ShopReferPackageAdapter.OnItemClickListener, ShopReferPackageAdapter.OnItemDeleteClickListener {

    com.alifew.bcopay.databinding.FragmentShopReferPackageBinding binding;
    String referID;

    public ShopReferPackageFragment(String referID) {
        this.referID = referID;
    }

    ShopReferViewModel shopReferViewModel;
    Dialog loader;
    SessionManagement sessionManagement;
    int shopID;
    private List<ReferPackageResponse> referPackageList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = com.alifew.bcopay.databinding.FragmentShopReferPackageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        initView(view);

        binding.addButton.setOnClickListener(v -> {
            add_refer_package();
        });


        load_data();

        return view;
    }

    private void add_refer_package() {
        Dialog addPackageAlert = new Dialog(getActivity());
        addPackageAlert.setContentView(R.layout.shop_coupon_package_add_alert);
        addPackageAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addPackageAlert.setCancelable(false);
        addPackageAlert.show();

        Window window = addPackageAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        ImageView closeButton = addPackageAlert.findViewById(R.id.closeButton);
        AppCompatButton addButton = addPackageAlert.findViewById(R.id.addButton);

        TextInputEditText packageNameText = addPackageAlert.findViewById(R.id.packageNameTextID);
        TextInputEditText packageAmountText = addPackageAlert.findViewById(R.id.packageAmountTextID);
        TextInputEditText winnerAmountText = addPackageAlert.findViewById(R.id.winnerAmountTextID);
        TextInputEditText giftNameText = addPackageAlert.findViewById(R.id.giftNameText);

        TextInputLayout packageNameError = addPackageAlert.findViewById(R.id.packageNameErrorID);
        TextInputLayout packageAmountError = addPackageAlert.findViewById(R.id.packageAmountErrorID);
        TextInputLayout winnerAmountError = addPackageAlert.findViewById(R.id.winnerAmountErrorID);
        TextInputLayout giftNameError = addPackageAlert.findViewById(R.id.giftNameErrorID);

        TextView headerText = addPackageAlert.findViewById(R.id.headerText);
        headerText.setText("Add Refer Package");

        packageAmountError.setHint(getString(R.string.min_point));

        addButton.setOnClickListener(v -> {
            String packageName = packageNameText.getText().toString().trim();
            String minReferPackagePoint = packageAmountText.getText().toString().trim();
            String winnerAmount = winnerAmountText.getText().toString().trim();
            String giftName = giftNameText.getText().toString().trim();


            packageAmountError.setErrorEnabled(false);
            packageNameError.setErrorEnabled(false);
            if (TextUtils.isEmpty(packageName) || TextUtils.isEmpty(minReferPackagePoint) || TextUtils.isEmpty(winnerAmount) || TextUtils.isEmpty(giftName)) {
                if (TextUtils.isEmpty(packageName)) {
                    packageNameError.setError(" ");
                }
                if (TextUtils.isEmpty(minReferPackagePoint)) {
                    packageAmountError.setError(" ");
                }
                if (TextUtils.isEmpty(winnerAmount)) {
                    winnerAmountError.setError(" ");
                }
                if (TextUtils.isEmpty(giftName)) {
                    giftNameError.setError(" ");
                }
            } else {
                loader.show();
                shopReferViewModel.addReferPackage(shopID, referID, packageName, minReferPackagePoint, winnerAmount, giftName).observe(getViewLifecycleOwner(), new Observer<CommonResponse>() {
                    @Override
                    public void onChanged(CommonResponse commonResponse) {
                        loader.dismiss();
                        String message = commonResponse.message;

                        if (message.equals("success")) {
                            addPackageAlert.dismiss();
                            Toast.makeText(getActivity(), "package added", Toast.LENGTH_SHORT).show();
                            load_data();
                        } else {
                            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPackageAlert.dismiss();
            }
        });
    }

    private void load_data() {


        binding.progressBar.setVisibility(View.VISIBLE);
        shopReferViewModel.getReferPackageList(referID).observe(getViewLifecycleOwner(), referPackageResponses -> {
            binding.progressBar.setVisibility(View.GONE);
            referPackageList = referPackageResponses;
            ShopReferPackageAdapter adapter = new ShopReferPackageAdapter(referPackageList);
            adapter.setOnItemClickListener(ShopReferPackageFragment.this, ShopReferPackageFragment.this);
            binding.itemView.setAdapter(adapter);
        });
    }

    private void initView(View view) {
        sessionManagement = new SessionManagement(getActivity());
        shopID = sessionManagement.getUserID();
        shopReferViewModel = new ViewModelProvider(getActivity()).get(ShopReferViewModel.class);

        binding.itemView.setHasFixedSize(true);
        binding.itemView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.itemView.setItemViewCacheSize(100);

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);
    }

    @Override
    public void onItemClick(int position) {
        ReferPackageResponse response = referPackageList.get(position);

        if (response.userCount > 0) {
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.fade_in,   // popEnter
                    R.anim.slide_out  // popExit
            ).replace(R.id.frame_container, new ShopReferPackageCustomerListFragment(response.id)).addToBackStack(null).commit();
        } else {
            Toast.makeText(getActivity(), "No customer available", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemDeleteClick(int position) {
        ReferPackageResponse response = referPackageList.get(position);

        loader.show();
        shopReferViewModel.deleteReferPackage(response.id).observe(getViewLifecycleOwner(), new Observer<CommonResponse>() {
            @Override
            public void onChanged(CommonResponse commonResponse) {
                loader.dismiss();
                if (commonResponse.message.equals("deleted successfully")) {
                    load_data();
                }

                Toast.makeText(getActivity(), commonResponse.message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}