package com.alifew.alifeworld.view.Customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.adapter.coupon.Customer_coupon_shop_list_adapter;
import com.alifew.alifeworld.model.cupon.ShopResponse;
import com.alifew.alifeworld.viewmodel.cuponViewmodel.CuponShopList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer_coupon_fragment extends Fragment implements Customer_coupon_shop_list_adapter.OnItemClickListener{
    RecyclerView shopListView;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    int page = 1, limit = 15, end = 0;
    private Customer_coupon_shop_list_adapter adapter;
    private final List<ShopResponse> shopList = new ArrayList<>();
    CuponShopList cuponShopListViewModel;
    String customerID;


    public Customer_coupon_fragment(String customerID) {
        this.customerID = customerID;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        shop_list(page,limit);
    }

    private void shop_list(int Page,int Limit) {
        cuponShopListViewModel.getData(Page,Limit).observe(getViewLifecycleOwner(), new Observer<List<ShopResponse>>() {
            @Override
            public void onChanged(List<ShopResponse> shop_respons) {
                progressBar.setVisibility(View.GONE);
                shopList.addAll(shop_respons);
                adapter = new Customer_coupon_shop_list_adapter(shopList);
                adapter.setOnClickListener(Customer_coupon_fragment.this::OnItemClick);
                shopListView.setAdapter(adapter);
                //Toast.makeText(getActivity(), String.valueOf(shopList.size()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.customer_coupon_fragment, container, false);

        cuponShopListViewModel = new ViewModelProvider(this).get(CuponShopList.class);

        shopListView = (RecyclerView) view.findViewById(R.id.shopListViewID);
        shopListView.setHasFixedSize(true);
        shopListView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);

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

    @Override
    public void OnItemClick(int position) {
        ShopResponse response = shopList.get(position);

        String shopID = response.getShop_id();
        //Toast.makeText(getActivity(), shopID, Toast.LENGTH_SHORT).show();

        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.cus_frame_container, new Customer_coupon_shop_coupon_list_fragment(shopID, customerID)).addToBackStack(null).commit();

    }
}