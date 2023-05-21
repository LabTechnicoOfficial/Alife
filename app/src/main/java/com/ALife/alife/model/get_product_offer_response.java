package com.ALife.alife.model;

import com.google.gson.annotations.SerializedName;

public class get_product_offer_response {
    @SerializedName("Id")
    private String Id;
    @SerializedName("amount")
    private String amount;
    @SerializedName("price")
    private String price;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
