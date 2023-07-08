package com.ALife.alife.view.Shop;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ALife.alife.R;
import com.ALife.alife.adapter.Shop_local_page_adapter;
import com.ALife.alife.model.add_local_business_response;
import com.ALife.alife.model.get_local_business_title_response;
import com.ALife.alife.viewmodel.Local_business;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class Shop_local_page_fragment extends Fragment implements Shop_local_page_adapter.onItemClickListener {

    String shopID;
    Shop_local_page_adapter adapter;
    ExtendedFloatingActionButton addButton;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    private List<get_local_business_title_response> data;
    int page = 1, limit = 10, end = 0;
    Local_business local_business;

    public Shop_local_page_fragment(String shopID) {
        this.shopID = shopID;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog alert = new Dialog(getActivity());
                alert.setContentView(R.layout.shop_local_page_alert);
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alert.show();
                alert.setCancelable(false);

                TextInputLayout titleError = (TextInputLayout) alert.findViewById(R.id.titleErrorID);
                TextInputEditText titleText = (TextInputEditText) alert.findViewById(R.id.titleText);
                AppCompatButton addButton = (AppCompatButton) alert.findViewById(R.id.addButtonID);
                ImageView closeButton = (ImageView) alert.findViewById(R.id.closeID);


                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo info = manager.getActiveNetworkInfo();
                        if (info == null) {
                            Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                        }else {
                            if (TextUtils.isEmpty(titleText.getText().toString().trim())) {
                                titleError.setError("Fill this field");
                            }
                            local_business = new ViewModelProvider(getActivity()).get(Local_business.class);
                            local_business.add_title(titleText.getText().toString().trim(),shopID).observe(getViewLifecycleOwner(), new Observer<add_local_business_response>() {
                                @Override
                                public void onChanged(add_local_business_response add_local_business_response) {
                                    if(add_local_business_response.getMessage().equals("added successfully"))
                                    {
                                        alert.dismiss();
                                        main();
                                    }else
                                    {
                                        Toast.makeText(getActivity(),add_local_business_response.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                    }
                });
            }
        });
    }

    private void main() {
        page = 1;
        end = 0;
        data=new ArrayList<>();
        adapter=new Shop_local_page_adapter(data);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(Shop_local_page_fragment.this::OnItemClick);

        getTitle(page, limit);
    }

    public void getTitle(int Page, int Limit) {
        local_business = new ViewModelProvider(getActivity()).get(Local_business.class);
        local_business.get_title(shopID, Page, Limit).observe(getViewLifecycleOwner(), new Observer<List<get_local_business_title_response>>() {
            @Override
            public void onChanged(List<get_local_business_title_response> get_local_business_title_responses) {
                progressBar.setVisibility(View.GONE);
                if (get_local_business_title_responses.size() < Limit) {
                    end = 1;
                }
                for (int i = 0; i < get_local_business_title_responses.size(); i++) {
                    data.add(get_local_business_title_responses.get(i));
                }
                adapter = new Shop_local_page_adapter(data);
                adapter.setOnClickListener(Shop_local_page_fragment.this::OnItemClick);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_local_page_fragment, container, false);

        addButton = (ExtendedFloatingActionButton) view.findViewById(R.id.addButtonID);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewID);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && addButton.getVisibility() == View.VISIBLE) {
                    addButton.hide();
                } else if (dy < 0 && addButton.getVisibility() != View.VISIBLE) {
                    addButton.show();
                }
            }
        });

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > oldScrollY) {
                    addButton.hide();
                } else {
                    addButton.show();
                }
                //addButton.show();
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (end == 0) {
                        progressBar.setVisibility(View.VISIBLE);
                        page++;

                        //filter(page, limit);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void OnItemClick(int position) {
        get_local_business_title_response title=data.get(position);
        //Toast.makeText(getActivity(),"mmm",Toast.LENGTH_SHORT).show();
        String title_id=title.getId();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_local_page_details_fragment(title_id)).addToBackStack(null).commit();


    }
}