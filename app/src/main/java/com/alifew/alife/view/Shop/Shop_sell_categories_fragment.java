package com.alifew.alife.view.Shop;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alifew.alife.Custom_Type.ProductSell;
import com.alifew.alife.R;
import com.alifew.alife.adapter.Sell_category_adapter;
import com.alifew.alife.model.Category_response;
import com.alifew.alife.viewmodel.Category_fetch;

import java.util.ArrayList;
import java.util.List;


public class Shop_sell_categories_fragment extends Fragment implements Sell_category_adapter.OnItemClickListener {

    String shop_id;
    private List<ProductSell> productSellList;
    EditText searchBar;
    RecyclerView categoryView;
    private RecyclerView.LayoutManager layoutManager;
    Category_fetch category_fetch;
    List<Category_response> data;
    private Sell_category_adapter adapter;
    private FragmentManager fragmentManager;

    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    int page = 1, limit = 10, end = 0;

    public Shop_sell_categories_fragment(String shop_id, List<ProductSell> productSellList) {
        this.shop_id = shop_id;
        this.productSellList = productSellList;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkConnection();
        main();
    }

    private void main() {
        category_fetch = new ViewModelProvider(getActivity()).get(Category_fetch.class);
        if (data != null) {
            data.clear();
        } else {
            data = new ArrayList<>();
        }

        adapter = new Sell_category_adapter(data);
        categoryView.setAdapter(adapter);
        adapter.setOnClickListener(Shop_sell_categories_fragment.this::OnItemClick);
        page = 1;
        end = 0;
        filter(page, limit);
        searchBar.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            //OnTextChanged
            int mesbaul;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(TextUtils.isEmpty(searchBar.getText().toString().trim()))) {
                    try {
                        //adapter.getFilter().filter(searchBar.getText());
                        data = new ArrayList<>();

                        adapter = new Sell_category_adapter(data);

                        adapter.setOnClickListener(Shop_sell_categories_fragment.this::OnItemClick);
                        get_search_category(searchBar.getText().toString().trim());
                    } catch (Exception e) {

                    }
                } else {
                    data = new ArrayList<>();

                    adapter = new Sell_category_adapter(data);

                    adapter.setOnClickListener(Shop_sell_categories_fragment.this::OnItemClick);
                    end = 0;
                    page = 1;
                    filter(page, limit);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

    }

    private void get_search_category(String value) {
        category_fetch.getCategory(shop_id, value).observe(getViewLifecycleOwner(), new Observer<List<Category_response>>() {
            @Override
            public void onChanged(List<Category_response> category_responses) {
                data = category_responses;

                adapter = new Sell_category_adapter(data);

                //adapter.setOnClickListener(Showdetails.this);
                adapter.setOnClickListener(Shop_sell_categories_fragment.this::OnItemClick);
                // adapter.setOnClick(Categories_fragment.this::OnItemEdit);

                categoryView.setAdapter(adapter);
            }
        });

    }

    private void filter(int Page, int Limit) {
        category_fetch.getdata(shop_id, Page, Limit).observe(getViewLifecycleOwner(), new Observer<List<Category_response>>() {
            @Override
            public void onChanged(List<Category_response> category_responses) {
                progressBar.setVisibility(View.GONE);
                //data.clear();
                // data = category_responses;
                if(page==1)
                {
                    data=new ArrayList<>();
                    adapter = new Sell_category_adapter(data);

                    //adapter.setOnClickListener(Showdetails.this);
                    adapter.setOnClickListener(Shop_sell_categories_fragment.this::OnItemClick);
                    // adapter.setOnClick(Categories_fragment.this::OnItemEdit);

                    categoryView.setAdapter(adapter);
                }
                for (int i = 0; i < category_responses.size(); i++) {
                    data.add(category_responses.get(i));
                }
                if (category_responses.size() < limit) {

                    end = 1;
                }
                adapter = new Sell_category_adapter(data);

                //adapter.setOnClickListener(Showdetails.this);
                adapter.setOnClickListener(Shop_sell_categories_fragment.this::OnItemClick);
                // adapter.setOnClick(Categories_fragment.this::OnItemEdit);

                categoryView.setAdapter(adapter);
            }

        });
    }

    private void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        Dialog networkAlert = new Dialog(getActivity());
        networkAlert.setContentView(R.layout.network_alert);
        networkAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView connectButton = (TextView) networkAlert.findViewById(R.id.connectButtonID);
        if (info == null) {
            networkAlert.show();
            connectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    networkAlert.dismiss();
                    main();

                    //refreshFragment();
                }
            });

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_sell_categories_fragment, container, false);
        searchBar = (EditText) view.findViewById(R.id.searchEditText);
        categoryView = (RecyclerView) view.findViewById(R.id.categoriesViewID);

        layoutManager = new LinearLayoutManager(view.getContext());
        categoryView.setHasFixedSize(true);
        categoryView.setLayoutManager(layoutManager);
        fragmentManager = getFragmentManager();


        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);
        data=new ArrayList<>();
        page=1;
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    if (end == 0) {
                        progressBar.setVisibility(View.VISIBLE);
                        page++;
                        filter(page, limit);
                    }
                }


            }
        });
        return view;
    }

    @Override
    public void OnItemClick(int position) {
        Category_response category = data.get(position);
        String category_id = category.getCatagory01y_id();
        //Toast.makeText(getActivity(), String.valueOf(data.size()), Toast.LENGTH_SHORT).show();
        /*fragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.frame_container, new Shop_sell_products_fragment(category_id, shop_id, productSellList)).addToBackStack(null).commit();*/
        productSellList=new ArrayList<>();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new Shop_sell_products_fragment(category_id, shop_id, productSellList)).addToBackStack(null).commit();


    }
}