package com.alifew.alife.view.OTP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alifew.alife.R;
import com.alifew.alife.Utils.Constants;
import com.alifew.alife.model.customer_registration_response;
import com.alifew.alife.model.token_update_response;
import com.alifew.alife.model.update_shop_customer_record_response;
import com.alifew.alife.view.Customer.Customer_main_activity;
import com.alifew.alife.view.LoginActivity;
import com.alifew.alife.view.Operator.Operator_main_activity;
import com.alifew.alife.view.Registration.Register_activity;
import com.alifew.alife.view.Shop.Shop_main_activity;
import com.alifew.alife.viewmodel.Customer_registration;
import com.alifew.alife.viewmodel.Last_logintime;
import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.viewmodel.SessionManagment_registration;
import com.alifew.alife.viewmodel.Shop_registration;
import com.alifew.alife.viewmodel.Token_update;
import com.alifew.alife.viewmodel.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.onesignal.OneSignal;

public class Otp_validation_activity extends AppCompatActivity implements TextWatcher {
    String token = "x";
    FirebaseAuth mAuth;

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

    @Override
    protected void onStart() {
        super.onStart();
        SessionManagement sessionManagement = new SessionManagement(Otp_validation_activity.this);
        int userId = sessionManagement.getSession();
        String Type = sessionManagement.getType();
        phone = sessionManagement.getPhone();
        if (userId != -1) {
            if (Type.equals("shopkeeper")) {
                Intent intent = new Intent(Otp_validation_activity.this, Shop_main_activity.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            } else if (Type.equals("customer")) {
                Intent intent = new Intent(Otp_validation_activity.this, Customer_main_activity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            } else if (Type.equals("admin")) {
                Intent intent = new Intent(Otp_validation_activity.this, Operator_main_activity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionManagement sessionManagement = new SessionManagement(Otp_validation_activity.this);
        int userId = sessionManagement.getSession();
        String Type = sessionManagement.getType();
        // String phone = sessionManagment.getPhone();
        if (userId != -1) {
            if (Type.equals("shopkeeper")) {
                Intent intent = new Intent(Otp_validation_activity.this, Shop_main_activity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            } else if (Type.equals("customer")) {
                Intent intent = new Intent(Otp_validation_activity.this, Customer_main_activity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            } else if (Type.equals("admin")) {
                Intent intent = new Intent(Otp_validation_activity.this, Operator_main_activity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }

        } else {
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
            task_type = sessionManagment_registration.getSESSION_TASK_TYPE();

         //   sessionManagment_registration.removeSession();
            try {
                this.getSupportActionBar().hide();
            } catch (Exception e) {

            }

            shop_registration = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Shop_registration.class);

            customer_registration = new ViewModelProvider(this).get(Customer_registration.class);

            token_update = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Token_update.class);
            last_logintime = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Last_logintime.class);

            editText1 = (EditText) findViewById(R.id.editText1ID);
            editText2 = (EditText) findViewById(R.id.editText2ID);
            editText3 = (EditText) findViewById(R.id.editText3ID);
            editText4 = (EditText) findViewById(R.id.editText4ID);
            editText5 = (EditText) findViewById(R.id.editText5ID);

            backButton = (ImageView) findViewById(R.id.backButtonID);

            verifyButton = (AppCompatButton) findViewById(R.id.verifyButtonID);

            editText1.addTextChangedListener(this);
            editText2.addTextChangedListener(this);
            editText3.addTextChangedListener(this);
            editText4.addTextChangedListener(this);
            editText5.addTextChangedListener(this);
            //requestsmspermission();

            new OTP_Receiver().setEditText(editText1, editText2, editText3, editText4, editText5);
            loader = new Dialog(Otp_validation_activity.this);
            loader.setContentView(R.layout.loader);
            loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            loader.setCancelable(false);

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
        }
    }

    public void requestsmspermission() {
        //ActivityCompat.requestPermissions(, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.CALL_PHONE,Manifest.permission.ACCESS_WIFI_STATE},1);

        /*String smspermission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(this,smspermission);
        //check if read SMS permission is granted or not
        if(grant!= PackageManager.PERMISSION_GRANTED)
        {
            String[] permission_list = new String[1];
            permission_list[0]=smspermission;
            ActivityCompat.requestPermissions(this,permission_list,1);
        }*/
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

        OneSignal.initWithContext(getApplicationContext());
        OneSignal.setAppId(Constants.ONESIGNAL_APP_ID);
        String deviceToken = OneSignal.getDeviceState().getUserId();
        Log.d("dataxx", "checkMultipleDeviceLogIN: "+password+" "+deviceToken);

        if (user_type.equals("shop")) {
            message = "no";
            token_update.shop_token_update(password, deviceToken).observe(Otp_validation_activity.this, new Observer<token_update_response>() {
                @Override
                public void onChanged(token_update_response token_update_response) {
                    if (token_update_response.getMessage().equals("Update successfully")) {
                        message = token_update_response.getMessage();

  /*                      SimpleDateFormat objSDF = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

                        String currentTime = (String) android.text.format.DateFormat.format("yy/MM/dd HH:mm:ss", new java.util.Date());



                                            last_logintime.getUpdate(password, currentTime, "shop").observe(Otp_validation_activity.this, new Observer<update_last_logintime_response>() {
                                                @Override
                                                public void onChanged(update_last_logintime_response update_last_logintime_response) {
                                                    Log.d("dataxx", update_last_logintime_response.getMessage());
                                                    if (update_last_logintime_response.getMessage().equals("Edited successfully")) {
                                                        User user = new User(password, type, phone);
                                                        SessionManagement sessionManagement = new SessionManagement(Otp_validation_activity.this);
                                                        sessionManagement.saveSession(user);
                                                        Intent intent = new Intent(Otp_validation_activity.this, Shop_main_activity.class);
                                                        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                        startActivity(intent);
                                                    } else {
                                                        SessionManagement sessionManagement = new SessionManagement(Otp_validation_activity.this);
                                                        sessionManagement.removeSession();
                                                        Intent intent = new Intent(Otp_validation_activity.this, LoginActivity.class);
                                                        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });*/
                        User user = new User(password, type, phone);
                        SessionManagement sessionManagement = new SessionManagement(Otp_validation_activity.this);
                        sessionManagement.saveSession(user);
                        Intent intent = new Intent(Otp_validation_activity.this, Shop_main_activity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Otp_validation_activity.this, "Something error.Try again", Toast.LENGTH_SHORT);
                        SessionManagement sessionManagement = new SessionManagement(Otp_validation_activity.this);
                        sessionManagement.removeSession();
                        Intent intent = new Intent(Otp_validation_activity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            });


        } else if (user_type.equals("customer")) {
            message = "no";
            token_update.shop_token_update(password, deviceToken).observe(Otp_validation_activity.this, new Observer<token_update_response>() {
                @Override
                public void onChanged(token_update_response token_update_response) {
                    if (token_update_response.getMessage().equals("Update successfully")) {

                        User user = new User(password, type, phone);
                        SessionManagement sessionManagement = new SessionManagement(Otp_validation_activity.this);
                        sessionManagement.saveSession(user);
                        Intent intent = new Intent(Otp_validation_activity.this, Customer_main_activity.class);
                        startActivity(intent);
/*
  SimpleDateFormat objSDF = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

                        String currentTime = (String) android.text.format.DateFormat.format("yy/MM/dd HH:mm:ss", new java.util.Date());
                                            last_logintime.getUpdate(password, currentTime, "customer").observe(Otp_validation_activity.this, new Observer<update_last_logintime_response>() {
                                                @Override
                                                public void onChanged(update_last_logintime_response update_last_logintime_response) {
                                                    if (update_last_logintime_response.getMessage().equals("Edited successfully")) {
                                                        message = token_update_response.getMessage();
                                                        User user = new User(password, type, phone);
                                                        SessionManagement sessionManagement = new SessionManagement(Otp_validation_activity.this);
                                                        sessionManagement.saveSession(user);
                                                        Intent intent = new Intent(Otp_validation_activity.this, Customer_main_activity.class);
                                                        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                        startActivity(intent);
                                                    } else {
                                                        SessionManagement sessionManagement = new SessionManagement(Otp_validation_activity.this);
                                                        sessionManagement.removeSession();
                                                        Intent intent = new Intent(Otp_validation_activity.this, LoginActivity.class);
                                                        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                        startActivity(intent);
                                                    }

                                                }
                                            });*/

                    } else {

                        SessionManagement sessionManagement = new SessionManagement(Otp_validation_activity.this);
                        sessionManagement.removeSession();
                        Intent intent = new Intent(Otp_validation_activity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            });


        }
    }

    private void shopTokenUpdate() {


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
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            token = task.getResult().getToken();

                            shop_registration.getmessage(shopname, ownername, location, registration_phone, password, image, token).observe(Otp_validation_activity.this, new Observer<String>() {
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
                    }
                });
    }

    private void customer_registration() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            token = task.getResult().getToken();
                            customer_registration.getmessage(shopname, location, registration_phone, password, image, token).observe(Otp_validation_activity.this, new Observer<customer_registration_response>() {
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

                        } else {
                            Toast.makeText(Otp_validation_activity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
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