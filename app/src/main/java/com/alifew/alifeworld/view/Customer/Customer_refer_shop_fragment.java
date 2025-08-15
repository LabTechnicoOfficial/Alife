package com.alifew.alifeworld.view.Customer;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.adapter.Customer.Customer_shopList_adapter;
import com.alifew.alifeworld.adapter.coupon.Customer_coupon_shop_list_adapter;
import com.alifew.alifeworld.model.cupon.ShopResponse;
import com.alifew.alifeworld.viewmodel.refer.ShopReferViewModel;

import java.util.ArrayList;
import java.util.List;

public class Customer_refer_shop_fragment extends Fragment implements Customer_shopList_adapter.OnItemClickListener {
    RecyclerView shopListView;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    int page = 1, limit = 15, end = 0;
    private Customer_coupon_shop_list_adapter adapter;
    private List<ShopResponse> shopList = new ArrayList<>();
    String customerID;
    ShopReferViewModel viewModel;

    public Customer_refer_shop_fragment(String customerId) {
        this.customerID = customerId;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        shop_list(page,limit);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.customer_refer_fragment, container, false);

        viewModel = new ViewModelProvider(this).get(ShopReferViewModel.class);
        
        shopListView = view.findViewById(R.id.shopListViewID);
        shopListView.setHasFixedSize(true);
        shopListView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        progressBar = view.findViewById(R.id.progressBar);
        nestedScrollView = view.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                //mFloatingActionButton.show();
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (end == 0) {
                        progressBar.setVisibility(View.VISIBLE);
                        page++;
                        //filter(page, limit);
                        shop_list(page,limit);
                    }
                }
            }
        });
        
        return view;
    }

    private void shop_list(int Page,int Limit) {
        viewModel.getReferShopList(Page,Limit).observe(getViewLifecycleOwner(), (Observer<List<ShopResponse>>) cuponShop_responses -> {
            progressBar.setVisibility(View.GONE);
            shopList=cuponShop_responses;
            adapter = new Customer_coupon_shop_list_adapter(shopList);
            adapter.setOnClickListener(Customer_refer_shop_fragment.this::OnItemClick);
            shopListView.setAdapter(adapter);
        });
    }

    @Override
    public void OnItemClick(int position) {
        ShopResponse response = shopList.get(position);

        String shopID = response.getShop_id();

        requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.cus_frame_container, new Customer_shop_refer_list_fragment(shopID, customerID)).addToBackStack(null).commit();
    }
}