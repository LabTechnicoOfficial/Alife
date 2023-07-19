package com.alifew.alife.view.Shop;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alifew.alife.R;
import com.alifew.alife.model.fetch_shop_admin_response;
import com.alifew.alife.viewmodel.Fetch_shop_adminList;
import com.alifew.alife.session.SessionManagement;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static com.alifew.alife.R.layout.shop_add_admin_fragment;

public class Shop_add_admin_fragment extends Fragment {

    com.mikhaellopez.circularimageview.CircularImageView adminImage;
    ImageView backButton;
    TextInputEditText adminNameText, phoneText, passwordText, rePasswordText;
    TextInputLayout adminNameError, phoneError, passwordError, rePasswordError;
    private FragmentManager fragmentManager;
    AppCompatButton nextButton;
    private List<fetch_shop_admin_response> adminList;
    String shop_id;

    public Shop_add_admin_fragment(String shop_id, List<fetch_shop_admin_response> adminList) {
        this.shop_id = shop_id;
        this.adminList = adminList;
    }

    int check = 0, final_check = 0;
    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    final int IMAGE_REQUEST_CODE = 999;
    private Uri filepath;
    private Bitmap bitmap;
    String imgdata;
    int equal = 0;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        update_adminList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(shop_add_admin_fragment, container, false);
        fragmentManager = getFragmentManager();

        adminImage = (com.mikhaellopez.circularimageview.CircularImageView) view.findViewById(R.id.admin_imageID);
        adminNameText = (TextInputEditText) view.findViewById(R.id.adminNameID);
        phoneText = (TextInputEditText) view.findViewById(R.id.phoneTextID);
        passwordText = (TextInputEditText) view.findViewById(R.id.passwordTextID);
        rePasswordText = (TextInputEditText) view.findViewById(R.id.rePasswordTextID);

        adminNameError = (TextInputLayout) view.findViewById(R.id.adminNameErrorID);
        phoneError = (TextInputLayout) view.findViewById(R.id.phoneErrorID);
        passwordError = (TextInputLayout) view.findViewById(R.id.passwordErrorID);
        rePasswordError = (TextInputLayout) view.findViewById(R.id.rePasswordErrorID);

        backButton = (ImageView) view.findViewById(R.id.backButtonID);
        nextButton = (AppCompatButton) view.findViewById(R.id.nextButtonID);

        adminImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                imageSelect();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adminName = adminNameText.getText().toString().trim();
                String adminPhone = phoneText.getText().toString().trim();
                String adminPassword = passwordText.getText().toString().trim();
                String adminRePassword = rePasswordText.getText().toString().trim();

                adminNameError.setErrorEnabled(false);
                phoneError.setErrorEnabled(false);
                passwordError.setErrorEnabled(false);
                rePasswordError.setErrorEnabled(false);

                if (TextUtils.isEmpty(adminName) || TextUtils.isEmpty(adminPhone) || TextUtils.isEmpty(adminPassword) || TextUtils.isEmpty(adminRePassword)) {
                    if (TextUtils.isEmpty(adminName)) {
                        adminNameError.setError(" ");
                    } else if (TextUtils.isEmpty(adminPhone)) {
                        phoneError.setError(" ");
                    } else if (TextUtils.isEmpty(adminPassword)) {
                        passwordError.setError(" ");
                    } else if (TextUtils.isEmpty(adminRePassword)) {
                        rePasswordError.setError(" ");
                    }
                } else {
                    if (adminPassword.length() < 6) {
                        passwordError.setError(" ");
                        Toast toast = Toast.makeText(getActivity(), "Password Too Short", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        if (adminPassword.equals(adminRePassword)) {
                            if (final_check == 1) {
                                equal = 0;
                                for (int i = 0; i < adminList.size(); i++) {
                                    if (adminPhone.equals(adminList.get(i).getAgent_phone()) && adminPassword.equals(adminList.get(i).getAgent_password())) {
                                        equal = 1;
                                        break;
                                    }
                                }
                                if (equal != 1) {
                                    imgdata = imgToString(bitmap);

                                    fragmentManager.beginTransaction().setCustomAnimations(
                                            R.anim.slide_in,  // enter
                                            R.anim.fade_out,  // exit
                                            R.anim.fade_in,   // popEnter
                                            R.anim.slide_out  // popExit
                                    ).replace(R.id.frame_container, new Shop_admin_categories_fragment(shop_id, imgdata, adminName, adminPhone, adminPassword, adminList)).addToBackStack(null).commit();

                                } else {
                                    Toast toast = Toast.makeText(getActivity(), "phone and password already used.", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    equal = 0;
                                }
                            } else {
                                Toast toast = Toast.makeText(getActivity(), "Upload Admin Image", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        } else {
                            Toast toast = Toast.makeText(getActivity(), "Unmatched Password", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_admin_fragments(shop_id)).addToBackStack(null).commit();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bundle bundle = data.getExtras();

                if (check == 1) {
                    bitmap = (Bitmap) bundle.get("data");
                    check = 0;
                    final_check = 1;
                    adminImage.setImageBitmap(bitmap);
                }


            } else if (requestCode == IMAGE_REQUEST_CODE) {
                filepath = data.getData();
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);

                    if (check == 1) {
                        check = 0;
                        final_check = 1;
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        adminImage.setImageBitmap(bitmap);
                    }

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

    private void imageSelect() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")) {
                    check = 1;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (items[i].equals("Gallery")) {
                    check = 1;
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
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    public void update_adminList() {
        SessionManagement sessionManagement = new SessionManagement(getActivity());
        String shop_id = String.valueOf(sessionManagement.getSession());
        Fetch_shop_adminList fetch_shop_admin = new ViewModelProvider(getActivity()).get(Fetch_shop_adminList.class);

        fetch_shop_admin.getData(shop_id).observe(getViewLifecycleOwner(), new Observer<List<fetch_shop_admin_response>>() {
            @Override
            public void onChanged(List<fetch_shop_admin_response> fetch_shop_admin_responses) {
                adminList = fetch_shop_admin_responses;

            }
        });
    }
}
