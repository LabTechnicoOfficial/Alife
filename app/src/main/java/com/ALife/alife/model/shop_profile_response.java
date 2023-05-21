package com.ALife.alife.model;

import com.google.gson.annotations.SerializedName;

public class shop_profile_response {
    @SerializedName("store01e_id")
    private String store01e_id;
    @SerializedName("store01e_name")
    private String store01e_name;
    @SerializedName("store01e_owner")
    private String store01e_owner;
    @SerializedName("store01e_phone")
    private String store01e_phone;
    @SerializedName("store01e_location")
    private String store01e_location;
    @SerializedName("store01e_contact")
    private String store01e_contact;
    @SerializedName("store01e_image")
    private String store01e_image;
    @SerializedName("all_discount")
    private String all_discount;

    public String getStore01e_id() {
        return store01e_id;
    }

    public void setStore01e_id(String store01e_id) {
        this.store01e_id = store01e_id;
    }

    public String getStore01e_name() {
        return store01e_name;
    }

    public void setStore01e_name(String store01e_name) {
        this.store01e_name = store01e_name;
    }

    public String getStore01e_owner() {
        return store01e_owner;
    }

    public void setStore01e_owner(String store01e_owner) {
        this.store01e_owner = store01e_owner;
    }

    public String getStore01e_phone() {
        return store01e_phone;
    }

    public void setStore01e_phone(String store01e_phone) {
        this.store01e_phone = store01e_phone;
    }

    public String getStore01e_location() {
        return store01e_location;
    }

    public void setStore01e_location(String store01e_location) {
        this.store01e_location = store01e_location;
    }

    public String getStore01e_contact() {
        return store01e_contact;
    }

    public void setStore01e_contact(String store01e_contact) {
        this.store01e_contact = store01e_contact;
    }

    public String getStore01e_image() {
        return store01e_image;
    }

    public void setStore01e_image(String store01e_image) {
        this.store01e_image = store01e_image;
    }

    public String getAll_discount() {
        return all_discount;
    }

    public void setAll_discount(String all_discount) {
        this.all_discount = all_discount;
    }
}
