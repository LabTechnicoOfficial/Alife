package com.alifew.alife.view.Shop;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.Utils.ImageHelper;
import com.alifew.alife.adapter.Normal_sell_details_image_adapter;
import com.alifew.alife.adapter.Normal_sell_product_image_show_adapter;
import com.alifew.alife.adapter.Systemetic_sell_details_adapter;
import com.alifew.alife.adapter.shop_customer_due_list_adapter;
import com.alifew.alife.model.add_normal_product_image_response;
import com.alifew.alife.model.add_normal_sell_response;
import com.alifew.alife.model.add_payment_transaction_response;
import com.alifew.alife.model.add_product_sell_response;
import com.alifew.alife.model.add_sell_payment_cash_response;
import com.alifew.alife.model.get_shop_customer_due_list_response;
import com.alifew.alife.model.image;
import com.alifew.alife.model.local_sell.get_local_sell_details_response;
import com.alifew.alife.model.normal_sell_details_response;
import com.alifew.alife.model.push_notification_response;
import com.alifew.alife.model.shop_profile_response;
import com.alifew.alife.model.systemetic_sell_details_response;
import com.alifew.alife.viewmodel.AddMessagetoHistory;
import com.alifew.alife.viewmodel.Get_shop_customer_due_list;
import com.alifew.alife.viewmodel.Local_sell.Get_local_sell;
import com.alifew.alife.viewmodel.Normal_sell;
import com.alifew.alife.viewmodel.OTP;
import com.alifew.alife.viewmodel.Product_sell;
import com.alifew.alife.viewmodel.Product_sell_payment;
import com.alifew.alife.viewmodel.Push_notification;
import com.alifew.alife.viewmodel.Sell_details;
import com.alifew.alife.viewmodel.Shop_profile;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.alifew.alife.R.layout.shop_customer_details_fragments;

public class Shop_customer_details_fragments extends Fragment implements shop_customer_due_list_adapter.OnDueClickListener, Normal_sell_product_image_show_adapter.OnItemClickListener, Normal_sell_details_image_adapter.ImageClickListener {

    TextInputEditText totalPriceText, paidPriceText, descriptionText;
    ImageView customerImage;
    TextView customerName, customerContact, customerLocation, totalDue, total_due_title;
    TextView dateText, timeText;
    TextView customerMainName, customerMainContact;
    List<get_shop_customer_due_list_response> transactionList;
    List<get_shop_customer_due_list_response> convertList;
    Get_shop_customer_due_list get_shop_customer_due_list;
    Sell_details sell_details;
    Push_notification push_notification;
    String shop_id;
    String customer_id;
    String customer_name;
    String customer_address;
    String customer_contact;
    String customer_image;
    String total_customer_due;
    String sell_id;
    String description;
    String totalPrice, paidPrice;
    shop_customer_due_list_adapter adapter;
    Systemetic_sell_details_adapter sell_details_adapter;
    LinearLayout addImageButton;
    RecyclerView dueListView, multipleImageView;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    AppCompatButton sellButton;
    Product_sell product_sell;
    Normal_sell normal_sell;
    Get_local_sell get_local_sell;
    Shop_profile shop_profile;
    OTP otp;
    AddMessagetoHistory addMessagetoHistory;
    Product_sell_payment product_sell_payment;
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 1;
    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    int check = 0, final_check = 0;
    String imgdata;
    final int IMAGE_REQUEST_CODE = 999;
    private Uri filepath;
    private Bitmap bitmap;
    List<Bitmap> imageList = new ArrayList<>();
    private Normal_sell_product_image_show_adapter image_show_adapter;
    LinearLayout productSellButton, productsSellLayout;
    Boolean productSellState = true;
    ImageView downImage, upImage;
    private List<image> normal_sell_image;
    private Normal_sell_details_image_adapter normal_sell_adapter;

    String dateCurrent, myFormat = "yyyy-MM-dd";
    String date, time;
    Boolean detailsState = true;
    ImageView detailsDown, detailsUp;
    LinearLayout mainLayout, detailsLayoutButton, detailsLayout, nameLayout;
    Dialog loaderDialog;
    int page = 1, limit = 20, end = 0;
    private String message, messagetocustomer, messagetoshop;
    Bitmap bitmapPDF;
    public Shop_customer_details_fragments(String id, String customer_id, String customer_name, String customer_address, String customer_contact, String customer_image, String total_customer_due) {
        this.shop_id = id;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.customer_address = customer_address;
        this.customer_contact = customer_contact;
        this.customer_image = customer_image;
        this.total_customer_due = total_customer_due;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main();
    }

