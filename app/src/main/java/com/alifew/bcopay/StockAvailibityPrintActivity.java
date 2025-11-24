package com.alifew.bcopay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.alifew.bcopay.DB.AppDatabase;
import com.alifew.bcopay.DB.dao.ProductDao;
import com.alifew.bcopay.DB.entity.Products;
import com.alifew.bcopay.Utils.PDFHelper;
import com.alifew.bcopay.adapter.stock.ShopPrintProductStockAdapter;
import com.alifew.bcopay.databinding.ActivityStockAvailibityPrintBinding;
import com.alifew.bcopay.model.Shop_profile_response;
import com.alifew.bcopay.session.SessionManagement;
import com.alifew.bcopay.viewmodel.ShopProfileViewModel;

import java.util.Set;

public class StockAvailibityPrintActivity extends AppCompatActivity {

    ActivityStockAvailibityPrintBinding binding;
    ProductDao productDao;

    Set<Products> productsList;
    ShopPrintProductStockAdapter shopPrintProductStockAdapter;
    ShopProfileViewModel shop_profile;
    Shop_profile_response shopProfileResponse;
    SessionManagement sessionManagement;
    Dialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStockAvailibityPrintBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PDFHelper.generatePDF(binding.itemView, getApplicationContext());
            }

        });


        initView();

        loadProducts();
    }

    private void loadProducts() {

        runOnUiThread(() -> {
            loader.show();
            shop_profile.getData(String.valueOf(sessionManagement.getUserID())).observe(this, shopProfileResponse -> {
                loader.dismiss();
                shopPrintProductStockAdapter = new ShopPrintProductStockAdapter(productDao.getSearchedProductsList(""), productDao, shopProfileResponse);
                binding.itemView.setAdapter(shopPrintProductStockAdapter);
            });


        });

    }

    private void initView() {
        sessionManagement = new SessionManagement(getApplicationContext());
        binding.itemView.setHasFixedSize(true);
        binding.itemView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        productDao = db.productDao();

        shop_profile = new ViewModelProvider(this).get(ShopProfileViewModel.class);

        loader = new Dialog(StockAvailibityPrintActivity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);
    }
}