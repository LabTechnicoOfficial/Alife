package com.alifew.alifeworld.model.slider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Customer_slider_response {

    @SerializedName("store01e_id")
    @Expose
    public String id;
    @SerializedName("store01e_name")
    @Expose
    public String storeName;
    @SerializedName("store01e_owner")
    @Expose
    public String storeOwnerName;
    @SerializedName("store01e_phone")
    @Expose
    public String phone;
    @SerializedName("store01e_password")
    @Expose
    public String store01ePassword;
    @SerializedName("store01e_location")
    @Expose
    public String location;
    @SerializedName("store01e_contact")
    @Expose
    public String contact;
    @SerializedName("registration_datetime")
    @Expose
    public String registrationDatetime;
    @SerializedName("last_updatetime")
    @Expose
    public String lastUpdatetime;
    @SerializedName("store01e_status")
    @Expose
    public String status;
    @SerializedName("store01e_image")
    @Expose
    public String image;
    @SerializedName("all_discount")
    @Expose
    public String allDiscount;
    @SerializedName("Token")
    @Expose
    public String token;
    @SerializedName("total_due_customer")
    @Expose
    public String totalDueCustomer;
    @SerializedName("total_product")
    @Expose
    public String totalProduct;
    @SerializedName("total_sell")
    @Expose
    public String totalSell;
    @SerializedName("latitude")
    @Expose
    public String latitude;
    @SerializedName("longitude")
    @Expose
    public String longitude;
    @SerializedName("banner")
    @Expose
    public String banner;
    @SerializedName("sliders")
    @Expose
    public List<Slider> sliders;

    public class Slider {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("banner_link")
        @Expose
        public String bannerLink;
        @SerializedName("shop_id")
        @Expose
        public String shopId;
        @SerializedName("status")
        @Expose
        public String status;

    }
}


