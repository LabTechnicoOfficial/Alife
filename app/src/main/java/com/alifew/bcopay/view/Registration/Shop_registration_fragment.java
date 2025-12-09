package com.alifew.bcopay.view.Registration;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.alifew.bcopay.R;
import com.alifew.bcopay.Utils.ShowToast;
import com.alifew.bcopay.model.OTP_response;
import com.alifew.bcopay.model.RegistrationResponse;
import com.alifew.bcopay.view.OTP.Otp_validation_activity;
import com.alifew.bcopay.viewmodel.OTP;
import com.alifew.bcopay.viewmodel.SessionManagment_registration;
import com.alifew.bcopay.viewmodel.Shop_registration;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Shop_registration_fragment extends Fragment implements View.OnClickListener {

    Shop_registration shop_registration;
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 1;
    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    int check = 0;
    String imgdata;
    final int IMAGE_REQUEST_CODE = 999;
    private Uri filepath;
    private Bitmap bitmap;
    ExtendedFloatingActionButton registerButton;
    ImageView backButton, profileImage;
    Dialog loaderDialog;

    TextInputEditText shopName, phone, password, repassword;
    OTP otp;

    String shop, owner, loc, cont, phn, pass, repass, type = "shopkeeper", task_type = "registration";


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        shop_registration = new ViewModelProvider(getActivity()).get(Shop_registration.class);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);

                imageselect();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.shop_registration_fragment, container, false);



        profileImage = (ImageView) view.findViewById(R.id.profile_imageID);
        shopName = (TextInputEditText) view.findViewById(R.id.shopNameText);
        phone = (TextInputEditText) view.findViewById(R.id.contactText);
        password = (TextInputEditText) view.findViewById(R.id.passwordText);
        repassword = (TextInputEditText) view.findViewById(R.id.rePasswordText);
        registerButton = view.findViewById(R.id.registrationButton);

        registerButton.setOnClickListener(this);


        loaderDialog = new Dialog(getActivity());
        loaderDialog.setContentView(R.layout.loader);
        loaderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loaderDialog.setCancelable(false);





        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registrationButton) {
            shop = shopName.getText().toString().trim();
            phn = phone.getText().toString().trim();
            pass = password.getText().toString().trim();
            repass = repassword.getText().toString().trim();
            owner = "";
            loc = "";


            validation(shop, owner, loc, phn, pass, repass);
        }
    }

    private void validation(String shop, String owner, String loc, String phn, String pass, String repass) {


        if (!(TextUtils.isEmpty(shop) || TextUtils.isEmpty(phn) || TextUtils.isEmpty(pass))) {

            int len = pass.length();

            if (len < 5 || len > 6) {

                String message = "";
                if (len < 5) {
                    message = "Min. Password length 5";

                } else if (len > 6) {
                    message = "Max. Password length 6";

                }
                ShowToast.errorToast(message, getActivity());

            } else {
                if (!TextUtils.isEmpty(repass)) {

                    if (pass.equals(repass)) {
                        if (phone_validation(phn)) {
                            if (check == 1) {
                                loaderDialog.show();
                                imgdata = imgToString(bitmap);
                                otp = new ViewModelProvider(this).get(OTP.class);
                                ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo info = manager.getActiveNetworkInfo();
                                if (info == null) {
                                    Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                                } else {
                                    shop_registration.getvarification(phn).observe(getActivity(), new Observer<String>() {
                                        @Override
                                        public void onChanged(String s) {
                                            if (!(s.equals("yes"))) {
                                                loaderDialog.dismiss();
                                                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                                            } else {
                                                Random r = new Random();
                                                int ran = r.nextInt(99999 - 10000 + 1) + 10000;
                                                String random_otp = String.valueOf(ran);
                                                otp.getStatus(phn, "ALife.Shop Registration OTP is-" + random_otp).observe(getActivity(), new Observer<OTP_response>() {
                                                    @Override
                                                    public void onChanged(OTP_response otp_response) {
                                                        if (otp_response.getStatus().equals("queued")) {
                                                            otp_activity(random_otp);
                                                        } else {

                                                        }
                                                    }
                                                });

                                            }
                                        }
                                    });

                                }


                            } else {
                                Toast.makeText(getActivity(), "Upload image", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            ShowToast.errorToast("Phone Number Not Valid", getActivity());
                        }
                    } else {
                        ShowToast.errorToast("Password Don't match", getActivity());

                    }
                } else if (TextUtils.isEmpty(repass)) {
                    ShowToast.errorToast("Empty Retype Password", getActivity());
                }

            }
        } else {

            if (TextUtils.isEmpty(shop)) {
                ShowToast.errorToast("Empty Shop Name", getActivity());

            } else if (TextUtils.isEmpty(phn)) {
                ShowToast.errorToast("Empty Phone", getActivity());

            } else if (TextUtils.isEmpty(pass)) {
                ShowToast.errorToast("Empty password", getActivity());

            }
        }
    }

    private boolean phone_validation(String phone) {
        if (phone.length() != 11) {
            return false;
        } else if (!(phone.charAt(0) == '0' && phone.charAt(1) == '1')) {
            return false;
        } else if (!(phone.charAt(2) == '3' || phone.charAt(2) == '4' || phone.charAt(2) == '7' || phone.charAt(2) == '8' || phone.charAt(2) == '9' || phone.charAt(2) == '5' || phone.charAt(2) == '6')) {
            return false;
        } else {
            return true;
        }

    }

    private void otp_activity(String otp) {
        RegistrationResponse registration;
        registration = new RegistrationResponse(shop, owner, phn, type, loc, pass, imgdata, otp, task_type);

        SessionManagment_registration sessionManagment_registration = new SessionManagment_registration(getActivity());
        sessionManagment_registration.saveSession(registration);

        alertControl();
        Intent intent = new Intent(getActivity(), Otp_validation_activity.class);
        startActivity(intent);
    }

    private void alertControl() {
        loaderDialog.show();

        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                loaderDialog.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 2000);
    }

    public void imageselect() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")) {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (items[i].equals("Gallery")) {
                    Intent intent = new Intent(new Intent(Intent.ACTION_GET_CONTENT));
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
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);
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

}