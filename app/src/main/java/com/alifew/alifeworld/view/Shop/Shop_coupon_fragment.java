package com.alifew.alifeworld.view.Shop;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.adapter.coupon.Shop_coupon_adapter;
import com.alifew.alifeworld.model.cupon.add_response;
import com.alifew.alifeworld.model.cupon.cupon_response;
import com.alifew.alifeworld.model.cupon.CustomerFor_cupon_response;
import com.alifew.alifeworld.model.cupon.edit_delete_response;
import com.alifew.alifeworld.model.cupon.notify_response;
import com.alifew.alifeworld.session.SessionManagement;
import com.alifew.alifeworld.viewmodel.cuponViewmodel.CouponViewModel;
import com.alifew.alifeworld.viewmodel.cuponViewmodel.CustomerFor_cupon;
import com.alifew.alifeworld.viewmodel.cuponViewmodel.Edit_delete_cupon_package;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Shop_coupon_fragment extends Fragment implements Shop_coupon_adapter.onItemNotifyListener, Shop_coupon_adapter.onItemClickListener, Shop_coupon_adapter.onItemDeleteListener {

    String shopID;
    ExtendedFloatingActionButton addCouponButton;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    int page = 1, limit = 10, end = 0;
    RecyclerView couponView;
    CouponViewModel couponViewModel;
    Edit_delete_cupon_package edit_delete_cupon_package;
    private List<cupon_response> couponList;
    private Shop_coupon_adapter shop_coupon_adapter;
    String myFormat = "yyyy-MM-dd", dateCurrent;
    TextView durationText;
    Dialog loader;
    List<CustomerFor_cupon_response> customerList;
    String cupon_available;
    SessionManagement sessionManagement;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        main();

        coupon_data();

        addCouponButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog addCouponAlert = new Dialog(getActivity());
                addCouponAlert.setContentView(R.layout.shop_coupon_add_alert);
                addCouponAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                addCouponAlert.setCancelable(false);
                addCouponAlert.show();


                Window window = addCouponAlert.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
                wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(wlp);

                ImageView closeButton = addCouponAlert.findViewById(R.id.closeButton);
                AppCompatButton addButton = addCouponAlert.findViewById(R.id.addButton);
                TextView creationDateText = addCouponAlert.findViewById(R.id.creationDateTextID);
                TextView endDateText = addCouponAlert.findViewById(R.id.dateTextID);
                TextInputEditText nameText = addCouponAlert.findViewById(R.id.shopNameText);
                TextInputEditText descriptionText = addCouponAlert.findViewById(R.id.descriptionTextID);
                durationText = addCouponAlert.findViewById(R.id.durationTextID);

                creationDateText.setText("Select Create Date");
                endDateText.setText("Select End Date");
                //creationDateText.setText(new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date()));

                creationDateText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickCreateDate(creationDateText, endDateText);
                    }
                });

                endDateText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickEndDate(creationDateText, endDateText);
                    }
                });

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String couponName = nameText.getText().toString().trim();
                        String description = descriptionText.getText().toString().trim();

                        if (TextUtils.isEmpty(couponName) || creationDateText.getText().toString().trim().equals("Select Create Date") || endDateText.getText().toString().trim().equals("Select End Date") || TextUtils.isEmpty(durationText.getText().toString().trim())) {
                            Toast.makeText(getActivity(), "Give data correctly", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(getActivity(), "F", Toast.LENGTH_SHORT).show();
                            if (TextUtils.isEmpty(description)) {
                                description = " ";
                            }

                            loader.show();
                            couponViewModel.addCupon(shopID, couponName, durationText.getText().toString().trim(), creationDateText.getText().toString().trim(), endDateText.getText().toString().trim(), description).observe(getViewLifecycleOwner(), new Observer<add_response>() {
                                @Override
                                public void onChanged(add_response add_response) {
                                    String message = add_response.getMessage();
                                    loader.dismiss();
                                    //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                                    if (message.equals("success")) {
                                        addCouponAlert.dismiss();

                                        Toast.makeText(getActivity(), "Coupon added", Toast.LENGTH_SHORT).show();
                                        coupon_data();
                                    } else {
                                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addCouponAlert.dismiss();
                    }
                });
            }
        });
    }

    private void coupon_data() {
        couponViewModel.getCupon(shopID).observe(getViewLifecycleOwner(), new Observer<List<cupon_response>>() {
            @Override
            public void onChanged(List<cupon_response> cupon_responses) {
                couponList = new ArrayList<>();
                couponList = cupon_responses;
                shop_coupon_adapter = new Shop_coupon_adapter(couponList);
                shop_coupon_adapter.setOnClickListener(Shop_coupon_fragment.this::OnItemClick, Shop_coupon_fragment.this::OnItemDelete, Shop_coupon_fragment.this::OnItemNotify);
                couponView.setAdapter(shop_coupon_adapter);
            }
        });
    }

    private void main() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_coupon_fragment, container, false);

        initView(view);


        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > oldScrollY) {
                    addCouponButton.hide();
                } else {
                    addCouponButton.show();
                }
                //mFloatingActionButton.show();
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (end == 0) {
                        progressBar.setVisibility(View.VISIBLE);
                        page++;
                        //filter(page, limit);
                    }
                }
            }
        });

        return view;
    }

    private void initView(View view) {
        sessionManagement = new SessionManagement(getActivity());
        shopID = String.valueOf(sessionManagement.getSession());
        edit_delete_cupon_package = new ViewModelProvider(this).get(Edit_delete_cupon_package.class);
        couponViewModel = new ViewModelProvider(this).get(CouponViewModel.class);

        addCouponButton = (ExtendedFloatingActionButton) view.findViewById(R.id.addCouponButtonID);
        couponView = (RecyclerView) view.findViewById(R.id.couponViewID);
        couponView.setHasFixedSize(true);
        couponView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);

        dateCurrent = new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date());
        //dateText.setText(dateCurrent);

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);
    }

    private void pickCreateDate(TextView creationDateText, TextView endDateText) {

        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateCurrent = sdf.format(myCalendar.getTime());
                creationDateText.setText(dateCurrent);

                if (endDateText.getText().toString().trim().equals("Select End Date")) {
                    Toast.makeText(getActivity(), "Select End Date", Toast.LENGTH_SHORT).show();
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                    Date startDate, endDate;
                    long numberOfDays = 0;
                    try {
                        startDate = dateFormat.parse(creationDateText.getText().toString().trim());
                        endDate = dateFormat.parse(endDateText.getText().toString().trim());

                        long timeDiff = endDate.getTime() - startDate.getTime();
                        int daysDifference = (int) (timeDiff / (1000 * 60 * 60 * 24));
                        //Toast.makeText(getActivity(), String.valueOf(daysDifference), Toast.LENGTH_SHORT).show();
                        if (daysDifference > 0) {
                            durationText.setText(String.valueOf(daysDifference));
                        } else {
                            Toast.makeText(getActivity(), "Select end date correctly", Toast.LENGTH_SHORT).show();
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

        };

        new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void pickEndDate(TextView creationDateText, TextView endDateText) {

        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateCurrent = sdf.format(myCalendar.getTime());
                endDateText.setText(dateCurrent);

                if (creationDateText.getText().toString().trim().equals("Select Create Date")) {
                    Toast.makeText(getActivity(), "Select Create Date", Toast.LENGTH_SHORT).show();
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                    Date startDate, endDate;
                    long numberOfDays = 0;
                    try {
                        startDate = dateFormat.parse(creationDateText.getText().toString().trim());
                        endDate = dateFormat.parse(endDateText.getText().toString().trim());

                        long timeDiff = endDate.getTime() - startDate.getTime();
                        int daysDifference = (int) (timeDiff / (1000 * 60 * 60 * 24));
                        //Toast.makeText(getActivity(), String.valueOf(daysDifference), Toast.LENGTH_SHORT).show();
                        if (daysDifference > 0) {
                            durationText.setText(String.valueOf(daysDifference));
                        } else {
                            Toast.makeText(getActivity(), "Select end date correctly", Toast.LENGTH_SHORT).show();
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

        };

        new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void OnItemClick(int position) {
        cupon_response response = couponList.get(position);
        //Toast.makeText(getActivity(), response.getCupon_name(), Toast.LENGTH_SHORT).show();

        String couponID = response.getId();
        String date1 = response.getCreation_date();
        String date2 = response.getEnd_date();
        CustomerFor_cupon customerFor_cupon;
        customerFor_cupon = new ViewModelProvider(this).get(CustomerFor_cupon.class);
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = (String) android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new java.util.Date());

        String targetdate = response.getEnd_date() + " 23:59:59";


        customerList = new ArrayList<>();
        customerFor_cupon.getData(shopID, date1, date2).observe(getViewLifecycleOwner(), new Observer<List<CustomerFor_cupon_response>>() {
            @Override
            public void onChanged(List<CustomerFor_cupon_response> customerFor_cupon_responses) {
                customerList = customerFor_cupon_responses;
                Collections.sort(customerList, new Comparator<CustomerFor_cupon_response>() {

                    @Override
                    public int compare(CustomerFor_cupon_response lhs, CustomerFor_cupon_response rhs) {
                        // TODO Auto-generated method stub

                        try {
                            Double v1 = (Double.parseDouble(lhs.getSell_amount()));
                            Double v3 = (Double.parseDouble(rhs.getSell_amount()));
                            return v3.compareTo(v1);

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            return 0;
                        }
                    }
                });
                try {
                    Date currentDate = myFormat.parse(currentTime);
                    Date targetDate = myFormat.parse(targetdate);
                    if (currentDate.getTime() > targetDate.getTime()) {
                        cupon_available = "0";
                    } else {
                        cupon_available = "1";
                    }

                } catch (Exception e) {

                }
                //Toast.makeText(getActivity(), couponID, Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_coupon_packages_fragment(shopID, couponID, customerList, cupon_available, response.getCupon_name())).addToBackStack(null).commit();


            }
        });

    }

    @Override
    public void OnItemDelete(int position) {

        cupon_response response = couponList.get(position);
        String couponID = response.getId();

        Dialog deleteAlert = new Dialog(getActivity());
        deleteAlert.setContentView(R.layout.confirm_alert);
        deleteAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteAlert.setCancelable(false);
        deleteAlert.show();

        Window window = deleteAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        TextView yesButton = deleteAlert.findViewById(R.id.yesButton);
        TextView noButton = deleteAlert.findViewById(R.id.noButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loader.show();
                edit_delete_cupon_package.deleteCupon(shopID, couponID).observe(getViewLifecycleOwner(), new Observer<edit_delete_response>() {
                    @Override
                    public void onChanged(edit_delete_response edit_delete_response) {

                        String message = edit_delete_response.getMesssage();

                        loader.dismiss();

                        if (message.equals("success")) {
                            coupon_data();
                            deleteAlert.dismiss();
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlert.dismiss();
            }
        });


    }

    @Override
    public void OnItemNotify(int position) {
        cupon_response response = couponList.get(position);
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = (String) android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new java.util.Date());

        String targetdate = response.getEnd_date() + " 23:59:59";

        String duration = "";
        //SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        try {
            // Use parse method to get date object of both dates

            Date date1 = myFormat.parse(currentTime);
            Date date2 = myFormat.parse(targetdate);
            if (!(date1.getTime() > date2.getTime())) {
                // Calucalte time difference in milliseconds
                long time_difference = date2.getTime() - date1.getTime();
                // Calucalte time difference in days
                long days_difference = (time_difference / (1000 * 60 * 60 * 24)) % 365;
                // Calucalte time difference in years
                long years_difference = (time_difference / (1000l * 60 * 60 * 24 * 365));
                // Calucalte time difference in seconds

                // Calucalte time difference in minutes
                long minutes_difference = (time_difference / (1000 * 60)) % 60;

                // Calucalte time difference in hours
                long hours_difference = (time_difference / (1000 * 60 * 60)) % 24;
                // Show difference in years, in days, hours, minutes, and seconds
                duration = days_difference + " দিন " + hours_difference + " ঘণ্টা " + minutes_difference + " মিনিট ";
                //holder.durationText.setText(duration);
            } else {

                duration = "Time End";
            }

        }
        // Catch parse exception
        catch (ParseException excep) {
            excep.printStackTrace();
        }
        String message = "Coupon:" + " " + response.getCupon_name() + " " + "Limit:" + " " + duration;

        couponViewModel.getNotify(shopID, message).observe(getViewLifecycleOwner(), new Observer<notify_response>() {
            @Override
            public void onChanged(notify_response notify_response) {
                String message = notify_response.getMessage();
                if (message.equals("success")) {
                    Toast.makeText(getActivity(), "Notificaton  sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}