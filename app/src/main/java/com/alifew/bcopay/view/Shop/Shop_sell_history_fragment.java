package com.alifew.bcopay.view.Shop;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.bcopay.R;
import com.alifew.bcopay.Utils.ImageHelper;
import com.alifew.bcopay.adapter.Shop_sell_history_main_adapter;
import com.alifew.bcopay.model.shop_sell_history_list_response;
import com.alifew.bcopay.model.shop_sell_history_summary_response;
import com.alifew.bcopay.viewmodel.Shop_sell_history;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Shop_sell_history_fragment extends Fragment implements AdapterView.OnItemSelectedListener, Shop_sell_history_main_adapter.onItemClickListener {

    String shopID;
    Spinner optionSpinner;
    String item;
    String[] options = {"Total Sell History", "Daily Sell History", "Custom Date Sell History"};

    TextView totalProfitText, totalSellPriceText, totalBuyPriceText;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;

    EditText searchDate, search;
    ImageView searchDateButton, customSearchButton;
    String myFormat = "yyyy-MM-dd";
    String dateCurrent, fromDate, toDate;

    LinearLayout searchLayout, customSearchLayout;

    EditText fromText, toText;

    Calendar myCalendar;
    SimpleDateFormat sdf;
    RecyclerView sellHistoryListView;
    Shop_sell_history shopSellHistoryViewModel;

    private List<shop_sell_history_list_response> sell_list;

    Shop_sell_history_main_adapter sell_list_adapter;

    int page1 = 1, page2 = 1, page3 = 1, limit = 15, end1 = 0, end2 = 0, end3 = 0;
    int select_type;

    public Shop_sell_history_fragment(String shopID) {
        this.shopID = shopID;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main();
    }

    private void main() {
        search.setText("");
        sell_list = new ArrayList<>();
        checkConnection();
        shopSellHistoryViewModel = new ViewModelProvider(getActivity()).get(Shop_sell_history.class);
        dateCurrent = new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date());

        searchDate.setText(dateCurrent);
        sell_list_adapter = new Shop_sell_history_main_adapter(sell_list);

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
        //search by product properties
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                progressBar.setVisibility(View.GONE);
                if (!search.getText().toString().trim().isEmpty()) {
                    search_history(search.getText().toString().trim());
                } else {
                    if (select_type == 3)
                        all_sell_summary();
                    else if (select_type == 2)
                        selected_sell_summary();
                    else
                        fetch_daily_sell_summary();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void search_history(String value) {
        shopSellHistoryViewModel.getAlllist(shopID, 0, 100).observe(getViewLifecycleOwner(), new Observer<List<shop_sell_history_list_response>>() {
            @Override
            public void onChanged(List<shop_sell_history_list_response> shop_sell_history_list_responses) {
                sell_list = new ArrayList<>();
                sell_list_adapter = new Shop_sell_history_main_adapter(sell_list);
                sell_list_adapter.setOnItemClickListener(Shop_sell_history_fragment.this::OnItemClick);
                sellHistoryListView.setAdapter(sell_list_adapter);
                //sell_list_adapter.setOnClickListener(Tali_khata_fragment.this::OnItemClick);

                sellHistoryListView.setAdapter(sell_list_adapter);

                for (int i = 0; i < shop_sell_history_list_responses.size(); i++) {
                    String brand_code = shop_sell_history_list_responses.get(i).getBrand() + shop_sell_history_list_responses.get(i).getCode();
                    if ((shop_sell_history_list_responses.get(i).getProduct_id().contains(value) || shop_sell_history_list_responses.get(i).getProduct_name().toLowerCase().contains(value.toLowerCase())) || (shop_sell_history_list_responses.get(i).getBrand().toLowerCase().contains(value.toLowerCase())) || (brand_code.toLowerCase().contains(value.toLowerCase()))) {

                        sell_list.add(shop_sell_history_list_responses.get(i));
                    }

                }

                // sell_list = shop_tally_khata_responses;
                sell_list_adapter = new Shop_sell_history_main_adapter(sell_list);
                sell_list_adapter.setOnItemClickListener(Shop_sell_history_fragment.this::OnItemClick);
                //sell_list_adapter.setOnClickListener(Tali_khata_fragment.this::OnItemClick);

                sellHistoryListView.setAdapter(sell_list_adapter);
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
                        totalSellPriceText.setText("");
                        totalBuyPriceText.setText("");
                        totalProfitText.setText("");
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

    private void fetch_daily_sell_summary() {
        totalSellPriceText.setText("");
        totalBuyPriceText.setText("");
        totalProfitText.setText("");

        shopSellHistoryViewModel.getDailySummary(shopID, dateCurrent).observe(getViewLifecycleOwner(), new Observer<shop_sell_history_summary_response>() {
            @Override
            public void onChanged(shop_sell_history_summary_response shop_sell_history_summary_response) {

                Double sellPrice = Double.parseDouble(shop_sell_history_summary_response.getTotal_sellPrice());
                totalSellPriceText.setText(new DecimalFormat("##.##").format(sellPrice));

                Double buyPrice = Double.parseDouble(shop_sell_history_summary_response.getTotal_buyPrice());
                totalBuyPriceText.setText(new DecimalFormat("##.##").format(buyPrice));

                Double profit = Double.parseDouble(shop_sell_history_summary_response.getTotal_profit());
                totalProfitText.setText(new DecimalFormat("##.##").format(profit));

                select_type = 1;
                page1 = 1;
                end1 = 0;
                daily_history(page1, limit);
            }
        });
    }

    private void selected_sell_summary() {
        totalSellPriceText.setText("");
        totalBuyPriceText.setText("");
        totalProfitText.setText("");

        shopSellHistoryViewModel.getSelectedSummary(shopID, fromText.getText().toString().trim(), toText.getText().toString().trim()).observe(getViewLifecycleOwner(), new Observer<shop_sell_history_summary_response>() {
            @Override
            public void onChanged(shop_sell_history_summary_response shop_sell_history_summary_response) {
                Double sellPrice = Double.parseDouble(shop_sell_history_summary_response.getTotal_sellPrice());
                totalSellPriceText.setText(new DecimalFormat("##.##").format(sellPrice));

                Double buyPrice = Double.parseDouble(shop_sell_history_summary_response.getTotal_buyPrice());
                totalBuyPriceText.setText(new DecimalFormat("##.##").format(buyPrice));

                Double profit = Double.parseDouble(shop_sell_history_summary_response.getTotal_profit());
                totalProfitText.setText(new DecimalFormat("##.##").format(profit));

                page2 = 1;
                select_type = 2;
                end2 = 0;
                selected_history(page2, limit);
            }
        });

    }

    private void daily_history(int page, int limit) {
        progressBar.setVisibility(View.GONE);
        if (page == 1) {
            sell_list.clear();
            sell_list_adapter.notifyDataSetChanged();
            sell_list = new ArrayList<>();
            sell_list_adapter = new Shop_sell_history_main_adapter(sell_list);
            sell_list_adapter.setOnItemClickListener(Shop_sell_history_fragment.this::OnItemClick);
            sellHistoryListView.setAdapter(sell_list_adapter);

        }

        //Toast.makeText(getActivity(),"yes",Toast.LENGTH_SHORT).show();
        shopSellHistoryViewModel.getDailylist(shopID, searchDate.getText().toString().trim(), page, limit).observe(getViewLifecycleOwner(), new Observer<List<shop_sell_history_list_response>>() {
            @Override
            public void onChanged(List<shop_sell_history_list_response> shop_sell_history_list_responses) {
                for (int i = 0; i < shop_sell_history_list_responses.size(); i++) {
                    sell_list.add(shop_sell_history_list_responses.get(i));
                }

                if (shop_sell_history_list_responses.size() < limit) {
                    end1 = 1;
                }
                sell_list_adapter = new Shop_sell_history_main_adapter(sell_list);
                sell_list_adapter.setOnItemClickListener(Shop_sell_history_fragment.this::OnItemClick);
                //sell_list_adapter.setOnClickListener(Tali_khata_fragment.this::OnItemClick);
                sellHistoryListView.setAdapter(sell_list_adapter);
            }
        });

    }

    private void all_sell_summary() {
        totalBuyPriceText.setText("");
        totalSellPriceText.setText("");
        totalProfitText.setText("");

        shopSellHistoryViewModel.getAllSummary(shopID).observe(getViewLifecycleOwner(), new Observer<shop_sell_history_summary_response>() {
            @Override
            public void onChanged(shop_sell_history_summary_response shop_sell_history_summary_response) {
                Double sellPrice = Double.parseDouble(shop_sell_history_summary_response.getTotal_sellPrice());
                totalSellPriceText.setText(new DecimalFormat("##.##").format(sellPrice));

                Double buyPrice = Double.parseDouble(shop_sell_history_summary_response.getTotal_buyPrice());
                totalBuyPriceText.setText(new DecimalFormat("##.##").format(buyPrice));

                Double profit = Double.parseDouble(shop_sell_history_summary_response.getTotal_profit());
                totalProfitText.setText(new DecimalFormat("##.##").format(profit));

                page3 = 1;
                end3 = 0;
                select_type = 3;
                all_history(page3, limit);
            }
        });
    }

    private void all_history(int page, int limit) {
        if (page == 1) {
            sell_list.clear();
            sell_list_adapter.notifyDataSetChanged();
            sell_list = new ArrayList<>();
            sell_list_adapter = new Shop_sell_history_main_adapter(sell_list);
            sell_list_adapter.setOnItemClickListener(Shop_sell_history_fragment.this::OnItemClick);
            sellHistoryListView.setAdapter(sell_list_adapter);
        }
        //sell_list_adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        shopSellHistoryViewModel.getAlllist(shopID, page, limit).observe(getViewLifecycleOwner(), new Observer<List<shop_sell_history_list_response>>() {
            @Override
            public void onChanged(List<shop_sell_history_list_response> shop_sell_history_list_responses) {
                for (int i = 0; i < shop_sell_history_list_responses.size(); i++) {
                    sell_list.add(shop_sell_history_list_responses.get(i));
                }
                if (shop_sell_history_list_responses.size() < limit) {
                    end3 = 1;
                }
                // sell_list = shop_tally_khata_responses;
                sell_list_adapter = new Shop_sell_history_main_adapter(sell_list);
                sell_list_adapter.setOnItemClickListener(Shop_sell_history_fragment.this::OnItemClick);
                //sell_list_adapter.setOnClickListener(Tali_khata_fragment.this::OnItemClick);

                sellHistoryListView.setAdapter(sell_list_adapter);
            }
        });
    }

    private void selected_history(int page, int limit) {
        progressBar.setVisibility(View.GONE);
        if (page == 1) {
            sell_list.clear();
            sell_list_adapter.notifyDataSetChanged();
            sell_list = new ArrayList<>();
            sell_list_adapter = new Shop_sell_history_main_adapter(sell_list);
            sell_list_adapter.setOnItemClickListener(Shop_sell_history_fragment.this::OnItemClick);
            sellHistoryListView.setAdapter(sell_list_adapter);
        }

        shopSellHistoryViewModel.getSelectedlist(shopID, fromText.getText().toString().trim(), toText.getText().toString().trim(), page, limit).observe(getViewLifecycleOwner(), new Observer<List<shop_sell_history_list_response>>() {
            @Override
            public void onChanged(List<shop_sell_history_list_response> shop_sell_history_list_responses) {
                for (int i = 0; i < shop_sell_history_list_responses.size(); i++) {
                    sell_list.add(shop_sell_history_list_responses.get(i));
                }
                if (shop_sell_history_list_responses.size() < limit) {
                    end2 = 1;
                }
                // sell_list = shop_tally_khata_responses;
                sell_list_adapter = new Shop_sell_history_main_adapter(sell_list);
                sell_list_adapter.setOnItemClickListener(Shop_sell_history_fragment.this::OnItemClick);
                //sell_list_adapter.setOnClickListener(Tali_khata_fragment.this::OnItemClick);

                sellHistoryListView.setAdapter(sell_list_adapter);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_sell_history_fragment, container, false);

        totalSellPriceText = (TextView) view.findViewById(R.id.totalSellID);
        totalBuyPriceText = (TextView) view.findViewById(R.id.totalBuyID);
        totalProfitText = (TextView) view.findViewById(R.id.totalProfitID);

        sellHistoryListView = (RecyclerView) view.findViewById(R.id.sellHistoryListID);

        searchDate = (EditText) view.findViewById(R.id.dateEditID);
        search = (EditText) view.findViewById(R.id.SearchID);
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

        sellHistoryListView.setHasFixedSize(true);
        sellHistoryListView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
                            daily_history(page1, limit);
                        }
                    } else if (select_type == 2) {
                        if (end2 == 0) {
                            page2++;
                            selected_history(page2, limit);
                        }
                    } else if (select_type == 3) {
                        if (end3 == 0) {
                            page3++;
                            all_history(page3, limit);
                        }
                    }

                }
            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
        if (item.equals("Total Sell History")) {
            searchLayout.setVisibility(View.GONE);
            customSearchLayout.setVisibility(View.GONE);
            sell_list = new ArrayList<>();
            sell_list_adapter = new Shop_sell_history_main_adapter(sell_list);
            sellHistoryListView.setAdapter(sell_list_adapter);
            all_sell_summary();


        } else if (item.equals("Daily Sell History")) {
            customSearchLayout.setVisibility(View.GONE);
            searchLayout.setVisibility(View.VISIBLE);
            dateCurrent = new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date());
            searchDate.setText(dateCurrent);

            sell_list = new ArrayList<>();
            sell_list_adapter = new Shop_sell_history_main_adapter(sell_list);
            sellHistoryListView.setAdapter(sell_list_adapter);
            fetch_daily_sell_summary();

        } else if (item.equals("Custom Date Sell History")) {
            searchLayout.setVisibility(View.GONE);
            customSearchLayout.setVisibility(View.VISIBLE);
            sell_list.clear();
            sell_list_adapter.notifyDataSetChanged();
            sell_list = new ArrayList<>();
            sell_list_adapter = new Shop_sell_history_main_adapter(sell_list);
            sellHistoryListView.setAdapter(sell_list_adapter);
            totalSellPriceText.setText("");
            totalProfitText.setText("");
            totalBuyPriceText.setText("");
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

        shop_sell_history_list_response history = sell_list.get(position);
        Dialog history_detailsAlert = new Dialog(getActivity());
        history_detailsAlert.setContentView(R.layout.shop_product_sell_history_details_alert);
        history_detailsAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        history_detailsAlert.setCancelable(true);
        ImageView productImage, close;
        TextView productId, productName, productAmount, productType, productBrand, productCode, sellPrice, buyPrice, sellProfit, sellCustomer, sellDate;
        productImage = (ImageView) history_detailsAlert.findViewById(R.id.productImage);
        close = (ImageView) history_detailsAlert.findViewById(R.id.closeID);
        productId = (TextView) history_detailsAlert.findViewById(R.id.productID);
        productName = (TextView) history_detailsAlert.findViewById(R.id.product_nameID);
        productAmount = (TextView) history_detailsAlert.findViewById(R.id.amountID);
        productType = (TextView) history_detailsAlert.findViewById(R.id.typeID);
        productBrand = (TextView) history_detailsAlert.findViewById(R.id.brandID);
        productCode = (TextView) history_detailsAlert.findViewById(R.id.codeID);
        sellPrice = (TextView) history_detailsAlert.findViewById(R.id.sell_priceID);
        buyPrice = (TextView) history_detailsAlert.findViewById(R.id.buy_priceID);
        sellProfit = (TextView) history_detailsAlert.findViewById(R.id.profitID);
        sellCustomer = (TextView) history_detailsAlert.findViewById(R.id.customerID);
        sellDate = (TextView) history_detailsAlert.findViewById(R.id.dateID);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history_detailsAlert.dismiss();
            }
        });

        history_detailsAlert.show();

        ImageHelper.imageLoader(getActivity(), productImage, history.getProduct_image());
        productId.setText(history.getProduct_id());
        productName.setText(history.getProduct_name());
        productAmount.setText(history.getProduct_amount());
        productType.setText(history.getProduct_type());
        productBrand.setText(history.getBrand());
        productCode.setText(history.getCode());
        sellPrice.setText(history.getSell_price());
        buyPrice.setText(history.getBuy_price());
        sellProfit.setText(history.getProfit());
        sellDate.setText(history.getDate());
        sellCustomer.setText(history.getCustomer());

    }
}