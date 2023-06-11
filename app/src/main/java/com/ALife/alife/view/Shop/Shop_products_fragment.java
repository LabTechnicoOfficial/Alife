package com.ALife.alife.view.Shop;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.adapter.get_gridoff_product_adapter;
import com.ALife.alife.adapter.get_product_adapter;
import com.ALife.alife.adapter.get_product_offer_adapter;
import com.ALife.alife.adapter.get_product_type_adapter;
import com.ALife.alife.Custom_Type.Product_offer;
import com.ALife.alife.Custom_Type.Product_type;
import com.ALife.alife.model.Update_product_status_response;
import com.ALife.alife.model.add_product_offer_response;
import com.ALife.alife.model.add_product_response;
import com.ALife.alife.model.add_product_type_response;
import com.ALife.alife.model.delete_category_response;
import com.ALife.alife.model.get_product_offer_response;
import com.ALife.alife.model.get_product_response;
import com.ALife.alife.model.get_product_type_response;
import com.ALife.alife.model.get_shop_products_summary_response;
import com.ALife.alife.model.shop_profile_response;
import com.ALife.alife.view.Product_details_fragment;
import com.ALife.alife.viewmodel.Add_product;
import com.ALife.alife.viewmodel.Add_product_offer;
import com.ALife.alife.viewmodel.Add_product_type;
import com.ALife.alife.viewmodel.Category_add;
import com.ALife.alife.viewmodel.Delete_category;
import com.ALife.alife.viewmodel.Get_product;
import com.ALife.alife.viewmodel.Get_product_offer;
import com.ALife.alife.viewmodel.Get_product_type;
import com.ALife.alife.viewmodel.Shop_products_summary;
import com.ALife.alife.viewmodel.Shop_profile;
import com.ALife.alife.viewmodel.Update_product_status;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.ALife.alife.R.layout.fragment_products;

public class Shop_products_fragment<SharedViewModel> extends Fragment implements get_product_adapter.OnItemClickListener, get_product_adapter.OnItemSellListener, get_gridoff_product_adapter.OnItemClickListener, get_gridoff_product_adapter.OnItemDeleteListener, get_gridoff_product_adapter.OnItemHideListener, get_gridoff_product_adapter.OnItemTypeListener, get_gridoff_product_adapter.OnItemOfferListener, get_gridoff_product_adapter.OnItemSellListener {
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
    TextView all_profit, all_product, all_selling_price, all_stock,all_buy_price;
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

    public Shop_products_fragment(String id1, String id2, String Category_unit) {
        this.id1 = id1;
        this.id2 = id2;
        this.Category_unit = Category_unit;
    }

