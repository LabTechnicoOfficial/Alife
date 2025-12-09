package com.alifew.bcopay.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alifew.bcopay.R;
import com.alifew.bcopay.Utils.ShowToast;
import com.alifew.bcopay.model.RegistrationResponse;
import com.alifew.bcopay.view.Customer.Customer_main_activity;
import com.alifew.bcopay.view.ForgotPassword.Forgot_password_activity;
import com.alifew.bcopay.view.OTP.Otp_validation_activity;
import com.alifew.bcopay.view.Operator.Operator_main_activity;
import com.alifew.bcopay.view.Registration.Register_activity;
import com.alifew.bcopay.view.Shop.Shop_main_activity;
import com.alifew.bcopay.viewmodel.Customer_login;
import com.alifew.bcopay.viewmodel.Last_logintime;
import com.alifew.bcopay.viewmodel.OTP;
import com.alifew.bcopay.session.SessionManagement;
import com.alifew.bcopay.viewmodel.SessionManagment_registration;
import com.alifew.bcopay.viewmodel.Shop_admin_login;
import com.alifew.bcopay.viewmodel.Shop_login;
import com.alifew.bcopay.viewmodel.Token_update;
import com.alifew.bcopay.viewmodel.User;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;
import java.util.Random;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView forgotPasswordClick, registerClick, loginWithOtpButton;
    Dialog dialog, agentDialog;
    ExtendedFloatingActionButton signInButton;
    EditText phoneText, passwordText;
    String type = "customer", phone, password;
    Shop_login shop_login;
    Customer_login customer_login;
    OTP otp;
    MaterialButtonToggleGroup toggleButton;
    Token_update token_update;
    Last_logintime last_logintime;

    String deviceToken;
    SessionManagement sessionManagement;
    View barID;
    Dialog loader;


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
        loginWithOtp();
        barID = findViewById(R.id.barID);
        signInButton = findViewById(R.id.signInButton);
        registerClick = findViewById(R.id.registerID);
        registerClick.setPaintFlags(
                registerClick.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG
        );
        loginWithOtpButton = findViewById(R.id.loginWithOtpButton);
        loginWithOtpButton.setPaintFlags(
                loginWithOtpButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG
        );

        loginWithOtpButton.setOnClickListener(v -> loginWithOtp());

        forgotPasswordClick = findViewById(R.id.forgotPasswordID);
        phoneText = findViewById(R.id.contactText);
        passwordText = findViewById(R.id.passwordText);
        toggleButton = findViewById(R.id.toggleGroup);


        dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.loader);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        shop_login = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Shop_login.class);
        customer_login = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Customer_login.class);
        token_update = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Token_update.class);
        otp = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(OTP.class);


        registerClick.setOnClickListener(this);
        signInButton.setOnClickListener(this);
        forgotPasswordClick.setOnClickListener(this);

        loader = new Dialog(LoginActivity.this);
        loader.setContentView(R.layout.loader);
        Objects.requireNonNull(loader.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        if (toggleButton.getCheckedButtonId() == R.id.customerID) {
            barID.setVisibility(VISIBLE);
            loginWithOtpButton.setVisibility(VISIBLE);
        } else {
            barID.setVisibility(GONE);
            loginWithOtpButton.setVisibility(GONE);
        }

        toggleButton.addOnButtonCheckedListener(
                (group, checkedId, isChecked) -> {
                    if (!isChecked) return;

                    if (checkedId == R.id.customerID) {
                        barID.setVisibility(VISIBLE);
                        loginWithOtpButton.setVisibility(VISIBLE);
                    } else if (checkedId == R.id.shopkeeperID) {
                        barID.setVisibility(GONE);
                        loginWithOtpButton.setVisibility(GONE);
                    }
                }
        );

        generateToken();

    }

    private void loginWithOtp() {
        Dialog otpLoginAlert = new Dialog(LoginActivity.this);
        otpLoginAlert.setContentView(R.layout.otp_login_alert);
        Objects.requireNonNull(otpLoginAlert.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        otpLoginAlert.setCancelable(false);
        otpLoginAlert.show();

        Window window = otpLoginAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        TextInputEditText contactText = otpLoginAlert.findViewById(R.id.contactText);
        AppCompatButton submitButton = otpLoginAlert.findViewById(R.id.submitButton);
        ImageView closeButton = otpLoginAlert.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> otpLoginAlert.dismiss());

        submitButton.setOnClickListener(v -> {
            if (TextUtils.isEmpty(Objects.requireNonNull(contactText.getText()).toString().trim())) {
                String message = "empty phone";
                ShowToast.errorToast(message, getApplicationContext());
            } else {
                Random r = new Random();
                int ran = r.nextInt(99999 - 10000 + 1) + 10000;
                String random_otp = String.valueOf(ran);
                loader.show();
                otp.getStatus(contactText.getText().toString().trim(), "Your customer login OTP code is -" + random_otp + " " + ". Powered by " + getApplication().getString(R.string.app_name)).observe(LoginActivity.this, otp_response -> {
                    loader.dismiss();
                    if (otp_response.getStatus().equals("queued")) {
                        customer_otp_activity(random_otp, "", contactText.getText().toString().trim());
                    } else {
                        Toast.makeText(LoginActivity.this, otp_response.getStatus(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void generateToken() {
        FirebaseApp.initializeApp(this);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM_TOKEN", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    deviceToken = task.getResult();
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
        shop_admin_login.getData(phone, password, shopID).observe(LoginActivity.this, shop_admin_login_response -> {
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
        });
    }

    private void shopkeeperLogIn(String phone, String password) {
        dialog.show();
        type = "shopkeeper";
        passwordText.setText("");
        last_logintime = new ViewModelProvider(this).get(Last_logintime.class); //new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Last_logintime.class);

        shop_login.getmessage(phone, password).observe(LoginActivity.this, shop_login_response -> {
            //dialog.dismiss();
            sessionManagement.saveDeviceToken(deviceToken);
            String id = shop_login_response.getId();
            // Log.d("dataxx", "onChanged: " + id);
            if (id.equals("35")) {
                token_update.shop_token_update(id, deviceToken).observe(LoginActivity.this, token_update_response -> {
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
                });
            } else {
                if (!id.equals("-1")) {

                    Random r = new Random();
                    int ran = r.nextInt(99999 - 10000 + 1) + 10000;
                    String random_otp = String.valueOf(ran);
                    otp.getStatus(phone, "Your shopkeeper login OTP code is -" + random_otp + " " + ". Powered by " + getApplication().getString(R.string.app_name)).observe(LoginActivity.this, otp_response -> {
                        dialog.dismiss();
                        if (otp_response.getStatus().equals("queued")) {
                            RegistrationResponse registration;
                            registration = new RegistrationResponse("", "", phone, "customer", "", "", "otp_login", random_otp, "login");

                            SessionManagment_registration sessionManagment_registration = new SessionManagment_registration(LoginActivity.this);
                            sessionManagment_registration.saveSession(registration);

                            shop_otp_activity(random_otp, id, phone);
                        } else {
                            Toast.makeText(LoginActivity.this, otp_response.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    dialog.dismiss();
                    Toast toast = Toast.makeText(LoginActivity.this, "Not registered", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

        });
    }

    private void customerLogin(String phone, String password) {
        dialog.show();
        type = "customer";
        passwordText.setText("");
        last_logintime = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Last_logintime.class);

        customer_login.getmessage(phone, password).observe(LoginActivity.this, shop_login_response -> {
            //dialog.dismiss();
            String id = shop_login_response.getId();
            sessionManagement.saveDeviceToken(deviceToken);

            if (id.equals("457")) {
                token_update.customer_token_update(id, deviceToken).observe(LoginActivity.this, token_update_response -> {
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
                });

            } else {
                if (!id.equals("-1")) {

                    Random r = new Random();
                    int ran = r.nextInt(99999 - 10000 + 1) + 10000;
                    String random_otp = String.valueOf(ran);

                    otp.getStatus(phone, "Your customer login OTP code is -" + random_otp + " " + ". Powered by " + getApplication().getString(R.string.app_name)).observe(LoginActivity.this, otp_response -> {
                        dialog.dismiss();
                        if (otp_response.getStatus().equals("queued")) {
                            customer_otp_activity(random_otp, id, phone);
                        } else {
                            Toast.makeText(LoginActivity.this, otp_response.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    dialog.dismiss();
                    Toast toast = Toast.makeText(LoginActivity.this, shop_login_response.getMessage(), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

        });
    }

    private void shop_otp_activity(String random_otp, String id, String phone) {

        RegistrationResponse registration;
        registration = new RegistrationResponse("", "", phone, "shopkeeper", "", id, "login_varification", random_otp, "login");

        SessionManagment_registration sessionManagment_registration = new SessionManagment_registration(LoginActivity.this);
        sessionManagment_registration.saveSession(registration);

        Intent intent = new Intent(LoginActivity.this, Otp_validation_activity.class);
        startActivity(intent);
    }

    private void customer_otp_activity(String random_otp, String id, String phone) {
        //  Toast.makeText(this, random_otp, Toast.LENGTH_SHORT).show();
        Log.d("dataxx", "customer_otp_activity: "+phone);
        RegistrationResponse registration;
        registration = new RegistrationResponse("", "", phone, "customer", "", id, "otp_login", random_otp, "login");


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
