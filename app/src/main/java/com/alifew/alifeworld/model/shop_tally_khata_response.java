package com.alifew.alifeworld.model;

import com.google.gson.annotations.SerializedName;

public class shop_tally_khata_response {
    @SerializedName("sell_id")
    public String sell_id;
    @SerializedName("customer_id")
    public String customer_id;
    @SerializedName("customer_name")
    public String customer_name;
    @SerializedName("customer_phone")
    public String customer_phone;
    @SerializedName("sell_price")
    public String sell_price;
    @SerializedName("sell_type")
    public String sell_type;
    @SerializedName("transaction_type")
    public String transaction_type;
    @SerializedName("due")
    public String due;
    @SerializedName("cash")
    public String cash;
    @SerializedName("due_pay")
    public String due_pay;
    @SerializedName("non_registered_pay")
    public String non_registered_pay;
    @SerializedName("payment_system")
    public String payment_system;
    @SerializedName("date")
    public String date;

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

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getSell_type() {
        return sell_type;
    }

    public void setSell_type(String sell_type) {
        this.sell_type = sell_type;
    }

    public String getSell_price() {
        return sell_price;
    }

    public void setSell_price(String sell_price) {
        this.sell_price = sell_price;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getDue_pay() {
        return due_pay;
    }

    public void setDue_pay(String due_pay) {
        this.due_pay = due_pay;
    }

    public String getNon_registered_pay() {
        return non_registered_pay;
    }

    public void setNon_registered_pay(String non_registered_pay) {
        this.non_registered_pay = non_registered_pay;
    }

    public String getPayment_system() {
        return payment_system;
    }

    public void setPayment_system(String payment_system) {
        this.payment_system = payment_system;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
