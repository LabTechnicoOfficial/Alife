package com.ALife.alife.model.cupon;

import com.google.gson.annotations.SerializedName;

public class cuponShop_response {
    @SerializedName("shop_id")
    private String shop_id;
    @SerializedName("shop_name")
    private String shop_name;
    @SerializedName("shop_phone")
    private String shop_phone;
    @SerializedName("shop_image")
    private String shop_image;

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
}