    public void showProduct1() {
        gridOffLayout.setVisibility(View.GONE);
        gridSearchLayout.setVisibility(View.VISIBLE);
        page1 = 1;
        end1 = 0;
        datagridoff = new ArrayList<>();
        get_product1(page1, limit1);


        productSearchGrid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(productSearchGrid.getText().toString().trim())) {
                    datagridoff = new ArrayList<>();
                    adapter = new get_product_adapter(datagridoff, allDiscountText.getText().toString().trim());
                    page1 = 1;
                    end1 = 0;
                    get_product1(page1, limit1);
                } else {

                    //adapter.getFilter().filter(productSearchGrid.getText());
                    get_product_by_search(productSearchGrid.getText().toString(), 1);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void get_product_by_search(String value, int position) {
        get_product = new ViewModelProvider(getActivity()).get(Get_product.class);
        get_product.getCategoryProduct(id2).observe(getViewLifecycleOwner(), new Observer<List<get_product_response>>() {
            @Override
            public void onChanged(List<get_product_response> get_product_responses) {
                if (position == 1) {
                    data = new ArrayList<>();
                    adapter = new get_product_adapter(data, allDiscountText.getText().toString().trim());
                    adapter.setOnClickListener(Shop_products_fragment.this::OnItemClick, Shop_products_fragment.this::OnItemSell);
                    recyclerView2.setAdapter(adapter);

                    for (int i = 0; i < get_product_responses.size(); i++) {
                        String brand_code = get_product_responses.get(i).getBrand() + get_product_responses.get(i).getCode();
                        if ((get_product_responses.get(i).getProduct_id().contains(value) || get_product_responses.get(i).getProduct_name().toLowerCase().contains(value.toLowerCase())) || (get_product_responses.get(i).getBrand().toLowerCase().contains(value.toLowerCase())) || (brand_code.toLowerCase().contains(value.toLowerCase()))) {
                            data.add(get_product_responses.get(i));
                        }
                    }
                    adapter = new get_product_adapter(data, allDiscountText.getText().toString().trim());
                    adapter.setOnClickListener(Shop_products_fragment.this::OnItemClick, Shop_products_fragment.this::OnItemSell);
                    recyclerView2.setAdapter(adapter);

                } else if (position == 2) {
                    data = new ArrayList<>();
                    grid_adapter = new get_gridoff_product_adapter(data, allDiscountText.getText().toString().trim());
                    grid_adapter.setOnClickListener(Shop_products_fragment.this::OnItemClick, Shop_products_fragment.this::OnItemDelete, Shop_products_fragment.this::OnItemHide, Shop_products_fragment.this::OnItemType, Shop_products_fragment.this::OnItemOffer, Shop_products_fragment.this::OnItemSell);

                    recyclerView1.setAdapter(grid_adapter);
                    for (int i = 0; i < get_product_responses.size(); i++) {
                        String brand_code = get_product_responses.get(i).getBrand() + get_product_responses.get(i).getCode();
                        if ((get_product_responses.get(i).getProduct_id().contains(value) || get_product_responses.get(i).getProduct_name().toLowerCase().contains(value.toLowerCase())) || (get_product_responses.get(i).getBrand().toLowerCase().contains(value.toLowerCase())) || (brand_code.toLowerCase().contains(value.toLowerCase()))) {
                            data.add(get_product_responses.get(i));
                        }
                    }
                    grid_adapter = new get_gridoff_product_adapter(data, allDiscountText.getText().toString().trim());
                    grid_adapter.setOnClickListener(Shop_products_fragment.this::OnItemClick, Shop_products_fragment.this::OnItemDelete, Shop_products_fragment.this::OnItemHide, Shop_products_fragment.this::OnItemType, Shop_products_fragment.this::OnItemOffer, Shop_products_fragment.this::OnItemSell);

                    recyclerView1.setAdapter(grid_adapter);

                }
            }
        });
    }

    public void get_product1(int page, int limit) {

        get_product = new ViewModelProvider(getActivity()).get(Get_product.class);
        get_product.getdata(id2, page, limit).observe(getViewLifecycleOwner(), new Observer<List<get_product_response>>() {
            @Override
            public void onChanged(List<get_product_response> get_product_responses) {
                progressBar.setVisibility(View.GONE);
                if (page == 1) {
                    datagridoff = new ArrayList<>();
                    adapter = new get_product_adapter(datagridoff, allDiscountText.getText().toString().trim());

                    // data = datagridoff;
                    //adapter.setOnClickListener(Showdetails.this);
                    adapter.setOnClickListener(Shop_products_fragment.this::OnItemClick, Shop_products_fragment.this::OnItemSell);
                    //loaderDialog.dismiss();
                    recyclerView2.setAdapter(adapter);
                }
                for (int i = 0; i < get_product_responses.size(); i++) {
                    datagridoff.add(get_product_responses.get(i));
                }
                if (get_product_responses.size() < limit) {
                    end1 = 1;
                }
                adapter = new get_product_adapter(datagridoff, allDiscountText.getText().toString().trim());
                data = datagridoff;
                //adapter.setOnClickListener(Showdetails.this);
                adapter.setOnClickListener(Shop_products_fragment.this::OnItemClick, Shop_products_fragment.this::OnItemSell);

                // product_discount_all = get_all_product_discount();

               /* all_product.setText(String.valueOf(total_product));
                all_profit.setText("bbbb");
                Toast.makeText(getActivity(),String.valueOf(totalProfit),Toast.LENGTH_SHORT).show();
                //all_profit.setText(String.valueOf(new DecimalFormat("##.##").format(totalProfit)));
                all_selling_price.setText(String.valueOf(new DecimalFormat("##.##").format(totalSelling_price)));
                all_stock.setText(String.valueOf(new DecimalFormat("##.##").format(total_stock)));*/
                recyclerView2.setAdapter(adapter);

            }
        });
    }

    public void showProduct2() {
        gridSearchLayout.setVisibility(View.GONE);
        gridOffLayout.setVisibility(View.VISIBLE);
        page2 = 1;
        end2 = 0;
        datagrid = new ArrayList<>();
        grid_adapter = new get_gridoff_product_adapter(datagrid, allDiscountText.getText().toString().trim());
        data = datagrid;
        //adapter.setOnClickListener(Showdetails.this);
        grid_adapter.setOnClickListener(Shop_products_fragment.this::OnItemClick, Shop_products_fragment.this::OnItemDelete, Shop_products_fragment.this::OnItemHide, Shop_products_fragment.this::OnItemType, Shop_products_fragment.this::OnItemOffer, Shop_products_fragment.this::OnItemSell);

        recyclerView1.setAdapter(grid_adapter);
        get_product2(page2, limit2);


        productSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(productSearch.getText().toString().trim())) {
                    page2 = 1;
                    end2 = 0;

                    get_product2(page2, limit2);
                } else {

                    //grid_adapter.getFilter().filter(productSearch.getText());
                    get_product_by_search(productSearch.getText().toString(), 2);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void get_product2(int page, int limit) {
        get_product = new ViewModelProvider(getActivity()).get(Get_product.class);
        get_product.getdata(id2, page, limit).observe(getViewLifecycleOwner(), new Observer<List<get_product_response>>() {
            @Override
            public void onChanged(List<get_product_response> get_product_responses) {
                progressBar.setVisibility(View.GONE);
                if (page == 1) {
                    datagrid = new ArrayList<>();
                    grid_adapter = new get_gridoff_product_adapter(datagrid, allDiscountText.getText().toString().trim());
                    grid_adapter.setOnClickListener(Shop_products_fragment.this::OnItemClick, Shop_products_fragment.this::OnItemDelete, Shop_products_fragment.this::OnItemHide, Shop_products_fragment.this::OnItemType, Shop_products_fragment.this::OnItemOffer, Shop_products_fragment.this::OnItemSell);

                    recyclerView1.setAdapter(grid_adapter);
                }
                for (int i = 0; i < get_product_responses.size(); i++) {
                    datagrid.add(get_product_responses.get(i));
                }
                if (get_product_responses.size() < limit) {
                    end2 = 1;
                }
                grid_adapter = new get_gridoff_product_adapter(datagrid, allDiscountText.getText().toString().trim());
                data = datagrid;
                //adapter.setOnClickListener(Showdetails.this);
                grid_adapter.setOnClickListener(Shop_products_fragment.this::OnItemClick, Shop_products_fragment.this::OnItemDelete, Shop_products_fragment.this::OnItemHide, Shop_products_fragment.this::OnItemType, Shop_products_fragment.this::OnItemOffer, Shop_products_fragment.this::OnItemSell);

                recyclerView1.setAdapter(grid_adapter);

            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        get_products_summary();
        //end add product
    }

    private void main() {

        checkConnection();
        product_discount_all = "0";


        /*layoutmanager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(layoutmanager);
        gridOffLayout.setVisibility(View.GONE);*/

        state = 0;
        get_all_product_discount();

        gridBUtton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    span = 2;

                    layoutmanager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
                    recyclerView2.setLayoutManager(layoutmanager);

                    showProduct1();

                } else {
                    span = 1;

                    layoutmanager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
                    recyclerView1.setLayoutManager(layoutmanager);
                    showProduct2();
                }
            }
        });
        //start add product
        productSearch.setText("");

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_products_add_fragment(id1, id2, Category_unit)).addToBackStack(null).commit();

                Dialog alert = new Dialog(getActivity());
                alert.setContentView(R.layout.products_form);
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alert.setCancelable(false);
                //alert.show();

                typeList = new ArrayList<>();
                offer_list = new ArrayList<>();
                closeButton = (ImageView) alert.findViewById(R.id.closeID);
                crossbutton = (ImageView) alert.findViewById(R.id.crossID);
                select_product_image = (ImageView) alert.findViewById(R.id.productImage);
                productError = (TextInputLayout) alert.findViewById(R.id.productErrorID);
                priceError = (TextInputLayout) alert.findViewById(R.id.priceErrorID);
                unitError = (TextInputLayout) alert.findViewById(R.id.unitErrorID);
                discountError = (TextInputLayout) alert.findViewById(R.id.discountErrorID);
                amountError = (TextInputLayout) alert.findViewById(R.id.amountErrorID);
                productText = (TextInputEditText) alert.findViewById(R.id.productTextID);
                priceText = (TextInputEditText) alert.findViewById(R.id.priceTextID);
                unitText = (TextInputEditText) alert.findViewById(R.id.unitTextID);
                discountText = (TextInputEditText) alert.findViewById(R.id.discountTextID);
                amount = (TextInputEditText) alert.findViewById(R.id.amountTextID);
                brand = (TextInputEditText) alert.findViewById(R.id.brandTextID);
                // product_details = (TextInputEditText) alert.findViewById(R.id.othersTextID);
                productAddButton = (TextView) alert.findViewById(R.id.add_ID);
                hideLayout = (LinearLayout) alert.findViewById(R.id.hidingLayoutID);
                stack_layout = (LinearLayout) alert.findViewById(R.id.addTypeID);
                OtherLayout = (LinearLayout) alert.findViewById(R.id.hidelaoutother);

                buyPriceError = (TextInputLayout) alert.findViewById(R.id.buyPriceErrorID);
                profitError = (TextInputLayout) alert.findViewById(R.id.profitErrorID);

                buyPrice = (TextInputEditText) alert.findViewById(R.id.buyPriceTextID);
                profit = (TextInputEditText) alert.findViewById(R.id.profitTextID);
                priceafterdiscount = (EditText) alert.findViewById(R.id.price_after_discount);
                itemCheckBox = (CheckBox) alert.findViewById(R.id.itemCheckBoxID);
                addItemButton = (ImageView) alert.findViewById(R.id.addItemButtonID);
                typeLayout = (LinearLayout) alert.findViewById(R.id.layout8);
                Type = (EditText) alert.findViewById(R.id.typeEditID);
                Count = (EditText) alert.findViewById(R.id.countEditID);
                tikButton = (ImageView) alert.findViewById(R.id.tikItemButtonID);
                discountCheckBox = (CheckBox) alert.findViewById(R.id.discountCheckID);
                discountLayout = (LinearLayout) alert.findViewById(R.id.discountLayoutID);
                set_sell_price_per_one = (TextView) alert.findViewById(R.id.sellingPriceOneID);
                total_sell_price = (EditText) alert.findViewById(R.id.totalPriceID);
                set_sell_price_with_discount_per_one = (TextView) alert.findViewById(R.id.unitPriceDiscount);
                total_sell_price_with_discount = (TextView) alert.findViewById(R.id.totalSellingPricewithdiscountID);
                add_product_type = new ViewModelProvider(getActivity()).get(Add_product_type.class);
                add_product_offer = new ViewModelProvider(getActivity()).get(Add_product_offer.class);
                description = (TextInputEditText) alert.findViewById(R.id.descriptionTextID);
                vaoture = (TextInputEditText) alert.findViewById(R.id.vaotureNoId);
                vaotureImage = (ImageView) alert.findViewById(R.id.vaotureImageID);
                otherCheckBox = (CheckBox) alert.findViewById(R.id.othercheckbox);
                offer_checkbox = (CheckBox) alert.findViewById(R.id.offerCheckBoxID);
                total_profit = (TextView) alert.findViewById(R.id.totalProfitID);
                total_profit_after_discount = (TextView) alert.findViewById(R.id.totalprofitDiscountID);
                unit_profit = (TextView) alert.findViewById(R.id.unitProfitShowID);
                unit_profit_with_discount = (TextView) alert.findViewById(R.id.unitProfitIdwithdiscount);
                //for offer
                offer_amount = (EditText) alert.findViewById(R.id.offerAmountID);
                offer_percentage = (EditText) alert.findViewById(R.id.offerPercentageID);
                offer_price = (TextView) alert.findViewById(R.id.OfferPriceID);
                tikofferbutton = (ImageView) alert.findViewById(R.id.tikOfferButtonID);
                addofferbutton = (ImageView) alert.findViewById(R.id.addOfferButtonID);
                crossofferbutton = (ImageView) alert.findViewById(R.id.offerCrossID);
                offer_layout = (LinearLayout) alert.findViewById(R.id.layoutOfferID);

                TextInputEditText productCodeText = (TextInputEditText) alert.findViewById(R.id.productCodeTextID);
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
                            LinearLayout layout = (LinearLayout) alert.findViewById(R.id.holder_layout);
                            layout.removeAllViews();
                            //submitButton
                            tikButton.setOnClickListener(new View.OnClickListener() {
                                LinearLayout layout = (LinearLayout) alert.findViewById(R.id.holder_layout);

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
                                LinearLayout layout = (LinearLayout) alert.findViewById(R.id.holder_layout);

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
                            LinearLayout layout_offer = (LinearLayout) alert.findViewById(R.id.offer_holder_layout);
                            layout_offer.removeAllViews();
                            //submitButton
                            tikofferbutton.setOnClickListener(new View.OnClickListener() {
                                LinearLayout layout_offer = (LinearLayout) alert.findViewById(R.id.offer_holder_layout);

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
                                LinearLayout layout_offer = (LinearLayout) alert.findViewById(R.id.offer_holder_layout);

                                @Override
                                public void onClick(View v) {
                                    //LinearLayout layout=(LinearLayout)alert.findViewById(R.id.holder_layout);
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

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.cancel();
                    }
                });

                vaotureImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                        vaoture_imageselect();
                    }
                });
                // add product submit start

