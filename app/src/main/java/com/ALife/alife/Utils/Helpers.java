package com.ALife.alife.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public static void createPDF(Bitmap bitmapPDF, Context context, String fileName, float width, float height) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();

        //float width = bitmapPDF.getWidth();//context.getResources().getDisplayMetrics().widthPixels;
       // float height = bitmapPDF.getHeight();//context.getResources().getDisplayMetrics().heightPixels;

        int convertWidth = (int) width, convertHeight = (int) height;

        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHeight, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);


        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);


        bitmapPDF = Bitmap.createScaledBitmap(bitmapPDF, convertWidth, convertHeight, true);
        canvas.drawBitmap(bitmapPDF, 0, 0, null);
        pdfDocument.finishPage(page);

        String targetPDF;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + FolderName);

            targetPDF = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toURI())+"/"+ fileName + new SimpleDateFormat("yyMMddHHmmss", Locale.getDefault()).format(new Date()) + ".pdf";
        } else {
            targetPDF = new File(Environment.getExternalStorageDirectory().toURI()) +"/"+ fileName + new SimpleDateFormat("yyMMddHHmmss", Locale.getDefault()).format(new Date()) + ".pdf";

        }


        File file;
        file = new File(targetPDF);

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(context, "Successfully saved at " + targetPDF, Toast.LENGTH_SHORT).show();
            //openPDF(targetPDF, context);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("errorxx", e.getMessage());

            pdfDocument.close();

            Toast.makeText(context, "Successfully saved at Documents", Toast.LENGTH_SHORT).show();

            //openPDF(targetPDF, context);
        }
    }

    private static void openPDF(String path, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(path)), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }

    public static Bitmap loadBitmap(View v, int width, int height) {
        Bitmap bitmapPDF = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapPDF);
        v.draw(canvas);

        return bitmapPDF;
    }
}
