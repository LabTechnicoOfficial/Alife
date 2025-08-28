package com.alifew.alifeworld.view.OTP;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.model.OTP_response;
import com.alifew.alifeworld.model.customer_registration_response;
import com.alifew.alifeworld.model.token_update_response;
import com.alifew.alifeworld.model.update_shop_customer_record_response;
import com.alifew.alifeworld.view.Customer.Customer_main_activity;
import com.alifew.alifeworld.view.LoginActivity;
import com.alifew.alifeworld.view.Registration.Register_activity;
import com.alifew.alifeworld.view.Shop.Shop_main_activity;
import com.alifew.alifeworld.viewmodel.Customer_registration;
import com.alifew.alifeworld.viewmodel.Last_logintime;
import com.alifew.alifeworld.session.SessionManagement;
import com.alifew.alifeworld.viewmodel.OTP;
import com.alifew.alifeworld.viewmodel.SessionManagment_registration;
import com.alifew.alifeworld.viewmodel.Shop_registration;
import com.alifew.alifeworld.viewmodel.Token_update;
import com.alifew.alifeworld.viewmodel.User;

import java.util.Objects;
import java.util.Random;

public class Otp_validation_activity extends AppCompatActivity implements TextWatcher {

    EditText editText1, editText2, editText3, editText4, editText5;
    String otpcode;
    AppCompatButton verifyButton;
    String shopname, ownername, location, phone, registration_phone, password, image, type, otp, task_type;

    ImageView backButton;

    Shop_registration shop_registration;
    Customer_registration customer_registration;
    Token_update token_update;
    Last_logintime last_logintime;
    Dialog loader;

    String deviceToken;
    SessionManagement sessionManagement;

    TextView sendAgainButton, timeText;

    OTP otpViewModel;

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManagement = new SessionManagement(Otp_validation_activity.this);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        setContentView(R.layout.otp_validation_activity);

        SessionManagment_registration sessionManagment_registration = new SessionManagment_registration(Otp_validation_activity.this);

        shopname = sessionManagment_registration.getName();
        ownername = sessionManagment_registration.getOwner();
        location = sessionManagment_registration.getLocation();
        registration_phone = sessionManagment_registration.getPhone();
        phone = sessionManagment_registration.getPhone();
        password = sessionManagment_registration.getPassword();
        image = sessionManagment_registration.getImage();
        type = sessionManagment_registration.getType();
        otp = sessionManagment_registration.getOtp();
        Log.d("dataxx", "OTP: "+otp);
        task_type = sessionManagment_registration.getSESSION_TASK_TYPE();

