package com.ALife.alife.view.Shop;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.ALife.alife.R;
import com.ALife.alife.model.shop_profile_response;
import com.ALife.alife.model.update_shop_response;
import com.ALife.alife.viewmodel.SessionManagment;
import com.ALife.alife.viewmodel.Shop_profile;
import com.ALife.alife.viewmodel.Update_shop;
import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.gkemon.XMLtoPDF.model.SuccessResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.ALife.alife.R.layout.shop_profile_fragment;

public class Shop_profile_fragments extends Fragment {
    SwipeRefreshLayout refresh;
    ImageView shop_image, edit_Button, closeButton, shopImageEdit, printButton;
    TextView shop_ID, shop_name, shop_owner, shop_location, shop_contact, save_changesButton;
    Shop_profile shop_profile;
    TextInputEditText shopNameEdit, ownerNameEdit, locationEdit;
    int check = 0, final_check = 0;
    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    final int IMAGE_REQUEST_CODE = 999;
    String image;
    private Uri filepath;
    private Bitmap bitmap, bitmapPDF;
    int token = 0;
    String imgdata;
    int userId;
    LinearLayout mainLayout;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkConnection();

        SessionManagment sessionManagment = new SessionManagment(getActivity());
        userId = sessionManagment.getSession();
        shop_profile = new ViewModelProvider(getActivity()).get(Shop_profile.class);
        shop_profile.getData(String.valueOf(userId)).observe(getViewLifecycleOwner(), new Observer<shop_profile_response>() {
            @Override
            public void onChanged(shop_profile_response shop_profile_response) {
                image = shop_profile_response.getStore01e_image();

                Glide.with(getActivity()).load(shop_profile_response.getStore01e_image()).into(shop_image);
                shop_ID.setText(shop_profile_response.getStore01e_id());
                shop_name.setText(shop_profile_response.getStore01e_name());
                shop_owner.setText(shop_profile_response.getStore01e_owner());
                shop_location.setText(shop_profile_response.getStore01e_location());
                shop_contact.setText(shop_profile_response.getStore01e_phone());
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(shop_profile_fragment, container, false);
        checkConnection();

        shop_image = (ImageView) view.findViewById(R.id.shopImageID);
        shop_name = (TextView) view.findViewById(R.id.shopNameId);
        shop_owner = (TextView) view.findViewById(R.id.ownerNameID);
        shop_location = (TextView) view.findViewById(R.id.locationID);
        shop_contact = (TextView) view.findViewById(R.id.phoneID);
        edit_Button = (ImageView) view.findViewById(R.id.editButtonID);
        shop_ID = (TextView) view.findViewById(R.id.shopID);
        printButton = (ImageView) view.findViewById(R.id.printButtonID);

        mainLayout = view.findViewById(R.id.printAreaID);

        edit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog alert = new Dialog(getActivity());
                alert.setContentView(R.layout.shop_edit_profile_form);
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alert.setCancelable(false);
                alert.show();

                closeButton = (ImageView) alert.findViewById(R.id.closeID);
                shopNameEdit = (TextInputEditText) alert.findViewById(R.id.shopNameEditID);
                ownerNameEdit = (TextInputEditText) alert.findViewById(R.id.ownerNameEditID);
                locationEdit = (TextInputEditText) alert.findViewById(R.id.locationEditID);
                shopImageEdit = (ImageView) alert.findViewById(R.id.shopImageEditID);
                save_changesButton = (TextView) alert.findViewById(R.id.saveButton);

                Picasso.get().load(image).into(shopImageEdit);
                shopNameEdit.setText(shop_name.getText().toString());
                ownerNameEdit.setText(shop_owner.getText().toString());
                locationEdit.setText(shop_location.getText().toString());

                shopImageEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                        imageSelect();
                    }


                });

                save_changesButton.setOnClickListener(new View.OnClickListener() {
                    String shopName, ownerName, location;

                    @Override
                    public void onClick(View v) {
                        shopName = shopNameEdit.getText().toString().trim();
                        ownerName = ownerNameEdit.getText().toString().trim();
                        location = locationEdit.getText().toString().trim();

                        if (TextUtils.isEmpty(shopName) || TextUtils.isEmpty(ownerName) || TextUtils.isEmpty(location)) {
                            Toast toast = Toast.makeText(getActivity(), "Empty Field Found", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {
                            if (final_check == 0) {
                                token = 0;
                                imgdata = "xyz";

                            } else if (final_check == 1) {
                                token = 1;
                                imgdata = imgToString(bitmap);
                            }
                            Update_shop update_shop;
                            update_shop = new ViewModelProvider(getActivity()).get(Update_shop.class);

                            update_shop.getData(String.valueOf(userId), shopName, ownerName, location, imgdata, token).observe(getViewLifecycleOwner(), new Observer<update_shop_response>() {
                                @Override
                                public void onChanged(update_shop_response update_shop_response) {
                                    if (update_shop_response.getMessage().equals("Edited successfully")) {
                                        alert.cancel();
                                        Toast toast = Toast.makeText(getActivity(), update_shop_response.getMessage(), Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                        refreshFragment();

                                    } else {
                                        Toast toast = Toast.makeText(getActivity(), update_shop_response.getMessage(), Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                }
                            });
                        }


                    }
                });

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });


            }
        });

        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bitmapPDF = LoadBitmap(mainLayout, mainLayout.getWidth(), mainLayout.getHeight());

                createPDF();
            }
        });

        return view;
    }

    private void createPDF() {
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float width = displayMetrics.widthPixels;
        float height = displayMetrics.heightPixels;

        int convertWidth = (int) width, convertHeight = (int) height;

        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHeight, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmapPDF = Bitmap.createScaledBitmap(bitmapPDF, convertWidth, convertHeight, true);
        canvas.drawBitmap(bitmapPDF, 0, 0, null);
        pdfDocument.finishPage(page);

        //targetPDF

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        String targetPDF = "/sdcard/documents/" + "Alife" + currentDate + currentTime + ".pdf";
        File file;
        file = new File(targetPDF);

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
            //openPDF();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("errorxx", e.getMessage());

            pdfDocument.close();

            Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();

            // openPDF();
        }
    }

    private Bitmap LoadBitmap(View v, int width, int height) {
        Bitmap bitmapPDF = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapPDF);
        v.draw(canvas);

        return bitmapPDF;
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
                    shopImageEdit.setImageBitmap(bitmap);
                }


            } else if (requestCode == IMAGE_REQUEST_CODE) {
                filepath = data.getData();
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);

                    if (check == 1) {
                        check = 0;
                        final_check = 1;
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        shopImageEdit.setImageBitmap(bitmap);
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

    public void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
        //adapter.notifyDataSetChanged();
    }

    private void checkConnection() {
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
