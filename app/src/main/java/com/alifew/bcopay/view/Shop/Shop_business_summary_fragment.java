package com.alifew.bcopay.view.Shop;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alifew.bcopay.R;
import com.alifew.bcopay.Utils.ImageHelper;
import com.alifew.bcopay.adapter.Normal_sell_product_image_show_adapter;
import com.alifew.bcopay.adapter.Shop_business_summary_adapter;
import com.alifew.bcopay.adapter.Shop_business_summary_image_adapter;
import com.alifew.bcopay.model.add_business_summary_image_response;
import com.alifew.bcopay.model.add_shop_business_summary_response;
import com.alifew.bcopay.model.get_shop_business_summary_details_response;
import com.alifew.bcopay.model.get_shop_business_summary_response;
import com.alifew.bcopay.model.image;
import com.alifew.bcopay.viewmodel.Add_shop_business_summary;
import com.alifew.bcopay.viewmodel.Get_shop_business_summary;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Shop_business_summary_fragment extends Fragment implements Normal_sell_product_image_show_adapter.OnItemClickListener, Shop_business_summary_adapter.ItemClickListener, Shop_business_summary_image_adapter.OnItemClickListner1 {

    String shop_id;
    RecyclerView businessAccountView, multipleImageView;
    TextView investText;
    Get_shop_business_summary get_shop_business_summary;
    Add_shop_business_summary add_shop_business_summary;
    List<get_shop_business_summary_response> summaryList;
    List<image> summaryimageList;
    private Shop_business_summary_adapter adapter;
    private Shop_business_summary_image_adapter image_adapter;
    String imgdata;
    final int IMAGE_REQUEST_CODE = 999;
    private Uri filepath;
    private Bitmap bitmap;
    List<Bitmap> imageList = new ArrayList<>();
    private Normal_sell_product_image_show_adapter image_show_adapter;
    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    int check = 0;
    LinearLayout addImageButton;
    String myFormat = "yyyy-MM-dd";

    String dateCurrent;
    AppCompatButton submitButton;
    TextInputEditText descriptionText, creditInText, creditOutText;
    TextView totalCreditIn, totalCreditOut;
    LinearLayout summaryViewLayout;

    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    int page = 1, limit = 10, end = 0;
    LinearLayoutManager layoutManager;
    Dialog loaderDialog;

    public Shop_business_summary_fragment(String shop_id) {
        this.shop_id = shop_id;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        main();

    }

    private void pickTime(TextView timeText) {
        Dialog timeAlert = new Dialog(getActivity());
        timeAlert.setContentView(R.layout.time_picker);
        timeAlert.show();
        timeAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TimePicker timePicker = (TimePicker) timeAlert.findViewById(R.id.TimePickerID);

        TextView doneButton = (TextView) timeAlert.findViewById(R.id.doneButtonID);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour, minute;
                String am_pm;
                hour = timePicker.getCurrentHour();
                minute = timePicker.getCurrentMinute();

                if (hour > 12) {
                    am_pm = "PM";
                    hour = hour - 12;
                } else {
                    am_pm = "AM";
                }

                String HOUR = String.valueOf(hour), MINUTE = String.valueOf(minute);

                if (HOUR == "00") {
                    HOUR = "12";
                }

                if (HOUR.length() < 2) {
                    HOUR = "0" + HOUR;
                }
                if (MINUTE.length() < 2) {
                    MINUTE = "0" + MINUTE;
                }


                String time = HOUR + ":" + MINUTE + am_pm;
                timeAlert.dismiss();
                timeText.setText(time);

            }
        });

    }


    private void pickDate(TextView dateText) {
        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateCurrent = sdf.format(myCalendar.getTime());
                dateText.setText(dateCurrent);
            }

        };

        new DatePickerDialog(getActivity(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bundle bundle = data.getExtras();

                bitmap = (Bitmap) bundle.get("data");


                imageList.add(bitmap);
                image_show_adapter = new Normal_sell_product_image_show_adapter(imageList);
                image_show_adapter.setOnClickListener(Shop_business_summary_fragment.this::OnItemClick);
                multipleImageView.setAdapter(image_show_adapter);


            } else if (requestCode == IMAGE_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK) {

                    if (data.getClipData() != null) {
                        try {
                            for (int i = 0; i < data.getClipData().getItemCount(); i++) {


                                InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getClipData().getItemAt(i).getUri());
                                bitmap = BitmapFactory.decodeStream(inputStream);
                                imageList.add(bitmap);


                            }
                            image_show_adapter = new Normal_sell_product_image_show_adapter(imageList);
                            image_show_adapter.setOnClickListener(Shop_business_summary_fragment.this::OnItemClick);
                            multipleImageView.setAdapter(image_show_adapter);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else if (data.getData() != null) {
                        try {
                            InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                            bitmap = BitmapFactory.decodeStream(inputStream);
                            imageList.add(bitmap);
                            // descriptionText.setText(String.valueOf(imageList.size()));
                            image_show_adapter = new Normal_sell_product_image_show_adapter(imageList);
                            image_show_adapter.setOnClickListener(Shop_business_summary_fragment.this::OnItemClick);
                            multipleImageView.setAdapter(image_show_adapter);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }


                    }


                }
            }
        }

    }

    private void imageSelect() {
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
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);


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
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        String encodeimg = Base64.encodeToString(imgbytes, Base64.DEFAULT);
        return encodeimg;
    }

    private void main() {
        get_shop_business_summary = new ViewModelProvider(getActivity()).get(Get_shop_business_summary.class);
        add_shop_business_summary = new ViewModelProvider(getActivity()).get(Add_shop_business_summary.class);
        imageList = new ArrayList<>();
        check = 0;
        summaryList = new ArrayList<>();
        summaryimageList = new ArrayList<>();
        adapter = new Shop_business_summary_adapter(summaryList);
        adapter.setOnclickListener(Shop_business_summary_fragment.this::ItemClick);
        businessAccountView.setAdapter(adapter);
        summaryDetails();
        page = 1;
        summaryList(page, limit);


        /*get_shop_business_summary.getData(shop_id,page,limit).observe(getViewLifecycleOwner(), new Observer<List<get_shop_business_summary_response>>() {
            @Override
            public void onChanged(List<get_shop_business_summary_response> get_shop_business_summary_responses) {


                if (summaryList.size() > 0) {

                    summaryViewLayout.setVisibility(View.VISIBLE);
                    investText.setText(summaryList.get(summaryList.size() - 1).getTotal_invest());
                    Double total_income = 0.0;
                    Double total_cost = 0.0;
                    for (int i = 0; i < summaryList.size(); i++) {
                        total_income += Double.parseDouble(summaryList.get(i).getCredit_in());
                        total_cost += Double.parseDouble(summaryList.get(i).getCredit_out());
                    }
                    totalCreditIn.setText(new DecimalFormat("##.##").format(total_income));
                    totalCreditOut.setText(new DecimalFormat("##.##").format(total_cost));
                    adapter = new Shop_business_summary_adapter(summaryList);
                    adapter.setOnclickListener(Shop_business_summary_fragment.this::ItemClick);
                    businessAccountView.setAdapter(adapter);
                    businessAccountView.scrollToPosition(summaryList.size() - 1);
                } else {
                    investText.setText("0");
                    totalCreditIn.setText("0");
                    totalCreditOut.setText("0");
                    summaryViewLayout.setVisibility(View.GONE);
                }

            }
        });*/
    }

    private void summaryDetails() {
        get_shop_business_summary.getDetails(shop_id).observe(getViewLifecycleOwner(), new Observer<get_shop_business_summary_details_response>() {
            @Override
            public void onChanged(get_shop_business_summary_details_response get_shop_business_summary_details_response) {
                investText.setText(new DecimalFormat("##.##").format(get_shop_business_summary_details_response.getTotal_invest()));
                totalCreditIn.setText(new DecimalFormat("##.##").format(get_shop_business_summary_details_response.getCredit_in()));
                totalCreditOut.setText(new DecimalFormat("##.##").format(get_shop_business_summary_details_response.getCredit_out()));

                loaderDialog.dismiss();
            }
        });
    }

    private void summaryList(int Page, int Limit) {
        get_shop_business_summary.getData(shop_id, Page, Limit).observe(getViewLifecycleOwner(), new Observer<List<get_shop_business_summary_response>>() {
            @Override
            public void onChanged(List<get_shop_business_summary_response> get_shop_business_summary_responses) {
                // progressBar.setVisibility(View.GONE);
                if (get_shop_business_summary_responses.size() < Limit) {
                    end = 1;
                }
                summaryViewLayout.setVisibility(View.GONE);
                summaryViewLayout.setVisibility(View.VISIBLE);
                List<get_shop_business_summary_response> temp = new ArrayList<>();
                temp = summaryList;
                summaryList = new ArrayList<>();
                for (int i = get_shop_business_summary_responses.size() - 1; i >= 0; i--) {
                    summaryList.add(get_shop_business_summary_responses.get(i));
                }
                for (int i = 0; i < temp.size(); i++) {
                    summaryList.add(temp.get(i));
                }
                adapter = new Shop_business_summary_adapter(summaryList);
                adapter.setOnclickListener(Shop_business_summary_fragment.this::ItemClick);
                businessAccountView.setAdapter(adapter);


                businessAccountView.setNestedScrollingEnabled(false);
                //businessAccountView.scrollToPosition(summaryList.size() - 1);
                if (Page == 1) {
                    if (summaryList.size() > 0) {
                        businessAccountView.post(() -> {
                            float y = businessAccountView.getChildAt(summaryList.size() - 1).getY();
                            nestedScrollView.smoothScrollTo(0, (int) y);
                        });
                    }
                }
            }


        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_business_summary_fragment, container, false);
        loaderDialog = new Dialog(getActivity());
        loaderDialog.setContentView(R.layout.loader);
        loaderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loaderDialog.setCancelable(false);
        loaderDialog.show();

        summaryViewLayout = (LinearLayout) view.findViewById(R.id.summaryViewLayoutID);

        investText = (TextView) view.findViewById(R.id.totalInvestTextID);
        totalCreditIn = (TextView) view.findViewById(R.id.totalCreditInID);
        totalCreditOut = (TextView) view.findViewById(R.id.totalCreditOutID);

        addImageButton = (LinearLayout) view.findViewById(R.id.addImageID);
        descriptionText = (TextInputEditText) view.findViewById(R.id.descriptionTextID);
        creditInText = (TextInputEditText) view.findViewById(R.id.creditInTextID);
        creditOutText = (TextInputEditText) view.findViewById(R.id.creditOutTextID);

        TextInputLayout descriptionError = view.findViewById(R.id.descriptionErrorID);
        TextInputLayout creditInError = view.findViewById(R.id.creditInErrorID);
        TextInputLayout creditOutError = view.findViewById(R.id.creditOutErrorID);

        submitButton = (AppCompatButton) view.findViewById(R.id.submitButton);

        businessAccountView = (RecyclerView) view.findViewById(R.id.businessAccountViewID);
        multipleImageView = (RecyclerView) view.findViewById(R.id.multipleImageViewID);

        businessAccountView.setHasFixedSize(true);
        multipleImageView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        businessAccountView.setLayoutManager(layoutManager);
        multipleImageView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                imageSelect();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = descriptionText.getText().toString().trim();
                String creditIn = creditInText.getText().toString().trim();
                String creditOut = creditOutText.getText().toString().trim();
                String date = new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date());
                DateFormat dateFormat = new SimpleDateFormat("hh:mma");
                String time = dateFormat.format(new Date()).toString();

                descriptionError.setErrorEnabled(false);
                creditInError.setErrorEnabled(false);
                creditOutError.setErrorEnabled(false);

                if (TextUtils.isEmpty(description)) {

                    descriptionError.setError(" ");

                } else {
                    if (!(TextUtils.isEmpty(creditIn) && TextUtils.isEmpty(creditOut))) {
                        if (TextUtils.isEmpty(creditIn)) {

                            creditIn = "0";

                        } else if (TextUtils.isEmpty(creditOut)) {
                            creditOut = "0";
                        }
                        if (imageList.size() > 0) {
                            imgdata = imgToString(imageList.get(0));

                        } else {
                            imgdata = "";
                        }
                        loaderDialog.show();
                        add_shop_business_summary.getData(shop_id, description, creditIn, creditOut, date, time, imgdata).observe(getViewLifecycleOwner(), new Observer<add_shop_business_summary_response>() {
                            @Override
                            public void onChanged(add_shop_business_summary_response add_shop_business_summary_response) {
                                if (!add_shop_business_summary_response.getMessage().equals("-1")) {
                                    // imageView.setImageBitmap(null);
                                    //imageView.setImageResource(R.drawable.grey_image);

                                    if (imageList.size() > 0) {
                                        for (int i = 0; i < imageList.size(); i++) {
                                            if (check != 1) {
                                                String image = imgToString(imageList.get(i));
                                                add_shop_business_summary.get_response(add_shop_business_summary_response.getMessage(), image).observe(getViewLifecycleOwner(), new Observer<add_business_summary_image_response>() {
                                                    @Override
                                                    public void onChanged(add_business_summary_image_response add_business_summary_image_response) {
                                                        if (add_business_summary_image_response.getMessage().equals("Image added successfully")) {
                                                            check = 0;
                                                        } else {
                                                            check = 1;
                                                        }
                                                    }
                                                });
                                            } else {
                                                break;
                                            }
                                        }
                                        if (check != 1) {
                                            imageList.clear();
                                            image_show_adapter.notifyDataSetChanged();
                                            descriptionText.setText("");
                                            creditInText.setText("");
                                            creditOutText.setText("");
                                            loaderDialog.dismiss();
                                            main();
                                        }
                                    } else {
                                        descriptionText.setText("");
                                        creditInText.setText("");
                                        creditOutText.setText("");
                                        loaderDialog.dismiss();
                                        main();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Something Error..", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(getActivity(), "Fill correctly", Toast.LENGTH_SHORT).show();
                        creditInError.setError(" ");
                        creditOutError.setError(" ");

                    }

                }

            }
        });

        //progressBar = (ProgressBar) view.findViewById(R.id.progressBarID);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == 0) {
                    //progressBar.setVisibility(View.VISIBLE);
                    if (end == 0) {
                        page++;
                        summaryList(page, limit);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void OnItemClick(int position) {
        Bitmap image = imageList.get(position);

        Dialog imageDialog = new Dialog(getActivity());
        imageDialog.setContentView(R.layout.multiple_image_show_alert);
        imageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        imageDialog.show();
        imageDialog.setCancelable(false);

        ImageView closeButton = imageDialog.findViewById(R.id.closeID);
        RelativeLayout hideLayout = (RelativeLayout) imageDialog.findViewById(R.id.hideLayoutID);
        ImageView individualImage = (ImageView) imageDialog.findViewById(R.id.individualImageID);
        ImageView deleteImage = (ImageView) imageDialog.findViewById(R.id.individualDeleteID);
        //hideLayout.setVisibility(View.INVISIBLE);
        individualImage.setImageBitmap(image);
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageList.remove(position);
                image_show_adapter.notifyDataSetChanged();
                imageDialog.dismiss();
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDialog.dismiss();
            }
        });
    }

    @Override
    public void ItemClick(int position) {
        get_shop_business_summary_response response = summaryList.get(position);
        summaryimageList = response.getSummary_image();
        Dialog itemAlert = new Dialog(getActivity());
        itemAlert.setContentView(R.layout.shop_business_summary_item_alert);
        itemAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        itemAlert.setCancelable(false);
        itemAlert.show();

        ImageView closeButton = itemAlert.findViewById(R.id.closeID);
        TextView descriptionText = itemAlert.findViewById(R.id.descriptionTextID);
        TextView creditInText = itemAlert.findViewById(R.id.creditInID);
        TextView creditOutText = itemAlert.findViewById(R.id.creditOutID);
        TextView totalInvestText = itemAlert.findViewById(R.id.totalInvestTextID);
        TextView dateText = itemAlert.findViewById(R.id.dateID);
        TextView timeText = itemAlert.findViewById(R.id.timeID);

        RecyclerView multipleImageView = itemAlert.findViewById(R.id.multipleImageViewID);
        multipleImageView.setHasFixedSize(true);
        //multipleImageView.setLayoutManager(new LinearLayoutManager(itemAlert.getContext()));
        multipleImageView.setLayoutManager(new LinearLayoutManager(itemAlert.getContext(), LinearLayoutManager.HORIZONTAL, false));

        descriptionText.setText(response.getDescription());
        creditInText.setText(response.getCredit_in());
        creditOutText.setText(response.getCredit_out());
        totalInvestText.setText(response.getTotal_invest());
        dateText.setText(response.getDate());
        timeText.setText(response.getTime());
        image_adapter = new Shop_business_summary_image_adapter(getActivity(), summaryimageList);
        image_adapter.setOnClickListener(Shop_business_summary_fragment.this::OnItemClick1);
        multipleImageView.setAdapter(image_adapter);


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemAlert.dismiss();
            }
        });

    }

    @Override
    public void OnItemClick1(int position) {
        image item = summaryimageList.get(position);
        String image = item.getImage();

        Dialog imageDialog = new Dialog(getActivity());
        imageDialog.setContentView(R.layout.multiple_image_show_alert);
        imageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        imageDialog.show();
        imageDialog.setCancelable(false);

        ImageView individualImage = (ImageView) imageDialog.findViewById(R.id.individualImageID);
        RelativeLayout hideLayout = (RelativeLayout) imageDialog.findViewById(R.id.hideLayoutID);

        ImageView closeButton = (ImageView) imageDialog.findViewById(R.id.closeID);
        ImageView deleteImage = (ImageView) imageDialog.findViewById(R.id.individualDeleteID);

        ImageHelper.imageLoader(getActivity(), individualImage, image);
        hideLayout.setVisibility(View.INVISIBLE);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDialog.cancel();
            }
        });

    }
}