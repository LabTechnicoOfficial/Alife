package com.alifew.alife.model;

import com.google.gson.annotations.SerializedName;

public class customer_shopList_response {
    @SerializedName("store01e_id")
    private String store01e_id;
    @SerializedName("store01e_name")
    private String store01e_name;
    @SerializedName("store01e_phone")
    private String store01e_phone;
    @SerializedName("store01e_location")
    private String store01e_location;
    @SerializedName("store01e_image")
    private String store01e_image;
    @SerializedName("total_due")
    private String total_due;

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

    public String getStore01e_phone() {
        return store01e_phone;
    }

    public void setStore01e_phone(String store01e_phone) {
        this.store01e_phone = store01e_phone;
    }

    public String getStore01e_location() {
        return store01e_location;
    }

    public void setStore01e_location(String store01e_location) {
        this.store01e_location = store01e_location;
    }

    public String getTotal_due() {
        return total_due;
    }

    public void setTotal_due(String total_due) {
        this.total_due = total_due;
    }
}
