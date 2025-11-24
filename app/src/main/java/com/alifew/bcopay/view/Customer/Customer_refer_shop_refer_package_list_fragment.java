package com.alifew.bcopay.view.Customer;

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

import com.alifew.bcopay.R;
import com.alifew.bcopay.adapter.refer.Customer_shop_refer_package_adapter;
import com.alifew.bcopay.model.refer.CustomerShopReferPackageResponse;
import com.alifew.bcopay.viewmodel.refer.ShopReferViewModel;

import java.util.ArrayList;
import java.util.List;


public class Customer_refer_shop_refer_package_list_fragment extends Fragment implements Customer_shop_refer_package_adapter.onItemClickListener {

    String shopID, referID, customerID;
    RecyclerView packagesView;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    int page = 1, limit = 10, end = 0;
    ShopReferViewModel viewModel;
    private List<CustomerShopReferPackageResponse> packageList;
    Customer_shop_refer_package_adapter adapter;

    public Customer_refer_shop_refer_package_list_fragment(String shopID, String referID, String customerID) {
        this.shopID = shopID;
        this.referID = referID;
        this.customerID = customerID;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getPosition();
        package_data();

    }

    private void package_data() {

        viewModel.getCustomerShopReferPackageList(shopID, referID).observe(getViewLifecycleOwner(), new Observer<List<CustomerShopReferPackageResponse>>() {
            @Override
            public void onChanged(List<CustomerShopReferPackageResponse> responses) {
                packageList = new ArrayList<>();
                packageList = responses;
                adapter = new Customer_shop_refer_package_adapter(packageList);
                adapter.setOnClickListener(Customer_refer_shop_refer_package_list_fragment.this::OnItemClick);
                packagesView.setAdapter(adapter);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_refer_shop_refer_package_list, container, false);


        initView(view);


        return view;
    }

    private void initView(View view) {
        viewModel = new ViewModelProvider(this).get(ShopReferViewModel.class);

        packagesView = view.findViewById(R.id.packagesViewID);
        packagesView.setHasFixedSize(true);
        packagesView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
    }

    @Override
    public void OnItemClick(int position) {
        CustomerShopReferPackageResponse response = packageList.get(position);

        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.cus_frame_container, new Customer_refer_package_customer_list_fragment(shopID, referID, response.id)).addToBackStack(null).commit();

    }
}