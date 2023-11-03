package com.alifew.alifeworld.view.Shop;

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
import com.alifew.alifeworld.adapter.coupon.ShopCouponPackageCustomerResultAdapter;
import com.alifew.alifeworld.databinding.FragmentShopCouponPackageCustomerResultFragmentBinding;
import com.alifew.alifeworld.model.CommonResponse;
import com.alifew.alifeworld.model.cupon.ShopCouponCustomerResponse;
import com.alifew.alifeworld.viewmodel.cuponViewmodel.CouponViewModel;

import java.util.ArrayList;
import java.util.List;


public class Shop_coupon_package_customer_result_fragment extends Fragment implements ShopCouponPackageCustomerResultAdapter.OnStatusChangeListener, ShopCouponPackageCustomerResultAdapter.OnDeleteClickListener {

    FragmentShopCouponPackageCustomerResultFragmentBinding binding;
    String packageID;
    Dialog loader;

    public Shop_coupon_package_customer_result_fragment(String packageID) {
        this.packageID = packageID;
    }

    ShopCouponPackageCustomerResultAdapter adapter;
    CouponViewModel couponViewModel;
    List<ShopCouponCustomerResponse.Customer> customerList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentShopCouponPackageCustomerResultFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        initView(view);

        load_data();

        return view;
    }

    private void load_data() {


        loader.show();
        couponViewModel.getCouponPackageCustomerResultList(packageID).observe(getViewLifecycleOwner(), new Observer<ShopCouponCustomerResponse>() {
            @Override
            public void onChanged(ShopCouponCustomerResponse shopCouponCustomerResponse) {
                loader.dismiss();
                customerList = shopCouponCustomerResponse.customerList;
                adapter = new ShopCouponPackageCustomerResultAdapter(customerList);
                adapter.setOnItemClickListener(Shop_coupon_package_customer_result_fragment.this::onStatusClick, Shop_coupon_package_customer_result_fragment.this::onDeleteClick);
                binding.itemView.setAdapter(adapter);
            }
        });

    }

    private void initView(View view) {
        binding.itemView.setHasFixedSize(true);
        binding.itemView.setLayoutManager(new LinearLayoutManager(getActivity()));
        couponViewModel = new ViewModelProvider(getActivity()).get(CouponViewModel.class);

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);
    }

    @Override
    public void onStatusClick(int position) {
        ShopCouponCustomerResponse.Customer response = customerList.get(position);
    }

    @Override
    public void onDeleteClick(int position) {
        ShopCouponCustomerResponse.Customer response = customerList.get(position);

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
            couponViewModel.deleteCouponPackageCustomerResultItem(response.id).observe(getViewLifecycleOwner(), new Observer<CommonResponse>() {
                @Override
                public void onChanged(CommonResponse commonResponse) {

                    loader.dismiss();

                    Toast.makeText(getActivity(), commonResponse.message, Toast.LENGTH_SHORT).show();

                    if(commonResponse.message.equals("deleted successfully")){
                        alertDialog.dismiss();
                        load_data();
                    }
                }
            });
        });

        noButton.setOnClickListener(v -> alertDialog.cancel());
    }
}