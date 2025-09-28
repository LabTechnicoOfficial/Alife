package com.alifew.bcopay.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer_response {
    @SerializedName("customer01r_id")
    @Expose
    public String customerID;
    @SerializedName("customer01r_name")
    @Expose
    public String customerName;
    @SerializedName("customer01r_address")
    @Expose
    public String customerAddress;
    @SerializedName("customer01r_phone")
    @Expose
    public String customerPhone;
    @SerializedName("customer01r_password")
    @Expose
    public String customerPassword;
    @SerializedName("numberOf_store")
    @Expose
    public String numberOfStore;
    @SerializedName("registration_datetime")
    @Expose
    public String registrationDatetime;
    @SerializedName("last_updatetime")
    @Expose
    public String lastUpdatetime;
    @SerializedName("customer01r_status")
    @Expose
    public String customerStatus;
    @SerializedName("customer01r_image")
    @Expose
    public String customerImage;
    @SerializedName("Token")
    @Expose
    public String token;
    @SerializedName("my_code")
    @Expose
    public String myCode;
    @SerializedName("referral_code")
    @Expose
    public String referralCode;

}
