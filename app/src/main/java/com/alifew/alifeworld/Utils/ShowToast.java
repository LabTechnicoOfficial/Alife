package com.alifew.alifeworld.Utils;

import android.content.Context;

import com.shashank.sony.fancytoastlib.FancyToast;

public class ShowToast {

    public static void successToast(String message, Context context) {
        FancyToast.makeText(context, message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
    }

    public static void errorToast(String message, Context context){
        FancyToast.makeText(context, message, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
    }
}
