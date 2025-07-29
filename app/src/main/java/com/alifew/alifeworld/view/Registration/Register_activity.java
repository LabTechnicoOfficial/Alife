package com.alifew.alifeworld.view.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alifew.alifeworld.R;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class Register_activity extends AppCompatActivity implements View.OnClickListener {
    ImageView backButton;
    MaterialButtonToggleGroup toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.registration_frame_container, new Customer_registration_fragment()).commit();
        }
        setContentView(R.layout.register_activity);

        backButton = findViewById(R.id.backButton);

        toggleButton = findViewById(R.id.toggleGroup);

        backButton.setOnClickListener(this);

        //toggle Action
        toggleButton.addOnButtonCheckedListener((group, checkedId, isChecked) -> {

            if (group.getCheckedButtonId() == R.id.shopkeeperID) {
                getSupportFragmentManager().beginTransaction().replace(R.id.registration_frame_container, new Shop_registration_fragment()).commit();

            } else if (group.getCheckedButtonId() == R.id.customerID) {
                getSupportFragmentManager().beginTransaction().replace(R.id.registration_frame_container, new Customer_registration_fragment()).commit();

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backButton) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = .9f;
        applyOverrideConfiguration(override);
    }
}