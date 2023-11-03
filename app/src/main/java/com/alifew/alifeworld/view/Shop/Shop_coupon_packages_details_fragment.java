package com.alifew.alifeworld.view.Shop;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.adapter.coupon.Shop_coupon_package_details_customer_list_adapter;
import com.alifew.alifeworld.model.CommonResponse;
import com.alifew.alifeworld.model.cupon.CustomerFor_cupon_response;
import com.alifew.alifeworld.model.cupon.edit_delete_response;
import com.alifew.alifeworld.model.cupon.notify_response;
import com.alifew.alifeworld.session.SessionManagement;
import com.alifew.alifeworld.viewmodel.cuponViewmodel.CouponViewModel;
import com.alifew.alifeworld.viewmodel.cuponViewmodel.Edit_delete_cupon_package;
import com.alifew.alifeworld.viewmodel.cuponViewmodel.sendPackageCustomer_notification;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class Shop_coupon_packages_details_fragment extends Fragment implements Shop_coupon_package_details_customer_list_adapter.OnAddIconClickListener {

    RecyclerView customersView;
    ExtendedFloatingActionButton editButton;
    int page = 1, limit = 10, end = 0;
    private List<CustomerFor_cupon_response> packageCustomerList;
    private Shop_coupon_package_details_customer_list_adapter adapter;
    Edit_delete_cupon_package edit_delete_cupon_package;
    Dialog loader;
    TextView notificationSend, resultButton;
    String packageID, packageName, packageSellAmount, cupon_available, cupon_name, package_name, shop_name;
    sendPackageCustomer_notification customer_notification;
    CouponViewModel couponViewModel;
    int shopID;
    SessionManagement sessionManagement;

    public Shop_coupon_packages_details_fragment(String packageID, String packageName, String packageSellAmount, String cupon_available, String cupon_name, String package_name) {
        //this.packageCustomerList = packageCustomerList;
        this.packageID = packageID;
        this.packageName = packageName;
        this.packageSellAmount = packageSellAmount;
        this.cupon_available = cupon_available;
        this.cupon_name = cupon_name;
        this.package_name = package_name;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog updatePackageAlert = new Dialog(getActivity());
                updatePackageAlert.setContentView(R.layout.shop_coupon_package_edit_alert);
                updatePackageAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                updatePackageAlert.setCancelable(false);
                updatePackageAlert.show();

                ImageView closeButton = updatePackageAlert.findViewById(R.id.closeButton);
                AppCompatButton updateButton = updatePackageAlert.findViewById(R.id.updateButtonID);
                TextInputEditText packageNameText = updatePackageAlert.findViewById(R.id.packageNameTextID);
                TextInputEditText packageAmountText = updatePackageAlert.findViewById(R.id.packageAmountTextID);
                TextInputEditText winnerAmountText = updatePackageAlert.findViewById(R.id.winnerAmountTextID);
                TextInputEditText giftNameText = updatePackageAlert.findViewById(R.id.giftNameText);

                TextInputLayout packageNameError = updatePackageAlert.findViewById(R.id.packageNameErrorID);
                TextInputLayout packageAmountError = updatePackageAlert.findViewById(R.id.packageAmountErrorID);
                TextInputLayout winnerAmountError = updatePackageAlert.findViewById(R.id.winnerAmountErrorID);
                TextInputLayout giftNameError = updatePackageAlert.findViewById(R.id.giftNameErrorID);

                packageNameText.setText(packageName);
                packageAmountText.setText(packageSellAmount);

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updatePackageAlert.dismiss();
                    }
                });

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String p_name = packageNameText.getText().toString().trim();
                        String p_sell_amount = packageAmountText.getText().toString().trim();

                        packageAmountError.setErrorEnabled(false);
                        packageNameError.setErrorEnabled(false);

                        if (TextUtils.isEmpty(p_name) || TextUtils.isEmpty(p_sell_amount)) {
                            if (TextUtils.isEmpty(p_name)) {
                                packageNameError.setError(" ");
                            } else if (TextUtils.isEmpty(p_sell_amount)) {
                                packageAmountError.setError(" ");
                            }
                        } else {
                            loader.show();
                            edit_delete_cupon_package.edit_package(packageID, p_name, p_sell_amount).observe(getViewLifecycleOwner(), new Observer<edit_delete_response>() {
                                @Override
                                public void onChanged(edit_delete_response edit_delete_response) {
                                    String message = edit_delete_response.getMesssage();
                                    loader.dismiss();
                                    if (message.equals("success")) {
                                        updatePackageAlert.dismiss();
                                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_coupon_packages_details_fragment, container, false);

        initView(view);


        loadCustomerList();

        resultButton.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                    R.anim.slide_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.fade_in,   // popEnter
                    R.anim.slide_out  // popExit
            ).replace(R.id.frame_container, new Shop_coupon_package_customer_result_fragment(packageID)).addToBackStack(null).commit();

        });

        notificationSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.show();
                for (int i = 0; i < packageCustomerList.size(); i++) {
                    int unicode = 0x1F60A;
                    String emoji = getEmojiByUnicode(unicode);
                    String message = "Your current position for " + package_name + " package under " + cupon_name + " cupon is " + (i + 1) +
                            " in " + shop_name + " shop." + emoji;
                    customer_notification.getData(packageCustomerList.get(i).getCustomer_id(), message).observe(getViewLifecycleOwner(), new Observer<notify_response>() {
                        @Override
                        public void onChanged(notify_response notify_response) {

                        }
                    });
                }
                Toast.makeText(getActivity(), "Succcessfully Notified", Toast.LENGTH_SHORT).show();
                loader.dismiss();
            }
        });


        return view;
    }

    private void initView(View view) {
        sessionManagement = new SessionManagement(getActivity());
        shopID = sessionManagement.getSession();
        edit_delete_cupon_package = new ViewModelProvider(this).get(Edit_delete_cupon_package.class);
        customer_notification = new ViewModelProvider(this).get(sendPackageCustomer_notification.class);
        editButton = (ExtendedFloatingActionButton) view.findViewById(R.id.editButton);
        notificationSend = (TextView) view.findViewById(R.id.notificationID);
        customersView = (RecyclerView) view.findViewById(R.id.customersViewID);
        customersView.setHasFixedSize(true);
        customersView.setLayoutManager(new LinearLayoutManager(getActivity()));
        resultButton  = view.findViewById(R.id.resultButton);

        Log.d("dataxx", "initView: "+packageID);

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        couponViewModel = new ViewModelProvider(getActivity()).get(CouponViewModel.class);
    }

    private void loadCustomerList() {
        couponViewModel.getCustomerListForCoupon(String.valueOf(shopID), packageID).observe(getViewLifecycleOwner(), customerForCuponResponses -> {
            packageCustomerList = customerForCuponResponses;
            adapter = new Shop_coupon_package_details_customer_list_adapter(packageCustomerList);
            adapter.setOnItemClickListener(Shop_coupon_packages_details_fragment.this::onAddClick);
            customersView.setAdapter(adapter);

            if (packageCustomerList.isEmpty()) {
                notificationSend.setVisibility(View.GONE);
            }
        });
    }

    public String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    @Override
    public void onAddClick(int position) {
        CustomerFor_cupon_response response = packageCustomerList.get(position);
        String pos = String.valueOf(position + 1);

        // packageID, response.getCustomer_phone(), response.getSell_amount(), response.points, shopID

        Dialog couponAddGiftAlert = new Dialog(getActivity());
        couponAddGiftAlert.setContentView(R.layout.coupon_add_gift_alert);
        couponAddGiftAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        couponAddGiftAlert.setCancelable(false);
        couponAddGiftAlert.show();

        Window window = couponAddGiftAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        TextView positionText = couponAddGiftAlert.findViewById(R.id.positionText);
        TextView phoneText = couponAddGiftAlert.findViewById(R.id.phoneText);
        TextView pointsText = couponAddGiftAlert.findViewById(R.id.pointsText);
        TextView amountText = couponAddGiftAlert.findViewById(R.id.amountText);
        EditText nameText = couponAddGiftAlert.findViewById(R.id.nameText);
        ImageView closeButton = couponAddGiftAlert.findViewById(R.id.closeButton);
        Button submitButton = couponAddGiftAlert.findViewById(R.id.submitButton);
        closeButton.setOnClickListener(v -> {
            couponAddGiftAlert.dismiss();
        });

        positionText.setText(pos);
        phoneText.setText(response.getCustomer_phone());
        pointsText.setText(String.valueOf(response.points));
        amountText.setText(String.valueOf(response.getSell_amount()));


        submitButton.setOnClickListener(v -> {
            if (TextUtils.isEmpty(nameText.getText().toString().trim())) {
                Toast.makeText(getActivity(), "empty gift name", Toast.LENGTH_SHORT).show();
            } else {

                loader.show();
                couponViewModel.addCustomerReferGift(packageID, response.getCustomer_phone(), response.getSell_amount(), response.points, shopID, pos, nameText.getText().toString().trim()).observe(getViewLifecycleOwner(), new Observer<CommonResponse>() {
                    @Override
                    public void onChanged(CommonResponse commonResponse) {
                        loader.dismiss();

                        Toast.makeText(getActivity(), commonResponse.message, Toast.LENGTH_SHORT).show();

                        if (commonResponse.message.equals("success")) {
                            couponAddGiftAlert.dismiss();
                        }
                    }
                });
            }
        });


    }
}