    private void main() {
        checkConnection();
        Double all_due = Double.parseDouble(total_customer_due);
        if (all_due >= 0.0) {
            total_due_title.setText(R.string.total_due);
            totalDue.setText(new DecimalFormat("##.##").format(all_due));
        } else if (all_due < 0.0) {
            all_due = all_due * (-1);
            total_due_title.setText(R.string.total_deposit);
            totalDue.setText(new DecimalFormat("##.##").format(all_due));
        }


        imageList = new ArrayList<>();
        totalPriceText.setText("");
        paidPriceText.setText("");
        descriptionText.setText("");
        transactionList = new ArrayList<>();
        page = 1;
        limit = 20;
        end = 0;
        getDuelist(page, limit);
        addImageButton.setBackgroundDrawable(null);
        get_shop_customer_due_list = new ViewModelProvider(getActivity()).get(Get_shop_customer_due_list.class);
        push_notification = new ViewModelProvider(getActivity()).get(Push_notification.class);
        //get_shop_customer_due_list.getData(shop_id, customer_id).observe(getViewLifecycleOwner(), new Observer<List<get_shop_customer_due_list_response>>() {
        //   @Override
        // public void onChanged(List<get_shop_customer_due_list_response> get_shop_customer_due_list_responses) {
               /* transactionList = get_shop_customer_due_list_responses;
                convertList = new ArrayList<>();
                Double all_due=0.0;
                if(transactionList.size()>0)
                {
                    all_due = Double.parseDouble(transactionList.get(0).getTotal_due());
                }
                //Double all_due = Double.parseDouble(transactionList.get(0).getTotal_due());
                if (all_due >= 0.0) {
                    total_due_title.setText(R.string.total_due);
                    totalDue.setText(new DecimalFormat("##.##").format(all_due));
                } else if (all_due < 0.0) {
                    all_due = all_due * (-1);
                    total_due_title.setText(R.string.total_deposit);
                    totalDue.setText(new DecimalFormat("##.##").format(all_due));
                }
               /* for (int i = 0; i < transactionList.size(); i++) {
                    convertList.add(i, transactionList.get(transactionList.size() - 1 - i));
                    if (i == transactionList.size() - 1) {
                        Double all_due = Double.parseDouble(transactionList.get(i).getTotal_due());
                        if (all_due >= 0.0) {
                            total_due_title.setText("মোট বাকিঃ");
                            totalDue.setText(new DecimalFormat("##.##").format(all_due));
                        } else if (all_due < 0.0) {
                            all_due = all_due * (-1);
                            total_due_title.setText("মোট জমাঃ");
                            totalDue.setText(new DecimalFormat("##.##").format(all_due));
                        }
                   }
                }*/
        //adapter = new shop_customer_due_list_adapter(transactionList);
        //adapter.SetOnClickListener(Shop_customer_details_fragments.this::OnDueLick);
        //dueListView.setAdapter(adapter);


        //  }
        //  });

        dateCurrent = new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date());
        //dateText.setText(dateCurrent);
        dateText.setText("Select Date");

