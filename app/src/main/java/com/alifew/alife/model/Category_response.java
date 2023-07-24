package com.alifew.alife.model;

import com.google.gson.annotations.SerializedName;

public class Category_response {
    @SerializedName("catagory01y_id")
    private String catagory01y_id;
    @SerializedName("catagory01y_logo")
    private  String catagory01y_logo;
    @SerializedName("catagory01y_name")
    private String catagory01y_name;
    @SerializedName("catagory01y_unit")
    private String catagory01y_unit;
    @SerializedName("total_product")
    private String total_product;
    @SerializedName("total_profit")
    private String total_profit;
    @SerializedName("total_sell_price")
    private String total_sell_price;
    @SerializedName("total_stock_product")
    private String total_stock_product;


    public String getCatagory01y_unit() {
        return catagory01y_unit;
    }

    public void setCatagory01y_unit(String catagory01y_unit) {
        this.catagory01y_unit = catagory01y_unit;
    }

    public String getCatagory01y_logo() {
        return catagory01y_logo;
    }

    public void setCatagory01y_logo(String catagory01y_logo) {
        this.catagory01y_logo = catagory01y_logo;
    }

    public String getCatagory01y_id() {
        return catagory01y_id;
    }

    public String getCatagory01y_name() {
        return catagory01y_name;
    }

    public void setCatagory01y_id(String catagory01y_id) {
        this.catagory01y_id = catagory01y_id;
    }

    public void setCatagory01y_name(String catagory01y_name) {
        this.catagory01y_name = catagory01y_name;
    }

    public String getTotal_product() {
        return total_product;
    }

    public void setTotal_product(String total_product) {
        this.total_product = total_product;
    }

    public String getTotal_profit() {
        return total_profit;
    }

    public void setTotal_profit(String total_profit) {
        this.total_profit = total_profit;
    }

    public String getTotal_sell_price() {
        return total_sell_price;
    }

    public void setTotal_sell_price(String total_sell_price) {
        this.total_sell_price = total_sell_price;
    }

    public String getTotal_stock_product() {
        return total_stock_product;
    }

    public void setTotal_stock_product(String total_stock_product) {
        this.total_stock_product = total_stock_product;
    }
}
