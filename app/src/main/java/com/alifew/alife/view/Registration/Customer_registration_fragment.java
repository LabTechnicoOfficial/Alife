package com.alifew.alife.view.Registration;

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

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alifew.alife.R;
import com.alifew.alife.Utils.ShowToast;
import com.alifew.alife.model.OTP_response;
import com.alifew.alife.model.registration;
import com.alifew.alife.view.OTP.Otp_validation_activity;
import com.alifew.alife.viewmodel.Customer_registration;
import com.alifew.alife.viewmodel.OTP;
import com.alifew.alife.viewmodel.SessionManagment_registration;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Customer_registration_fragment extends Fragment implements View.OnClickListener {
    String token = "x";
    FirebaseAuth mAuth;
    DatabaseReference databaseReference, registerUsers;
    Customer_registration customer_registration;
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 1;
    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    int check = 0;
    String imgdata;
    final int IMAGE_REQUEST_CODE = 999;
    private Uri filepath;
    private Bitmap bitmap;
    ImageView backButton, addimage;
    Dialog loaderDialog;
    TextInputEditText cusName, address, phone, password, repassword;
    ExtendedFloatingActionButton registerButton;

    String type = "customer", cname, addr, phn, pass, repass, task_type = "registration";
    OTP otp;
    String s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.customer_registration_fragment, container, false);

        customer_registration = new ViewModelProvider(this).get(Customer_registration.class);

        addimage = (ImageView) view.findViewById(R.id.profile_image);

        cusName = (TextInputEditText) view.findViewById(R.id.nameText);
        address = (TextInputEditText) view.findViewById(R.id.addressText);
        phone = (TextInputEditText) view.findViewById(R.id.contactText);
        password = (TextInputEditText) view.findViewById(R.id.passwordText);
        repassword = (TextInputEditText) view.findViewById(R.id.rePasswordText);

        registerButton =  view.findViewById(R.id.registrationButton);

        loaderDialog = new Dialog(getActivity());
        loaderDialog.setContentView(R.layout.loader);
        loaderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loaderDialog.setCancelable(false);

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                imageselect();
            }
        });

        registerButton.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registerButton) {
            cname = cusName.getText().toString().trim();
            addr = address.getText().toString().trim();
            phn = phone.getText().toString().trim();
            pass = password.getText().toString().trim();
            repass = repassword.getText().toString().trim();

            validation(cname, addr, phn, pass, repass);
        }
    }

    private void validation(String cname, String addr, String phn, String pass, String repass) {
        if (!(TextUtils.isEmpty(cname) || TextUtils.isEmpty(addr) || TextUtils.isEmpty(phn) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass))) {

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
                if (pass.equals(repass)) {


                    if (phone_validation(phn) == true) {
                        if (check == 1) {
                            loaderDialog.show();
                            imgdata = imgToString(bitmap);
                            otp = new ViewModelProvider(getActivity()).get(OTP.class);

                            ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo info = manager.getActiveNetworkInfo();
                            if (info == null) {
                                Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                            } else {
                                customer_registration.getvarification(phn).observe(getActivity(), new Observer<String>() {
                                    @Override
                                    public void onChanged(String s) {
                                        if (!(s.equals("yes"))) {
                                            loaderDialog.cancel();
                                            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                                        } else {
                                            //dialog.show();
                                            //registration();
                                            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                                            Random r = new Random();
                                            int ran = r.nextInt(99999 - 10000 + 1) + 10000;
                                            String random_otp = String.valueOf(ran);
                                            otp.getStatus(phn, "ALife.Customer Registration OTP is-" + random_otp).observe(getActivity(), new Observer<OTP_response>() {
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
            }
        } else {
            String message = "";
            if (TextUtils.isEmpty(cname)) {
                message = "empty name";

            } else if (TextUtils.isEmpty(addr)) {
                message = "empty address";

            } else if (TextUtils.isEmpty(phn)) {
                message = "empty phone";

            } else if (TextUtils.isEmpty(pass)) {
                message = "empty password";

            } else if (TextUtils.isEmpty(repass)) {
                message = "empty re-password";

            }

            ShowToast.errorToast(message, getActivity());
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
        registration registration;
        // Log.d("phonexxx",phn);
        registration = new registration(cname, "xxx", phn, type, addr, pass, imgdata, otp, task_type);

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bundle bundle = data.getExtras();
                bitmap = (Bitmap) bundle.get("data");
                check = 1;
                addimage.setImageBitmap(bitmap);

            } else if (requestCode == IMAGE_REQUEST_CODE) {
                filepath = data.getData();
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    addimage.setImageBitmap(bitmap);
                    check = 1;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void imageselect() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

    private String imgToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        String encodeimg = Base64.encodeToString(imgbytes, Base64.DEFAULT);
        return encodeimg;
    }

    /*public void registration() {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            token = task.getResult().getToken();
                            customer_registration.getmessage(cname, addr, phn, pass, imgdata, token).observe(getActivity(), new Observer<String>() {
                                @Override
                                public void onChanged(String s) {

                                    if (!(s.equals("Registration failed"))) {
                                        dialog.dismiss();

                                        Toast.makeText(getActivity(), "Registration complete successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getActivity(), LoginActivity.class));
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(getActivity(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }*/
}