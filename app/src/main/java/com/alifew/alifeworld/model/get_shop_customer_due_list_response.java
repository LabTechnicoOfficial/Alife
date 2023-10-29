package com.alifew.alifeworld.model;

import com.google.gson.annotations.SerializedName;

public class get_shop_customer_due_list_response {
    @SerializedName("sell_id")
    String sell_id;
    @SerializedName("sell_price")
    String sell_price;
    @SerializedName("sell_type")
    String sell_type;
    @SerializedName("due")
    String due;
    @SerializedName("pay")
    String pay;
    @SerializedName("date")
    String date;
    @SerializedName("total_due")
    String total_due;

    public String getTotal_due() {
        return total_due;
    }

    public void setTotal_due(String total_due) {
        this.total_due = total_due;
    }

    public String getSell_id() {
        return sell_id;
    }

    public void setSell_id(String sell_id) {
        this.sell_id = sell_id;
    }

    public String getSell_price() {
        return sell_price;
    }

    public void setSell_price(String sell_price) {
        this.sell_price = sell_price;
    }

    public String getSell_type() {
        return sell_type;
    }

    public void setSell_type(String sell_type) {
        this.sell_type = sell_type;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
