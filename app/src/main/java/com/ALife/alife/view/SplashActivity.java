package com.ALife.alife.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ALife.alife.R;
import com.ALife.alife.session.SessionManagement;
import com.ALife.alife.view.Customer.Customer_main_activity;
import com.ALife.alife.view.Operator.Operator_main_activity;
import com.ALife.alife.view.Shop.Shop_main_activity;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SessionManagement sessionManagement = new SessionManagement(SplashActivity.this);
        int userId = sessionManagement.getSession();
        String type = sessionManagement.getType();
        String phone = sessionManagement.getPhone();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userId != -1) {
                    if (type.equals("shopkeeper")) {
                        startActivity(new Intent(SplashActivity.this, Shop_main_activity.class));
                    } else if (type.equals("customer")) {
                        startActivity(new Intent(SplashActivity.this, Customer_main_activity.class));
                    } else if (type.equals("admin")) {
                        startActivity(new Intent(SplashActivity.this, Operator_main_activity.class));
                    }

                }else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            }
        }, SPLASH_DISPLAY_LENGTH);


    }
}