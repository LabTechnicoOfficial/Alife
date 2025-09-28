package com.alifew.bcopay.view.Shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alifew.bcopay.DB.AppDatabase;
import com.alifew.bcopay.DB.dao.ProductDao;
import com.alifew.bcopay.R;
import com.alifew.bcopay.databinding.FragmentShopProductStockCheckPrintBinding;


public class ShopProductStockCheckPrintFragment extends Fragment {

    FragmentShopProductStockCheckPrintBinding binding;
    ProductDao productDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShopProductStockCheckPrintBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        initView(view);

        loadProducts();

        return view;
    }

    private void loadProducts() {
    }

    private void initView(View view) {
        AppDatabase db = AppDatabase.getDatabase(getActivity());
        productDao = db.productDao();
    }
}