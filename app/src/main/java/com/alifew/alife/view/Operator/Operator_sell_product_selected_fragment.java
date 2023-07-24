package com.alifew.alife.view.Operator;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alifew.alife.Custom_Type.ProductSel_type;
import com.alifew.alife.Custom_Type.ProductSell;
import com.alifew.alife.Custom_Type.Product_sell_offer;
import com.alifew.alife.R;
import com.alifew.alife.adapter.Shop_sell_type_select_adapter;
import com.alifew.alife.model.get_all_product_offer_response;
import com.alifew.alife.model.get_product_offer_response;
import com.alifew.alife.model.Get_product_response;
import com.alifew.alife.model.get_product_type_response;
import com.alifew.alife.model.shop_profile_response;
import com.alifew.alife.viewmodel.Get_product;
import com.alifew.alife.viewmodel.Get_product_offer;
import com.alifew.alife.viewmodel.Get_product_type;
import com.alifew.alife.viewmodel.Shop_profile;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Operator_sell_product_selected_fragment extends Fragment implements Shop_sell_type_select_adapter.OnItemSelectListener, Shop_sell_type_select_adapter.OnItemAddListener, Shop_sell_type_select_adapter.OnItemMinusListener {
    String productID, productName, productUnit, productPrice, productImage, shop_id, agent_id, product_buy_price;
    private List<ProductSell> productSellList;
    private List<ProductSel_type> productSel_types;
    TextView productNameText, productUnitText, stock, all_discount;
    TextView price, product_amount, noOffersText;
    Get_product_type get_product_type;
    Get_product get_product;
    Get_product_offer get_product_offer;
    List<get_product_type_response> types;
    private String product_types[];
    List<get_product_offer_response> offers;
    private String product_offers[];
    private String product_offers_price[];
    Spinner type_spinner, offerSpinner;
    Spinner offerSecondSpinner;
    private String type_id, type_name, type_count;
    private String offer_id;
    private String offer_type = "none";
    private String minimum_offer_amount = "0";
    private String minimum_offer_pricee = "0";
    private String offer_percentage = "0";
    Spinner offerTypeSpinner;
    String offerTypeText;
    int available_type = 0, getOfferState = 0;
    LinearLayout typeLayout, getOfferLayout, offerSpinnerLayout, amountlayout, getOfferMainLayout;
    LinearLayout offerSecondSpinnerLayout, offerMainLayout;
    ImageView arrowDown, arrowUp;
    AppCompatButton nextButton;
    private int type_check = 0;
    private Double type_amount = 0.0;
    private FragmentManager fragmentManager;
    List<String> offerType;

    private int offer_check = 0;
    RecyclerView typeView;
    private Shop_sell_type_select_adapter type_adapter;
    ImageView decButton, incButton;
    Shop_profile shop_profile;
    String product_discount_all = "9";
    List<get_all_product_offer_response> offer_all;
    int x;

    public Operator_sell_product_selected_fragment(String shop_id, String agent_id, String productID, List<ProductSell> productSellList) {
        this.shop_id = shop_id;
        this.agent_id = agent_id;
        this.productID = productID;
        this.productSellList = productSellList;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main();
    }

    private void main() {
        checkConnection();
        get_all_product_discount();

        // get_product();

        type_id = "0";
        type_name = "";
        offer_id = "0";

        types = new ArrayList<>();
        // get_product();
        get_type();


    }

    private void get_all_product_discount() {
        shop_profile = new ViewModelProvider(getActivity()).get(Shop_profile.class);
        shop_profile.getData(shop_id).observe(getViewLifecycleOwner(), new Observer<shop_profile_response>() {
            @Override
            public void onChanged(shop_profile_response shop_profile_response) {
                //product_discount_all = shop_profile_response.getAll_discount();
                all_discount.setText(shop_profile_response.getAll_discount());
                get_product();
            }
        });
    }

    private void get_offer() {

        get_product_offer = new ViewModelProvider(getActivity()).get(Get_product_offer.class);
        get_product_offer.getdata(productID).observe(getViewLifecycleOwner(), new Observer<List<get_product_offer_response>>() {
            @Override
            public void onChanged(List<get_product_offer_response> get_product_offer_responses) {
                get_product();
                offers = get_product_offer_responses;

                for (int i = 0; i < offers.size(); i++) {
                    if (Double.parseDouble(offers.get(i).getAmount()) > Double.parseDouble(stock.getText().toString().trim())) {
                        offers.remove(i);
                        i--;

                    }
                }

                if (offers.size() > 0) {
                    getOfferMainLayout.setVisibility(View.VISIBLE);
                    product_offers = new String[offers.size() + 1];
                    product_offers_price = new String[offers.size() + 1];
                    product_offers[0] = "Select offer";
                    for (int i = 0; i < offers.size(); i++) {
                        // Double price_value = Double.parseDouble(offers.get(i).getAmount()) * (Double.parseDouble(productPrice_with_discount) - (Double.parseDouble(productPrice_with_discount) * (Double.parseDouble(offers.get(i).getPrice()) / 100)));
                        product_offers[i + 1] = offers.get(i).getAmount() + productUnit + "+     " + offers.get(i).getPrice() + "%";

                        // product_offers[i + 1] = offers.get(i).getAmount() + " " + productUnit + "          " + String.valueOf(Double.parseDouble(offers.get(i).getAmount()) * (Double.parseDouble(productPrice_with_discount) - (Double.parseDouble(productPrice_with_discount) * (Double.parseDouble(offers.get(i).getPrice()) / 100)))) + " taka";
                        product_offers_price[i + 1] = offers.get(i).getPrice();
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, product_offers);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    offerSpinner.setAdapter(adapter);
                    show_offer_spinner();
                }
            }
        });

    }

    public void get_all_product_offer() {
        get_product_offer.get_all_data(shop_id).observe(getViewLifecycleOwner(), new Observer<List<get_all_product_offer_response>>() {
            @Override
            public void onChanged(List<get_all_product_offer_response> get_all_product_offer_responses) {
                offer_all = get_all_product_offer_responses;
                if (offer_all.size() > 0) {
                    getOfferMainLayout.setVisibility(View.VISIBLE);
                    product_offers = new String[offer_all.size() + 1];
                    product_offers_price = new String[offer_all.size() + 1];
                    product_offers[0] = "Select offer";
                    for (int i = 0; i < offer_all.size(); i++) {
                        // Double price_value = Double.parseDouble(offers.get(i).getAmount()) * (Double.parseDouble(productPrice_with_discount) - (Double.parseDouble(productPrice_with_discount) * (Double.parseDouble(offers.get(i).getPrice()) / 100)));
                        product_offers[i + 1] = offer_all.get(i).getMinimum_amount() + productUnit + "+          " + offer_all.get(i).getMinimum_price() + "taka+" + "       " + offer_all.get(i).getOffer_percentage() + "%";

                        // product_offers[i + 1] = offers.get(i).getAmount() + " " + productUnit + "          " + String.valueOf(Double.parseDouble(offers.get(i).getAmount()) * (Double.parseDouble(productPrice_with_discount) - (Double.parseDouble(productPrice_with_discount) * (Double.parseDouble(offers.get(i).getPrice()) / 100)))) + " taka";
                        product_offers_price[i + 1] = offer_all.get(i).getOffer_percentage();
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, product_offers);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    offerSecondSpinner.setAdapter(adapter);
                    show_all_product_offer_spinner();
                }
            }
        });

    }

    public void show_offer_spinner() {
        offerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    if (!product_amount.getText().toString().isEmpty()) {
                        // amountlayout.setVisibility(View.GONE);
                        // product_amount.setText(offers.get(position - 1).getAmount());
                        if (Double.parseDouble(product_amount.getText().toString().trim()) >= Double.parseDouble(offers.get(position - 1).getAmount())) {
                            offer_id = offers.get(position - 1).getId();
                            offer_check = 1;

                            //  Double price_value = Double.parseDouble(offers.get(position - 1).getAmount()) * (Double.parseDouble(productPrice_with_discount) - (Double.parseDouble(productPrice_with_discount) * (Double.parseDouble(offers.get(position - 1).getPrice()) / 100)));
                            //  price.setText(String.valueOf(Double.parseDouble(offers.get(position - 1).getAmount()) * (Double.parseDouble(productPrice) - (Double.parseDouble(productPrice) * (Double.parseDouble(offers.get(position - 1).getPrice()) / 100)))));
                            // price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));

                            Double percentage = Double.parseDouble(product_offers_price[position]);
                            Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * (Double.parseDouble(productPrice) - (Double.parseDouble(productPrice) * (percentage / 100)));
                            price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));

                            offer_type = "individual";
                            minimum_offer_amount = offers.get(position - 1).getAmount();
                            minimum_offer_pricee = "0";
                            offer_percentage = product_offers_price[position];
                            //  get_type();
                        } else {
                            Toast.makeText(getActivity(), "number of selected product is short of offer", Toast.LENGTH_SHORT).show();
                            offerSpinner.setSelection(0);
                            Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * (Double.parseDouble(productPrice));
                            price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                            offer_type = "none";
                            minimum_offer_amount = "0";
                            minimum_offer_pricee = "0";
                            offer_percentage = "0";
                        }
                    }
                } else {
                    //price.setText("");
                    //product_amount.setText("");
                    offer_id = "0";
                    offer_check = 0;
                    offer_type = "none";
                    minimum_offer_amount = "0";
                    minimum_offer_pricee = "0";
                    offer_percentage = "0";
                    if (product_amount.getText().toString().trim().isEmpty()) {
                        price.setText("0.0");
                    } else {
                        Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * (Double.parseDouble(productPrice));
                        price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                    }

                    // typeLayout.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void show_all_product_offer_spinner() {
        offerSecondSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    if (!product_amount.getText().toString().isEmpty()) {
                        if ((Double.parseDouble(product_amount.getText().toString().trim()) >= Double.parseDouble(offer_all.get(position - 1).getMinimum_amount())) && (Double.parseDouble(price.getText().toString().trim()) >= Double.parseDouble(offer_all.get(position - 1).getMinimum_price()))) {

                            // amountlayout.setVisibility(View.GONE);
                            // product_amount.setText(offers.get(position - 1).getAmount());
                            offer_id = offer_all.get(position - 1).getId();
                            offer_check = 1;

                            //  Double price_value = Double.parseDouble(offers.get(position - 1).getAmount()) * (Double.parseDouble(productPrice_with_discount) - (Double.parseDouble(productPrice_with_discount) * (Double.parseDouble(offers.get(position - 1).getPrice()) / 100)));
                            //  price.setText(String.valueOf(Double.parseDouble(offers.get(position - 1).getAmount()) * (Double.parseDouble(productPrice) - (Double.parseDouble(productPrice) * (Double.parseDouble(offers.get(position - 1).getPrice()) / 100)))));
                            // price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                            Double percentage = Double.parseDouble(product_offers_price[position]);
                            Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * (Double.parseDouble(productPrice) - (Double.parseDouble(productPrice) * (percentage / 100)));
                            price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                            offer_type = "whole";
                            minimum_offer_amount = offer_all.get(position - 1).getMinimum_amount();
                            minimum_offer_pricee = offer_all.get(position - 1).getMinimum_price();
                            offer_percentage = product_offers_price[position];
                            //  get_type();
                        } else {
                            Toast.makeText(getActivity(), "number of selected product is short of offer", Toast.LENGTH_SHORT).show();
                            offerSecondSpinner.setSelection(0);
                            Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * (Double.parseDouble(productPrice));
                            price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                            offer_type = "none";
                            minimum_offer_amount = "0";
                            minimum_offer_pricee = "0";
                            offer_percentage = "0";
                        }
                    }

                } else {
                    //price.setText("");
                    //product_amount.setText("");
                    offer_id = "0";
                    offer_check = 0;
                    offer_type = "none";
                    minimum_offer_amount = "0";
                    minimum_offer_pricee = "0";
                    offer_percentage = "0";
                    if (product_amount.getText().toString().trim().isEmpty()) {
                        price.setText("0.0");
                    } else {
                        Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * (Double.parseDouble(productPrice));
                        price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                    }
                    //Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * (Double.parseDouble(productPrice_with_discount));
                    // price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                    // typeLayout.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void get_product() {
        get_product = new ViewModelProvider(getActivity()).get(Get_product.class);
        get_product.getsingle_product(productID).observe(getViewLifecycleOwner(), new Observer<Get_product_response>() {
            @Override
            public void onChanged(Get_product_response get_product_response) {
                productNameText.setText(get_product_response.getProduct_name());
                productImage = get_product_response.getProduct_image();
                productUnitText.setText(get_product_response.getProduct_unit());
                productUnit = get_product_response.getProduct_unit();
                double selling_price = Double.parseDouble(get_product_response.getSelling_price());
                double discount = Double.parseDouble(get_product_response.getProduct_offer());
                double price_with_offer = selling_price - selling_price * (discount / 100);

                stock.setText(get_product_response.getStock_amount());
                if (Double.parseDouble(all_discount.getText().toString().trim()) > discount) {
                    discount = Double.parseDouble(all_discount.getText().toString().trim());
                }
                productPrice = String.valueOf(price_with_offer);
                product_buy_price = get_product_response.getBuy_price();
                //Toast.makeText(getActivity(),productID,Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void get_type() {

        get_product_type = new ViewModelProvider(getActivity()).get(Get_product_type.class);
        get_product_type.getdata(productID).observe(getViewLifecycleOwner(), new Observer<List<get_product_type_response>>() {
            @Override
            public void onChanged(List<get_product_type_response> get_product_type_responses) {
                types = get_product_type_responses;
                for (int i = 0; i < types.size(); i++) {
                    if (Double.parseDouble(types.get(i).getCount()) == 0.0) {
                        types.remove(i);
                        i--;
                    }
                }
                if (types.size() > 0) {
                    //layout visible
                    typeLayout.setVisibility(View.VISIBLE);
                    decButton.setVisibility(View.GONE);
                    incButton.setVisibility(View.GONE);
                    productSel_types = new ArrayList<>();
                    ProductSel_type sel_type;
                    for (int i = 0; i < types.size(); i++) {
                        sel_type = new ProductSel_type();
                        sel_type.setType_id(types.get(i).getId());
                        sel_type.setType_name(types.get(i).getType());
                        sel_type.setType_amount("0");
                        productSel_types.add(sel_type);
                    }
                    type_check = 1;
                    typeLayout.setVisibility(View.VISIBLE);
                    type_adapter = new Shop_sell_type_select_adapter(types, productSel_types);
                    type_adapter.setOnClickListener(Operator_sell_product_selected_fragment.this::OnTypeClick, Operator_sell_product_selected_fragment.this::OnTypeInc, Operator_sell_product_selected_fragment.this::OnTypeDec);
                    typeView.setAdapter(type_adapter);
                    //product_types = new String[types.size() + 1];
                    // product_types[0] = "Select Type";
                   /* for (int i = 0; i < types.size(); i++) {
                        product_types[i + 1] = types.get(i).getType() + "(" + types.get(i).getCount() + ")";
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, product_types);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    type_spinner.setAdapter(adapter);
                    show_type_spinner();*/
                } else {
                    typeLayout.setVisibility(View.INVISIBLE);
                    decButton.setVisibility(View.VISIBLE);
                    incButton.setVisibility(View.VISIBLE);
                    type_check = 0;
                    product_amount.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            get_product();
                            if (product_amount.getText().toString().isEmpty()) {
                                //typeLayout.setVisibility(View.INVISIBLE);
                                price.setText("");
                            } else {
                                if (Double.parseDouble(product_amount.getText().toString().trim()) > Double.parseDouble(stock.getText().toString().trim())) {
                                    typeLayout.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getActivity(), "amount must less than stock", Toast.LENGTH_SHORT).show();
                                    product_amount.setText("");
                                    price.setText("");
                                } else {
                                    Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * Double.parseDouble(productPrice);
                                    price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                                    get_offer();
                                    // get_type();
                                }
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }


            }
        });

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

    private void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_sell_product_selected_fragment, container, false);

        productNameText = (TextView) view.findViewById(R.id.productNameID);
        productUnitText = (TextView) view.findViewById(R.id.productUnitID);
        stock = (TextView) view.findViewById(R.id.stockAmountID);
        //price = (EditText) view.findViewById(R.id.priceID);
        price = (TextView) view.findViewById(R.id.priceID);

        product_amount = (TextView) view.findViewById(R.id.amountTextID);
        noOffersText = (TextView) view.findViewById(R.id.noOffersTextID);

        all_discount = (TextView) view.findViewById(R.id.allDiscountID);
        type_spinner = (Spinner) view.findViewById(R.id.typeSpinnerID);
        offerSpinner = (Spinner) view.findViewById(R.id.offerSpinnerID);
        offerTypeSpinner = (Spinner) view.findViewById(R.id.offerTypeSpinnerID);
        offerSecondSpinner = (Spinner) view.findViewById(R.id.offerSecondSpinnerID);
        typeView = (RecyclerView) view.findViewById(R.id.typeViewID);
        typeView.setHasFixedSize(true);
        typeView.setLayoutManager(new LinearLayoutManager(getContext()));

        typeLayout = (LinearLayout) view.findViewById(R.id.typeLayoutID);
        getOfferLayout = (LinearLayout) view.findViewById(R.id.getOfferLayoutID);
        offerSpinnerLayout = (LinearLayout) view.findViewById(R.id.offerSpinnerLayoutID);
        // amountlayout = (LinearLayout) view.findViewById(R.id.amountLayoutID);
        getOfferMainLayout = (LinearLayout) view.findViewById(R.id.getOfferMainLayoutID);
        offerSecondSpinnerLayout = (LinearLayout) view.findViewById(R.id.offerSecondSpinnerLayoutID);
        offerMainLayout = (LinearLayout) view.findViewById(R.id.offerMainLayoutID);
        arrowDown = (ImageView) view.findViewById(R.id.arrowDownID);
        arrowUp = (ImageView) view.findViewById(R.id.arrowUpID);
        nextButton = (AppCompatButton) view.findViewById(R.id.nextButtonID);
        decButton = (ImageView) view.findViewById(R.id.minusButtonID);
        incButton = (ImageView) view.findViewById(R.id.plusButtonID);

        decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double amount = Double.parseDouble(product_amount.getText().toString().trim());
                if (amount > 0.0) {
                    amount -= 1.0;
                    product_amount.setText(String.valueOf(amount));
                    Double price_value = amount * Double.parseDouble(productPrice);
                    price.setText(String.valueOf(price_value));
                }
            }
        });

        incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double amount = Double.parseDouble(product_amount.getText().toString().trim());
                amount += 1.0;
                if (amount <= Double.parseDouble(stock.getText().toString().trim())) {
                    product_amount.setText(String.valueOf(amount));
                    Double price_value = amount * Double.parseDouble(productPrice);
                    price.setText(String.valueOf(price_value));
                }
            }
        });

        offers = new ArrayList<>();
        offer_all = new ArrayList<>();
        offerType = new ArrayList<>();
        get_product_offer = new ViewModelProvider(getActivity()).get(Get_product_offer.class);
        get_product_offer.getdata(productID).observe(getViewLifecycleOwner(), new Observer<List<get_product_offer_response>>() {
            @Override
            public void onChanged(List<get_product_offer_response> get_product_offer_responses) {
                offers = get_product_offer_responses;
                if (offers.size() > 0) {
                    offerType.add("Offer on  selected product");

                }
                get_product_offer.get_all_data(shop_id).observe(getViewLifecycleOwner(), new Observer<List<get_all_product_offer_response>>() {
                    @Override
                    public void onChanged(List<get_all_product_offer_response> get_all_product_offer_responses) {
                        offer_all = get_all_product_offer_responses;
                        if (offer_all.size() > 0) {

                            offerType.add("Offer on whole product");

                        }
                        if (offerType.size() > 0) {
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, offerType);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            offerTypeSpinner.setAdapter(adapter);
                            show_offer_type_spinner();
                        } else {
                            offerTypeSpinner.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        fragmentManager = getFragmentManager();
        get_product();

        getOfferLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getOfferState == 0) {
                    if (offerType.size() > 0) {

                        offerMainLayout.setVisibility(View.VISIBLE);
                        noOffersText.setVisibility(View.GONE);

                    } else {
                        offerMainLayout.setVisibility(View.GONE);
                        noOffersText.setVisibility(View.VISIBLE);
                    }
                    arrowDown.setVisibility(View.GONE);
                    arrowUp.setVisibility(View.VISIBLE);
                    getOfferState = 1;
                } else if (getOfferState == 1) {
                    if (offerType.size() > 0) {
                        offerMainLayout.setVisibility(View.GONE);


                    } else {
                        noOffersText.setVisibility(View.GONE);
                    }
                    arrowUp.setVisibility(View.GONE);
                    arrowDown.setVisibility(View.VISIBLE);

                    getOfferState = 0;
                }
            }

        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = 0, break_check = 0;
                if (!TextUtils.isEmpty(price.getText().toString().trim())) {

                    if (check == 1 && break_check == 1) {
                        Toast.makeText(getActivity(), productNameText.getText().toString().trim() + " amount must less than stock", Toast.LENGTH_SHORT).show();
                    } else {
                        if (check == 1 && break_check == 0) {
                            next();
                        } else if (check == 0 && break_check == 0) {
                            get_product();
                            if (Double.parseDouble(stock.getText().toString().trim()) < Double.parseDouble(product_amount.getText().toString().trim())) {
                                Toast.makeText(getActivity(), productNameText.getText().toString().trim() + " amount must less than stock", Toast.LENGTH_SHORT).show();
                            } else {
                                if (types.size() > 0) {
                                    ProductSell productSell = new ProductSell();
                                    //productSell.setOffer_type(offer_type);
                                    productSell.setProduct_id(productID);
                                    productSell.setProduct_name(productNameText.getText().toString().trim());
                                    productSell.setProduct_image(productImage);
                                    productSell.setType_id(type_id);
                                    productSell.setType_name(type_name);
                                    //productSell.setOffer_id(offer_id);
                                    productSell.setPrice(price.getText().toString().trim());
                                    productSell.setUnit_price(String.valueOf(Double.parseDouble(productPrice)));
                                    Double total_type_amount = 0.0;
                                    List<ProductSel_type> productSel_type = new ArrayList<>();
                                    for (int i = 0; i < productSel_types.size(); i++) {
                                        if (productSel_types.get(i).getType_amount().equals("0")) {

                                        } else {
                                            total_type_amount += Double.parseDouble(productSel_types.get(i).getType_amount());
                                            productSel_type.add(productSel_types.get(i));
                                        }
                                    }
                                    if (productSel_type.size() == 0) {
                                        Toast.makeText(getActivity(), "Select type ", Toast.LENGTH_SHORT).show();

                                    }
                                    if (total_type_amount > Double.parseDouble(product_amount.getText().toString().trim())) {
                                        productSell.setAmount(String.valueOf(total_type_amount));
                                        productSell.setBuy_price(String.valueOf(total_type_amount * Double.parseDouble(product_buy_price)));

                                        // Toast.makeText(getActivity(),"Selected type amount not more than sell product amount",Toast.LENGTH_SHORT).show();
                                    } else {
                                        productSell.setAmount(String.valueOf(Double.parseDouble(product_amount.getText().toString().trim())));
                                        productSell.setBuy_price(String.valueOf(Double.parseDouble(product_amount.getText().toString().trim()) * Double.parseDouble(product_buy_price)));

                                    }
                                    productSell.setTypeList(productSel_type);
                                    Product_sell_offer product_sell_offer = new Product_sell_offer(offer_type, minimum_offer_amount, minimum_offer_pricee, offer_percentage);
                                    //productSell.setSell_offer(product_sell_offer);
                                    productSellList.add(productSell);

                                    next();


                                } else {
                                    ProductSell productSell = new ProductSell();
                                    //productSell.setOffer_type(offer_type);
                                    productSell.setProduct_id(productID);
                                    productSell.setProduct_name(productNameText.getText().toString().trim());
                                    productSell.setProduct_image(productImage);
                                    productSell.setType_id(type_id);
                                    productSell.setType_name(type_name);
                                    // productSell.setOffer_id(offer_id);
                                    productSell.setAmount(String.valueOf(Double.parseDouble(product_amount.getText().toString().trim())));
                                    productSell.setPrice(price.getText().toString().trim());
                                    productSell.setUnit_price(String.valueOf(Double.parseDouble(productPrice)));
                                    productSell.setBuy_price(String.valueOf(Double.parseDouble(product_amount.getText().toString().trim()) * Double.parseDouble(product_buy_price)));
                                    List<ProductSel_type> productSel_type = new ArrayList<>();
                                    productSell.setTypeList(productSel_type);
                                    Product_sell_offer product_sell_offer = new Product_sell_offer(offer_type, minimum_offer_amount, minimum_offer_pricee, offer_percentage);
                                    //productSell.setSell_offer(product_sell_offer);
                                    productSellList.add(productSell);

                                    next();

                                }
                            }
                        }

                    }
                } else {
                    Toast.makeText(getActivity(), "select product properly", Toast.LENGTH_SHORT).show();
                }

            }


        });

        return view;
    }

    private void show_offer_type_spinner() {
        offerTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // type_name = parent.getItemAtPosition(position).toString();
                if (offerType.size() == 2) {
                    if (position == 0) {

                        offerSecondSpinnerLayout.setVisibility(View.GONE);
                        offerSpinnerLayout.setVisibility(View.VISIBLE);
                        x = 1;
                        //get_type();
                        get_offer();

                    } else {

                        offerSpinnerLayout.setVisibility(View.GONE);
                        offerSecondSpinnerLayout.setVisibility(View.VISIBLE);
                        x = 2;
                        get_all_product_offer();
                        // get_type();
                    }
                } else if (offerType.size() == 1) {
                    if (offerType.get(0).equals("Offer on whole product")) {

                        offerSpinnerLayout.setVisibility(View.GONE);
                        offerSecondSpinnerLayout.setVisibility(View.VISIBLE);
                        x = 2;
                        get_all_product_offer();
                        //get_type();
                    } else {

                        offerSecondSpinnerLayout.setVisibility(View.GONE);
                        offerSpinnerLayout.setVisibility(View.VISIBLE);
                        x = 1;
                        // get_type();
                        get_offer();

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void next() {
        getOfferMainLayout.setVisibility(View.GONE);
        fragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.frame_container, new Operator_sell_selected_product_list_fragment(shop_id, agent_id, productSellList)).addToBackStack(null).commit();
    }

    @Override
    public void OnTypeClick(int position) {
        get_product_type_response type = types.get(position);
        ProductSel_type selected_type = productSel_types.get(position);

        Dialog setAmountAlert = new Dialog(getActivity());
        setAmountAlert.setContentView(R.layout.shop_select_typeview_alert);
        setAmountAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setAmountAlert.setCancelable(false);
        setAmountAlert.show();

        ImageView closeButton = setAmountAlert.findViewById(R.id.crossID);
        AppCompatButton setButton = setAmountAlert.findViewById(R.id.setButtonID);
        TextInputEditText amountText = setAmountAlert.findViewById(R.id.amountTextID);
        TextInputLayout amountError = setAmountAlert.findViewById(R.id.amountErrorID);
        amountText.setText(selected_type.getType_amount());

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String amount = amountText.getText().toString().trim();
                amountError.setErrorEnabled(false);

                if (TextUtils.isEmpty(amount)) {
                    amountError.setError(" ");
                } else {
                    if (Double.parseDouble(amount) > Double.parseDouble(type.getCount())) {
                        amountError.setError("amount overflow ");
                    } else {
                        selected_type.setType_amount(amount);
                        type_adapter.notifyDataSetChanged();
                        setAmountAlert.dismiss();
                    }
                    //do code
                }

            }
        });


        for (int i = 0; i < productSel_types.size(); i++) {
            //Log.d("types:", productSel_types.get(i).getType_name());
        }


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAmountAlert.dismiss();
            }
        });
    }

    @Override
    public void OnTypeInc(int position) {
        get_product_type_response type = types.get(position);
        ProductSel_type selected_type = productSel_types.get(position);
        if (Double.parseDouble(selected_type.getType_amount()) == Double.parseDouble(type.getCount())) {

            Toast.makeText(getActivity(), "amount overflow", Toast.LENGTH_SHORT).show();
        } else {
            String amount = String.valueOf(Double.parseDouble(selected_type.getType_amount()) + 1.0);
            selected_type.setType_amount(amount);
            type_adapter.notifyDataSetChanged();
            Double selected_product_amount = 0.0;
            for (int i = 0; i < productSel_types.size(); i++) {
                selected_product_amount += Double.parseDouble(productSel_types.get(i).getType_amount());
            }
            product_amount.setText(String.valueOf(selected_product_amount));
            Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * Double.parseDouble(productPrice);
            price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
            if (x == 1) {
                get_offer();
            } else if (x == 2) {
                get_all_product_offer();
            }
            // setAmountAlert.dismiss();
        }
    }

    @Override
    public void OnTypeDec(int position) {
        get_product_type_response type = types.get(position);
        ProductSel_type selected_type = productSel_types.get(position);
        if (Double.parseDouble(selected_type.getType_amount()) == 0.0) {

            Toast.makeText(getActivity(), "amount underflow", Toast.LENGTH_SHORT).show();
        } else {
            String amount = String.valueOf(Double.parseDouble(selected_type.getType_amount()) - 1.0);
            selected_type.setType_amount(amount);
            type_adapter.notifyDataSetChanged();
            Double selected_product_amount = 0.0;
            for (int i = 0; i < productSel_types.size(); i++) {
                selected_product_amount += Double.parseDouble(productSel_types.get(i).getType_amount());
            }
            product_amount.setText(String.valueOf(selected_product_amount));
            Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * Double.parseDouble(productPrice);
            price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
            if (x == 1) {
                get_offer();
            } else if (x == 2) {
                get_all_product_offer();
            }
            // setAmountAlert.dismiss();
        }
    }
}