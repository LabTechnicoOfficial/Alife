package com.ALife.alife.model;

import com.google.gson.annotations.SerializedName;

public class image {
    @SerializedName("image")
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
