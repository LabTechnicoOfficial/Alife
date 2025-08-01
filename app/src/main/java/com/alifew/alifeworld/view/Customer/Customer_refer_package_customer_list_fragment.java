package com.alifew.alifeworld.view.Customer;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.adapter.coupon.Customer_coupon_package_customer_list_adapter;
import com.alifew.alifeworld.adapter.refer.Customer_shop_refer_package_adapter;
import com.alifew.alifeworld.adapter.refer.Customer_shop_refer_package_customer_list_adapter;
import com.alifew.alifeworld.model.cupon.CustomerFor_cupon_response;
import com.alifew.alifeworld.model.refer.CustomerReferPackageCustomer;
import com.alifew.alifeworld.model.refer.CustomerShopReferPackageResponse;
import com.alifew.alifeworld.viewmodel.refer.ShopReferViewModel;

import java.util.ArrayList;
import java.util.List;

public class Customer_refer_package_customer_list_fragment extends Fragment {

    String shopID,  referID,  packageId;
    private List<CustomerReferPackageCustomer> customerList;
    RecyclerView customersView;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    int page = 1, limit = 10, end = 0;
    private Customer_shop_refer_package_customer_list_adapter adapter;
    ShopReferViewModel viewModel;

    public Customer_refer_package_customer_list_fragment(String shopID, String referID, String id) {
        this.shopID = shopID;
        this.referID = referID;
        this.packageId = id;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        customer_list();
    }

    private void customer_list() {

        viewModel.getCustomerShopReferCustomerList(shopID, referID, packageId).observe(getViewLifecycleOwner(), responses -> {
            customerList = new ArrayList<>();
            customerList = responses;
            adapter = new Customer_shop_refer_package_customer_list_adapter(customerList);
            customersView.setAdapter(adapter);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_refer_package_customer_list, container, false);

        viewModel = new ViewModelProvider(this).get(ShopReferViewModel.class);

        customersView = view.findViewById(R.id.customersViewID);
        customersView.setHasFixedSize(true);
        customersView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar = view.findViewById(R.id.progressBar);
        nestedScrollView = view.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                if (end == 0) {
                    progressBar.setVisibility(View.VISIBLE);
                    page++;
                    //filter(page, limit);
                }
            }
        });

        return view;
    }
}