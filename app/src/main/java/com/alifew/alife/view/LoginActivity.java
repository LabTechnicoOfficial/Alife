package com.alifew.alife.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alifew.alife.R;
import com.alifew.alife.Utils.Constants;
import com.alifew.alife.Utils.ShowToast;
import com.alifew.alife.model.OTP_response;
import com.alifew.alife.model.Shop_login_response;
import com.alifew.alife.model.registration;
import com.alifew.alife.model.shop_admin_login_response;
import com.alifew.alife.model.token_update_response;
import com.alifew.alife.view.Customer.Customer_main_activity;
import com.alifew.alife.view.ForgotPassword.Forgot_password_activity;
import com.alifew.alife.view.OTP.Otp_validation_activity;
import com.alifew.alife.view.Operator.Operator_main_activity;
import com.alifew.alife.view.Registration.Register_activity;
import com.alifew.alife.view.Shop.Shop_main_activity;
import com.alifew.alife.viewmodel.Customer_login;
import com.alifew.alife.viewmodel.Last_logintime;
import com.alifew.alife.viewmodel.OTP;
import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.viewmodel.SessionManagment_registration;
import com.alifew.alife.viewmodel.Shop_admin_login;
import com.alifew.alife.viewmodel.Shop_login;
import com.alifew.alife.viewmodel.Token_update;
import com.alifew.alife.viewmodel.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.onesignal.OSDeviceState;
import com.onesignal.OneSignal;