        DateFormat dateFormat = new SimpleDateFormat("hh:mma");
        String timeString = dateFormat.format(new Date()).toString();
        timeText.setText(timeString);
        timeText.setText("Select Time");
    }

    public void getDuelist(int Page, int Limit) {
        get_shop_customer_due_list = new ViewModelProvider(getActivity()).get(Get_shop_customer_due_list.class);
        push_notification = new ViewModelProvider(getActivity()).get(Push_notification.class);
        get_shop_customer_due_list.getData(shop_id, customer_id, customer_contact, Page, Limit).observe(getViewLifecycleOwner(), new Observer<List<get_shop_customer_due_list_response>>() {
            @Override
            public void onChanged(List<get_shop_customer_due_list_response> get_shop_customer_due_list_responses) {
                progressBar.setVisibility(View.GONE);

                if (get_shop_customer_due_list_responses.size() < Limit) {
                    end = 1;
                }
                if (Page == 1) {
                    convertList = new ArrayList<>();
                    transactionList = new ArrayList<>();
                    adapter = new shop_customer_due_list_adapter(transactionList);
                    adapter.SetOnClickListener(Shop_customer_details_fragments.this::OnDueLick);
                    dueListView.setAdapter(adapter);
                    transactionList = get_shop_customer_due_list_responses;
                    Double all_due = 0.0;
                    if (transactionList.size() > 0) {
                        all_due = Double.parseDouble(transactionList.get(0).getTotal_due());
                    }
                    //Double all_due = Double.parseDouble(transactionList.get(0).getTotal_due());
                    if (all_due >= 0.0) {
                        total_due_title.setText(R.string.total_due);
                        totalDue.setText(new DecimalFormat("##.##").format(all_due));
                    } else if (all_due < 0.0) {
                        all_due = all_due * (-1);
                        total_due_title.setText(R.string.total_deposit);
                        totalDue.setText(new DecimalFormat("##.##").format(all_due));
                    }

                    adapter = new shop_customer_due_list_adapter(transactionList);
                    adapter.SetOnClickListener(Shop_customer_details_fragments.this::OnDueLick);
                    dueListView.setAdapter(adapter);
                } else {
                    for (int i = 0; i < get_shop_customer_due_list_responses.size(); i++) {
                        transactionList.add(get_shop_customer_due_list_responses.get(i));
                    }
                    adapter = new shop_customer_due_list_adapter(transactionList);
                    adapter.SetOnClickListener(Shop_customer_details_fragments.this::OnDueLick);
                    dueListView.setAdapter(adapter);
                }


            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(shop_customer_details_fragments, container, false);
        checkConnection();

        customerImage = (ImageView) view.findViewById(R.id.customerImageID);
        downImage = (ImageView) view.findViewById(R.id.downImageID);
        upImage = (ImageView) view.findViewById(R.id.upImageID);
        detailsDown = (ImageView) view.findViewById(R.id.detailsDownID);
        detailsUp = (ImageView) view.findViewById(R.id.detailsUpID);

        customerName = (TextView) view.findViewById(R.id.customerNameID);
        customerContact = (TextView) view.findViewById(R.id.customerContactID);
        customerLocation = (TextView) view.findViewById(R.id.customerAddressID);
        totalDue = (TextView) view.findViewById(R.id.totalDueID);
        dateText = (TextView) view.findViewById(R.id.dateTextID);
        timeText = (TextView) view.findViewById(R.id.timeTextID);
        total_due_title = (TextView) view.findViewById(R.id.totalDueTilte);
        customerMainName = (TextView) view.findViewById(R.id.customerMainNameID);
        customerMainContact = (TextView) view.findViewById(R.id.customerMainContactID);
        dueListView = (RecyclerView) view.findViewById(R.id.dueViewID);
        multipleImageView = (RecyclerView) view.findViewById(R.id.multipleImageViewID);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        sellButton = (AppCompatButton) view.findViewById(R.id.customSellButtonID);

        totalPriceText = (TextInputEditText) view.findViewById(R.id.totalPriceTextID);
        paidPriceText = (TextInputEditText) view.findViewById(R.id.paidPriceTextID);
        descriptionText = (TextInputEditText) view.findViewById(R.id.descriptionTextID);

        TextInputLayout totalPriceError = (TextInputLayout) view.findViewById(R.id.totalPriceErrorID);
        TextInputLayout paidPriceError = (TextInputLayout) view.findViewById(R.id.paidPriceErrorID);

        addImageButton = (LinearLayout) view.findViewById(R.id.addImageID);
        detailsLayoutButton = (LinearLayout) view.findViewById(R.id.detailsLayoutButtonID);
        mainLayout = (LinearLayout) view.findViewById(R.id.mainLayoutID);
        detailsLayout = (LinearLayout) view.findViewById(R.id.detailsLayoutID);
        productSellButton = (LinearLayout) view.findViewById(R.id.productsSellButtonID);
        productsSellLayout = (LinearLayout) view.findViewById(R.id.productsSellLayoutID);
        nameLayout = (LinearLayout) view.findViewById(R.id.nameLayoutID);

        dueListView.setHasFixedSize(true);
        dueListView.setLayoutManager(new LinearLayoutManager(getContext()));

        multipleImageView.setHasFixedSize(true);
        multipleImageView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        if (!customer_id.equals("0")) {
            ImageHelper.imageLoader(getActivity(), customerImage, customer_image);
        }
        customerName.setText(customer_name);
        customerContact.setText(customer_contact);
        customerLocation.setText(customer_address);
        totalDue.setText(total_customer_due);
        customerMainName.setText(customer_name);
        customerMainContact.setText(customer_contact);

        loaderDialog = new Dialog(getActivity());
        loaderDialog.setContentView(R.layout.loader);
        loaderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loaderDialog.setCancelable(false);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                //offersButton.show();
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    if (end == 0) {

                        progressBar.setVisibility(View.VISIBLE);
                        page++;
                        getDuelist(page, limit);
                    }

                }
            }
        });

        detailsLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailsState == true) {
                    detailsDown.setVisibility(View.GONE);
                    mainLayout.setVisibility(View.GONE);
                    nameLayout.setVisibility(View.GONE);
                    detailsUp.setVisibility(View.VISIBLE);
                    detailsLayout.setVisibility(View.VISIBLE);

                    detailsState = false;

                } else {
                    detailsUp.setVisibility(View.GONE);
                    detailsLayout.setVisibility(View.GONE);
                    nameLayout.setVisibility(View.VISIBLE);
                    detailsDown.setVisibility(View.VISIBLE);
                    mainLayout.setVisibility(View.VISIBLE);

                    detailsState = true;
                }
            }
        });


        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                image_select();
            }
        });

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(dateText);
            }
        });

        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime(timeText);
            }
        });

        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = manager.getActiveNetworkInfo();
                if (info == null) {
                    Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();

                } else {
                    totalPrice = totalPriceText.getText().toString().trim();
                    paidPrice = paidPriceText.getText().toString().trim();
                    description = descriptionText.getText().toString().trim();

                    // String date;
                    date = dateText.getText().toString().trim();
                    time = timeText.getText().toString().trim();

                    if (date.equals("Select Date")) {
                        date = new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date());
                        date = "Select Date";

                    }
                    if (time.equals("Select Time")) {

                        DateFormat dateFormat = new SimpleDateFormat("hh:mma");
                        time = dateFormat.format(new Date()).toString();
                    }
                    totalPriceError.setErrorEnabled(false);
                    paidPriceError.setErrorEnabled(false);

                    if (!(TextUtils.isEmpty(totalPrice) && TextUtils.isEmpty(paidPrice))) {
                        loaderDialog.show();
                        if (TextUtils.isEmpty(totalPrice)) {

                            totalPrice = "0";
                        } else if (TextUtils.isEmpty(paidPrice)) {

                            paidPrice = "0";
                        }
                        if (totalPrice.equals(".")) {
                            totalPrice = "0";
                        }
                        if (paidPrice.equals(".")) {
                            paidPrice = "0";
                        }
                        if (TextUtils.isEmpty(description)) {
                            description = "";
                        }

                        if (imageList.size() <= 5) {
                            product_sell = new ViewModelProvider(getActivity()).get(Product_sell.class);
                            product_sell_payment = new ViewModelProvider(getActivity()).get(Product_sell_payment.class);
                            normal_sell = new ViewModelProvider(getActivity()).get(Normal_sell.class);
                            //Toast.makeText(getActivity(),date,Toast.LENGTH_SHORT).show();

                            product_sell.sell(shop_id, customer_id, customer_name, customer_contact, totalPrice, "0","0", "0", "normally", date).observe(getViewLifecycleOwner(), new Observer<add_product_sell_response>() {
                                @Override
                                public void onChanged(add_product_sell_response add_product_sell_response) {
                                    if (!(add_product_sell_response.getSell_id().equals("failed") || add_product_sell_response.equals(null))) {
                                        sell_id = add_product_sell_response.getSell_id();

                                        product_sell_payment.get_cash(sell_id, "manual", paidPrice, date).observe(getViewLifecycleOwner(), new Observer<add_sell_payment_cash_response>() {
                                            @Override
                                            public void onChanged(add_sell_payment_cash_response add_sell_payment_cash_response) {
                                                if (add_sell_payment_cash_response.getMessage().equals("yess")) {


                                                    product_sell_payment.get_transaction(sell_id, shop_id, customer_id, customer_contact, "due", String.valueOf(Double.parseDouble(totalPrice) - Double.parseDouble(paidPrice)), "", date).observe(getViewLifecycleOwner(), new Observer<add_payment_transaction_response>() {
                                                        @Override
                                                        public void onChanged(add_payment_transaction_response add_payment_transaction_response) {
                                                            if (add_payment_transaction_response.getMessage().equals("yess")) {
                                                                //Log.d("ttoal_due: ", add_payment_transaction_response.getTotal_due());

                                                                normal_sell.getData(sell_id, description).observe(getViewLifecycleOwner(), new Observer<add_normal_sell_response>() {
                                                                    @Override
                                                                    public void onChanged(add_normal_sell_response add_normal_sell_response) {
                                                                        if (add_normal_sell_response.getMessage().equals("yess")) {

                                                                            if (imageList.size() > 0) {
                                                                                for (int i = 0; i < imageList.size(); i++) {
                                                                                    check = i;
                                                                                    imgdata = imgToString(imageList.get(i));
                                                                                    normal_sell.add_image(add_normal_sell_response.getId(), imgdata).observe(getViewLifecycleOwner(), new Observer<add_normal_product_image_response>() {
                                                                                        @Override
                                                                                        public void onChanged(add_normal_product_image_response add_normal_product_image_response) {
                                                                                            if (check == imageList.size() - 1) {
                                                                                                push_notification.sell_notification_customer(shop_id, customer_id, totalPrice, String.valueOf(Double.parseDouble(totalPrice) - Double.parseDouble(paidPrice))).observe(getViewLifecycleOwner(), new Observer<push_notification_response>() {
                                                                                                    @Override
                                                                                                    public void onChanged(push_notification_response push_notification_response) {
                                                                                                        if (push_notification_response.getMessage().equals("success")) {


                                                                                                            push_notification.sell_notification_shop(shop_id, customer_contact, totalPrice, String.valueOf(Double.parseDouble(totalPrice) - Double.parseDouble(paidPrice))).observe(getViewLifecycleOwner(), new Observer<com.alifew.alife.model.push_notification_response>() {
                                                                                                                @Override
                                                                                                                public void onChanged(com.alifew.alife.model.push_notification_response push_notification_response) {
                                                                                                                    if (push_notification_response.getMessage().equals("success")) {
                                                                                                                        imageList.clear();
                                                                                                                        image_show_adapter.notifyDataSetChanged();
                                                                                                                        loaderDialog.dismiss();
                                                                                                                        success_alert();
                                                                                                                        main();

                                                                                                                    }
                                                                                                                }
                                                                                                            });
                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                            }
                                                                                        }
                                                                                    });

                                                                                }
                                                                            } else {
                                                                                push_notification.sell_notification_customer(shop_id, customer_id, totalPrice, String.valueOf(Double.parseDouble(totalPrice) - Double.parseDouble(paidPrice))).observe(getViewLifecycleOwner(), new Observer<push_notification_response>() {
                                                                                    @Override
                                                                                    public void onChanged(push_notification_response push_notification_response) {
                                                                                        if (push_notification_response.getMessage().equals("success")) {


                                                                                            push_notification.sell_notification_shop(shop_id, customer_contact, totalPrice, String.valueOf(Double.parseDouble(totalPrice) - Double.parseDouble(paidPrice))).observe(getViewLifecycleOwner(), new Observer<com.alifew.alife.model.push_notification_response>() {
                                                                                                @Override
                                                                                                public void onChanged(com.alifew.alife.model.push_notification_response push_notification_response) {
                                                                                                    if (push_notification_response.getMessage().equals("success")) {
                                                                                                        loaderDialog.dismiss();
                                                                                                        success_alert();
                                                                                                        main();

                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    }
                                                                                });
                                                                                //  main();
                                                                            }

                                                                        }
                                                                    }
                                                                });

                                                            } else {
                                                                loaderDialog.dismiss();
                                                                Toast.makeText(getActivity(), add_payment_transaction_response.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    loaderDialog.dismiss();
                                                    Toast.makeText(getActivity(), add_sell_payment_cash_response.getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                                    } else {
                                        loaderDialog.dismiss();
                                        Toast.makeText(getActivity(), "Something Wrong!!!", Toast.LENGTH_SHORT).show();

                                        main();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "Maximum image number 5", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Fill correctly", Toast.LENGTH_SHORT).show();
                        totalPriceError.setError(" ");
                        paidPriceError.setError(" ");
                    }
                }
            }
        });

        productSellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productSellState) {
                    downImage.setVisibility(View.GONE);
                    upImage.setVisibility(View.VISIBLE);
                    productsSellLayout.setVisibility(View.VISIBLE);
                    productSellState = false;
                } else{
                    upImage.setVisibility(View.GONE);
                    productsSellLayout.setVisibility(View.GONE);
                    downImage.setVisibility(View.VISIBLE);
                    productSellState = true;
                }
            }
        });

        return view;
    }

    private void success_alert() {
        Dialog successDialog = new Dialog(getActivity());
        successDialog.setContentView(R.layout.successful_loader);
        successDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        successDialog.setCancelable(false);
        successDialog.show();
        Window window = successDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

      //  ImageView closeButton = successDialog.findViewById(R.id.closeButtonID);
        LinearLayout mainLayout = successDialog.findViewById(R.id.mainLayoutID);
        TextView shopNameText = successDialog.findViewById(R.id.nameText);
        TextView productDetailsTextPDF = successDialog.findViewById(R.id.productDetailsTextID);
        TextView totalPriceTextPDF = successDialog.findViewById(R.id.totalPriceTextID);
        TextView paidPriceTextPDF = successDialog.findViewById(R.id.paidPriceTextID);
        TextView phoneTextPDF = successDialog.findViewById(R.id.contactText);
        TextView dateText = successDialog.findViewById(R.id.dateTextID);
        LinearLayout savePDFButton = successDialog.findViewById(R.id.savePDFButtonID);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        dateText.setText(currentDate);

        shop_profile = new ViewModelProvider(getActivity()).get(Shop_profile.class);
        shop_profile.getData(String.valueOf(shop_id)).observe(getViewLifecycleOwner(), new Observer<shop_profile_response>() {
            @Override
            public void onChanged(shop_profile_response shop_profile_response) {
                shopNameText.setText(shop_profile_response.getStore01e_name());
            }
        });

        productDetailsTextPDF.setText(descriptionText.getText().toString().trim());
        totalPriceTextPDF.setText(totalPriceText.getText().toString().trim());
        paidPriceTextPDF.setText(paidPriceText.getText().toString().trim());
        phoneTextPDF.setText(customer_contact);

        savePDFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmapPDF = LoadBitmap(mainLayout, mainLayout.getWidth(), mainLayout.getHeight());

                createPDF();
            }
        });
        AppCompatButton okButton = (AppCompatButton) successDialog.findViewById(R.id.okButton);
        TextView titleText = (TextView) successDialog.findViewById(R.id.titleText);

        titleText.setText("Sell Successful");
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successDialog.dismiss();
            }
        });

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

        String targetPDF = "/sdcard/Download/" + "Alife" + currentDate + currentTime + ".pdf";
        File file;
        file = new File(targetPDF);

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(getActivity(), "Successfully saved at Download", Toast.LENGTH_SHORT).show();
            //openPDF();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("errorxx", e.getMessage());

            pdfDocument.close();

            Toast.makeText(getActivity(), "Successfully saved at Download", Toast.LENGTH_SHORT).show();

            // openPDF();
        }
    }

    private Bitmap LoadBitmap(View v, int width, int height) {
        Bitmap bitmapPDF = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapPDF);
        v.draw(canvas);

        return bitmapPDF;
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bundle bundle = data.getExtras();

                bitmap = (Bitmap) bundle.get("data");

                imageList.add(bitmap);
                image_show_adapter = new Normal_sell_product_image_show_adapter(imageList);
                image_show_adapter.setOnClickListener(Shop_customer_details_fragments.this::OnItemClick);
                multipleImageView.setAdapter(image_show_adapter);


            } else if (requestCode == 999) {
                if (resultCode == Activity.RESULT_OK) {

                    if (data.getClipData() != null) {
                        try {
                            for (int i = 0; i < data.getClipData().getItemCount(); i++) {


                                InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getClipData().getItemAt(i).getUri());
                                bitmap = BitmapFactory.decodeStream(inputStream);
                                imageList.add(bitmap);


                            }
                            image_show_adapter = new Normal_sell_product_image_show_adapter(imageList);
                            image_show_adapter.setOnClickListener(Shop_customer_details_fragments.this::OnItemClick);
                            multipleImageView.setAdapter(image_show_adapter);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else if (data.getData() != null) {
                        try {
                            InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                            bitmap = BitmapFactory.decodeStream(inputStream);
                            imageList.add(bitmap);
                            //descriptionText.setText(String.valueOf(imageList.size()));
                            image_show_adapter = new Normal_sell_product_image_show_adapter(imageList);
                            image_show_adapter.setOnClickListener(Shop_customer_details_fragments.this::OnItemClick);
                            multipleImageView.setAdapter(image_show_adapter);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }


                    }


                }
            }

        }
    }

    private void image_select() {
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
                    check = 1;
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
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        String encodeimg = Base64.encodeToString(imgbytes, Base64.DEFAULT);
        return encodeimg;
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

    @Override
    public void OnDueLick(int position) {
        get_shop_customer_due_list_response due = transactionList.get(position);

        String sell_id = due.getSell_id();
        String sell_type = due.getSell_type();
        if (sell_type.equals("systemetic")) {
            Dialog alertCustom = new Dialog(getActivity());
            alertCustom.setContentView(R.layout.sell_customer_history_alert);
            alertCustom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertCustom.setCancelable(false);
            alertCustom.show();

            RecyclerView recyclerView = (RecyclerView) alertCustom.findViewById(R.id.productViewID);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            ImageView closeButton = (ImageView) alertCustom.findViewById(R.id.closeID);

            sell_details = new ViewModelProvider(getActivity()).get(Sell_details.class);
            sell_details.systemetic_sell_details(sell_id).observe(getViewLifecycleOwner(), new Observer<List<systemetic_sell_details_response>>() {
                @Override
                public void onChanged(List<systemetic_sell_details_response> systemetic_sell_details_responses) {
                    sell_details_adapter = new Systemetic_sell_details_adapter(systemetic_sell_details_responses);
                    recyclerView.setAdapter(sell_details_adapter);
                }
            });
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertCustom.dismiss();
                }
            });
        } else if(sell_type.equals("normally")) {
            //Toast.makeText(getActivity(), sell_type, Toast.LENGTH_SHORT).show();
            Dialog alertCustom = new Dialog(getActivity());
            alertCustom.setContentView(R.layout.normal_sell_details_alert);
            alertCustom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertCustom.setCancelable(false);
            alertCustom.show();

            TextView descriptionText = (TextView) alertCustom.findViewById(R.id.descriptionTextID);
            RecyclerView multipleImages = (RecyclerView) alertCustom.findViewById(R.id.multipleImageViewID);
            ImageView closeButton = (ImageView) alertCustom.findViewById(R.id.closeID);

            multipleImages.setHasFixedSize(true);
            multipleImages.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

            sell_details = new ViewModelProvider(getActivity()).get(Sell_details.class);
            sell_details.normal_sell_details(sell_id).observe(getViewLifecycleOwner(), new Observer<normal_sell_details_response>() {
                @Override
                public void onChanged(normal_sell_details_response normal_sell_details_response) {
                    descriptionText.setText(normal_sell_details_response.getDescription());
                    normal_sell_image = normal_sell_details_response.getImage();
                    normal_sell_adapter = new Normal_sell_details_image_adapter(normal_sell_image);
                    normal_sell_adapter.setOnClickListener(Shop_customer_details_fragments.this::ImageClick);
                    multipleImages.setAdapter(normal_sell_adapter);
                }
            });
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertCustom.dismiss();
                }
            });
        }else if(sell_type.equals("local"))
        {
            Dialog alertCustom = new Dialog(getActivity());
            alertCustom.setContentView(R.layout.normal_sell_details_alert);
            alertCustom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertCustom.setCancelable(false);
            alertCustom.show();

            TextView descriptionText = (TextView) alertCustom.findViewById(R.id.descriptionTextID);
            RecyclerView multipleImages = (RecyclerView) alertCustom.findViewById(R.id.multipleImageViewID);
            ImageView closeButton = (ImageView) alertCustom.findViewById(R.id.closeID);

            multipleImages.setHasFixedSize(true);
            multipleImages.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

            get_local_sell=new ViewModelProvider(getActivity()).get(Get_local_sell.class);
            get_local_sell.getDetails(sell_id).observe(getViewLifecycleOwner(), new Observer<get_local_sell_details_response>() {
                @Override
                public void onChanged(get_local_sell_details_response get_local_sell_details_response) {
                    descriptionText.setText(get_local_sell_details_response.getDescription());
                    normal_sell_image = get_local_sell_details_response.getImage();
                    normal_sell_adapter = new Normal_sell_details_image_adapter(normal_sell_image);
                    normal_sell_adapter.setOnClickListener(Shop_customer_details_fragments.this::ImageClick);
                    multipleImages.setAdapter(normal_sell_adapter);
                }
            });
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertCustom.dismiss();
                }
            });
        }
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
    public void ImageClick(int position) {
        image item = normal_sell_image.get(position);
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