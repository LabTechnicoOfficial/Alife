package com.ALife.alife.view.Customer;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ALife.alife.R;
import com.ALife.alife.adapter.Customer.Customer_product_adapter;
import com.ALife.alife.model.Get_product_response;
import com.ALife.alife.model.shop_profile_response;
import com.ALife.alife.viewmodel.Get_product;
import com.ALife.alife.viewmodel.Shop_profile;

import java.util.ArrayList;
import java.util.List;

public class Customer_products_fragment extends Fragment {

    String shopID, categoryID;
    RecyclerView productsView;
    TextView allDiscountText;
    Get_product getProduct;
    private List<Get_product_response> productList;
    Customer_product_adapter adapter;
    private Shop_profile shop_profile;
    ProgressBar progressBar;
    int page=1,limit=10,end=0;

    public Customer_products_fragment(String shopID, String categoryID) {
        this.shopID = shopID;
        this.categoryID = categoryID;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        main();
    }

    private void all_discount() {
        shop_profile = new ViewModelProvider(getActivity()).get(Shop_profile.class);
        shop_profile.getData(shopID).observe(getViewLifecycleOwner(), new Observer<shop_profile_response>() {
            @Override
            public void onChanged(shop_profile_response shop_profile_response) {
                allDiscountText.setText(shop_profile_response.getAll_discount() + "%");
            }
        });
    }

    private void main() {
        shop_profile = new ViewModelProvider(getActivity()).get(Shop_profile.class);
        shop_profile.getData(shopID).observe(getViewLifecycleOwner(), new Observer<shop_profile_response>() {
            @Override
            public void onChanged(shop_profile_response shop_profile_response) {
                allDiscountText.setText(shop_profile_response.getAll_discount() + "%");
                productList = new ArrayList<>();
                adapter = new Customer_product_adapter(productList);
                productsView.setAdapter(adapter);
                page=1;
                end=0;
                get_product(page,limit);
            }
        });

    }



    private void get_product(int Page,int Limit)
    {
        getProduct = new ViewModelProvider(getActivity()).get(Get_product.class);
        getProduct.getdata(categoryID, Page, Limit).observe(getViewLifecycleOwner(), new Observer<List<Get_product_response>>() {
            @Override
            public void onChanged(List<Get_product_response> get_product_responses) {
                progressBar.setVisibility(View.GONE);
                for (int i = 0; i < get_product_responses.size(); i++) {
                    productList.add(get_product_responses.get(i));
                }
                if(get_product_responses.size()<Limit)
                {
                    end=1;
                }

                //productList = get_product_responses;
                adapter = new Customer_product_adapter(productList);
                productsView.setAdapter(adapter);

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.customer_products_fragment, container, false);

        allDiscountText = (TextView) view.findViewById(R.id.allDiscountID);

        productsView = (RecyclerView) view.findViewById(R.id.productsViewID);
        productsView.setHasFixedSize(true);
        productsView.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));


        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        NestedScrollView nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if(end==0) {
                        progressBar.setVisibility(View.VISIBLE);
                        page++;
                        get_product(page, limit);
                    }
                }
            }
        });

        return view;
    }
}