package com.ALife.alife.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

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
}
