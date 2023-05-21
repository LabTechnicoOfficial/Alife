package com.ALife.alife.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagment {
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREE_NAME="session";
    String SESSION_KEY="-1";
    String SESSION_TYPE="session_type";
    String SESSION_PHONE="session_phone";
    public SessionManagment(Context  context)
    {
        sharedpreferences=context.getSharedPreferences(SHARED_PREE_NAME,Context.MODE_PRIVATE);
        editor=sharedpreferences.edit();
    }
    public void saveSession(User user)
    {
        int id=user.getId();
        String type=user.getType();
        String phone=user.getPhone();
        editor.putInt(SESSION_KEY,id).commit();
        editor.putString(SESSION_TYPE,type).commit();
        editor.putString(SESSION_PHONE,phone).commit();

    }
    public int getSession()
    {

        return sharedpreferences.getInt(SESSION_KEY,-1);
    }
    public String getType()
    {
        return sharedpreferences.getString(SESSION_TYPE,"");

    }
    public String getPhone()
    {
        return  sharedpreferences.getString(SESSION_PHONE,"");
    }
    public void removeSession(){
        editor.putInt(SESSION_KEY,-1).commit();
        editor.putString(SESSION_TYPE,"").commit();
    }

}
