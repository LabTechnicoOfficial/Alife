package com.alifew.bcopay.view.Shop;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alifew.bcopay.Custom_Type.ProductSell;
import com.alifew.bcopay.R;
import com.alifew.bcopay.adapter.Shop_registered_customer_adapter;
import com.alifew.bcopay.model.shop_due_customer_response;
import com.alifew.bcopay.viewmodel.ShopCustomerViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class Shop_sell_select_customer_fragment extends Fragment implements Shop_registered_customer_adapter.OnItemClickListener {

    RecyclerView customerView;
    LinearLayoutManager layoutManager;
    ExtendedFloatingActionButton addCustomer;
    private List<ProductSell> productsList;
    private String shop_id;
    private ShopCustomerViewModel get_customer;
    private List<shop_due_customer_response> customerList;
    private Shop_registered_customer_adapter adapter;
    LinearLayout layout;
    private FragmentManager fragmentManager;

    public Shop_sell_select_customer_fragment(String shop_id, List<ProductSell> productsList) {
        this.shop_id = shop_id;
        this.productsList = productsList;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main();


    }

    public void main() {
        checkConnection();
        get_shop_customer();
        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                default_customer();
            }
        });
    }

    public void get_shop_customer() {
        get_customer = new ViewModelProvider(getActivity()).get(ShopCustomerViewModel.class);
        get_customer.get_due_customer(shop_id).observe(getViewLifecycleOwner(), new Observer<List<shop_due_customer_response>>() {
            @Override
            public void onChanged(List<shop_due_customer_response> get_shop_customer_responses) {
                customerList = get_shop_customer_responses;
                adapter = new Shop_registered_customer_adapter(customerList);
                adapter.setOnClickListener(Shop_sell_select_customer_fragment.this::OnItemClickCustomer);
                customerView.setAdapter(adapter);
            }
        });
    }

    public void default_customer() {
        layout.setVisibility(View.INVISIBLE);
        Dialog alert = new Dialog(getActivity());
        alert.setContentView(R.layout.unregistered_customer_alert);
        alert.show();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.setCancelable(false);

        ImageView closeButton = (ImageView) alert.findViewById(R.id.closeID);
        TextView doneButton = (TextView) alert.findViewById(R.id.doneButtonID);

        TextInputEditText nameText = (TextInputEditText) alert.findViewById(R.id.shopNameText);
        TextInputEditText phoneText = (TextInputEditText) alert.findViewById(R.id.contactText);
        TextInputEditText locationText = (TextInputEditText) alert.findViewById(R.id.locationTextID);

        TextInputLayout nameError = (TextInputLayout) alert.findViewById(R.id.nameError);
        TextInputLayout phoneError = (TextInputLayout) alert.findViewById(R.id.phoneErrorID);
        TextInputLayout locationError = (TextInputLayout) alert.findViewById(R.id.locationErrorID);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString().trim();
                String phone = phoneText.getText().toString().trim();
                String location = locationText.getText().toString().trim();

                nameError.setErrorEnabled(false);
                phoneError.setErrorEnabled(false);
                locationError.setErrorEnabled(false);

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(location)) {
                    if (TextUtils.isEmpty(name)) {
                        nameError.setError(" ");
                    } else if (TextUtils.isEmpty(phone)) {
                        phoneError.setError(" ");
                    } else if (TextUtils.isEmpty(location)) {
                        locationError.setError(" ");
                    }
                } else {
                    String customer_id = "0";
                    String customer_image = "blank";
                    //redirect to new  fragment along with shop_id, customer_id,customer_image,customer_name,customer_phone,customer_location,productList
                    alert.dismiss();
                    goCheckout(shop_id, customer_id, customer_image, name, phone, location, productsList);
                }
            }
        });


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                layout.setVisibility(View.VISIBLE);
            }
        });

    }

    private void goCheckout(String shop_id, String customer_id, String customer_image, String customer_name, String customer_phone, String customer_location, List<ProductSell> productsList) {
        fragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.frame_container, new Shop_sell_checkout_fragment(shop_id, customer_id, customer_image, customer_name, customer_phone, customer_location, productsList)).addToBackStack(null).commit();
    }

    public void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        Dialog networkAlert = new Dialog(getActivity());
        networkAlert.setContentView(R.layout.network_alert);
        networkAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView connectButton = (TextView) networkAlert.findViewById(R.id.connectButtonID);
        if (info == null) {
            networkAlert.show();
            connectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    networkAlert.dismiss();

                    refreshFragment();
                }
            });
        }
    }

    public void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
        //adapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_sell_select_customer_alert, container, false);
        customerView = (RecyclerView) view.findViewById(R.id.customersViewID);
        addCustomer = (ExtendedFloatingActionButton) view.findViewById(R.id.addCustomerID);
        layout = (LinearLayout) view.findViewById(R.id.layoutID);

        layoutManager = new LinearLayoutManager(getContext());
        customerView.setHasFixedSize(true);
        customerView.setLayoutManager(layoutManager);

        fragmentManager = getFragmentManager();

        customerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && addCustomer.getVisibility() == View.VISIBLE) {
                    addCustomer.hide();
                } else if (dy < 0 && addCustomer.getVisibility() != View.VISIBLE) {
                    addCustomer.show();
                }
            }
        });

        return view;
    }

    @Override
    public void OnItemClickCustomer(int position) {
        shop_due_customer_response customer = customerList.get(position);
        String customer_id = customer.getCustomer_id();
        String customer_name = customer.getCustomer_name();
        String customer_phone = customer.getCustomer_phone();
        String customer_location = customer.getCustomer_address();
        String customer_image = customer.getCustomer_image();
        //redirect to new  fragment along with shop_id, customer_id,customer_image,customer_name,customer_phone,customer_location,productList

        goCheckout(shop_id, customer_id, customer_image, customer_name, customer_phone, customer_location, productsList);
    }
}