package com.alifew.alife.model;

import com.google.gson.annotations.SerializedName;

public class get_daily_sell_cash_response {
    @SerializedName("customer_id")
    private String customer_id;
    @SerializedName("customer_name")
    private String customer_name;
    @SerializedName("customer_phone")
    private String customer_phone;
    @SerializedName("payment_system")
    private String payment_system;
    @SerializedName("payment_amount")
    private String payment_amount;

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

    public String getPayment_system() {
        return payment_system;
    }

    public void setPayment_system(String payment_system) {
        this.payment_system = payment_system;
    }

    public String getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(String payment_amount) {
        this.payment_amount = payment_amount;
    }
}
