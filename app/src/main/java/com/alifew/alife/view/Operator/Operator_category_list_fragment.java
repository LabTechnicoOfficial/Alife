package com.alifew.alife.view.Operator;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.adapter.Shop_category_adapter;
import com.alifew.alife.model.Category_response;
import com.alifew.alife.model.get_shop_products_summary_response;
import com.alifew.alife.viewmodel.Fetch_shop_admin_category;
import com.alifew.alife.viewmodel.Get_category_summary;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.alifew.alife.R.layout.operator_category_list_fragment;

public class Operator_category_list_fragment extends Fragment implements Shop_category_adapter.OnItemClickListener {
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutmanager;
    EditText search;
    TextView total_product, total_sell_price, total_profit, total_items;
    List<Category_response> data;
    private Shop_category_adapter adapter;
    Fetch_shop_admin_category fetch_shop_admin_category;
    private String shop_id, agent_id, agent_type, agent_access;
    double all_profit, all_sell_price;
    int all_product;
    int page=1,limit=10,end=0;
    Get_category_summary get_category_summary;
    ProgressBar progressBar;
    public Operator_category_list_fragment(String shop_id, String agent_id, String agent_access, String agent_type) {
        this.shop_id = shop_id;
        this.agent_id = agent_id;
        this.agent_access = agent_access;
        this.agent_type = agent_type;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkConnection();
        get_category_summary();
        main();



    }

    private void get_category_summary() {
        get_category_summary = new ViewModelProvider(getActivity()).get(Get_category_summary.class);
        get_category_summary.get_SummaryAgent(shop_id,agent_id).observe(getViewLifecycleOwner(), new Observer<get_shop_products_summary_response>() {
            @Override
            public void onChanged(get_shop_products_summary_response get_shop_products_summary_response) {
                total_items.setText(String.valueOf(get_shop_products_summary_response.getAll_product()));
                total_product.setText(String.valueOf(new DecimalFormat("##.##").format(get_shop_products_summary_response.getAll_stock())));
                total_sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(get_shop_products_summary_response.getAll_sell_price())));
                total_profit.setText(String.valueOf(new DecimalFormat("##.##").format(get_shop_products_summary_response.getAll_profit())));
                main();
            }
        });
    }

    private void main() {
        data = new ArrayList<>();
        adapter = new Shop_category_adapter(data, agent_type);
        adapter.setOnClickListener1(Operator_category_list_fragment.this::OnItemClick);
        recyclerView.setAdapter(adapter);
        page=1;
        end=0;
        filter(page,limit);


        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            //OnTextChanged
            int mesbaul;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(TextUtils.isEmpty(search.getText().toString().trim()))) {
                    try {
                        adapter.getFilter().filter(search.getText());
                        get_search_category(search.getText().toString().trim());
                    } catch (Exception e) {

                    }
                } else {
                    data = new ArrayList<>();
                    adapter = new Shop_category_adapter(data, agent_type);
                    adapter.setOnClickListener1(Operator_category_list_fragment.this::OnItemClick);
                    recyclerView.setAdapter(adapter);
                    page=1;
                    end=0;
                    filter(page,limit);

                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void get_search_category(String value) {
        fetch_shop_admin_category = new ViewModelProvider(getActivity()).get(Fetch_shop_admin_category.class);
        fetch_shop_admin_category.getSearchData(agent_id,value).observe(getViewLifecycleOwner(), new Observer<List<Category_response>>() {
            @Override
            public void onChanged(List<Category_response> category_responses) {
                data=category_responses;
                adapter = new Shop_category_adapter(data, agent_type);
                adapter.setOnClickListener1(Operator_category_list_fragment.this::OnItemClick);
                recyclerView.setAdapter(adapter);

            }
        });
    }

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(operator_category_list_fragment, container, false);
        checkConnection();
        recyclerView = view.findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        layoutmanager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutmanager);
        search = (EditText) view.findViewById(R.id.searchID);
        total_product = (TextView) view.findViewById(R.id.totalProductsID);
        total_sell_price = (TextView) view.findViewById(R.id.totalSellPriceID);
        total_profit = (TextView) view.findViewById(R.id.totalProfitID);
        total_items = (TextView) view.findViewById(R.id.totalItemsID);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        NestedScrollView nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if(end==0){
                        progressBar.setVisibility(View.VISIBLE);
                        page++;
                        filter(page, limit);
                    }}
            }
        });


        return view;
    }

    public void filter(int  Page,int Limit) {
        fetch_shop_admin_category = new ViewModelProvider(getActivity()).get(Fetch_shop_admin_category.class);
        fetch_shop_admin_category.getData(agent_id,Page,Limit).observe(getViewLifecycleOwner(), new Observer<List<Category_response>>() {
            @Override
            public void onChanged(List<Category_response> fetch_shop_admin_category_responses) {
                progressBar.setVisibility(View.GONE);
                //data = fetch_shop_admin_category_responses;
                for(int i=0;i<fetch_shop_admin_category_responses.size();i++)
                {
                    data.add(fetch_shop_admin_category_responses.get(i));
                }
                if(fetch_shop_admin_category_responses.size()<Limit)
                {
                    end=1;
                }

                adapter = new Shop_category_adapter(data, agent_type);
                adapter.setOnClickListener1(Operator_category_list_fragment.this::OnItemClick);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public void checkConnection() {
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

    public void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
        //adapter.notifyDataSetChanged();
    }

    @Override
    public void OnItemClick(int position) {
        Category_response clickItem = data.get(position);

        String Category_id = clickItem.getCatagory01y_id();
        String Category_unit = clickItem.getCatagory01y_unit();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Operator_product_fragment(shop_id, Category_id, Category_unit, agent_id, agent_access)).addToBackStack(null).commit();


        Toast.makeText(getActivity(), "go to productList", Toast.LENGTH_SHORT).show();

    }
}
