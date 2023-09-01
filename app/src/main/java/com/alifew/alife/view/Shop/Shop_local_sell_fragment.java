package com.alifew.alife.view.Shop;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.DB.AppDatabase;
import com.alifew.alife.DB.dao.CustomerDao;
import com.alifew.alife.DB.dao.LocalSellProductsDao;
import com.alifew.alife.DB.entity.Customer;
import com.alifew.alife.DB.entity.LocalSellProducts;
import com.alifew.alife.R;
import com.alifew.alife.Utils.Helpers;
import com.alifew.alife.Utils.ImageHelper;
import com.alifew.alife.adapter.Shop_local_sell_image_list_adapter;
import com.alifew.alife.adapter.Shop_local_sell_select_customer_adapter;
import com.alifew.alife.adapter.Shop_local_sell_select_product_adapter;
import com.alifew.alife.adapter.localsell.CustomerAdapter;
import com.alifew.alife.adapter.localsell.ProductAdapter;
import com.alifew.alife.model.Get_shop_customer_response;
import com.alifew.alife.model.add_payment_transaction_response;
import com.alifew.alife.model.add_product_sell_response;
import com.alifew.alife.model.add_sell_payment_cash_response;
import com.alifew.alife.model.add_shop_due_customer_response;
import com.alifew.alife.model.customer_exist_check_response;
import com.alifew.alife.model.local_sell.add_local_sell_details_response;
import com.alifew.alife.model.local_sell.add_local_sell_image_response;
import com.alifew.alife.model.local_sell.Get_local_sell_product_response;
import com.alifew.alife.model.points.Shop_local_sell_point_response;
import com.alifew.alife.model.push_notification_response;
import com.alifew.alife.model.shop_due_customer_response;
import com.alifew.alife.model.shop_profile_response;
import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.viewmodel.Customer_exist_check;
import com.alifew.alife.viewmodel.Customer_registration;
import com.alifew.alife.viewmodel.Local_sell.Add_local_sell;
import com.alifew.alife.viewmodel.Local_sell.Get_local_sell;
import com.alifew.alife.viewmodel.Product_sell;
import com.alifew.alife.viewmodel.Product_sell_payment;
import com.alifew.alife.viewmodel.Push_notification;
import com.alifew.alife.viewmodel.ShopCustomerViewModel;
import com.alifew.alife.viewmodel.ShopLocalSellPointsViewModel;
import com.alifew.alife.viewmodel.Shop_profile;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class Shop_local_sell_fragment extends Fragment implements Shop_local_sell_select_product_adapter.OnItemClickListener {

    String shopID;

    public TextInputEditText productPriceText, paidPriceText, buyPriceText;
    TextInputLayout paidPriceError, phoneError;
    EditText productDetailsText;
    TextView choseImageButton, select_product, select_phone, profitText, duePriceText, phoneText;
    public TextView nameText;
    LinearLayout bar_code_search;
    AppCompatButton sellButton;

    ImageView productImage, closeButton;
    private Uri filepath;
    private Bitmap bitmap;
    Dialog loader;
    ShopCustomerViewModel shop_customer;
    Customer_exist_check customer_exist_check;
    Customer_registration customer_registration;
    Product_sell product_sell;
    Product_sell_payment product_sell_payment;
    Add_local_sell add_local_sell;
    Push_notification push_notification;
    int duecustomerCheck = 0;
    String customer_id, customer_name;
    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    static int check;
    int state;
    String imgdata;
    final int IMAGE_REQUEST_CODE = 999;
    String sell_id, image;
    LinearLayout addProductsButton;

    Get_local_sell get_local_sell;
    FragmentManager fragmentManager;

    private List<Get_local_sell_product_response> searchProductList = new ArrayList<>();

    Shop_profile shop_profile;
    Bitmap bitmapPDF;
    LinearLayout historyButton;
    RecyclerView imageRecyclerView, productView, contactView;
    public List<String> imageList;
    private int loopItem;
    LinearLayout selectImage, listImage;
    Shop_local_sell_image_list_adapter adapter;

    ShopLocalSellPointsViewModel shopLocalSellPointsViewModel;

    TextView pointsCriteriaText, pointsText;
    List<Shop_local_sell_point_response> shopSellPointRulesList = new ArrayList<>();

    Shop_local_sell_select_product_adapter shopLocalSellSelectProductAdapter;
    Dialog productDialog, contactDialog;

    public Double buyPrice = 0.0, productPrice = 0.0, paidPrice = 0.0, sellPoint = 0.0, duePrice = 0.0;
    String productName, phone;
    SessionManagement sessionManagement;
    CustomerDao customerDao;
    List<Customer> customerList = new ArrayList<>();
    private List<LocalSellProducts> productList = new ArrayList<>();
    Shop_local_sell_select_customer_adapter shop_local_sell_select_customer_adapter;

    AutoCompleteTextView customerSearchEditText, productsAutoCompleteText;
    ConstraintLayout rulesLayout;
    ShopCustomerViewModel shopCustomerViewModel;
    LocalSellProductsDao localSellProductsDao;
    AppCompatButton calculateButton;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bundle bundle = data.getExtras();
                bitmap = (Bitmap) bundle.get("data");
                check = 1;
                listImage.setVisibility(View.GONE);
                selectImage.setVisibility(View.VISIBLE);
                productImage.setImageBitmap(bitmap);

            } else if (requestCode == IMAGE_REQUEST_CODE) {
                filepath = data.getData();
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    listImage.setVisibility(View.GONE);
                    selectImage.setVisibility(View.VISIBLE);
                    productImage.setImageBitmap(bitmap);
                    check = 1;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_local_sell_fragment, container, false);

        initView(view);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rulesLayout.setVisibility(View.GONE);
            }
        });

        productDetailsText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    buyPriceText.setText("");
                    paidPriceText.setText("");
                    productPriceText.setText("");
                    sellPoint = 0.0;
                    duePrice = 0.0;
                    setPointText(sellPoint, productPriceText.getText().toString().trim());
                    imageList.clear();
                    setImageAdapter(imageList);

                    profitText.setText("");

                }
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_local_sell_history_fragment(shopID)).addToBackStack(null).commit();
            }
        });

        addProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_local_sell_add_product_fragment(shopID)).addToBackStack(null).commit();
            }
        });
        select_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //downLoadLocalSellProducts();
                selectProducts();
            }
        });
        select_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_local_sell_select_customer_phone_fragment()).addToBackStack(null).commit();*/

                loadContacts();

            }
        });
        bar_code_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Bar_code_fragment(shopID)).addToBackStack(null).commit();

            }
        });

        choseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                imageselect();
            }
        });

        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //productName = productNameText.getText().toString().trim();

                phone = customerSearchEditText.getText().toString().trim();


                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(productPriceText.getText().toString().trim()) || TextUtils.isEmpty(paidPriceText.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "Empty field", Toast.LENGTH_SHORT).show();
                } else {

                    customer_exist_check = new ViewModelProvider(getActivity()).get(Customer_exist_check.class);
                    shop_customer = new ViewModelProvider(getActivity()).get(ShopCustomerViewModel.class);
                    push_notification = new ViewModelProvider(getActivity()).get(Push_notification.class);
                    customer_exist_check.getData(phone).observe(getViewLifecycleOwner(), new Observer<customer_exist_check_response>() {
                        @Override
                        public void onChanged(customer_exist_check_response customer_exist_check_response) {
                            if (!customer_exist_check_response.getId().equals("0")) {
                                customer_id = customer_exist_check_response.getId();
                                customer_name = customer_exist_check_response.getName();
                            } else {
                                customer_id = "0";
                                customer_name = "";
                            }
                            shop_customer.get_due_customer(shopID).observe(getViewLifecycleOwner(), new Observer<List<shop_due_customer_response>>() {
                                @Override
                                public void onChanged(List<shop_due_customer_response> shop_due_customer_responses) {
                                    for (int i = 0; i < shop_due_customer_responses.size(); i++) {
                                        if (phone.equals(shop_due_customer_responses.get(i).getCustomer_phone())) {
                                            duecustomerCheck = 1;
                                            break;
                                        }
                                    }
                                    if (duecustomerCheck == 0) {
                                        customer_registration = new ViewModelProvider(getActivity()).get(Customer_registration.class);
                                        customer_registration.get_add_due_customer_response(shopID, customer_id, phone).observe(getViewLifecycleOwner(), new Observer<add_shop_due_customer_response>() {
                                            @Override
                                            public void onChanged(add_shop_due_customer_response add_shop_due_customer_response) {
                                                if (add_shop_due_customer_response.getMessage().equals("Customer added successfully")) {
                                                    //Toast.makeText(getActivity(), add_shop_due_customer_response.getMessage(), Toast.LENGTH_SHORT).show();

                                                    sell(productDetailsText.getText().toString().trim(), productPriceText.getText().toString().trim(), paidPriceText.getText().toString().trim(), phone);

                                                } else {
                                                    Toast.makeText(getActivity(), "Something Wrong", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else if (duecustomerCheck == 1) {
                                        sell(productDetailsText.getText().toString().trim(), productPriceText.getText().toString().trim(), paidPriceText.getText().toString().trim(), phone);
                                    }
                                }
                            });

                        }
                    });

                    //code
                }
            }
        });

        loadLocalSellPoints();

        loadCustomerList();

        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                customerDao.deleteAllCustomer();
                customerDao.resetPrimaryKeySequence("tblCustomer");
                getAllCustomer();
            }
        });

        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                localSellProductsDao.deleteAllProducts();
                localSellProductsDao.resetPrimaryKeySequence("tblLocalSellProducts");
                downLoadLocalSellProducts();

            }
        });

        loadLocalSellProducts();

        productPriceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    // setUIValue(productsAutoCompleteText.getText().toString().trim(), String.valueOf(buyPrice), buyPriceText.getText().toString());
                }
            }
        });


        buyPriceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    //setUIValue(productsAutoCompleteText.getText().toString().trim(), productPriceText.getText().toString().trim(), buyPriceText.getText().toString());
                }
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setUIValue(productsAutoCompleteText.getText().toString().trim(), productPriceText.getText().toString().trim(), buyPriceText.getText().toString());
                calculateButtonFunc(productPriceText.getText().toString().trim(), paidPriceText.getText().toString().trim(), buyPriceText.getText().toString());
            }
        });

        return view;
    }

    private void calculateButtonFunc(String product_price, String paid_price, String buy_price) {
        productPrice = Double.parseDouble(product_price);
        productPriceText.setText(String.valueOf(productPrice));
        paidPriceText.setText(paid_price);

        buyPrice = Double.parseDouble(buy_price);
        buyPriceText.setText(String.valueOf(buyPrice));

        Double profit = Double.parseDouble(productPriceText.getText().toString().trim()) - Double.parseDouble(buyPriceText.getText().toString().trim());
        profitText.setText(String.valueOf(profit));

        duePrice = Double.parseDouble(productPriceText.getText().toString().trim()) - Double.parseDouble(paidPriceText.getText().toString().trim());

        duePriceText.setText(getString(R.string.due) + ": " + String.valueOf(duePrice));
        check = 2;

        pointsCalculation(productPrice);
    }

    @SuppressLint("SetTextI18n")
    private void initView(View view) {
        calculateButton = view.findViewById(R.id.calculateButton);
        AppDatabase db = AppDatabase.getDatabase(getActivity());
        customerDao = db.customerDao();
        localSellProductsDao = db.localSellProductsDao();
        shopCustomerViewModel = new ViewModelProvider(this).get(ShopCustomerViewModel.class);

        sessionManagement = new SessionManagement(getActivity());
        shopID = String.valueOf(sessionManagement.getSession());
        pointsCriteriaText = view.findViewById(R.id.pointsCriteriaText);
        duePriceText = view.findViewById(R.id.duePriceText);
        get_local_sell = new ViewModelProvider(this).get(Get_local_sell.class);
        //productNameText = view.findViewById(R.id.productNameTextID);
        imageRecyclerView = view.findViewById(R.id.imageRecyclerViewID);
        imageRecyclerView.setHasFixedSize(true);
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        productDetailsText = view.findViewById(R.id.productDetailsTextID);
        productPriceText = view.findViewById(R.id.productPriceTextID);
        paidPriceText = view.findViewById(R.id.paidPriceTextID);
        buyPriceText = view.findViewById(R.id.buyPriceTextID);
        profitText = view.findViewById(R.id.profitTextID);
        phoneText = view.findViewById(R.id.contactText);
        nameText = view.findViewById(R.id.nameText);
        selectImage = view.findViewById(R.id.selectImageId);
        listImage = view.findViewById(R.id.listImageId);

        historyButton = view.findViewById(R.id.historyButtonID);

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        //productPriceError = view.findViewById(R.id.productPriceErrorID);
        paidPriceError = view.findViewById(R.id.paidPriceErrorID);
        phoneError = view.findViewById(R.id.phoneErrorID);

        choseImageButton = view.findViewById(R.id.choseImageButtonId);
        bar_code_search = view.findViewById(R.id.barcodesearchId);
        sellButton = view.findViewById(R.id.sellButtonID);
        select_product = view.findViewById(R.id.selectProductsButtonID);
        select_phone = view.findViewById(R.id.selectPhoneButtonID);
        productImage = view.findViewById(R.id.productImage);

        addProductsButton = view.findViewById(R.id.addProductsButtonID);
        fragmentManager = getFragmentManager();

        shopLocalSellPointsViewModel = new ViewModelProvider(getActivity()).get(ShopLocalSellPointsViewModel.class);
        closeButton = view.findViewById(R.id.closeButton);
        imageList = new ArrayList<>();

        productDialog = new Dialog(getActivity());
        productDialog.setContentView(R.layout.local_sell_product_alert);
        productDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        productDialog.setCancelable(false);


        contactDialog = new Dialog(getActivity());
        contactDialog.setContentView(R.layout.local_sell_phone_alert);
        contactDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        contactDialog.setCancelable(false);

        contactView = contactDialog.findViewById(R.id.contactView);
        contactView.setHasFixedSize(true);
        contactView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pointsText = view.findViewById(R.id.pointsText);

        duePriceText.setText(getString(R.string.due) + ": " + String.valueOf(0.0));

        productView = productDialog.findViewById(R.id.productView);
        productView.setHasFixedSize(true);
        productView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        customerSearchEditText = view.findViewById(R.id.customerSearchEditText);
        productsAutoCompleteText = view.findViewById(R.id.productsAutoCompleteText);
        closeButton = view.findViewById(R.id.closeButton);
        rulesLayout = view.findViewById(R.id.rulesLayout);


    }


    public void selectProducts() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                productList = localSellProductsDao.getLocalSellProducts("");
            }
        });
        if (!productList.isEmpty()) {

            productDialog.show();
            Window window = productDialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);

            ImageView closeButton = productDialog.findViewById(R.id.closeButton);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productDialog.dismiss();
                }
            });


            setProductAdapter(productList);

            EditText searchEditText = productDialog.findViewById(R.id.searchEditText);
            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
