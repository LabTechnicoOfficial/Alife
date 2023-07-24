package com.alifew.alife.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.alifew.alife.BuildConfig;
import com.alifew.alife.R;
import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.gkemon.XMLtoPDF.model.SuccessResponse;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Helpers {

    public static String uniqueProductCodeGenerator(String text) {


        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyMMddHHmmss").format(Calendar.getInstance().getTime());

        if (text.length() > 0) {
            text = text.substring(0, 2).toLowerCase(Locale.ROOT) + "al";
            return text + timeStamp;
        } else {
            return timeStamp;
        }


    }

    public static Bitmap barCodeGenerator(Context context, String barCode) {
        //write code here
        Bitmap bitmap = null;
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.encodeBitmap(barCode, BarcodeFormat.CODE_128, 800, 400);


        } catch (Exception e) {

        }
        return bitmap;
    }

    public static void createPDF(View view, Context context, String fileName) {

        PdfGenerator.getBuilder()
                .setContext(context)
                .fromViewSource()
                .fromView(view)
                .setFileName(generateFileName(fileName))
                .setFolderName(Environment.DIRECTORY_DOCUMENTS)
                .openPDFafterGeneration(true)
                .build(new PdfGeneratorListener() {
                    @Override
                    public void onFailure(FailureResponse failureResponse) {
                        super.onFailure(failureResponse);
                        Log.d("dataxx", "onFailure: " + failureResponse.getErrorMessage());
                    }

                    @Override
                    public void showLog(String log) {
                        super.showLog(log);

                        Log.d("dataxx", "showLog: " + log);
                    }

                    @Override
                    public void onStartPDFGeneration() {
                        /*When PDF generation begins to start*/
                    }

                    @Override
                    public void onFinishPDFGeneration() {
                        /*When PDF generation is finished*/
                    }

                    @Override
                    public void onSuccess(SuccessResponse response) {
                        super.onSuccess(response);

                        Log.d("dataxx", "onSuccesPATH: " + response.getPath() + " ab: " + response.getFile().getAbsolutePath());
                        Toast.makeText(context, context.getResources().getString(R.string.file_save) + Html.fromHtml(" \n<b>" + response.getPath() + "<b>"), Toast.LENGTH_SHORT).show();
//                        Intent myIntent = new Intent(Intent.ACTION_VIEW);
//                        myIntent.setDataAndType(Uri.fromFile(response.getFile()), "application/pdf");
//                        myIntent.createChooser(myIntent, "Choose an application to open with:");
//                        context.startActivity(myIntent);

                        //copyFile(response.getFile(), Environment.DIRECTORY_DOCUMENTS, context);

                    }
                });

    }


    @SuppressLint("SimpleDateFormat")
    private static String generateFileName(String fileName) {


        return fileName + new SimpleDateFormat("yyMMddHHmmss").format(Calendar.getInstance().getTime());
    }

    public static Bitmap loadBitmap(View v, int width, int height) {
        Bitmap bitmapPDF = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapPDF);
        v.draw(canvas);

        return bitmapPDF;
    }
}