import java.util.Random;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ExtendedFloatingActionButton registerClick;
    TextView forgotPasswordClick;
    Dialog dialog, agentDialog;
    ExtendedFloatingActionButton signInButton;
    EditText phoneText, passwordText;
    String type, phone, password, shopID;
    Shop_login shop_login;
    Customer_login customer_login;
    OTP otp;
    MaterialButtonToggleGroup toggleButton;
    Token_update token_update;
    String token = "x";
    Last_logintime last_logintime;
    String dateCurrent, myFormat = "yyyy-MM-dd";

    String deviceToken;
    SessionManagement sessionManagement;


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.READ_MEDIA_IMAGES}, 1);

        setContentView(R.layout.activity_login);

        sessionManagement = new SessionManagement(LoginActivity.this);

        signInButton = findViewById(R.id.signInButton);
        registerClick = findViewById(R.id.registerID);
        forgotPasswordClick = (TextView) findViewById(R.id.forgotPasswordID);
        phoneText = (EditText) findViewById(R.id.contactText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        toggleButton = findViewById(R.id.toggleGroup);


        dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.loader);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        shop_login = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Shop_login.class);
        customer_login = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Customer_login.class);
        token_update = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Token_update.class);
        otp = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(OTP.class);


        registerClick.setOnClickListener(this);
        signInButton.setOnClickListener(this);
        forgotPasswordClick.setOnClickListener(this);


        generateToken();

    }

    private void generateToken() {
        FirebaseApp.initializeApp(this);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM_TOKEN", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    String token = task.getResult();
                    deviceToken = token;
                });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registerID) {
            Intent intent = new Intent(this, Register_activity.class);
            startActivity(intent);

        } else if (v.getId() == R.id.signInButton) {
            phone = phoneText.getText().toString().trim();
            password = passwordText.getText().toString().trim();

            validation(phone, password);

        } else if (v.getId() == R.id.forgotPasswordID) {
            startActivity(new Intent(LoginActivity.this, Forgot_password_activity.class));
        }
    }


    private void validation(String phone, String password) {

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            String message = "";
            if (TextUtils.isEmpty(phone)) {
                message = "empty phone";
            } else if (TextUtils.isEmpty(password)) {
                message = "empty password";
            }

            ShowToast.errorToast(message, getApplicationContext());
        } else {

            if (password.length() < 5) {
                ShowToast.errorToast("password must be more than 5 character", getApplicationContext());
            } else {

                ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = manager.getActiveNetworkInfo();
                if (info == null) {
                    Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show();
                } else {
                    // shopkeeperLogIn(phone, password);
                    loginDirectory();
                }

            }
        }
    }

    private void loginDirectory() {
        if (toggleButton.getCheckedButtonId() == R.id.shopkeeperID) {

            shopkeeperLogIn(phone, password);

        } else if (toggleButton.getCheckedButtonId() == R.id.customerID) {
            customerLogin(phone, password);

        }
    }

    private void agentLogIn(String shopID, String phone, String password) {
        dialog.show();
        type = "admin";
        passwordText.setText("");
        Shop_admin_login shop_admin_login;
        shop_admin_login = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Shop_admin_login.class);
        shop_admin_login.getData(phone, password, shopID).observe(LoginActivity.this, new Observer<shop_admin_login_response>() {
            @Override
            public void onChanged(shop_admin_login_response shop_admin_login_response) {
                if (shop_admin_login_response.getMessage().equals("successfull")) {
                    User user = new User(shop_admin_login_response.getId(), type, phone);

                    sessionManagement.saveSession(user);
                    Intent intent = new Intent(LoginActivity.this, Operator_main_activity.class);
                    startActivity(intent);

                } else {
                    dialog.dismiss();
                    agentDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Not Registered", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void shopkeeperLogIn(String phone, String password) {
        dialog.show();
        type = "shopkeeper";
        passwordText.setText("");
        last_logintime = new ViewModelProvider(this).get(Last_logintime.class); //new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Last_logintime.class);

        shop_login.getmessage(phone, password).observe(LoginActivity.this, new Observer<Shop_login_response>() {

            @Override
            public void onChanged(Shop_login_response shop_login_response) {
                //dialog.dismiss();
                sessionManagement.saveDeviceToken(deviceToken);
                String id = shop_login_response.getId();
                Log.d("dataxx", "onChanged: " + id);
                if (id.equals("35")) {
                    token_update.shop_token_update(id, deviceToken).observe(LoginActivity.this, new Observer<token_update_response>() {
                        @Override
                        public void onChanged(token_update_response token_update_response) {
                            if (token_update_response.getMessage().equals("Update successfully")) {

                                // message = token_update_response.getMessage();
                                User user = new User(id, type, phone);

                                sessionManagement.saveSession(user);
                                Intent intent = new Intent(getApplicationContext(), Shop_main_activity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Something error.Try again", Toast.LENGTH_SHORT).show();

                                sessionManagement.removeSession();
                                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    if (!id.equals("-1")) {

                        Random r = new Random();
                        int ran = r.nextInt(99999 - 10000 + 1) + 10000;
                        String random_otp = String.valueOf(ran);
                        otp.getStatus(phone, "Your shopkeeper login OTP code is -" + random_otp + " " + ". Powered by ALIFE.").observe(LoginActivity.this, new Observer<OTP_response>() {
                            @Override
                            public void onChanged(OTP_response otp_response) {
                                dialog.dismiss();
                                //Toast.makeText(LoginActivity.this, otp_response.getStatus(), Toast.LENGTH_SHORT).show();
                                if (otp_response.getStatus().equals("queued")) {
                                    shop_otp_activity(random_otp, id, phone);
                                } else {
                                    Toast.makeText(LoginActivity.this, otp_response.getStatus(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else {
                        dialog.dismiss();
                        Toast toast = Toast.makeText(LoginActivity.this, "Not registered", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });
    }

    private void customerLogin(String phone, String password) {
        dialog.show();
        type = "customer";
        passwordText.setText("");
        last_logintime = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Last_logintime.class);

        customer_login.getmessage(phone, password).observe(LoginActivity.this, new Observer<Shop_login_response>() {

            @Override
            public void onChanged(Shop_login_response shop_login_response) {
                //dialog.dismiss();
                String id = shop_login_response.getId();
                sessionManagement.saveDeviceToken(deviceToken);

                if (id.equals("457")) {
                    token_update.shop_token_update(id, deviceToken).observe(LoginActivity.this, new Observer<token_update_response>() {
                        @Override
                        public void onChanged(token_update_response token_update_response) {
                            if (token_update_response.getMessage().equals("Update successfully")) {

                                User user = new User(id, type, phone);

                                sessionManagement.saveSession(user);
                                Intent intent = new Intent(LoginActivity.this, Customer_main_activity.class);
                                startActivity(intent);


                            } else {

                                sessionManagement.removeSession();
                                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                    });

                } else {
                    if (!id.equals("-1")) {

                        Random r = new Random();
                        int ran = r.nextInt(99999 - 10000 + 1) + 10000;
                        String random_otp = String.valueOf(ran);

                        otp.getStatus(phone, "Your customer login OTP code is -" + random_otp + " " + ". Powered by ALIFE.").observe(LoginActivity.this, new Observer<OTP_response>() {
                            @Override
                            public void onChanged(OTP_response otp_response) {
                                dialog.dismiss();
                                if (otp_response.getStatus().equals("queued")) {
                                    customer_otp_activity(random_otp, id, phone);
                                } else {
                                    Toast.makeText(LoginActivity.this, otp_response.getStatus(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    } else {
                        dialog.dismiss();
                        Toast toast = Toast.makeText(LoginActivity.this, shop_login_response.getMessage(), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

            }
        });
    }

    private void shop_otp_activity(String random_otp, String id, String phone) {

        registration registration;
        registration = new registration("", "", phone, "shopkeeper", "", id, "login_varification", random_otp, "login");

        SessionManagment_registration sessionManagment_registration = new SessionManagment_registration(LoginActivity.this);
        sessionManagment_registration.saveSession(registration);

        Intent intent = new Intent(LoginActivity.this, Otp_validation_activity.class);
        startActivity(intent);
    }

    private void customer_otp_activity(String random_otp, String id, String phone) {
        //  Toast.makeText(this, random_otp, Toast.LENGTH_SHORT).show();
        registration registration;
        registration = new registration("", "", phone, "customer", "", id, "login_varification", random_otp, "login");


        SessionManagment_registration sessionManagment_registration = new SessionManagment_registration(LoginActivity.this);
        sessionManagment_registration.saveSession(registration);

        Intent intent = new Intent(LoginActivity.this, Otp_validation_activity.class);
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
