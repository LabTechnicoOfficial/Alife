package com.alifew.alife.model;

import com.google.gson.annotations.SerializedName;

public class get_shop_all_due_details_response {
    @SerializedName("sell_id")
    public String sell_id;
    @SerializedName("customer_id")
    public String customer_id;
    @SerializedName("customer_name")
    public String customer_name;
    @SerializedName("customer_phone")
    public String customer_phone;
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

    public String sell_price;
    public String pay_amount;
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

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
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

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }
}
