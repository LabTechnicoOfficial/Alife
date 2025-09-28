package com.alifew.bcopay.view.Shop;

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

import com.alifew.bcopay.R;
import com.alifew.bcopay.adapter.Normal_sell_details_image_adapter;
import com.alifew.bcopay.adapter.Shop_customer_allduelist_adapter;
import com.alifew.bcopay.adapter.Systemetic_sell_details_adapter;
import com.alifew.bcopay.model.get_shop_all_due_details_response;
import com.alifew.bcopay.model.image;
import com.alifew.bcopay.model.local_sell.get_local_sell_details_response;
import com.alifew.bcopay.model.normal_sell_details_response;
import com.alifew.bcopay.model.systemetic_sell_details_response;
import com.alifew.bcopay.viewmodel.Local_sell.Get_local_sell;
import com.alifew.bcopay.viewmodel.Sell_details;
import com.alifew.bcopay.viewmodel.ShopCustomerViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Shop_due_list_fragment extends Fragment implements Shop_customer_allduelist_adapter.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private String shop_id;
    RecyclerView duelistview;
    FragmentManager fragmentManager;
    private Shop_customer_allduelist_adapter duelist_adapter;
    Systemetic_sell_details_adapter sell_details_adapter;
    Normal_sell_details_image_adapter image_show_adapter;
    Get_local_sell get_local_sell;
    List<image> imageList;
    List<get_shop_all_due_details_response> dueList;
    List<get_shop_all_due_details_response> convertList;
    Sell_details sell_details;
    ShopCustomerViewModel shop_customer;
    Spinner optionSpinner;
    EditText searchDate, fromText, toText;
    ImageView searchDateButton, customSearchButton;
    String myFormat = "yyyy-MM-dd";
    String dateCurrent, fromDate, toDate;
    Calendar myCalendar;
    SimpleDateFormat sdf;

    LinearLayout searchLayout, customSearchLayout;
    TextInputLayout fromError, toError;

    ProgressBar progressBar;
    NestedScrollView nestedScrollView;

    String[] options = {"Total Due History", "Daily Due History", "Custom Due History"};
    int page1 = 1, page2 = 1, page3 = 1, limit = 20, end1 = 0, end2 = 0, end3 = 0;
    int select_type;

    public Shop_due_list_fragment(String shop_id) {
        this.shop_id = shop_id;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main();


    }

    private void main() {
        checkConnection();
        dueList = new ArrayList<>();
        shop_customer = new ViewModelProvider(getActivity()).get(ShopCustomerViewModel.class);
        // all_due();
        dateCurrent = new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date());
        //String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        searchDate.setText(dateCurrent);
        searchDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Toast.makeText(getActivity(), "Search", Toast.LENGTH_SHORT).show();
                page1 = 1;
                end1 = 0;
                daily_due(page1, limit);

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
                    Toast.makeText(getActivity(), "Custom Search", Toast.LENGTH_SHORT).show();
                    page2 = 1;
                    end2 = 0;
                    selected_due(page2, limit);

                }

            }
        });
    }

    private void selected_due(int page, int limit) {
        progressBar.setVisibility(View.GONE);
        if (page == 1) {
            dueList = new ArrayList<>();
            duelist_adapter = new Shop_customer_allduelist_adapter(dueList);
            duelist_adapter.SetOnClickListener(Shop_due_list_fragment.this::OnDueLick);
        }
        shop_customer.get_selected_days_due_details(shop_id, fromText.getText().toString().trim(), toText.getText().toString().trim(), page, limit).observe(getViewLifecycleOwner(), new Observer<List<get_shop_all_due_details_response>>() {
            @Override
            public void onChanged(List<get_shop_all_due_details_response> get_shop_all_due_details_responses) {
                for (int i = 0; i < get_shop_all_due_details_responses.size(); i++) {
                    dueList.add(get_shop_all_due_details_responses.get(i));
                }
                if (get_shop_all_due_details_responses.size() < limit) {
                    end2 = 1;
                }
                convertList = new ArrayList<>();

                for (int i = 0; i < dueList.size(); i++) {
                    convertList.add(i, dueList.get(dueList.size() - 1 - i));
                }
                duelist_adapter = new Shop_customer_allduelist_adapter(dueList);
                duelist_adapter.SetOnClickListener(Shop_due_list_fragment.this::OnDueLick);
                duelistview.setAdapter(duelist_adapter);
            }
        });
    }

    private void daily_due(int page, int limit) {
        progressBar.setVisibility(View.GONE);
        if (page == 1) {
            dueList = new ArrayList<>();
            duelist_adapter = new Shop_customer_allduelist_adapter(dueList);
            duelist_adapter.SetOnClickListener(Shop_due_list_fragment.this::OnDueLick);
        }
        shop_customer.get_daily_due_details(shop_id, searchDate.getText().toString().trim(), page, limit).observe(getViewLifecycleOwner(), new Observer<List<get_shop_all_due_details_response>>() {
            @Override
            public void onChanged(List<get_shop_all_due_details_response> get_shop_all_due_details_responses) {
                for (int i = 0; i < get_shop_all_due_details_responses.size(); i++) {
                    dueList.add(get_shop_all_due_details_responses.get(i));
                }
                if (get_shop_all_due_details_responses.size() < limit) {
                    end1 = 1;
                }
                convertList = new ArrayList<>();

                for (int i = 0; i < dueList.size(); i++) {
                    convertList.add(i, dueList.get(dueList.size() - 1 - i));
                }
                duelist_adapter = new Shop_customer_allduelist_adapter(dueList);
                duelist_adapter.SetOnClickListener(Shop_due_list_fragment.this::OnDueLick);
                duelistview.setAdapter(duelist_adapter);
            }
        });
    }

    private void all_due(int page, int limit) {
        progressBar.setVisibility(View.GONE);
        if (page == 1) {
            dueList = new ArrayList<>();
            duelist_adapter = new Shop_customer_allduelist_adapter(dueList);
            duelist_adapter.SetOnClickListener(Shop_due_list_fragment.this::OnDueLick);
        }
        // dueList.clear();
        //duelist_adapter.notifyDataSetChanged();
        shop_customer.get_dueList(shop_id, page, limit).observe(getViewLifecycleOwner(), new Observer<List<get_shop_all_due_details_response>>() {
            @Override
            public void onChanged(List<get_shop_all_due_details_response> get_shop_all_due_details_responses) {
                for (int i = 0; i < get_shop_all_due_details_responses.size(); i++) {
                    dueList.add(get_shop_all_due_details_responses.get(i));
                }
                if (get_shop_all_due_details_responses.size() < limit) {
                    end3 = 1;
                }
                convertList = new ArrayList<>();

                for (int i = 0; i < dueList.size(); i++) {
                    convertList.add(i, dueList.get(dueList.size() - 1 - i));
                }
                duelist_adapter = new Shop_customer_allduelist_adapter(dueList);
                duelist_adapter.SetOnClickListener(Shop_due_list_fragment.this::OnDueLick);
                duelistview.setAdapter(duelist_adapter);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_due_list_fragment, container, false);

        duelistview = (RecyclerView) view.findViewById(R.id.dueViewID);
        duelistview.setHasFixedSize(true);
        duelistview.setLayoutManager(new LinearLayoutManager(getContext()));

        fragmentManager = getFragmentManager();

        searchLayout = (LinearLayout) view.findViewById(R.id.searchLayoutID);
        customSearchLayout = (LinearLayout) view.findViewById(R.id.customSearchLayoutID);

        searchDate = (EditText) view.findViewById(R.id.dateEditID);
        fromError = (TextInputLayout) view.findViewById(R.id.fromDateErrorID);
        toError = (TextInputLayout) view.findViewById(R.id.toDateErrorID);
        searchDateButton = (ImageView) view.findViewById(R.id.searchButtonID);
        customSearchButton = (ImageView) view.findViewById(R.id.customSearchButtonID);

        fromText = (TextInputEditText) view.findViewById(R.id.fromDateTextID);
        toText = (TextInputEditText) view.findViewById(R.id.toDateTextID);

        optionSpinner = (Spinner) view.findViewById(R.id.optionSpinnerID);

        ArrayAdapter optionAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, options);
        optionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        optionSpinner.setAdapter(optionAdapter);
        optionSpinner.setOnItemSelectedListener(this);

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


        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    progressBar.setVisibility(View.VISIBLE);
                    if (select_type == 1 && end1 == 0) {
                        page1++;
                        daily_due(page1, limit);
                    } else if (select_type == 2 && end2 == 0) {
                        page2++;
                        selected_due(page2, limit);
                    } else if (select_type == 3 && end3 == 0) {
                        page3++;
                        all_due(page3, limit);
                    }

                }
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
                        page1 = 1;
                        select_type = 1;
                        daily_due(page1, limit);

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
        get_shop_all_due_details_response due = dueList.get(position);
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
        }
        else if(sell_type.equals("local"))
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
                   // descriptionText.setText(normal_sell_details_response.getDescription());
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
            select_type = 3;
            page3 = 1;
            end3 = 0;
            all_due(page3, limit);


        } else if (item.equals("Daily Due History")) {
            customSearchLayout.setVisibility(View.GONE);
            searchLayout.setVisibility(View.VISIBLE);
            dateCurrent = new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date());

            searchDate.setText(dateCurrent);
            //fetch_daily_sell_summary();
            select_type = 1;
            page1 = 1;
            end1 = 0;
            daily_due(page1, limit);

        } else if (item.equals("Custom Due History")) {
            searchLayout.setVisibility(View.GONE);
            select_type = 2;
            page2 = 1;
            end2 = 0;
            customSearchLayout.setVisibility(View.VISIBLE);
            dueList.clear();
            duelist_adapter.notifyDataSetChanged();
            //sell_list.clear();
            //sell_list_adapter.notifyDataSetChanged();


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
