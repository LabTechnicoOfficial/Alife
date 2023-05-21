package com.ALife.alife.model;

import com.google.gson.annotations.SerializedName;

public class get_product_response {
    @SerializedName("product_id")
    private String product_id;
    @SerializedName("product_name")
    private String product_name;
    @SerializedName("product_unit")
    private String product_unit;
    @SerializedName("selling_price")
    private String selling_price;
    @SerializedName("product_offer")
    private String product_offer;
    @SerializedName("buy_price")
    private String buy_price;
    @SerializedName("sell_profit")
    private String sell_profit;
    @SerializedName("stock_amount")
    private String stock_amount;
    @SerializedName("product_image")
    String product_image;
    @SerializedName("product_added_date")
    private String product_added_date;
    @SerializedName("product_description")
    private String product_description;
    @SerializedName("vaoture_no")
    private String vaoture_no;
    @SerializedName("vaoture_image")
    private String vaoture_image;
    @SerializedName("brand")
    private String brand;
    @SerializedName("code")
    private String code;
    @SerializedName("status")
    private String status;
    @SerializedName("added_by")
    private String added_by;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }


    public String getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(String buy_price) {
        this.buy_price = buy_price;
    }

    public String getSell_profit() {
        return sell_profit;
    }

    public void setSell_profit(String sell_profit) {
        this.sell_profit = sell_profit;
    }

    public String getStock_amount() {
        return stock_amount;
    }

    public void setStock_amount(String stock_amount) {
        this.stock_amount = stock_amount;
    }


    public String getProduct_added_date() {
        return product_added_date;
    }

    public void setProduct_added_date(String product_added_date) {
        this.product_added_date = product_added_date;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_unit() {
        return product_unit;
    }

    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }


    public String getProduct_offer() {
        return product_offer;
    }

    public void setProduct_offer(String product_offer) {
        this.product_offer = product_offer;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getVaoture_no() {
        return vaoture_no;
    }

    public void setVaoture_no(String vaoture_no) {
        this.vaoture_no = vaoture_no;
    }

    public String getVaoture_image() {
        return vaoture_image;
    }

    public void setVaoture_image(String vaoture_image) {
        this.vaoture_image = vaoture_image;
    }

    public String getAdded_by() {
        return added_by;
    }

    public void setAdded_by(String added_by) {
        this.added_by = added_by;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
