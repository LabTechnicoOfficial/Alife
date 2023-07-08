package com.ALife.alife.view.Customer;

import android.annotation.SuppressLint;
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

import com.ALife.alife.R;
import com.ALife.alife.adapter.Customer.Customer_due_shop_adapter;
import com.ALife.alife.model.customer_due_shop_list_response;
import com.ALife.alife.viewmodel.Customer_shop;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Customer_due_shop_fragment extends Fragment implements Customer_due_shop_adapter.OnItemClickListener {
    String customerID;
    TextView totalDueText, totalDuetitle,totalShop;
    RecyclerView dueShopView;
    EditText search;
    Customer_shop customer_shop;
    Double total_due = 0.0;
    private List<customer_due_shop_list_response> due_shop_list;
    private List<customer_due_shop_list_response> temp;
    private Customer_due_shop_adapter adapter;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    int page=1,limit=10;
    int check;
    public Customer_due_shop_fragment(String customerID) {
        this.customerID = customerID;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main();
    }

    private void main() {
        checkConnection();
        customer_shop = new ViewModelProvider(getActivity()).get(Customer_shop.class);
        check = 0;
        page=1;
        limit=10;
        due_shop_list=new ArrayList<>();
        due_shop(page,limit);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(TextUtils.isEmpty(search.getText().toString().trim()))) {
                    try {
                        // adapter.getFilter().filter(search.getText());
                        get_shop_by_search(search.getText().toString().trim());
                    } catch (Exception e) {
                    }
                } else {
                    page=1;
                    limit=10;
                    due_shop(page,limit);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


    }

    private void get_shop_by_search(String value) {
        temp=new ArrayList<>();
        adapter = new Customer_due_shop_adapter(temp);
        adapter.setOnClickListener(Customer_due_shop_fragment.this::onItemClick);
        dueShopView.setAdapter(adapter);
        for(int i=0;i<due_shop_list.size();i++)
        {
            if(due_shop_list.get(i).getShop_name().toLowerCase().contains(value.toLowerCase())||due_shop_list.get(i).getShop_phone().toLowerCase().contains(value.toLowerCase())||due_shop_list.get(i).getShop_address().toLowerCase().contains(value.toLowerCase()))
            {
                temp.add(due_shop_list.get(i));
            }
        }

        adapter = new Customer_due_shop_adapter(temp);
        adapter.setOnClickListener(Customer_due_shop_fragment.this::onItemClick);
        dueShopView.setAdapter(adapter);

    }

    private void due_shop(int Page,int Limit) {
        progressBar.setVisibility(View.GONE);
        if(Page==1)
        {
            customer_shop.getDueShop(customerID).observe(getViewLifecycleOwner(), new Observer<List<customer_due_shop_list_response>>() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onChanged(List<customer_due_shop_list_response> customer_due_shop_list_responses) {
                    due_shop_list = new ArrayList<>();
                    temp=new ArrayList<>();
                    totalShop.setText(String.valueOf(customer_due_shop_list_responses.size()));
                    due_shop_list = customer_due_shop_list_responses;
                    adapter = new Customer_due_shop_adapter(temp);
                    adapter.setOnClickListener(Customer_due_shop_fragment.this::onItemClick);
                    dueShopView.setAdapter(adapter);
                    total_due = 0.0;
                    for (int i = 0; i < due_shop_list.size(); i++) {
                        total_due += Double.parseDouble(due_shop_list.get(i).getTotal_due());
                        if(i<Limit)
                        {
                            temp.add(due_shop_list.get(i));
                        }
                    }
                    if (total_due >= 0) {
                        totalDueText.setText(String.valueOf(new DecimalFormat("##.##").format(total_due)));
                    } else {
                        total_due = total_due * (-1);
                        totalDuetitle.setTextColor(0xffff0000);
                        totalDuetitle.setText("মোট জমা");
                        totalDueText.setTextColor(0xffff0000);

                        totalDueText.setText(String.valueOf(new DecimalFormat("##.##").format(total_due)));
                    }
                    showList(temp);

                }
            });
        }else
        {
            int x=(page-1)*10;

            for(int i=x;i<x+limit-1;i++)
            {
                if(i<due_shop_list.size()-1) {
                    temp.add(due_shop_list.get(i));
                }else
                {
                    break;
                }
            }
            showList(temp);
        }

    }

    private void showList(List<customer_due_shop_list_response> temp) {
        adapter = new Customer_due_shop_adapter(temp);
        adapter.setOnClickListener(Customer_due_shop_fragment.this::onItemClick);
        dueShopView.setAdapter(adapter);
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

                    main();
                }
            });

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_due_shop_fragment, container, false);

        totalDueText = (TextView) view.findViewById(R.id.totalDueTextID);
        totalDuetitle = (TextView) view.findViewById(R.id.totalDueID);
        totalShop = (TextView) view.findViewById(R.id.totalShopID);
        search = (EditText) view.findViewById(R.id.searchID);
        dueShopView = (RecyclerView) view.findViewById(R.id.dueShopViewID);
        dueShopView.setHasFixedSize(true);
        dueShopView.setLayoutManager(new LinearLayoutManager(getContext()));


        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    progressBar.setVisibility(View.VISIBLE);
                    page++;
                    due_shop(page, limit);
                }
            }
        });

        return view;
    }

    @Override
    public void onItemClick(int position) {
        customer_due_shop_list_response shop = due_shop_list.get(position);
        String shop_id = shop.shop_id;
        String shop_name = shop.getShop_name();
        String shop_location = shop.getShop_address();
        String shop_image = shop.getShop_image();
        String shop_phone = shop.getShop_phone();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cus_frame_container, new Customer_shop_details_fragment(customerID, shop_id, shop_name, shop_location, shop_phone, shop_image)).addToBackStack(null).commit();

    }
}