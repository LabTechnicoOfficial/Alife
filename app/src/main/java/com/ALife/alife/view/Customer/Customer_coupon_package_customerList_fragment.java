package com.ALife.alife.view.Customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.adapter.Customer_coupon_package_customer_list_adapter;
import com.ALife.alife.model.cupon.customerFor_cupon_response;

import java.util.List;


public class Customer_coupon_package_customerList_fragment extends Fragment {

    String phone;
    private List<customerFor_cupon_response> customerList;
    RecyclerView customersView;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    int page = 1, limit = 10, end = 0;
    private Customer_coupon_package_customer_list_adapter adapter;

    public Customer_coupon_package_customerList_fragment(List<customerFor_cupon_response> customerList, String phone) {
        this.customerList = customerList;
        this.phone = phone;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        customer_list();
    }

    private void customer_list() {
        adapter = new Customer_coupon_package_customer_list_adapter(phone, customerList);
        customersView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.customer_coupon_package_customer_list_fragment, container, false);

        customersView = (RecyclerView) view.findViewById(R.id.customersViewID);
        customersView.setHasFixedSize(true);
        customersView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (end == 0) {
                        progressBar.setVisibility(View.VISIBLE);
                        page++;
                        //filter(page, limit);
                    }
                }
            }
        });

        return view;
    }
}