package com.alifew.alifeworld.model;

import com.google.gson.annotations.SerializedName;

public class fetch_shop_response {
    @SerializedName("store01e_id")
    private String store01e_id;
    @SerializedName("store01e_name")
    private String store01e_name;
    @SerializedName("store01e_image")
    private String store01e_image;

    public String getStore01e_id() {
        return store01e_id;
    }

    public void setStore01e_id(String store01e_id) {
        this.store01e_id = store01e_id;
    }

    public String getStore01e_name() {
        return store01e_name;
    }

    public void setStore01e_name(String store01e_name) {
        this.store01e_name = store01e_name;
    }

    public String getStore01e_image() {
        return store01e_image;
    }

    public void setStore01e_image(String store01e_image) {
        this.store01e_image = store01e_image;
    }
}
