package com.alifew.bcopay.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import com.alifew.bcopay.model.RegistrationResponse;

public class SessionManagment_registration {
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREE_NAME = "session";
    String SESSION_NAME = "session_user";
    String SESSION_TYPE = "session_type";
    String SESSION_PHONE = "session_phone";
    String SESSION_OWNER = "session_owner";
    String SESSION_PASSWORD = "session_password";
    String SESSION_LOCATION = "session_location";
    String SESSION_OTP = "session_otp";
    String SESSION_IMAGE = "session_image";
    String SESSION_TASK_TYPE = "session-task_type";

    public SessionManagment_registration(Context context) {
        sharedpreferences = context.getSharedPreferences(SHARED_PREE_NAME, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public void saveSession(RegistrationResponse r) {

        editor.putString(SESSION_NAME, r.getName()).commit();
        editor.putString(SESSION_TYPE, r.getType()).commit();
        editor.putString(SESSION_PHONE, r.getPhone()).commit();
        editor.putString(SESSION_OWNER, r.getOwner()).commit();
        editor.putString(SESSION_PASSWORD, r.getPassword()).commit();
        editor.putString(SESSION_LOCATION, r.getLocation()).commit();
        editor.putString(SESSION_OTP, r.getOtp()).commit();
        editor.putString(SESSION_IMAGE, r.getImage()).commit();
        editor.putString(SESSION_TASK_TYPE, r.getTask_type()).commit();

    }

    public String getSESSION_TASK_TYPE() {
        return sharedpreferences.getString(SESSION_TASK_TYPE, "");
    }

    public String getName() {
        return sharedpreferences.getString(SESSION_NAME, "");
    }

    public String getPhone() {
        return sharedpreferences.getString(SESSION_PHONE, "");
    }

    public String getPassword() {
        return sharedpreferences.getString(SESSION_PASSWORD, "");
    }

    public String getType() {
        return sharedpreferences.getString(SESSION_TYPE, "");
    }

    public String getImage() {
        return sharedpreferences.getString(SESSION_IMAGE, "");
    }

    public String getLocation() {
        return sharedpreferences.getString(SESSION_LOCATION, "");
    }

    public String getOtp() {
        return sharedpreferences.getString(SESSION_OTP, "");
    }

    public String getOwner() {
        return sharedpreferences.getString(SESSION_OWNER, "");
    }

    public void removeSession() {

        editor.putString(SESSION_NAME, "-1").commit();
        editor.putString(SESSION_TYPE, "-1").commit();
        editor.putString(SESSION_PHONE, "-1").commit();
        editor.putString(SESSION_OWNER, "-1").commit();
        editor.putString(SESSION_PASSWORD, "-1").commit();
        editor.putString(SESSION_LOCATION, "-1").commit();
        editor.putString(SESSION_OTP, "-1").commit();
        editor.putString(SESSION_IMAGE, "-1").commit();
        editor.putString(SESSION_TASK_TYPE, "-1").commit();
    }


}
