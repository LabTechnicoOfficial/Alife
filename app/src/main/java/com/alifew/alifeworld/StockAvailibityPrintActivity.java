package com.alifew.alifeworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.alifew.alifeworld.DB.AppDatabase;
import com.alifew.alifeworld.DB.dao.ProductDao;
import com.alifew.alifeworld.DB.entity.Products;
import com.alifew.alifeworld.Utils.Helpers;
import com.alifew.alifeworld.Utils.PDFHelper;
import com.alifew.alifeworld.adapter.stock.ShopPrintProductStockAdapter;
import com.alifew.alifeworld.databinding.ActivityStockAvailibityPrintBinding;
import com.alifew.alifeworld.model.Shop_profile_response;
import com.alifew.alifeworld.session.SessionManagement;
import com.alifew.alifeworld.viewmodel.ShopProfileViewModel;

import java.io.File;
import java.nio.file.Files;
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
                // createPDF(binding.barCodeLayout, binding.itemView.getWidth(), binding.itemView.getHeight());

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

    private void createPDF(View barCodeLayout, int width, int height) {
        Log.d("dataxx", "size: " + String.valueOf(width) + " " + String.valueOf(height));
        Bitmap bitmap = Helpers.loadBitmap(barCodeLayout);
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int convertedWidth = displayMetrics.widthPixels;
        int convertedHeight = displayMetrics.heightPixels;

        Log.d("dataxx", "cont size: " + String.valueOf(convertedWidth) + " " + String.valueOf(convertedHeight));

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertedWidth, convertedHeight, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        canvas.drawPaint(paint);
        bitmap = Bitmap.createScaledBitmap(bitmap, convertedWidth, convertedHeight, true);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);


        String folderName = "Alife";


        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), folderName);

        if (!dir.exists()) {
            dir.mkdir();

        }

        File file = new File(dir, Helpers.generateFileName("bar") + ".pdf");

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                document.writeTo(Files.newOutputStream(file.toPath()));
            }

            Helpers.openPdf(file.getPath().toString(), this);

            Log.d("dataxx", "path: " + file.getPath().toString());
        } catch (Exception e) {
            Log.d("dataxx", "createPDF: " + e.getMessage());
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
            Log.d("dataxx", "epath: " + file.getPath().toString());
            document.close();

            //openPdf(file.getPath().toString());
//            Toast.makeText(getActivity(), "PDF", Toast.LENGTH_SHORT).show();
        }
    }
}