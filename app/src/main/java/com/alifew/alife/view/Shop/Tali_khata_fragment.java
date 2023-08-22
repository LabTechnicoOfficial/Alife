package com.alifew.alife.view.Shop;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alifew.alife.R;
import com.alifew.alife.Utils.ImageHelper;
import com.alifew.alife.adapter.Normal_sell_details_image_adapter;
import com.alifew.alife.adapter.Shop_daily_due_list_adapter;
import com.alifew.alife.adapter.Shop_daily_paid_list_adapter;
import com.alifew.alife.adapter.Shop_tally_khata_adapter;
import com.alifew.alife.adapter.Systemetic_sell_details_adapter;
import com.alifew.alife.model.get_daily_due_sell_response;
import com.alifew.alife.model.get_daily_sell_cash_response;
import com.alifew.alife.model.get_sell_details_response;
import com.alifew.alife.model.image;
import com.alifew.alife.model.normal_sell_details_response;
import com.alifew.alife.model.shop_tally_khata_response;
import com.alifew.alife.model.systemetic_sell_details_response;
import com.alifew.alife.viewmodel.Get_daily_shop_tally;
import com.alifew.alife.viewmodel.Sell_details;
import com.alifew.alife.viewmodel.Shop_all_tally;
import com.alifew.alife.viewmodel.Shop_selected_days_tally;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Tali_khata_fragment extends Fragment implements Shop_tally_khata_adapter.OnItemClickListener, AdapterView.OnItemSelectedListener, Normal_sell_details_image_adapter.ImageClickListener {

    TextView totalSellText, totalProfitText, paidText, dueText;
    private String shop_id;
    RecyclerView sellHistoryListView;
    private LinearLayoutManager sellHistoryLayoutManager;
    private Shop_daily_paid_list_adapter paid_adapter;
    private Shop_daily_due_list_adapter due_adapter;
    private Shop_tally_khata_adapter sell_list_adapter;
    List<get_daily_sell_cash_response> paid_list;
    List<get_daily_due_sell_response> due_list;
    List<shop_tally_khata_response> sell_list;
    List<systemetic_sell_details_response> sell_product_list;
    private Systemetic_sell_details_adapter productlist_adapter;
    Spinner optionSpinner;

    EditText searchDate;
    ImageView searchDateButton, customSearchButton;
    String myFormat = "yyyy-MM-dd";
    String dateCurrent, fromDate, toDate;
    Get_daily_shop_tally get_daily_shop_tally;
    Shop_selected_days_tally get_selected_tally;
    Shop_all_tally get_all_tally;

    LinearLayout searchLayout, customSearchLayout;

    EditText fromText, toText;

    Calendar myCalendar;
    SimpleDateFormat sdf;

    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    Sell_details sell_details;
    Systemetic_sell_details_adapter sell_details_adapter;
    String[] options = {"Total Account History", "Daily Account History", "Custom Date History"};
    private List<image> normal_sell_image;
    private Normal_sell_details_image_adapter normal_sell_adapter;

    public Tali_khata_fragment(String shop_id) {
        this.shop_id = shop_id;
    }

    int page1 = 1, page2 = 1, page3 = 1, limit = 15, end1 = 0, end2 = 0, end3 = 0;
    int select_type;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main();
    }

    private void main() {
        sell_list = new ArrayList<>();
        checkConnection();
        get_daily_shop_tally = new ViewModelProvider(getActivity()).get(Get_daily_shop_tally.class);
        get_selected_tally = new ViewModelProvider(getActivity()).get(Shop_selected_days_tally.class);
        get_all_tally = new ViewModelProvider(getActivity()).get(Shop_all_tally.class);
        dateCurrent = new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date());
        //String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        searchDate.setText(dateCurrent);
        sell_list_adapter = new Shop_tally_khata_adapter(sell_list);
        //all_sell_summary();


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

        searchDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fetch_daily_sell_summary();


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

                    selected_sell_summary();

                }

            }
        });

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

                        fetch_daily_sell_summary();

                    } else {
                        sell_list.clear();
                        sell_list_adapter.notifyDataSetChanged();
                        totalSellText.setText("");
                        totalProfitText.setText("");
                        paidText.setText("");
                        dueText.setText("");
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

    private void daily_tally(int page, int limit) {
        progressBar.setVisibility(View.GONE);
        if (page == 1) {
            sell_list.clear();
            sell_list_adapter.notifyDataSetChanged();
            sell_list = new ArrayList<>();
            sell_list_adapter = new Shop_tally_khata_adapter(sell_list);

        }

        //Toast.makeText(getActivity(),"yes",Toast.LENGTH_SHORT).show();
        get_daily_shop_tally.getSell_list(shop_id, searchDate.getText().toString().trim(), page, limit).observe(getViewLifecycleOwner(), new Observer<List<shop_tally_khata_response>>() {
            @Override
            public void onChanged(List<shop_tally_khata_response> shoptallykhata_respons) {
                for (int i = 0; i < shoptallykhata_respons.size(); i++) {
                    sell_list.add(shoptallykhata_respons.get(i));
                }

                if (shoptallykhata_respons.size() < limit) {
                    end1 = 1;
                }
                sell_list_adapter = new Shop_tally_khata_adapter(sell_list);
                sell_list_adapter.setOnClickListener(Tali_khata_fragment.this::OnItemClick);

                sellHistoryListView.setAdapter(sell_list_adapter);
            }
        });

    }

    private void selected_tally(int page, int limit) {
        progressBar.setVisibility(View.GONE);
        if (page == 1) {
            sell_list.clear();
            sell_list_adapter.notifyDataSetChanged();
            sell_list = new ArrayList<>();
            sell_list_adapter = new Shop_tally_khata_adapter(sell_list);
        }

        get_selected_tally.getTally(shop_id, fromText.getText().toString().trim(), toText.getText().toString().trim(), page, limit).observe(getViewLifecycleOwner(), new Observer<List<shop_tally_khata_response>>() {
            @Override
            public void onChanged(List<shop_tally_khata_response> shop_tally_khata_responses) {
                for (int i = 0; i < shop_tally_khata_responses.size(); i++) {
                    sell_list.add(shop_tally_khata_responses.get(i));
                }
                if (shop_tally_khata_responses.size() < limit) {
                    end2 = 1;
                }
                // sell_list = shop_tally_khata_responses;
                sell_list_adapter = new Shop_tally_khata_adapter(sell_list);
                sell_list_adapter.setOnClickListener(Tali_khata_fragment.this::OnItemClick);

                sellHistoryListView.setAdapter(sell_list_adapter);
            }
        });
    }

    private void all_tally(int page, int limit) {
        if (page == 1) {
            sell_list.clear();
            sell_list_adapter.notifyDataSetChanged();
            sell_list = new ArrayList<>();
            sell_list_adapter = new Shop_tally_khata_adapter(sell_list);
        }
        //sell_list_adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        get_all_tally.getTally(shop_id, page, limit).observe(getViewLifecycleOwner(), new Observer<List<shop_tally_khata_response>>() {
            @Override
            public void onChanged(List<shop_tally_khata_response> shop_tally_khata_responses) {
                for (int i = 0; i < shop_tally_khata_responses.size(); i++) {
                    sell_list.add(shop_tally_khata_responses.get(i));
                }
                if (shop_tally_khata_responses.size() < limit) {
                    end3 = 1;
                }
                // sell_list = shop_tally_khata_responses;
                sell_list_adapter = new Shop_tally_khata_adapter(sell_list);
                sell_list_adapter.setOnClickListener(Tali_khata_fragment.this::OnItemClick);

                sellHistoryListView.setAdapter(sell_list_adapter);
            }
        });
    }


    private void fetch_paid_sell_list() {
        paid_list = new ArrayList<>();
        //Toast.makeText(getActivity(),"yes",Toast.LENGTH_SHORT).show();
        get_daily_shop_tally.get_sell_cash(shop_id, dateCurrent).observe(getViewLifecycleOwner(), new Observer<List<get_daily_sell_cash_response>>() {
            @Override
            public void onChanged(List<get_daily_sell_cash_response> get_daily_sell_cash_responses) {
                paid_list = get_daily_sell_cash_responses;

                paid_adapter = new Shop_daily_paid_list_adapter(paid_list);
                sellHistoryListView.setAdapter(paid_adapter);
            }
        });

    }

    private void fetch_due_sell_list() {
        due_list = new ArrayList<>();
        get_daily_shop_tally.get_sell_due(shop_id, dateCurrent).observe(getViewLifecycleOwner(), new Observer<List<get_daily_due_sell_response>>() {
            @Override
            public void onChanged(List<get_daily_due_sell_response> get_daily_due_sell_responses) {
                due_list = get_daily_due_sell_responses;
                due_adapter = new Shop_daily_due_list_adapter(due_list);
                //dueListView.setAdapter(due_adapter);

            }
        });

    }

    private void fetch_daily_sell_summary() {
        totalSellText.setText("");
        totalProfitText.setText("");
        paidText.setText("");
        dueText.setText("");
        //Toast.makeText(getActivity(),shop_id,Toast.LENGTH_SHORT).show();
        get_daily_shop_tally.get_sell_details(shop_id, dateCurrent).observe(getViewLifecycleOwner(), new Observer<get_sell_details_response>() {
            @Override
            public void onChanged(get_sell_details_response get_sell_details_response) {

                Double t_price = Double.parseDouble(get_sell_details_response.getTotal_price());
                totalSellText.setText(new DecimalFormat("##.##").format(t_price));

                Double t_profit = Double.parseDouble(get_sell_details_response.getTotal_profit());
                totalProfitText.setText(new DecimalFormat("##.##").format(t_profit));

                Double paid = Double.parseDouble(get_sell_details_response.getTotal_cash_price());
                paidText.setText(new DecimalFormat("##.##").format(paid));

                Double due = Double.parseDouble(get_sell_details_response.getTotal_due_price());
                dueText.setText(new DecimalFormat("##.##").format(due));
                select_type = 1;
                page1 = 1;
                end1 = 0;
                daily_tally(page1, limit);
            }
        });
    }

    private void selected_sell_summary() {
        totalSellText.setText("");
        totalProfitText.setText("");
        paidText.setText("");
        dueText.setText("");
        get_selected_tally.getDetails(shop_id, fromText.getText().toString().trim(), toText.getText().toString().trim()).observe(getViewLifecycleOwner(), new Observer<get_sell_details_response>() {
            @Override
            public void onChanged(get_sell_details_response get_sell_details_response) {
                /*totalSellText.setText(get_sell_details_response.getTotal_price());
                totalProfitText.setText(get_sell_details_response.getTotal_profit());
                paidText.setText(get_sell_details_response.getTotal_cash_price());
                dueText.setText(get_sell_details_response.getTotal_due_price());*/


                Double t_price = Double.parseDouble(get_sell_details_response.getTotal_price());
                totalSellText.setText(new DecimalFormat("##.##").format(t_price));

                Double t_profit = Double.parseDouble(get_sell_details_response.getTotal_profit());
                totalProfitText.setText(new DecimalFormat("##.##").format(t_profit));

                Double paid = Double.parseDouble(get_sell_details_response.getTotal_cash_price());
                paidText.setText(new DecimalFormat("##.##").format(paid));

                Double due = Double.parseDouble(get_sell_details_response.getTotal_due_price());
                dueText.setText(new DecimalFormat("##.##").format(due));
                page2 = 1;
                select_type = 2;
                end2 = 0;
                selected_tally(page2, limit);
            }
        });
    }

    private void all_sell_summary() {
        totalSellText.setText("");
        totalProfitText.setText("");
        paidText.setText("");
        dueText.setText("");
        get_all_tally.getDetails(shop_id).observe(getViewLifecycleOwner(), new Observer<get_sell_details_response>() {
            @Override
            public void onChanged(get_sell_details_response get_sell_details_response) {

                Double t_price = Double.parseDouble(get_sell_details_response.getTotal_price());
                totalSellText.setText(new DecimalFormat("##.##").format(t_price));

                Double t_profit = Double.parseDouble(get_sell_details_response.getTotal_profit());
                totalProfitText.setText(new DecimalFormat("##.##").format(t_profit));

                Double paid = Double.parseDouble(get_sell_details_response.getTotal_cash_price());
                paidText.setText(new DecimalFormat("##.##").format(paid));

                Double due = Double.parseDouble(get_sell_details_response.getTotal_due_price());
                dueText.setText(new DecimalFormat("##.##").format(due));
                page3 = 1;
                end3 = 0;
                select_type = 3;
                all_tally(page3, limit);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_daily_account_fragment, container, false);

        totalSellText = (TextView) view.findViewById(R.id.totalSellID);
        totalProfitText = (TextView) view.findViewById(R.id.totalProfitID);
        paidText = (TextView) view.findViewById(R.id.paidTextID);
        dueText = (TextView) view.findViewById(R.id.dueTextID);

        sellHistoryListView = (RecyclerView) view.findViewById(R.id.sellHistoryListID);

        searchDate = (EditText) view.findViewById(R.id.dateEditID);
        searchDateButton = (ImageView) view.findViewById(R.id.searchButtonID);
        customSearchButton = (ImageView) view.findViewById(R.id.customSearchButtonID);

        searchLayout = (LinearLayout) view.findViewById(R.id.searchLayoutID);
        customSearchLayout = (LinearLayout) view.findViewById(R.id.customSearchLayoutID);

        optionSpinner = (Spinner) view.findViewById(R.id.optionSpinnerID);

        fromText = (TextInputEditText) view.findViewById(R.id.fromDateTextID);
        toText = (TextInputEditText) view.findViewById(R.id.toDateTextID);

        ArrayAdapter optionAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, options);
        optionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        optionSpinner.setAdapter(optionAdapter);
        optionSpinner.setOnItemSelectedListener(this);

        sellHistoryLayoutManager = new LinearLayoutManager(getContext());
        sellHistoryListView.setHasFixedSize(true);
        sellHistoryListView.setLayoutManager(sellHistoryLayoutManager);

        myCalendar = Calendar.getInstance();


        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    progressBar.setVisibility(View.VISIBLE);
                    if (select_type == 1) {
                        if (end1 == 0) {
                            page1++;
                            daily_tally(page1, limit);
                        }
                    } else if (select_type == 2) {
                        if (end2 == 0) {
                            page2++;
                            selected_tally(page2, limit);
                        }
                    } else if (select_type == 3) {
                        if (end3 == 0) {
                            page3++;
                            all_tally(page3, limit);
                        }
                    }

                }
            }
        });

        return view;
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

    public void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
        //adapter.notifyDataSetChanged();
    }

    @Override
    public void OnItemClick(int position) {
        shop_tally_khata_response sell = sell_list.get(position);
        String sell_id = sell.getSell_id();
        String totalPrice = sell.getSell_price();

        String sell_type = sell.getSell_type();
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
        } else {
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
                    normal_sell_adapter.setOnClickListener(Tali_khata_fragment.this::ImageClick);
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
        // String paidPrice = sell.getPayment_cash_amount();
        //String duePrice = sell.getPayment_due_amount();

        //sell_customer_history_alert(sell_id,totalPrice, paidPrice, duePrice);
        /*
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
        } else {
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
        }
         */
    }

    private void sell_customer_history_alert(String sell_id, String totalPrice, String paidPrice, String duePrice) {
        Dialog sellHistoryAlert = new Dialog(getActivity());
        sellHistoryAlert.setContentView(R.layout.sell_customer_history_alert);
        sellHistoryAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sellHistoryAlert.setCancelable(false);
        sellHistoryAlert.show();

        ImageView closeButton = (ImageView) sellHistoryAlert.findViewById(R.id.closeID);
        TextView sellPriceText = (TextView) sellHistoryAlert.findViewById(R.id.sellPriceID);
        TextView paidText = (TextView) sellHistoryAlert.findViewById(R.id.paidID);
        TextView dueText = (TextView) sellHistoryAlert.findViewById(R.id.dueID);

        RecyclerView productView = (RecyclerView) sellHistoryAlert.findViewById(R.id.productViewID);
        productView.setHasFixedSize(true);
        productView.setLayoutManager(new LinearLayoutManager(getContext()));
        sell_product_list = new ArrayList<>();

        get_daily_shop_tally.get_productlist(sell_id).observe(getViewLifecycleOwner(), new Observer<List<systemetic_sell_details_response>>() {
            @Override
            public void onChanged(List<systemetic_sell_details_response> systemetic_sell_details_respons) {
                sell_product_list = systemetic_sell_details_respons;
                productlist_adapter = new Systemetic_sell_details_adapter(sell_product_list);
                productView.setAdapter(productlist_adapter);
            }
        });
        sellPriceText.setText(totalPrice);
        paidText.setText(paidPrice);
        dueText.setText(duePrice);


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellHistoryAlert.dismiss();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        if (item.equals("Total Account History")) {
            searchLayout.setVisibility(View.GONE);
            customSearchLayout.setVisibility(View.GONE);
            sell_list = new ArrayList<>();
            sell_list_adapter = new Shop_tally_khata_adapter(sell_list);
            sellHistoryListView.setAdapter(sell_list_adapter);
            all_sell_summary();


        } else if (item.equals("Daily Account History")) {
            customSearchLayout.setVisibility(View.GONE);
            searchLayout.setVisibility(View.VISIBLE);
            dateCurrent = new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date());
            searchDate.setText(dateCurrent);
            sell_list = new ArrayList<>();
            sell_list_adapter = new Shop_tally_khata_adapter(sell_list);
            sellHistoryListView.setAdapter(sell_list_adapter);
            fetch_daily_sell_summary();


        } else if (item.equals("Custom Date History")) {
            searchLayout.setVisibility(View.GONE);
            customSearchLayout.setVisibility(View.VISIBLE);
            sell_list.clear();
            sell_list_adapter.notifyDataSetChanged();
            sell_list = new ArrayList<>();
            sell_list_adapter = new Shop_tally_khata_adapter(sell_list);
            sellHistoryListView.setAdapter(sell_list_adapter);
            totalSellText.setText("");
            totalProfitText.setText("");
            paidText.setText("");
            dueText.setText("");

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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