package com.alifew.alife.view.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.alifew.alife.R;
import com.alifew.alife.model.OTP_response;
import com.alifew.alife.model.phone_verification_response;
import com.alifew.alife.model.update_password_response;
import com.alifew.alife.view.LoginActivity;
import com.alifew.alife.view.OTP.OTP_Receiver;
import com.alifew.alife.viewmodel.OTP;
import com.alifew.alife.viewmodel.Phone_verification;
import com.alifew.alife.viewmodel.Update_password;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;

public class Forgot_password_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TextWatcher {

    AppCompatButton sendCodeButton, verifyButton, updateButton;
    LinearLayout sendCodeLayout, otpVerificationLayout, setPasswordLayout;
    Spinner typeSpinner;
    EditText numberText, editText1, editText2, editText3, editText4, editText5;
    TextInputEditText passwordText, retypePasswordText;
    TextInputLayout passwordError, retypePasswordError;

    String[] type = {"Customer", "Shopkeeper"};
    String typeSpin, otpcode;
    Phone_verification phone_verification;
    Update_password update_password;
    OTP otp;
    String shop_id, customer_id;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_activity);
        dialog = new Dialog(Forgot_password_activity.this);
        dialog.setContentView(R.layout.loader);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        phone_verification = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Phone_verification.class);
        update_password = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Update_password.class);
        otp = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(OTP.class);

        sendCodeLayout = (LinearLayout) findViewById(R.id.sendOTPLayoutID);
        otpVerificationLayout = (LinearLayout) findViewById(R.id.otpVerificationLayoutID);
        setPasswordLayout = (LinearLayout) findViewById(R.id.setPasswordLayoutID);

        sendCodeButton = (AppCompatButton) findViewById(R.id.sendOTPButtonID);
        verifyButton = (AppCompatButton) findViewById(R.id.verifyButtonID);
        updateButton = (AppCompatButton) findViewById(R.id.updateButtonID);

        typeSpinner = (Spinner) findViewById(R.id.typeSpinnerID);

        numberText = (EditText) findViewById(R.id.numberTextID);
        editText1 = (EditText) findViewById(R.id.editText1ID);
        editText2 = (EditText) findViewById(R.id.editText2ID);
        editText3 = (EditText) findViewById(R.id.editText3ID);
        editText4 = (EditText) findViewById(R.id.editText4ID);
        editText5 = (EditText) findViewById(R.id.editText5ID);

        passwordText = (TextInputEditText) findViewById(R.id.passwordTextID);
        retypePasswordText = (TextInputEditText) findViewById(R.id.retypePasswordTextID);

        passwordError = (TextInputLayout) findViewById(R.id.passwordErrorID);
        retypePasswordError = (TextInputLayout) findViewById(R.id.retypePasswordErrorID);

        ArrayAdapter typeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, type);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeSpin = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editText1.addTextChangedListener(this);
        editText2.addTextChangedListener(this);
        editText3.addTextChangedListener(this);
        editText4.addTextChangedListener(this);


        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = numberText.getText().toString().trim();

                if (TextUtils.isEmpty(number)) {
                    Toast.makeText(Forgot_password_activity.this, "Enter Number", Toast.LENGTH_SHORT).show();

                } else {
                    //spinner holder variable: typespin
                    dialog.show();
                    if (typeSpin.equals("Shopkeeper")) {
                        phone_verification.shop_phone(number).observe(Forgot_password_activity.this, new Observer<phone_verification_response>() {
                            @Override
                            public void onChanged(phone_verification_response phone_verification_response) {
                                if (!phone_verification_response.getId().equals("0")) {
                                    shop_id = phone_verification_response.getId();
                                    Random r = new Random();
                                    int ran = r.nextInt(99999 - 10000 + 1) + 10000;
                                    otpcode = String.valueOf(ran);
                                    otp.getStatus(number, "ALife Change Password OTP is-" + otpcode).observe(Forgot_password_activity.this, new Observer<OTP_response>() {
                                        @Override
                                        public void onChanged(OTP_response otp_response) {
                                            if (otp_response.getStatus().equals("queued")) {
                                                // otp_activity(random_otp);
                                                dialog.dismiss();
                                                sendCodeLayout.setVisibility(View.GONE);
                                                otpVerificationLayout.setVisibility(View.VISIBLE);
                                                //requestsmspermission();

                                                new OTP_Receiver().setEditText(editText1, editText2, editText3, editText4, editText5);

                                            } else {
                                                dialog.dismiss();
                                                Toast.makeText(Forgot_password_activity.this, "Phone number not valid", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(Forgot_password_activity.this, "No Account available for this number", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    } else if (typeSpin.equals("Customer")) {
                        phone_verification.customer_phone(number).observe(Forgot_password_activity.this, new Observer<phone_verification_response>() {
                            @Override
                            public void onChanged(phone_verification_response phone_verification_response) {
                                Toast.makeText(Forgot_password_activity.this, phone_verification_response.getId(), Toast.LENGTH_SHORT).show();
                                if (!phone_verification_response.getId().equals("0")) {
                                    customer_id = phone_verification_response.getId();
                                    Random r = new Random();
                                    int ran = r.nextInt(99999 - 10000 + 1) + 10000;
                                    otpcode = String.valueOf(ran);


                                    String message = "ALife Change Password OTP is-" + otpcode;
                                    otp.getStatus(number, message).observe(Forgot_password_activity.this, new Observer<OTP_response>() {
                                        @Override
                                        public void onChanged(OTP_response otp_response) {
                                            if (otp_response.getStatus().equals("queued")) {
                                                dialog.dismiss();
                                                // otp_activity(random_otp);
                                                sendCodeLayout.setVisibility(View.GONE);
                                                otpVerificationLayout.setVisibility(View.VISIBLE);
                                                requestsmspermission();

                                                new OTP_Receiver().setEditText(editText1, editText2, editText3, editText4, editText5);

                                            } else {
                                                dialog.dismiss();
                                                Toast.makeText(Forgot_password_activity.this, "Phone number not valid", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(Forgot_password_activity.this, "No Account available for this number", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }
                }
            }
        });

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
                    String otp_code = s1 + s2 + s3 + s4 + s5;
                    if (otp_code.equals(otpcode)) {
                        otpVerificationLayout.setVisibility(View.GONE);
                        setPasswordLayout.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(Forgot_password_activity.this, "OTP not correct", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = passwordText.getText().toString().trim();
                String retypePassword = retypePasswordText.getText().toString().trim();

                passwordError.setErrorEnabled(false);
                retypePasswordError.setErrorEnabled(false);
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(retypePassword)) {
                    if (TextUtils.isEmpty(password)) {
                        passwordError.setError(" ");
                    } else if (TextUtils.isEmpty(retypePassword)) {
                        retypePasswordError.setError(" ");
                    }
                } else {
                    if (password.length() < 5) {
                        passwordError.setError("Min. Password length 5");
                    }else if(password.length() >6)
                    {
                        passwordError.setError("Max. Password length 6");

                    }else {
                        if (!password.equals(retypePassword)) {
                            retypePasswordError.setError("Password don't match");
                        } else {
                            dialog.show();
                            //do code
                            if (typeSpin.equals("Shopkeeper")) {
                                update_password.shop(shop_id, password).observe(Forgot_password_activity.this, new Observer<update_password_response>() {
                                    @Override
                                    public void onChanged(update_password_response update_password_response) {
                                        if (update_password_response.getMessage().equals("Update successfully")) {
                                            dialog.dismiss();
                                            Toast.makeText(Forgot_password_activity.this, "Update successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Forgot_password_activity.this, LoginActivity.class));

                                        } else {
                                            dialog.dismiss();
                                            Toast.makeText(Forgot_password_activity.this, "Try again", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(Forgot_password_activity.this, update_password_response.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else if (typeSpin.equals("Customer")) {
                                update_password.customer(customer_id, password).observe(Forgot_password_activity.this, new Observer<update_password_response>() {
                                    @Override
                                    public void onChanged(update_password_response update_password_response) {
                                        if (update_password_response.getMessage().equals("Update successfully")) {
                                            dialog.dismiss();
                                            Toast.makeText(Forgot_password_activity.this, "Update successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Forgot_password_activity.this, LoginActivity.class));


                                        } else {
                                            dialog.dismiss();
                                            Toast.makeText(Forgot_password_activity.this, "Try again", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(Forgot_password_activity.this, update_password_response.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });

    }

    private void requestsmspermission() {
        String smspermission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(this, smspermission);
        //check if read SMS permission is granted or not
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = smspermission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        typeSpin = parent.getItemAtPosition(position).toString().trim();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = .9f;
        applyOverrideConfiguration(override);
    }
}