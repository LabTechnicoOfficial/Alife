package com.alifew.alife.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


import com.alifew.alife.R;

import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.view.Customer.Customer_main_activity;
import com.alifew.alife.view.Operator.Operator_main_activity;
import com.alifew.alife.view.Shop.Shop_main_activity;

import java.util.LinkedHashMap;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1500;
    SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();



        checkSession();


    }

    private void initView() {
        sessionManagement = new SessionManagement(SplashActivity.this);
    }




    private void checkSession() {

        int userId = sessionManagement.getSession();
        String type = sessionManagement.getType();
        String phone = sessionManagement.getPhone();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userId != -1) {
                    switch (type) {
                        case "shopkeeper":
                            startActivity(new Intent(SplashActivity.this, Shop_main_activity.class));
                            break;
                        case "customer":
                            startActivity(new Intent(SplashActivity.this, Customer_main_activity.class));
                            break;
                        case "admin":
                            startActivity(new Intent(SplashActivity.this, Operator_main_activity.class));
                            break;
                    }


                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}