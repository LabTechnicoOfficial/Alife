package com.alifew.alifeworld.view.Customer;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.alifew.alifeworld.adapter.coupon.Customer_coupon_adapter;
import com.alifew.alifeworld.adapter.refer.Customer_refer_adapter;
import com.alifew.alifeworld.model.refer.CustomerShopReferResponse;
import com.alifew.alifeworld.viewmodel.refer.ShopReferViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer_shop_refer_list_fragment extends Fragment implements Customer_refer_adapter.OnItemClickListener {

    private String shopID, customerID;

    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    int page = 1, limit = 10, end = 0;
    RecyclerView itemView;
    ShopReferViewModel viewModel;
    private Customer_refer_adapter adapter;
    private List<CustomerShopReferResponse> referList;
    String cupon_available;


    public Customer_shop_refer_list_fragment(String shopID, String customerID) {
        this.shopID = shopID;
        this.customerID = customerID;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        coupon_data();
    }

    private void coupon_data() {
        viewModel.getCustomerShopReferList(shopID).observe(getViewLifecycleOwner(), responses -> {
            referList = new ArrayList<>();
            referList = responses;
            adapter = new Customer_refer_adapter(referList);
            adapter.setOnClickListener(Customer_shop_refer_list_fragment.this);
            itemView.setAdapter(adapter);
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_shop_refer_list, container, false);


        viewModel = new ViewModelProvider(this).get(ShopReferViewModel.class);

        itemView = view.findViewById(R.id.itemViewID);
        itemView.setHasFixedSize(true);
        itemView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar = view.findViewById(R.id.progressBar);
        nestedScrollView = view.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

            //mFloatingActionButton.show();
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

    @Override
    public void onItemClick(int position) {
        CustomerShopReferResponse response = referList.get(position);

        requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.cus_frame_container, new Customer_refer_shop_refer_package_list_fragment(shopID, response.id, customerID)).addToBackStack(null).commit();

    }
}