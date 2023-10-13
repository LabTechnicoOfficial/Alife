package com.alifew.alife.view.Shop;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.adapter.Shop_coupon_package_adapter;
import com.alifew.alife.model.cupon.add_response;
import com.alifew.alife.model.cupon.CustomerFor_cupon_response;
import com.alifew.alife.model.cupon.edit_delete_response;
import com.alifew.alife.model.cupon.Package_response;
import com.alifew.alife.model.shop_profile_response;
import com.alifew.alife.viewmodel.SessionManagment_registration;
import com.alifew.alife.viewmodel.Shop_profile;
import com.alifew.alife.viewmodel.cuponViewmodel.CouponPackageViewModel;
import com.alifew.alife.viewmodel.cuponViewmodel.Edit_delete_cupon_package;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Shop_coupon_packages_fragment extends Fragment implements Shop_coupon_package_adapter.onItemClickListener, Shop_coupon_package_adapter.onItemDeleteListener {

    RecyclerView packagesView;
    String shopID, couponID;
    ExtendedFloatingActionButton addPackageButton;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    int page = 1, limit = 10, end = 0;
    Edit_delete_cupon_package edit_delete_cupon_package;
    CouponPackageViewModel couponPackageViewModel;
    private List<Package_response> packagesList;
    private Shop_coupon_package_adapter adapter;
    List<CustomerFor_cupon_response> customerList;
    private String cupon_available;
    String cupon_name;
    Dialog loader;
    Shop_profile shop_profile;

    public Shop_coupon_packages_fragment(String shopID, String couponID, List<CustomerFor_cupon_response> customerList, String cupon_available, String cupon_name) {
        this.shopID = shopID;
        this.couponID = couponID;
        this.customerList = customerList;
        this.cupon_available = cupon_available;
        this.cupon_name = cupon_name;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        main();
        package_data();

        addPackageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                ImageView closeButton = addPackageAlert.findViewById(R.id.closeButtonID);
                AppCompatButton addButton = addPackageAlert.findViewById(R.id.addButtonID);

                TextInputEditText packageNameText = addPackageAlert.findViewById(R.id.packageNameTextID);
                TextInputEditText packageAmountText = addPackageAlert.findViewById(R.id.packageAmountTextID);
                TextInputEditText winnerAmountText = addPackageAlert.findViewById(R.id.winnerAmountTextID);
                TextInputEditText giftNameText = addPackageAlert.findViewById(R.id.giftNameTextID);

                TextInputLayout packageNameError = addPackageAlert.findViewById(R.id.packageNameErrorID);
                TextInputLayout packageAmountError = addPackageAlert.findViewById(R.id.packageAmountErrorID);
                TextInputLayout winnerAmountError = addPackageAlert.findViewById(R.id.winnerAmountErrorID);
                TextInputLayout giftNameError = addPackageAlert.findViewById(R.id.giftNameErrorID);

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String packageName = packageNameText.getText().toString().trim();
                        String packageAmount = packageAmountText.getText().toString().trim();
                        String winnerAmount = winnerAmountText.getText().toString().trim();
                        String giftName = giftNameText.getText().toString().trim();


                        packageAmountError.setErrorEnabled(false);
                        packageNameError.setErrorEnabled(false);
                        if (TextUtils.isEmpty(packageName) || TextUtils.isEmpty(packageAmount) || TextUtils.isEmpty(winnerAmount) || TextUtils.isEmpty(giftName)) {
                            if (TextUtils.isEmpty(packageName)) {
                                packageNameError.setError(" ");
                            }
                            if (TextUtils.isEmpty(packageAmount)) {
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
                            couponPackageViewModel.addPackage(couponID, packageName, packageAmount, winnerAmount, giftName).observe(getViewLifecycleOwner(), new Observer<add_response>() {
                                @Override
                                public void onChanged(add_response add_response) {
                                    loader.dismiss();
                                    String message = add_response.getMessage();

                                    if (message.equals("success")) {
                                        addPackageAlert.dismiss();
                                        Toast.makeText(getActivity(), "package added", Toast.LENGTH_SHORT).show();
                                        package_data();
                                    } else {
                                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addPackageAlert.dismiss();
                    }
                });
            }
        });
    }

    private void package_data() {
        couponPackageViewModel.getPackage(couponID, shopID, "", "", "").observe(getViewLifecycleOwner(), new Observer<List<Package_response>>() {
            @Override
            public void onChanged(List<Package_response> package_respons) {
                packagesList = package_respons;

                adapter = new Shop_coupon_package_adapter(packagesList, cupon_available);
                adapter.setOnClickListener(Shop_coupon_packages_fragment.this::OnItemClick, Shop_coupon_packages_fragment.this::OnItemDelete);
                packagesView.setAdapter(adapter);
               // Toast.makeText(getActivity(),String.valueOf(couponID),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void main() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_coupon_packages_fragment, container, false);
        shop_profile = new ViewModelProvider(this).get(Shop_profile.class);
        edit_delete_cupon_package = new ViewModelProvider(this).get(Edit_delete_cupon_package.class);
        couponPackageViewModel = new ViewModelProvider(this).get(CouponPackageViewModel.class);
        addPackageButton = (ExtendedFloatingActionButton) view.findViewById(R.id.addPackageButtonID);

        packagesView = (RecyclerView) view.findViewById(R.id.packagesViewID);
        packagesView.setHasFixedSize(true);
        packagesView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > oldScrollY) {
                    addPackageButton.hide();
                } else {
                    addPackageButton.show();
                }
                //mFloatingActionButton.show();
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (end == 0) {
                        progressBar.setVisibility(View.VISIBLE);
                        page++;
                        //filter(page, limit);
                    }
                }
            }
        });


        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);
        if (cupon_available.equals("0")) {
            addPackageButton.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void OnItemClick(int position) {
        Package_response response = packagesList.get(position);
        String packageID = response.getId();
        String packageName = response.getPackage_name();
        String packageSellAmount = response.getPackageSellAmount();

        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.frame_container, new Shop_coupon_packages_details_fragment( packageID, packageName, packageSellAmount, cupon_available, cupon_name, packageName)).addToBackStack(null).commit();

    }

    @Override
    public void OnItemDelete(int position) {
        Package_response response = packagesList.get(position);
        String packageID = response.getId();

        Dialog deleteAlert = new Dialog(getActivity());
        deleteAlert.setContentView(R.layout.delete_alert);
        deleteAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteAlert.setCancelable(false);
        deleteAlert.show();

        TextView yesButton = deleteAlert.findViewById(R.id.yesButton);
        TextView noButton = deleteAlert.findViewById(R.id.noButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loader.show();
                edit_delete_cupon_package.deletePackage(packageID).observe(getViewLifecycleOwner(), new Observer<edit_delete_response>() {
                    @Override
                    public void onChanged(edit_delete_response edit_delete_response) {
                        String message = edit_delete_response.getMesssage();

                        loader.dismiss();

                        if (message.equals("success")) {
                            package_data();
                            deleteAlert.dismiss();
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlert.dismiss();
            }
        });
    }
}