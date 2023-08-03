package com.alifew.alife.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.alifew.alife.viewmodel.User;

public class SessionManagement {
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "-1";
    String SESSION_TYPE = "session_type";
    String SESSION_PHONE = "session_phone";

    String SESSION_TOKEN = "alife_token";


    String SESSION_SHOP_NAME = "session_shop_name";

    public SessionManagement(Context context) {
        sharedpreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public void saveSession(User user) {
        int id = user.getId();
        String type = user.getType();
        String phone = user.getPhone();
        editor.putInt(SESSION_KEY, id).commit();
        editor.putString(SESSION_TYPE, type).commit();
        editor.putString(SESSION_PHONE, phone).commit();

    }

    public int getSession() {

        return sharedpreferences.getInt(SESSION_KEY, -1);
    }

    public String getType() {
        return sharedpreferences.getString(SESSION_TYPE, "");

    }

    public String getPhone() {
        return sharedpreferences.getString(SESSION_PHONE, "");
    }

    public void removeSession() {
        editor.putInt(SESSION_KEY, -1).commit();
        editor.putString(SESSION_TYPE, "").commit();
    }

    public void saveShopName(String userName) {
       editor.putString(SESSION_SHOP_NAME, userName).commit();
    }

    public String getSaveShopName(){
        return sharedpreferences.getString(SESSION_SHOP_NAME, "");
    }

    public void saveDeviceToken(String token){
        editor.putString(SESSION_TOKEN, token).commit();
    }

    public String getDeviceToken(){
        return sharedpreferences.getString(SESSION_TOKEN, "");
    }
}
