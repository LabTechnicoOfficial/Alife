package com.alifew.alife.EarningApp.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Earning_Session_Management {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "SESSION_ID";
    String SESSION_BASEID = "SESSION_BASEID";
    String SESSION_Type = "SESSION_TYPE";

    public Earning_Session_Management(Context con) {
        sharedPreferences = con.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(String userID) {
        String id = userID;
        editor.putString(SESSION_KEY, id);
        editor.commit();
        Log.d(SESSION_KEY, id);
    }

    public void saveType(String type) {
        String s_type = type;
        editor.putString(SESSION_Type, s_type);
        editor.commit();
        Log.d(SESSION_Type, s_type);
    }
    public void saveBaseId(String baseId) {
        String s_baseId = baseId;
        editor.putString(SESSION_BASEID, s_baseId);
        editor.commit();
        Log.d(SESSION_BASEID, s_baseId);
    }

    public String getSession() {
        return sharedPreferences.getString(SESSION_KEY, "-1");
    }

    public String getType() {
        return sharedPreferences.getString(SESSION_Type, "-1");
    }
    public String getBaseid() {
        return sharedPreferences.getString(SESSION_BASEID, "-1");
    }
    public void removeSession() {
        editor.putString(SESSION_KEY, "-1").commit();
    }
}
