package com.alifew.alife.EarningApp.View.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.alifew.alife.EarningApp.Model.Earning_Session_Management;
import com.alifew.alife.EarningApp.View.Fragment.Home_fragment;
import com.alifew.alife.R;
import com.alifew.alife.view.Customer.Customer_main_activity;
import com.alifew.alife.view.Shop.Shop_main_activity;


public class MainActivity extends AppCompatActivity {

    Earning_Session_Management earning_session_management;
    String userID, baseId, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE}, 1);

        super.onCreate(savedInstanceState);
        earning_session_management = new Earning_Session_Management(MainActivity.this);
        userID = earning_session_management.getSession();
        type = earning_session_management.getType();
        baseId = earning_session_management.getBaseid();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Home_fragment(userID)).commit();
        }
        setContentView(R.layout.earning_activity_main);
        try {
            this.getSupportActionBar().hide();
        } catch (Exception e) {

        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = .9f;
        applyOverrideConfiguration(override);
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            //Toast.makeText(this, "Going Back", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().popBackStack();
            //getFragmentManager().popBackStack();

        } else {
            if (type.equals("shop")) {
                startActivity(new Intent(MainActivity.this, Shop_main_activity.class));
            } else if (type.equals("customer")) {
                startActivity(new Intent(MainActivity.this, Customer_main_activity.class));
            }
        }

    }

    public static boolean isNetworkOnline(Context con) {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) con
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);

            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);

                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                    status = true;
                } else {
                    status = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return status;
    }
}