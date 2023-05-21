package com.ALife.alife.view.Shop;

import android.os.Bundle;

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

import com.ALife.alife.R;
import com.ALife.alife.adapter.Shop_local_sell_select_customer_adapter;
import com.ALife.alife.adapter.Shop_local_sell_select_product_adapter;
import com.ALife.alife.model.local_sell.LocalSell_property;
import com.ALife.alife.model.local_sell.customer_phone_response;
import com.ALife.alife.model.local_sell.get_local_sell_product_response;
import com.ALife.alife.viewmodel.Local_sell.Get_local_sell;

import java.util.ArrayList;
import java.util.List;


public class Shop_local_sell_select_customer_phone_fragment extends Fragment implements Shop_local_sell_select_customer_adapter.OnItemClickListener {
    private String shop_id;
    Get_local_sell get_local_sell;
    private List<customer_phone_response> phoneList;
    RecyclerView recyclerView;
    private Shop_local_sell_select_customer_adapter adapter;
    int page = 1, limit = 15, end = 0;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    private RecyclerView.LayoutManager layoutmanager;

    public Shop_local_sell_select_customer_phone_fragment(String shop_id) {
        // Required empty public constructor
        this.shop_id = shop_id;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_local_sell_select_customer_phone_fragment, container, false);
        get_local_sell = new ViewModelProvider(this).get(Get_local_sell.class);
        recyclerView = view.findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        layoutmanager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutmanager);
        page = 1;
        limit = 15;

        progressBar = (ProgressBar) view.findViewById(R.id.progressBarID);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);
        get_phone(page, limit);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                //mFloatingActionButton.show();
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (end == 0) {
                        progressBar.setVisibility(View.VISIBLE);
                        page++;
                        get_phone(page, limit);
                    }
                }
            }
        });
        return view;
    }

    private void get_phone(int Page, int Limit) {
        progressBar.setVisibility(View.GONE);
        if (Page == 1) {
            phoneList = new ArrayList<>();
            adapter = new Shop_local_sell_select_customer_adapter(phoneList);
            adapter.setOnClickListener(Shop_local_sell_select_customer_phone_fragment.this::itemClick);

            recyclerView.setAdapter(adapter);


        }
        get_local_sell.getCustomer(shop_id, Page, Limit).observe(getViewLifecycleOwner(), new Observer<List<customer_phone_response>>() {
            @Override
            public void onChanged(List<customer_phone_response> customer_phone_responses) {
                if (customer_phone_responses.size() < limit) {
                    end = 1;
                }
                for (int i = 0; i < customer_phone_responses.size(); i++) {
                    phoneList.add(customer_phone_responses.get(i));
                }
                adapter = new Shop_local_sell_select_customer_adapter(phoneList);
                adapter.setOnClickListener(Shop_local_sell_select_customer_phone_fragment.this::itemClick);
                recyclerView.setAdapter(adapter);
            }
        });

    }

    @Override
    public void itemClick(int position) {
        LocalSell_property.Customer_phone = phoneList.get(position).getCustomer_phone();

        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.frame_container, new Shop_local_sell_fragment(shop_id, 3)).addToBackStack(null).commit();


    }
}