        shop_registration = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Shop_registration.class);

        customer_registration = new ViewModelProvider(this).get(Customer_registration.class);

        token_update = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Token_update.class);
        last_logintime = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Last_logintime.class);

        editText1 = findViewById(R.id.editText1ID);
        editText2 = findViewById(R.id.editText2ID);
        editText3 = findViewById(R.id.editText3ID);
        editText4 = findViewById(R.id.editText4ID);
        editText5 = findViewById(R.id.editText5ID);

        backButton = findViewById(R.id.backButton);

        verifyButton = findViewById(R.id.verifyButtonID);

        editText1.addTextChangedListener(this);
        editText2.addTextChangedListener(this);
        editText3.addTextChangedListener(this);
        editText4.addTextChangedListener(this);
        editText5.addTextChangedListener(this);

        new OTP_Receiver().setEditText(editText1, editText2, editText3, editText4, editText5);
        loader = new Dialog(Otp_validation_activity.this);
        loader.setContentView(R.layout.loader);
        Objects.requireNonNull(loader.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        deviceToken = sessionManagement.getDeviceToken();

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = editText1.getText().toString();
                String s2 = editText2.getText().toString();
                String s3 = editText3.getText().toString();
                String s4 = editText4.getText().toString();
                String s5 = editText5.getText().toString();

                if (TextUtils.isEmpty(s1) || TextUtils.isEmpty(s2) || TextUtils.isEmpty(s3) || TextUtils.isEmpty(s4) || TextUtils.isEmpty(s5)) {
                    Toast.makeText(getBaseContext(), "Field empty", Toast.LENGTH_SHORT).show();
                } else {
                    otpcode = s1 + s2 + s3 + s4 + s5;

                    if (otp.equals(otpcode)) {
                        loader.show();
                        if (type.equals("shopkeeper")) {
                            if (image.equals("login_varification")) {

                                update_token("shop");
                            } else {
                                shop_registration();
                            }
                        } else if (type.equals("customer")) {
                            if (image.equals("login_varification")) {
                                update_token("customer");
                            } else {
                                customer_registration();
                            }
                        }

                    } else {
                        Toast.makeText(Otp_validation_activity.this, "Invalid OTP Code", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_function();
            }
        });

        timeText = findViewById(R.id.timerText);
        sendAgainButton = findViewById(R.id.sendAgainButton);
        otpViewModel = new ViewModelProvider(this).get(OTP.class);

        startCountDown();

        sendAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtpCode();
            }
        });

    }

    private void startCountDown() {

       // Toast.makeText(this, otp, Toast.LENGTH_SHORT).show();
        sendAgainButton.setVisibility(View.GONE);
        new CountDownTimer(60000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                timeText.setText("You can resend OTP after " + millisUntilFinished / 1000 + " seconds");
            }

            @SuppressLint("SetTextI18n")
            public void onFinish() {
                timeText.setText("You can resend OTP");
                sendAgainButton.setVisibility(View.VISIBLE);
                //startCountDown();
            }
        }.start();
    }


    private void back_function() {
        if (task_type.equals("registration")) {
            startActivity(new Intent(Otp_validation_activity.this, Register_activity.class));
        } else if (task_type.equals("login")) {
            startActivity(new Intent(Otp_validation_activity.this, LoginActivity.class));
        }
    }

    public String message = "no";

    private void update_token(String user_type) {

        if (user_type.equals("shop")) {
            message = "no";
            token_update.shop_token_update(password, deviceToken).observe(Otp_validation_activity.this, new Observer<token_update_response>() {
                @Override
                public void onChanged(token_update_response token_update_response) {
                    if (token_update_response.getMessage().equals("Update successfully")) {
                        message = token_update_response.getMessage();
                        User user = new User(password, type, phone);

                        sessionManagement.saveSession(user);
                        Intent intent = new Intent(Otp_validation_activity.this, Shop_main_activity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Otp_validation_activity.this, "Something error.Try again", Toast.LENGTH_SHORT);

                        sessionManagement.removeSession();
                        Intent intent = new Intent(Otp_validation_activity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            });


        } else if (user_type.equals("customer")) {
            message = "no";
            token_update.customer_token_update(password, deviceToken).observe(Otp_validation_activity.this, new Observer<token_update_response>() {
                @Override
                public void onChanged(token_update_response token_update_response) {
                    if (token_update_response.getMessage().equals("Update successfully")) {

                        User user = new User(password, type, phone);

                        sessionManagement.saveSession(user);
                        Intent intent = new Intent(Otp_validation_activity.this, Customer_main_activity.class);
                        startActivity(intent);


                    } else {

                        sessionManagement.removeSession();
                        Intent intent = new Intent(Otp_validation_activity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            });


        }
    }

    private void resendOtpCode() {
        Random r = new Random();
        int ran = r.nextInt(99999 - 10000 + 1) + 10000;
        String random_otp = String.valueOf(ran);
        otpViewModel.getStatus(phone, "Your " + type + " login OTP code is -" + random_otp + " " + ". Powered by ALIFE.").observe(Otp_validation_activity.this, new Observer<OTP_response>() {
            @Override
            public void onChanged(OTP_response otp_response) {

                if (otp_response.getStatus().equals("queued")) {
                    Toast.makeText(Otp_validation_activity.this, "OTP code successfully send to the phone.", Toast.LENGTH_SHORT).show();
                    otp = random_otp;
                    startCountDown();

                } else {
                    Toast.makeText(Otp_validation_activity.this, otp_response.getStatus(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s == editText1.getEditableText()) {
            if (editText1.length() == 1) {
                editText2.requestFocus();
            }
        } else if (s == editText2.getEditableText()) {
            if (editText2.length() == 1) {
                editText3.requestFocus();
            }
        } else if (s == editText3.getEditableText()) {
            if (editText3.length() == 1) {
                editText4.requestFocus();
            }
        } else if (s == editText4.getEditableText()) {
            if (editText4.length() == 1) {
                editText5.requestFocus();
            }
        }

        editText5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    editText5.setText("");
                    editText4.requestFocus();
                }
                return false;
            }
        });

        editText4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    editText4.setText("");
                    editText3.requestFocus();
                }
                return false;
            }
        });

        editText3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    editText3.setText("");
                    editText2.requestFocus();
                }
                return false;
            }
        });

        editText2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    editText2.setText("");
                    editText1.requestFocus();
                }
                return false;
            }
        });

    }

    public void shop_registration() {
        shop_registration.getmessage(shopname, ownername, location, registration_phone, password, image, deviceToken, sessionManagement.getLatitude(), sessionManagement.getLongitude()).observe(Otp_validation_activity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                loader.dismiss();
                if (s.equals("Registration complete successfully")) {
                    Toast.makeText(Otp_validation_activity.this, "Registration Completed", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Otp_validation_activity.this, LoginActivity.class));
                } else {
                    loader.dismiss();

                    Toast.makeText(Otp_validation_activity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void customer_registration() {
        customer_registration.getmessage(shopname, location, registration_phone, password, image, deviceToken).observe(Otp_validation_activity.this, new Observer<customer_registration_response>() {
            @Override
            public void onChanged(customer_registration_response s) {
                loader.dismiss();
                if (s.getMessage().equals("Success")) {

                    Toast.makeText(Otp_validation_activity.this, "Registration Completed", Toast.LENGTH_SHORT).show();
                    //update_content
                    customer_registration.getData(s.getCustomer_id(), phone).observe(Otp_validation_activity.this, new Observer<update_shop_customer_record_response>() {
                        @Override
                        public void onChanged(update_shop_customer_record_response update_shop_customer_record_response) {
                            startActivity(new Intent(Otp_validation_activity.this, LoginActivity.class));

                        }
                    });
                    //end update content
                    startActivity(new Intent(Otp_validation_activity.this, LoginActivity.class));
                } else {
                    Toast.makeText(Otp_validation_activity.this, "Fail.Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back_function();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = .9f;
        applyOverrideConfiguration(override);
    }
}