package com.alifew.bcopay.model.local_sell;

import com.google.gson.annotations.SerializedName;

public class Get_local_sell_product_response {
    @SerializedName("id")
    String id;
    @SerializedName("product_details")
    String product_details;
    @SerializedName("price")
    String price;
    @SerializedName("buy_price")
    private String buy_price;
    @SerializedName("image")
    String image;
    @SerializedName("shop_id")
    String shop_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_details() {
        return product_details;
    }

    public void setProduct_details(String product_details) {
        this.product_details = product_details;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(String buy_price) {
        this.buy_price = buy_price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

}
