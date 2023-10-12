package com.alifew.alife.view.Shop;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alifew.alife.R;
import com.alifew.alife.adapter.refer.ShopReferAdapter;
import com.alifew.alife.databinding.FragmentShopReferBinding;
import com.alifew.alife.model.CommonResponse;
import com.alifew.alife.model.refer.ReferResponse;
import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.viewmodel.refer.ShopReferViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ShopReferFragment extends Fragment implements ShopReferAdapter.OnItemDeleteListener, ShopReferAdapter.OnItemClickListener {

    SessionManagement sessionManagement;
    int shopID;
    ExtendedFloatingActionButton addButton;
    FragmentShopReferBinding binding;
    List<ReferResponse> referList;
    ShopReferViewModel referViewModel;
    ShopReferAdapter adapter;
    String myFormat = "yyyy-MM-dd", dateCurrent;

    TextView durationText;

    Dialog loader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShopReferBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        initView(view);

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_refer();
            }
        });

        load_data();


        return view;
    }


    private void load_data() {

        binding.progressBar.setVisibility(View.VISIBLE);
        referViewModel.getReferList(String.valueOf(shopID)).observe(getViewLifecycleOwner(), referResponses -> {
            binding.progressBar.setVisibility(View.GONE);
            referList = referResponses;
            adapter = new ShopReferAdapter(referList);
            adapter.setOnClickListener(ShopReferFragment.this::onDeleteClick, ShopReferFragment.this::onItemClick);
            binding.itemView.setAdapter(adapter);
        });
    }

    private void initView(View view) {
        referViewModel = new ViewModelProvider(getActivity()).get(ShopReferViewModel.class);
        sessionManagement = new SessionManagement(getActivity());
        shopID = sessionManagement.getSession();

        binding.itemView.setHasFixedSize(true);
        binding.itemView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.itemView.setItemViewCacheSize(100);

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

    }

    private void add_refer() {
        Dialog addReferDialog = new Dialog(getActivity());
        addReferDialog.setContentView(R.layout.shop_coupon_add_alert);
        addReferDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addReferDialog.setCancelable(false);
        addReferDialog.show();

        Window window = addReferDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        ImageView closeButton = addReferDialog.findViewById(R.id.closeButtonID);
        AppCompatButton addButton = addReferDialog.findViewById(R.id.addButtonID);
        TextView creationDateText = addReferDialog.findViewById(R.id.creationDateTextID);
        TextView endDateText = addReferDialog.findViewById(R.id.dateTextID);
        TextInputEditText nameText = addReferDialog.findViewById(R.id.nameTextID);
        TextInputEditText descriptionText = addReferDialog.findViewById(R.id.descriptionTextID);
        durationText = addReferDialog.findViewById(R.id.durationTextID);

        TextView headerText = addReferDialog.findViewById(R.id.headerText);
        headerText.setText("Add Refer");

        creationDateText.setText("Select Create Date");
        endDateText.setText("Select End Date");
        //creationDateText.setText(new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date()));

        creationDateText.setOnClickListener(v -> pickCreateDate(creationDateText, endDateText));

        endDateText.setOnClickListener(v -> pickEndDate(creationDateText, endDateText));

        addButton.setOnClickListener(v -> {
            String name = nameText.getText().toString().trim();
            String description = descriptionText.getText().toString().trim();

            if (TextUtils.isEmpty(name) || creationDateText.getText().toString().trim().equals("Select Create Date") || endDateText.getText().toString().trim().equals("Select End Date") || TextUtils.isEmpty(durationText.getText().toString().trim())) {
                Toast.makeText(getActivity(), "Give data correctly", Toast.LENGTH_SHORT).show();
            } else {

                if (TextUtils.isEmpty(description)) {
                    description = " ";
                }

                loader.show();
                referViewModel.addRefer(shopID, name, creationDateText.getText().toString().trim(), endDateText.getText().toString().trim(), description).observe(getViewLifecycleOwner(), commonResponse -> {
                    String message = commonResponse.message;
                    loader.dismiss();


                    if (message.equals("Add successfully")) {
                        addReferDialog.dismiss();

                        load_data();
                    }
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                });
            }
        });

        closeButton.setOnClickListener(v -> addReferDialog.dismiss());
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
    public void onDeleteClick(int position) {
        ReferResponse response = referList.get(position);
        String referID = response.id;

        Dialog deleteAlert = new Dialog(getActivity());
        deleteAlert.setContentView(R.layout.delete_alert);
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

        yesButton.setOnClickListener(v -> {

            loader.show();
            referViewModel.deleteRefer(referID).observe(getViewLifecycleOwner(), new Observer<CommonResponse>() {
                @Override
                public void onChanged(CommonResponse commonResponse) {

                    String message = commonResponse.message;

                    loader.dismiss();

                    if (message.equals("deleted successfully")) {
                        load_data();
                        deleteAlert.dismiss();
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();

                    }
                }
            });

        });

        noButton.setOnClickListener(v -> deleteAlert.dismiss());
    }

    @Override
    public void onItemClick(int position) {
        ReferResponse response = referList.get(position);

        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.frame_container, new ShopReferPackageFragment(response.id)).addToBackStack(null).commit();
    }
}