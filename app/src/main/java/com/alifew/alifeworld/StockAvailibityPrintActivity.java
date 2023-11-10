package com.alifew.alifeworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.alifew.alifeworld.DB.AppDatabase;
import com.alifew.alifeworld.DB.dao.ProductDao;
import com.alifew.alifeworld.DB.entity.Products;
import com.alifew.alifeworld.Utils.Helpers;
import com.alifew.alifeworld.adapter.stock.ShopPrintProductStockAdapter;
import com.alifew.alifeworld.databinding.ActivityStockAvailibityPrintBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;

public class StockAvailibityPrintActivity extends AppCompatActivity {

    ActivityStockAvailibityPrintBinding binding;
    ProductDao productDao;

    Set<Products> productsList;
    ShopPrintProductStockAdapter shopPrintProductStockAdapter;

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
                createPDF(binding.barCodeLayout, binding.itemView.getWidth(), binding.itemView.getHeight());}
        });


        initView();

        loadProducts();
    }

    private void loadProducts() {

        runOnUiThread(() -> {

           /* for (int i = 0; i < productDao.getProductsList().size(); i++) {
                productsList.add(productDao.getProductsList().get(i));
            }*/

         //   Toast.makeText(this, String.valueOf(productDao.getSearchedProductsList("").size()), Toast.LENGTH_SHORT).show();

            shopPrintProductStockAdapter = new ShopPrintProductStockAdapter(productDao.getSearchedProductsList(""), productDao);
            binding.itemView.setAdapter(shopPrintProductStockAdapter);
        });

    }

    private void initView() {
        binding.itemView.setHasFixedSize(true);
        binding.itemView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        productDao = db.productDao();
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