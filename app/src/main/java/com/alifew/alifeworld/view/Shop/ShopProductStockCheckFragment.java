package com.alifew.alifeworld.view.Shop;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.alifew.alifeworld.DB.AppDatabase;
import com.alifew.alifeworld.DB.dao.ProductDao;
import com.alifew.alifeworld.DB.entity.Products;
import com.alifew.alifeworld.R;
import com.alifew.alifeworld.adapter.stock.ShopProductStockCheckSearchAdapter;
import com.alifew.alifeworld.databinding.FragmentShopProductStockCheckBinding;
import com.alifew.alifeworld.model.Get_product_response;
import com.alifew.alifeworld.session.SessionManagement;
import com.alifew.alifeworld.viewmodel.Get_all_shop_product;

import java.util.ArrayList;
import java.util.List;


public class ShopProductStockCheckFragment extends Fragment {

    FragmentShopProductStockCheckBinding binding;
    ProductDao productDao;
    SessionManagement sessionManagement;
    Get_all_shop_product getAllShopProduct;
    String shopID;

    Dialog loader;


    List<Products> productList = new ArrayList<>();
    ShopProductStockCheckSearchAdapter adapter;
    RecyclerView itemView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentShopProductStockCheckBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        initView(view);

        loadProducts();

        binding.searchEditText.setOnClickListener(v -> {
            Dialog productSearchAlert = new Dialog(getActivity());
            productSearchAlert.setContentView(R.layout.search_product_with_type_alert);
            productSearchAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            productSearchAlert.setCancelable(false);
            productSearchAlert.show();

            Window window = productSearchAlert.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);

            ImageView closeButton = productSearchAlert.findViewById(R.id.closeButton);
            closeButton.setOnClickListener(v1 -> {
                productSearchAlert.dismiss();
            });

            itemView = productSearchAlert.findViewById(R.id.itemView);
            itemView.setHasFixedSize(true);
            itemView.setLayoutManager(new LinearLayoutManager(getActivity()));

            EditText searchEditText = productSearchAlert.findViewById(R.id.searchEditText);
            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    productList = productDao.getSearchedProductsList(s.toString().trim());
                    //Toast.makeText(getActivity(), String.valueOf(productList.size()), Toast.LENGTH_SHORT).show();
                    setUpAdapter(productList);
                }
            });

            get_products();
        });

        return view;
    }

    private void initView(View view) {
        AppDatabase db = AppDatabase.getDatabase(getActivity());
        productDao = db.productDao();
        productDao.clearProducts();
        productDao.resetPrimaryKeySequence("tblProducts");

        sessionManagement = new SessionManagement(getActivity());
        getAllShopProduct = new ViewModelProvider(getActivity()).get(Get_all_shop_product.class);
        shopID = String.valueOf(sessionManagement.getSession());

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);
    }

    private void loadProducts() {
        loader.show();
        getAllShopProduct.getAllProductWithOutPagination(shopID).observe(getViewLifecycleOwner(), new Observer<List<Get_product_response>>() {
            @Override
            public void onChanged(List<Get_product_response> get_product_responses) {
                loader.dismiss();
                for (int i = 0; i < get_product_responses.size(); i++) {
                    String printCheck = "0";
                    Get_product_response response = get_product_responses.get(i);
                    productDao.insertProducts(new Products(response.getProduct_id(), response.getProduct_name(), printCheck, response.getProduct_image(), response.getCode(), response.getStock_amount(), response.getSelling_price(), response.getProduct_unit(), response.getType(), "0"));
                }

                new Handler().postDelayed(() -> {
                    //get_products();
                }, 1000);


            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    private void get_products() {
        productList = productDao.getSearchedProductsList("");
        setUpAdapter(productList);

    }

    private void setUpAdapter(List<Products> productList) {
        adapter = new ShopProductStockCheckSearchAdapter(productList, productDao);
        itemView.setAdapter(adapter);
    }
}