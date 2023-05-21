package com.ALife.alife.model;

import com.google.gson.annotations.SerializedName;

public class Shop_response {
    @SerializedName("name")
    private String name;
    @SerializedName("owner")
    private String owner;
    @SerializedName("location")
    private String location;
    @SerializedName("contact")
    private String contact;
    @SerializedName("customer")
    private String customer;
    @SerializedName("image")
    private String image;

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public String getPhone() {
        return contact;
    }

    public String getLocation() {
        return location;
    }

    public String getCustomer() {
        return customer;
    }

    public String getImage() {
        return image;
    }
}
