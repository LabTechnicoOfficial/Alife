package com.alifew.alifeworld.view.Customer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.adapter.Customer.Customer_shop_all_due_list_adapter;
import com.alifew.alifeworld.adapter.Normal_sell_details_image_adapter;
import com.alifew.alifeworld.adapter.Systemetic_sell_details_adapter;
import com.alifew.alifeworld.model.get_customer_all_due_details_response;
import com.alifew.alifeworld.model.image;
import com.alifew.alifeworld.model.local_sell.get_local_sell_details_response;
import com.alifew.alifeworld.model.normal_sell_details_response;
import com.alifew.alifeworld.model.systemetic_sell_details_response;
import com.alifew.alifeworld.viewmodel.Customer_shop;
import com.alifew.alifeworld.viewmodel.Local_sell.Get_local_sell;
import com.alifew.alifeworld.viewmodel.Sell_details;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Customer_due_list_fragment extends Fragment implements Customer_shop_all_due_list_adapter.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private String customer_id;
    RecyclerView duelistview;
    List<get_customer_all_due_details_response> dueList;
    List<get_customer_all_due_details_response> convertList;
    Customer_shop_all_due_list_adapter duelistadapter;
    Sell_details sell_details;
    Get_local_sell get_local_sell;
    Customer_shop customer_shop;
    Systemetic_sell_details_adapter sell_details_adapter;
    FragmentManager fragmentManager;
    List<image> imageList;
    Normal_sell_details_image_adapter image_show_adapter;

    LinearLayout searchLayout, customSearchLayout;
    EditText searchDate;
    ImageView searchDateButton, customSearchButton;
    TextInputEditText fromText, toText;
    Spinner optionSpinner;
    String[] options = {"Total Due History", "Daily Due History", "Custom Due History"};
    String myFormat = "yyyy-MM-dd";
    String dateCurrent, fromDate, toDate;
    Calendar myCalendar;
    SimpleDateFormat sdf;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    int page1 = 1, page2 = 1, page3 = 1, limit = 20,end1=0,end2=0,end3=0;
    int select_type;

    public Customer_due_list_fragment(String customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main();


    }

    private void main() {
        checkConnection();
        dueList = new ArrayList<>();
        customer_shop = new ViewModelProvider(getActivity()).get(Customer_shop.class);
        dateCurrent = new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date());
        //String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        searchDate.setText(dateCurrent);
        searchDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Search", Toast.LENGTH_SHORT).show();
                page1 = 1;
                end1=0;
                daily_due_details(page1, limit);
            }
        });

        customSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDate = fromText.getText().toString().trim();
                toDate = toText.getText().toString().trim();

                if (TextUtils.isEmpty(fromDate) || TextUtils.isEmpty(toDate)) {
                    Toast.makeText(getActivity(), "Select Date", Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(getActivity(), "Custom Search", Toast.LENGTH_SHORT).show();
                    page2 = 1;
                    end2=0;
                    custome_due_details(page2, limit);
                }

            }
        });
        // all_due_details();
    }

    private void all_due_details(int page, int limit) {
        progressBar.setVisibility(View.GONE);
        if (page == 1) {
            dueList = new ArrayList<>();
            duelistadapter = new Customer_shop_all_due_list_adapter(dueList);
            duelistadapter.SetOnClickListener(Customer_due_list_fragment.this::OnDueLick);
            duelistview.setAdapter(duelistadapter);
        }
        customer_shop.getDue_details(customer_id, page, limit).observe(getViewLifecycleOwner(), new Observer<List<get_customer_all_due_details_response>>() {
            @Override
            public void onChanged(List<get_customer_all_due_details_response> get_customer_all_due_details_responses) {

                for (int i = 0; i < get_customer_all_due_details_responses.size(); i++) {
                    dueList.add(get_customer_all_due_details_responses.get(i));
                }
                if(get_customer_all_due_details_responses.size()<limit)
                {
                    end3=1;
                }
                duelistadapter = new Customer_shop_all_due_list_adapter(dueList);
                duelistadapter.SetOnClickListener(Customer_due_list_fragment.this::OnDueLick);
                duelistview.setAdapter(duelistadapter);
            }
        });
    }

    private void daily_due_details(int page, int limit) {
        progressBar.setVisibility(View.GONE);
        if (page == 1) {
            dueList = new ArrayList<>();
            duelistadapter = new Customer_shop_all_due_list_adapter(dueList);
            duelistadapter.SetOnClickListener(Customer_due_list_fragment.this::OnDueLick);
            duelistview.setAdapter(duelistadapter);

        }

        customer_shop.getDaily_due_details(customer_id, searchDate.getText().toString().trim(), page, limit).observe(getViewLifecycleOwner(), new Observer<List<get_customer_all_due_details_response>>() {
            @Override
            public void onChanged(List<get_customer_all_due_details_response> get_customer_all_due_details_responses) {

                for (int i = 0; i < get_customer_all_due_details_responses.size(); i++) {
                    convertList.add(get_customer_all_due_details_responses.get(i));
                }
                if(get_customer_all_due_details_responses.size()<limit)
                {
                    end1=1;
                }
                duelistadapter = new Customer_shop_all_due_list_adapter(dueList);
                duelistadapter.SetOnClickListener(Customer_due_list_fragment.this::OnDueLick);
                duelistview.setAdapter(duelistadapter);
            }
        });
    }

    private void custome_due_details(int page, int limit) {
        progressBar.setVisibility(View.GONE);
        if (page == 1) {
            dueList = new ArrayList<>();
            duelistadapter = new Customer_shop_all_due_list_adapter(dueList);
            duelistadapter.SetOnClickListener(Customer_due_list_fragment.this::OnDueLick);
            duelistview.setAdapter(duelistadapter);

        }

        customer_shop.getSelected_due_details(customer_id, fromText.getText().toString().trim(), toText.getText().toString().trim(), page, limit).observe(getViewLifecycleOwner(), new Observer<List<get_customer_all_due_details_response>>() {
            @Override
            public void onChanged(List<get_customer_all_due_details_response> get_customer_all_due_details_responses) {
                // dueList = get_customer_all_due_details_responses;
                for (int i = 0; i < get_customer_all_due_details_responses.size(); i++) {
                    convertList.add(get_customer_all_due_details_responses.get(i));
                }
                if(get_customer_all_due_details_responses.size()<limit)
                {
                    end2=1;
                }
                duelistadapter = new Customer_shop_all_due_list_adapter(dueList);
                duelistadapter.SetOnClickListener(Customer_due_list_fragment.this::OnDueLick);
                duelistview.setAdapter(duelistadapter);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.customer_due_list_fragment, container, false);
        duelistview = (RecyclerView) view.findViewById(R.id.dueViewID);
        duelistview.setHasFixedSize(true);
        duelistview.setLayoutManager(new LinearLayoutManager(getContext()));

        fragmentManager = getFragmentManager();

        searchLayout = (LinearLayout) view.findViewById(R.id.searchLayoutID);
        customSearchLayout = (LinearLayout) view.findViewById(R.id.customSearchLayoutID);

        searchDate = (EditText) view.findViewById(R.id.dateEditID);
        searchDateButton = (ImageView) view.findViewById(R.id.searchButtonID);
        customSearchButton = (ImageView) view.findViewById(R.id.customSearchButtonID);

        fromText = (TextInputEditText) view.findViewById(R.id.fromDateTextID);
        toText = (TextInputEditText) view.findViewById(R.id.toDateTextID);

        optionSpinner = (Spinner) view.findViewById(R.id.optionSpinnerID);

        ArrayAdapter optionAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, options);
        optionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        optionSpinner.setAdapter(optionAdapter);
        optionSpinner.setOnItemSelectedListener(this);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    progressBar.setVisibility(View.VISIBLE);
                    if (select_type == 1&&end1==0) {
                        page1++;
                        daily_due_details(page1, limit);
                    } else if (select_type == 2&&end2==0) {
                        page2++;
                        custome_due_details(page2, limit);
                    } else if (select_type == 3&&end3==0) {
                        page3++;
                        all_due_details(page3, limit);
                    }

                }
            }
        });

        searchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daily_date(searchDate);
            }
        });

        fromText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick_date(fromText);
            }
        });

        toText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick_date(toText);
            }
        });


        return view;
    }

    private void pick_date(EditText editText) {
        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateCurrent = sdf.format(myCalendar.getTime());


                //In which you need put here
                Date strDate = null;
                try {
                    strDate = sdf.parse(dateCurrent);
                    if (System.currentTimeMillis() > strDate.getTime()) {
                        editText.setText(dateCurrent);
                    } else {
                        Toast.makeText(getActivity(), "ERROR: Cross current date", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }


            }

        };

        new DatePickerDialog(getActivity(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void daily_date(EditText editText) {
        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateCurrent = sdf.format(myCalendar.getTime());


                //In which you need put here
                Date strDate = null;
                try {
                    strDate = sdf.parse(dateCurrent);
                    if (System.currentTimeMillis() > strDate.getTime()) {
                        editText.setText(dateCurrent);
                        select_type=1;
                        page1=1;
                        daily_due_details(page1,limit);

                    } else {

                        Toast.makeText(getActivity(), "ERROR: Cross current date", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }


            }

        };

        new DatePickerDialog(getActivity(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


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

                    main();
                }
            });

        }
    }

    @Override
    public void OnDueLick(int position) {
        get_customer_all_due_details_response due = dueList.get(position);
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
                    imageList = normal_sell_details_response.getImage();
                    image_show_adapter = new Normal_sell_details_image_adapter(imageList);
                    multipleImages.setAdapter(image_show_adapter);
                }
            });
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertCustom.dismiss();
                }
            });
        } else if(sell_type.equals("local"))
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

            get_local_sell = new ViewModelProvider(getActivity()).get(Get_local_sell.class);
            get_local_sell.getDetails(sell_id).observe(getViewLifecycleOwner(), new Observer<get_local_sell_details_response>() {
                @Override
                public void onChanged(get_local_sell_details_response get_local_sell_details_response) {
                    descriptionText.setText(get_local_sell_details_response.getDescription());
                    imageList = get_local_sell_details_response.getImage();
                    image_show_adapter = new Normal_sell_details_image_adapter(imageList);
                    multipleImages.setAdapter(image_show_adapter);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if (item.equals("Total Due History")) {
            searchLayout.setVisibility(View.GONE);
            customSearchLayout.setVisibility(View.GONE);
            select_type=3;
            page3=1;
            end3=0;
            all_due_details(page3,limit);


        } else if (item.equals("Daily Due History")) {
            customSearchLayout.setVisibility(View.GONE);
            searchLayout.setVisibility(View.VISIBLE);
            dateCurrent = new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date());

            searchDate.setText(dateCurrent);
            select_type=1;
            page1=1;
            end1=0;
            daily_due_details(page1,limit);
            //fetch_daily_sell_summary();
            //daily_tally();

        } else if (item.equals("Custom Due History")) {
            searchLayout.setVisibility(View.GONE);
            customSearchLayout.setVisibility(View.VISIBLE);
            select_type=2;
            page2=1;
            end2=0;
            //sell_list.clear();
            //sell_list_adapter.notifyDataSetChanged();
            //convertList.clear();
            //dueList=new ArrayList<>();
            dueList.clear();
            duelistadapter.notifyDataSetChanged();


        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
