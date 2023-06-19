package com.ALife.alife.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Helpers {

    public static String uniqueProductCodeGenerator(String text) {


        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());

        if (text.length() > 0) {
            text = text.substring(0, 2).toLowerCase(Locale.ROOT) + "al";
            return text + timeStamp;
        } else {
            return timeStamp;
        }


    }

    public static void barCodeGenerator(Context context, String barCode) {
        //write code here
        Toast.makeText(context, barCode, Toast.LENGTH_SHORT).show();
    }
}
