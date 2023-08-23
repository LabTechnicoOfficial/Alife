package com.alifew.alife.view.Shop;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alifew.alife.Custom_Type.ProductSel_type;
import com.alifew.alife.Custom_Type.ProductSell;
import com.alifew.alife.Custom_Type.Product_sell_offer;
import com.alifew.alife.Custom_Type.productSell_temp;
import com.alifew.alife.R;
import com.alifew.alife.Utils.ImageHelper;
import com.alifew.alife.adapter.Selected_sell_product_list_adapter;
import com.alifew.alife.adapter.Sell_product_adapter;
import com.alifew.alife.adapter.Sell_success_adapter;
import com.alifew.alife.adapter.Shop_registered_customer_adapter;
import com.alifew.alife.adapter.Shop_sell_type_select_adapter;
import com.alifew.alife.adapter.Shop_sellamount_inc_dec_adapter;
import com.alifew.alife.adapter.selected_product_list_adapter;
import com.alifew.alife.model.add_product_sell_response;
import com.alifew.alife.model.add_sell_details_response;
import com.alifew.alife.model.add_sell_payment_cash_response;
import com.alifew.alife.model.add_payment_transaction_response;
import com.alifew.alife.model.get_all_product_offer_response;
import com.alifew.alife.model.get_count_for_type_response;
import com.alifew.alife.model.get_product_offer_response;
import com.alifew.alife.model.Get_product_response;
import com.alifew.alife.model.get_product_type_response;
import com.alifew.alife.model.payment_method_response;
import com.alifew.alife.model.push_notification_response;
import com.alifew.alife.model.shop_due_customer_response;
import com.alifew.alife.model.shop_profile_response;
import com.alifew.alife.model.update_product_stock_by_sell_response;
import com.alifew.alife.model.update_product_type_by_sell_response;
import com.alifew.alife.viewmodel.Get_all_shop_product;
import com.alifew.alife.viewmodel.Get_product;
import com.alifew.alife.viewmodel.Get_product_offer;
import com.alifew.alife.viewmodel.Get_product_type;
import com.alifew.alife.viewmodel.Payment_method;
import com.alifew.alife.viewmodel.Product_sell;
import com.alifew.alife.viewmodel.Product_sell_payment;
import com.alifew.alife.viewmodel.Push_notification;
import com.alifew.alife.viewmodel.ShopCustomerViewModel;
import com.alifew.alife.viewmodel.Shop_profile;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Shop_sell_selected_product_list_fragment extends Fragment implements Selected_sell_product_list_adapter.OnItemAddListener, Selected_sell_product_list_adapter.OnItemMinusListener, Selected_sell_product_list_adapter.OnItemRemoveListener, Sell_product_adapter.OnItemClickListener, Shop_registered_customer_adapter.OnItemClickListener, Shop_sell_type_select_adapter.OnItemSelectListener, Shop_sell_type_select_adapter.OnItemAddListener, Shop_sell_type_select_adapter.OnItemMinusListener, Shop_sellamount_inc_dec_adapter.addListener, Shop_sellamount_inc_dec_adapter.minusListener {
    String product_discount_all;
    Shop_profile shop_profile;
    Push_notification push_notification;
    String selected_product_unit_price;
    int product_sell_cart_position;
    private List<ProductSell> productsList;
    private List<ProductSel_type> productSel_types;
    private Shop_sell_type_select_adapter type_adapter;
    private Shop_sellamount_inc_dec_adapter amount_inc_dec_adapter;
    Dialog inc_dec_dialog;
    private List<shop_due_customer_response> customerList;
    List<Get_product_response> data;
    Double total_buy_price;
    Double total_price;
    RecyclerView productView, customerView;
    LinearLayoutManager layoutManager, layoutManager1, layoutManager2;
    AppCompatButton sellButton;
    LinearLayout addMoreButton, showCartLayout, customerDetailsLayout;
    LinearLayout dueLayout, customerIDLayout;
    AppCompatButton addCustomerButton;
    TextView dueText, profitText;
    Bitmap bitmapPDF;
    //Double paidAmount = 0.0;
    private FragmentManager fragmentManager;
    Get_product get_product;
    Get_product_type get_product_type;
    String shop_id, customer_id, customer_image, customer_name, customer_phone, customer_location;
    private Double stock;
    private Double type_amount_check;
    Dialog showCartAlert, addMoreAlert, selectCustomerAlert, addAmountAlert;
    private RecyclerView all_productView;
    private Sell_product_adapter adapter_more_product_add;
    Get_all_shop_product get_all_shop_product;
    EditText searchBar;
    TextView allDiscountText;
    CircularImageView customerImage;
    private String offer_type = "none";

    ExtendedFloatingActionButton addUnregisteredCustomer;
    private ShopCustomerViewModel get_customer;

    TextView customerName, customerLocation, customerPhone, customerID;
    TextView price, showDate, noProductsAvailableText;

    TextInputEditText paidText, priceLessText;
    TextInputLayout paidError, dueError;

    Spinner paymentMethodSpinner;
    private String paymentMethod[];// = {"Select Method", "Cash", "bKash", "Rocket", "Nagad"};
    List<payment_method_response> methods;
    Payment_method payment_method;
    String paymentSystem;
    // start for add more product
    String productID, productName, productUnit, productPrice, productImage, product_buy_price;
    TextView productNameText, productUnitText, stock_amount, sell_price, product_amount;
    Get_product_offer get_product_offer;
    List<get_product_type_response> types;
    private String product_types[];
    List<get_product_offer_response> offers;
    private String product_offers[];
    private String product_offers_price[];
    Spinner offerSpinner;
    List<String> offerType;
    List<get_all_product_offer_response> offer_all;
    private String type_id, type_name, type_count;
    private String offer_id;
    int available_type = 0, getOfferState = 0;
    LinearLayout typeLayout, getOfferLayout, offerSpinnerLayout, getOfferMainLayout;
    ImageView arrowDown, arrowUp;
    AppCompatButton nextButton;
    private int type_check = 0, offer_check = 0;
    private Double type_amount = 0.0;
    int x;
    RecyclerView typeView;

    private String minimum_offer_amount = "0";
    private String minimum_offer_pricee = "0";
    private String offer_percentage = "0";
    //end for add more product

    private Shop_registered_customer_adapter customerAdapter;
    private selected_product_list_adapter adapter;
    private Sell_success_adapter success_Adapter;
    private int error_check = 0;
    String buy_price, dateValue;
    //String myFormat = "dd/MM/yyyy";
    String myFormat = "yyyy-MM-dd";
    Product_sell product_sell;
    Product_sell_payment product_sell_payment;
    ImageView incButton, decButton;

    Spinner offerTypeSpinner, offerSecondSpinner;

    LinearLayout offerSecondSpinnerLayout, offerMainLayout;
    double TotalPrice;
    // EditText reducePrice;
    TextView offer_discount;
    TextView finalPrice, noOffersText;
    int page = 1, limit = 10, end = 0;
    ProgressBar progressBar;
    Spinner allOfferSpinner;
    LinearLayout allOfferLayout;
    CheckBox checkBox;
    Dialog loader;
    private double TotalBuyPrice;
    private double Totalprofit;
    int section;

    public Shop_sell_selected_product_list_fragment(String shop_id, List<ProductSell> productsList, String minimum_offer_amount, String minimum_offer_pricee, String offer_percentage, String offer_type, int section) {
        this.shop_id = shop_id;
        this.productsList = productsList;
        this.minimum_offer_amount = minimum_offer_amount;
        this.minimum_offer_pricee = minimum_offer_pricee;
        this.offer_percentage = offer_percentage;
        this.offer_type = offer_type;
        this.section = section;
    }

   /* private void all_offer_spinner() {
        allOfferSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    double count_amount = 0.0;
                    double count_price = 0.0;
                    for (int i = 0; i < productsList.size(); i++) {
                        if (productsList.get(i).getOffer_type().equals("whole")) {
                            count_amount += Double.parseDouble(productsList.get(i).getAmount());
                            count_price += Double.parseDouble(productsList.get(i).getPrice());
                        }
                    }
                    if (count_amount >= Double.parseDouble(offer_all.get(position - 1).getMinimum_amount()) && count_price >= Double.parseDouble(offer_all.get(position - 1).getMinimum_price())) {
                        for (int i = 0; i < productsList.size(); i++) {
                            if (productsList.get(i).getOffer_type().equals("whole")) {
                                Double amount = Double.parseDouble(productsList.get(i).getAmount());
                                Double percentage = Double.parseDouble(product_offers_price[position]);
                                Double price_value = amount * (Double.parseDouble(productsList.get(i).getUnit_price()) - (Double.parseDouble(productsList.get(i).getUnit_price()) * (percentage / 100)));
                                minimum_offer_amount = offer_all.get(position - 1).getMinimum_amount();
                                minimum_offer_pricee = offer_all.get(position - 1).getMinimum_price();
                                offer_percentage = product_offers_price[position];
                                productsList.get(i).setPrice(String.valueOf(price_value));
                                Product_sell_offer product_sell_offer = new Product_sell_offer("whole", minimum_offer_amount, minimum_offer_pricee, offer_percentage);
                                productsList.get(i).setSell_offer(product_sell_offer);
                                // productSell.setSell_offer(product_sell_offer);


                            }
                            if(i==productsList.size()-1)
                            {
                                show_product_cart();
                            }
                        }
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void select_all_offer() {
        get_product_offer = new ViewModelProvider(getActivity()).get(Get_product_offer.class);
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
                        // Double price_value = Double.parseDouble(offers.get(i).getAmount()) * (Double.parseDouble(productPrice) - (Double.parseDouble(productPrice) * (Double.parseDouble(offers.get(i).getPrice()) / 100)));
                        product_offers[i + 1] = offer_all.get(i).getMinimum_amount() + productUnit + "+          " + offer_all.get(i).getMinimum_price() + "taka+" + "       " + offer_all.get(i).getOffer_percentage() + "%";

                        // product_offers[i + 1] = offers.get(i).getAmount() + " " + productUnit + "          " + String.valueOf(Double.parseDouble(offers.get(i).getAmount()) * (Double.parseDouble(productPrice_with_discount) - (Double.parseDouble(productPrice_with_discount) * (Double.parseDouble(offers.get(i).getPrice()) / 100)))) + " taka";
                        product_offers_price[i + 1] = offer_all.get(i).getOffer_percentage();
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, product_offers);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                   allOfferSpinner.setAdapter(adapter);
                    all_offer_spinner();
                }
            }
        });
    }

    public void get_product_type_count(String productId, ProductSell product, Double amount) {
        get_product_type = new ViewModelProvider(getActivity()).get(Get_product_type.class);
        get_product_type.getdata(productId).observe(getViewLifecycleOwner(), new Observer<List<get_product_type_response>>() {
            @Override
            public void onChanged(List<get_product_type_response> get_product_type_responses) {
                for (int i = 0; i < get_product_type_responses.size(); i++) {
                    if (product.getType_id().equals(get_product_type_responses.get(i).getId())) {
                        if (amount < Double.parseDouble(get_product_type_responses.get(i).getCount())) {
                            product.setAmount(String.valueOf(Double.parseDouble(product.getAmount()) + 1));
                            product.setPrice(String.valueOf(Double.parseDouble(product.getAmount()) * Double.parseDouble(product.getUnit_price())));
                            adapter.notifyDataSetChanged();
                            double total_price = 0.0;
                            for (int j = 0; j < productsList.size(); j++) {
                                total_price += Double.parseDouble(productsList.get(j).getPrice());
                            }
                            // price.setText(String.valueOf(total_price));
                            price.setText(String.valueOf(new DecimalFormat("##.##").format(total_price)));
                            reducePrice.setText("0");
                            finalPrice.setText(String.valueOf(new DecimalFormat("##.##").format(total_price)));

                        } else {
                            Toast.makeText(getActivity(), "amount overflow", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public void get_product_stock(String productId, ProductSell product, Double amount) {
        stock = 0.0;
        get_product = new ViewModelProvider(getActivity()).get(Get_product.class);
        get_product.getsingle_product(productId).observe(getViewLifecycleOwner(), new Observer<get_product_response>() {
            @Override
            public void onChanged(get_product_response get_product_response) {
                stock = Double.parseDouble(get_product_response.getStock_amount());
                if (stock <= amount) {
                    Toast.makeText(getActivity(), "amount overflow", Toast.LENGTH_SHORT).show();
                } else {
                    // if (product.getType_id().equals("0")) {
                    product.setAmount(String.valueOf(Double.parseDouble(product.getAmount()) + 1));
                    double temp_price = 0.0;
                    if (!product.getSell_offer().getOffer_type().equals("none")) {

                        temp_price = Double.parseDouble(product.getUnit_price()) - (Double.parseDouble(product.getUnit_price()) * Double.parseDouble(product.getSell_offer().getOffer_percentage()) / 100);
                    } else {
                        temp_price = Double.parseDouble(product.getUnit_price());

                    }
                    product.setPrice(String.valueOf(Double.parseDouble(product.getAmount()) * temp_price));
                    adapter.notifyDataSetChanged();
                    double total_price = 0.0;
                    for (int i = 0; i < productsList.size(); i++) {
                        total_price += Double.parseDouble(productsList.get(i).getPrice());
                    }
                    //    price.setText(String.valueOf(total_price));
                    price.setText(String.valueOf(new DecimalFormat("##.##").format(total_price)));
                    reducePrice.setText("0");
                    finalPrice.setText(String.valueOf(new DecimalFormat("##.##").format(total_price)));
                   /* } else {
                        type_amount_check = 0.0;
                        for (int i = 0; i < productsList.size(); i++) {
                            if (product.getType_id().equals(productsList.get(i).getType_id())) {
                                type_amount_check += Double.parseDouble(product.getAmount());
                            }
                        }
                        get_product_type_count(productId, product, type_amount_check);


                }
            }
        });

    }*/

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main();
    }

    private void main() {
        checkConnection();

        payment_method = new ViewModelProvider(getActivity()).get(Payment_method.class);
        payment_method.getData("paymentSystem").observe(getViewLifecycleOwner(), new Observer<List<payment_method_response>>() {
            @Override
            public void onChanged(List<payment_method_response> payment_method_responses) {
                methods = new ArrayList<>();
                methods = payment_method_responses;

                paymentMethod = new String[methods.size() + 1];
                paymentMethod[0] = "Select Method";
                for (int i = 0; i < methods.size(); i++) {
                    paymentMethod[i + 1] = methods.get(i).getName();
                }
                ArrayAdapter<String> paymentMethodSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, paymentMethod);
                paymentMethodSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                paymentMethodSpinner.setAdapter(paymentMethodSpinnerAdapter);
            }
        });


        paymentMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                paymentSystem = String.valueOf(parent.getItemAtPosition(pos));
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        addMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_more_product();
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
                    if (productsList.size() > 0) {
                        //finale_productList_check();
                        if (paymentSystem.equals("Select Method")) {
                            Toast.makeText(getActivity(), "Select a method", Toast.LENGTH_SHORT).show();
                        } else {
                            product_sell_function();
                        }
                    }
                }

                // Toast.makeText(getActivity(), "Hello", Toast.LENGTH_LONG).show();
            }
        });
        int check_offer_spinner = 0;
        show_product_cart();
        /*for (int i = 0; i < productsList.size(); i++) {
            if (productsList.get(i).getOffer_type().equals("whole")) {
                check_offer_spinner = 1;
                break;
            }
        }*/
        // Log.d("check: ",productsList.get(0).getOffer_type());

        //reduce price scope
       /* reducePrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (reducePrice.getText().toString().trim().isEmpty()) {
                    reducePrice.setText("0");
                    finalPrice.setText(String.valueOf(new DecimalFormat("##.##").format(TotalPrice)));
                } else {
                    finalPrice.setText(String.valueOf(new DecimalFormat("##.##").format(TotalPrice - Double.parseDouble(reducePrice.getText().toString().trim()))));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
        //end reduce price scope
    }

    private void get_all_product_discount() {
        shop_profile = new ViewModelProvider(getActivity()).get(Shop_profile.class);
        shop_profile.getData(shop_id).observe(getViewLifecycleOwner(), new Observer<shop_profile_response>() {
            @Override
            public void onChanged(shop_profile_response shop_profile_response) {
                //product_discount_all = shop_profile_response.getAll_discount();
                allDiscountText.setText(shop_profile_response.getAll_discount());
            }
        });
    }

    private void show_product_cart() {
        if (productsList.size() != 0) {
            List<productSell_temp> productSell_tempList = new ArrayList<>();
            for (int i = 0; i < productsList.size(); i++) {
                productSell_temp temp = new productSell_temp();
                temp.setProduct_name(productsList.get(i).getProduct_name());
                temp.setAmount(productsList.get(i).getAmount());
                temp.setPrice(productsList.get(i).getPrice());
                temp.setProduct_image(productsList.get(i).getProduct_image());
                String s = "";
                if (productsList.get(i).getTypeList().size() > 0) {

                    for (int j = 0; j < productsList.get(i).getTypeList().size(); j++) {
                        if (!s.equals("")) {
                            s = s + " ";
                        }
                        s = s + productsList.get(i).getTypeList().get(j).getType_name() + "(" + productsList.get(i).getTypeList().get(j).getType_amount() + ")";

                    }
                }
                temp.setType_name(s);
                productSell_tempList.add(temp);
            }
            noProductsAvailableText.setVisibility(View.GONE);
            productView.setVisibility(View.VISIBLE);

            adapter = new selected_product_list_adapter(productSell_tempList);
            //adapter.setOnClickListener(Shop_sell_selected_product_list_fragment.this::OnItemAdd, Shop_sell_selected_product_list_fragment.this::OnItemMinus, Shop_sell_selected_product_list_fragment.this::OnItemRemove);
            productView.setAdapter(adapter);
            TotalPrice = 0.0;
            TotalBuyPrice = 0.0;
            Totalprofit = 0.0;
            for (int i = 0; i < productsList.size(); i++) {
                TotalPrice += Double.parseDouble(productsList.get(i).getPrice());
                TotalBuyPrice += Double.parseDouble(productsList.get(i).getBuy_price());
            }
            Totalprofit = TotalPrice - TotalBuyPrice;
            price.setText(String.valueOf(new DecimalFormat("##.##").format(TotalPrice)));
            offer_discount.setText(String.valueOf(new DecimalFormat("##.##").format(TotalPrice * (Double.parseDouble(offer_percentage) / 100))) + "(" + offer_percentage + "%)");
            TotalPrice = TotalPrice - TotalPrice * (Double.parseDouble(offer_percentage) / 100);
            finalPrice.setText(String.valueOf(new DecimalFormat("##.##").format(TotalPrice)));
            profitText.setText(new DecimalFormat("##.##").format(Totalprofit));


        } else {
            productView.setVisibility(View.GONE);
            noProductsAvailableText.setVisibility(View.VISIBLE);
        }
    }

    private void add_more_product() {
        get_all_product_discount();
        data = new ArrayList<>();
        ImageView closeButton = (ImageView) addMoreAlert.findViewById(R.id.crossID);
        layoutManager1 = new LinearLayoutManager(getActivity());
        all_productView = (RecyclerView) addMoreAlert.findViewById(R.id.productsViewID);
        searchBar = (EditText) addMoreAlert.findViewById(R.id.searchEditText);
        allDiscountText = (TextView) addMoreAlert.findViewById(R.id.allDiscountID);

        all_productView.setHasFixedSize(true);
        all_productView.setLayoutManager(layoutManager1);
        adapter_more_product_add = new Sell_product_adapter(data);
        adapter_more_product_add.setOnClickListener(Shop_sell_selected_product_list_fragment.this::OnItemClick);
        all_productView.setAdapter(adapter_more_product_add);
        page = 1;
        end = 0;
        select_from_all_product(page, limit);
        addMoreAlert.show();
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
                        adapter_more_product_add.setOnClickListener(Shop_sell_selected_product_list_fragment.this::OnItemClick);
                        all_productView.setAdapter(adapter_more_product_add);
                        get_search_product(searchBar.getText().toString().trim());

                    } catch (Exception e) {

                    }
                } else {
                    data = new ArrayList<>();
                    adapter_more_product_add = new Sell_product_adapter(data);
                    adapter_more_product_add.setOnClickListener(Shop_sell_selected_product_list_fragment.this::OnItemClick);
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

    private void get_search_product(String value) {
        get_all_shop_product = new ViewModelProvider(getActivity()).get(Get_all_shop_product.class);
        get_all_shop_product.getSearchData(shop_id).observe(getViewLifecycleOwner(), new Observer<List<Get_product_response>>() {
            @Override
            public void onChanged(List<Get_product_response> get_product_responses) {
                for (int i = 0; i < get_product_responses.size(); i++) {
                    String brand_code = get_product_responses.get(i).getBrand() + get_product_responses.get(i).getCode();
                    if ((get_product_responses.get(i).getProduct_name().toLowerCase().contains(value.toLowerCase())) || (get_product_responses.get(i).getBrand().toLowerCase().contains(value.toLowerCase())) || (brand_code.toLowerCase().contains(value.toLowerCase()))) {
                        data.add(get_product_responses.get(i));
                    }
                }
                adapter_more_product_add = new Sell_product_adapter(data);
                adapter_more_product_add.setOnClickListener(Shop_sell_selected_product_list_fragment.this::OnItemClick);
                all_productView.setAdapter(adapter_more_product_add);

            }
        });
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
                adapter_more_product_add.setOnClickListener(Shop_sell_selected_product_list_fragment.this::OnItemClick);
                all_productView.setAdapter(adapter_more_product_add);


            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_sell_selected_product_list_fragment, container, false);
        checkConnection();

        sellButton = (AppCompatButton) view.findViewById(R.id.sellButtonID);
        addCustomerButton = (AppCompatButton) view.findViewById(R.id.addCustomerID);

        customerImage = (CircularImageView) view.findViewById(R.id.customerImageID);
        customerID = (TextView) view.findViewById(R.id.customerid_ID);
        customerName = (TextView) view.findViewById(R.id.customerNameID);
        customerPhone = (TextView) view.findViewById(R.id.customerPhoneID);
        customerLocation = (TextView) view.findViewById(R.id.customerLocationID);
        showDate = (TextView) view.findViewById(R.id.showDateID);
        price = (TextView) view.findViewById(R.id.priceID);
        finalPrice = (TextView) view.findViewById(R.id.finalPriceID);
        offer_discount = (TextView) view.findViewById(R.id.offer_discountID);
        noProductsAvailableText = (TextView) view.findViewById(R.id.noProductsAvailableID);
        profitText = (TextView) view.findViewById(R.id.profitTextID);

        showCartLayout = (LinearLayout) view.findViewById(R.id.showCartID);
        addMoreButton = (LinearLayout) view.findViewById(R.id.addMoreID);
        customerDetailsLayout = (LinearLayout) view.findViewById(R.id.customerDetailsLayoutID);
        customerIDLayout = (LinearLayout) view.findViewById(R.id.customerIDLayoutID);
        dueLayout = (LinearLayout) view.findViewById(R.id.dueLayoutID);

        dueText = (TextView) view.findViewById(R.id.dueTextID);
        paidText = (TextInputEditText) view.findViewById(R.id.paidTextID);
        priceLessText = (TextInputEditText) view.findViewById(R.id.priceLessTextID);

        paidError = (TextInputLayout) view.findViewById(R.id.paidErrorID);

        productView = (RecyclerView) view.findViewById(R.id.productsViewID);
        productView.setHasFixedSize(true);
        productView.setLayoutManager(new LinearLayoutManager(getContext()));

        checkBox = (CheckBox) view.findViewById(R.id.checkBoxID);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox.isChecked()) {
                    if (!dueText.getText().toString().trim().equals("")) {
                        sellButton.setEnabled(true);
                        sellButton.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.pink));
                    }

                } else {
                    sellButton.setEnabled(false);
                    sellButton.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.grey));
                }
            }
        });

        // start payment method selection
        paymentMethodSpinner = (Spinner) view.findViewById(R.id.paymentMethodSpinnerID);

        //end payment method selection
        fragmentManager = getFragmentManager();

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        showCartAlert = new Dialog(getActivity());
        showCartAlert.setContentView(R.layout.show_cart_alert);
        showCartAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        showCartAlert.setCancelable(false);

        addMoreAlert = new Dialog(getActivity());
        addMoreAlert.setContentView(R.layout.add_more_sellproduct_alert);
        addMoreAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addMoreAlert.setCancelable(false);

        selectCustomerAlert = new Dialog(getActivity());
        selectCustomerAlert.setContentView(R.layout.shop_sell_select_customer_alert);
        selectCustomerAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        selectCustomerAlert.setCancelable(false);

        addAmountAlert = new Dialog(getActivity());
        addAmountAlert.setContentView(R.layout.shop_add_more_sellproduct_alert);
        addAmountAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addAmountAlert.setCancelable(false);

        //add more product alert


        //start for add more product component declear
        productNameText = (TextView) addAmountAlert.findViewById(R.id.productNameID);
        productUnitText = (TextView) addAmountAlert.findViewById(R.id.productUnitID);
        stock_amount = (TextView) addAmountAlert.findViewById(R.id.stockAmountID);
        sell_price = (TextView) addAmountAlert.findViewById(R.id.priceID);
        product_amount = (TextView) addAmountAlert.findViewById(R.id.amountTextID);
        noOffersText = (TextView) addAmountAlert.findViewById(R.id.noOffersTextID);


        offerSecondSpinnerLayout = (LinearLayout) addAmountAlert.findViewById(R.id.offerSecondSpinnerLayoutID);
        offerMainLayout = (LinearLayout) addAmountAlert.findViewById(R.id.offerMainLayoutID);

        typeView = (RecyclerView) addAmountAlert.findViewById(R.id.typeViewID);
        typeView.setHasFixedSize(true);
        typeView.setLayoutManager(new LinearLayoutManager(getContext()));

        offerSpinner = (Spinner) addAmountAlert.findViewById(R.id.offerSpinnerID);
        offerTypeSpinner = (Spinner) addAmountAlert.findViewById(R.id.offerTypeSpinnerID);
        offerSecondSpinner = (Spinner) addAmountAlert.findViewById(R.id.offerSecondSpinnerID);

        //offerTypeSpinner.setOnItemSelectedListener(this);

        typeLayout = (LinearLayout) addAmountAlert.findViewById(R.id.typeLayoutID);
        getOfferLayout = (LinearLayout) addAmountAlert.findViewById(R.id.getOfferLayoutID);
        offerSpinnerLayout = (LinearLayout) addAmountAlert.findViewById(R.id.offerSpinnerLayoutID);
        getOfferMainLayout = (LinearLayout) addAmountAlert.findViewById(R.id.getOfferMainLayoutID);

        arrowDown = (ImageView) addAmountAlert.findViewById(R.id.arrowDownID);
        arrowUp = (ImageView) addAmountAlert.findViewById(R.id.arrowUpID);
        nextButton = (AppCompatButton) addAmountAlert.findViewById(R.id.nextButtonID);
        decButton = (ImageView) addAmountAlert.findViewById(R.id.minusButtonID);
        incButton = (ImageView) addAmountAlert.findViewById(R.id.plusButtonID);

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

        paidText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkBox.setChecked(false);

                if ((paidText.getText().toString().trim().isEmpty())) {
                    priceLessText.setVisibility(View.GONE);
                    dueLayout.setVisibility(View.GONE);
                    checkBox.setChecked(false);
                    sellButton.setEnabled(false);
                    sellButton.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.grey));

                } else {
                    dueLayout.setVisibility(View.VISIBLE);
                    Double paidAmount = Double.parseDouble(paidText.getText().toString().trim());
                    Double finalAmount = Double.parseDouble(finalPrice.getText().toString().trim());
                    profitText.setText(String.valueOf(new DecimalFormat("##.##").format(paidAmount - TotalBuyPrice)));

                    priceLessText.setVisibility(View.VISIBLE);
                    priceLessText.setText(String.valueOf(new DecimalFormat("##.##").format(finalAmount - paidAmount)));
                    dueText.setText(String.valueOf("0.0"));
                    checkBox.setChecked(true);
                    sellButton.setEnabled(true);
                    sellButton.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.pink));


                    Double less_price = 0.0;
                    if (!priceLessText.getText().toString().trim().isEmpty()) {
                        less_price = Double.parseDouble(priceLessText.getText().toString().trim());

                    }
                    /*Double paidAmount = Double.parseDouble(paidText.getText().toString().trim());
                    Double finalAmount = Double.parseDouble(finalPrice.getText().toString().trim());
                    dueText.setText(String.valueOf(new DecimalFormat("##.##").format(finalAmount - paidAmount - less_price)));
               */
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        priceLessText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkBox.setChecked(false);
                if (!priceLessText.getText().toString().trim().isEmpty() && !priceLessText.getText().toString().trim().equals(".")) {
                    Double less_price = Double.parseDouble(priceLessText.getText().toString().trim());
                    if (!paidText.getText().toString().trim().isEmpty()) {
                        Double paidAmount = Double.parseDouble(paidText.getText().toString().trim());
                        Double finalAmount = Double.parseDouble(finalPrice.getText().toString().trim());
                        dueLayout.setVisibility(View.VISIBLE);
                        if (paidAmount + less_price == finalAmount) {
                            dueText.setText("0.0");
                            checkBox.setChecked(true);
                            sellButton.setEnabled(true);
                            sellButton.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.pink));
                            profitText.setText(String.valueOf(new DecimalFormat("##.##").format(paidAmount - TotalBuyPrice)));


                        } else {
                            dueText.setText(String.valueOf(new DecimalFormat("##.##").format(finalAmount - paidAmount - less_price)));
                            checkBox.setChecked(false);
                            sellButton.setEnabled(false);
                            sellButton.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.grey));
                            profitText.setText(String.valueOf(new DecimalFormat("##.##").format(finalAmount - less_price - TotalBuyPrice)));

                        }
                    } else {
                        dueText.setText("");
                        dueLayout.setVisibility(View.GONE);
                    }
                } else {
                    if (!paidText.getText().toString().trim().isEmpty()) {
                        Double paidAmount = Double.parseDouble(paidText.getText().toString().trim());
                        Double finalAmount = Double.parseDouble(finalPrice.getText().toString().trim());
                        dueLayout.setVisibility(View.VISIBLE);
                        dueText.setText(String.valueOf(new DecimalFormat("##.##").format(finalAmount - paidAmount)));
                        checkBox.setChecked(false);
                        sellButton.setEnabled(false);
                        sellButton.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.grey));

                    } else {
                        dueText.setText("");
                        dueLayout.setVisibility(View.GONE);
                        checkBox.setChecked(false);
                        sellButton.setEnabled(false);
                        sellButton.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.grey));

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double amount = Double.parseDouble(product_amount.getText().toString().trim());
                if (amount > 0.0) {
                    amount -= 1.0;
                    product_amount.setText(String.valueOf(amount));
                    Double price_value = amount * Double.parseDouble(productPrice);
                    sell_price.setText(String.valueOf(price_value));
                }
            }
        });

        incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double amount = Double.parseDouble(product_amount.getText().toString().trim());
                amount += 1.0;

                for (int i = 0; i < productsList.size(); i++) {
                    if (productID.equals(productsList.get(i).getProduct_id())) {
                        amount += Double.parseDouble(productsList.get(i).getAmount());
                    }
                }
                if (amount <= Double.parseDouble(stock_amount.getText().toString().trim())) {
                    product_amount.setText(String.valueOf(amount));
                    Double price_value = amount * Double.parseDouble(productPrice);
                    sell_price.setText(String.valueOf(price_value));
                }
            }
        });

        //end for add more product componenet declear

        dateValue = new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date());
        showDate.setText(dateValue);
        showDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        dateValue = sdf.format(myCalendar.getTime());
                        showDate.setText(dateValue);
                        //Toast.makeText(getActivity(), dateValue, Toast.LENGTH_SHORT).show();
                    }

                };

                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productsList.size() != 0) {
                    selectProduct();
                } else {
                    Toast.makeText(getActivity(), "No Product Selected", Toast.LENGTH_SHORT).show();
                }

            }
        });

        show_product_cart();

        return view;
    }

    private void show_offer_type_spinner() {
        offerTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // type_name = parent.getItemAtPosition(position).toString();
                //   product_amount.setText(String.valueOf(offerType.size()));
                if (offerType.size() == 2) {
                    if (position == 0) {


                        offerSecondSpinnerLayout.setVisibility(View.GONE);
                        offerSpinnerLayout.setVisibility(View.VISIBLE);
                        x = 1;
                        get_offer();

                    } else {
                        //getOfferMainLayout.setVisibility(View.GONE);
                        //typeLayout.setVisibility(View.INVISIBLE);
                        x = 2;
                        offerSpinnerLayout.setVisibility(View.GONE);
                        offerSecondSpinnerLayout.setVisibility(View.VISIBLE);
                        get_all_product_offer();
                    }
                } else if (offerType.size() == 1) {
                    if (offerType.get(0).equals("Offer on whole product")) {
                        x = 2;
                        offerSpinnerLayout.setVisibility(View.GONE);
                        offerSecondSpinnerLayout.setVisibility(View.VISIBLE);
                        get_all_product_offer();
                    } else {
                        x = 1;
                        offerSecondSpinnerLayout.setVisibility(View.GONE);
                        offerSpinnerLayout.setVisibility(View.VISIBLE);
                        get_offer();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void selectProduct() {
        selectCustomerAlert.show();
        customerView = (RecyclerView) selectCustomerAlert.findViewById(R.id.customersViewID);
        addUnregisteredCustomer = (ExtendedFloatingActionButton) selectCustomerAlert.findViewById(R.id.addCustomerID);
        ImageView closeButton = (ImageView) selectCustomerAlert.findViewById(R.id.closeButtonID);
        EditText searchBox = (EditText) selectCustomerAlert.findViewById(R.id.searchEditText);

        layoutManager2 = new LinearLayoutManager(getContext());
        customerView.setHasFixedSize(true);
        customerView.setLayoutManager(layoutManager2);
        customerList = new ArrayList<>();
        customerAdapter = new Shop_registered_customer_adapter(customerList);
        customerAdapter.setOnClickListener(Shop_sell_selected_product_list_fragment.this::OnItemClickCustomer);
        customerView.setAdapter(customerAdapter);
        searchBox.setText("");
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(searchBox.getText().toString().trim())) {
                    customerList = new ArrayList<>();
                    customerAdapter = new Shop_registered_customer_adapter(customerList);
                    customerAdapter.setOnClickListener(Shop_sell_selected_product_list_fragment.this::OnItemClickCustomer);
                    customerView.setAdapter(customerAdapter);
                    get_shop_customer(searchBox.getText().toString().trim());

                } else {
                    // customerList = new ArrayList<>();
                    //customerList.removeAll((Collection<? extends shop_due_customer_response>) );
                    customerList = new ArrayList<>();
                    customerAdapter = new Shop_registered_customer_adapter(customerList);
                    customerAdapter.setOnClickListener(Shop_sell_selected_product_list_fragment.this::OnItemClickCustomer);
                    customerView.setAdapter(customerAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        addUnregisteredCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                default_customer();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCustomerAlert.dismiss();
            }
        });

        customerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && addUnregisteredCustomer.getVisibility() == View.VISIBLE) {
                    addUnregisteredCustomer.hide();
                } else if (dy < 0 && addUnregisteredCustomer.getVisibility() != View.VISIBLE) {
                    addUnregisteredCustomer.show();
                }
            }
        });
    }

    public void get_shop_customer(String search) {
        get_customer = new ViewModelProvider(getActivity()).get(ShopCustomerViewModel.class);
        get_customer.get_due_customer_by_search(shop_id, search).observe(getViewLifecycleOwner(), new Observer<List<shop_due_customer_response>>() {
            @Override
            public void onChanged(List<shop_due_customer_response> get_shop_customer_responses) {
                customerList.clear();
                customerList = get_shop_customer_responses;
                // customerAdapter.notifyDataSetChanged();
                customerAdapter = new Shop_registered_customer_adapter(customerList);
                customerAdapter.setOnClickListener(Shop_sell_selected_product_list_fragment.this::OnItemClickCustomer);
                customerView.setAdapter(customerAdapter);
            }
        });
    }

    public void default_customer() {
        Dialog alert = new Dialog(getActivity());
        alert.setContentView(R.layout.unregistered_customer_alert);
        alert.show();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.setCancelable(false);

        ImageView closeButton = (ImageView) alert.findViewById(R.id.closeID);
        TextView doneButton = (TextView) alert.findViewById(R.id.doneButtonID);

        TextInputEditText nameText = (TextInputEditText) alert.findViewById(R.id.nameTextID);
        TextInputEditText phoneText = (TextInputEditText) alert.findViewById(R.id.contactText);
        TextInputEditText locationText = (TextInputEditText) alert.findViewById(R.id.locationTextID);

        TextInputLayout nameError = (TextInputLayout) alert.findViewById(R.id.nameErrorID);
        TextInputLayout phoneError = (TextInputLayout) alert.findViewById(R.id.phoneErrorID);
        TextInputLayout locationError = (TextInputLayout) alert.findViewById(R.id.locationErrorID);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString().trim();
                String phone = phoneText.getText().toString().trim();
                String location = locationText.getText().toString().trim();

                nameError.setErrorEnabled(false);
                phoneError.setErrorEnabled(false);
                locationError.setErrorEnabled(false);

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(location)) {
                    if (TextUtils.isEmpty(name)) {
                        nameError.setError(" ");
                    } else if (TextUtils.isEmpty(phone)) {
                        phoneError.setError(" ");
                    } else if (TextUtils.isEmpty(location)) {
                        locationError.setError(" ");
                    }
                } else {
                    customer_name = name;
                    customer_location = location;
                    customer_phone = phone;
                    customer_id = "0";
                    customer_image = "blank";

                    //redirect to new  fragment along with shop_id, customer_id,customer_image,customer_name,customer_phone,customer_location,productList
                    alert.dismiss();
                    customerDetailsFunction(customer_id, customer_image, customer_name, customer_phone, customer_location);
                }
            }
        });


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                //layout.setVisibility(View.VISIBLE);
            }
        });

    }

    private void categoriesorproducts(String shop_id, List<ProductSell> productsList) {
        fragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.frame_container, new Shop_sellcategoriesORproducts_fragment(shop_id, productsList)).addToBackStack(null).commit();
    }

    @Override
    public void OnItemAdd(int position) {
        product_sell_cart_position = position;
        ProductSell product = productsList.get(position);
        List<ProductSel_type> selected_types = product.getTypeList();
        String selected_productId = product.getProduct_id();

        String type_id = product.getType_id();
        // String offer_id = product.getOffer_id();
       /* Double amount = 0.0;
        for (int i = 0; i < productsList.size(); i++) {
            if (productsList.get(i).getProduct_id().equals(selected_productId)) {
                amount += Double.parseDouble(productsList.get(i).getAmount());
            }
        }
        get_product_stock(selected_productId, product, amount);*/


        IncDecDialog(selected_types, "add", selected_productId);
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
                    amount_inc_dec_adapter.setOnClickListener(Shop_sell_selected_product_list_fragment.this::increament, Shop_sell_selected_product_list_fragment.this::decreament);
                    typeView.setAdapter(amount_inc_dec_adapter);

                } else {
                    if (operation.equals("add")) {
                        Double amount = 0.0;
                        for (int i = 0; i < productsList.size(); i++) {
                            if (productsList.get(i).getProduct_id().equals(productsList.get(product_sell_cart_position).getProduct_id())) {
                                amount += Double.parseDouble(productsList.get(i).getAmount());
                            }
                        }
                        // get_product_stock(productsList.get(product_sell_cart_position).getProduct_id(), productsList.get(product_sell_cart_position), amount);

                    } else {
                        Double sell_amount = Double.parseDouble(productsList.get(product_sell_cart_position).getAmount());
                        String unit_price = productsList.get(product_sell_cart_position).getUnit_price();
                        Double temp_price = 0.0;
                        if (sell_amount > 0) {
                            sell_amount -= 1;
                            productsList.get(product_sell_cart_position).setAmount(String.valueOf(sell_amount));
                           /* if (productsList.get(product_sell_cart_position).getSell_offer().getOffer_type().equals("individual")) {
                                if (sell_amount < Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_minimum_amount())) {
                                    temp_price = Double.parseDouble(unit_price);
                                } else {
                                    temp_price = Double.parseDouble(unit_price) - Double.parseDouble(unit_price) * (Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_percentage()) / 100);
                                }
                            } else if (productsList.get(product_sell_cart_position).getSell_offer().getOffer_type().equals("whole")) {
                                if ((sell_amount >= Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_minimum_amount())) && ((sell_amount * Double.parseDouble(unit_price)) >= Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_minimum_price()))) {
                                    temp_price = Double.parseDouble(unit_price) - Double.parseDouble(unit_price) * (Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_percentage()) / 100);

                                } else {
                                    temp_price = Double.parseDouble(unit_price);
                                }
                            } else {
                                temp_price = Double.parseDouble(unit_price);
                            }*/

                            productsList.get(product_sell_cart_position).setPrice(String.valueOf(Double.parseDouble(productsList.get(product_sell_cart_position).getAmount()) * temp_price));
                        }
                        adapter.notifyDataSetChanged();
                        double total_price = 0.0;
                        for (int i = 0; i < productsList.size(); i++) {
                            total_price += Double.parseDouble(productsList.get(i).getPrice());
                        }
                        //  price.setText(String.valueOf(total_price));
                        price.setText(String.valueOf(new DecimalFormat("##.##").format(total_price)));
                        offer_discount.setText("0");
                        finalPrice.setText(String.valueOf(new DecimalFormat("##.##").format(total_price)));
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

    @Override
    public void OnItemMinus(int position) {
        product_sell_cart_position = position;
        ProductSell product = productsList.get(position);
        List<ProductSel_type> selected_types = product.getTypeList();
        String sell_amount = product.getAmount();
        String unit_price = product.getUnit_price();
        if (Double.parseDouble(sell_amount) > 0.0) {
            IncDecDialog(selected_types, "minus", product.getProduct_id());
        }

    }

    @Override
    public void OnItemRemove(int position) {
        productsList.remove(position);
        adapter.notifyDataSetChanged();
        if (productsList.size() == 0) {
            showCartAlert.dismiss();
        }
    }

    /*
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.frame_container, new Shop_sellcategoriesORproducts_fragment(shop_id, productsList)).addToBackStack(null).commit();
    }

    */

    /*
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.frame_container, new Shop_sellcategoriesORproducts_fragment(shop_id, productsList)).addToBackStack(null).commit();
    }

     */

    @Override
    public void onDetach() {
        super.onDetach();
        if (section == 1) {
            categoriesorproducts(shop_id, productsList);
        } else if (section == 2) {
            fragmentManager.beginTransaction().setCustomAnimations(
                    R.anim.slide_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.fade_in,   // popEnter
                    R.anim.slide_out  // popExit
            ).replace(R.id.frame_container, new Bar_code_fragment(shop_id)).addToBackStack(null).commit();

        }

    }


    @Override
    public void OnItemClick(int position) {
        Get_product_response product = data.get(position);
        productID = product.getProduct_id();
        addMoreAlert.dismiss();

        addAmountAlert.show();
        add_more_product_function();
        ImageView closeButton = (ImageView) addAmountAlert.findViewById(R.id.closeID);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAmountAlert.dismiss();
            }
        });
    }


    @Override
    public void OnItemClickCustomer(int position) {
        shop_due_customer_response customer = customerList.get(position);
        customer_id = customer.getCustomer_id();
        customer_name = customer.getCustomer_name();
        customer_phone = customer.getCustomer_phone();
        customer_location = customer.getCustomer_address();
        customer_image = customer.getCustomer_image();
        //nnnn
        customerDetailsFunction(customer_id, customer_image, customer_name, customer_phone, customer_location);

    }

    private void customerDetailsFunction(String customer_id, String customer_image, String customer_name, String customer_phone, String customer_location) {
        selectCustomerAlert.dismiss();
        customerDetailsLayout.setVisibility(View.VISIBLE);
        if (!customer_image.equals("blank")) {
            customerImage.setVisibility(View.VISIBLE);

            ImageHelper.imageLoader(getActivity(), customerImage, customer_image);
        } else {
            customerImage.setVisibility(View.GONE);
        }

        if (!customer_id.equals("0")) {
            customerID.setVisibility(View.VISIBLE);
            customerID.setText(customer_id);
        } else {
            customerIDLayout.setVisibility(View.GONE);
        }
        customerName.setText(customer_name);
        customerPhone.setText(customer_phone);
        customerLocation.setText(customer_location);
    }

    // need for final submition
    int p = 0;

    private void finale_productList_check() {
        get_product = new ViewModelProvider(getActivity()).get(Get_product.class);
        get_product_type = new ViewModelProvider(getActivity()).get(Get_product_type.class);
        error_check = 0;
        for (p = 0; p < productsList.size(); p++) {
            if (error_check != 1) {
                if (productsList.get(p).getType_id().equals("0")) {
                    String listed_amount = productsList.get(p).getAmount();
                    Toast.makeText(getActivity(), String.valueOf(p), Toast.LENGTH_SHORT).show();
                    get_product.getsingle_product(productsList.get(p).getProduct_id()).observe(getViewLifecycleOwner(), new Observer<Get_product_response>() {
                        @Override
                        public void onChanged(Get_product_response get_product_response) {
                            if (Double.parseDouble(listed_amount) > Double.parseDouble(get_product_response.getStock_amount())) {
                                Toast.makeText(getActivity(), get_product_response.getProduct_name() + " amount stock overflow", Toast.LENGTH_SHORT).show();
                                error_check = 1;
                            }
                        }
                    });

                } else {
                    String listed_amount = productsList.get(p).getAmount();
                    String id = productsList.get(p).getProduct_id();
                    String type_id = productsList.get(p).getType_id();
                    ProductSell product = productsList.get(p);
                    get_product_type.getdata(id).observe(getViewLifecycleOwner(), new Observer<List<get_product_type_response>>() {
                        @Override
                        public void onChanged(List<get_product_type_response> get_product_type_responses) {
                            for (int j = 0; j < get_product_type_responses.size(); j++) {
                                if (get_product_type_responses.get(j).getCount().equals(type_id)) {
                                    if (Double.parseDouble(listed_amount) > Double.parseDouble(get_product_type_responses.get(j).getCount())) {
                                        Toast.makeText(getActivity(), get_product_type_responses.get(j).getType() + " amount stock overflow", Toast.LENGTH_SHORT).show();
                                        error_check = 1;
                                    }
                                }
                            }
                        }
                    });

                }
            }
        }
        if (error_check != 1) {
            //move to next
            product_sell_function();
        } else {
            // error found
        }
    }

    private void product_sell_function() {
        //String paid_amount = paidText.getText().toString().trim();
        Double paid_amount = Double.parseDouble(paidText.getText().toString().trim());
        int customer_sell_check = 0;
        if (customer_id.equals("0")) {
            double final_price = 0.0;
            final_price = Double.parseDouble(finalPrice.getText().toString().trim());

            if (!priceLessText.getText().toString().trim().isEmpty()) {
                final_price = final_price - Double.parseDouble(priceLessText.getText().toString().trim());
            }
            if (paid_amount == final_price) {

                customer_sell_check = 0;
            } else {
                sellButton.setVisibility(View.GONE);
                customer_sell_check = 1;
            }
        }
        if (customer_sell_check == 0) {
            product_sell = new ViewModelProvider(getActivity()).get(Product_sell.class);
            product_sell_payment = new ViewModelProvider(getActivity()).get(Product_sell_payment.class);
            total_buy_price = 0.0;
            total_price = 0.0;
            if (!priceLessText.getText().toString().trim().isEmpty()) {
                total_price = Double.parseDouble(finalPrice.getText().toString().trim()) - Double.parseDouble(priceLessText.getText().toString().trim());
           /* for (int i = 0; i < productsList.size(); i++) {
                total_buy_price += Double.parseDouble(productsList.get(i).getBuy_price());
                total_price += Double.parseDouble(productsList.get(i).getPrice());
            }*/
            } else {
                total_price = Double.parseDouble(finalPrice.getText().toString().trim());
            }
            for (int i = 0; i < productsList.size(); i++) {
                total_buy_price += Double.parseDouble(productsList.get(i).getBuy_price());

            }
            // total_price = Double.parseDouble(finalPrice.getText().toString().trim());
            loader.show();
            double price_less_rate = total_price / Double.parseDouble(finalPrice.getText().toString().trim());

            Double duePrice = total_price - total_buy_price;

            product_sell.sell(shop_id, customer_id, customer_name, customer_phone, String.valueOf(total_price), String.valueOf(total_buy_price), String.valueOf(duePrice), "0","systemetic","", showDate.getText().toString().trim()).observe(getViewLifecycleOwner(), new Observer<add_product_sell_response>() {
                @Override
                public void onChanged(add_product_sell_response add_product_sell_response) {

                    if (!(add_product_sell_response.getSell_id().equals("0"))) {
                        // Toast.makeText(getActivity(),add_product_sell_response.getSell_id(),Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < productsList.size(); i++) {
                            Double priceSell = Double.parseDouble(productsList.get(i).getPrice()) * price_less_rate;
                            // send_sell_details(add_product_sell_response.getSell_id(), productsList.get(i).getProduct_id(),productsList.get(i).getProduct_name(),productsList.get(i).getProduct_image(), productsList.get(i).getTypeList(), productsList.get(i).getAmount(), productsList.get(i).getPrice(), productsList.get(i).getBuy_price(), i);
                            send_sell_details(add_product_sell_response.getSell_id(), productsList.get(i).getProduct_id(), productsList.get(i).getProduct_name(), productsList.get(i).getProduct_image(), productsList.get(i).getTypeList(), productsList.get(i).getAmount(), String.valueOf(priceSell), productsList.get(i).getBuy_price(), i);

                          /*  if (customer_id.equals("0")) {
                                sell_payment_due(add_product_sell_response.getSell_id(), shop_id, customer_id, "non_registered_pay", paidText.getText().toString().trim(), paymentSystem);
                            } else {
                                sell_payment_case(add_product_sell_response.getSell_id(), paymentSystem, paidText.getText().toString().trim());

                                double due_price = total_price - Double.parseDouble(paidText.getText().toString().trim());
                                if (due_price >= 1.0) {
                                    sell_payment_due(add_product_sell_response.getSell_id(), shop_id, customer_id, "due", String.valueOf(due_price), "");
                                }
                            }*/


                        }
                        if (customer_id.equals("0")) {
                            sell_payment_due(add_product_sell_response.getSell_id(), shop_id, customer_id, "non_registered_pay", paidText.getText().toString().trim(), paymentSystem);
                        } else {
                            sell_payment_case(add_product_sell_response.getSell_id(), paymentSystem, paidText.getText().toString().trim());

                            double due_price = total_price - Double.parseDouble(paidText.getText().toString().trim());
                            if (!dueText.getText().toString().trim().isEmpty()) {
                                due_price = Double.parseDouble(dueText.getText().toString().trim());
                            }
                            if (due_price != 0) {
                                sell_payment_due(add_product_sell_response.getSell_id(), shop_id, customer_id, "due", String.valueOf(due_price), "");
                            }
                            //redirect after sell

                            //

                        }
                    } else {
                        Toast.makeText(getActivity(), add_product_sell_response.getSell_id(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(getActivity(), "enter payment amount properly", Toast.LENGTH_SHORT).show();
        }
    }

    public void send_sell_details(String sell_id, String product_id, String product_name, String product_image, List<ProductSel_type> Type, String product_amount, String price, String buy_price, int count) {
        String type = "";
        for (int i = 0; i < Type.size(); i++) {
            if (i < Type.size() - 1)
                type = type + Type.get(i).getType_name() + "(" + Type.get(i).getType_amount() + "),";
            else
                type = type + Type.get(i).getType_name() + "(" + Type.get(i).getType_amount() + ").";
        }
        product_sell.sell_details(sell_id, product_id, product_name, product_image, type, product_amount, price, buy_price).observe(getViewLifecycleOwner(), new Observer<add_sell_details_response>() {
            @Override
            public void onChanged(add_sell_details_response add_sell_details_response) {
                if (add_sell_details_response.getMessage().equals("success")) {
                    update_product(product_id, Type, product_amount, count);
                } else {
                    Toast.makeText(getActivity(), add_sell_details_response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    public void update_product(String product_id, List<ProductSel_type> Type, String product_amount, int count) {

        get_product = new ViewModelProvider(getActivity()).get(Get_product.class);
        get_product.getsingle_product(product_id).observe(getViewLifecycleOwner(), new Observer<Get_product_response>() {
            @Override
            public void onChanged(Get_product_response get_product_response) {
                Double temp_stock = Double.parseDouble(get_product_response.getStock_amount()) - Double.parseDouble(product_amount);
                update_product_stock(product_id, String.valueOf(temp_stock), Type, count);
                //Log.d("stock available: ", String.valueOf(temp_stock));
            }
        });

    }

    public void update_product_stock(String product_id, String stock, List<ProductSel_type> Type, int count) {
        // Toast.makeText(getActivity(),product_id,Toast.LENGTH_SHORT).show();
        product_sell.stock_update(stock, product_id).observe(getViewLifecycleOwner(), new Observer<update_product_stock_by_sell_response>() {
            @Override
            public void onChanged(update_product_stock_by_sell_response update_product_stock_by_sell_response) {

                if (Type.size() > 0) {
                    for (int j = 0; j < Type.size(); j++) {
                        get_update_type(Type.get(j).getType_id(), Type.get(j).getType_amount(), count, j, Type.size());
                    }
                } else {
                    if (count == (productsList.size() - 1)) {
                        // show success alert
                        notifyUser();
                        //Toast.makeText(getActivity(), update_product_stock_by_sell_response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public void get_update_type(String type_id, String type_amount, int count_step, int count_type_step, int type_length) {
        get_product_type = new ViewModelProvider(getActivity()).get(Get_product_type.class);


        get_product_type.getCount(type_id).observe(getViewLifecycleOwner(), new Observer<get_count_for_type_response>() {
            @Override
            public void onChanged(get_count_for_type_response get_count_for_type_response) {

                Double count = Double.parseDouble(get_count_for_type_response.getCount()) - Double.parseDouble(type_amount);
                update_type_count(type_id, String.valueOf(count), count_step, count_type_step, type_length);
            }
        });
    }

    public void update_type_count(String type_id, String count, int count_step, int count_type_step, int type_length) {
        product_sell.type_update(count, type_id).observe(getViewLifecycleOwner(), new Observer<update_product_type_by_sell_response>() {
            @Override
            public void onChanged(update_product_type_by_sell_response update_product_type_by_sell_response) {
                if (count_step == (productsList.size() - 1) && count_type_step == type_length - 1) {
                    // show_success alert
                    notifyUser();                    //Toast.makeText(getActivity(), update_product_type_by_sell_response.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void success_alert_funtion() {
        Dialog successAlert;
        successAlert = new Dialog(getActivity());
        successAlert.setContentView(R.layout.sell_success_alert);
        successAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        successAlert.show();
        successAlert.setCancelable(false);
        Window window = successAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        TextView shopNameText = (TextView) successAlert.findViewById(R.id.nameText);
        TextView customerName = (TextView) successAlert.findViewById(R.id.customerNameID);
        TextView customerPhone = (TextView) successAlert.findViewById(R.id.customerPhoneID);
        TextView subtotal = (TextView) successAlert.findViewById(R.id.subtotalPriceID);
        TextView specialdiscount = (TextView) successAlert.findViewById(R.id.specialdiscountID);
        TextView discount = (TextView) successAlert.findViewById(R.id.discountID);
        TextView totalPrice = (TextView) successAlert.findViewById(R.id.totalPriceID);
        TextView paidprice = (TextView) successAlert.findViewById(R.id.paidID);
        TextView dueprice = (TextView) successAlert.findViewById(R.id.dueID);
        TextView paymentMethodText = (TextView) successAlert.findViewById(R.id.paymentMethodID);
        TextView dateText = (TextView) successAlert.findViewById(R.id.dateTextID);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        LinearLayout savePDFButton = (LinearLayout) successAlert.findViewById(R.id.savePDFButtonID);
        LinearLayout mainLayout = (LinearLayout) successAlert.findViewById(R.id.mainLayoutID);
        shop_profile = new ViewModelProvider(getActivity()).get(Shop_profile.class);
        shop_profile.getData(String.valueOf(shop_id)).observe(getViewLifecycleOwner(), new Observer<shop_profile_response>() {
            @Override
            public void onChanged(shop_profile_response shop_profile_response) {
                shopNameText.setText(shop_profile_response.getStore01e_name());
            }
        });

        dateText.setText(currentDate);
        customerName.setText(customer_name);
        customerPhone.setText(customer_phone);
        subtotal.setText(price.getText().toString().trim());
        discount.setText(offer_discount.getText().toString().trim());
        specialdiscount.setText(priceLessText.getText().toString().trim());
        paidprice.setText(paidText.getText().toString().trim());
        double due_price = total_price - Double.parseDouble(paidText.getText().toString().trim());
        dueprice.setText(String.valueOf(new DecimalFormat("##.##").format(due_price)));
        paymentMethodText.setText(paymentSystem);

        totalPrice.setText(String.valueOf(total_price));

        RecyclerView productsView = (RecyclerView) successAlert.findViewById(R.id.productsViewID);

        productsView.setHasFixedSize(true);
        productsView.setLayoutManager(new LinearLayoutManager(getContext()));
        success_Adapter = new Sell_success_adapter(productsList);
        productsView.setAdapter(success_Adapter);

        savePDFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmapPDF = LoadBitmap(mainLayout, mainLayout.getWidth(), mainLayout.getHeight());

                createPDF();
            }
        });
        AppCompatButton goToHomeButton = (AppCompatButton) successAlert.findViewById(R.id.goToHomeButtonID);
        goToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successAlert.dismiss();
                List<ProductSell> productSellList = new ArrayList<>();
                fragmentManager.beginTransaction().replace(R.id.frame_container, new Shop_sellcategoriesORproducts_fragment(shop_id, productSellList)).commit();


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

    public void sell_payment_case(String sell_id, String payment_method, String payment_amount) {
        product_sell_payment.get_cash(sell_id, payment_method, payment_amount, showDate.getText().toString().trim()).observe(getViewLifecycleOwner(), new Observer<add_sell_payment_cash_response>() {
            @Override
            public void onChanged(add_sell_payment_cash_response add_sell_payment_cash_response) {

            }
        });
    }

    public void sell_payment_due(String sell_id, String shop_id, String customer_id, String transaction_type, String payment_amount, String payment_method) {
        product_sell_payment.get_transaction(sell_id, shop_id, customer_id, customer_phone, transaction_type, payment_amount, payment_method, showDate.getText().toString().trim()).observe(getViewLifecycleOwner(), new Observer<add_payment_transaction_response>() {
            @Override
            public void onChanged(add_payment_transaction_response add_sell_payment_due_response) {

            }
        });
    }


    private void add_more_product_function() {
        type_id = "0";
        type_name = "";
        offer_id = "0";

        types = new ArrayList<>();
        get_product();
        // get_type();

        // product_amount.setText("");
        get_type();

        get_product();
        select_offer_type();

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
                Double select_product_amount = 0.0;
                if (!TextUtils.isEmpty(sell_price.getText().toString().trim()) && !TextUtils.isEmpty(product_amount.getText().toString().trim())) {
                    for (int i = 0; i < productsList.size(); i++) {
                        if (productID.equals(productsList.get(i).getProduct_id())) {
                            select_product_amount += Double.parseDouble(productsList.get(i).getAmount());
                        }
                    }
                    get_product();
                    select_product_amount += Double.parseDouble(product_amount.getText().toString().trim());
                    if (Double.parseDouble(stock_amount.getText().toString().trim()) < select_product_amount) {
                        Toast.makeText(getActivity(), "Total select amount must less than stock", Toast.LENGTH_SHORT).show();

                    } else {
                        if (types.size() > 0) {
                            ProductSell productSell = new ProductSell();
                            //   productSell.setOffer_type(offer_type);
                            productSell.setProduct_id(productID);
                            productSell.setProduct_name(productNameText.getText().toString().trim());
                            productSell.setProduct_image(productImage);
                            productSell.setType_id(type_id);
                            productSell.setType_name(type_name);
                            // productSell.setOffer_id(offer_id);
                            productSell.setAmount(String.valueOf(Double.parseDouble(product_amount.getText().toString().trim())));
                            productSell.setPrice(sell_price.getText().toString().trim());
                            productSell.setUnit_price(String.valueOf(Double.parseDouble(productPrice)));
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
                                Product_sell_offer product_sell_offer = new Product_sell_offer(offer_type, minimum_offer_amount, minimum_offer_pricee, offer_percentage);
                                // productSell.setSell_offer(product_sell_offer);
                                productsList.add(productSell);
                                addAmountAlert.dismiss();
                                main();
                            }


                        } else {
                            ProductSell productSell = new ProductSell();
                            // productSell.setOffer_type(offer_type);
                            productSell.setProduct_id(productID);
                            productSell.setProduct_name(productNameText.getText().toString().trim());
                            productSell.setProduct_image(productImage);
                            productSell.setType_id(type_id);
                            productSell.setType_name(type_name);
                            // productSell.setOffer_id(offer_id);
                            productSell.setAmount(String.valueOf(Double.parseDouble(product_amount.getText().toString().trim())));
                            productSell.setPrice(sell_price.getText().toString().trim());
                            productSell.setUnit_price(String.valueOf(Double.parseDouble(productPrice)));
                            productSell.setBuy_price(String.valueOf(Double.parseDouble(product_amount.getText().toString().trim()) * Double.parseDouble(product_buy_price)));
                            List<ProductSel_type> productSel_type = new ArrayList<>();
                            productSell.setTypeList(productSel_type);
                            Product_sell_offer product_sell_offer = new Product_sell_offer(offer_type, minimum_offer_amount, minimum_offer_pricee, offer_percentage);
                            //productSell.setSell_offer(product_sell_offer);
                            productsList.add(productSell);
                            addAmountAlert.dismiss();
                            main();

                        }
                    }


                } else {
                    Toast.makeText(getActivity(), "select product properly", Toast.LENGTH_SHORT).show();
                }

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
                    if (Double.parseDouble(offers.get(i).getAmount()) > Double.parseDouble(stock_amount.getText().toString().trim())) {
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
                        product_offers[i + 1] = offers.get(i).getAmount() + productUnit + "+     " + offers.get(i).getPrice() + "%";
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
                        // Double price_value = Double.parseDouble(offers.get(i).getAmount()) * (Double.parseDouble(productPrice) - (Double.parseDouble(productPrice) * (Double.parseDouble(offers.get(i).getPrice()) / 100)));
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

    private void show_offer_spinner() {
        offerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    if (!product_amount.getText().toString().isEmpty()) {

                        if (Double.parseDouble(product_amount.getText().toString().trim()) >= Double.parseDouble(offers.get(position - 1).getAmount())) {

                            //amountlayout.setVisibility(View.GONE);
                            //product_amount.setText(offers.get(position - 1).getAmount());
                            offer_id = offers.get(position - 1).getId();
                            offer_check = 1;

                            Double percentage = Double.parseDouble(product_offers_price[position]);
                            Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * (Double.parseDouble(productPrice) - (Double.parseDouble(productPrice) * (percentage / 100)));
                            sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                            offer_type = "individual";
                            minimum_offer_amount = offers.get(position - 1).getAmount();
                            minimum_offer_pricee = "0";
                            offer_percentage = product_offers_price[position];
                            //get_type();
                        } else {
                            Toast.makeText(getActivity(), "number of selected product is short of offer", Toast.LENGTH_SHORT).show();
                            offerSpinner.setSelection(0);
                            Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * (Double.parseDouble(productPrice));
                            sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                            offer_type = "none";
                            minimum_offer_amount = "0";
                            minimum_offer_pricee = "0";
                            offer_percentage = "0";
                        }
                    }
                } else {
                    // price.setText("");
                    //product_amount.setText("");
                    offer_id = "0";
                    offer_check = 0;
                    offer_type = "none";
                    minimum_offer_amount = "0";
                    minimum_offer_pricee = "0";
                    offer_percentage = "0";
                    if (product_amount.getText().toString().trim().isEmpty()) {
                        sell_price.setText("0.0");
                    } else {
                        Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * (Double.parseDouble(productPrice));
                        sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                    }
                    //typeLayout.setVisibility(View.INVISIBLE);
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
                        if (Double.parseDouble(product_amount.getText().toString().trim()) >= Double.parseDouble(offer_all.get(position - 1).getMinimum_amount())) {

                            // amountlayout.setVisibility(View.GONE);
                            // product_amount.setText(offers.get(position - 1).getAmount());
                            offer_id = offer_all.get(position - 1).getId();
                            offer_check = 1;

                            //  Double price_value = Double.parseDouble(offers.get(position - 1).getAmount()) * (Double.parseDouble(productPrice_with_discount) - (Double.parseDouble(productPrice_with_discount) * (Double.parseDouble(offers.get(position - 1).getPrice()) / 100)));
                            //  price.setText(String.valueOf(Double.parseDouble(offers.get(position - 1).getAmount()) * (Double.parseDouble(productPrice) - (Double.parseDouble(productPrice) * (Double.parseDouble(offers.get(position - 1).getPrice()) / 100)))));
                            // price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                            Double percentage = Double.parseDouble(product_offers_price[position]);
                            Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * (Double.parseDouble(productPrice) - (Double.parseDouble(productPrice) * (percentage / 100)));
                            sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                            offer_type = "whole";
                            minimum_offer_amount = offer_all.get(position - 1).getMinimum_amount();
                            minimum_offer_pricee = offer_all.get(position - 1).getMinimum_price();
                            offer_percentage = product_offers_price[position];
                            //  get_type();
                        } else {
                            Toast.makeText(getActivity(), "number of selected product is short of offer", Toast.LENGTH_SHORT).show();
                            offerSecondSpinner.setSelection(0);
                            Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * (Double.parseDouble(productPrice));
                            sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
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
                        sell_price.setText("0.0");
                    } else {
                        Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * (Double.parseDouble(productPrice));
                        sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                    }
                    // typeLayout.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                    type_adapter.setOnClickListener(Shop_sell_selected_product_list_fragment.this::OnTypeClick, Shop_sell_selected_product_list_fragment.this::OnTypeInc, Shop_sell_selected_product_list_fragment.this::OnTypeDec);
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
                    type_check = 0;
                    typeLayout.setVisibility(View.GONE);
                    decButton.setVisibility(View.VISIBLE);
                    incButton.setVisibility(View.VISIBLE);
                    product_amount.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            get_product();
                            if (product_amount.getText().toString().isEmpty()) {
                                typeLayout.setVisibility(View.GONE);
                                sell_price.setText("");
                            } else {
                                if (Double.parseDouble(product_amount.getText().toString().trim()) > Double.parseDouble(stock_amount.getText().toString().trim())) {
                                    typeLayout.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "amount must less than stock", Toast.LENGTH_SHORT).show();
                                    product_amount.setText("");
                                    sell_price.setText("");
                                } else {
                                    Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * Double.parseDouble(productPrice);
                                    sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                                    //get_type();
                                    get_offer();
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


    private void get_product() {
        get_product = new ViewModelProvider(getActivity()).get(Get_product.class);
        get_product.getsingle_product(productID).observe(getViewLifecycleOwner(), new Observer<Get_product_response>() {
            @Override
            public void onChanged(Get_product_response get_product_response) {
                productNameText.setText(get_product_response.getProduct_name());
                productImage = get_product_response.getProduct_image();
                productUnitText.setText(get_product_response.getProduct_unit());
                productUnit = get_product_response.getProduct_unit();

                stock_amount.setText(get_product_response.getStock_amount());
                double selling_price = Double.parseDouble(get_product_response.getSelling_price());
                double discount = Double.parseDouble(get_product_response.getProduct_offer());
                if (Double.parseDouble(allDiscountText.getText().toString().trim()) > discount) {
                    discount = Double.parseDouble(allDiscountText.getText().toString().trim());
                }
                double price_with_offer = selling_price - selling_price * (discount / 100);
                productPrice = String.valueOf(price_with_offer);
                product_buy_price = get_product_response.getBuy_price();
                Toast.makeText(getActivity(), productID, Toast.LENGTH_SHORT).show();

            }
        });
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
                        Double total_selected_type = 0.0;
                        for (int i = 0; i < productsList.size(); i++) {
                            if (productID.equals(productsList.get(i).getProduct_id())) {
                                //Log.d("position:", productSel_types.get(i).getType_name());
                                for (int j = 0; j < productsList.get(i).getTypeList().size(); j++) {
                                    if (productsList.get(i).getTypeList().get(j).getType_id().equals(type.getId())) {
                                        total_selected_type += Double.parseDouble(productsList.get(i).getTypeList().get(j).getType_amount());

                                    }
                                }
                            }
                        }
                        total_selected_type += Double.parseDouble(amount);
                        if (total_selected_type > Double.parseDouble(type.getCount())) {
                            amountError.setError("Total selected type amount overflow stock ");
                        } else {
                            selected_type.setType_amount(amount);
                            type_adapter.notifyDataSetChanged();
                            setAmountAlert.dismiss();
                        }
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
    public void increament(int position) {
        get_product_type_response type = types.get(position);

        String unit_price = productsList.get(product_sell_cart_position).getUnit_price();
        double all_type_amount = 0.0;
        for (int i = 0; i < productsList.size(); i++) {
            for (int j = 0; j < productsList.get(i).getTypeList().size(); j++) {
                if (type.getId().equals(productsList.get(i).getTypeList().get(j).getType_id())) {
                    all_type_amount += Double.parseDouble(productsList.get(i).getTypeList().get(j).getType_amount());
                }
            }
        }
        if (Double.parseDouble(type.getCount()) == all_type_amount) {
            Toast.makeText(getActivity(), "Amount Overflow", Toast.LENGTH_SHORT).show();
            inc_dec_dialog.dismiss();
        } else {
            Double temp_price = 0.0;
            int inc_dec_check = 0;
            for (int i = 0; i < productsList.get(product_sell_cart_position).getTypeList().size(); i++) {
                if (type.getId().equals(productsList.get(product_sell_cart_position).getTypeList().get(i).getType_id())) {
                    Double amount = Double.parseDouble(productsList.get(product_sell_cart_position).getAmount());
                    Double type_amount = Double.parseDouble(productsList.get(product_sell_cart_position).getTypeList().get(i).getType_amount());
                    Double amount_price = Double.parseDouble(productsList.get(product_sell_cart_position).getPrice());
                    amount = amount + 1;
                    type_amount = type_amount + 1;
                   /* if (productsList.get(product_sell_cart_position).getSell_offer().getOffer_type().equals("individual")) {
                        if (amount < Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_minimum_amount())) {
                            temp_price = Double.parseDouble(unit_price);
                        } else {
                            temp_price = Double.parseDouble(unit_price) - Double.parseDouble(unit_price) * (Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_percentage()) / 100);
                        }
                    } else if (productsList.get(product_sell_cart_position).getSell_offer().getOffer_type().equals("whole")) {
                        if ((amount >= Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_minimum_amount())) && ((amount * Double.parseDouble(unit_price)) >= Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_minimum_price()))) {
                            temp_price = Double.parseDouble(unit_price) - Double.parseDouble(unit_price) * (Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_percentage()) / 100);

                        } else {
                            temp_price = Double.parseDouble(unit_price);
                        }
                    } else {
                        temp_price = Double.parseDouble(unit_price);
                    }*/
                    amount_price = amount * temp_price;
                    productsList.get(product_sell_cart_position).setAmount(String.valueOf(amount));
                    productsList.get(product_sell_cart_position).setPrice(String.valueOf(amount_price));
                    productsList.get(product_sell_cart_position).getTypeList().get(i).setType_amount(String.valueOf(type_amount));
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
                productsList.get(product_sell_cart_position).getTypeList().add(new_type);
                Double amount = Double.parseDouble(productsList.get(product_sell_cart_position).getAmount());
                amount += 1;
                Double amount_price = Double.parseDouble(productsList.get(product_sell_cart_position).getPrice());
                /*if (productsList.get(product_sell_cart_position).getSell_offer().getOffer_type().equals("individual")) {
                    if (amount < Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_minimum_amount())) {
                        temp_price = Double.parseDouble(unit_price);
                    } else {
                        temp_price = Double.parseDouble(unit_price) - Double.parseDouble(unit_price) * (Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_percentage()) / 100);
                    }
                } else if (productsList.get(product_sell_cart_position).getSell_offer().getOffer_type().equals("whole")) {
                    if ((amount >= Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_minimum_amount())) && ((amount * Double.parseDouble(unit_price)) >= Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_minimum_price()))) {
                        temp_price = Double.parseDouble(unit_price) - Double.parseDouble(unit_price) * (Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_percentage()) / 100);

                    } else {
                        temp_price = Double.parseDouble(unit_price);
                    }
                } else {
                    temp_price = Double.parseDouble(unit_price);
                }*/
                amount_price = amount * temp_price;
                //amount_price += Double.parseDouble(unit_price);
                productsList.get(product_sell_cart_position).setAmount(String.valueOf(amount));
                productsList.get(product_sell_cart_position).setPrice(String.valueOf(amount_price));
                inc_dec_dialog.dismiss();
                amount_inc_dec_adapter.notifyDataSetChanged();
            }
            adapter.notifyDataSetChanged();
            double total_price = 0.0;
            for (int i = 0; i < productsList.size(); i++) {
                total_price += Double.parseDouble(productsList.get(i).getPrice());
            }
            // price.setText(String.valueOf(total_price));
            price.setText(String.valueOf(new DecimalFormat("##.##").format(total_price)));
            offer_discount.setText("0");
            finalPrice.setText(String.valueOf(new DecimalFormat("##.##").format(total_price)));


        }


    }

    @Override
    public void decreament(int position) {

        String selected_type_id = productsList.get(product_sell_cart_position).getTypeList().get(position).getType_id();
        Double selected_type_amount = Double.parseDouble(productsList.get(product_sell_cart_position).getTypeList().get(position).getType_amount());
        String unit_price = productsList.get(product_sell_cart_position).getUnit_price();
        Double amount = Double.parseDouble(productsList.get(product_sell_cart_position).getAmount());
        Double amount_price = Double.parseDouble(productsList.get(product_sell_cart_position).getPrice());
        amount -= 1;
        Double temp_price = 0.0;
       /* if (productsList.get(product_sell_cart_position).getSell_offer().getOffer_type().equals("individual")) {
            if (amount < Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_minimum_amount())) {
                temp_price = Double.parseDouble(unit_price);
            } else {
                temp_price = Double.parseDouble(unit_price) - Double.parseDouble(unit_price) * (Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_percentage()) / 100);
            }
        } else if (productsList.get(product_sell_cart_position).getSell_offer().getOffer_type().equals("whole")) {
            if ((amount >= Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_minimum_amount())) && ((amount * Double.parseDouble(unit_price)) >= Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_minimum_price()))) {
                temp_price = Double.parseDouble(unit_price) - Double.parseDouble(unit_price) * (Double.parseDouble(productsList.get(product_sell_cart_position).getSell_offer().getOffer_percentage()) / 100);

            } else {
                temp_price = Double.parseDouble(unit_price);
            }
        } else {
            temp_price = Double.parseDouble(unit_price);
        }*/

        amount_price = amount * temp_price;
        if (selected_type_amount - 1.0 == 0.0) {
            productsList.get(product_sell_cart_position).setAmount(String.valueOf(amount));
            productsList.get(product_sell_cart_position).setPrice(String.valueOf(amount_price));
            productsList.get(product_sell_cart_position).getTypeList().remove(position);
            amount_inc_dec_adapter.notifyDataSetChanged();
            inc_dec_dialog.dismiss();


        } else {
            selected_type_amount = selected_type_amount - 1;
            productsList.get(product_sell_cart_position).setAmount(String.valueOf(amount));
            productsList.get(product_sell_cart_position).setPrice(String.valueOf(amount_price));
            productsList.get(product_sell_cart_position).getTypeList().get(position).setType_amount(String.valueOf(selected_type_amount));
            amount_inc_dec_adapter.notifyDataSetChanged();
            inc_dec_dialog.dismiss();

        }
        adapter.notifyDataSetChanged();
        double total_price = 0.0;
        for (int i = 0; i < productsList.size(); i++) {
            total_price += Double.parseDouble(productsList.get(i).getPrice());
        }
        // price.setText(String.valueOf(total_price));
        price.setText(String.valueOf(new DecimalFormat("##.##").format(total_price)));
        offer_discount.setText("0");
        finalPrice.setText(String.valueOf(new DecimalFormat("##.##").format(total_price)));
    }

    @Override
    public void OnTypeInc(int position) {
        get_product_type_response type = types.get(position);
        ProductSel_type selected_type = productSel_types.get(position);

        if (Double.parseDouble(selected_type.getType_amount()) == Double.parseDouble(type.getCount())) {

            Toast.makeText(getActivity(), "amount overflow", Toast.LENGTH_SHORT).show();
        } else {
            Double total_selected_type = Double.parseDouble(selected_type.getType_amount());
            for (int i = 0; i < productsList.size(); i++) {
                if (productID.equals(productsList.get(i).getProduct_id())) {
                    // Log.d("position:", productSel_types.get(i).getType_name());
                    for (int j = 0; j < productsList.get(i).getTypeList().size(); j++) {
                        if (productsList.get(i).getTypeList().get(j).getType_id().equals(type.getId())) {
                            total_selected_type += Double.parseDouble(productsList.get(i).getTypeList().get(j).getType_amount());

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
                Double price_value = Double.parseDouble(product_amount.getText().toString().trim()) * Double.parseDouble(productPrice);
                sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                // setAmountAlert.dismiss();

                if (x == 1) {
                    get_offer();
                } else if (x == 2) {
                    get_all_product_offer();
                }
            }
        }
        //do code
    }


    @Override
    public void OnTypeDec(int position) {
        get_product_type_response type = types.get(position);
        ProductSel_type selected_type = productSel_types.get(position);

        if (Double.parseDouble(selected_type.getType_amount()) == 0.0) {

            Toast.makeText(getActivity(), "amount underflow", Toast.LENGTH_SHORT).show();
        } else {
            Double total_selected_type = Double.parseDouble(selected_type.getType_amount());
            /*for (int i = 0; i < productsList.size(); i++) {
                if (productID.equals(productsList.get(i).getProduct_id())) {
                    Log.d("position:", productSel_types.get(i).getType_name());
                    for (int j = 0; j < productsList.get(i).getTypeList().size(); j++) {
                        if (productsList.get(i).getTypeList().get(j).getType_id().equals(type.getId())) {
                            total_selected_type += Double.parseDouble(productsList.get(i).getTypeList().get(j).getType_amount());

                        }
                    }
                }
            }*/
            total_selected_type -= 1.0;
            if (total_selected_type == 0.0) {
                //  amountError.setError("Total selected type amount overflow stock ");
                Toast.makeText(getActivity(), "Total selected type amount overflow stock ", Toast.LENGTH_SHORT).show();
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
                sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(price_value)));
                if (x == 1) {
                    get_offer();
                } else if (x == 2) {
                    get_all_product_offer();
                }
                // setAmountAlert.dismiss();
            }
        }
    }

    //product_offer_type_selection part
    public void select_offer_type() {
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
    }

    //end
    public void notifyUser() {


        double due_price = total_price - Double.parseDouble(paidText.getText().toString().trim());

        push_notification = new ViewModelProvider(getActivity()).get(Push_notification.class);
        push_notification.sell_notification_shop(shop_id, customer_name, String.valueOf(new DecimalFormat("##.##").format(total_price)), String.valueOf(new DecimalFormat("##.##").format(due_price))).observe(getViewLifecycleOwner(), new Observer<push_notification_response>() {
            @Override
            public void onChanged(push_notification_response push_notification_response) {
                if (push_notification_response.getMessage().equals("success")) {
                    List<ProductSell> productSellList = new ArrayList<>();
                    //I = 0;
                    if (customer_id.equals("0")) {
                        loader.dismiss();
                        success_alert_funtion();
                        /*OTP otp = new ViewModelProvider(getActivity()).get(OTP.class);
                        AddMessagetoHistory addMessagetoHistory = new ViewModelProvider(getActivity()).get(AddMessagetoHistory.class);
                        String due_status, due;
                        if (due_price >= 0.0) {
                            due_status = "Due: ";
                            due = String.valueOf(new DecimalFormat("##.##").format(due_price));

                        } else {
                            due_status = "Deposit: ";
                            due = String.valueOf(new DecimalFormat("##.##").format(-1 * due_price));
                        }

                        String message = "ALife...Sell_price: " + String.valueOf(new DecimalFormat("##.##").format(total_price)) + "," + due_status + due + ".";
                        String messagetocustomer = "ALife...Buy from: " + Shop_main_activity.SHOP_NAME + ", Total price: " + String.valueOf(new DecimalFormat("##.##").format(total_price)) + "," + due_status + due + ",Shop contact: " + Shop_main_activity.SHOP_NUMBER + ".";
                        String messagetoshop = "ALife...Sell to: " + customer_name + ", Total price: " + String.valueOf(new DecimalFormat("##.##").format(total_price)) + "," + due_status + due + ",Customer contact: " + customer_phone + ".";
                        String shop_name = Shop_main_activity.SHOP_NAME;
                        String shop_contact = Shop_main_activity.SHOP_NUMBER;
                        otp.getStatus(customer_phone, messagetocustomer).observe(getViewLifecycleOwner(), new Observer<OTP_response>() {
                            @Override
                            public void onChanged(OTP_response otp_response) {
                                otp.getStatus(Shop_main_activity.SHOP_NUMBER, messagetoshop).observe(getViewLifecycleOwner(), new Observer<OTP_response>() {
                                    @Override
                                    public void onChanged(OTP_response otp_response) {
                                        addMessagetoHistory.getResponse(shop_id, shop_name, shop_contact, customer_id, customer_name, customer_phone, message).observe(getViewLifecycleOwner(), new Observer<addMessageResponse>() {
                                            @Override
                                            public void onChanged(addMessageResponse addMessageResponse) {
                                                loader.dismiss();
                                                success_alert_funtion();
                                            }

                                        });

                                    }
                                });
                            }
                        });*/
                    } else {
                        push_notification.sell_notification_customer(shop_id, customer_id, String.valueOf(new DecimalFormat("##.##").format(total_price)), String.valueOf(new DecimalFormat("##.##").format(due_price))).observe(getViewLifecycleOwner(), new Observer<com.alifew.alife.model.push_notification_response>() {
                            @Override
                            public void onChanged(com.alifew.alife.model.push_notification_response push_notification_response) {
                                loader.dismiss();
                                success_alert_funtion();
                                /*OTP otp = new ViewModelProvider(getActivity()).get(OTP.class);
                                AddMessagetoHistory addMessagetoHistory = new ViewModelProvider(getActivity()).get(AddMessagetoHistory.class);
                                String due_status, due;
                                if (due_price >= 0.0) {
                                    due_status = "Due: ";
                                    due = String.valueOf(new DecimalFormat("##.##").format(due_price));

                                } else {
                                    due_status = "Deposit: ";
                                    due = String.valueOf(new DecimalFormat("##.##").format(-1 * due_price));
                                }

                                String message = "ALife...Sell_price: " + String.valueOf(new DecimalFormat("##.##").format(total_price)) + "," + due_status + due + ".";
                                String messagetocustomer = "ALife...Buy from: " + Shop_main_activity.SHOP_NAME + ", Total price: " + String.valueOf(new DecimalFormat("##.##").format(total_price)) + "," + due_status + due + ",Shop contact: " + Shop_main_activity.SHOP_NUMBER + ".";
                                String messagetoshop = "ALife...Sell to: " + customer_name + ", Total price: " + String.valueOf(new DecimalFormat("##.##").format(total_price)) + "," + due_status + due + ",Customer contact: " + customer_phone + ".";
                                String shop_name = Shop_main_activity.SHOP_NAME;
                                String shop_contact = Shop_main_activity.SHOP_NUMBER;
                                otp.getStatus(customer_phone, messagetocustomer).observe(getViewLifecycleOwner(), new Observer<OTP_response>() {
                                    @Override
                                    public void onChanged(OTP_response otp_response) {
                                        otp.getStatus(Shop_main_activity.SHOP_NUMBER, messagetoshop).observe(getViewLifecycleOwner(), new Observer<OTP_response>() {
                                            @Override
                                            public void onChanged(OTP_response otp_response) {
                                                addMessagetoHistory.getResponse(shop_id, shop_name, shop_contact, customer_id, customer_name, customer_phone, message).observe(getViewLifecycleOwner(), new Observer<addMessageResponse>() {
                                                    @Override
                                                    public void onChanged(addMessageResponse addMessageResponse) {
                                                        loader.dismiss();
                                                        success_alert_funtion();
                                                    }

                                                });

                                            }
                                        });
                                    }
                                });*/
                            }
                        });

                    }
                }
            }

        });
    }
    //notify user about sell details


}
