package com.alifew.alife.view.Customer;

import android.os.Bundle;

import androidx.annotation.Nullable;
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

import com.alifew.alife.R;
import com.alifew.alife.adapter.Customer.Customer_category_adapter;
import com.alifew.alife.model.Category_response;
import com.alifew.alife.viewmodel.Category_fetch;

import java.util.ArrayList;
import java.util.List;

public class Customer_categories_fragment extends Fragment implements Customer_category_adapter.OnItemClickListener {

    String shopID;
    RecyclerView categoriesView;
    Category_fetch category_fetch;
    private List<Category_response> categoryList;
    private Customer_category_adapter adapter;
    ProgressBar progressBar;
    int page=1,limit=10,end=0;

    public Customer_categories_fragment(String shopID) {
        this.shopID = shopID;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        main();
    }

    private void main() {
        categoryList = new ArrayList<>();
        adapter = new Customer_category_adapter(categoryList);
        adapter.setOnClickListener(Customer_categories_fragment.this::OnItemClick);
        categoriesView.setAdapter(adapter);
        page=1;
        end=0;
        get_category(page,limit);

    }
    public void get_category(int Page,int Limit)
    {
        category_fetch = new ViewModelProvider(getActivity()).get(Category_fetch.class);
        category_fetch.getdata(shopID, Page, Limit).observe(getViewLifecycleOwner(), new Observer<List<Category_response>>() {
            @Override
            public void onChanged(List<Category_response> category_responses) {
                progressBar.setVisibility(View.GONE);
                for(int i=0;i<category_responses.size();i++)
                {
                    categoryList.add(category_responses.get(i));
                }
                if(category_responses.size()<Limit)
                {
                    end=1;
                }
                //categoryList = category_responses;
                adapter = new Customer_category_adapter(categoryList);
                adapter.setOnClickListener(Customer_categories_fragment.this::OnItemClick);
                categoriesView.setAdapter(adapter);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.customer_categories_fragment, container, false);

        categoriesView = (RecyclerView) view.findViewById(R.id.categoriesViewID);
        categoriesView.setHasFixedSize(true);
        categoriesView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        NestedScrollView nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if(end==0) {
                        progressBar.setVisibility(View.VISIBLE);
                        page++;
                        get_category(page, limit);
                    }
                }
            }
        });


        return view;
    }

    @Override
    public void OnItemClick(int position) {
        Category_response response = categoryList.get(position);
        String categoryID = response.getCatagory01y_id();

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cus_frame_container, new Customer_products_fragment(shopID, categoryID)).addToBackStack(null).commit();

    }
}