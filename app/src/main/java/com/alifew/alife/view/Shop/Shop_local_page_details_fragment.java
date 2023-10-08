package com.alifew.alife.view.Shop;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alifew.alife.Custom_Type.Local_business_title_item;
import com.alifew.alife.R;
import com.alifew.alife.adapter.Shop_local_page_details_adapter;
import com.alifew.alife.adapter.Shop_local_page_show_details_adapter;
import com.alifew.alife.model.add_local_business_response;
import com.alifew.alife.model.get_local_business_details_response;
import com.alifew.alife.model.shop_local_page_item_list_response;
import com.alifew.alife.viewmodel.Local_business;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Shop_local_page_details_fragment extends Fragment implements Shop_local_page_details_adapter.OnItemClickListener {

    String title_id;
    RecyclerView recyclerView;
    ExtendedFloatingActionButton addButton;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;

    int page = 1, limit = 10, end = 0;
    Local_business local_business;

    private List<shop_local_page_item_list_response> Data;
    private Shop_local_page_details_adapter adapter;
    Dialog show_details, add_details;
    int item_length = 0;
    EditText NAME, AMOUNT, PRICE;
    TextView TOTAL_PRICE;
    ImageView CROSS;
    int point = 0;
    String myFormat = "yyyy-MM-dd";

    public Shop_local_page_details_fragment(String title_id) {
        this.title_id = title_id;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        main();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Add", Toast.LENGTH_LONG).show();

                Dialog alert = new Dialog(getActivity());
                alert.setContentView(R.layout.shop_local_page_add_details_alert);
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alert.show();
                alert.setCancelable(false);
                EditText name = (EditText) alert.findViewById(R.id.nameTextID);
                EditText amount = (EditText) alert.findViewById(R.id.amountText);
                EditText price = (EditText) alert.findViewById(R.id.priceTextID);
                TextView total_price = (TextView) alert.findViewById(R.id.totalPriceID);
                ImageView closeButton = (ImageView) alert.findViewById(R.id.closeID);

                TextInputEditText titleText = (TextInputEditText) alert.findViewById(R.id.titleText);
                TextInputLayout titleError = (TextInputLayout) alert.findViewById(R.id.titleErrorID);

                ImageView add = (ImageView) alert.findViewById(R.id.addButtonID);
                AppCompatButton saveButton = (AppCompatButton) alert.findViewById(R.id.saveButtonID);
                TextView totalAmount = (TextView) alert.findViewById(R.id.totalAmountID);
                TextView totalPrice = (TextView) alert.findViewById(R.id.wholePriceID);
                item_length = 0;

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });

                amount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!amount.getText().toString().trim().isEmpty()) {
                            if (!price.getText().toString().trim().isEmpty()) {

                                Double number = Double.parseDouble(amount.getText().toString().trim());
                                Double unit_price = Double.parseDouble(price.getText().toString().trim());
                                Double total = number * unit_price;
                                total_price.setText(String.valueOf(new DecimalFormat("##.##").format(total)));

                                //unit_profit_with_discount.setText(String.valueOf(new DecimalFormat("##.##").format(discountprofitOne)));

                                if (item_length == 0) {
                                    totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(number)));
                                    totalPrice.setText(String.valueOf(new DecimalFormat("##.##").format(total)));

                                } else {
                                    LinearLayout layout = (LinearLayout) alert.findViewById(R.id.holder_layout);
                                    for (int i = 0; i < item_length; i++) {
                                        View single_layout = layout.getChildAt(i);
                                        EditText amount_text = (EditText) single_layout.findViewById(R.id.amountText);
                                        EditText price_text = (EditText) single_layout.findViewById(R.id.priceTextID);
                                        if (!price_text.getText().toString().trim().isEmpty() && !amount_text.getText().toString().trim().isEmpty()) {
                                            total += Double.parseDouble(amount_text.getText().toString().trim()) * Double.parseDouble(price_text.getText().toString().trim());
                                            number += Double.parseDouble(amount_text.getText().toString().trim());
                                        }
                                        //  total+=Double.parseDouble(layout.getChildAt(i).)
                                    }

                                    totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(number)));
                                    totalPrice.setText(String.valueOf(new DecimalFormat("##.##").format(total)));

                                }
                            } else {
                                total_price.setText("");
                                totalAmount.setText("");
                                totalPrice.setText("");

                            }
                        } else {
                            total_price.setText("");
                            totalAmount.setText("");
                            totalPrice.setText("");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                price.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!price.getText().toString().trim().isEmpty()) {
                            if (!amount.getText().toString().trim().isEmpty()) {
                                Double number = Double.parseDouble(amount.getText().toString().trim());
                                Double unit_price = Double.parseDouble(price.getText().toString().trim());
                                Double total = number * unit_price;
                                total_price.setText(String.valueOf(new DecimalFormat("##.##").format(total)));
                                //unit_profit_with_discount.setText(String.valueOf(new DecimalFormat("##.##").format(discountprofitOne)));
                                if (item_length == 0) {
                                    totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(number)));
                                    totalPrice.setText(String.valueOf(new DecimalFormat("##.##").format(total)));

                                } else {
                                    LinearLayout layout = (LinearLayout) alert.findViewById(R.id.holder_layout);
                                    for (int i = 0; i < item_length; i++) {
                                        View single_layout = layout.getChildAt(i);
                                        EditText amount_text = (EditText) single_layout.findViewById(R.id.amountText);
                                        EditText price_text = (EditText) single_layout.findViewById(R.id.priceTextID);
                                        if (!price_text.getText().toString().trim().isEmpty() && !amount_text.getText().toString().trim().isEmpty()) {
                                            total += Double.parseDouble(amount_text.getText().toString().trim()) * Double.parseDouble(price_text.getText().toString().trim());
                                            number += Double.parseDouble(amount_text.getText().toString().trim());
                                        }
                                        //  total+=Double.parseDouble(layout.getChildAt(i).)
                                    }

                                    totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(number)));
                                    totalPrice.setText(String.valueOf(new DecimalFormat("##.##").format(total)));

                                }
                            } else {
                                total_price.setText("");
                                totalAmount.setText("");
                                totalPrice.setText("");

                            }
                        } else {
                            total_price.setText("");
                            totalAmount.setText("");
                            totalPrice.setText("");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                add.setOnClickListener(new View.OnClickListener() {
                    LinearLayout layout = (LinearLayout) alert.findViewById(R.id.holder_layout);

                    @Override
                    public void onClick(View v) {

                        View dynamicView = LayoutInflater.from(getActivity()).inflate(R.layout.add_title_details_daynamic_layout, null, false);
                        NAME = (EditText) dynamicView.findViewById(R.id.nameTextID);
                        AMOUNT = (EditText) dynamicView.findViewById(R.id.amountText);
                        PRICE = (EditText) dynamicView.findViewById(R.id.priceTextID);
                        TOTAL_PRICE = (TextView) dynamicView.findViewById(R.id.totalPriceID);
                        CROSS = (ImageView) dynamicView.findViewById(R.id.crossID);
                        AMOUNT.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                                if (!AMOUNT.getText().toString().trim().isEmpty() && !PRICE.getText().toString().trim().isEmpty()) {
                                    Double number = Double.parseDouble(AMOUNT.getText().toString().trim());
                                    Double unit_price = Double.parseDouble(PRICE.getText().toString().trim());
                                    Double total = number * unit_price;
                                    TOTAL_PRICE.setText(String.valueOf(new DecimalFormat("##.##").format(total)));
                                    if (item_length > 0) {

                                        LinearLayout layout = (LinearLayout) alert.findViewById(R.id.holder_layout);
                                        total = Double.parseDouble(total_price.getText().toString().trim());
                                        number = Double.parseDouble(amount.getText().toString().trim());
                                        for (int i = 0; i < item_length; i++) {
                                            View single_layout = layout.getChildAt(i);
                                            EditText amount_text = (EditText) single_layout.findViewById(R.id.amountText);
                                            EditText price_text = (EditText) single_layout.findViewById(R.id.priceTextID);
                                            if (!price_text.getText().toString().trim().isEmpty() && !amount_text.getText().toString().trim().isEmpty()) {
                                                total += Double.parseDouble(amount_text.getText().toString().trim()) * Double.parseDouble(price_text.getText().toString().trim());
                                                number += Double.parseDouble(amount_text.getText().toString().trim());
                                            }
                                            //  total+=Double.parseDouble(layout.getChildAt(i).)
                                        }
                                        totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(number)));
                                        totalPrice.setText(String.valueOf(new DecimalFormat("##.##").format(total)));


                                    } else {
                                        total = Double.parseDouble(total_price.getText().toString().trim()) + Double.parseDouble(TOTAL_PRICE.getText().toString().trim());

                                        number = Double.parseDouble(amount.getText().toString().trim()) + Double.parseDouble(AMOUNT.getText().toString().trim());

                                        totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(number)));
                                        totalPrice.setText(String.valueOf(new DecimalFormat("##.##").format(total)));

                                    }


                                } else {
                                    TOTAL_PRICE.setText("");
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                        PRICE.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                if (!AMOUNT.getText().toString().trim().isEmpty() && !PRICE.getText().toString().trim().isEmpty()) {
                                    Double number = Double.parseDouble(AMOUNT.getText().toString().trim());
                                    Double unit_price = Double.parseDouble(PRICE.getText().toString().trim());
                                    Double total = number * unit_price;
                                    TOTAL_PRICE.setText(String.valueOf(new DecimalFormat("##.##").format(total)));
                                    if (item_length > 0) {
                                        LinearLayout layout = (LinearLayout) alert.findViewById(R.id.holder_layout);
                                        total = Double.parseDouble(total_price.getText().toString().trim());
                                        number = Double.parseDouble(amount.getText().toString().trim());
                                        for (int i = 0; i < item_length; i++) {
                                            View single_layout = layout.getChildAt(i);
                                            EditText amount_text = (EditText) single_layout.findViewById(R.id.amountText);
                                            EditText price_text = (EditText) single_layout.findViewById(R.id.priceTextID);
                                            if (!price_text.getText().toString().trim().isEmpty() && !amount_text.getText().toString().trim().isEmpty()) {
                                                total += Double.parseDouble(amount_text.getText().toString().trim()) * Double.parseDouble(price_text.getText().toString().trim());
                                                number += Double.parseDouble(amount_text.getText().toString().trim());
                                            }
                                            //  total+=Double.parseDouble(layout.getChildAt(i).)
                                        }
                                        totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(number)));
                                        totalPrice.setText(String.valueOf(new DecimalFormat("##.##").format(total)));


                                    } else {
                                        total = Double.parseDouble(total_price.getText().toString().trim()) + Double.parseDouble(TOTAL_PRICE.getText().toString().trim());

                                        number = Double.parseDouble(amount.getText().toString().trim()) + Double.parseDouble(AMOUNT.getText().toString().trim());

                                        totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(number)));
                                        totalPrice.setText(String.valueOf(new DecimalFormat("##.##").format(total)));

                                    }

                                } else {
                                    TOTAL_PRICE.setText("");
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                        CROSS.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                View single_Layout = layout.getChildAt(item_length - 1);
                                item_length--;
                                layout.removeView(dynamicView);
                                if (item_length == 0) {
                                    Double total = Double.parseDouble(total_price.getText().toString().trim());
                                    Double number = Double.parseDouble(amount.getText().toString().trim());
                                    totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(number)));
                                    totalPrice.setText(String.valueOf(new DecimalFormat("##.##").format(total)));

                                } else {
                                    Double total = Double.parseDouble(total_price.getText().toString().trim());
                                    Double number = Double.parseDouble(amount.getText().toString().trim());
                                    LinearLayout layout = (LinearLayout) alert.findViewById(R.id.holder_layout);
                                    for (int i = 0; i < item_length; i++) {
                                        View single_layout = layout.getChildAt(i);
                                        EditText amount_text = (EditText) single_layout.findViewById(R.id.amountText);
                                        EditText price_text = (EditText) single_layout.findViewById(R.id.priceTextID);
                                        if (!price_text.getText().toString().trim().isEmpty() && !amount_text.getText().toString().trim().isEmpty()) {
                                            total += Double.parseDouble(amount_text.getText().toString().trim()) * Double.parseDouble(price_text.getText().toString().trim());
                                            number += Double.parseDouble(amount_text.getText().toString().trim());
                                        }
                                        //  total+=Double.parseDouble(layout.getChildAt(i).)
                                    }
                                    totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(number)));
                                    totalPrice.setText(String.valueOf(new DecimalFormat("##.##").format(total)));

                                }
                            }
                        });

                        if (price.getText().toString().trim().isEmpty() || amount.getText().toString().trim().isEmpty() || name.getText().toString().trim().isEmpty()) {
                            Toast.makeText(getActivity(), "Fill before field properly", Toast.LENGTH_SHORT).show();
                            totalAmount.setText("");
                            totalPrice.setText("");
                        } else {
                            if (item_length > 0) {
                                View single_Layout = layout.getChildAt(item_length - 1);
                                EditText name_text = (EditText) single_Layout.findViewById(R.id.nameTextID);
                                EditText amount_text = (EditText) single_Layout.findViewById(R.id.amountText);
                                EditText price_text = (EditText) single_Layout.findViewById(R.id.priceTextID);
                                if (name_text.getText().toString().trim().isEmpty() || amount_text.getText().toString().trim().isEmpty() || price_text.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(getActivity(), "Fill before field properly!!", Toast.LENGTH_SHORT).show();
                                } else {
                                    for (int i = item_length; i > 0; i--) {
                                        single_Layout = layout.getChildAt(i - 1);
                                        name_text = (EditText) single_Layout.findViewById(R.id.nameTextID);
                                        amount_text = (EditText) single_Layout.findViewById(R.id.amountText);
                                        price_text = (EditText) single_Layout.findViewById(R.id.priceTextID);
                                        TextView total_price = (TextView) single_Layout.findViewById(R.id.totalPriceID);

                                        NAME.setText(name_text.getText().toString().trim());
                                        AMOUNT.setText(amount_text.getText().toString().trim());
                                        PRICE.setText(price_text.getText().toString().trim());
                                        TOTAL_PRICE.setText(total_price.getText().toString().trim());

                                        NAME = (EditText) dynamicView.findViewById(R.id.nameTextID);
                                        AMOUNT = (EditText) dynamicView.findViewById(R.id.amountText);
                                        PRICE = (EditText) dynamicView.findViewById(R.id.priceTextID);
                                        TOTAL_PRICE = (TextView) dynamicView.findViewById(R.id.totalPriceID);     // layout.addView(single_Layout,i);
                                    }
                                    NAME.setText("");
                                    AMOUNT.setText("");
                                    PRICE.setText("");
                                    TOTAL_PRICE.setText("");
                                    layout.addView(dynamicView);
                                    item_length = layout.getChildCount();

                                }


                            } else {

                                layout.addView(dynamicView);
                                item_length = layout.getChildCount();
                            }
                        }

                    }
                });

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo info = manager.getActiveNetworkInfo();
                        if (info == null) {
                            Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                        } else {
                            String subtitle = titleText.getText().toString().trim();
                            titleError.setErrorEnabled(false);

                            if (TextUtils.isEmpty(subtitle)) {
                                subtitle = " ";
                            }
                            Dialog dialog = new Dialog(getActivity());
                            dialog.setContentView(R.layout.loader);
                            dialog.show();
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.setCancelable(false);
                            // Toast.makeText(getActivity(),"yess",Toast.LENGTH_SHORT).show();
                            List<Local_business_title_item> items = new ArrayList<>();
                            if (!amount.getText().toString().trim().isEmpty() && !price.getText().toString().trim().isEmpty() && !name.getText().toString().trim().isEmpty()) {
                                Local_business_title_item item = new Local_business_title_item();
                                item.setName(name.getText().toString().trim());
                                item.setAmount(amount.getText().toString().trim());
                                item.setPrice(price.getText().toString().trim());
                                items.add(item);
                            }
                            LinearLayout layout = (LinearLayout) alert.findViewById(R.id.holder_layout);

                            for (int i = 0; i < item_length; i++) {
                                View single_layout = layout.getChildAt(i);
                                EditText name_text = (EditText) single_layout.findViewById(R.id.nameTextID);
                                EditText amount_text = (EditText) single_layout.findViewById(R.id.amountText);
                                EditText price_text = (EditText) single_layout.findViewById(R.id.priceTextID);
                                if (!price_text.getText().toString().trim().isEmpty() && !amount_text.getText().toString().trim().isEmpty() && !name_text.getText().toString().trim().isEmpty()) {
                                    Local_business_title_item item = new Local_business_title_item();
                                    item.setName(name_text.getText().toString().trim());
                                    item.setAmount(amount_text.getText().toString().trim());
                                    item.setPrice(price_text.getText().toString().trim());
                                    items.add(item);
                                }
                                //  total+=Double.parseDouble(layout.getChildAt(i).)
                            }
                            if (items.size() > 0) {
                                point = 0;
                                local_business.add_subtitle(subtitle, title_id).observe(getViewLifecycleOwner(), new Observer<add_local_business_response>() {
                                    @Override
                                    public void onChanged(add_local_business_response add_local_business_response) {
                                        if (!(add_local_business_response.getMessage().equals("fail to add") || add_local_business_response.getMessage().equals("Something Error"))) {
                                            for (int i = 0; i < items.size(); i++) {

                                                local_business.add_details(items.get(i).getName(), items.get(i).getAmount(), items.get(i).getPrice(), add_local_business_response.getMessage()).observe(getViewLifecycleOwner(), new Observer<add_local_business_response>() {
                                                    @Override
                                                    public void onChanged(add_local_business_response add_local_business_response) {

                                                    }
                                                });

                                            }
                                            dialog.dismiss();
                                            alert.dismiss();
                                            main();
                                        } else {
                                            Toast.makeText(getActivity(), add_local_business_response.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                dialog.dismiss();
                                Toast.makeText(getActivity(), "No Data Insert", Toast.LENGTH_SHORT).show();
                            }
                        }


                    }
                });
            }
        });
    }

    private void main() {
        Data = new ArrayList<>();
        page = 1;
        end = 0;
        adapter = new Shop_local_page_details_adapter(Data);

        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(Shop_local_page_details_fragment.this::OnItemClick);
        //getsubTitle(page, limit);
        getItem(page, limit);
    }

    public void getItem(int Page, int Limit) {
        local_business = new ViewModelProvider(getActivity()).get(Local_business.class);
        local_business.getItem(title_id, Page, Limit).observe(getViewLifecycleOwner(), new Observer<List<shop_local_page_item_list_response>>() {
            @Override
            public void onChanged(List<shop_local_page_item_list_response> shop_local_page_item_list_responses) {
                progressBar.setVisibility(View.GONE);
                if (shop_local_page_item_list_responses.size() < Limit) {
                    end = 1;
                }
                if (Page == 1) {
                    Data = new ArrayList<>();
                    adapter = new Shop_local_page_details_adapter(Data);
                    adapter.setOnClickListener(Shop_local_page_details_fragment.this::OnItemClick);
                    recyclerView.setAdapter(adapter);
                    Double total_price = 0.0;

                    if (shop_local_page_item_list_responses.size() > 0) {
                        total_price = Double.parseDouble(shop_local_page_item_list_responses.get(0).getTotal_price());
                    }
                    TOTAL_PRICE.setText(String.valueOf(new DecimalFormat("##.##").format(total_price)));


                }
                for (int i = 0; i < shop_local_page_item_list_responses.size(); i++) {
                    Data.add(shop_local_page_item_list_responses.get(i));
                }
                adapter = new Shop_local_page_details_adapter(Data);
                adapter.setOnClickListener(Shop_local_page_details_fragment.this::OnItemClick);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    /*public void getItem(int Page, int Limit) {
        local_business = new ViewModelProvider(getActivity()).get(Local_business.class);
        local_business.getItem(title_id,Page,Limit).observe(getViewLifecycleOwner(), new Observer<List<shop_local_page_item_list_response>>() {
            @Override
            public void onChanged(List<shop_local_page_item_list_response> shop_local_page_item_list_responses) {
                Data = new ArrayList<>();

               // Data = shop_local_page_item_list_responses;
                adapter = new Shop_local_page_details_adapter(Data);
                adapter.setOnClickListener(Shop_local_page_details_fragment.this::OnItemClick);
                recyclerView.setAdapter(adapter);

            }
        });

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_local_page_details_fragment, container, false);

        //Toast.makeText(getActivity(), title_id, Toast.LENGTH_LONG).show();

        addButton = (ExtendedFloatingActionButton) view.findViewById(R.id.addButtonID);
        TOTAL_PRICE = (TextView) view.findViewById(R.id.totalPriceID);
        recyclerView = (RecyclerView) view.findViewById(R.id.itemView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && addButton.getVisibility() == View.VISIBLE) {
                    addButton.hide();
                } else if (dy < 0 && addButton.getVisibility() != View.VISIBLE) {
                    addButton.show();
                }
            }
        });

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > oldScrollY) {
                    addButton.hide();
                } else {
                    addButton.show();
                }
                //addButton.show();
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (end == 0) {
                        progressBar.setVisibility(View.VISIBLE);
                        page++;
                        getItem(page, limit);
                        //filter(page, limit);
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void OnItemClick(int position) {
        //Toast.makeText(getActivity(), data.get(position).getTitle(), Toast.LENGTH_SHORT).show();
        shop_local_page_item_list_response subtitle = Data.get(position);
        String subtitle_id = subtitle.getSubtitle_id();
        //Toast.makeText(getActivity(),"mmm",Toast.LENGTH_SHORT).show();
        local_business = new ViewModelProvider(getActivity()).get(Local_business.class);
        local_business.get_details(subtitle_id).observe(getViewLifecycleOwner(), new Observer<List<get_local_business_details_response>>() {
            @Override
            public void onChanged(List<get_local_business_details_response> get_local_business_details_responses) {
                if (get_local_business_details_responses.size() <= 0) {


                } else {
                    Dialog alert = new Dialog(getActivity());
                    alert.setContentView(R.layout.shop_local_page_show_details_alert);
                    alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alert.show();
                    alert.setCancelable(false);
                    RecyclerView detailsView = (RecyclerView) alert.findViewById(R.id.detailsViewID);
                    TextView total_number = (TextView) alert.findViewById(R.id.totalAmountID);
                    TextView total_price = (TextView) alert.findViewById(R.id.totalPriceID);
                    ImageView close = (ImageView) alert.findViewById(R.id.closeID);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                        }
                    });
                    double totalPrice = 0.0;
                    double totalAmount = 0.0;
                    for (int i = 0; i < get_local_business_details_responses.size(); i++) {
                        totalAmount += Double.parseDouble(get_local_business_details_responses.get(i).getAmount());
                        totalPrice += Double.parseDouble(get_local_business_details_responses.get(i).getAmount()) * Double.parseDouble(get_local_business_details_responses.get(i).getPrice());
                    }

                    total_number.setText(String.valueOf(new DecimalFormat("##.##").format(totalAmount)));
                    total_price.setText(String.valueOf(new DecimalFormat("##.##").format(totalPrice)));

                    detailsView.setHasFixedSize(true);
                    detailsView.setLayoutManager(new LinearLayoutManager(getContext()));
                    Shop_local_page_show_details_adapter details_adapter = new Shop_local_page_show_details_adapter(get_local_business_details_responses);
                    detailsView.setAdapter(details_adapter);

                }
            }
        });


    }

   /* @Override
    public void OnItemClick(int position) {
        //Toast.makeText(getActivity(), data.get(position).getTitle(), Toast.LENGTH_SHORT).show();
        get_local_business_subtitle_response subtitle = data.get(position);
        String subtitle_id = subtitle.getId();
        Toast.makeText(getActivity(),"mmm",Toast.LENGTH_SHORT).show();
        local_business = new ViewModelProvider(getActivity()).get(Local_business.class);
        local_business.get_details(subtitle_id).observe(getViewLifecycleOwner(), new Observer<List<get_local_business_details_response>>() {
            @Override
            public void onChanged(List<get_local_business_details_response> get_local_business_details_responses) {
                if (get_local_business_details_responses.size() <= 0) {


                } else {
                    Dialog alert = new Dialog(getActivity());
                    alert.setContentView(R.layout.shop_local_page_show_details_alert);
                    alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alert.show();
                    alert.setCancelable(false);
                    RecyclerView detailsView = (RecyclerView) alert.findViewById(R.id.detailsViewID);
                    TextView total_number = (TextView) alert.findViewById(R.id.totalAmountID);
                    TextView total_price = (TextView) alert.findViewById(R.id.totalPriceID);
                    ImageView close = (ImageView) alert.findViewById(R.id.closeID);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                        }
                    });
                    double totalPrice = 0.0;
                    double totalAmount = 0.0;
                    for (int i = 0; i < get_local_business_details_responses.size(); i++) {
                        totalAmount += Double.parseDouble(get_local_business_details_responses.get(i).getAmount());
                        totalPrice += Double.parseDouble(get_local_business_details_responses.get(i).getAmount()) * Double.parseDouble(get_local_business_details_responses.get(i).getPrice());
                    }

                    total_number.setText(String.valueOf(new DecimalFormat("##.##").format(totalAmount)));
                    total_price.setText(String.valueOf(new DecimalFormat("##.##").format(totalPrice)));

                    detailsView.setHasFixedSize(true);
                    detailsView.setLayoutManager(new LinearLayoutManager(getContext()));
                    Shop_local_page_show_details_adapter details_adapter = new Shop_local_page_show_details_adapter(get_local_business_details_responses);
                    detailsView.setAdapter(details_adapter);

                }
            }
        });
   

    }*/
}