                productAddButton.setOnClickListener(new View.OnClickListener() {
                    LinearLayout layout = (LinearLayout) alert.findViewById(R.id.holder_layout);

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
                                                alert.cancel();

                                                add_success_alert(product, stock_amount, price_sell);

                                                //  main();
                                                // refreshFragment();
                                            } else {

                                                dialog.dismiss();
                                                alert.cancel();
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
                // add product submit end
            }

        });
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
                get_products_summary();

            }
        });
    }

    private void get_all_product_discount() {
        shop_profile = new ViewModelProvider(getActivity()).get(Shop_profile.class);
        shop_profile.getData(id1).observe(getViewLifecycleOwner(), new Observer<shop_profile_response>() {
            @Override
            public void onChanged(shop_profile_response shop_profile_response) {

                // product_discount_all = shop_profile_response.getAll_discount();
                allDiscountText.setText(shop_profile_response.getAll_discount());
                if (span == 2) {
                    layoutmanager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
                    recyclerView2.setLayoutManager(layoutmanager);
                    showProduct1();
                } else {
                    if (state == 0) {
                        layoutmanager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
                        recyclerView1.setLayoutManager(layoutmanager);
                        gridOffLayout.setVisibility(View.GONE);
                        state = 1;
                        showProduct2();
                    }
                }


            }
        });

    }

    private void get_products_summary() {
        products_summary = new ViewModelProvider(getActivity()).get(Shop_products_summary.class);
        products_summary.getData_category(id2, id1).observe(getViewLifecycleOwner(), new Observer<get_shop_products_summary_response>() {
            @Override
            public void onChanged(get_shop_products_summary_response get_shop_products_summary_response) {
                all_product.setText(String.valueOf(get_shop_products_summary_response.getAll_product()));
                all_profit.setText(String.valueOf(new DecimalFormat("##.##").format(get_shop_products_summary_response.getAll_profit())));
                all_selling_price.setText(String.valueOf(new DecimalFormat("##.##").format(get_shop_products_summary_response.getAll_sell_price())));
                all_buy_price.setText(String.valueOf(new DecimalFormat("##.##").format(get_shop_products_summary_response.getAll_buy_price())));
                main();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(fragment_products, container, false);
        checkConnection();
        recyclerView1 = view.findViewById(R.id.recyclerViewID);
        recyclerView2 = view.findViewById(R.id.gridRecyclerViewID);
        gridBUtton = (ToggleButton) view.findViewById(R.id.toggleButtonID);
        gridOffLayout = (LinearLayout) view.findViewById(R.id.gridOffLayoutID);
        gridSearchLayout = (LinearLayout) view.findViewById(R.id.gridLayoutID);
        //gridOffLayout.setVisibility(View.GONE);
        recyclerView1.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        productSearch = (EditText) view.findViewById(R.id.productSearchID);
        productSearchGrid = (EditText) view.findViewById(R.id.gridProductSearchID);
        addProductButton = (ExtendedFloatingActionButton) view.findViewById(R.id.add_productID);
        all_product = (TextView) view.findViewById(R.id.totalProductsID);
        all_buy_price= (TextView) view.findViewById(R.id.all_buyPriceId);
        all_profit = (TextView) view.findViewById(R.id.totalProfitID);
        all_selling_price = (TextView) view.findViewById(R.id.totalSellPriceID);
        allDiscountText = (TextView) view.findViewById(R.id.allDiscountID);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBarID);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);
        gridNestedScrollView = (NestedScrollView) view.findViewById(R.id.gridNestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > oldScrollY) {
                    addProductButton.hide();
                } else {
                    addProductButton.show();
                }
                //addProductButton.show();
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    if (end2 == 0) {
                        progressBar.setVisibility(View.VISIBLE);
                        page2++;
                        get_product2(page2, limit2);
                    }

                }
            }
        });

        gridNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > oldScrollY) {
                    addProductButton.hide();
                } else {
                    addProductButton.show();
                }
                //addProductButton.show();
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    if (end1 == 0) {
                        progressBar.setVisibility(View.VISIBLE);
                        page1++;
                        get_product1(page1, limit2);
                    }
                }
            }
        });

        recyclerView1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && addProductButton.getVisibility() == View.VISIBLE) {
                    addProductButton.hide();
                } else if (dy < 0 && addProductButton.getVisibility() != View.VISIBLE) {
                    addProductButton.show();
                }
            }
        });

        recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && addProductButton.getVisibility() == View.VISIBLE) {
                    addProductButton.hide();
                } else if (dy < 0 && addProductButton.getVisibility() != View.VISIBLE) {
                    addProductButton.show();
                }
            }
        });

        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (productSearch != null) {
            productSearch.setText("");
        }
        // gridBUtton.setChecked(false);
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

    @Override
    public void OnItemClick(int position) {
        get_product_response clickItem = data.get(position);
        String product_description = clickItem.getProduct_description();
        String vaoture_no = clickItem.getVaoture_no();
        String vaoture_image = clickItem.getVaoture_image();
        if (TextUtils.isEmpty(product_description)) {
            product_description = "";
        }
        if (TextUtils.isEmpty(vaoture_no)) {
            vaoture_no = "";
        }
        if (TextUtils.isEmpty(vaoture_image)) {
            vaoture_image = "";
        }
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Product_details_fragment(id1, id2, clickItem.getProduct_id(), Category_unit)).addToBackStack(null).commit();


    }

    public void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
        // adapter.notifyDataSetChanged();
    }

    @Override
    public void OnItemDelete(int position) {
        String response_product_id = data.get(position).getProduct_id();
        Dialog alert = new Dialog(getActivity());
        alert.setContentView(R.layout.delete_alert);
        alert.show();

        TextView yesButton = alert.findViewById(R.id.yesButton);
        TextView noButton = alert.findViewById(R.id.noButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete_category delete_category;
                delete_category = new ViewModelProvider(getActivity()).get(Delete_category.class);
                delete_category.getdelete_product(response_product_id).observe(getViewLifecycleOwner(), new Observer<delete_category_response>() {
                    @Override
                    public void onChanged(delete_category_response delete_category_response) {
                        if (delete_category_response.getMessage().equals("Product deleted successfully")) {

                            Toast toast = Toast.makeText(getActivity(), delete_category_response.getMessage(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            alert.cancel();
                            get_products_summary();
                            //main();  // refreshFragment();
                        } else {
                            Toast toast = Toast.makeText(getActivity(), delete_category_response.getMessage(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            alert.cancel();
                        }
                    }
                });
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.cancel();
            }
        });
    }


    @Override
    public void OnItemHide(int position) {
        Dialog alertCustom = new Dialog(getActivity());
        alertCustom.setContentView(R.layout.loader);
        alertCustom.show();
        alertCustom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertCustom.setCancelable(false);

        get_product_response clickItem = data.get(position);
        String status = clickItem.getStatus();
        Update_product_status update_product_status;
        update_product_status = new ViewModelProvider(getActivity()).get(Update_product_status.class);
        if (status.equals("0")) {
            update_product_status.getData(clickItem.getProduct_id(), "1").observe(getViewLifecycleOwner(), new Observer<Update_product_status_response>() {
                @Override
                public void onChanged(Update_product_status_response s) {
                    refreshFragment();
                    final Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        public void run() {
                            alertCustom.dismiss(); // when the task active then close the dialog
                            t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                        }
                    }, 1500);
                }

            });
        } else {
            update_product_status.getData(clickItem.getProduct_id(), "0").observe(getViewLifecycleOwner(), new Observer<Update_product_status_response>() {
                @Override
                public void onChanged(Update_product_status_response s) {
                    refreshFragment();
                    final Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        public void run() {
                            alertCustom.dismiss(); // when the task active then close the dialog
                            t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                        }
                    }, 1500);

                }


            });
        }

    }

    get_product_type_adapter adapter_type;

    @Override
    public void OnItemType(int position) {
        Dialog typeAlert = new Dialog(getActivity());
        typeAlert.setContentView(R.layout.type_open_alert);
        typeAlert.show();
        typeAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        typeAlert.setCancelable(false);
        ImageView closeButton = typeAlert.findViewById(R.id.closeID);
        TextView noTypesText = typeAlert.findViewById(R.id.noTypesID);
        RecyclerView typeOpenRecyclerView = typeAlert.findViewById(R.id.typeOpenRecyclerViewID);

        LinearLayoutManager typeOpenLayoutmanager = new LinearLayoutManager(getActivity());

        typeOpenRecyclerView.setHasFixedSize(true);
        typeOpenRecyclerView.setLayoutManager(typeOpenLayoutmanager);
        Get_product_type get_product_type;
        get_product_type = new ViewModelProvider(getActivity()).get(Get_product_type.class);
        //  get_product_type_adapter adapter_type;
        //data = new ArrayList<>();
        get_product_type.getdata(data.get(position).getProduct_id()).observe(getViewLifecycleOwner(), new Observer<List<get_product_type_response>>() {
            @Override
            public void onChanged(List<get_product_type_response> get_product_type_responses) {
                if (get_product_type_responses.size() > 0) {
                    adapter_type = new get_product_type_adapter(get_product_type_responses);
                    // data = get_product_type_responses;
                    //adapter.setOnClickListener(Product_details_fragment.this::OnItemClick);
                    typeOpenRecyclerView.setAdapter(adapter_type);
                } else {
                    //Toast.makeText(getActivity(), "no type availabel", Toast.LENGTH_SHORT).show();
                    typeOpenRecyclerView.setVisibility(View.GONE);
                    noTypesText.setVisibility(View.VISIBLE);

                }
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeAlert.dismiss();
            }
        });
    }

    Get_product_offer get_product_offer;
    get_product_offer_adapter offer_adapter;

    @Override
    public void OnItemOffer(int position) {
        Dialog offerAlert = new Dialog(getActivity());
        offerAlert.setContentView(R.layout.offer_open_alert);
        offerAlert.show();
        offerAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        offerAlert.setCancelable(false);
        ImageView closeButton = offerAlert.findViewById(R.id.closeID);
        TextView noOffersText = offerAlert.findViewById(R.id.noOffersID);
        RecyclerView offerOpenRecyclerView = offerAlert.findViewById(R.id.offerOpenRecyclerViewID);

        LinearLayoutManager offerOpenLayoutmanager = new LinearLayoutManager(getActivity());

        offerOpenRecyclerView.setHasFixedSize(true);
        offerOpenRecyclerView.setLayoutManager(offerOpenLayoutmanager);
        get_product_offer = new ViewModelProvider(getActivity()).get(Get_product_offer.class);
        //offer = new ArrayList<>();
        get_product_offer.getdata(data.get(position).getProduct_id()).observe(getViewLifecycleOwner(), new Observer<List<get_product_offer_response>>() {
            @Override
            public void onChanged(List<get_product_offer_response> get_product_offer_responses) {
                if (get_product_offer_responses.size() > 0) {


                    offer_adapter = new get_product_offer_adapter(get_product_offer_responses, data.get(position).getProduct_unit(), data.get(position).getSelling_price());
                    // offer = get_product_offer_responses;
                    //offer_adapter.setOnClickListener(Product_details_fragment.this::OnItemClick);
                    offerOpenRecyclerView.setAdapter(offer_adapter);
                } else {
                    //Toast.makeText(getActivity(), "no offer availabel", Toast.LENGTH_SHORT).show();
                    offerOpenRecyclerView.setVisibility(View.GONE);
                    noOffersText.setVisibility(View.VISIBLE);

                }

            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerAlert.dismiss();
            }
        });
    }

    @Override
    public void OnItemSell(int position) {
        get_product_response product = data.get(position);

    }
}
