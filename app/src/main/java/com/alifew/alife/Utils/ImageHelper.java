package com.alifew.alife.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.alifew.alife.R;
import com.bumptech.glide.Glide;

public class ImageHelper {
    public static void imageLoader(Context context, ImageView imageView, String imagePath) {
        Glide.with(context)
                .load(imagePath)
                .centerCrop()
                .placeholder(R.drawable.loader)
                .into(imageView);
    }
}
