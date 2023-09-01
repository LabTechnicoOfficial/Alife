package com.alifew.alife.view.Shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alifew.alife.R;
import com.alifew.alife.adapter.Shop_local_sell_select_product_adapter;
import com.alifew.alife.model.local_sell.LocalSell_property;
import com.alifew.alife.model.local_sell.Get_local_sell_product_response;
import com.alifew.alife.viewmodel.Local_sell.Get_local_sell;

import java.util.ArrayList;
import java.util.List;

public class Shop_local_sell_select_product_fragment extends Fragment implements Shop_local_sell_select_product_adapter.OnItemClickListener {

    private String shop_id;
    Get_local_sell get_local_sell;
    private List<Get_local_sell_product_response> productList;
    RecyclerView productsView;
    private Shop_local_sell_select_product_adapter adapter;
    private GridLayoutManager layoutmanager;
    EditText search;

    public Shop_local_sell_select_product_fragment(String shop_id) {
        // Required empty public constructor
        this.shop_id = shop_id;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_local_sell_select_product_fragment, container, false);
        get_local_sell = new ViewModelProvider(this).get(Get_local_sell.class);
        productsView = view.findViewById(R.id.gridRecyclerViewID);
        search = (EditText) view.findViewById(R.id.gridProductSearchID);
        layoutmanager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        productsView.setLayoutManager(layoutmanager);
        products_func();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
// implement search code
                if (TextUtils.isEmpty(search.getText().toString().trim())) {
                    products_func();
                } else {
                    search_product_function(search.getText().toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }

    private void products_func() {

        //Toast.makeText(getActivity(), "hi", Toast.LENGTH_SHORT).show();
//        get_local_sell.getData_product(shop_id).observe(getViewLifecycleOwner(), new Observer<List<Get_local_sell_product_response>>() {
//            @Override
//            public void onChanged(List<Get_local_sell_product_response> get_local_sell_product_responses) {
//                productList = new ArrayList<>();
//                productList = get_local_sell_product_responses;
//                adapter = new Shop_local_sell_select_product_adapter(productList);
//                adapter.setOnClickListener(Shop_local_sell_select_product_fragment.this::itemClick);
//                productsView.setAdapter(adapter);
//            }
//        });
    }

    private void search_product_function(String searchText) {
//        get_local_sell.getData_product_bySearch(shop_id, searchText).observe(getViewLifecycleOwner(), new Observer<List<Get_local_sell_product_response>>() {
//            @Override
//            public void onChanged(List<Get_local_sell_product_response> get_local_sell_product_responses) {
//                productList = new ArrayList<>();
//                productList = get_local_sell_product_responses;
//                adapter = new Shop_local_sell_select_product_adapter(productList);
//                adapter.setOnClickListener(Shop_local_sell_select_product_fragment.this::itemClick);
//                productsView.setAdapter(adapter);
//            }
//        });
    }

    @Override
    public void itemClick(int position) {
        Get_local_sell_product_response product = productList.get(position);
        String sell_price = LocalSell_property.Product_price;
        String buy_price = LocalSell_property.Product_buePrice;
        String profit = LocalSell_property.SellProfit;
        String description = LocalSell_property.Product_description;
        Double product_profit;
        if (product.getBuy_price().equals("0"))
            product_profit = 0.0;
        else {
            product_profit = Double.parseDouble(product.getPrice()) - Double.parseDouble(product.getBuy_price());
        }
        if (!sell_price.isEmpty())
            LocalSell_property.Product_price = String.valueOf(Double.parseDouble(sell_price) + Double.parseDouble(product.getPrice()));
        else
            LocalSell_property.Product_price = product.getPrice();
        if (!buy_price.isEmpty())
            LocalSell_property.Product_buePrice = String.valueOf(Double.parseDouble(buy_price) + Double.parseDouble(product.getBuy_price()));
        else
            LocalSell_property.Product_buePrice = product.getBuy_price();
        if (!profit.isEmpty())
            LocalSell_property.SellProfit = String.valueOf(Double.parseDouble(profit) + product_profit);
        else
            LocalSell_property.SellProfit = String.valueOf(product_profit);
        LocalSell_property.Product_description = description + "," + product.getProduct_details();
        if (!product.getImage().isEmpty())
            LocalSell_property.Product_image.add(product.getImage());
        // Toast.makeText(getActivity(), String.valueOf(LocalSell_property.Product_image.size()), Toast.LENGTH_SHORT).show();

        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.frame_container, new Shop_local_sell_fragment()).commit();


    }
}