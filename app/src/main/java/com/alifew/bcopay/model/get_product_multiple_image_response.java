package com.alifew.bcopay.model;

import com.google.gson.annotations.SerializedName;

public class get_product_multiple_image_response {
    @SerializedName("image")
    private String image;
    @SerializedName("id")
    private String id;

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
