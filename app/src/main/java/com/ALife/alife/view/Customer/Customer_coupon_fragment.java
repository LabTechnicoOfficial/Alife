package com.ALife.alife.view.Customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.API.ApiUtilize;
import com.ALife.alife.R;
import com.ALife.alife.adapter.Customer_coupon_shop_list_adapter;
import com.ALife.alife.model.cupon.active_cupon;
import com.ALife.alife.model.cupon.cuponShop_response;
import com.ALife.alife.model.cupon.cupon_api;
import com.ALife.alife.model.cupon.customerFor_cupon_repositories;
import com.ALife.alife.model.cupon.customerFor_cupon_response;
import com.ALife.alife.view.Shop.Shop_coupon_packages_fragment;
import com.ALife.alife.viewmodel.cuponViewmodel.CuponShopList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class Customer_coupon_fragment extends Fragment implements Customer_coupon_shop_list_adapter.OnItemClickListener{
    private cupon_api cupon_api;
    RecyclerView shopListView;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    int page = 1, limit = 15, end = 0;
    private Customer_coupon_shop_list_adapter adapter;
    private List<cuponShop_response> shopList = new ArrayList<>();
    CuponShopList cuponShopListViewModel;
    String customerID;
    private MutableLiveData<List<customerFor_cupon_response>> data;


    public Customer_coupon_fragment(String customerID) {
        data = new MutableLiveData<>();
        cupon_api = ApiUtilize.cupon_response();
        this.customerID = customerID;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        shop_list(page,limit);
    }

    private void shop_list(int Page,int Limit) {
        cuponShopListViewModel.getData(Page,Limit).observe(getViewLifecycleOwner(), new Observer<List<cuponShop_response>>() {
            @Override
            public void onChanged(List<cuponShop_response> cuponShop_responses) {
                progressBar.setVisibility(View.GONE);
                shopList.addAll(cuponShop_responses);
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

        progressBar = (ProgressBar) view.findViewById(R.id.progressBarID);
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
        cuponShop_response response = shopList.get(position);

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