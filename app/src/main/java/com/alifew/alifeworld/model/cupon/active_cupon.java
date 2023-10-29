package com.alifew.alifeworld.model.cupon;

import com.google.gson.annotations.SerializedName;

public class active_cupon {
    @SerializedName("position")
    private int position;

    @SerializedName("shop_id")
    private String shop_id;

    @SerializedName("cupon_id")
    private String cupon_id;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getCupon_id() {
        return cupon_id;
    }

    public void setCupon_id(String cupon_id) {
        this.cupon_id = cupon_id;
    }
}