//                    if (editable.toString().trim().isEmpty()) {
//                        setProductAdapter(productList);
//                        productSearched = false;
//                    } else {
//                        productSearched = true;
//                        searchProductList.clear();
//                        HashSet<Get_local_sell_product_response> searchSet = new HashSet<>();
//                        for (int i = 0; i < productList.size(); i++) {
//                            if (productList.get(i).getProduct_details().toLowerCase(Locale.ROOT).contains(editable.toString().trim().toLowerCase())) {
//                                searchSet.add(productList.get(i));
//                            }
//                        }
//
//                        searchProductList.addAll(searchSet);
//                        setProductAdapter(searchProductList);
//
//                    }
                    productList = localSellProductsDao.getLocalSellProducts(editable.toString().trim());
                    setProductAdapter(productList);
                }
            });


        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_product_found_for_local_sell), Toast.LENGTH_SHORT).show();
        }
    }


    private void loadLocalSellProducts() {
        productsAutoCompleteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().isEmpty()) {
                    productList = localSellProductsDao.getLocalSellProducts(editable.toString().trim());
                    ProductAdapter productAdapter = new ProductAdapter(getActivity(), productList, Shop_local_sell_fragment.this);
                    productsAutoCompleteText.setAdapter(productAdapter);
                }
            }
        });

        productsAutoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Helpers.hideSoftKeyboard(getActivity());


                productPriceText.setText(String.valueOf(productPrice));
                buyPriceText.setText(String.valueOf(buyPrice));

                setUIValue(
                        productsAutoCompleteText.getText().toString().trim(),
                        productPriceText.getText().toString().trim(),
                        buyPriceText.getText().toString().trim()
                );

