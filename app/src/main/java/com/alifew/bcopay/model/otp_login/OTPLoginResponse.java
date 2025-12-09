package com.alifew.bcopay.model.otp_login;

import com.google.gson.annotations.SerializedName;

public class OTPLoginResponse {
    @SerializedName("customer01r_id")
    public String customer0Id;

    @SerializedName("customer01r_name")
    public String customerName;

    @SerializedName("customer01r_address")
    public String customerAddress;

    @SerializedName("customer01r_phone")
    public String customerPhone;

    @SerializedName("numberOf_store")
    public String numberOfStore;

    @SerializedName("registration_datetime")
    public String registrationDatetime;

    @SerializedName("last_updatetime")
    public String lastUpdatetime;

    @SerializedName("customer01r_status")
    public String customer01rStatus;

    @SerializedName("customer01r_image")
    public String customer01rImage;

    @SerializedName("Token")
    public String token;

    @SerializedName("my_code")
    public String myCode;

    @SerializedName("referral_code")
    public String referralCode;

    @SerializedName("balance_point")
    public String balancePoint;
}
