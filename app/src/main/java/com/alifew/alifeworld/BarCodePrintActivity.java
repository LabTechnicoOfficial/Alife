package com.alifew.alifeworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
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
import com.alifew.alifeworld.adapter.Barcode.Barcode_view_adapter;
import com.alifew.alifeworld.session.SessionManagement;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        PdfDocument pdfDocument = new PdfDocument();
        Bitmap bitmap = Helpers.loadBitmap(barCodeLayout);
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int convertedWidth = displayMetrics.widthPixels;
        int convertedHeight = displayMetrics.heightPixels;
        int viewWidth = barCodeLayout.getWidth();
        int viewHeight = barCodeLayout.getHeight();

        /*PdfDocument document = new PdfDocument();
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
        }*/
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(viewWidth, viewHeight+100, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Draw the view hierarchy directly to the PDF canvas
        barCodeLayout.draw(canvas);
        pdfDocument.finishPage(page);

        // Generate output file path
        String currentDate = new SimpleDateFormat("ddMMyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "Alife_Invoice_" + currentDate + "_" + currentTime + ".pdf";

        // Save the PDF document
        try {
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(downloadsDir, fileName);

            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(getApplicationContext(), "PDF saved to Downloads", Toast.LENGTH_SHORT).show();

            // Optionally open the PDF
            openPDF(file);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error saving PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("PDF_CREATION", "Error saving PDF", e);
        } finally {
            pdfDocument.close();
        }
    }

    private void openPDF(File pdfFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(getApplicationContext(),
                getApplicationContext().getPackageName() + ".provider",
                pdfFile);

        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(),
                    "No PDF viewer installed",
                    Toast.LENGTH_SHORT).show();
        }
    }

}