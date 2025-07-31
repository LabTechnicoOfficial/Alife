package com.alifew.alifeworld.model.cupon;

import com.google.gson.annotations.SerializedName;

public class CustomerFor_cupon_response {
    @SerializedName("customer_id")
    public String customer_id;
    @SerializedName("customer_phone")
    public String customer_phone;
    @SerializedName("sell_amount")
    public String sell_amount;
    @SerializedName("customer_name")
    public String customerName;
    @SerializedName("points")
    public String points;

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getSell_amount() {
        return sell_amount;
    }

    public void setSell_amount(String sell_amount) {
        this.sell_amount = sell_amount;
    }


}
