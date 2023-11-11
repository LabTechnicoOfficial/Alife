package com.alifew.alifeworld.view.Shop;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alifew.alifeworld.Custom_Type.ProductSel_type;
import com.alifew.alifeworld.Custom_Type.ProductSell;
import com.alifew.alifeworld.R;
import com.alifew.alifeworld.adapter.Selected_sell_product_list_adapter;
import com.alifew.alifeworld.adapter.Sell_product_adapter;
import com.alifew.alifeworld.adapter.Shop_sell_type_select_adapter;
import com.alifew.alifeworld.adapter.Shop_sellamount_inc_dec_adapter;
import com.alifew.alifeworld.model.get_all_product_offer_response;
import com.alifew.alifeworld.model.get_product_offer_response;
import com.alifew.alifeworld.model.Get_product_response;
import com.alifew.alifeworld.model.get_product_type_response;
import com.alifew.alifeworld.model.Shop_profile_response;
import com.alifew.alifeworld.viewmodel.Get_all_shop_product;
import com.alifew.alifeworld.viewmodel.Get_product;
import com.alifew.alifeworld.viewmodel.Get_product_offer;
import com.alifew.alifeworld.viewmodel.Get_product_type;
import com.alifew.alifeworld.viewmodel.ShopProfileViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Shop_sell_product_selected_fragment extends Fragment implements Shop_sell_type_select_adapter.OnItemSelectListener, Shop_sell_type_select_adapter.OnItemAddListener, Shop_sell_type_select_adapter.OnItemMinusListener, Sell_product_adapter.OnItemClickListener, Selected_sell_product_list_adapter.OnItemAddListener, Selected_sell_product_list_adapter.OnItemMinusListener, Selected_sell_product_list_adapter.OnItemRemoveListener, Shop_sellamount_inc_dec_adapter.addListener, Shop_sellamount_inc_dec_adapter.minusListener {
    String productID, productName, productUnit;
    String productPrice_with_discount, productImage, shop_id, product_buy_price, product_unit_price;
    String product_discount_all = "9";
    String product_discount;
    ShopProfileViewModel shop_profile;
    private List<ProductSell> productSellList;
    private List<ProductSel_type> productSel_types;
    TextView productNameText, productUnitText, stock;
    TextView price, allDiscountText;
    EditText product_amount;
    Get_product_type get_product_type;
    Get_product get_product;
    Get_product_offer get_product_offer;
    List<get_product_type_response> types;
    private String product_types[];
    List<get_product_offer_response> offers;
    List<get_all_product_offer_response> offer_all;
    private String product_offers[];
    private String product_offers_price[];
    Spinner offerSpinner, offerTypeSpinner;
    Spinner offerSecondSpinner;
    List<String> offerType;
    int x = 0;//for offer type selecction
    String offerTypeText;
    private String type_id, type_name, type_count;
    private String offer_id;
    private String offer_type = "none";
    private String minimum_offer_amount = "0";
    private String minimum_offer_pricee = "0";
    private String offer_percentage = "0";
    int available_type = 0, getOfferState = 0;
    LinearLayout typeLayout, getOfferLayout, offerSpinnerLayout, getOfferMainLayout;
    LinearLayout offerSecondSpinnerLayout, offerMainLayout, topLayout;
    ImageView arrowDown, arrowUp;
    ExtendedFloatingActionButton nextButton;
    private int type_check = 0;
    private Double type_amount = 0.0;
    private FragmentManager fragmentManager;
    private int offer_check = 0;

    //ImageView decButton, incButton;

    RecyclerView typeView, productsView;
    private Shop_sell_type_select_adapter type_adapter;

    TextView noOffersText;
    LinearLayout addMoreButton;
    List<Get_product_response> data;
    EditText searchBar;
    private Sell_product_adapter adapter_more_product_add;
    int page = 1, limit = 10, end = 0;
    ProgressBar progressBar;
    Dialog addMoreAlert;
    RecyclerView all_productView, cartProductView;
    Get_all_shop_product get_all_shop_product;
    AppCompatButton addButton;
    int product_sell_cart_position;
    Dialog inc_dec_dialog;
    private Selected_sell_product_list_adapter adapter;
    private Shop_sellamount_inc_dec_adapter amount_inc_dec_adapter;
    Double stock_product;
    boolean offer_product_satisfication = true;
    private LinearLayout amountLayout;

    public Shop_sell_product_selected_fragment(String shop_id, String productID, List<ProductSell> productSellList, String product_discount_all) {
        this.shop_id = shop_id;
        this.productID = productID;
        this.productSellList = productSellList;
        this.product_discount_all = product_discount_all;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addMoreButton.setVisibility(View.GONE);
        set_offer();
        main();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = 0, break_check = 0;
                //  if (!TextUtils.isEmpty(price.getText().toString().trim())) {
                if (productSellList.size() > 0) {
                    double amount = 0.0;
                    double total_price = 0.0;
                    for (int i = 0; i < productSellList.size(); i++) {
                        amount += Double.parseDouble(productSellList.get(i).getAmount());
                        total_price += Double.parseDouble(productSellList.get(i).getPrice());

                    }
                    if (amount >= Double.parseDouble(minimum_offer_amount) && total_price >= Double.parseDouble(minimum_offer_pricee)) {
                        if ((offer_check == 1&& offerType.size()>0)||offerType.size()==0) {
                            next();
                        } else {
                            Toast.makeText(getActivity(), "Select Offer", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "selected product not match offer condition", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "select product properly", Toast.LENGTH_SHORT).show();
                }

            }


        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = 0, break_check = 0;
                Double select_product_amount = 0.0;

                if (!TextUtils.isEmpty(price.getText().toString().trim()) && !TextUtils.isEmpty(product_amount.getText().toString().trim()) && Double.parseDouble(product_amount.getText().toString().trim()) > 0.0) {
                    for (int i = 0; i < productSellList.size(); i++) {
                        if (productID.equals(productSellList.get(i).getProduct_id())) {
                            select_product_amount += Double.parseDouble(productSellList.get(i).getAmount());
                        }
                    }
                    get_product();
                    select_product_amount += Double.parseDouble(product_amount.getText().toString().trim());
                    if (Double.parseDouble(stock.getText().toString().trim()) < select_product_amount) {
                        Toast.makeText(getActivity(), "Total select amount must less than stock", Toast.LENGTH_SHORT).show();

                    } else {
                        offer_product_satisfication = true;
                        if (offerType.equals("individual")) {
                            for (int i = 0; i < productSellList.size(); i++) {
                                if (!productID.equals(productSellList.get(i).getProduct_id())) {
                                    offer_product_satisfication = false;
                                    break;
                                }
                            }
                        }
                        if (offer_product_satisfication == false) {
                            Toast.makeText(getActivity(), "Select Offer Correctly to add this product", Toast.LENGTH_SHORT).show();
                        } else if (offer_check == 0&& offerType.size()>0) {
                            Toast.makeText(getActivity(), "Set Offer", Toast.LENGTH_SHORT).show();
                        } else {
                            if (types.size() > 0) {
                                ProductSell productSell = new ProductSell();
                                //productSell.setOffer_type(offer_type);

                                productSell.setDiscount(product_discount);

                                productSell.setProduct_id(productID);
                                productSell.setProduct_name(productNameText.getText().toString().trim());
                                productSell.setProduct_image(productImage);
                                productSell.setType_id(type_id);
                                productSell.setType_name(type_name);
                                // productSell.setOffer_id(offer_id);
                                productSell.setAmount(String.valueOf(Double.parseDouble(product_amount.getText().toString().trim())));
                                productSell.setPrice(price.getText().toString().trim());
                                productSell.setUnit_price(String.valueOf(Double.parseDouble(product_unit_price)));
                                productSell.setUnit_price_with_discount(productPrice_with_discount);
                                productSell.setBuy_price(String.valueOf(Double.parseDouble(product_amount.getText().toString().trim()) * Double.parseDouble(product_buy_price)));
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

                                } else if (total_type_amount > Double.parseDouble(product_amount.getText().toString().trim())) {
                                    Toast.makeText(getActivity(), "Selected type amount not more than sell product amount", Toast.LENGTH_SHORT).show();
                                } else {
                                    productSell.setTypeList(productSel_type);
                                    //Product_sell_offer product_sell_offer = new Product_sell_offer(offer_type, minimum_offer_amount, minimum_offer_pricee, offer_percentage);
                                    // productSell.setSell_offer(product_sell_offer);
                                    productSellList.add(productSell);
                                    // addAmountAlert.dismiss();
                                    // main();
                                    // here item select layout gone and add to cart code will be implemented
                                    topLayout.setVisibility(View.GONE);
                                    addMoreButton.setVisibility(View.VISIBLE);
                                    show_product_cart();
                                }


                            } else {
                                ProductSell productSell = new ProductSell();
                                //productSell.setOffer_type(offer_type);
                                productSell.setDiscount(product_discount);

                                productSell.setProduct_id(productID);
                                productSell.setProduct_name(productNameText.getText().toString().trim());
                                productSell.setProduct_image(productImage);
                                productSell.setType_id(type_id);
                                productSell.setType_name(type_name);
                                //productSell.setOffer_id(offer_id);
                                productSell.setAmount(String.valueOf(Double.parseDouble(product_amount.getText().toString().trim())));
                                productSell.setPrice(price.getText().toString().trim());
                                productSell.setUnit_price(String.valueOf(Double.parseDouble(product_unit_price)));
                                productSell.setUnit_price_with_discount(productPrice_with_discount);
                                productSell.setBuy_price(String.valueOf(Double.parseDouble(product_amount.getText().toString().trim()) * Double.parseDouble(product_buy_price)));
                                List<ProductSel_type> productSel_type = new ArrayList<>();
                                productSell.setTypeList(productSel_type);
                                //Product_sell_offer product_sell_offer = new Product_sell_offer(offer_type, minimum_offer_amount, minimum_offer_pricee, offer_percentage);
                                // productSell.setSell_offer(product_sell_offer);
                                productSellList.add(productSell);
                                //addAmountAlert.dismiss();
                                //main();
                                // here item select layout gone and add to cart code will be implemented
                                show_product_cart();
                                topLayout.setVisibility(View.GONE);
                                addMoreButton.setVisibility(View.VISIBLE);

                            }
                        }
                    }


                } else {
                    Toast.makeText(getActivity(), "select product properly", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void set_offer() {
        get_product();
        //assign product attribute
        // get_all_product_discount();
        //product_amount.setText("0");
        price.setText("0");
        offers = new ArrayList<>();
        offer_all = new ArrayList<>();
        offerType = new ArrayList<>();
        get_product_offer = new ViewModelProvider(getActivity()).get(Get_product_offer.class);
        get_product_offer.getdata(productID).observe(getViewLifecycleOwner(), new Observer<List<get_product_offer_response>>() {
            @Override
            public void onChanged(List<get_product_offer_response> get_product_offer_responses) {
                offers = get_product_offer_responses;
                offerType.add("ডিসকাউন্ট"+ product_discount+"%");
                if (offers.size() > 0) {
                    offerType.add("এই পন্যের অফার");

                }
                get_product_offer.get_all_data(shop_id).observe(getViewLifecycleOwner(), new Observer<List<get_all_product_offer_response>>() {
                    @Override
                    public void onChanged(List<get_all_product_offer_response> get_all_product_offer_responses) {
                        offer_all = get_all_product_offer_responses;
                        if (offer_all.size() > 0) {

                            offerType.add("সকল পন্যের অফার");

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
    }

    private void show_product_cart() {

        //  noProductsAvailableText.setVisibility(View.GONE);
        //  productsView .setVisibility(View.VISIBLE);

        adapter = new Selected_sell_product_list_adapter(productSellList, getActivity(), 1);
        adapter.setOnClickListener(Shop_sell_product_selected_fragment.this::OnItemAdd, Shop_sell_product_selected_fragment.this::OnItemMinus, Shop_sell_product_selected_fragment.this::OnItemRemove);
        productsView.setAdapter(adapter);
        // TotalPrice = 0.0;
            /*for (int i = 0; i < productsList.size(); i++) {
                TotalPrice += Double.parseDouble(productsList.get(i).getPrice());
            }
            price.setText(String.valueOf(new DecimalFormat("##.##").format(TotalPrice)));
            reducePrice.setText("0");
            finalPrice.setText(String.valueOf(new DecimalFormat("##.##").format(TotalPrice)));*/


    }

    private void main() {
        checkConnection();
        //get_all_product_discount();

        //product_amount.setText("0");
        price.setText("0");
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
// end assign product attribute

        // get_product();

        type_id = "0";
        type_name = "";
        offer_id = "0";

        types = new ArrayList<>();

        // get_product();
        get_type();
        //  get_offer();

    }

    private void get_all_product_discount() {
        shop_profile = new ViewModelProvider(getActivity()).get(ShopProfileViewModel.class);
        shop_profile.getData(shop_id).observe(getViewLifecycleOwner(), new Observer<Shop_profile_response>() {
            @Override
            public void onChanged(Shop_profile_response shop_profile_response) {
                // product_discount_all = shop_profile_response.getAll_discount();
                allDiscountText.setText(shop_profile_response.getAll_discount());
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
                    offer_type = "individual";
                    minimum_offer_amount = offers.get(position - 1).getAmount();
                    minimum_offer_pricee = "0";
                    offer_percentage = product_offers_price[position];
                    offer_check = 1;

                } else {
                    //price.setText("");
                    //product_amount.setText("");
                    offer_check = 0;
                    offer_type = "none";
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
                //if (position > 0) {
                if ((!product_amount.getText().toString().isEmpty() && Double.parseDouble(product_amount.getText().toString().trim()) > 0)) {

                    //if ((Double.parseDouble(product_amount.getText().toString().trim()) >= Double.parseDouble(offer_all.get(position - 1).getMinimum_amount())) && (Double.parseDouble(price.getText().toString().trim()) >= Double.parseDouble(offer_all.get(position - 1).getMinimum_price()))) {

                    // amountlayout.setVisibility(View.GONE);
                    // product_amount.setText(offers.get(position - 1).getAmount());
                    if (position > 0) {
                        offer_id = offer_all.get(position - 1).getId();
                        offer_check = 1;
                        offer_type = "whole";
                        minimum_offer_amount = offer_all.get(position - 1).getMinimum_amount();
                        minimum_offer_pricee = offer_all.get(position - 1).getMinimum_price();
                        offer_percentage = product_offers_price[position];
                    } else {
                        offer_id = "0";
                        offer_check = 0;

                    }


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void get_product() {
        //get_all_product_discount();
        get_product = new ViewModelProvider(getActivity()).get(Get_product.class);
        get_product.getsingle_product(productID).observe(getViewLifecycleOwner(), new Observer<Get_product_response>() {
            @Override
            public void onChanged(Get_product_response get_product_response) {
                productNameText.setText(get_product_response.getProduct_name());
                productImage = get_product_response.getProduct_image();
                productUnitText.setText(get_product_response.getProduct_unit());
                productUnit = get_product_response.getProduct_unit();

                stock.setText(get_product_response.getStock_amount());
                double selling_price = Double.parseDouble(get_product_response.getSelling_price());
                double discount = Double.parseDouble(get_product_response.getProduct_offer());
                if (discount > Double.parseDouble(product_discount_all)) {
                    product_discount = String.valueOf(discount);
                } else {
                    product_discount = product_discount_all;
                }
                allDiscountText.setText("Discount :"+ product_discount+"%");


           /*  if (Double.parseDouble(allDiscountText.getText().toString().trim()) > discount) {
                    discount = Double.parseDouble(allDiscountText.getText().toString().trim());
                }*/
                double price_with_offer = selling_price - selling_price * (discount / 100);
                productPrice_with_discount = String.valueOf(price_with_offer);
                product_unit_price = String.valueOf(selling_price);
                product_buy_price = get_product_response.getBuy_price();

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
                    amountLayout.setVisibility(View.GONE);
                    typeLayout.setVisibility(View.VISIBLE);
                    //decButton.setVisibility(View.GONE);
                    //incButton.setVisibility(View.GONE);
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
                    type_adapter.setOnClickListener(Shop_sell_product_selected_fragment.this::OnTypeClick, Shop_sell_product_selected_fragment.this::OnTypeInc, Shop_sell_product_selected_fragment.this::OnTypeDec);
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
                    typeLayout.setVisibility(View.GONE);
                    //decButton.setVisibility(View.VISIBLE);
                    //incButton.setVisibility(View.VISIBLE);
                    amountLayout.setVisibility(View.VISIBLE);
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
                                    typeLayout.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "amount must less than stock", Toast.LENGTH_SHORT).show();
                                    product_amount.setText("");
                                    price.setText("");
                                } else {
                                    Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * Double.parseDouble(productPrice_with_discount);
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

        addButton = (AppCompatButton) view.findViewById(R.id.addButton);
        productNameText = (TextView) view.findViewById(R.id.productNameID);
        productUnitText = (TextView) view.findViewById(R.id.productUnitID);
        stock = (TextView) view.findViewById(R.id.stockAmountID);
        price = (TextView) view.findViewById(R.id.priceID);
        allDiscountText = (TextView) view.findViewById(R.id.allDiscountID);
        noOffersText = (TextView) view.findViewById(R.id.noOffersTextID);

        product_amount = (EditText) view.findViewById(R.id.amountText);

        //type_spinner = (Spinner) view.findViewById(R.id.typeSpinnerID);
        offerSpinner = (Spinner) view.findViewById(R.id.offerSpinnerID);
        offerTypeSpinner = (Spinner) view.findViewById(R.id.offerTypeSpinnerID);
        offerSecondSpinner = (Spinner) view.findViewById(R.id.offerSecondSpinnerID);

        typeView = (RecyclerView) view.findViewById(R.id.typeViewID);
        productsView = (RecyclerView) view.findViewById(R.id.productsViewID);

        typeView.setHasFixedSize(true);
        typeView.setLayoutManager(new LinearLayoutManager(getContext()));
        productsView.setHasFixedSize(true);
        productsView.setLayoutManager(new LinearLayoutManager(getContext()));

        typeLayout = (LinearLayout) view.findViewById(R.id.typeLayoutID);
        getOfferLayout = (LinearLayout) view.findViewById(R.id.getOfferLayoutID);
        offerSpinnerLayout = (LinearLayout) view.findViewById(R.id.offerSpinnerLayoutID);
        getOfferMainLayout = (LinearLayout) view.findViewById(R.id.getOfferMainLayoutID);
        addMoreButton = (LinearLayout) view.findViewById(R.id.addMoreID);
        topLayout = (LinearLayout) view.findViewById(R.id.topLayoutID);
        amountLayout = (LinearLayout) view.findViewById(R.id.amountLayoutId);

        offerSecondSpinnerLayout = (LinearLayout) view.findViewById(R.id.offerSecondSpinnerLayoutID);
        offerMainLayout = (LinearLayout) view.findViewById(R.id.offerMainLayoutID);

        arrowDown = (ImageView) view.findViewById(R.id.arrowDownID);
        arrowUp = (ImageView) view.findViewById(R.id.arrowUpID);
        nextButton = (ExtendedFloatingActionButton) view.findViewById(R.id.nextButtonID);

        //decButton = (ImageView) view.findViewById(R.id.minusButtonID);
        //incButton = (ImageView) view.findViewById(R.id.plusButtonID);

        addMoreAlert = new Dialog(getActivity());
        addMoreAlert.setContentView(R.layout.add_more_sellproduct_alert);
        addMoreAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addMoreAlert.setCancelable(false);

        /*decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double amount = Double.parseDouble(product_amount.getText().toString().trim());
                if (amount > 0.0) {
                    amount -= 1.0;
                    product_amount.setText(String.valueOf(amount));
                    Double price_value = amount * Double.parseDouble(product_unit_price);
                    price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                }
            }
        });*/

        /*incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double amount = Double.parseDouble(product_amount.getText().toString().trim());
                amount += 1.0;
                if (amount <= Double.parseDouble(stock.getText().toString().trim())) {
                    product_amount.setText(String.valueOf(amount));
                    Double price_value = amount * Double.parseDouble(product_unit_price);
                    price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                }
            }
        });*/

        /* get_all_product_discount();
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

        });*/

        addMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_more_product();
            }
        });

        productsView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && nextButton.getVisibility() == View.VISIBLE) {
                    nextButton.hide();
                } else if (dy < 0 && nextButton.getVisibility() != View.VISIBLE) {
                    nextButton.show();
                }
            }
        });

        progressBar = (ProgressBar) addMoreAlert.findViewById(R.id.progressBar);
        NestedScrollView nestedScrollView = (NestedScrollView) addMoreAlert.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    if (end == 0) {
                        progressBar.setVisibility(View.VISIBLE);
                        page++;
                        select_from_all_product(page, limit);
                    }

                }
            }
        });

        return view;
    }


    private void select_from_all_product(int Page, int Limit) {
        get_all_shop_product = new ViewModelProvider(getActivity()).get(Get_all_shop_product.class);
        get_all_shop_product.getData(shop_id, Page, Limit).observe(getViewLifecycleOwner(), new Observer<List<Get_product_response>>() {
            @Override
            public void onChanged(List<Get_product_response> get_product_responses) {
                //data = get_product_responses;
                progressBar.setVisibility(View.GONE);
                for (int i = 0; i < get_product_responses.size(); i++) {
                    data.add(get_product_responses.get(i));
                }
                if (get_product_responses.size() < Limit) {
                    end = 1;
                }
                adapter_more_product_add = new Sell_product_adapter(data);
                adapter_more_product_add.setOnClickListener(Shop_sell_product_selected_fragment.this::OnItemClick);
                all_productView.setAdapter(adapter_more_product_add);


            }
        });
    }

    private void get_search_product(String value) {
        get_all_shop_product = new ViewModelProvider(getActivity()).get(Get_all_shop_product.class);
        get_all_shop_product.getSearchData(shop_id).observe(getViewLifecycleOwner(), new Observer<List<Get_product_response>>() {
            @Override
            public void onChanged(List<Get_product_response> get_product_responses) {
                for (int i = 0; i < get_product_responses.size(); i++) {
                    String brand_code = get_product_responses.get(i).getBrand() + get_product_responses.get(i).getCode();
                    if ((get_product_responses.get(i).getProduct_id().contains(value)||get_product_responses.get(i).getProduct_name().toLowerCase().contains(value.toLowerCase())) || (get_product_responses.get(i).getBrand().toLowerCase().contains(value.toLowerCase())) || (brand_code.toLowerCase().contains(value.toLowerCase()))) {
                        data.add(get_product_responses.get(i));
                    }
                }
                adapter_more_product_add = new Sell_product_adapter(data);
                adapter_more_product_add.setOnClickListener(Shop_sell_product_selected_fragment.this::OnItemClick);
                all_productView.setAdapter(adapter_more_product_add);

            }
        });
    }

    private void add_more_product() {
        get_all_product_discount();
        data = new ArrayList<>();
        addMoreAlert.show();
        ImageView closeButton = (ImageView) addMoreAlert.findViewById(R.id.crossID);
        all_productView = (RecyclerView) addMoreAlert.findViewById(R.id.productsViewID);
        searchBar = (EditText) addMoreAlert.findViewById(R.id.searchEditText);
        // allDiscountText = (TextView) addMoreAlert.findViewById(R.id.allDiscountID);

        all_productView.setHasFixedSize(true);
        all_productView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter_more_product_add = new Sell_product_adapter(data);
        adapter_more_product_add.setOnClickListener(Shop_sell_product_selected_fragment.this::OnItemClick);
        all_productView.setAdapter(adapter_more_product_add);
        page = 1;
        end = 0;
        select_from_all_product(page, limit);

        searchBar.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(TextUtils.isEmpty(searchBar.getText().toString().trim()))) {
                    try {
                        adapter_more_product_add.getFilter().filter(searchBar.getText());
                        data = new ArrayList<>();
                        adapter_more_product_add = new Sell_product_adapter(data);
                        adapter_more_product_add.setOnClickListener(Shop_sell_product_selected_fragment.this::OnItemClick);
                        all_productView.setAdapter(adapter_more_product_add);
                        get_search_product(searchBar.getText().toString().trim());

                    } catch (Exception e) {

                    }
                } else {
                    data = new ArrayList<>();
                    adapter_more_product_add = new Sell_product_adapter(data);
                    adapter_more_product_add.setOnClickListener(Shop_sell_product_selected_fragment.this::OnItemClick);
                    all_productView.setAdapter(adapter_more_product_add);
                    page = 1;
                    end = 0;
                    select_from_all_product(page, limit);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMoreAlert.dismiss();
            }
        });
    }


    private void show_offer_type_spinner() {
        offerTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // type_name = parent.getItemAtPosition(position).toString();
                if (offerType.size() == 3) {
                    if (position == 0) {
                        //discount code will be implemented here
                        //offerTypeSpinner.setVisibility(View.GONE);
                        offer_type = "discount";
                        offer_check = 1;
                        offer_percentage = product_discount;
                        minimum_offer_pricee = "0";
                        minimum_offer_amount = "0";
                        offerSecondSpinnerLayout.setVisibility(View.GONE);
                        offerSpinnerLayout.setVisibility(View.GONE);


                    } else if (position == 1) {

                        offerSecondSpinnerLayout.setVisibility(View.GONE);
                        offerSpinnerLayout.setVisibility(View.VISIBLE);
                        x = 1;
                        //get_type();
                        get_offer();
                        // get_type();
                    } else {
                        offerSpinnerLayout.setVisibility(View.GONE);
                        offerSecondSpinnerLayout.setVisibility(View.VISIBLE);
                        x = 2;
                        offer_type = "whole";
                        get_all_product_offer();
                    }
                } else if (offerType.size() == 2) {
                    if (position == 0) {
                        //discount code will be implemented here
                        // offerTypeSpinner.setVisibility(View.GONE);
                        offer_type = "discount";
                        offer_check = 1;
                        offer_percentage = product_discount;
                        minimum_offer_pricee = "0";
                        minimum_offer_amount = "0";
                        offerSecondSpinnerLayout.setVisibility(View.GONE);
                        offerSpinnerLayout.setVisibility(View.GONE);



                    } else {
                        if (offerType.get(1).equals("সকল পন্যের অফার")) {

                            offerSpinnerLayout.setVisibility(View.GONE);
                            offerSecondSpinnerLayout.setVisibility(View.VISIBLE);
                            x = 2;
                            offer_type = "whole";
                            get_all_product_offer();
                            //get_type();
                        } else if (offerType.get(1).equals("এই পন্যের অফার")) {

                            offerSecondSpinnerLayout.setVisibility(View.GONE);
                            offerSpinnerLayout.setVisibility(View.VISIBLE);
                            x = 1;
                            // get_type();
                            get_offer();

                        }

                    }
                } else if (offerType.size() == 1) {
                    if (offerType.get(0).equals("সকল পন্যের অফার")) {

                        offerSpinnerLayout.setVisibility(View.GONE);
                        offerSecondSpinnerLayout.setVisibility(View.VISIBLE);
                        x = 2;
                        offer_type = "whole";
                        get_all_product_offer();
                        //get_type();
                    } else if (offerType.get(0).equals("এই পন্যের অফার")) {

                        offerSecondSpinnerLayout.setVisibility(View.GONE);
                        offerSpinnerLayout.setVisibility(View.VISIBLE);
                        x = 1;
                        // get_type();
                        get_offer();

                    } else {
                        //discount code will be implemented here
                        // offerTypeSpinner.setVisibility(View.GONE);
                        offer_type = "discount";
                        offer_check = 1;
                        offer_percentage = product_discount;
                        minimum_offer_pricee = "0";
                        minimum_offer_amount = "0";
                        offerSecondSpinnerLayout.setVisibility(View.GONE);
                        offerSpinnerLayout.setVisibility(View.GONE);
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
        ).replace(R.id.frame_container, new Shop_sell_selected_product_list_fragment(shop_id, productSellList, minimum_offer_amount, minimum_offer_pricee, offer_percentage, offer_type,1)).addToBackStack(null).commit();
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
        TextInputEditText amountText = setAmountAlert.findViewById(R.id.amountText);
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
            Double total_selected_type = Double.parseDouble(selected_type.getType_amount());
            for (int i = 0; i < productSellList.size(); i++) {
                if (productID.equals(productSellList.get(i).getProduct_id())) {
                    // Log.d("position:", productSel_types.get(i).getType_name());
                    for (int j = 0; j < productSellList.get(i).getTypeList().size(); j++) {
                        if (productSellList.get(i).getTypeList().get(j).getType_id().equals(type.getId())) {
                            total_selected_type += Double.parseDouble(productSellList.get(i).getTypeList().get(j).getType_amount());

                        }
                    }
                }
            }
            total_selected_type += 1.0;
            if (total_selected_type > Double.parseDouble(type.getCount())) {
                //  amountError.setError("Total selected type amount overflow stock ");
                Toast.makeText(getActivity(), "Total selected type amount overflow stock ", Toast.LENGTH_SHORT).show();
            } else {
                String amount = String.valueOf(Double.parseDouble(selected_type.getType_amount()) + 1.0);
                selected_type.setType_amount(amount);
                type_adapter.notifyDataSetChanged();
                Double selected_product_amount = 0.0;
                for (int i = 0; i < productSel_types.size(); i++) {
                    selected_product_amount += Double.parseDouble(productSel_types.get(i).getType_amount());
                }
                product_amount.setText(String.valueOf(selected_product_amount));
                Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * Double.parseDouble(product_unit_price);
                price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                // setAmountAlert.dismiss();

                /*if (x == 1) {
                    get_offer();
                } else if (x == 2) {
                    get_all_product_offer();
                }*/
            }
        }

    }

    @Override
    public void OnTypeDec(int position) {
        get_product_type_response type = types.get(position);
        ProductSel_type selected_type = productSel_types.get(position);

        if (Double.parseDouble(selected_type.getType_amount()) == 0.0) {

            Toast.makeText(getActivity(), "amount underflow", Toast.LENGTH_SHORT).show();
        } else {
            Double total_selected_type = Double.parseDouble(selected_type.getType_amount());

            total_selected_type -= 1.0;
            if (total_selected_type < 0.0) {
                //  amountError.setError("Total selected type amount overflow stock ");
                Toast.makeText(getActivity(), "Total selected type amount underflow stock ", Toast.LENGTH_SHORT).show();
            } else {
                String amount = String.valueOf(Double.parseDouble(selected_type.getType_amount()) - 1.0);
                selected_type.setType_amount(amount);
                type_adapter.notifyDataSetChanged();
                Double selected_product_amount = 0.0;
                for (int i = 0; i < productSel_types.size(); i++) {
                    selected_product_amount += Double.parseDouble(productSel_types.get(i).getType_amount());
                }
                product_amount.setText(String.valueOf(selected_product_amount));
                Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * Double.parseDouble(product_unit_price);
                price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
               /* if (x == 1) {
                    get_offer();
                } else if (x == 2) {
                    get_all_product_offer();
                }*/
                // setAmountAlert.dismiss();
            }
        }
    }


    @Override
    public void OnItemClick(int position) {
        Get_product_response product = data.get(position);
        productID = product.getProduct_id();
        if (Double.parseDouble(product.getStock_amount()) > 0) {
            addMoreAlert.dismiss();
            topLayout.setVisibility(View.VISIBLE);
            addMoreButton.setVisibility(View.GONE);

            main();
        } else {
            Toast.makeText(getActivity(), "Stock Out", Toast.LENGTH_SHORT).show();
        }
        //add_more_product_function();

    }

    @Override
    public void OnItemAdd(int position) {
        product_sell_cart_position = position;
        ProductSell product = productSellList.get(position);
        List<ProductSel_type> selected_types = product.getTypeList();
        String selected_productId = product.getProduct_id();

        String type_id = product.getType_id();
        //String offer_id = product.getOffer_id();
       /* Double amount = 0.0;
        for (int i = 0; i < productsList.size(); i++) {
            if (productsList.get(i).getProduct_id().equals(selected_productId)) {
                amount += Double.parseDouble(productsList.get(i).getAmount());
            }
        }
        get_product_stock(selected_productId, product, amount);*/


        IncDecDialog(selected_types, "add", selected_productId);
    }

    @Override
    public void OnItemMinus(int position) {
        product_sell_cart_position = position;
        ProductSell product = productSellList.get(position);
        List<ProductSel_type> selected_types = product.getTypeList();
        String sell_amount = product.getAmount();
        String unit_price = product.getUnit_price();
        if (Double.parseDouble(sell_amount) > 0.0) {
            IncDecDialog(selected_types, "minus", product.getProduct_id());
        }
    }

    @Override
    public void OnItemRemove(int position) {
        productSellList.remove(position);
        adapter.notifyDataSetChanged();

    }

    private void IncDecDialog(List<ProductSel_type> selected_types, String operation, String selected_product_id) {
        inc_dec_dialog = new Dialog(getActivity());
        inc_dec_dialog.setContentView(R.layout.shop_sellamount_inc_dec_alert);
        inc_dec_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        inc_dec_dialog.setCancelable(false);
        ImageView closeButton = inc_dec_dialog.findViewById(R.id.closeID);

        RecyclerView typeView = inc_dec_dialog.findViewById(R.id.typeViewID);
        typeView.setHasFixedSize(true);
        typeView.setLayoutManager(new LinearLayoutManager(getContext()));
        get_product_type = new ViewModelProvider(getActivity()).get(Get_product_type.class);
        get_product_type.getdata(selected_product_id).observe(getViewLifecycleOwner(), new Observer<List<get_product_type_response>>() {
            @Override
            public void onChanged(List<get_product_type_response> get_product_type_responses) {
                types = get_product_type_responses;
                if (types.size() > 0) {

                    inc_dec_dialog.show();


                    amount_inc_dec_adapter = new Shop_sellamount_inc_dec_adapter(types, selected_types, operation);
                    amount_inc_dec_adapter.setOnClickListener(Shop_sell_product_selected_fragment.this::increament, Shop_sell_product_selected_fragment.this::decreament);
                    typeView.setAdapter(amount_inc_dec_adapter);

                } else {
                    if (operation.equals("add")) {
                        Double amount = 0.0;
                        for (int i = 0; i < productSellList.size(); i++) {
                            if (productSellList.get(i).getProduct_id().equals(productSellList.get(product_sell_cart_position).getProduct_id())) {
                                amount += Double.parseDouble(productSellList.get(i).getAmount());
                            }
                        }
                        get_product_stock(productSellList.get(product_sell_cart_position).getProduct_id(), productSellList.get(product_sell_cart_position), amount);

                    } else {
                        Double sell_amount = Double.parseDouble(productSellList.get(product_sell_cart_position).getAmount());
                        String unit_price = productSellList.get(product_sell_cart_position).getUnit_price();
                        String unit_price_with_discount = productSellList.get(product_sell_cart_position).getUnit_price_with_discount();
                        Double temp_price = Double.parseDouble(unit_price);
                        if (sell_amount > 0) {
                            sell_amount -= 1;
                            productSellList.get(product_sell_cart_position).setAmount(String.valueOf(sell_amount));


                            productSellList.get(product_sell_cart_position).setPrice(String.valueOf(Double.parseDouble(productSellList.get(product_sell_cart_position).getAmount()) * temp_price));
                        }
                        adapter.notifyDataSetChanged();
                        double total_price = 0.0;

                    }
                }


            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inc_dec_dialog.dismiss();
            }
        });
    }

    public void get_product_stock(String productId, ProductSell product, Double amount) {
        stock_product = 0.0;
        get_product = new ViewModelProvider(getActivity()).get(Get_product.class);
        get_product.getsingle_product(productId).observe(getViewLifecycleOwner(), new Observer<Get_product_response>() {
            @Override
            public void onChanged(Get_product_response get_product_response) {
                stock_product = Double.parseDouble(get_product_response.getStock_amount());
                if (stock_product <= amount) {
                    Toast.makeText(getActivity(), "amount overflow", Toast.LENGTH_SHORT).show();
                } else {
                    // if (product.getType_id().equals("0")) {
                    product.setAmount(String.valueOf(Double.parseDouble(product.getAmount()) + 1));
                    double temp_price = Double.parseDouble(get_product_response.getSelling_price());

                    product.setPrice(String.valueOf(Double.parseDouble(product.getAmount()) * temp_price));
                    adapter.notifyDataSetChanged();
                    double total_price = 0.0;
                    for (int i = 0; i < productSellList.size(); i++) {
                        total_price += Double.parseDouble(productSellList.get(i).getPrice());
                    }


                }
            }
        });

    }

    @Override
    public void increament(int position) {
        get_product_type_response type = types.get(position);

        String unit_price = productSellList.get(product_sell_cart_position).getUnit_price();
        String unit_price_with_discount = productSellList.get(product_sell_cart_position).getUnit_price_with_discount();
        double all_type_amount = 0.0;
        for (int i = 0; i < productSellList.size(); i++) {
            for (int j = 0; j < productSellList.get(i).getTypeList().size(); j++) {
                if (type.getId().equals(productSellList.get(i).getTypeList().get(j).getType_id())) {
                    all_type_amount += Double.parseDouble(productSellList.get(i).getTypeList().get(j).getType_amount());
                }
            }
        }
        if (Double.parseDouble(type.getCount()) == all_type_amount) {
            Toast.makeText(getActivity(), "Amount Overflow", Toast.LENGTH_SHORT).show();
            inc_dec_dialog.dismiss();
        } else {
            Double temp_price = 0.0;
            int inc_dec_check = 0;
            for (int i = 0; i < productSellList.get(product_sell_cart_position).getTypeList().size(); i++) {
                if (type.getId().equals(productSellList.get(product_sell_cart_position).getTypeList().get(i).getType_id())) {
                    Double amount = Double.parseDouble(productSellList.get(product_sell_cart_position).getAmount());
                    Double type_amount = Double.parseDouble(productSellList.get(product_sell_cart_position).getTypeList().get(i).getType_amount());
                    Double amount_price = Double.parseDouble(productSellList.get(product_sell_cart_position).getPrice());
                    amount = amount + 1;
                    type_amount = type_amount + 1;
                    temp_price = Double.parseDouble(productSellList.get(product_sell_cart_position).getUnit_price());
                    amount_price = amount * temp_price;
                    productSellList.get(product_sell_cart_position).setAmount(String.valueOf(amount));
                    productSellList.get(product_sell_cart_position).setPrice(String.valueOf(amount_price));
                    productSellList.get(product_sell_cart_position).getTypeList().get(i).setType_amount(String.valueOf(type_amount));
                    inc_dec_check = 1;
                    break;
                }
            }
            if (inc_dec_check == 1) {
                inc_dec_dialog.dismiss();
                amount_inc_dec_adapter.notifyDataSetChanged();
            } else {
                ProductSel_type new_type = new ProductSel_type();
                new_type.setType_id(type.getId());
                new_type.setType_name(type.getType());
                new_type.setType_amount("1");
                productSellList.get(product_sell_cart_position).getTypeList().add(new_type);
                Double amount = Double.parseDouble(productSellList.get(product_sell_cart_position).getAmount());
                amount += 1;
                Double amount_price = Double.parseDouble(productSellList.get(product_sell_cart_position).getPrice());
                temp_price = Double.parseDouble(productSellList.get(product_sell_cart_position).getUnit_price());
                amount_price = amount * temp_price;
                //amount_price += Double.parseDouble(unit_price);
                productSellList.get(product_sell_cart_position).setAmount(String.valueOf(amount));
                productSellList.get(product_sell_cart_position).setPrice(String.valueOf(amount_price));
                inc_dec_dialog.dismiss();
                amount_inc_dec_adapter.notifyDataSetChanged();
            }
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void decreament(int position) {
        String selected_type_id = productSellList.get(product_sell_cart_position).getTypeList().get(position).getType_id();
        Double selected_type_amount = Double.parseDouble(productSellList.get(product_sell_cart_position).getTypeList().get(position).getType_amount());
        String unit_price = productSellList.get(product_sell_cart_position).getUnit_price();
        String unit_price_with_discount = productSellList.get(product_sell_cart_position).getUnit_price_with_discount();
        Double amount = Double.parseDouble(productSellList.get(product_sell_cart_position).getAmount());
        Double amount_price = Double.parseDouble(productSellList.get(product_sell_cart_position).getPrice());
        amount -= 1;
        Double temp_price = Double.parseDouble(unit_price);


        amount_price = amount * temp_price;
        if (selected_type_amount - 1.0 == 0.0) {
            productSellList.get(product_sell_cart_position).setAmount(String.valueOf(amount));
            productSellList.get(product_sell_cart_position).setPrice(String.valueOf(amount_price));
            productSellList.get(product_sell_cart_position).getTypeList().remove(position);
            amount_inc_dec_adapter.notifyDataSetChanged();
            inc_dec_dialog.dismiss();


        } else {
            selected_type_amount = selected_type_amount - 1;
            productSellList.get(product_sell_cart_position).setAmount(String.valueOf(amount));
            productSellList.get(product_sell_cart_position).setPrice(String.valueOf(amount_price));
            productSellList.get(product_sell_cart_position).getTypeList().get(position).setType_amount(String.valueOf(selected_type_amount));
            amount_inc_dec_adapter.notifyDataSetChanged();
            inc_dec_dialog.dismiss();

        }
        adapter.notifyDataSetChanged();


    }


}