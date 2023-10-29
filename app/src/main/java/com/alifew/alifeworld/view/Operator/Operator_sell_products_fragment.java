package com.alifew.alifeworld.view.Operator;

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

import com.alifew.alifeworld.Custom_Type.ProductSell;
import com.alifew.alifeworld.R;
import com.alifew.alifeworld.adapter.Sell_product_adapter;
import com.alifew.alifeworld.model.Get_product_response;
import com.alifew.alifeworld.viewmodel.Get_operator_product;
import com.alifew.alifeworld.viewmodel.Get_product;

import java.util.ArrayList;
import java.util.List;

public class Operator_sell_products_fragment extends Fragment implements Sell_product_adapter.OnItemClickListener {

    private String shop_id, agent_id;
    private String Category_id;
    private List<ProductSell> productSellList;
    private String sell_type_selection;// product_select os category_select
    EditText searchBar;
    RecyclerView productView;
    LinearLayoutManager layoutManager;
    Get_operator_product get_all_product;
    Get_product getProduct;

    List<Get_product_response> data;
    private Sell_product_adapter adapter;
    private FragmentManager fragmentManager;
    int page1 = 0, page2 = 0, limit = 10, end1 = 0, end2 = 0;
    ProgressBar progressBar;

    public Operator_sell_products_fragment(String shop_id, String agent_id, List<ProductSell> productSellList) {
        this.shop_id = shop_id;
        this.agent_id = agent_id;
        this.productSellList = productSellList;
        this.Category_id = "0";

    }

    public Operator_sell_products_fragment(String Category_id, String Shop_id, String agent_id, List<ProductSell> productSellList) {
        this.shop_id = Shop_id;
        this.agent_id = agent_id;
        this.Category_id = Category_id;
        this.productSellList = productSellList;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        main();
        //start add product

        //end add product
    }

