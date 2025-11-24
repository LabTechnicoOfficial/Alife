package com.alifew.bcopay.model;

import com.google.gson.annotations.SerializedName;

public class Get_shop_customer_response {
    @SerializedName("customer01r_id")
   private String customer01r_id;
    @SerializedName("customer01r_name")
    private String customer01r_name;
    @SerializedName("customer01r_address")
    private String customer01r_address;
    @SerializedName("customer01r_phone")
    private String customer01r_phone;
    @SerializedName("customer01r_image")
    private String customer01r_image;
    @SerializedName("total_due")
    private String total_due;

    public String getCustomer01r_id() {
        return customer01r_id;
    }

    public void setCustomer01r_id(String customer01r_id) {
        this.customer01r_id = customer01r_id;
    }

    public String getCustomer01r_name() {
        return customer01r_name;
    }

    public void setCustomer01r_name(String customer01r_name) {
        this.customer01r_name = customer01r_name;
    }

    public String getCustomer01r_address() {
        return customer01r_address;
    }

    public void setCustomer01r_address(String customer01r_address) {
        this.customer01r_address = customer01r_address;
    }

    public String getCustomer01r_phone() {
        return customer01r_phone;
    }

    public void setCustomer01r_phone(String customer01r_phone) {
        this.customer01r_phone = customer01r_phone;
    }

    public String getCustomer01r_image() {
        return customer01r_image;
    }

    public void setCustomer01r_image(String customer01r_image) {
        this.customer01r_image = customer01r_image;
    }

    public String getTotal_due() {
        return total_due;
    }

    public void setTotal_due(String total_due) {
        this.total_due = total_due;
    }
}
