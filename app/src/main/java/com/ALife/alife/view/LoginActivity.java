package com.ALife.alife.view;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ALife.alife.R;
import com.ALife.alife.model.OTP_response;
import com.ALife.alife.model.Shop_login_response;
import com.ALife.alife.model.last_logintime_response;
import com.ALife.alife.model.registration;
import com.ALife.alife.model.shop_admin_login_response;
import com.ALife.alife.view.Customer.Customer_main_activity;
import com.ALife.alife.view.ForgotPassword.Forgot_password_activity;
import com.ALife.alife.view.OTP.Otp_validation_activity;
import com.ALife.alife.view.Operator.Operator_main_activity;
import com.ALife.alife.view.Registration.Register_activity;
import com.ALife.alife.view.Shop.Shop_main_activity;
import com.ALife.alife.viewmodel.Customer_login;
import com.ALife.alife.viewmodel.Last_logintime;
import com.ALife.alife.viewmodel.OTP;
import com.ALife.alife.session.SessionManagement;
import com.ALife.alife.viewmodel.SessionManagment_registration;
import com.ALife.alife.viewmodel.Shop_admin_login;
import com.ALife.alife.viewmodel.Shop_login;
import com.ALife.alife.viewmodel.Token_update;
import com.ALife.alife.viewmodel.User;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView registerClick, forgotPasswordClick;
    Dialog dialog, agentDialog;
    Button signInButton;
    EditText phoneText, passwordText;
    String type, phone, password, shopID;
    Shop_login shop_login;
    Customer_login customer_login;
    OTP otp;
    MaterialButtonToggleGroup toggleButton;
    TextInputLayout phoneError, passwordError;
    Token_update token_update;
    String token = "x";
    Last_logintime last_logintime;
    String dateCurrent, myFormat = "yyyy-MM-dd";

    @Override
    protected void onStart() {
        super.onStart();
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        int userId = sessionManagement.getSession();
        String type = sessionManagement.getType();
        String phone = sessionManagement.getPhone();
//        if (userId != -1) {
//            if (type.equals("shopkeeper")) {
//                Intent intent = new Intent(LoginActivity.this, Shop_main_activity.class);
//                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                startActivity(intent);
//            } else if (type.equals("customer")) {
//                Intent intent = new Intent(LoginActivity.this, Customer_main_activity.class);
//                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                startActivity(intent);
//            } else if (type.equals("admin")) {
//                Intent intent = new Intent(LoginActivity.this, Operator_main_activity.class);
//                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                startActivity(intent);
//            }
//
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE}, 1);
        //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        int userId = sessionManagement.getSession();
        String type = sessionManagement.getType();
        String phone = sessionManagement.getPhone();