    private void main() {
        checkConnection();
        data = new ArrayList<>();
        if (Category_id.equals("0")) {
            adapter = new Sell_product_adapter(data);
            adapter.setOnClickListener(Operator_sell_products_fragment.this::OnItemClick);
            productView.setAdapter(adapter);
            page1 = 1;
            end1 = 0;
            select_product1(page1, limit);
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
                            adapter.getFilter().filter(searchBar.getText());
                            data = new ArrayList<>();
                            adapter = new Sell_product_adapter(data);
                            adapter.setOnClickListener(Operator_sell_products_fragment.this::OnItemClick);
                            productView.setAdapter(adapter);
                            get_search_product(searchBar.getText().toString().trim(), 1);
                        } catch (Exception e) {

                        }
                    } else {
                        data = new ArrayList<>();
                        adapter = new Sell_product_adapter(data);
                        adapter.setOnClickListener(Operator_sell_products_fragment.this::OnItemClick);
                        productView.setAdapter(adapter);
                        page1 = 1;
                        end1 = 0;
                        select_product1(page1, limit);
                    }


                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });
        } else {
            adapter = new Sell_product_adapter(data);
            adapter.setOnClickListener(Operator_sell_products_fragment.this::OnItemClick);
            productView.setAdapter(adapter);
            page2 = 1;
            end2 = 0;
            select_product2(page2, limit);
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
                            // adapter.getFilter().filter(searchBar.getText());
                            data = new ArrayList<>();
                            adapter = new Sell_product_adapter(data);
                            adapter.setOnClickListener(Operator_sell_products_fragment.this::OnItemClick);
                            productView.setAdapter(adapter);
                            get_search_product(searchBar.getText().toString().trim(), 2);
                        } catch (Exception e) {

                        }
                    } else {
                        data = new ArrayList<>();
                        adapter = new Sell_product_adapter(data);
                        adapter.setOnClickListener(Operator_sell_products_fragment.this::OnItemClick);
                        productView.setAdapter(adapter);
                        page2 = 1;
                        end2 = 0;
                        select_product2(page2, limit);
                    }


                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });
        }
    }

    private void get_search_product(String value, int state) {
        if (state == 1) {
            get_all_product = new ViewModelProvider(getActivity()).get(Get_operator_product.class);

            get_all_product.getSearchData(agent_id).observe(getViewLifecycleOwner(), new Observer<List<Get_product_response>>() {
                @Override
                public void onChanged(List<Get_product_response> get_product_responses) {
                    for (int i = 0; i < get_product_responses.size(); i++) {
                        String brand_code = get_product_responses.get(i).getBrand() + get_product_responses.get(i).getCode();
                        if ((get_product_responses.get(i).getProduct_name().toLowerCase().contains(value.toLowerCase())) || (get_product_responses.get(i).getBrand().toLowerCase().contains(value.toLowerCase())) || (brand_code.toLowerCase().contains(value.toLowerCase()))) {
                            data.add(get_product_responses.get(i));
                        }
                    }
                    adapter = new Sell_product_adapter(data);
                    adapter.setOnClickListener(Operator_sell_products_fragment.this::OnItemClick);
                    productView.setAdapter(adapter);
                }
            });

        } else if (state == 2) {
            getProduct = new ViewModelProvider(getActivity()).get(Get_product.class);
            getProduct.getCategoryProduct(Category_id).observe(getViewLifecycleOwner(), new Observer<List<Get_product_response>>() {
                @Override
                public void onChanged(List<Get_product_response> get_product_responses) {
                    for (int i = 0; i < get_product_responses.size(); i++) {
                        String brand_code = get_product_responses.get(i).getBrand() + get_product_responses.get(i).getCode();
                        if ((get_product_responses.get(i).getProduct_name().toLowerCase().contains(value.toLowerCase())) || (get_product_responses.get(i).getBrand().toLowerCase().contains(value.toLowerCase())) || (brand_code.toLowerCase().contains(value.toLowerCase()))) {
                            data.add(get_product_responses.get(i));
                        }
                    }
                    adapter = new Sell_product_adapter(data);
                    adapter.setOnClickListener(Operator_sell_products_fragment.this::OnItemClick);
                    productView.setAdapter(adapter);

                }
            });
        }
    }

    private void select_product2(int Page, int Limit) {
        getProduct = new ViewModelProvider(getActivity()).get(Get_product.class);
        getProduct.getdata(Category_id, Page, Limit).observe(getViewLifecycleOwner(), new Observer<List<Get_product_response>>() {
            @Override
            public void onChanged(List<Get_product_response> get_product_responses) {
                progressBar.setVisibility(View.GONE);
                for (int i = 0; i < get_product_responses.size(); i++) {
                    data.add(get_product_responses.get(i));
                }
                if (get_product_responses.size() < Limit) {
                    end2 = 1;
                }
                //data = get_product_responses;
                adapter = new Sell_product_adapter(data);
                adapter.setOnClickListener(Operator_sell_products_fragment.this::OnItemClick);
                productView.setAdapter(adapter);

            }
        });
    }

    private void select_product1(int Page, int Limit) {
        get_all_product = new ViewModelProvider(getActivity()).get(Get_operator_product.class);
        get_all_product.getData(agent_id, Page, Limit).observe(getViewLifecycleOwner(), new Observer<List<Get_product_response>>() {
            @Override
            public void onChanged(List<Get_product_response> get_product_responses) {
                progressBar.setVisibility(View.GONE);
                for (int i = 0; i < get_product_responses.size(); i++) {
                    data.add(get_product_responses.get(i));
                }
                if (get_product_responses.size() < Limit) {
                    end1 = 1;
                }
                //data = get_product_responses;
                adapter = new Sell_product_adapter(data);
                adapter.setOnClickListener(Operator_sell_products_fragment.this::OnItemClick);
                productView.setAdapter(adapter);

            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_sell_products_fragment, container, false);

        productView = view.findViewById(R.id.productViewID);
        searchBar = (EditText) view.findViewById(R.id.searchEditText);

        productView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        productView.setLayoutManager(layoutManager);

        fragmentManager = getFragmentManager();

        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        NestedScrollView nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    progressBar.setVisibility(View.VISIBLE);
                    if (Category_id.equals("0")) {
                        if (end1 == 0) {
                            page1++;
                            select_product1(page1, limit);
                        }
                    } else {
                        if (end2 == 0) {
                            page2++;
                            select_product2(page2, limit);
                        }
                    }
                }

            }
        });


        return view;
    }

    @Override
    public void OnItemClick(int position) {
        Get_product_response product = data.get(position);
        String productID = product.getProduct_id();
        fragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.frame_container, new Operator_sell_product_selected_fragment(shop_id, agent_id, productID, productSellList)).addToBackStack(null).commit();
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
                    refreshFragment();
                }
            });

        }
    }

    private void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
    }

}