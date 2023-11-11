package com.alifew.alifeworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.alifew.alifeworld.DB.entity.Products;
import com.alifew.alifeworld.Utils.Helpers;
import com.alifew.alifeworld.Utils.PDFHelper;
import com.alifew.alifeworld.adapter.Barcode.Barcode_view_adapter;
import com.alifew.alifeworld.session.SessionManagement;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class BarCodePrintActivity extends AppCompatActivity {

    ImageView closeButton;
    List<Products> markedProductList;
    RecyclerView barCodeView;
    Spinner itemSpinner;
    ImageView printButton;
    LinearLayout barCodeLayout;
    SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        initView();

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        List<String> items = new ArrayList<>();
        if (markedProductList.size() < 5) {
            for (int i = 0; i < markedProductList.size(); i++) {
                items.add(String.valueOf(i + 1));
            }
        } else {
            for (int i = 0; i < 5; i++) {
                items.add(String.valueOf(i + 1));
            }
        }

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(aa);

        itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int item = Integer.parseInt(parent.getItemAtPosition(position).toString());
                barCodeView.setLayoutManager(new GridLayoutManager(BarCodePrintActivity.this, item));
                Barcode_view_adapter barcodeViewAdapter = new Barcode_view_adapter(markedProductList, sessionManagement.getSaveShopName(), item);
                barCodeView.setAdapter(barcodeViewAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Helpers.createPDF(barCodeLayout, getApplicationContext(), "bar");

                createPDF(barCodeLayout, barCodeView.getWidth(), barCodeView.getHeight());

                //createPDF2(barCodeLayout);
               // PDFHelper.generatePDF(barCodeView, getApplicationContext());
            }
        });

    }

    private void initView() {
        sessionManagement = new SessionManagement(this);
        closeButton = findViewById(R.id.closeButton);
        markedProductList = new ArrayList<>();
        markedProductList = getIntent().getParcelableArrayListExtra("BARCODELIST");

        barCodeView = findViewById(R.id.barCodeView);
        barCodeView.setHasFixedSize(true);

        itemSpinner = findViewById(R.id.itemSpinner);

        printButton = findViewById(R.id.printButton);
        barCodeLayout = findViewById(R.id.barCodeLayout);

        //    Toast.makeText(this, String.valueOf(markedProductList.get(0).getName()), Toast.LENGTH_SHORT).show();
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

    private void createPDF2(LinearLayout barCodeLayout) {

        String folderName = "Alife";


        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), folderName);

        if (!dir.exists()) {
            dir.mkdir();

        }

        File pdfFile = new File(dir, Helpers.generateFileName("bar") + ".pdf");


        // File pdfFile = new File(Environment.getExternalStorageDirectory(), "my_pdf_file.pdf");

        try {
            Document document = new Document();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PdfWriter.getInstance(document, Files.newOutputStream(pdfFile.toPath()));
            }

            // Open the Document for writing
            document.open();

            // Get the ScrollView's content as a Bitmap
            Bitmap bitmap = Helpers.loadBitmap(barCodeLayout);

            // Create an iText Image from the Bitmap
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());

            // Scale the image to fit the PDF page
            image.scaleToFit(document.getPageSize());

            // Add the image to the PDF
            document.add(image);

            // Close the Document
            document.close();

            Log.d("dataxx", "filepath: " + pdfFile.getPath().toString());
            Helpers.openPdf(pdfFile.getPath().toString(), getApplicationContext());
        } catch (Exception e) {
            Log.d("datax", "createPDF2: " + e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}