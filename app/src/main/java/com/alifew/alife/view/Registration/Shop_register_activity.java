package com.alifew.alife.view.Registration;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alifew.alife.R;
import com.alifew.alife.model.OTP_response;
import com.alifew.alife.viewmodel.OTP;
import com.alifew.alife.viewmodel.Shop_registration;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class Shop_register_activity extends AppCompatActivity implements View.OnClickListener {
    Shop_registration shop_registration;
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 1;
    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    int check = 0;
    String imgdata;
    final int IMAGE_REQUEST_CODE = 999;
    private Uri filepath;
    private Bitmap bitmap;
    Button registerButton;
    ImageView backButton, profileImage;
    ACProgressFlower dialog;
    TextInputEditText shopName, ownerName, location, phone, password, repassword;
    TextInputLayout shopNameError, ownerNameError, locationError, phoneError, passwordError, repasswordError;
    OTP otp;

    String shop, owner, loc, cont, phn, pass, repass, type = "shop";

    MaterialButtonToggleGroup toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_register_activity);

        try {
            this.getSupportActionBar().hide();
        } catch (Exception e) {
        }

        shop_registration = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Shop_registration.class);

        dialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY)
                .build();

        /*Bundle extras = getIntent().getExtras();
        type = extras.getString("key");*/

        backButton = (ImageView) findViewById(R.id.backButtonID);
        profileImage = (ImageView) findViewById(R.id.profile_imageID);
        shopName = (TextInputEditText) findViewById(R.id.nameText);
        ownerName = (TextInputEditText) findViewById(R.id.ownerNameTextID);
        location = (TextInputEditText) findViewById(R.id.locationTextID);
        phone = (TextInputEditText) findViewById(R.id.contactText);
        password = (TextInputEditText) findViewById(R.id.passwordText);
        repassword = (TextInputEditText) findViewById(R.id.rePasswordText);
        registerButton = (Button) findViewById(R.id.registrationButton);

        shopNameError = (TextInputLayout) findViewById(R.id.shopNameErrorID);
        ownerNameError = (TextInputLayout) findViewById(R.id.ownerNameErrorID);
        locationError = (TextInputLayout) findViewById(R.id.locationErrorID);
        phoneError = (TextInputLayout) findViewById(R.id.contactErrorID);
        passwordError = (TextInputLayout) findViewById(R.id.passwordErrorID);
        repasswordError = (TextInputLayout) findViewById(R.id.repasswordErrorID);

        toggleButton = findViewById(R.id.toggleGroup);


        //toggle Action

        toggleButton.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                dialog.show();
                if (group.getCheckedButtonId() == R.id.customerID) {
                    startActivity(new Intent(Shop_register_activity.this, Register_activity.class));
                }
            }
        });

        backButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(Shop_register_activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                imageselect();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backButtonID) {
            dialog.show();
            Intent intent = new Intent(this, Register_activity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.registrationButton) {
            shop = shopName.getText().toString().trim();
            owner = ownerName.getText().toString().trim();
            loc = location.getText().toString().trim();
            phn = phone.getText().toString().trim();
            pass = password.getText().toString().trim();
            repass = repassword.getText().toString().trim();

            validation(shop, owner, loc, phn, pass, repass);
        }
    }

    private void validation(String shop, String owner, String loc, String phn, String pass, String repass) {
        if (!(TextUtils.isEmpty(shop) || TextUtils.isEmpty(owner) || TextUtils.isEmpty(loc) || TextUtils.isEmpty(phn) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass))) {
            shopNameError.setErrorEnabled(false);
            ownerNameError.setErrorEnabled(false);
            locationError.setErrorEnabled(false);
            phoneError.setErrorEnabled(false);
            passwordError.setErrorEnabled(false);
            repasswordError.setErrorEnabled(false);
            int l = pass.length();

            if (l < 6) {
                passwordError.setErrorEnabled(true);
                passwordError.setError("Password Too Short");
            } else {
                if (pass.equals(repass)) {
                    passwordError.setErrorEnabled(false);
                    if (check == 1) {
                        imgdata = imgToString(bitmap);
                        otp = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(OTP.class);
                        shop_registration.getvarification(phn).observe(Shop_register_activity.this, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                if (!(s.equals("yes"))) {
                                    Toast.makeText(Shop_register_activity.this, s, Toast.LENGTH_SHORT).show();
                                } else {
                                    /*dialog.show();
                                    registration();*/
                                    Random r = new Random();
                                    int ran = r.nextInt(99999 - 10000 + 1) + 10000;
                                    String random_otp = String.valueOf(ran);
                                    otp.getStatus( phn,"ALifeOTP-"+random_otp).observe(Shop_register_activity.this, new Observer<OTP_response>() {
                                        @Override
                                        public void onChanged(OTP_response otp_response) {
                                            if (otp_response.getStatus().equals("queued")) {
                                                otp_activity(random_otp);
                                            } else {

                                            }
                                        }
                                    });

                                    // Toast.makeText(Shop_register_activity.this,s,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    } else {
                        Toast.makeText(this, "Upload image", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    repasswordError.setErrorEnabled(true);
                    repasswordError.setError("Password Don't match");
                }
            }
        } else {
            shopNameError.setErrorEnabled(false);
            ownerNameError.setErrorEnabled(false);
            locationError.setErrorEnabled(false);
            phoneError.setErrorEnabled(false);
            passwordError.setErrorEnabled(false);
            repasswordError.setErrorEnabled(false);

            if (TextUtils.isEmpty(shop)) {
                shopNameError.setError("Empty Shop Name");
            } else if (TextUtils.isEmpty(owner)) {

                ownerNameError.setError("Empty Owner Name");
            } else if (TextUtils.isEmpty(loc)) {

                locationError.setError("Empty Location");
            } else if (TextUtils.isEmpty(phn)) {

                phoneError.setError("Empty Phone");
            } else if (TextUtils.isEmpty(pass)) {

                passwordError.setError("Empty Password");
            } else if (TextUtils.isEmpty(repass)) {

                repasswordError.setError("Empty Retype Password");
            }
        }
    }

    private void otp_activity(String otp) {
        /*registration r;
        r = new registration(shop, owner, phn, type, loc, pass, imgdata);
        Intent intent = new Intent(Shop_register_activity.this, Otp_validation_activity.class);
        intent.putExtra("shop", shop);
                                  intent.putExtra("type", type);
                                    intent.putExtra("owner", owner);
                                    intent.putExtra("loc", loc);
                                    intent.putExtra("phn", phn);
                                    intent.putExtra("pass", pass);
                                    intent.putExtra("imgdata", imgdata);
                                    intent.putExtra("otp",otp);
        //intent.putExtra("registration", (Parcelable) r);

        startActivity(intent);*/
    }

    public void imageselect() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Shop_register_activity.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (items[i].equals("Gallery")) {
                    Intent intent = new Intent(new Intent(Intent.ACTION_PICK));
                    intent.setType("image/*");

                    startActivityForResult(Intent.createChooser(intent, "select image"), IMAGE_REQUEST_CODE);

                } else if (items[i].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bundle bundle = data.getExtras();
                bitmap = (Bitmap) bundle.get("data");
                check = 1;
                profileImage.setImageBitmap(bitmap);

            } else if (requestCode == IMAGE_REQUEST_CODE) {
                filepath = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(filepath);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    profileImage.setImageBitmap(bitmap);
                    check = 1;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    private String imgToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        String encodeimg = Base64.encodeToString(imgbytes, Base64.DEFAULT);
        return encodeimg;
    }

    public void registration() {
        /*shop_registration.getmessage(shop, owner, loc, phn, pass, imgdata).observe(Shop_register_activity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dialog.cancel();
                if (s.equals("Registration complete successfully")) {
                    //Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(Shop_register_activity.this, LoginActivity.class));
                } else {

                    Toast.makeText(Shop_register_activity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

}