package com.alifew.alifeworld.model;

import com.google.gson.annotations.SerializedName;

public class shop_local_page_item_list_response {
    @SerializedName("name")
    private String name;
    @SerializedName("amount")
    private String amount;
    @SerializedName("price")
    private String price;
    @SerializedName("title")
    private String title;
    @SerializedName("subtitle_id")
    private String subtitle_id;
    @SerializedName("total_price")
    private String total_price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle_id() {
        return subtitle_id;
    }

    public void setSubtitle_id(String subtitle_id) {
        this.subtitle_id = subtitle_id;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
}
