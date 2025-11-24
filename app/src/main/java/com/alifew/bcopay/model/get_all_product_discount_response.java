package com.alifew.bcopay.model;

import com.google.gson.annotations.SerializedName;

public class get_all_product_discount_response{
    @SerializedName("discount")
    private String discount;

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}