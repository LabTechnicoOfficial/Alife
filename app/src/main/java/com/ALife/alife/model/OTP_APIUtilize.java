package com.ALife.alife.model;

public class OTP_APIUtilize {
    public OTP_APIUtilize() {
    }

    public static final String OTP_URL = "http://mimsms.com.bd/";

    public static OTP_api otprespose(){
        return Retrofit_client.getClient(OTP_URL).create(OTP_api.class);
    }
}
