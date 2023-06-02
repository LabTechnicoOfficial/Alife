package com.ALife.alife.Utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Helpers {

    public static String uniqueProductCodeGenerator(String text) {

        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());

        return text + timeStamp;
    }
}
