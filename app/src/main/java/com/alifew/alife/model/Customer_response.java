package com.alifew.alife.model;

import com.google.gson.annotations.SerializedName;

public class Customer_response {
    @SerializedName("name")
    private String name;
    @SerializedName("phone")
    private String phone;
    @SerializedName("location")
    private String location;
    @SerializedName("shop")
    private String shop;

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }

    public String getShop() {
        return shop;
    }

    public String getImage() {
        return image;
    }

    private String image;

}
