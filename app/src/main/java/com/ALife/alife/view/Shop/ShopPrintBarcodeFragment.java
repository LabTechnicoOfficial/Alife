package com.ALife.alife.view.Shop;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ALife.alife.DB.AppDatabase;
import com.ALife.alife.DB.InsertProductThread;
import com.ALife.alife.DB.ProductDao;
import com.ALife.alife.DB.Products;
import com.ALife.alife.R;
import com.ALife.alife.adapter.Shop_product_barcode_print_adapter;
import com.ALife.alife.model.Get_product_response;
import com.ALife.alife.session.SessionManagement;
import com.ALife.alife.viewmodel.Get_all_shop_product;

import java.util.ArrayList;
import java.util.List;

public class ShopPrintBarcodeFragment extends Fragment implements Shop_product_barcode_print_adapter.OnCheckBoxClickListener {

    RecyclerView productView;
    EditText searchEditText;
    SessionManagement sessionManagement;
    String shopID;

    Get_all_shop_product getAllShopProduct;


    int page = 1, limit = 10;

    Shop_product_barcode_print_adapter productAdapter;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;

    ProductDao productDao;
    List<Products> productList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_print_barcode, container, false);

        init_view(view);


        loadProducts(page);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                //offersButton.show();
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    page++;
                    loadProducts(page);

                }
            }
        });
/*
        Button click = view.findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_products();
            }
        });*/


        return view;
    }

    private void loadProducts(int page) {
        progressBar.setVisibility(View.VISIBLE);
        getAllShopProduct.getData(shopID, page, limit).observe(getViewLifecycleOwner(), new Observer<List<Get_product_response>>() {
            @Override
            public void onChanged(List<Get_product_response> get_product_responses) {

                for (int i = 0; i < get_product_responses.size(); i++) {

                    String printCheck = "0";

                    Get_product_response response = get_product_responses.get(i);
//                    if (response.getProduct_id().equals("319")) {
//                        printCheck = "1";
//                    }
                    InsertProductThread insertProductThread = new InsertProductThread(response.getProduct_id(), response.getProduct_name(), printCheck, response.getProduct_image(), response.getCode(), getActivity());
                    insertProductThread.start();

                }

                // get_products();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        get_products();
                    }
                }, 1000);


            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    private void get_products() {
        progressBar.setVisibility(View.GONE);
        productList = productDao.getProductsList();
        //Toast.makeText(getActivity(), String.valueOf(productList.size()), Toast.LENGTH_SHORT).show();
        productAdapter = new Shop_product_barcode_print_adapter(productList);
        productAdapter.notifyDataSetChanged();
        productAdapter.setOnClickListener(ShopPrintBarcodeFragment.this::onCheckBoxClick);
        productView.setAdapter(productAdapter);


    }

    private void setUpAdapter() {
        productAdapter = new Shop_product_barcode_print_adapter(productList);
        //productAdapter.notifyDataSetChanged();
        productView.setAdapter(productAdapter);
    }

    private void init_view(View view) {
        getAllShopProduct = new ViewModelProvider(getActivity()).get(Get_all_shop_product.class);
        sessionManagement = new SessionManagement(getActivity());
        progressBar = view.findViewById(R.id.progressBar);
        nestedScrollView = view.findViewById(R.id.nestedRecyclerView);
        productView = view.findViewById(R.id.productView);
        productView.setHasFixedSize(true);
        productView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchEditText = view.findViewById(R.id.searchEditText);
        shopID = String.valueOf(sessionManagement.getSession());

        AppDatabase db = Room.databaseBuilder(getActivity(), AppDatabase.class, "alifeDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        productDao = db.productDao();
        productDao.clearProducts();

        //Toast.makeText(getActivity(), shopID, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckBoxClick(int position, boolean state) {
        Products response = productList.get(position);
        //Toast.makeText(getActivity(), response.getProductID() + " " + String.valueOf(state), Toast.LENGTH_SHORT).show();
        productDao.updatePrintCheck(response.getProductID(), state ? "1":"0");
    }
}