package com.ALife.alife.model;

import com.google.gson.annotations.SerializedName;

public class  get_shop_products_summary_response {
    @SerializedName("all_sell_price")
    private Double all_sell_price;
    @SerializedName("all_buy_price")
    private Double all_buy_price;
    @SerializedName("all_profit")
    private Double all_profit;
    @SerializedName("all_product")
    private Double all_product;
    @SerializedName("all_stock")
    private Double all_stock;
    @SerializedName("all_category")
    private String total_category;

    public Double getAll_sell_price() {
        return all_sell_price;
    }

    public void setAll_sell_price(Double all_sell_price) {
        this.all_sell_price = all_sell_price;
    }

    public Double getAll_buy_price() {
        return all_buy_price;
    }

    public void setAll_buy_price(Double all_buy_price) {
        this.all_buy_price = all_buy_price;
    }

    public Double getAll_profit() {
        return all_profit;
    }

    public void setAll_profit(Double all_profit) {
        this.all_profit = all_profit;
    }

    public Double getAll_product() {
        return all_product;
    }

    public void setAll_product(Double all_product) {
        this.all_product = all_product;
    }

    public Double getAll_stock() {
        return all_stock;
    }

    public void setAll_stock(Double all_stock) {
        this.all_stock = all_stock;
    }

    public String getTotal_category() {
        return total_category;
    }

    public void setTotal_category(String total_category) {
        this.total_category = total_category;
    }
}

