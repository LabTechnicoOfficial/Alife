package com.alifew.alifeworld.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.view.Customer.Customer_main_activity;
import com.alifew.alifeworld.view.Shop.Shop_main_activity;
import com.alifew.alifeworld.session.SessionManagement;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button shopkeeperClick, customerClick;
    String buttonClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        int userId = sessionManagement.getUserID();
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
            } catch (Exception ignored) {

            }


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

            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("key", buttonClick);
            startActivity(intent);

        } else if (v.getId() == R.id.customerID) {
            buttonClick = "customer";
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("key", buttonClick);
            startActivity(intent);
        }

    }
}