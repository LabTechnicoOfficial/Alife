package com.alifew.alifeworld.model;

import com.google.gson.annotations.SerializedName;

public class customer_due_shop_list_response {
    @SerializedName("shop_id")
    public String shop_id;
    @SerializedName("shop_name")
    public String shop_name;
    @SerializedName("shop_address")
    public String shop_address;
    @SerializedName("shop_phone")
    public String shop_phone;
    @SerializedName("shop_image")
    public String shop_image;
    @SerializedName("total_due")
    public String total_due;

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public String getShop_phone() {
        return shop_phone;
    }

    public void setShop_phone(String shop_phone) {
        this.shop_phone = shop_phone;
    }

    public String getShop_image() {
        return shop_image;
    }

    public void setShop_image(String shop_image) {
        this.shop_image = shop_image;
    }

    public String getTotal_due() {
        return total_due;
    }

    public void setTotal_due(String total_due) {
        this.total_due = total_due;
    }
}
