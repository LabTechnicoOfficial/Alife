package com.alifew.alifeworld.view.Customer;

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
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.Utils.ImageHelper;
import com.alifew.alifeworld.model.customer_profile_response;
import com.alifew.alifeworld.model.update_customer_response;
import com.alifew.alifeworld.viewmodel.Customer_profile;
import com.alifew.alifeworld.session.SessionManagement;
import com.alifew.alifeworld.viewmodel.Update_customer;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.alifew.alifeworld.R.layout.customer_profile_fragments;

public class Customer_profile_fragments extends Fragment {
    SwipeRefreshLayout refresh;
    TextView name, phone, address, saveChange, pointsText;
    ImageView editButton, closeButton;
    com.mikhaellopez.circularimageview.CircularImageView image;
    de.hdodenhof.circleimageview.CircleImageView imageEdit;
    Customer_profile customer_profile;
    private int customer_id;
    String customerImage;
    TextInputEditText nameText, locationText;
    TextInputLayout nameError, locationError;
    private Uri filepath;
    private Bitmap bitmap;
    int token = 0;
    String imgdata;
    int check = 0, final_check = 0;
    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    final int IMAGE_REQUEST_CODE = 999;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkConnection();
        SessionManagement sessionManagement = new SessionManagement(getActivity());
        customer_id = sessionManagement.getUserID();
        customer_profile = new ViewModelProvider(getActivity()).get(Customer_profile.class);

        customer_profile.getData(String.valueOf(customer_id)).observe(getViewLifecycleOwner(), new Observer<customer_profile_response>() {
            @Override
            public void onChanged(customer_profile_response customer_profile_response) {
                customerImage = customer_profile_response.customer01r_image;
                ImageHelper.imageLoader(getActivity(), image, customer_profile_response.getCustomer01r_image());
                name.setText(customer_profile_response.getCustomer01r_name());
                phone.setText(customer_profile_response.getCustomer01r_phone());
                address.setText(customer_profile_response.getCustomer01r_address());
                pointsText.setText(customer_profile_response.balance_point);

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog alert = new Dialog(getActivity());
                alert.setContentView(R.layout.customer_profile_edit_fragments);
                alert.show();
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                closeButton = (ImageView) alert.findViewById(R.id.closeID);
                nameText = (TextInputEditText) alert.findViewById(R.id.shopNameText);
                nameError = (TextInputLayout) alert.findViewById(R.id.nameError);
                locationText = (TextInputEditText) alert.findViewById(R.id.locationTextID);
                locationError = (TextInputLayout) alert.findViewById(R.id.locationErrorID);
                imageEdit = (de.hdodenhof.circleimageview.CircleImageView) alert.findViewById(R.id.customerImageID);
                saveChange = (TextView) alert.findViewById(R.id.save_ID);

                nameText.setText(name.getText().toString().trim());
                locationText.setText(address.getText().toString().trim());

                ImageHelper.imageLoader(getActivity(), imageEdit, customerImage);

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });

                imageEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                        imageSelect();
                    }
                });

                saveChange.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String name = nameText.getText().toString().trim();
                        String location = locationText.getText().toString().trim();

                        nameError.setErrorEnabled(false);
                        locationError.setErrorEnabled(false);
                        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(location)) {
                            if (TextUtils.isEmpty(name)) {
                                nameError.setError(" ");
                            } else if (TextUtils.isEmpty(location))
                                locationError.setError(" ");
                        } else {
                            //edit code

                            Dialog alertCustom = new Dialog(getActivity());
                            alertCustom.setContentView(R.layout.loader);
                            alertCustom.show();
                            alertCustom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            if (final_check == 0) {
                                token = 0;
                                imgdata = "xyz";

                            } else if (final_check == 1) {
                                token = 1;
                                imgdata = imgToString(bitmap);
                            }
                            Update_customer update_customer;
                            update_customer = new ViewModelProvider(getActivity()).get(Update_customer.class);
                            update_customer.getData(String.valueOf(customer_id), name, location, imgdata, token).observe(getViewLifecycleOwner(), new Observer<update_customer_response>() {
                                @Override
                                public void onChanged(update_customer_response update_customer_response) {
                                    if (update_customer_response.getMessage().equals("Edited successfully")) {
                                        alertCustom.cancel();
                                        alert.cancel();
                                        /*Toast toast = Toast.makeText(getActivity(), update_customer_response.getMessage(), Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();*/
                                        refreshFragment();

                                    } else {
                                        alertCustom.cancel();
                                        Toast toast = Toast.makeText(getActivity(), update_customer_response.getMessage(), Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                }
                            });
                        }
                    }
                });


            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bundle bundle = data.getExtras();

                if (check == 1) {
                    bitmap = (Bitmap) bundle.get("data");
                    check = 0;
                    final_check = 1;
                    imageEdit.setImageBitmap(bitmap);
                }


            } else if (requestCode == IMAGE_REQUEST_CODE) {
                filepath = data.getData();
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);

                    if (check == 1) {
                        check = 0;
                        final_check = 1;
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        imageEdit.setImageBitmap(bitmap);
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(customer_profile_fragments, container, false);
        checkConnection();

        name = (TextView) view.findViewById(R.id.NameId);
        phone = (TextView) view.findViewById(R.id.phoneID);
        address = (TextView) view.findViewById(R.id.locationID);
        editButton = (ImageView) view.findViewById(R.id.editButton);
        image = (com.mikhaellopez.circularimageview.CircularImageView) view.findViewById(R.id.profile_imageID);
        pointsText = view.findViewById(R.id.pointsText);

        return view;
    }

    public void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
        //adapter.notifyDataSetChanged();
    }

    public void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        Dialog networkAlert = new Dialog(getActivity());
        networkAlert.setContentView(R.layout.network_alert);
        networkAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView connectButton = (TextView) networkAlert.findViewById(R.id.connectButtonID);
        if (info == null) {
            networkAlert.show();
            connectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    networkAlert.dismiss();

                    refreshFragment();
                }
            });

        }
    }
}
