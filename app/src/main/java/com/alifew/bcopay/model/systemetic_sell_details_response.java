package com.alifew.bcopay.model;

import com.google.gson.annotations.SerializedName;

public class systemetic_sell_details_response {
    @SerializedName("product_name")
    private String product_name;
    @SerializedName("product_image")
    private String product_image;
    @SerializedName("type")
    private String type;
    @SerializedName("product_amount")
    private String product_amount;
    @SerializedName("price")
    private String price;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_id) {
        this.product_name = product_id;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_amount() {
        return product_amount;
    }

    public void setProduct_amount(String product_amount) {
        this.product_amount = product_amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
