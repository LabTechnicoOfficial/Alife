package com.ALife.alife.Custom_Type;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product_offer {
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("percentage")
    @Expose
    private String percentage;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("id")
    @Expose
    private int id;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String price) {
        this.percentage = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
