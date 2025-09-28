package com.alifew.bcopay.view.Shop;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.alifew.bcopay.R;
import com.alifew.bcopay.adapter.Slider.ShopSliderAdapter;
import com.alifew.bcopay.model.CommonResponse;
import com.alifew.bcopay.model.slider.SliderResponse;
import com.alifew.bcopay.session.SessionManagement;
import com.alifew.bcopay.viewmodel.banner.SliderViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ShopSliderFragment extends Fragment implements ShopSliderAdapter.SwitchChangeListener {

    RecyclerView sliderView;
    SliderViewModel sliderViewModel;
    String shopID;
    SessionManagement sessionManagement;
    Dialog loader;
    List<SliderResponse> sliderList;
    ShopSliderAdapter sliderAdapter;
    ExtendedFloatingActionButton addButton;
    int check = 0, final_check = 0;
    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    final int IMAGE_REQUEST_CODE = 999;
    private Uri filepath;
    private Bitmap bitmap;
    ImageView sliderImageView;
    Dialog addSliderAlert;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_slider, container, false);

        initView(view);

        loader.show();
        loadSlider();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadBanner();
                addSliderFunc();
            }
        });

        return view;
    }

    private void addSliderFunc() {
        addSliderAlert.show();

        Window window = addSliderAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        MaterialButton uploadButton = addSliderAlert.findViewById(R.id.uploadButton);
        ImageView closeButton = addSliderAlert.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> addSliderAlert.dismiss());
        uploadButton.setOnClickListener(v -> {

            if (final_check == 1){
                uploadSliderFunction();
            }else {
                Toast.makeText(getActivity(), "No image found", Toast.LENGTH_SHORT).show();
            }

        });

        sliderImageView.setOnClickListener(v -> {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
            imageSelect();
        });
    }

    private void uploadSliderFunction() {
        loader.show();
        sliderViewModel.uploadSlider(shopID, imgToString(bitmap)).observe(getViewLifecycleOwner(), new Observer<CommonResponse>() {
            @Override
            public void onChanged(CommonResponse commonResponse) {
                loader.dismiss();
                if (commonResponse.message.equals("Add successfully")) {
                    addSliderAlert.dismiss();
                    Toast.makeText(getActivity(), commonResponse.message, Toast.LENGTH_SHORT).show();
                    loadSlider();
                } else {
                    Toast.makeText(getActivity(), commonResponse.message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void imageSelect() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Image");
        builder.setItems(items, (dialog, i) -> {
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
        });
        builder.show();
    }

    private String imgToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    private void loadSlider() {
        sliderViewModel.getBannerList(shopID).observe(getViewLifecycleOwner(), sliderResponses -> {
            loader.dismiss();
            try {
                sliderList = new ArrayList<>();
                sliderList = sliderResponses;
                sliderAdapter = new ShopSliderAdapter(sliderList);
                sliderAdapter.setOnClickListener(ShopSliderFragment.this);
                sliderView.setAdapter(sliderAdapter);
            } catch (Exception e) {
                Log.d("dataxx", "exception: " + e.getMessage());
            }

        });

    }

    private void initView(View view) {
        sliderView = view.findViewById(R.id.sliderView);
        sliderView.setHasFixedSize(true);
        sliderView.setLayoutManager(new LinearLayoutManager(getActivity()));

        sliderViewModel = new ViewModelProvider(getActivity()).get(SliderViewModel.class);

        sessionManagement = new SessionManagement(getActivity());
        shopID = String.valueOf(sessionManagement.getUserID());

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        addButton = view.findViewById(R.id.addButton);

        addSliderAlert = new Dialog(getActivity());
        addSliderAlert.setContentView(R.layout.add_slider_alert);
        addSliderAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addSliderAlert.setCancelable(false);
        sliderImageView = addSliderAlert.findViewById(R.id.sliderImageView);
    }


    @Override
    public void OnSwitchChange(int position, boolean isChecked) {
        SliderResponse response = sliderList.get(position);
        String status = isChecked ? "active" : "inactive";
        String sliderID = response.id;


        sliderViewModel.updateBannerStatus(shopID, sliderID, status).observe(getViewLifecycleOwner(), new Observer<CommonResponse>() {
            @Override
            public void onChanged(CommonResponse commonResponse) {

                if (commonResponse.message.equals("edited successfully")) {
                    Toast.makeText(getActivity(), commonResponse.message, Toast.LENGTH_SHORT).show();
                    loadSlider();
                } else {
                    Toast.makeText(getActivity(), getActivity().getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {

            if (requestCode == CAMERA_REQUEST) {
                Bundle bundle = data.getExtras();
                if (bundle != null && check == 1) {
                    bitmap = (Bitmap) bundle.get("data");
                    if (bitmap != null) {
                        check = 0;
                        final_check = 1;
                        sliderImageView.setImageBitmap(bitmap);
                    } else {
                        Toast.makeText(getActivity(), "Failed to capture image", Toast.LENGTH_SHORT).show();
                    }
                }

            } else if (requestCode == IMAGE_REQUEST_CODE) {
                filepath = data.getData();
                if (filepath != null && check == 1) {
                    try {
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        if (bitmap != null) {
                            check = 0;
                            final_check = 1;
                            sliderImageView.setImageBitmap(bitmap);
                        } else {
                            Toast.makeText(getActivity(), "Failed to load image", Toast.LENGTH_SHORT).show();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Image file not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        } else {
            // Optional: notify user image selection was cancelled
            Toast.makeText(getActivity(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

}