package com.ALife.alife.view.Shop;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.ToneGenerator;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.Custom_Type.Product_offer;
import com.ALife.alife.Custom_Type.Product_type;
import com.ALife.alife.R;
import com.ALife.alife.Utils.Helpers;
import com.ALife.alife.adapter.get_gridoff_product_adapter;
import com.ALife.alife.adapter.get_product_adapter;
import com.ALife.alife.model.add_product_offer_response;
import com.ALife.alife.model.add_product_response;
import com.ALife.alife.model.add_product_type_response;
import com.ALife.alife.model.get_product_response;
import com.ALife.alife.session.SessionManagement;
import com.ALife.alife.viewmodel.Add_product;
import com.ALife.alife.viewmodel.Add_product_offer;
import com.ALife.alife.viewmodel.Add_product_type;
import com.ALife.alife.viewmodel.Category_add;
import com.ALife.alife.viewmodel.Get_product;
import com.ALife.alife.viewmodel.Shop_products_summary;
import com.ALife.alife.viewmodel.Shop_profile;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Shop_products_add_fragment extends Fragment {
    String product_id;
    String product_discount_all;
    List<Product_type> typeList;
    List<Product_offer> offer_list;
    JSONArray jsonArray;
    ExtendedFloatingActionButton addProductButton;
    TextInputLayout productError, priceError, unitError, discountError, buyPriceError, profitError, amountError;
    TextInputEditText productText, priceText, unitText, discountText, buyPrice, profit, amount, brand;
    EditText Type, Count, priceafterdiscount, total_sell_price;
    EditText offer_amount, offer_percentage;
    EditText piece, percentage;
    TextView price, allDiscountText;
    TextView offer_price;
    ImageView tikofferbutton, addofferbutton, crossofferbutton;
    TextView productAddButton, okButton, set_sell_price_per_one, set_sell_price_with_discount_per_one, total_sell_price_with_discount, total_profit, total_profit_after_discount, unit_profit, unit_profit_with_discount;
    String profitUnit, profitUnitDiscount;
    ImageView closeButton, select_product_image, addItemButton, tikButton, submitDiscount, crossbutton;
    String id1, id2, price_with_discount, selling_profit, buy_price, products_number, buy_price_per_unit, total_selling_price, total_selling_price_after_discount, details, sell_profit, sell_profit_with_discount;
    String product_description, vaoture_no, vaoture_image;
    TextInputEditText description, vaoture;
    ImageView vaotureImage;
    RecyclerView recyclerView1, recyclerView2;
    ToggleButton gridBUtton;
    private get_product_adapter adapter;
    private get_gridoff_product_adapter grid_adapter;
    Category_add category_add;
    CheckBox itemCheckBox, discountCheckBox, otherCheckBox, offer_checkbox;
    LinearLayout hideLayout, typeLayout, discountLayout, stack_layout, OtherLayout, gridOffLayout, offer_layout, gridSearchLayout;

    Get_product get_product;
    Add_product add_product;
    Add_product_type add_product_type;
    Add_product_offer add_product_offer;
    List<get_product_response> data = new ArrayList<>();
    List<get_product_response> datagrid = new ArrayList<>();
    List<get_product_response> datagridoff = new ArrayList<>();
    private int total_count = 0, cross_check = 0;
    private GridLayoutManager layoutmanager;
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 1;
    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    int check = 0, vaoture_check = 0, final_check = 0, final_vaoture_check = 0;
    String imgdata;
    final int IMAGE_REQUEST_CODE = 999;
    private Uri filepath;
    private Bitmap bitmap, vaoture_bitmap;
    int layoutlist = 0, offerlayoutlist = 0, total_type_count, span = 1, cross_check_offer = 0;
    String Category_unit;
    EditText productSearch, productSearchGrid;
    private String all_product_discount;
    TextView all_profit, all_product, all_selling_price, all_stock;
    private int total_product;
    Double totalProfit, totalDiscount, totalSelling_price, total_stock;

    Shop_profile shop_profile;
    Shop_products_summary products_summary;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView, gridNestedScrollView;
    int page1 = 1, page2 = 1, limit1 = 10, limit2 = 10;
    int state = 0;
    int end1 = 0, end2 = 0;
    String price_sell;
    private LinearLayout barCodeScanner;
    static final int REQUEST_CAMERA_PERMISSION = 201;
    //bar code scanner property
    String shop_id;

    SurfaceView surfaceView;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    //
    ToneGenerator toneGen1;
    TextView barcodeText;
    Button ok;
    String barcodeData, barcode;
    SessionManagement sessionManagement;

    Dialog barCodeAlert;

    public Shop_products_add_fragment(String id1, String id2, String Category_unit) {
        this.id1 = id1;
        this.id2 = id2;
        this.Category_unit = Category_unit;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_products_add_fragment, container, false);

        typeList = new ArrayList<>();
        offer_list = new ArrayList<>();
        crossbutton = (ImageView) view.findViewById(R.id.crossID);
        select_product_image = (ImageView) view.findViewById(R.id.productImageID);
        productError = (TextInputLayout) view.findViewById(R.id.productErrorID);
        priceError = (TextInputLayout) view.findViewById(R.id.priceErrorID);
        unitError = (TextInputLayout) view.findViewById(R.id.unitErrorID);
        discountError = (TextInputLayout) view.findViewById(R.id.discountErrorID);
        amountError = (TextInputLayout) view.findViewById(R.id.amountErrorID);
        productText = (TextInputEditText) view.findViewById(R.id.productTextID);
        priceText = (TextInputEditText) view.findViewById(R.id.priceTextID);
        unitText = (TextInputEditText) view.findViewById(R.id.unitTextID);
        discountText = (TextInputEditText) view.findViewById(R.id.discountTextID);
        amount = (TextInputEditText) view.findViewById(R.id.amountTextID);
        brand = (TextInputEditText) view.findViewById(R.id.brandTextID);
        // product_details = (TextInputEditText) view.findViewById(R.id.othersTextID);
        productAddButton = (TextView) view.findViewById(R.id.add_ID);
        hideLayout = (LinearLayout) view.findViewById(R.id.hidingLayoutID);
        stack_layout = (LinearLayout) view.findViewById(R.id.addTypeID);
        OtherLayout = (LinearLayout) view.findViewById(R.id.hidelaoutother);

        buyPriceError = (TextInputLayout) view.findViewById(R.id.buyPriceErrorID);
        profitError = (TextInputLayout) view.findViewById(R.id.profitErrorID);

        buyPrice = (TextInputEditText) view.findViewById(R.id.buyPriceTextID);
        profit = (TextInputEditText) view.findViewById(R.id.profitTextID);
        priceafterdiscount = (EditText) view.findViewById(R.id.price_after_discount);
        itemCheckBox = (CheckBox) view.findViewById(R.id.itemCheckBoxID);
        addItemButton = (ImageView) view.findViewById(R.id.addItemButtonID);
        typeLayout = (LinearLayout) view.findViewById(R.id.layout8);
        Type = (EditText) view.findViewById(R.id.typeEditID);
        Count = (EditText) view.findViewById(R.id.countEditID);
        tikButton = (ImageView) view.findViewById(R.id.tikItemButtonID);
        discountCheckBox = (CheckBox) view.findViewById(R.id.discountCheckID);
        discountLayout = (LinearLayout) view.findViewById(R.id.discountLayoutID);
        set_sell_price_per_one = (TextView) view.findViewById(R.id.sellingPriceOneID);
        total_sell_price = (EditText) view.findViewById(R.id.totalPriceID);
        //set_sell_price_with_discount_per_one = (TextView) view.findViewById(R.id.unitPriceDiscount);
        total_sell_price_with_discount = (TextView) view.findViewById(R.id.totalSellingPricewithdiscountID);
        add_product_type = new ViewModelProvider(getActivity()).get(Add_product_type.class);
        add_product_offer = new ViewModelProvider(getActivity()).get(Add_product_offer.class);
        description = (TextInputEditText) view.findViewById(R.id.descriptionTextID);
        vaoture = (TextInputEditText) view.findViewById(R.id.vaotureNoId);
        vaotureImage = (ImageView) view.findViewById(R.id.vaotureImageID);
        otherCheckBox = (CheckBox) view.findViewById(R.id.othercheckbox);
        offer_checkbox = (CheckBox) view.findViewById(R.id.offerCheckBoxID);
        total_profit = (TextView) view.findViewById(R.id.totalProfitID);
        total_profit_after_discount = (TextView) view.findViewById(R.id.totalprofitDiscountID);
        unit_profit = (TextView) view.findViewById(R.id.unitProfitShowID);
        unit_profit_with_discount = (TextView) view.findViewById(R.id.unitProfitIdwithdiscount);
        //for offer
        offer_amount = (EditText) view.findViewById(R.id.offerAmountID);
        offer_percentage = (EditText) view.findViewById(R.id.offerPercentageID);
        offer_price = (TextView) view.findViewById(R.id.OfferPriceID);
        tikofferbutton = (ImageView) view.findViewById(R.id.tikOfferButtonID);
        addofferbutton = (ImageView) view.findViewById(R.id.addOfferButtonID);
        crossofferbutton = (ImageView) view.findViewById(R.id.offerCrossID);
        offer_layout = (LinearLayout) view.findViewById(R.id.layoutOfferID);
        barCodeScanner = (LinearLayout) view.findViewById(R.id.barCodeScannerButtonID);
        TextInputEditText productCodeText = (TextInputEditText) view.findViewById(R.id.productCodeTextID);

        sessionManagement = new SessionManagement(getActivity());

        productCodeText.setText(Helpers.uniqueProductCodeGenerator(sessionManagement.getSaveShopName()));
        unitText.setText(Category_unit);
        //end
        buyPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                priceText.getText().clear();
                unit_profit.setText("");
                if (!(TextUtils.isEmpty(profit.getText().toString().trim()) || TextUtils.isEmpty(buyPrice.getText().toString().trim()))) {
                    Double buy_product_price = Double.parseDouble(buyPrice.getText().toString().trim());
                    Double product_sell_profit = Double.parseDouble(profit.getText().toString().trim());
                    Double extra = 100 - product_sell_profit;
                    Double selling_price_product = buy_product_price + buy_product_price * (product_sell_profit / extra);
                    priceText.setText(String.valueOf(new DecimalFormat("##.##").format(selling_price_product)));
                    Double profitOne = Double.parseDouble(priceText.getText().toString().trim()) - Double.parseDouble(buyPrice.getText().toString().trim());
                    unit_profit.setText(String.valueOf(new DecimalFormat("##.##").format(profitOne)));
                    if (!(TextUtils.isEmpty(amount.getText().toString().trim()))) {
                        total_selling_price = String.valueOf(Double.parseDouble(priceText.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim()));
                        //total_sell_price.setText(total_selling_price);
                        total_sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(total_selling_price))));

                        sell_profit = String.valueOf(Double.parseDouble(total_selling_price) - buy_product_price * Double.parseDouble(amount.getText().toString().trim()));
                        //total_profit.setText(sell_profit);
                        total_profit.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(sell_profit))));


                    }

                } else {
                    unit_profit.setText("");
                    unit_profit_with_discount.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        profit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                priceText.getText().clear();
                if (!(TextUtils.isEmpty(buyPrice.getText().toString().trim()) || (TextUtils.isEmpty(profit.getText().toString().trim())))) {
                    Double buy_product_price = Double.parseDouble(buyPrice.getText().toString().trim());
                    Double product_sell_profit = Double.parseDouble(profit.getText().toString().trim());
                    Double extra = 100 - product_sell_profit;
                    Double selling_price_product = buy_product_price + buy_product_price * (product_sell_profit / extra);
                    priceText.setText(String.valueOf(new DecimalFormat("##.##").format(selling_price_product)));
                    //priceText.setText(String.valueOf(selling_price_product));
                    Double profitOne = Double.parseDouble(priceText.getText().toString().trim()) - Double.parseDouble(buyPrice.getText().toString().trim());
                    unit_profit.setText(String.valueOf(new DecimalFormat("##.##").format(profitOne)));
                    if (!(TextUtils.isEmpty(amount.getText().toString().trim()))) {
                        total_selling_price = String.valueOf(Double.parseDouble(priceText.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim()));
                        //total_sell_price.setText(total_selling_price);
                        total_sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(total_selling_price))));

                        sell_profit = String.valueOf(Double.parseDouble(total_selling_price) - buy_product_price * Double.parseDouble(amount.getText().toString().trim()));
                        // total_profit.setText(sell_profit);
                        total_profit.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(sell_profit))));
                    }

                } else {
                    unit_profit_with_discount.setText("");
                    unit_profit.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        priceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(TextUtils.isEmpty(priceText.getText().toString().trim()) || TextUtils.isEmpty(amount.getText().toString().trim()))) {
                    total_selling_price = String.valueOf(Double.parseDouble(priceText.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim()));
                    //total_sell_price.setText(total_selling_price);
                    total_sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(total_selling_price))));
                    sell_profit = String.valueOf(Double.parseDouble(total_selling_price) - Double.parseDouble(buyPrice.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim()));
                    // total_profit.setText(sell_profit);
                    total_profit.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(sell_profit))));
                } else {
                    if (!(TextUtils.isEmpty(priceText.getText().toString().trim()) || TextUtils.isEmpty(buyPrice.getText().toString().trim()))) {
                        Double profitOne = Double.parseDouble(priceText.getText().toString().trim()) - Double.parseDouble(buyPrice.getText().toString().trim());
                        unit_profit.setText(String.valueOf(new DecimalFormat("##.##").format(profitOne)));
                    } else {
                        unit_profit.setText("");
                        unit_profit_with_discount.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        select_product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                imageselect();
            }
        });

        /* set_sell_price_per_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!(TextUtils.isEmpty(priceText.getText().toString().trim()) || TextUtils.isEmpty(amount.getText().toString().trim()))) {
                            total_selling_price = String.valueOf(Double.parseDouble(priceText.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim()));
                            total_sell_price.setText(total_selling_price);
                        }

                    }
                });
                set_sell_price_with_discount_per_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!(TextUtils.isEmpty(priceafterdiscount.getText().toString().trim()) || TextUtils.isEmpty(amount.getText().toString().trim()))) {
                            selling_price_per_unit_after_discount = String.valueOf(Double.parseDouble(priceafterdiscount.getText().toString().trim()) / Double.parseDouble(amount.getText().toString().trim()));

                            sell_price_with_discount_per_one.setText(selling_price_per_unit_after_discount);
                        }
                    }
                });*/

                /*checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = ((CheckBox) v).isChecked();
                        // Check which checkbox was clicked
                        if (checked) {
                            // Do your coding
                            hideLayout.setVisibility(View.VISIBLE);
                        } else {
                            // Do your coding
                            hideLayout.setVisibility(View.GONE);
                            buyPrice.setText("");
                            profit.setText("");
                        }
                    }
                });*/

        otherCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked) {
                    // Do your coding
                    OtherLayout.setVisibility(View.VISIBLE);
                } else {
                    // Do your coding
                    OtherLayout.setVisibility(View.GONE);
                    description.setText("");
                    vaoture.setText("");
                }
            }
        });

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(TextUtils.isEmpty(amount.getText().toString().trim()))) {
                    if (!(TextUtils.isEmpty(priceText.getText().toString().trim()))) {

                        if (!(TextUtils.isEmpty(buyPrice.getText().toString().trim()))) {
                            total_selling_price = String.valueOf(Double.parseDouble(priceText.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim()));
                            //total_sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(total_selling_price)));
                            total_sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(total_selling_price))));

                            //total_sell_price.setText(total_selling_price);
                            sell_profit = String.valueOf(Double.parseDouble(total_selling_price) - Double.parseDouble(buyPrice.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim()));
                            //total_profit.setText(sell_profit);
                            total_profit.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(sell_profit))));


                        } else {
                            total_profit.setText("");
                            total_selling_price = String.valueOf(Double.parseDouble(priceText.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim()));
                            total_sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(total_selling_price))));

                            //  total_sell_price.setText(total_selling_price);
                        }
                        if (!(TextUtils.isEmpty(discountText.getText().toString().trim()))) {
                            double price_after_discount = Double.parseDouble(priceText.getText().toString().trim()) - (Double.parseDouble(priceText.getText().toString().trim()) * (Double.parseDouble(discountText.getText().toString().trim()) / 100));

                            total_selling_price_after_discount = String.valueOf(price_after_discount * Double.parseDouble(amount.getText().toString().trim()));
                            //total_sell_price_with_discount.setText(total_selling_price_after_discount);
                            total_sell_price_with_discount.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(total_selling_price_after_discount))));

                            priceafterdiscount.setText(String.valueOf(new DecimalFormat("##.##").format(price_after_discount)));


                            total_selling_price = String.valueOf(Double.parseDouble(priceText.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim()));
                            //total_sell_price.setText(total_selling_price);
                            total_sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(total_selling_price))));

                            if (!(TextUtils.isEmpty(buyPrice.getText().toString().trim()))) {
                                sell_profit_with_discount = String.valueOf(Double.parseDouble(total_selling_price_after_discount) - Double.parseDouble(buyPrice.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim()));
                                // total_profit_after_discount.setText(sell_profit_with_discount);
                                total_profit_after_discount.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(sell_profit_with_discount))));


                            } else {
                                total_profit_after_discount.setText(" ");
                            }
                        }


                    } else if (TextUtils.isEmpty(priceText.getText().toString().trim())) {
                        priceafterdiscount.setText("");
                        total_profit_after_discount.setText("");
                        total_profit.setText("");
                    } else if (TextUtils.isEmpty(discountText.getText().toString().trim())) {
                        priceafterdiscount.setText(priceText.getText().toString().trim());
                        if (TextUtils.isEmpty(buyPrice.getText().toString().trim())) {
                            total_profit_after_discount.setText("");
                            total_profit.setText("");
                        } else {
                            total_selling_price_after_discount = String.valueOf(Double.parseDouble(priceText.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim()));
                            sell_profit_with_discount = String.valueOf(Double.parseDouble(total_selling_price_after_discount) - Double.parseDouble(buyPrice.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim()));
                            total_profit_after_discount.setText(" ");
                            sell_profit = String.valueOf(Double.parseDouble(total_selling_price) - Double.parseDouble(buyPrice.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim()));
                            //total_profit.setText(sell_profit);
                            total_profit.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(sell_profit))));

                        }
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        discountCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked) {
                    discountLayout.setVisibility(View.VISIBLE);
                    //discountText.getText().clear();
                    discountText.setText("0");
                    discountText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (!(TextUtils.isEmpty(priceText.getText().toString().trim()) || TextUtils.isEmpty(discountText.getText().toString().trim()) || TextUtils.isEmpty(discountText.getText().toString().trim()))) {
                                double price_after_discount = Double.parseDouble(priceText.getText().toString().trim()) - (Double.parseDouble(priceText.getText().toString().trim()) * (Double.parseDouble(discountText.getText().toString().trim()) / 100));
                                priceafterdiscount.setText(String.valueOf(new DecimalFormat("##.##").format(price_after_discount)));

                                if (!(TextUtils.isEmpty(amount.getText().toString().trim()) || TextUtils.isEmpty(buyPrice.getText().toString().trim()))) {
                                    total_selling_price_after_discount = String.valueOf(price_after_discount * Double.parseDouble(amount.getText().toString().trim()));
                                    // total_sell_price_with_discount.setText(total_selling_price_after_discount);
                                    total_sell_price_with_discount.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(total_selling_price_after_discount))));


                                    sell_profit_with_discount = String.valueOf(Double.parseDouble(total_selling_price_after_discount) - Double.parseDouble(buyPrice.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim()));
                                    // total_profit_after_discount.setText(sell_profit_with_discount);
                                    total_profit_after_discount.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(sell_profit_with_discount))));


                                }
                                if (!(TextUtils.isEmpty(buyPrice.getText().toString().trim()))) {
                                    Double discountprofitOne = Double.parseDouble(priceafterdiscount.getText().toString().trim()) - Double.parseDouble(buyPrice.getText().toString().trim());
                                    unit_profit_with_discount.setText(String.valueOf(new DecimalFormat("##.##").format(discountprofitOne)));
                                } else {

                                    total_profit_after_discount.setText(" ");
                                }

                            } else if (TextUtils.isEmpty(priceText.getText().toString().trim())) {

                                priceafterdiscount.getText().clear();
                                total_profit_after_discount.setText(" ");
                                unit_profit_with_discount.setText("");
                            } else if (TextUtils.isEmpty(discountText.getText().toString().trim())) {
                                unit_profit_with_discount.setText("");
                                priceafterdiscount.setText(priceText.getText().toString().trim());
                                total_sell_price_with_discount.setText(String.valueOf(Double.parseDouble(priceText.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim())));
                                if (!(TextUtils.isEmpty(buyPrice.getText().toString().trim()))) {
                                    sell_profit_with_discount = String.valueOf(Double.parseDouble(total_selling_price_after_discount) - Double.parseDouble(buyPrice.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim()));
                                    //total_profit_after_discount.setText(sell_profit_with_discount);
                                    total_profit_after_discount.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(sell_profit_with_discount))));
                                } else {
                                    total_profit.setText(" ");
                                    total_profit_after_discount.setText(" ");
                                }
                            }


                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    priceafterdiscount.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (!(TextUtils.isEmpty(priceafterdiscount.getText().toString().trim()) || TextUtils.isEmpty(amount.getText().toString().trim()))) {
                                total_selling_price_after_discount = String.valueOf(Double.parseDouble(priceafterdiscount.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim()));

                                // total_sell_price_with_discount.setText(total_selling_price_after_discount);
                                total_sell_price_with_discount.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(total_selling_price_after_discount))));

                            }
                            if (!(TextUtils.isEmpty(priceafterdiscount.getText().toString().trim()) || TextUtils.isEmpty(buyPrice.getText().toString().trim()))) {
                                Double discountprofitOne = Double.parseDouble(priceafterdiscount.getText().toString().trim()) - Double.parseDouble(buyPrice.getText().toString().trim());
                                unit_profit_with_discount.setText(String.valueOf(new DecimalFormat("##.##").format(discountprofitOne)));
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                } else {
                    discountLayout.setVisibility(View.GONE);
                    discountCheckBox.setChecked(false);
                }
            }
        });

        //start add type checkbox
        itemCheckBox.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked) {
                    // Do your coding
                    addItemButton.setVisibility(View.VISIBLE);
                    typeLayout.setVisibility(View.VISIBLE);
                    tikButton.setVisibility(View.VISIBLE);
                    stack_layout.setVisibility(View.VISIBLE);
                    Type.setVisibility(View.VISIBLE);
                    Count.setVisibility(View.VISIBLE);
                    Type.setText("");
                    Count.setText("");
                    crossbutton.setVisibility(View.VISIBLE);
                    layoutlist = 0;
                    LinearLayout layout = (LinearLayout) view.findViewById(R.id.holder_layout);
                    layout.removeAllViews();
                    //submitButton
                    tikButton.setOnClickListener(new View.OnClickListener() {
                        LinearLayout layout = (LinearLayout) view.findViewById(R.id.holder_layout);

                        @Override
                        public void onClick(View v) {
                            total_count = 0;
                            typeList.clear();
                            if (Type.getText().toString().trim().isEmpty() || Count.getText().toString().trim().isEmpty()) {
                                Toast.makeText(getActivity(), "entre properly", Toast.LENGTH_SHORT).show();
                            } else {

                                String type, count;
                                Product_type product_type = new Product_type();
                                jsonArray = new JSONArray();
                                if (cross_check != 1) {
                                    product_type.setId(Integer.parseInt(id2));
                                    type = Type.getText().toString().trim();
                                    count = Count.getText().toString().trim();
                                    product_type.setType(type);
                                    product_type.setCount(count);
                                    typeList.add(product_type);
                                    total_count = total_count + Integer.parseInt(count);
                                }
                                for (int i = 0; i < layoutlist; i++) {

                                    View single_Layout = layout.getChildAt(i);
                                    EditText product_type_name = (EditText) single_Layout.findViewById(R.id.typeEditID);
                                    EditText product_type_count = (EditText) single_Layout.findViewById(R.id.countEditID);
                                    if (!(product_type_name.getText().toString().trim().isEmpty() || product_type_count.getText().toString().trim().isEmpty())) {
                                        product_type = new Product_type();
                                        product_type.setId(Integer.parseInt(id2));
                                        product_type.setType(product_type_name.getText().toString().trim());
                                        product_type.setCount(product_type_count.getText().toString().trim());
                                        total_count = total_count + Integer.parseInt(product_type_count.getText().toString().trim());
                                        typeList.add(product_type);
                                    }

                                }
                                layout.removeAllViews();
                                // typeLayout.setVisibility(View.GONE);
                                //layout.setVisibility(View.GONE);
                                stack_layout.setVisibility(View.GONE);
                                Type.setVisibility(View.GONE);
                                Count.setVisibility(View.GONE);
                                addItemButton.setVisibility(View.GONE);
                                tikButton.setVisibility(View.GONE);
                                itemCheckBox.setChecked(false);
                                crossbutton.setVisibility(View.GONE);
                                int stock_product_amount;
                                if (TextUtils.isEmpty(amount.getText().toString().trim())) {
                                    stock_product_amount = 0;
                                } else {
                                    stock_product_amount = Integer.parseInt(amount.getText().toString().trim());
                                }
                                if ((total_count <= stock_product_amount) && (stock_product_amount > 0)) {
                                    for (int i = 0; i < typeList.size(); i++) {
                                        View dynamicView = LayoutInflater.from(getActivity()).inflate(R.layout.type_product, null, false);
                                        TextView type_name = (TextView) dynamicView.findViewById(R.id.type);
                                        TextView count_number = (TextView) dynamicView.findViewById(R.id.count);
                                        type_name.setText(typeList.get(i).getType());
                                        count_number.setText(typeList.get(i).getCount());
                                        layout.addView(dynamicView);
                                    }
                                    itemCheckBox.setChecked(true);
                                } else {
                                    Toast.makeText(getActivity(), "type count must less than stock amount", Toast.LENGTH_SHORT).show();
                                }
                            }


                        }


                    });
                    crossbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cross_check = 1;
                            Type.setVisibility(View.GONE);
                            Count.setVisibility(View.GONE);
                            stack_layout.setVisibility(View.GONE);
                            //checkBox.setChecked(false);
                        }
                    });
                    addItemButton.setOnClickListener(new View.OnClickListener() {
                        LinearLayout layout = (LinearLayout) view.findViewById(R.id.holder_layout);

                        @Override
                        public void onClick(View v) {
                            //LinearLayout layout=(LinearLayout)alert.findViewById(R.id.holder_layout);
                            View dynamicView = LayoutInflater.from(getActivity()).inflate(R.layout.itemlayout, null, false);
                            EditText type = (EditText) dynamicView.findViewById(R.id.typeEditID);
                            EditText count = (EditText) dynamicView.findViewById(R.id.countEditID);
                            ImageView cross = (ImageView) dynamicView.findViewById(R.id.crossID);
                            //give id to your textview in my_linear_layout
                            // youtTextView.setText(your_each_message_from_db);


                            cross.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    View single_Layout = layout.getChildAt(layoutlist - 1);
                                    EditText product_type_name = (EditText) single_Layout.findViewById(R.id.typeEditID);
                                    EditText product_type_count = (EditText) single_Layout.findViewById(R.id.countEditID);
                                    layoutlist--;
                                    if (TextUtils.isEmpty(product_type_name.getText().toString().trim()) || TextUtils.isEmpty(product_type_count.getText().toString().trim())) {
                                        //Toast.makeText(getActivity(), "fill befor to add another box", Toast.LENGTH_LONG).show();
                                        // layoutlist--;
                                    }
                                    layout.removeView(dynamicView);
                                    //layoutlist = layout.getChildCount();
                                }
                            });
                            if ((TextUtils.isEmpty(Type.getText().toString().trim()) || TextUtils.isEmpty(Count.getText().toString().trim())) && (cross_check != 1)) {
                                Toast.makeText(getActivity(), "fill befor to add another box", Toast.LENGTH_LONG).show();
                            } else if (layoutlist > 0) {
                                View single_Layout = layout.getChildAt(layoutlist - 1);
                                EditText product_type_name = (EditText) single_Layout.findViewById(R.id.typeEditID);
                                EditText product_type_count = (EditText) single_Layout.findViewById(R.id.countEditID);
                                if (TextUtils.isEmpty(product_type_name.getText().toString().trim()) || TextUtils.isEmpty(product_type_count.getText().toString().trim())) {
                                    Toast.makeText(getActivity(), "fill befor to add another box", Toast.LENGTH_LONG).show();
                                } else {

                                    for (int i = layoutlist; i > 0; i--) {
                                        single_Layout = layout.getChildAt(i - 1);
                                        product_type_name = (EditText) single_Layout.findViewById(R.id.typeEditID);
                                        product_type_count = (EditText) single_Layout.findViewById(R.id.countEditID);
                                        type.setText(product_type_name.getText().toString().trim());
                                        count.setText(product_type_count.getText().toString().trim());
                                        type = (EditText) single_Layout.findViewById(R.id.typeEditID);
                                        count = product_type_count = (EditText) single_Layout.findViewById(R.id.countEditID);
                                        // layout.addView(single_Layout,i);
                                    }
                                    type.setText("");
                                    count.setText("");
                                    layout.addView(dynamicView);
                                    layoutlist = layout.getChildCount();
                                }
                            } else {

                                layout.addView(dynamicView);
                                layoutlist = layout.getChildCount();
                            }
                        }
                    });

                } else {
                    // Do your coding
                    addItemButton.setVisibility(View.GONE);
                    typeLayout.setVisibility(View.GONE);
                    tikButton.setVisibility(View.GONE);
                }
            }
        });
        // end add type checkbox

        //start offer checkbox
        offer_checkbox.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked) {
                    // Do your coding
                    addofferbutton.setVisibility(View.VISIBLE);
                    //typeLayout.setVisibility(View.VISIBLE);
                    tikofferbutton.setVisibility(View.VISIBLE);
                    offer_layout.setVisibility(View.VISIBLE);
                    offer_amount.setVisibility(View.VISIBLE);
                    offer_percentage.setVisibility(View.VISIBLE);
                    offer_price.setVisibility(View.VISIBLE);
                    offer_amount.setText("");
                    offer_percentage.setText("");
                    offer_price.setText("");
                    crossofferbutton.setVisibility(View.VISIBLE);
                    cross_check_offer = 0;
                    offerlayoutlist = 0;
                    LinearLayout layout_offer = (LinearLayout) view.findViewById(R.id.offer_holder_layout);
                    layout_offer.removeAllViews();
                    //submitButton
                    tikofferbutton.setOnClickListener(new View.OnClickListener() {
                        LinearLayout layout_offer = (LinearLayout) view.findViewById(R.id.offer_holder_layout);

                        @Override
                        public void onClick(View v) {
                            offer_list.clear();

                            if (offer_amount.getText().toString().trim().isEmpty() || offer_percentage.getText().toString().trim().isEmpty()) {
                                Toast.makeText(getActivity(), "fill offer entity properly", Toast.LENGTH_LONG).show();

                            } else {
                                String amount, percentage, price;
                                Product_offer product_offer = new Product_offer();
                                jsonArray = new JSONArray();
                                if (cross_check_offer != 1) {
                                    product_offer.setId(Integer.parseInt(id2));

                                    amount = offer_amount.getText().toString().trim();
                                    percentage = offer_percentage.getText().toString().trim();
                                    price = offer_price.getText().toString().trim();


                                    product_offer.setAmount(amount);
                                    product_offer.setPercentage(percentage);
                                    product_offer.setPrice(price);
                                    offer_list.add(product_offer);
                                    //  total_count = total_count + Integer.parseInt(count);
                                }
                                for (int i = 0; i < offerlayoutlist; i++) {

                                    View single_Layout = layout_offer.getChildAt(i);
                                    EditText product_amount = (EditText) single_Layout.findViewById(R.id.amountEditID);
                                    EditText offerpercentage = (EditText) single_Layout.findViewById(R.id.offerPercentageID);
                                    TextView offerprice = (TextView) single_Layout.findViewById(R.id.OfferPriceID);

                                    if (!(product_amount.getText().toString().trim().isEmpty() || offerpercentage.getText().toString().trim().isEmpty())) {
                                        product_offer = new Product_offer();
                                        product_offer.setId(Integer.parseInt(id2));
                                        product_offer.setAmount(product_amount.getText().toString().trim());
                                        product_offer.setPercentage(offerpercentage.getText().toString().trim());
                                        product_offer.setPrice(offerprice.getText().toString().trim());
                                        //total_count = total_count + Integer.parseInt(product_type_count.getText().toString().trim());
                                        offer_list.add(product_offer);
                                    }

                                }
                                layout_offer.removeAllViews();
                                // typeLayout.setVisibility(View.GONE);
                                //layout.setVisibility(View.GONE);
                                offer_layout.setVisibility(View.GONE);
                                offer_amount.setVisibility(View.GONE);
                                offer_percentage.setVisibility(View.GONE);
                                offer_price.setVisibility(View.GONE);
                                addofferbutton.setVisibility(View.GONE);
                                tikofferbutton.setVisibility(View.GONE);
                                offer_checkbox.setChecked(false);
                                offer_checkbox.setChecked(true);
                                crossofferbutton.setVisibility(View.GONE);
                                    /*int stock_product_amount;
                                    if (TextUtils.isEmpty(amount.getText().toString().trim())) {
                                        stock_product_amount = 0;
                                    } else {
                                        stock_product_amount = Integer.parseInt(amount.getText().toString().trim());
                                    }*/
                                   /* if ((total_count <= stock_product_amount) && (stock_product_amount > 0)) {
                                        for (int i = 0; i < typeList.size(); i++) {
                                            View dynamicView = LayoutInflater.from(getActivity()).inflate(R.layout.type_product, null, false);
                                            TextView type_name = (TextView) dynamicView.findViewById(R.id.type);
                                            TextView count_number = (TextView) dynamicView.findViewById(R.id.count);
                                            type_name.setText(typeList.get(i).getType());
                                            count_number.setText(typeList.get(i).getCount());
                                            layout.addView(dynamicView);
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), "type count must less than stock amount", Toast.LENGTH_SHORT).show();
                                    }*/
                                for (int i = 0; i < offer_list.size(); i++) {
                                    View dynamicView = LayoutInflater.from(getActivity()).inflate(R.layout.offer_product, null, false);
                                    TextView amount_offer = (TextView) dynamicView.findViewById(R.id.amount);
                                    TextView percentage_offer = (TextView) dynamicView.findViewById(R.id.percentage);
                                    TextView price_offer = (TextView) dynamicView.findViewById(R.id.price);
                                    amount_offer.setText(offer_list.get(i).getAmount());
                                    percentage_offer.setText(offer_list.get(i).getPercentage());
                                    price_offer.setText(offer_list.get(i).getPrice());
                                    layout_offer.addView(dynamicView);
                                }
                            }


                        }


                    });
                    crossofferbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cross_check_offer = 1;
                            offer_amount.setVisibility(View.GONE);
                            offer_amount.setVisibility(View.GONE);
                            offer_layout.setVisibility(View.GONE);
                            //checkBox.setChecked(false);
                        }
                    });

                    addofferbutton.setOnClickListener(new View.OnClickListener() {
                        LinearLayout layout_offer = (LinearLayout) view.findViewById(R.id.offer_holder_layout);

                        @Override
                        public void onClick(View v) {
                            //LinearLayout layout=(LinearLayout)view.findViewById(R.id.holder_layout);
                            View dynamicView = LayoutInflater.from(getActivity()).inflate(R.layout.dynamic_offer_layout, null, false);
                            piece = (EditText) dynamicView.findViewById(R.id.amountEditID);
                            percentage = (EditText) dynamicView.findViewById(R.id.offerPercentageID);
                            TextView price = (TextView) dynamicView.findViewById(R.id.OfferPriceID);
                            ImageView cross = (ImageView) dynamicView.findViewById(R.id.crossID);
                            //give id to your textview in my_linear_layout
                            // youtTextView.setText(your_each_message_from_db);


                            //start set offer price autometic for dynamic view
                            piece.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    if (!(TextUtils.isEmpty(piece.getText().toString().trim()) || TextUtils.isEmpty(percentage.getText().toString().trim()))) {
                                        if (!(TextUtils.isEmpty(priceafterdiscount.getText().toString().trim()))) {
                                            Double real_price = Double.parseDouble(piece.getText().toString().trim()) * Double.parseDouble(priceafterdiscount.getText().toString().trim());
                                            Double afteroffer = real_price - real_price * (Double.parseDouble(percentage.getText().toString().trim()) / 100);
                                            price.setText(String.valueOf(new DecimalFormat("##.##").format(afteroffer)));
                                        } else if (!(TextUtils.isEmpty(priceText.getText().toString().trim()))) {
                                            Double real_price = Double.parseDouble(piece.getText().toString().trim()) * Double.parseDouble(priceText.getText().toString().trim());
                                            Double afteroffer = real_price - real_price * (Double.parseDouble(percentage.getText().toString().trim()) / 100);
                                            price.setText(String.valueOf(new DecimalFormat("##.##").format(afteroffer)));
                                        } else {
                                            price.setText("");
                                        }
                                    } else {
                                        price.setText("");
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                }
                            });

                            percentage.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    if (!(TextUtils.isEmpty(piece.getText().toString().trim()) || TextUtils.isEmpty(percentage.getText().toString().trim()))) {
                                        if (!(TextUtils.isEmpty(priceafterdiscount.getText().toString().trim()))) {
                                            Double real_price = Double.parseDouble(piece.getText().toString().trim()) * Double.parseDouble(priceafterdiscount.getText().toString().trim());
                                            Double afteroffer = real_price - real_price * (Double.parseDouble(percentage.getText().toString().trim()) / 100);
                                            price.setText(String.valueOf(new DecimalFormat("##.##").format(afteroffer)));
                                        } else if (!(TextUtils.isEmpty(priceText.getText().toString().trim()))) {
                                            Double real_price = Double.parseDouble(piece.getText().toString().trim()) * Double.parseDouble(priceText.getText().toString().trim());
                                            Double afteroffer = real_price - real_price * (Double.parseDouble(percentage.getText().toString().trim()) / 100);
                                            price.setText(String.valueOf(new DecimalFormat("##.##").format(afteroffer)));
                                        } else {
                                            price.setText("");
                                        }
                                    } else {
                                        price.setText("");
                                    }

                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                }
                            });

                            //end  set offer price autometic


                            cross.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    View single_Layout = layout_offer.getChildAt(offerlayoutlist - 1);
                                    EditText product_amount = (EditText) single_Layout.findViewById(R.id.amountEditID);
                                    EditText offer_percentage = (EditText) single_Layout.findViewById(R.id.offerPercentageID);


                                    offerlayoutlist--;
                                    if (TextUtils.isEmpty(product_amount.getText().toString().trim()) || TextUtils.isEmpty(offer_percentage.getText().toString().trim())) {
                                        //Toast.makeText(getActivity(), "fill befor to add another box", Toast.LENGTH_LONG).show();
                                        // offerlayoutlist--;
                                    }
                                    layout_offer.removeView(dynamicView);
                                    //layoutlist = layout.getChildCount();
                                }
                            });
                            if ((TextUtils.isEmpty(offer_amount.getText().toString().trim()) || TextUtils.isEmpty(offer_percentage.getText().toString().trim())) && (cross_check_offer != 1)) {
                                Toast.makeText(getActivity(), "fill befor to add another box", Toast.LENGTH_LONG).show();
                            } else if (offerlayoutlist > 0) {
                                View single_Layout = layout_offer.getChildAt(offerlayoutlist - 1);
                                EditText product_amount = (EditText) single_Layout.findViewById(R.id.amountEditID);
                                EditText offer_percentage = (EditText) single_Layout.findViewById(R.id.offerPercentageID);
                                if (TextUtils.isEmpty(product_amount.getText().toString().trim()) || TextUtils.isEmpty(offer_percentage.getText().toString().trim())) {
                                    Toast.makeText(getActivity(), "fill befor to add another box", Toast.LENGTH_LONG).show();
                                } else {

                                    for (int i = offerlayoutlist; i > 0; i--) {
                                        single_Layout = layout_offer.getChildAt(i - 1);
                                        product_amount = (EditText) single_Layout.findViewById(R.id.amountEditID);
                                        offer_percentage = (EditText) single_Layout.findViewById(R.id.offerPercentageID);
                                        piece.setText(product_amount.getText().toString().trim());
                                        percentage.setText(offer_percentage.getText().toString().trim());
                                        piece = (EditText) single_Layout.findViewById(R.id.amountEditID);
                                        percentage = (EditText) single_Layout.findViewById(R.id.offerPercentageID);
                                        // layout.addView(single_Layout,i);
                                    }
                                    piece.setText("");
                                    percentage.setText("");
                                    layout_offer.addView(dynamicView);
                                    offerlayoutlist = layout_offer.getChildCount();
                                }
                            } else {

                                layout_offer.addView(dynamicView);
                                offerlayoutlist = layout_offer.getChildCount();
                            }
                        }


                    });
                    //start set offer price autometic
                    offer_amount.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (!(TextUtils.isEmpty(offer_amount.getText().toString().trim()) || TextUtils.isEmpty(offer_percentage.getText().toString().trim()))) {
                                if (!(TextUtils.isEmpty(priceafterdiscount.getText().toString().trim()))) {
                                    Double real_price = Double.parseDouble(offer_amount.getText().toString().trim()) * Double.parseDouble(priceafterdiscount.getText().toString().trim());
                                    Double afteroffer = real_price - real_price * (Double.parseDouble(offer_percentage.getText().toString().trim()) / 100);
                                    offer_price.setText(String.valueOf(new DecimalFormat("##.##").format(afteroffer)));
                                } else if (!(TextUtils.isEmpty(priceText.getText().toString().trim()))) {
                                    Double real_price = Double.parseDouble(offer_amount.getText().toString().trim()) * Double.parseDouble(priceText.getText().toString().trim());
                                    Double afteroffer = real_price - real_price * (Double.parseDouble(offer_percentage.getText().toString().trim()) / 100);
                                    offer_price.setText(String.valueOf(new DecimalFormat("##.##").format(afteroffer)));
                                } else {
                                    offer_price.setText("");
                                }
                            } else {
                                offer_price.setText("");
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    offer_percentage.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (!(TextUtils.isEmpty(offer_amount.getText().toString().trim()) || TextUtils.isEmpty(offer_percentage.getText().toString().trim()))) {
                                if (!(TextUtils.isEmpty(priceafterdiscount.getText().toString().trim()))) {
                                    Double real_price = Double.parseDouble(offer_amount.getText().toString().trim()) * Double.parseDouble(priceafterdiscount.getText().toString().trim());
                                    Double afteroffer = real_price - real_price * (Double.parseDouble(offer_percentage.getText().toString().trim()) / 100);
                                    offer_price.setText(String.valueOf(new DecimalFormat("##.##").format(afteroffer)));
                                } else if (!(TextUtils.isEmpty(priceText.getText().toString().trim()))) {
                                    Double real_price = Double.parseDouble(offer_amount.getText().toString().trim()) * Double.parseDouble(priceText.getText().toString().trim());
                                    Double afteroffer = real_price - real_price * (Double.parseDouble(offer_percentage.getText().toString().trim()) / 100);
                                    offer_price.setText(String.valueOf(new DecimalFormat("##.##").format(afteroffer)));
                                } else {
                                    offer_price.setText("");
                                }
                            } else {
                                offer_price.setText("");
                            }

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    //end  set offer price autometic


                } else {
                    // Do your coding
                    addofferbutton.setVisibility(View.GONE);
                    offer_layout.setVisibility(View.GONE);
                    tikofferbutton.setVisibility(View.GONE);
                }
            }
        });

        //end offer checkbox

        vaotureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                vaoture_imageselect();
            }
        });
        // add product submit start

        productAddButton.setOnClickListener(new View.OnClickListener() {
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.holder_layout);

            @Override
            public void onClick(View v) {

                ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = manager.getActiveNetworkInfo();

                if (info == null) {
                    Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                } else {
                    String product = productText.getText().toString().trim();
                    price_sell = priceText.getText().toString().trim();
                    String unit = unitText.getText().toString().trim();
                    String discount = discountText.getText().toString().trim();
                    String productCode = productCodeText.getText().toString().trim();
                    if (productCode.isEmpty()) {
                        productCode = "";
                    }
                    if (!(TextUtils.isEmpty(discount))) {
                        double price_after_discount = Double.parseDouble(price_sell) - (Double.parseDouble(price_sell) * (Double.parseDouble(discount) / 100));
                        price_with_discount = String.valueOf(price_after_discount);
                        total_selling_price_after_discount = String.valueOf(Double.parseDouble(priceafterdiscount.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim()));
                    } else {
                        price_with_discount = price_sell;
                    }
                    String stock_amount = amount.getText().toString().trim();
                    selling_profit = profit.getText().toString().trim();
                    productError.setErrorEnabled(false);
                    priceError.setErrorEnabled(false);
                    unitError.setErrorEnabled(false);
                    discountError.setErrorEnabled(false);

                    if (TextUtils.isEmpty(product) || TextUtils.isEmpty(price_sell) || TextUtils.isEmpty(unit)) {

                        if (TextUtils.isEmpty(product)) {
                            productError.setError(" ");
                        } else if (TextUtils.isEmpty(price_sell)) {
                            priceError.setError(" ");
                        } else if (TextUtils.isEmpty(stock_amount)) {
                            amountError.setError(" ");
                        } else if (TextUtils.isEmpty(unit)) {
                            unitError.setError(" ");
                        } else if (TextUtils.isEmpty(discount)) {
                            discountError.setError(" ");
                        }

                    } else {


                        Dialog dialog = new Dialog(getActivity());
                        dialog.setContentView(R.layout.loader);
                        dialog.show();
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setCancelable(false);

                        if (final_check == 1) {
                            imgdata = imgToString(bitmap);
                        } else {
                            imgdata = "xyz";
                        }
                        price_sell = priceText.getText().toString().trim();
                        buy_price = buyPrice.getText().toString().trim();
                        if (final_vaoture_check == 1) {
                            vaoture_image = imgToString(vaoture_bitmap);
                        } else {
                            vaoture_image = "xyz";
                        }
                        product_description = description.getText().toString().trim();
                        vaoture_no = vaoture.getText().toString().trim();
                        if (TextUtils.isEmpty(buy_price)) {
                            buy_price = "0";
                        }
                        if (TextUtils.isEmpty(selling_profit)) {
                            selling_profit = "0";
                        }
                        if (TextUtils.isEmpty(total_selling_price_after_discount)) {
                            total_selling_price_after_discount = "0";
                        }
                        if (TextUtils.isEmpty(product_description)) {
                            product_description = "";
                        }
                        if (TextUtils.isEmpty(vaoture_no)) {
                            vaoture_no = "";
                        }
                        if (TextUtils.isEmpty(price_with_discount)) {
                            price_with_discount = "0";
                        }
                        if (TextUtils.isEmpty(discount)) {
                            discount = "0";
                        }
                        //product_details.getText().toString().trim();
                        add_product = new ViewModelProvider(getActivity()).get(Add_product.class);
                        sell_profit = total_profit.getText().toString().trim();
                        sell_profit_with_discount = total_profit_after_discount.getText().toString().trim();
                        if (TextUtils.isEmpty(selling_profit)) {
                            sell_profit = "0";
                        }
                        if (TextUtils.isEmpty(sell_profit_with_discount)) {
                            sell_profit_with_discount = "0";
                        }
                        int total_type_count = 0, total_product = Integer.parseInt(stock_amount);
                        // Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < typeList.size(); i++) {
                            total_type_count += Integer.parseInt(typeList.get(i).getCount());
                        }//Toast.makeText(getActivity(),"ok",Toast.LENGTH_SHORT).show();
                        if (total_type_count <= total_product) {
                            //Toast.makeText(getActivity(),"ok",Toast.LENGTH_SHORT).show();
                            total_selling_price = String.valueOf(Double.parseDouble(priceText.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim()));
                            String productBrand = brand.getText().toString().trim();
                            profitUnit = unit_profit.getText().toString().trim();
                            profitUnitDiscount = unit_profit_with_discount.getText().toString().trim();

                            add_product.getmessage(product, id1, id2, unit, price_sell, discount, buy_price, selling_profit, stock_amount, imgdata, product_description, vaoture_no, vaoture_image, productBrand, productCode, "0").observe(getActivity(), new Observer<add_product_response>() {
                                int temp = 0;

                                @Override
                                public void onChanged(add_product_response response) {
                                    if (response.getMessage().equals("Product added successfully")) {
                                        product_id = response.getId();

                                        // type insert into database
                                        if (typeList.size() > 0) {
                                            JSONArray jArray = new JSONArray();
                                            for (int i = 0; i < typeList.size(); i++) {
                                                if (temp == 0) {
                                                    add_product_type.getmessage(typeList.get(i).getType(), typeList.get(i).getCount(), response.getId()).observe(getViewLifecycleOwner(), new Observer<add_product_type_response>() {
                                                        @Override
                                                        public void onChanged(add_product_type_response add_product_type_response) {
                                                            if (add_product_type_response.getMessage().equals("yes")) {
                                                                Toast.makeText(getActivity(), "yess", Toast.LENGTH_LONG).show();
                                                            } else {
                                                                // Toast.makeText(getActivity(), add_product_type_response.getMessage(), Toast.LENGTH_LONG).show();
                                                                temp = 1;
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    break;
                                                }

                                            }
                                            // add_product_type = new ViewModelProvider(getActivity()).get(Add_product_type.class);

                                            Toast.makeText(getActivity(), typeList.get(typeList.size() - 1).getType(), Toast.LENGTH_SHORT).show();
                                        }
                                        //
                                        if (temp == 0) {


                                            Toast toast = Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_LONG);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();
                                        } else {
                                            Toast toast = Toast.makeText(getActivity(), "something error.  ", Toast.LENGTH_LONG);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();
                                        }
                                        //start send offer to server
                                        for (int i = 0; i < offer_list.size(); i++) {
                                            add_product_offer.getmessage(offer_list.get(i).getAmount(), offer_list.get(i).getPercentage(), response.getId()).observe(getViewLifecycleOwner(), new Observer<add_product_offer_response>() {
                                                @Override
                                                public void onChanged(add_product_offer_response add_product_offer_response) {
                                                    // Toast.makeText(getActivity(), add_product_offer_response.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                        // end send offer

                                        dialog.dismiss();


                                        add_success_alert(product, stock_amount, price_sell);

                                        //  main();
                                        // refreshFragment();
                                    } else {

                                        dialog.dismiss();

                                        Toast toast = Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                }
                            });
                        } else {
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "set type count properly", Toast.LENGTH_SHORT).show();
                        }

                        // add_product.getmessage()
                        //add_products
                    }
                }
            }
        });
        barCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barCodeAlert = new Dialog(getActivity());
                barCodeAlert.setContentView(R.layout.barcode);
                barCodeAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                barCodeAlert.setCancelable(false);
                barCodeAlert.show();

                Window window = barCodeAlert.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();

                wlp.gravity = Gravity.CENTER;
                wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
                wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(wlp);


                //toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                surfaceView = barCodeAlert.findViewById(R.id.surface_view);
                barcodeText = barCodeAlert.findViewById(R.id.barcode_text);
                ok = barCodeAlert.findViewById(R.id.ok);
                ImageView close = barCodeAlert.findViewById(R.id.closeButtonID);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        barCodeAlert.dismiss();
                    }
                });
                initialiseDetectorsAndSources();
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        barcode = barcodeText.getText().toString();

                        if (barcode.isEmpty()) {

                            Toast.makeText(getActivity(), "No Barcode Available", Toast.LENGTH_SHORT).show();
                        } else {
                            barCodeAlert.dismiss();
                            productCodeText.setText(barcode);

                        }
                    }
                });

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bundle bundle = data.getExtras();

                if (check == 1) {
                    bitmap = (Bitmap) bundle.get("data");
                    check = 0;
                    final_check = 1;
                    select_product_image.setImageBitmap(bitmap);
                }
                if (vaoture_check == 1) {
                    final_vaoture_check = 1;
                    vaoture_check = 0;
                    vaoture_bitmap = (Bitmap) bundle.get("data");
                    vaotureImage.setImageBitmap(vaoture_bitmap);
                }

            } else if (requestCode == IMAGE_REQUEST_CODE) {
                filepath = data.getData();
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);

                    if (check == 1) {
                        check = 0;
                        final_check = 1;
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        select_product_image.setImageBitmap(bitmap);
                    }
                    if (vaoture_check == 1) {
                        final_vaoture_check = 1;
                        vaoture_check = 0;
                        vaoture_bitmap = BitmapFactory.decodeStream(inputStream);
                        vaotureImage.setImageBitmap(vaoture_bitmap);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

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
                    check = 1;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (items[i].equals("Gallery")) {
                    check = 1;
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

    public void vaoture_imageselect() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")) {
                    vaoture_check = 1;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (items[i].equals("Gallery")) {
                    vaoture_check = 1;
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

    private void add_success_alert(String product, String stock_amount, String price_sell) {
        Dialog successDialog = new Dialog(getActivity());
        successDialog.setContentView(R.layout.product_add_successful_alert);
        successDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        successDialog.setCancelable(false);
        successDialog.show();

        AppCompatButton okButton = (AppCompatButton) successDialog.findViewById(R.id.okButtonID);
        TextView product_name, product_stock, sell_price;
        product_name = (TextView) successDialog.findViewById(R.id.productNameID);
        product_stock = (TextView) successDialog.findViewById(R.id.stockAmountID);
        sell_price = (TextView) successDialog.findViewById(R.id.sellPriceID);
        product_name.setText(product);
        product_stock.setText(stock_amount);
        sell_price.setText(price_sell);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successDialog.dismiss();
                //get_products_summary();
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_products_fragment(id1, id2, Category_unit)).addToBackStack(null).commit();


            }
        });
    }

    private void initialiseDetectorsAndSources() {

        barcodeDetector = new BarcodeDetector.Builder(getActivity())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(getActivity(), barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @SuppressLint("MissingPermission")
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                // Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    barcodeText.post(new Runnable() {

                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).email != null) {
                                barcodeText.removeCallbacks(null);
                                barcodeData = barcodes.valueAt(0).email.address;
                                barcodeText.setText(barcodeData);
                                //toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                               // barCodeAlert.dismiss();
                            } else {

                                barcodeData = barcodes.valueAt(0).displayValue;
                                barcodeText.setText(barcodeData);
                                //toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                               // barCodeAlert.dismiss();
                            }


                        }
                    });

                }
            }
        });
    }
}