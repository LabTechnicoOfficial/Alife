package com.alifew.alife.view;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alifew.alife.R;
import com.alifew.alife.view.Customer.Customer_main_activity;
import com.alifew.alife.view.Shop.Shop_main_activity;
import com.alifew.alife.session.SessionManagement;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button shopkeeperClick, customerClick;
    String buttonClick;
    ACProgressFlower dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        int userId = sessionManagement.getSession();
        String type = sessionManagement.getType();
        if (userId != -1) {
            if (type.equals("shopkeeper")) {
                startActivity(new Intent(MainActivity.this, Shop_main_activity.class));
            } else if (type.equals("customer")) {
                startActivity(new Intent(MainActivity.this, Customer_main_activity.class));
            }

        } else {


            setContentView(R.layout.activity_main);
            try {
                this.getSupportActionBar().hide();
            } catch (Exception e) {

            }

            dialog = new ACProgressFlower.Builder(this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .fadeColor(Color.DKGRAY)
                    .build();

            shopkeeperClick = (Button) findViewById(R.id.shopkeeperID);
            customerClick = (Button) findViewById(R.id.customerID);
            shopkeeperClick.setOnClickListener(this);
            customerClick.setOnClickListener(this);
        }


    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.shopkeeperID) {
            buttonClick = "shopkeeper";
            dialog.show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("key", buttonClick);
            startActivity(intent);

        } else if (v.getId() == R.id.customerID) {
            buttonClick = "customer";
            dialog.show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("key", buttonClick);
            startActivity(intent);
        }

    }
}