//                imageList.add(product.getImage());
                selectImage.setVisibility(View.GONE);
                listImage.setVisibility(View.VISIBLE);
                //    setUIValue(product.getProduct_details(), product.getPrice(), product.getBuy_price());
                setImageAdapter(imageList);
            }
        });
    }

    private void getAllCustomer() {
        shopCustomerViewModel.getAllCustomer(shopID).observe(this, new Observer<List<Get_shop_customer_response>>() {
            @Override
            public void onChanged(List<Get_shop_customer_response> getShopCustomerResponses) {
                //Toast.makeText(Shop_main_activity.this, String.valueOf(getShopCustomerResponses.size()), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < getShopCustomerResponses.size(); i++) {
                    Get_shop_customer_response response = getShopCustomerResponses.get(i);

                    customerDao.insertCustomers(new Customer(response.getCustomer01r_id(), response.getCustomer01r_name(), response.getCustomer01r_address(), response.getCustomer01r_phone(), response.getCustomer01r_image()));
                }
            }
        });
    }

    private void loadContacts() {

        contactDialog.show();

        Window window = contactDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        ImageView closeButton = contactDialog.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactDialog.dismiss();
            }
        });
        EditText searchEditText = contactDialog.findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getPhoneList(editable.toString().trim());
            }
        });

        getPhoneList("");


    }

    private void loadCustomerList() {
        customerSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    customerList = customerDao.getAllCustomer(editable.toString().trim());
                    CustomerAdapter customerAdapter = new CustomerAdapter(getActivity(), customerList, Shop_local_sell_fragment.this);
                    customerSearchEditText.setAdapter(customerAdapter);
                }
            }
        });

        customerSearchEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Helpers.hideSoftKeyboard(getActivity());
            }
        });


    }

    private void getPhoneList(String searchKey) {
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                customerList = customerDao.getAllCustomer(searchKey);
                shop_local_sell_select_customer_adapter = new Shop_local_sell_select_customer_adapter(customerList);
                //shop_local_sell_select_customer_adapter.setOnClickListener(Shop_local_sell_fragment.this::customerItemClick);
                contactView.setAdapter(shop_local_sell_select_customer_adapter);
            }
        });
    }

    private void downLoadLocalSellProducts() {
        get_local_sell.getData_product(shopID).observe(getViewLifecycleOwner(), new Observer<List<Get_local_sell_product_response>>() {
            @Override
            public void onChanged(List<Get_local_sell_product_response> get_local_sell_product_responses) {

                //productList = get_local_sell_product_responses;
                for (int i = 0; i < get_local_sell_product_responses.size(); i++) {
                    Get_local_sell_product_response response = get_local_sell_product_responses.get(i);
                    localSellProductsDao.insertProducts(new LocalSellProducts(response.getId(), response.getProduct_details(), response.getPrice(), response.getBuy_price(), response.getImage()));
                }

            }
        });
    }

    private void setProductAdapter(List<LocalSellProducts> productList) {
        shopLocalSellSelectProductAdapter = new Shop_local_sell_select_product_adapter(productList);
        shopLocalSellSelectProductAdapter.setOnClickListener(Shop_local_sell_fragment.this::itemClick);
        productView.setAdapter(shopLocalSellSelectProductAdapter);
    }

    private void loadLocalSellPoints() {
        shopLocalSellPointsViewModel.getShopLocalSellPoints(shopID).observe(getViewLifecycleOwner(), new Observer<List<Shop_local_sell_point_response>>() {
            @Override
            public void onChanged(List<Shop_local_sell_point_response> shopLocalSellPointResponses) {

                shopSellPointRulesList = shopLocalSellPointResponses;

/*                Collections.sort(shopSellPointRulesList, new Comparator<Shop_local_sell_point_response>() {
                    @Override
                    public int compare(Shop_local_sell_point_response t1, Shop_local_sell_point_response t2) {
                        return t1.amount.compareToIgnoreCase(t2.amount);
                    }
                });*/
//
                //              Log.d("dataxx", String.valueOf(shopLocalSellPointResponses.size()));
                for (int i = 0; i < shopSellPointRulesList.size(); i++) {
                    String pos = String.valueOf(i + 1);
                    pointsCriteriaText.append(
                            "\n" + pos + ". " + shopSellPointRulesList.get(i).amount
                                    + " " + getActivity().getResources().getString(R.string.point_text1)
                                    + " " + shopSellPointRulesList.get(i).points
                                    + " " + getActivity().getResources().getString(R.string.point_text2)
                    );
                }
            }
        });
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();


    }

    private void convert_pdf() {
        Dialog memoAlert = new Dialog(getActivity());
        memoAlert.setContentView(R.layout.sell_success_pdf_alert);
        memoAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        memoAlert.setCancelable(false);
        memoAlert.show();

        Window window = memoAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        ImageView closeButton = memoAlert.findViewById(R.id.closeButtonID);
        LinearLayout mainLayout = memoAlert.findViewById(R.id.mainLayoutID);
        TextView shopNameText = memoAlert.findViewById(R.id.nameText);
        TextView productDetailsTextPDF = memoAlert.findViewById(R.id.productDetailsTextID);
        TextView totalPriceTextPDF = memoAlert.findViewById(R.id.totalPriceTextID);
        TextView paidPriceTextPDF = memoAlert.findViewById(R.id.paidPriceTextID);
        TextView phoneTextPDF = memoAlert.findViewById(R.id.contactText);
        TextView dateText = memoAlert.findViewById(R.id.dateTextID);
        LinearLayout savePDFButton = memoAlert.findViewById(R.id.savePDFButtonID);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        dateText.setText(currentDate);

        shop_profile = new ViewModelProvider(getActivity()).get(Shop_profile.class);
        shop_profile.getData(String.valueOf(shopID)).observe(getViewLifecycleOwner(), new Observer<shop_profile_response>() {
            @Override
            public void onChanged(shop_profile_response shop_profile_response) {
                shopNameText.setText(shop_profile_response.getStore01e_name());
            }
        });

        productDetailsTextPDF.setText(productDetailsText.getText().toString().trim());
        totalPriceTextPDF.setText(productPriceText.getText().toString().trim());
        paidPriceTextPDF.setText(paidPriceText.getText().toString().trim());
        phoneTextPDF.setText(phoneText.getText().toString().trim());

        savePDFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmapPDF = LoadBitmap(mainLayout, mainLayout.getWidth(), mainLayout.getHeight());

                createPDF();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memoAlert.dismiss();
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
            Toast.makeText(getActivity(), "Successfully saved at Documents", Toast.LENGTH_SHORT).show();
            //openPDF();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("errorxx", e.getMessage());

            pdfDocument.close();

            Toast.makeText(getActivity(), "Successfully saved at Documents", Toast.LENGTH_SHORT).show();

            // openPDF();
        }
    }

    private Bitmap LoadBitmap(View v, int width, int height) {
        Bitmap bitmapPDF = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapPDF);
        v.draw(canvas);

        return bitmapPDF;
    }

    private void sell(String productDetails, String productPrice, String paidPrice, String phone) {
        product_sell = new ViewModelProvider(getActivity()).get(Product_sell.class);
        product_sell_payment = new ViewModelProvider(getActivity()).get(Product_sell_payment.class);
        add_local_sell = new ViewModelProvider(getActivity()).get(Add_local_sell.class);
        product_sell.sell(shopID, customer_id, customer_name, customerSearchEditText.getText().toString().trim(), productPrice,
                buyPriceText.getText().toString().trim(),
                String.valueOf(duePrice),
                String.valueOf(sellPoint), "0", "local", "cc").observe(getViewLifecycleOwner(), new Observer<add_product_sell_response>() {
            @Override
            public void onChanged(add_product_sell_response add_product_sell_response) {
                if (!add_product_sell_response.getSell_id().equals("failed")) {
                    sell_id = add_product_sell_response.getSell_id();
                    product_sell_payment.get_cash(sell_id, "manual", paidPrice, "vbvb").observe(getViewLifecycleOwner(), new Observer<add_sell_payment_cash_response>() {
                        @Override
                        public void onChanged(add_sell_payment_cash_response add_sell_payment_cash_response) {
                            if (add_sell_payment_cash_response.getMessage().equals("yess")) {
                                product_sell_payment.get_transaction(sell_id, shopID, customer_id, phone, "due", String.valueOf(Double.parseDouble(productPrice) - Double.parseDouble(paidPrice)), "", "bvbv").observe(getViewLifecycleOwner(), new Observer<add_payment_transaction_response>() {
                                    @Override
                                    public void onChanged(add_payment_transaction_response add_payment_transaction_response) {
                                        if (add_payment_transaction_response.getMessage().equals("yess")) {
                                            add_local_sell.addDetails(sell_id, productDetails).observe(getViewLifecycleOwner(), new Observer<add_local_sell_details_response>() {
                                                @Override
                                                public void onChanged(add_local_sell_details_response add_local_sell_details_response) {
                                                    if (add_local_sell_details_response.getMessage().equals("yess")) {
                                                        if (check != 0) {
                                                            if (check == 1) {
                                                                image = imgToString(bitmap);
                                                                add_local_sell.addImage(sell_id, image, check).observe(getViewLifecycleOwner(), new Observer<add_local_sell_image_response>() {
                                                                    @Override
                                                                    public void onChanged(add_local_sell_image_response add_local_sell_image_response) {
                                                                        push_notification.sell_notification_customer(shopID, customer_id, productPrice, String.valueOf(Double.parseDouble(productPrice) - Double.parseDouble(paidPrice))).observe(getViewLifecycleOwner(), new Observer<push_notification_response>() {
                                                                            @Override
                                                                            public void onChanged(push_notification_response push_notification_response) {
                                                                                push_notification.sell_notification_shop(shopID, phone, productPrice, String.valueOf(Double.parseDouble(productPrice) - Double.parseDouble(paidPrice))).observe(getViewLifecycleOwner(), new Observer<com.alifew.alife.model.push_notification_response>() {
                                                                                    @Override
                                                                                    public void onChanged(com.alifew.alife.model.push_notification_response push_notification_response) {
                                                                                        loader.dismiss();
                                                                                        Toast.makeText(getActivity(), "Sell Successfully", Toast.LENGTH_SHORT).show();
                                                                                        clearAllData();
                                                                                    }
                                                                                });
                                                                            }
                                                                        });
                                                                    }
                                                                });

                                                            } else if (check == 2) {
                                                                if (imageList.size() > 0) {
                                                                    for (int i = 0; i < imageList.size(); i++) {
                                                                        loopItem = i;
                                                                        add_local_sell.addImage(sell_id, imageList.get(i), check).observe(getViewLifecycleOwner(), new Observer<add_local_sell_image_response>() {
                                                                            @Override
                                                                            public void onChanged(add_local_sell_image_response add_local_sell_image_response) {
                                                                                if (loopItem == imageList.size() - 1) {
                                                                                    push_notification.sell_notification_customer(shopID, customer_id, productPrice, String.valueOf(Double.parseDouble(productPrice) - Double.parseDouble(paidPrice))).observe(getViewLifecycleOwner(), new Observer<push_notification_response>() {
                                                                                        @Override
                                                                                        public void onChanged(push_notification_response push_notification_response) {
                                                                                            push_notification.sell_notification_shop(shopID, phone, productPrice, String.valueOf(Double.parseDouble(productPrice) - Double.parseDouble(paidPrice))).observe(getViewLifecycleOwner(), new Observer<com.alifew.alife.model.push_notification_response>() {
                                                                                                @Override
                                                                                                public void onChanged(com.alifew.alife.model.push_notification_response push_notification_response) {
                                                                                                    loader.dismiss();
                                                                                                    Toast.makeText(getActivity(), "Sell Successfully", Toast.LENGTH_SHORT).show();

                                                                                                    clearAllData();
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    });

                                                                                }
                                                                            }
                                                                        });

                                                                    }
                                                                } else {
                                                                    push_notification.sell_notification_customer(shopID, customer_id, productPrice, String.valueOf(Double.parseDouble(productPrice) - Double.parseDouble(paidPrice))).observe(getViewLifecycleOwner(), new Observer<push_notification_response>() {
                                                                        @Override
                                                                        public void onChanged(push_notification_response push_notification_response) {
                                                                            push_notification.sell_notification_shop(shopID, phone, productPrice, String.valueOf(Double.parseDouble(productPrice) - Double.parseDouble(paidPrice))).observe(getViewLifecycleOwner(), new Observer<com.alifew.alife.model.push_notification_response>() {
                                                                                @Override
                                                                                public void onChanged(com.alifew.alife.model.push_notification_response push_notification_response) {
                                                                                    loader.dismiss();
                                                                                    Toast.makeText(getActivity(), "Sell Successfully", Toast.LENGTH_SHORT).show();

                                                                                    clearAllData();

                                                                                }
                                                                            });
                                                                        }
                                                                    });

                                                                }
                                                            }

                                                        } else {
                                                            push_notification.sell_notification_customer(shopID, customer_id, productPrice, String.valueOf(Double.parseDouble(productPrice) - Double.parseDouble(paidPrice))).observe(getViewLifecycleOwner(), new Observer<push_notification_response>() {
                                                                @Override
                                                                public void onChanged(push_notification_response push_notification_response) {
                                                                    push_notification.sell_notification_shop(shopID, phone, productPrice, String.valueOf(Double.parseDouble(productPrice) - Double.parseDouble(paidPrice))).observe(getViewLifecycleOwner(), new Observer<com.alifew.alife.model.push_notification_response>() {
                                                                        @Override
                                                                        public void onChanged(com.alifew.alife.model.push_notification_response push_notification_response) {
                                                                            loader.dismiss();
                                                                            Toast.makeText(getActivity(), "Sell Successfully", Toast.LENGTH_SHORT).show();

                                                                            clearAllData();

                                                                        }
                                                                    });
                                                                }
                                                            });
                                                        }
                                                    }
                                                }
                                            });
                                        } else {
                                            loader.dismiss();
                                            Toast.makeText(getActivity(), add_payment_transaction_response.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                            } else {
                                loader.dismiss();
                                Toast.makeText(getActivity(), add_sell_payment_cash_response.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                } else {
                    loader.dismiss();
                    Toast.makeText(getActivity(), "Something Wrong!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    private void clearAllData() {

        convert_pdf();
        phoneText.setText("");
        paidPriceText.setText("");
        productDetailsText.setText("");
        productPriceText.setText("");
        buyPriceText.setText("");
        nameText.setText("");
        imageList.clear();
        adapter.notifyDataSetChanged();
        setImageAdapter(imageList);
        sellPoint = 0.0;
        duePrice = 0.0;
        setPointText(sellPoint, productPriceText.getText().toString().trim());
        customerSearchEditText.setText("");
        productsAutoCompleteText.setText("");

        profitText.setText("");
    }

    private String imgToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        String encodeimg = Base64.encodeToString(imgbytes, Base64.DEFAULT);
        return encodeimg;
    }

    public void imageselect() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (items[i].equals("Gallery")) {
                    Intent intent = new Intent(new Intent(Intent.ACTION_PICK));
                    intent.setType("image/*");

                    startActivityForResult(Intent.createChooser(intent, "select image"), IMAGE_REQUEST_CODE);

                } else if (items[i].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    //    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String item = String.valueOf(parent.getItemAtPosition(position));
//
//        ((TextView) view).setVisibility(View.GONE);
//        productDetailsText.setText(item);
//        productPriceText.setText(productList.get(position).getPrice());
//        if (!productList.get(position).getImage().equals("")) {
//            ImageHelper.imageLoader(getActivity(), productImage, productList.get(position).getImage());
//            check = 2;
//            image = productList.get(position).getImage();
//        }
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
//
    @Override
    public void itemClick(int position) {
        productDialog.dismiss();

        LocalSellProducts product = productList.get(position);
        imageList.add(product.getImage());
        selectImage.setVisibility(View.GONE);
        listImage.setVisibility(View.VISIBLE);
        setUIValue(product.getName(), product.getSellPrice(), product.getBuyPrice());
        setImageAdapter(imageList);


    }

    @SuppressLint("SetTextI18n")
    public void setUIValue(String productDetails, String product_price, String buy_price) {
        productDetailsText.append(productDetails + ", ");
        productPrice = Double.parseDouble(product_price);
        productPriceText.setText(String.valueOf(productPrice));
        paidPriceText.setText(String.valueOf(productPrice));

        buyPrice = Double.parseDouble(buy_price);
        buyPriceText.setText(String.valueOf(buyPrice));

        Double profit = Double.parseDouble(productPriceText.getText().toString().trim()) - Double.parseDouble(buyPriceText.getText().toString().trim());
        profitText.setText(String.valueOf(profit));

        duePrice = Double.parseDouble(productPriceText.getText().toString().trim()) - Double.parseDouble(paidPriceText.getText().toString().trim());

        duePriceText.setText(getString(R.string.due) + ": " + String.valueOf(duePrice));
        check = 2;

        pointsCalculation(productPrice);
    }

    private void pointsCalculation(Double productPrice) {


        if (productPrice >= Double.parseDouble(shopSellPointRulesList.get(shopSellPointRulesList.size() - 1).amount)) {
            sellPoint = Double.parseDouble(shopSellPointRulesList.get(shopSellPointRulesList.size() - 1).points);
        } else {
            for (int i = 0; i < shopSellPointRulesList.size() - 1; i++) {
                if (productPrice >= Double.parseDouble(shopSellPointRulesList.get(i).amount)
                        && productPrice < Double.parseDouble(shopSellPointRulesList.get(i + 1).amount)) {
                    sellPoint = Double.parseDouble(shopSellPointRulesList.get(i).points);
                }
            }
        }


        setPointText(sellPoint, String.valueOf(productPrice));
    }

    @SuppressLint("SetTextI18n")
    private void setPointText(Double sellPoint, String productPrice) {
        if (!productPrice.isEmpty()) {
            pointsText.setVisibility(View.VISIBLE);
            pointsText.setText("** " + productPrice + " " + getActivity().getResources().getString(R.string.point_text1) + " " + String.valueOf(sellPoint) + " " + getActivity().getResources().getString(R.string.point_text2));

        } else {
            pointsText.setVisibility(View.GONE);
        }
    }

    private void setImageAdapter(List<String> imageList) {
        adapter = new Shop_local_sell_image_list_adapter(imageList);
        imageRecyclerView.setAdapter(adapter);
    }


//    @Override
//    public void customerItemClick(int position) {
//
//        Customer customer = customerList.get(position);
//
//        phoneText.setText(customer.getPhone());
//        nameText.setText(customer.getCustomerName());
//
//        contactDialog.dismiss();
//    }
}