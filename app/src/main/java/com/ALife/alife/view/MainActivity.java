package com.ALife.alife.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ALife.alife.R;
import com.ALife.alife.view.Customer.Customer_main_activity;
import com.ALife.alife.view.Shop.Shop_main_activity;
import com.ALife.alife.viewmodel.SessionManagment;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button shopkeeperClick, customerClick;
    String buttonClick;
    ACProgressFlower dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionManagment sessionManagment = new SessionManagment(MainActivity.this);
        int userId = sessionManagment.getSession();
        String type = sessionManagment.getType();
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