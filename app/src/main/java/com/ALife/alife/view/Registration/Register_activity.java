package com.ALife.alife.view.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ALife.alife.R;
import com.ALife.alife.view.LoginActivity;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class Register_activity extends AppCompatActivity implements View.OnClickListener {
    ImageView backButton;
    Button registerButton;

    MaterialButtonToggleGroup toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.registration_frame_container, new Shop_registration_fragment()).commit();
        }
        setContentView(R.layout.register_activity);
        try {
            this.getSupportActionBar().hide();
        } catch (Exception e) {
        }

        backButton = (ImageView) findViewById(R.id.backButtonID);

        registerButton = (Button) findViewById(R.id.registerButtonID);
        toggleButton = findViewById(R.id.toggleGroup);

        backButton.setOnClickListener(this);

        //toggle Action
        toggleButton.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {

                if (group.getCheckedButtonId() == R.id.shopkeeperID) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.registration_frame_container, new Shop_registration_fragment()).commit();

                } else if (group.getCheckedButtonId() == R.id.customerID) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.registration_frame_container, new Customer_registration_fragment()).commit();

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backButtonID) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = .9f;
        applyOverrideConfiguration(override);
    }
}