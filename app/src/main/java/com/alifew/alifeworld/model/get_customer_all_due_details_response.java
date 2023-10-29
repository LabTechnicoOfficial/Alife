package com.alifew.alifeworld.model;

import com.google.gson.annotations.SerializedName;

public class get_customer_all_due_details_response {
    @SerializedName("sell_id")
    public String sell_id;
    @SerializedName("shop_id")
    public String shop_id;
    @SerializedName("shop_name")
    public String shop_name;
    @SerializedName("sell_price")
    public String sell_price;
    @SerializedName("pay_amount")
    public String pay_amount;

    public String getSell_price() {
        return sell_price;
    }

    public void setSell_price(String sell_price) {
        this.sell_price = sell_price;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getDue_amount() {
        return due_amount;
    }

    public void setDue_amount(String due_amount) {
        this.due_amount = due_amount;
    }

    public String due_amount;
    public String date;
    public String total_due;
    public String sell_type;

    public String getSell_id() {
        return sell_id;
    }

    public void setSell_id(String sell_id) {
        this.sell_id = sell_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal_due() {
        return total_due;
    }

    public void setTotal_due(String total_due) {
        this.total_due = total_due;
    }

    public String getSell_type() {
        return sell_type;
    }

    public void setSell_type(String sell_type) {
        this.sell_type = sell_type;
    }
}