//
//        if (userId != -1) {
//            if (type.equals("shopkeeper")) {
//                Intent intent = new Intent(LoginActivity.this, Shop_main_activity.class);
//                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                startActivity(intent);
//            } else if (type.equals("customer")) {
//                Intent intent = new Intent(LoginActivity.this, Customer_main_activity.class);
//                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                startActivity(intent);
//            } else if (type.equals("admin")) {
//                Intent intent = new Intent(LoginActivity.this, Operator_main_activity.class);
//                //.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                startActivity(intent);
//            }
//
//        }
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_login);

        signInButton = (Button) findViewById(R.id.signinButtonID);
        registerClick = (TextView) findViewById(R.id.registerID);
        forgotPasswordClick = (TextView) findViewById(R.id.forgotPasswordID);
        phoneText = (EditText) findViewById(R.id.phoneTextID);
        passwordText = (EditText) findViewById(R.id.passwordTextID);
        toggleButton = findViewById(R.id.toggleGroup);

        phoneError = (TextInputLayout) findViewById(R.id.phoneErrorID);
        passwordError = (TextInputLayout) findViewById(R.id.passwordErrorID);

        try {
            this.getSupportActionBar().hide();
        } catch (Exception e) {
        }

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

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registerID) {
            dialog.show();
            Intent intent = new Intent(this, Register_activity.class);
            startActivity(intent);

        } else if (v.getId() == R.id.signinButtonID) {
            phone = phoneText.getText().toString().trim();
            password = passwordText.getText().toString().trim();

            validation(phone, password);

        } else if (v.getId() == R.id.forgotPasswordID) {
            startActivity(new Intent(LoginActivity.this, Forgot_password_activity.class));
        }
    }


    private void validation(String phone, String password) {
        phoneError.setErrorEnabled(false);
        passwordError.setErrorEnabled(false);
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            phoneError.setErrorEnabled(false);
            passwordError.setErrorEnabled(false);
            if (TextUtils.isEmpty(phone)) {
                phoneError.setError("Enter Phone");
            } else if (TextUtils.isEmpty(password)) {
                passwordError.setError("Empty Password");
            }
        } else {
            passwordError.setErrorEnabled(false);
            if (password.length() < 5) {
                phoneError.setError("Password Too short");
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
            /*dialog.show();
            type = "customer";
            passwordText.setText("");
            customer_login.getmessage(phone, password).observe(LoginActivity.this, new Observer<Shop_login_response>() {

                @Override
                public void onChanged(Shop_login_response shop_login_response) {

                    String id = shop_login_response.getId();

                    if (!(id.equals("-1"))) {
                        // for update token
                        FirebaseInstanceId.getInstance().getInstanceId()
                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                        if (task.isSuccessful()) {
                                            token = task.getResult().getToken();
                                            token_update.customer_token_update(id, token).observe(LoginActivity.this, new Observer<token_update_response>() {
                                                @Override
                                                public void onChanged(token_update_response token_update_response) {
                                                    if (token_update_response.getMessage().equals("Update successfully")) {
                                                        User user = new User(shop_login_response.getId(), type, phone);
                                                        SessionManagment sessionManagment = new SessionManagment(LoginActivity.this);
                                                        sessionManagment.saveSession(user);
                                                        Intent intent = new Intent(LoginActivity.this, Customer_main_activity.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                        startActivity(intent);
                                                    } else {
                                                        dialog.dismiss();
                                                        Toast toast = Toast.makeText(LoginActivity.this, "Something error.Try again", Toast.LENGTH_SHORT);
                                                        toast.show();
                                                    }
                                                }
                                            });

                                        } else {
                                            Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                        //end update token

                    } else {
                        dialog.dismiss();
                        Toast toast = Toast.makeText(LoginActivity.this, "Not registered", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });

       */
        }
    }


    /*private void validationOk(String phone, String password) {

        if (toggleButton.getCheckedButtonId() == R.id.shopkeeperID) {

            agentDialog = new Dialog(LoginActivity.this);
            agentDialog.setContentView(R.layout.shopkeeper_agent_alert);
            agentDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            agentDialog.show();

            RadioGroup radioGroup = (RadioGroup) agentDialog.findViewById(R.id.radioGroupID);
            TextInputEditText shopText = (TextInputEditText) agentDialog.findViewById(R.id.shopTextID);
            TextInputLayout shopError = (TextInputLayout) agentDialog.findViewById(R.id.shopErrorID);
            LinearLayout shopLayout = (LinearLayout) agentDialog.findViewById(R.id.shopLayoutID);
            AppCompatButton nextButton = (AppCompatButton) agentDialog.findViewById(R.id.nextButtonID);

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.adminRadioID) {
                        shopLayout.setVisibility(View.VISIBLE);
                    }
                    if (checkedId == R.id.shopkepperRadioID) {
                        shopLayout.setVisibility(View.GONE);
                    }
                }
            });

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (radioGroup.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(LoginActivity.this, "Ensure whether you Operator or Shopkeeper", Toast.LENGTH_SHORT).show();
                    } else {
                        if (radioGroup.getCheckedRadioButtonId() == R.id.adminRadioID) {
                            shopID = shopText.getText().toString().trim();
                            shopError.setErrorEnabled(false);
                            if (TextUtils.isEmpty(shopID)) {
                                shopError.setError(" ");
                            } else {
                                agentLogIn(shopID, phone, password);
                            }

                        } else if (radioGroup.getCheckedRadioButtonId() == R.id.shopkepperRadioID) {
                            shopkeeperLogIn(phone, password);
                        }
                    }
                }
            });

        } else if (toggleButton.getCheckedButtonId() == R.id.customerID) {
            dialog.show();
            type = "customer";
            passwordText.setText("");
            customer_login.getmessage(phone, password).observe(LoginActivity.this, new Observer<Shop_login_response>() {

                @Override
                public void onChanged(Shop_login_response shop_login_response) {

                    String id = shop_login_response.getId();

                    if (!(id.equals("-1"))) {
                        Log.d("dataxx", "onChanged: ");
                        // for update token
                        FirebaseInstanceId.getInstance().getInstanceId()
                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                        if (task.isSuccessful()) {
                                            token = task.getResult().getToken();
                                            token_update.customer_token_update(id, token).observe(LoginActivity.this, new Observer<token_update_response>() {
                                                @Override
                                                public void onChanged(token_update_response token_update_response) {
                                                    if (token_update_response.getMessage().equals("Update successfully")) {
                                                        User user = new User(shop_login_response.getId(), type, phone);
                                                        SessionManagment sessionManagment = new SessionManagment(LoginActivity.this);
                                                        sessionManagment.saveSession(user);
                                                        Intent intent = new Intent(LoginActivity.this, Customer_main_activity.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                        startActivity(intent);
                                                    } else {
                                                        dialog.dismiss();
                                                        Toast toast = Toast.makeText(LoginActivity.this, "Something error.Try again", Toast.LENGTH_SHORT);
                                                        toast.show();
                                                    }
                                                }
                                            });

                                        } else {
                                            Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                        //end update token

                    } else {

                        Log.d("dataxx", "onChanged: hi");
                        dialog.dismiss();
                        Toast toast = Toast.makeText(LoginActivity.this, shop_login_response.getMessage(), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });

        }


    }*/

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
                    SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
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
        last_logintime = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Last_logintime.class);

        shop_login.getmessage(phone, password).observe(LoginActivity.this, new Observer<Shop_login_response>() {

            @Override
            public void onChanged(Shop_login_response shop_login_response) {

                String id = shop_login_response.getId();
                if (!(id.equals("-1"))) {
                    last_logintime.getTime(id, "shop").observe(LoginActivity.this, new Observer<last_logintime_response>() {

                        @Override
                        public void onChanged(last_logintime_response last_logintime_response) {
                            if (last_logintime_response.getLast_time().equals("0")) {
                                Random r = new Random();
                                int ran = r.nextInt(99999 - 10000 + 1) + 10000;
                                String random_otp = String.valueOf(ran);
                                Toast.makeText(LoginActivity.this, random_otp, Toast.LENGTH_SHORT).show();
                                otp.getStatus(phone, "ALife..Your Shop LogIn OTP is -" + random_otp).observe(LoginActivity.this, new Observer<OTP_response>() {
                                    @Override
                                    public void onChanged(OTP_response otp_response) {
                                        if (otp_response.getStatus().equals("queued")) {
                                            shop_otp_activity(random_otp, id, phone);
                                        } else {

                                        }
                                    }
                                });
                            } else if (last_logintime_response.getLast_time().isEmpty()) {
                                Toast.makeText(LoginActivity.this, "Something Wrong!!!Try again", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Random r = new Random();
                                int ran = r.nextInt(99999 - 10000 + 1) + 10000;
                                String random_otp = String.valueOf(ran);
                                Toast.makeText(LoginActivity.this, random_otp, Toast.LENGTH_SHORT).show();
                                otp.getStatus(phone, "ALife..Your Shop LogIn OTP is -" + random_otp).observe(LoginActivity.this, new Observer<OTP_response>() {
                                    @Override
                                    public void onChanged(OTP_response otp_response) {
                                        if (otp_response.getStatus().equals("queued")) {
                                            shop_otp_activity(random_otp, id, phone);
                                        } else {

                                        }
                                    }
                                });

/*                                String last_login_time = last_logintime_response.getLast_time();
                                SimpleDateFormat objSDF = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

                                String currentTime = (String) android.text.format.DateFormat.format("yy/MM/dd HH:mm:ss", new java.util.Date());
                                Date d1 = null;
                                Date d2 = null;
                                try {
                                    d1 = objSDF.parse(last_login_time);
                                    d2 = objSDF.parse(currentTime);
                                } catch (Exception e) {

                                }
                                //long diff = d2.getTime() - d1.getTime();

                                long diff = TimeUnit.MILLISECONDS.toHours(d2.getTime() - d1.getTime());
                                if (diff > 47.99) {
                                    Random r = new Random();
                                    int ran = r.nextInt(99999 - 10000 + 1) + 10000;
                                    String random_otp = String.valueOf(ran);

                                    Toast.makeText(LoginActivity.this, random_otp, Toast.LENGTH_SHORT).show();
                                    otp.getStatus(phone, "ALife..Your Shop LogIn OTP is -" + random_otp).observe(LoginActivity.this, new Observer<OTP_response>() {
                                        @Override
                                        public void onChanged(OTP_response otp_response) {
                                            if (otp_response.getStatus().equals("queued")) {
                                                shop_otp_activity(random_otp, id, phone);
                                            } else {

                                            }
                                        }
                                    });
                                } else {
                                    Double waiting_time = 48.00 - diff;
                                    Toast.makeText(LoginActivity.this, "Please try after " + String.valueOf(waiting_time) + " hours.", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }*/


                            }
                        }
                    });


                    // for update token


                    //end update token
                } else {
                    dialog.dismiss();
                    //agentDialog.dismiss();
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

        customer_login.getmessage(phone, password).observe(LoginActivity.this, new Observer<Shop_login_response>() {

            @Override
            public void onChanged(Shop_login_response shop_login_response) {

                String id = shop_login_response.getId();
                Log.d("mijan", "bbbb");
                if (!(id.equals("-1"))) {
                    last_logintime.getTime(id, "customer").observe(LoginActivity.this, new Observer<last_logintime_response>() {
                        @Override
                        public void onChanged(last_logintime_response last_logintime_response) {
                            if (last_logintime_response.getLast_time().equals("0")) {
                                Random r = new Random();
                                int ran = r.nextInt(99999 - 10000 + 1) + 10000;
                                String random_otp = String.valueOf(ran);
                                Toast.makeText(LoginActivity.this, random_otp, Toast.LENGTH_SHORT).show();
                                otp.getStatus(phone, "ALife..Your Customer LogIn OTP is -" + random_otp).observe(LoginActivity.this, new Observer<OTP_response>() {
                                    @Override
                                    public void onChanged(OTP_response otp_response) {
                                        if (otp_response.getStatus().equals("queued")) {
                                            customer_otp_activity(random_otp, id, phone);
                                        } else {

                                        }
                                    }
                                });
                            } else if (last_logintime_response.getLast_time().isEmpty()) {
                                Toast.makeText(LoginActivity.this, "Something Wrong!!!Try again", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
/*                                String last_login_time = last_logintime_response.getLast_time();
                                SimpleDateFormat objSDF = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

                                String currentTime = (String) android.text.format.DateFormat.format("yy/MM/dd HH:mm:ss", new java.util.Date());
                                Date d1 = null;
                                Date d2 = null;
                                try {
                                    d1 = objSDF.parse(last_login_time);
                                    d2 = objSDF.parse(currentTime);
                                } catch (Exception e) {

                                }
                                //long diff = d2.getTime() - d1.getTime();

                                long diff = TimeUnit.MILLISECONDS.toHours(d2.getTime() - d1.getTime());
                                if (diff > 47.99) {
                                    Random r = new Random();
                                    int ran = r.nextInt(99999 - 10000 + 1) + 10000;
                                    String random_otp = String.valueOf(ran);
                                    Toast.makeText(LoginActivity.this, random_otp, Toast.LENGTH_SHORT).show();
                                    customer_otp_activity(random_otp, id, phone);
                                    otp.getStatus(phone, "ALife..Your Customer LogIn OTP is -" + random_otp).observe(LoginActivity.this, new Observer<OTP_response>() {
                                        @Override
                                        public void onChanged(OTP_response otp_response) {
                                            if (otp_response.getStatus().equals("queued")) {
                                                customer_otp_activity(random_otp, id, phone);
                                            } else {

                                            }
                                        }
                                    });
                                } else {
                                    Double waiting_time = 48.00 - diff;
                                    Toast.makeText(LoginActivity.this, "Please try after " + String.valueOf(waiting_time) + " hours.", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }*/
                                Random r = new Random();
                                int ran = r.nextInt(99999 - 10000 + 1) + 10000;
                                String random_otp = String.valueOf(ran);

                                otp.getStatus(phone, "ALife..Your Customer LogIn OTP is -" + random_otp).observe(LoginActivity.this, new Observer<OTP_response>() {
                                    @Override
                                    public void onChanged(OTP_response otp_response) {
                                        if (otp_response.getStatus().equals("queued")) {
                                            customer_otp_activity(random_otp, id, phone);
                                        } else {

                                        }
                                    }
                                });

                            }
                        }
                    });


                    // for update token


                    //end update token
                } else {
                    dialog.dismiss();
                    //agentDialog.dismiss();
                    Toast toast = Toast.makeText(LoginActivity.this, shop_login_response.getMessage(), Toast.LENGTH_SHORT);
                    toast.show();
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
        Toast.makeText(this, random_otp, Toast.LENGTH_SHORT).show();
        registration registration;
        registration = new registration("", "", phone, "customer", "", id, "login_varification", random_otp, "login");



        SessionManagment_registration sessionManagment_registration = new SessionManagment_registration(LoginActivity.this);
        sessionManagment_registration.saveSession(registration);

        Intent intent = new Intent(LoginActivity.this, Otp_validation_activity.class);
        startActivity(intent);
    }

   /*  @Override
   public void onBackPressed() {

        Dialog alert = new Dialog(LoginActivity.this);
        alert.setContentView(R.layout.exit_alert);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.setCancelable(false);
        alert.show();

        TextView yesButton = (TextView) alert.findViewById(R.id.yesButtonID);
        TextView noButton = (TextView) alert.findViewById(R.id.noButtonID);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.cancel();
            }
        });


    }*/

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = .9f;
        applyOverrideConfiguration(override);
    }
}
