package com.ALife.alife.view;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.adapter.get_product_offer_adapter;
import com.ALife.alife.adapter.get_product_type_adapter;
import com.ALife.alife.adapter.product_multiple_image_adapter;
import com.ALife.alife.adapter.product_offer_edit_adapter;
import com.ALife.alife.adapter.product_type_edit_adapter;
import com.ALife.alife.model.Imagetoserver_response;
import com.ALife.alife.model.add_product_offer_response;
import com.ALife.alife.model.add_product_type_response;
import com.ALife.alife.model.delete_category_response;
import com.ALife.alife.model.delete_product_image_response;
import com.ALife.alife.model.delete_type_count_response;
import com.ALife.alife.model.edit_type_count_response;
import com.ALife.alife.model.get_product_multiple_image_response;
import com.ALife.alife.model.get_product_offer_response;
import com.ALife.alife.model.get_product_response;
import com.ALife.alife.model.get_product_type_response;
import com.ALife.alife.model.product_offer_edit_delete_response;
import com.ALife.alife.model.update_product_response;
import com.ALife.alife.view.Shop.Shop_products_fragment;
import com.ALife.alife.viewmodel.Add_product_offer;
import com.ALife.alife.viewmodel.Add_product_type;
import com.ALife.alife.viewmodel.Delete_category;
import com.ALife.alife.viewmodel.Delete_product_image;
import com.ALife.alife.viewmodel.Delete_type_count;
import com.ALife.alife.viewmodel.Edit_type_count;
import com.ALife.alife.viewmodel.Get_product;
import com.ALife.alife.viewmodel.Get_product_multiple_image;
import com.ALife.alife.viewmodel.Get_product_offer;
import com.ALife.alife.viewmodel.Get_product_type;
import com.ALife.alife.viewmodel.Product_imagetoserver;
import com.ALife.alife.viewmodel.Product_offer_edit_delete;
import com.ALife.alife.viewmodel.Update_product;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.ALife.alife.R.layout.product_details_fragment;

public class Product_details_fragment<SharedViewModel> extends Fragment implements get_product_type_adapter.OnItemClickListener, product_type_edit_adapter.OnItemEditListener, product_type_edit_adapter.OnItemDeleteListener, product_multiple_image_adapter.OnItemClickListner1, product_offer_edit_adapter.OnItemOfferEditListener, product_offer_edit_adapter.OnItemOfferDeleteListener {
    Double count_item = 0.0;
    Double temp_count_item;
    Get_product_type get_product_type;
    Get_product_offer get_product_offer;
    Add_product_offer add_product_offer;
    Product_offer_edit_delete product_offer_edit_delete;
    List<get_product_type_response> data;
    List<get_product_offer_response> offer;
    List<get_product_offer_response> offerList;
    Delete_category delete_category;
    private String product_id, productimage, productname, buyprice, sellprofit, sellprice, productunit, stockamount, productdiscount, price_withdiscount, total_sell_price, total_price_with_discount, description, vaoture_no, vaoture_image;
    String product_name, buy_price, sell_profit, sell_price, unit, product_discount, product_sell_price_with_discount, product_stock_amount, productdescription, productvaoture_no, unit_product_sell_price, unit_product_sell_price_with_discount;
    private String category_id, shop_id, Category_unit;
    RecyclerView recyclerView, recyclerView_offer, offerView;
    private RecyclerView.LayoutManager layoutmanager, layoutManager_offer;
    ImageView productImage, deleteImage, editImage, vaotureImage;
    get_product_type_adapter adapter;
    get_product_offer_adapter offer_adapter;
    product_type_edit_adapter edit_adapter;
    product_offer_edit_adapter offer_edit_adapter;
    TextView productName, product_buyPrice, product_sellProfit, product_sellPrice, productDiscount, productUnit, productStock_amount, pricewithDiscount, unit_sellPrice, unit_discountPrice, product_description, product_vaoture_no, total_product_profit, total_product_discountprofit;
    TextInputLayout priceError, unitError, discountError, buyPriceError, profitError, amountError;
    TextInputEditText productText, priceText, unitText, discountText, buyPrice, profit, amount, description_product, product_vaoture;
    TextView priceTextwithDiscount_forone, priceTextforone;
    EditText priceTextwithDiscount;
    ImageView product_image, Image_vaoture, Add;
    TextView Save, submit;
    LinearLayout showImages, showImageLayout;
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 1;
    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    int check = 0, vaoture_check = 0, final_check = 0, final_vaoture_check = 0, multiple_image_check = 0;
    String imgdata, imgdata_vaoture;
    final int IMAGE_REQUEST_CODE = 999;
    private Uri filepath;
    private Bitmap bitmap, vaoture_bitmap;
    RecyclerView showImageRecycleView;
    private product_multiple_image_adapter image_adapter;
    List<get_product_multiple_image_response> image_data;

    public Product_details_fragment(String shop_id, String category_id, String product_id, String Category_unit) {
        this.shop_id = shop_id;
        this.category_id = category_id;
        this.product_id = product_id;
        this.Category_unit = Category_unit;
    }

    CheckBox checkBox;
    LinearLayout hideLayout;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main();

    }

    private void main() {
        checkConnection();

        Get_product get_product;
        get_product = new ViewModelProvider(getActivity()).get(Get_product.class);
        get_product.getsingle_product(product_id).observe(getViewLifecycleOwner(), new Observer<get_product_response>() {
            @Override
            public void onChanged(get_product_response get_product_response) {
                productname = get_product_response.getProduct_name();
                //Toast.makeText(getActivity(), productname, Toast.LENGTH_SHORT).show();
                buyprice = get_product_response.getBuy_price();
                sellprofit = get_product_response.getSell_profit();
                sellprice = get_product_response.getSelling_price();
                // total_sell_price = get_product_response.getTotal_selling_price();
                productunit = get_product_response.getProduct_unit();
                stockamount = get_product_response.getStock_amount();
                productdiscount = get_product_response.getProduct_offer();
                //price_withdiscount = get_product_response.getPrice_with_offer();
                //total_price_with_discount = get_product_response.getTotal_price_with_offer();
                productimage = get_product_response.getProduct_image();
                vaoture_image = get_product_response.getVaoture_image();
                description = get_product_response.getProduct_description();
                vaoture_no = get_product_response.getVaoture_no();
                Picasso.get().load(productimage).into(productImage);
                productImage.setClipToOutline(true);
                productName.setText(productname);
                product_buyPrice.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(buyprice))));

                //product_buyPrice.setText(buyprice);
                product_sellProfit.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(sellprofit))));
                // product_sellProfit.setText(sellprofit);
                total_product_profit.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(stockamount) * (Double.parseDouble(sellprice) - Double.parseDouble(buyprice)))));

                product_sellPrice.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(sellprice))));

                //product_sellPrice.setText(sellprice);
                unit_sellPrice.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(sellprice) * Double.parseDouble(stockamount))));
                // unit_sellPrice.setText(total_sell_price);
                productUnit.setText(productunit);
                productStock_amount.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(stockamount))));

                //productStock_amount.setText(stockamount);
                productDiscount.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(productdiscount))));

                //productDiscount.setText(productdiscount);
                Double priceWithDiscount = Double.parseDouble(sellprice) - Double.parseDouble(sellprice) * (Double.parseDouble(productdiscount) / 100);
                pricewithDiscount.setText(new DecimalFormat("##.##").format(priceWithDiscount));
                Double total_discount_price = priceWithDiscount * Double.parseDouble(stockamount);
                unit_discountPrice.setText(new DecimalFormat("##.##").format(total_discount_price));
                total_product_discountprofit.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(stockamount) * (priceWithDiscount - Double.parseDouble(buyprice)))));


                product_description.setText(description);

                product_vaoture_no.setText(vaoture_no);

                Picasso.get().load(vaoture_image).into(vaotureImage);
            }
        });
        //end get product info


        //start get all image
        LinearLayoutManager showImageLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        showImageRecycleView.setLayoutManager(showImageLayoutManager);

        Get_product_multiple_image get_product_multiple_image;
        get_product_multiple_image = new ViewModelProvider(getActivity()).get(Get_product_multiple_image.class);
        get_product_multiple_image.getData(product_id).observe(getViewLifecycleOwner(), new Observer<List<get_product_multiple_image_response>>() {
            @Override
            public void onChanged(List<get_product_multiple_image_response> get_product_multiple_image_responses) {
                image_data = new ArrayList<>();
                image_data = get_product_multiple_image_responses;
                image_adapter = new product_multiple_image_adapter(getActivity(), image_data);

                //adapter.setOnClickListener(Showdetails.this);
                image_adapter.setOnClickListener(Product_details_fragment.this::OnItemClick1);
                // adapter.setOnClick(Categories_fragment.this::OnItemEdit);

                showImageRecycleView.setAdapter(image_adapter);

            }
        });
        //end get all image


// end load fragment


        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked) {
                    // Do your coding
                    hideLayout.setVisibility(View.VISIBLE);
                    // showtypecount();
                } else {
                    // Do your coding
                    hideLayout.setVisibility(View.GONE);
                }
            }
        });
// show type
        get_product_type = new ViewModelProvider(getActivity()).get(Get_product_type.class);
        data = new ArrayList<>();
        get_product_type.getdata(product_id).observe(getViewLifecycleOwner(), new Observer<List<get_product_type_response>>() {
            @Override
            public void onChanged(List<get_product_type_response> get_product_type_responses) {
                adapter = new get_product_type_adapter(get_product_type_responses);
                data = get_product_type_responses;
                //adapter.setOnClickListener(Showdetails.this);
                // adapter.setOnClickListener(getActivity());
                adapter.setOnClickListener(Product_details_fragment.this::OnItemClick);
                recyclerView.setAdapter(adapter);
            }
        });

        // show offer
        get_product_offer = new ViewModelProvider(getActivity()).get(Get_product_offer.class);
        offer = new ArrayList<>();
        get_product_offer.getdata(product_id).observe(getViewLifecycleOwner(), new Observer<List<get_product_offer_response>>() {
            @Override
            public void onChanged(List<get_product_offer_response> get_product_offer_responses) {
                offer_adapter = new get_product_offer_adapter(get_product_offer_responses, productunit, sellprice);
                offer = get_product_offer_responses;
                offer_adapter.setOnClickListener(Product_details_fragment.this::OnItemClick);
                recyclerView_offer.setAdapter(offer_adapter);

            }
        });

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog alert = new Dialog(getActivity());
                alert.setContentView(R.layout.delete_alert);
                alert.show();

                TextView yesButton = alert.findViewById(R.id.yesButton);
                TextView noButton = alert.findViewById(R.id.noButton);

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete_category = new ViewModelProvider(getActivity()).get(Delete_category.class);
                        delete_category.getdelete_product(product_id).observe(getViewLifecycleOwner(), new Observer<delete_category_response>() {
                            @Override
                            public void onChanged(delete_category_response delete_category_response) {
                                if (delete_category_response.getMessage().equals("Product deleted successfully")) {

                                    Toast toast = Toast.makeText(getActivity(), delete_category_response.getMessage(), Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    alert.cancel();
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_products_fragment(shop_id, category_id, Category_unit)).commit();
                                    // refreshFragment();
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
        });


        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog alert = new Dialog(getActivity());
                alert.setContentView(R.layout.product_edit_form);
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alert.setCancelable(false);
                alert.show();

                get_product_type = new ViewModelProvider(getActivity()).get(Get_product_type.class);
                data = new ArrayList<>();
                recyclerView = alert.findViewById(R.id.recyclerViewID);
                recyclerView.setHasFixedSize(true);
                layoutmanager = new LinearLayoutManager(alert.getContext());
                recyclerView.setLayoutManager(layoutmanager);
                get_product_type.getdata(product_id).observe(getViewLifecycleOwner(), new Observer<List<get_product_type_response>>() {
                    @Override
                    public void onChanged(List<get_product_type_response> get_product_type_responses) {
                        adapter = new get_product_type_adapter(get_product_type_responses);
                        data = get_product_type_responses;
                        //adapter.setOnClickListener(Showdetails.this);
                        // adapter.setOnClickListener(getActivity());
                        adapter.setOnClickListener(Product_details_fragment.this::OnItemClick);
                        recyclerView.setAdapter(adapter);
                    }
                });
                product_image = (ImageView) alert.findViewById(R.id.productImage);
                Image_vaoture = (ImageView) alert.findViewById(R.id.vaotureImageID);
                ImageView closeButton = (ImageView) alert.findViewById(R.id.closeID);
                Save = (TextView) alert.findViewById(R.id.save_ID);
                priceError = (TextInputLayout) alert.findViewById(R.id.priceErrorID);
                unitError = (TextInputLayout) alert.findViewById(R.id.unitErrorID);
                discountError = (TextInputLayout) alert.findViewById(R.id.discountErrorID);
                amountError = (TextInputLayout) alert.findViewById(R.id.amountErrorID);
                productText = (TextInputEditText) alert.findViewById(R.id.nameTextID);

                priceText = (TextInputEditText) alert.findViewById(R.id.priceTextID);
                unitText = (TextInputEditText) alert.findViewById(R.id.unitTextID);
                discountText = (TextInputEditText) alert.findViewById(R.id.discountTextID);
                amount = (TextInputEditText) alert.findViewById(R.id.amountTextID);
                priceTextforone = (TextView) alert.findViewById(R.id.unitPriceID);
                priceTextwithDiscount = (EditText) alert.findViewById(R.id.price_after_discount);
                priceTextwithDiscount_forone = (TextView) alert.findViewById(R.id.unitPriceDiscountID);
                description_product = (TextInputEditText) alert.findViewById(R.id.descriptionTextID);
                product_vaoture = (TextInputEditText) alert.findViewById(R.id.vaotureNoId);

                buyPrice = (TextInputEditText) alert.findViewById(R.id.buyPriceTextID);
                profit = (TextInputEditText) alert.findViewById(R.id.profitTextID);
                Picasso.get().load(productimage).into(product_image);
                productText.setText(productname);
                amount.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(stockamount))));
                //amount.setText(stockamount);
                buyPrice.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(buyprice))));

                // buyPrice.setText(buyprice);
                unitText.setText(productunit);
                discountText.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(productdiscount))));
                //discountText.setText(productdiscount);
                priceText.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(sellprice))));

                //priceText.setText(sellprice);
                priceTextforone.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(sellprice) * Double.parseDouble(stockamount))));
                Double priceWithDiscount = Double.parseDouble(sellprice) - Double.parseDouble(sellprice) * (Double.parseDouble(productdiscount) / 100);

                //priceTextforone.setText(total_sell_price);
                priceTextwithDiscount.setText(String.valueOf(new DecimalFormat("##.##").format(priceWithDiscount)));

                //priceTextwithDiscount.setText(price_withdiscount);
                priceTextwithDiscount_forone.setText(String.valueOf(new DecimalFormat("##.##").format(priceWithDiscount * Double.parseDouble(stockamount))));

                // priceTextwithDiscount_forone.setText(total_price_with_discount);
                profit.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(sellprofit))));

                //profit.setText(sellprofit);
                description_product.setText(description);
                product_vaoture.setText(vaoture_no);
                if (!(TextUtils.isEmpty(vaoture_image))) {
                    Picasso.get().load(vaoture_image).into(Image_vaoture);
                }
                // edittext onchanged
                buyPrice.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        //priceText.getText().clear();
                        if (!(TextUtils.isEmpty(profit.getText().toString().trim()) || TextUtils.isEmpty(buyPrice.getText().toString().trim()) || TextUtils.isEmpty(amount.getText().toString().trim()))) {
                            Double buy_product_price = Double.parseDouble(buyPrice.getText().toString().trim());
                            Double product_sell_profit = Double.parseDouble(profit.getText().toString().trim());
                            Double selling_price_product = buy_product_price + buy_product_price * (product_sell_profit / 100);
                            // priceText.setText(String.valueOf(selling_price_product));
                            priceText.setText(String.valueOf(new DecimalFormat("##.##").format(selling_price_product)));

                            Double selling_price_all = Double.parseDouble(priceText.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim());
                            priceTextforone.setText(String.valueOf(new DecimalFormat("##.##").format(selling_price_all)));

                        } else if (TextUtils.isEmpty(profit.getText().toString().trim()) || TextUtils.isEmpty(buyPrice.getText().toString().trim())) {

                            priceText.setText("");
                            priceTextforone.setText("");
                            //sell_price_per_one.getText().clear();

                        }
                        if (!(TextUtils.isEmpty(discountText.getText().toString().trim()) || TextUtils.isEmpty(priceText.getText().toString().trim()) || TextUtils.isEmpty(amount.getText().toString().trim()))) {

                            double price_after_discount = Double.parseDouble(priceText.getText().toString().trim()) - (Double.parseDouble(priceText.getText().toString().trim()) * (Double.parseDouble(discountText.getText().toString().trim()) / 100));

                            String selling_price_per_unit_after_discount = String.valueOf(price_after_discount / Double.parseDouble(amount.getText().toString().trim()));
                            //priceTextwithDiscount.setText(String.valueOf(price_after_discount));
                            priceTextwithDiscount.setText(String.valueOf(new DecimalFormat("##.##").format(price_after_discount)));
                            Double selling_price_all_after_discount = Double.parseDouble(priceTextwithDiscount.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim());

                            priceTextwithDiscount_forone.setText(String.valueOf(new DecimalFormat("##.##").format(selling_price_all_after_discount)));


                            //sell_price_per_one.getText().clear();

                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                amount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!(TextUtils.isEmpty(buyPrice.getText().toString().trim()) || TextUtils.isEmpty(amount.getText().toString().trim()))) {
                            Double buy_product_price = Double.parseDouble(buyPrice.getText().toString().trim());
                            Double product_sell_profit = Double.parseDouble(profit.getText().toString().trim());
                            Double selling_price_product = buy_product_price + buy_product_price * (product_sell_profit / 100);
                            // priceText.setText(String.valueOf(selling_price_product));
                            priceText.setText(String.valueOf(new DecimalFormat("##.##").format(selling_price_product)));

                            Double selling_price_all = Double.parseDouble(priceText.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim());
                            priceTextforone.setText(String.valueOf(new DecimalFormat("##.##").format(selling_price_all)));

                        }
                        if (!(TextUtils.isEmpty(discountText.getText().toString().trim()) || TextUtils.isEmpty(priceText.getText().toString().trim()) || TextUtils.isEmpty(amount.getText().toString().trim()))) {

                            double price_after_discount = Double.parseDouble(priceText.getText().toString().trim()) - (Double.parseDouble(priceText.getText().toString().trim()) * (Double.parseDouble(discountText.getText().toString().trim()) / 100));

                            String selling_price_per_unit_after_discount = String.valueOf(price_after_discount / Double.parseDouble(amount.getText().toString().trim()));
                            //priceTextwithDiscount.setText(String.valueOf(price_after_discount));
                            priceTextwithDiscount.setText(String.valueOf(new DecimalFormat("##.##").format(price_after_discount)));
                            Double selling_price_all_after_discount = Double.parseDouble(priceTextwithDiscount.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim());

                            priceTextwithDiscount_forone.setText(String.valueOf(new DecimalFormat("##.##").format(selling_price_all_after_discount)));


                            //sell_price_per_one.getText().clear();

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
                        if (!(TextUtils.isEmpty(buyPrice.getText().toString().trim()) || (TextUtils.isEmpty(profit.getText().toString().trim())) || TextUtils.isEmpty(amount.getText().toString().trim()))) {
                            Double buy_product_price = Double.parseDouble(buyPrice.getText().toString().trim());
                            Double product_sell_profit = Double.parseDouble(profit.getText().toString().trim());
                            Double selling_price_product = buy_product_price + buy_product_price * (product_sell_profit / 100);

                            priceText.setText(String.valueOf(new DecimalFormat("##.##").format(selling_price_product)));

                            Double selling_price_all = Double.parseDouble(priceText.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim());
                            priceTextforone.setText(String.valueOf(new DecimalFormat("##.##").format(selling_price_all)));


                        } else if (TextUtils.isEmpty(profit.getText().toString().trim()) || TextUtils.isEmpty(buyPrice.getText().toString().trim())) {

                            priceText.setText("");

                        }
                        if (!(TextUtils.isEmpty(priceText.getText().toString().trim()) || TextUtils.isEmpty(amount.getText().toString().trim()))) {

                            Double selling_price_all = Double.parseDouble(priceText.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim());
                            priceTextforone.setText(String.valueOf(new DecimalFormat("##.##").format(selling_price_all)));

                        }
                        if (!(TextUtils.isEmpty(discountText.getText().toString().trim()) || TextUtils.isEmpty(priceText.getText().toString().trim()) || TextUtils.isEmpty(amount.getText().toString().trim()))) {

                            double price_after_discount = Double.parseDouble(priceText.getText().toString().trim()) - (Double.parseDouble(priceText.getText().toString().trim()) * (Double.parseDouble(discountText.getText().toString().trim()) / 100));

                            String selling_price_per_unit_after_discount = String.valueOf(price_after_discount / Double.parseDouble(amount.getText().toString().trim()));
                            //priceTextwithDiscount.setText(String.valueOf(price_after_discount));
                            priceTextwithDiscount.setText(String.valueOf(new DecimalFormat("##.##").format(price_after_discount)));
                            Double selling_price_all_after_discount = Double.parseDouble(priceTextwithDiscount.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim());

                            priceTextwithDiscount_forone.setText(String.valueOf(new DecimalFormat("##.##").format(selling_price_all_after_discount)));


                            //sell_price_per_one.getText().clear();

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
                            Double selling_price_all = Double.parseDouble(priceText.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim());
                            priceTextforone.setText(String.valueOf(new DecimalFormat("##.##").format(selling_price_all)));

                        }
                        if (!(TextUtils.isEmpty(discountText.getText().toString().trim()) || TextUtils.isEmpty(priceText.getText().toString().trim()) || TextUtils.isEmpty(amount.getText().toString().trim()))) {

                            double price_after_discount = Double.parseDouble(priceText.getText().toString().trim()) - (Double.parseDouble(priceText.getText().toString().trim()) * (Double.parseDouble(discountText.getText().toString().trim()) / 100));

                            String selling_price_per_unit_after_discount = String.valueOf(price_after_discount / Double.parseDouble(amount.getText().toString().trim()));
                            //priceTextwithDiscount.setText(String.valueOf(price_after_discount));
                            priceTextwithDiscount.setText(String.valueOf(new DecimalFormat("##.##").format(price_after_discount)));
                            Double selling_price_all_after_discount = Double.parseDouble(priceTextwithDiscount.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim());

                            priceTextwithDiscount_forone.setText(String.valueOf(new DecimalFormat("##.##").format(selling_price_all_after_discount)));


                            //sell_price_per_one.getText().clear();

                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                discountText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!(TextUtils.isEmpty(priceText.getText().toString().trim()) || TextUtils.isEmpty(discountText.getText().toString().trim()))) {
                            double price_after_discount = Double.parseDouble(priceText.getText().toString().trim()) - (Double.parseDouble(priceText.getText().toString().trim()) * (Double.parseDouble(discountText.getText().toString().trim()) / 100));

                            String selling_price_per_unit_after_discount = String.valueOf(price_after_discount / Double.parseDouble(amount.getText().toString().trim()));
                            //priceTextwithDiscount.setText(String.valueOf(price_after_discount));
                            priceTextwithDiscount.setText(String.valueOf(new DecimalFormat("##.##").format(price_after_discount)));

                        } else if (TextUtils.isEmpty(priceText.getText().toString().trim())) {

                            priceTextwithDiscount.getText().clear();
                        } else if (TextUtils.isEmpty(discountText.getText().toString().trim())) {
                            priceTextwithDiscount.setText(priceText.getText().toString().trim());
                        }
                        if (!(TextUtils.isEmpty(priceTextwithDiscount.getText().toString().trim()) || TextUtils.isEmpty(amount.getText().toString().trim()))) {
                            Double selling_price_all_after_discount = Double.parseDouble(priceTextwithDiscount.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim());

                            priceTextwithDiscount_forone.setText(String.valueOf(new DecimalFormat("##.##").format(selling_price_all_after_discount)));
                        }


                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                priceTextwithDiscount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!(TextUtils.isEmpty(priceTextwithDiscount.getText().toString().trim()) || TextUtils.isEmpty(amount.getText().toString().trim()))) {
                            Double selling_price_all_after_discount = Double.parseDouble(priceTextwithDiscount.getText().toString().trim()) * Double.parseDouble(amount.getText().toString().trim());

                            priceTextwithDiscount_forone.setText(String.valueOf(new DecimalFormat("##.##").format(selling_price_all_after_discount)));

                        } else {
                            priceTextwithDiscount_forone.setText("");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                //end of edittext onchanged


                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.cancel();
                    }
                });
                Save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo info = manager.getActiveNetworkInfo();

                        if (info == null) {
                            Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                        }else {

                            product_name = productText.getText().toString().trim();
                            buy_price = buyPrice.getText().toString().trim();
                            sell_profit = profit.getText().toString().trim();
                            sell_price = priceText.getText().toString().trim();
                            unit = unitText.getText().toString().trim();
                            product_discount = discountText.getText().toString().trim();
                            product_stock_amount = amount.getText().toString().trim();
                            productdescription = description_product.getText().toString().trim();
                            productvaoture_no = product_vaoture.getText().toString().trim();
                            product_sell_price_with_discount = priceTextwithDiscount.getText().toString().trim();
                            unit_product_sell_price = priceTextforone.getText().toString().trim();
                            unit_product_sell_price_with_discount = priceTextwithDiscount_forone.getText().toString().trim();

                            if (!(TextUtils.isEmpty(product_name) || TextUtils.isEmpty(sell_price) || TextUtils.isEmpty(unit) || TextUtils.isEmpty(product_stock_amount))) {


                                Dialog submit_alert = new Dialog(getActivity());
                                submit_alert.setContentView(R.layout.edit_type_count_list);
                                submit_alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                submit_alert.setCancelable(false);
                                submit_alert.show();

                                ImageView closeButton = (ImageView) submit_alert.findViewById(R.id.closeID);
                                Add = (ImageView) submit_alert.findViewById(R.id.addItemButtonID);
                                submit = (TextView) submit_alert.findViewById(R.id.submit_ID);
                                recyclerView = submit_alert.findViewById(R.id.recyclerViewID);
                                recyclerView.setHasFixedSize(true);
                                layoutmanager = new LinearLayoutManager(alert.getContext());
                                recyclerView.setLayoutManager(layoutmanager);
                                get_product_type = new ViewModelProvider(getActivity()).get(Get_product_type.class);
                                data = new ArrayList<>();
                                product_edit_alert();

                                Add.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Dialog alert = new Dialog(getActivity());
                                        alert.setContentView(R.layout.edit_type_form);
                                        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        alert.setCancelable(false);
                                        alert.show();

                                        ImageView closeButton = (ImageView) alert.findViewById(R.id.closeID);
                                        TextInputLayout typeError = (TextInputLayout) alert.findViewById(R.id.typeError);
                                        TextInputEditText Type = (TextInputEditText) alert.findViewById(R.id.typeID);
                                        TextInputLayout countError = (TextInputLayout) alert.findViewById(R.id.countError);
                                        TextInputEditText Count = (TextInputEditText) alert.findViewById(R.id.countID);
                                        TextView save = (TextView) alert.findViewById(R.id.save_ID);

                                        //temp_count_item=count_item-(Integer.parseInt(count));

                                        //edit

                                        save.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                String type = Type.getText().toString().trim();
                                                String count = Count.getText().toString().trim();

                                                typeError.setErrorEnabled(false);
                                                countError.setErrorEnabled(false);
                                                if (TextUtils.isEmpty(type)) {
                                                    typeError.setError(" ");
                                                } else if (TextUtils.isEmpty(count)) {
                                                    countError.setError(" ");
                                                } else {
                                                    temp_count_item = 0.0;
                                                    temp_count_item = count_item + Double.parseDouble(count);
                                                    if (!(temp_count_item > Integer.parseInt(amount.getText().toString().trim()))) {
                                                        Add_product_type add_product_type;
                                                        add_product_type = new ViewModelProvider(getActivity()).get(Add_product_type.class);
                                                        add_product_type.getmessage(type, count, product_id).observe(getViewLifecycleOwner(), new Observer<add_product_type_response>() {
                                                            @Override
                                                            public void onChanged(add_product_type_response add_product_type_response) {
                                                                if (add_product_type_response.getMessage().equals("yess")) {
                                                                    alert.cancel();
                                                                    product_edit_alert();
                                                                    // Toast.makeText(getActivity(), "yess", Toast.LENGTH_LONG).show();

                                                                } else {
                                                                    Toast.makeText(getActivity(), add_product_type_response.getMessage(), Toast.LENGTH_LONG).show();

                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        Toast toast = Toast.makeText(getActivity(), "enter type_count_properly", Toast.LENGTH_SHORT);
                                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                                        toast.show();
                                                    }
                                                }
                                            }

                                        });


                                        closeButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                alert.cancel();
                                                product_edit_alert();

                                            }
                                        });
                                    }
                                });
                                submit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Double total_item_count = 0.0;
                                        total_item_count = Double.valueOf(count_item);
                                        Double stock_item = Double.parseDouble(amount.getText().toString().trim());
                                        if (total_item_count > stock_item) {
                                            Toast.makeText(getActivity(), "total count not accurate", Toast.LENGTH_LONG).show();
                                        } else {
                                            //offer layout open
                                            submit_alert.dismiss();
                                            Dialog offerAlert = new Dialog(getActivity());
                                            offerAlert.setContentView(R.layout.edit_offer_countlist);
                                            offerAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            offerAlert.setCancelable(false);
                                            offerAlert.show();

                                            ImageView closeButton = (ImageView) offerAlert.findViewById(R.id.closeID);
                                            offerView = (RecyclerView) offerAlert.findViewById(R.id.offerViewID);
                                            ImageView offerAddButton = (ImageView) offerAlert.findViewById(R.id.addItemButtonID);
                                            TextView submitButton = (TextView) offerAlert.findViewById(R.id.submit_ID);

                                            offerView.setHasFixedSize(true);
                                            offerView.setLayoutManager(new LinearLayoutManager(offerAlert.getContext()));
                                            get_offer_for_edit();

                                            offerAddButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Dialog offerAddAlert = new Dialog(getActivity());
                                                    offerAddAlert.setContentView(R.layout.edit_offer_form);
                                                    offerAddAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                    offerAddAlert.setCancelable(false);
                                                    offerAddAlert.show();

                                                    ImageView closeButton = (ImageView) offerAddAlert.findViewById(R.id.closeID);
                                                    TextView price = (TextView) offerAddAlert.findViewById(R.id.priceID);
                                                    TextView saveButton = (TextView) offerAddAlert.findViewById(R.id.save_ID);

                                                    TextInputEditText packageText = (TextInputEditText) offerAddAlert.findViewById(R.id.packageTextID);
                                                    TextInputEditText percentageText = (TextInputEditText) offerAddAlert.findViewById(R.id.percentageTextID);

                                                    TextInputLayout packageError = (TextInputLayout) offerAddAlert.findViewById(R.id.packageErrorID);
                                                    TextInputLayout percentageError = (TextInputLayout) offerAddAlert.findViewById(R.id.percentageErrorID);
                                                    packageText.addTextChangedListener(new TextWatcher() {
                                                        @Override
                                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                        }

                                                        @Override
                                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                            if (!(packageText.getText().toString().trim().isEmpty() || percentageText.getText().toString().trim().isEmpty())) {
                                                                Double sell = Double.parseDouble(sell_price);
                                                                Double amount = Double.parseDouble(packageText.getText().toString().trim());
                                                                Double offer = Double.parseDouble(percentageText.getText().toString().trim());
                                                                Double net_price = amount * (sell - sell * (offer / 100));
                                                                price.setText(String.valueOf(net_price));
                                                            }
                                                            if (packageText.getText().toString().trim().isEmpty()) {
                                                                price.setText("");
                                                            }

                                                        }

                                                        @Override
                                                        public void afterTextChanged(Editable s) {

                                                        }
                                                    });
                                                    percentageText.addTextChangedListener(new TextWatcher() {
                                                        @Override
                                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                        }

                                                        @Override
                                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                            if (!(packageText.getText().toString().trim().isEmpty() || percentageText.getText().toString().trim().isEmpty())) {
                                                                Double sell = Double.parseDouble(sell_price);
                                                                Double amount = Double.parseDouble(packageText.getText().toString().trim());
                                                                Double offer = Double.parseDouble(percentageText.getText().toString().trim());
                                                                Double net_price = amount * (sell - sell * (offer / 100));
                                                                price.setText(String.valueOf(net_price));
                                                            }
                                                            if (percentageText.getText().toString().trim().isEmpty()) {
                                                                price.setText("");
                                                            }

                                                        }

                                                        @Override
                                                        public void afterTextChanged(Editable s) {

                                                        }
                                                    });

                                                    saveButton.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            String packageamount = packageText.getText().toString().trim();
                                                            String percentage_data = percentageText.getText().toString().trim();

                                                            packageError.setErrorEnabled(false);
                                                            percentageError.setErrorEnabled(false);

                                                            if (TextUtils.isEmpty(packageamount) || (TextUtils.isEmpty(percentage_data))) {
                                                                if (TextUtils.isEmpty(packageamount)) {
                                                                    packageError.setError(" ");
                                                                } else if (TextUtils.isEmpty(percentage_data)) {
                                                                    percentageError.setError(" ");
                                                                }
                                                            } else {
                                                                add_product_offer = new ViewModelProvider(getActivity()).get(Add_product_offer.class);
                                                                add_product_offer.getmessage(packageamount, percentage_data, product_id).observe(getViewLifecycleOwner(), new Observer<add_product_offer_response>() {
                                                                    @Override
                                                                    public void onChanged(add_product_offer_response add_product_offer_response) {
                                                                        if (add_product_offer_response.getMessage().equals("yess")) {
                                                                            offerAddAlert.dismiss();
                                                                            get_offer_for_edit();
                                                                        } else {
                                                                            Toast.makeText(getActivity(), add_product_offer_response.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    });

                                                    closeButton.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            offerAddAlert.dismiss();
                                                        }
                                                    });
                                                }
                                            });

                                            closeButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    offerAlert.dismiss();
                                                }
                                            });
                                            submitButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (TextUtils.isEmpty(buy_price)) {
                                                        buy_price = "0";
                                                    }
                                                    if (TextUtils.isEmpty(sell_profit)) {
                                                        sell_profit = "0";
                                                    }
                                                    if (TextUtils.isEmpty(product_discount)) {
                                                        product_discount = "0";
                                                    }
                                                    if (TextUtils.isEmpty(productdescription)) {
                                                        productdescription = "blank";
                                                    }
                                                    if (TextUtils.isEmpty(productvaoture_no)) {
                                                        productvaoture_no = "blank";
                                                    }
                                                    if (TextUtils.isEmpty(product_sell_price_with_discount)) {
                                                        product_sell_price_with_discount = "0";
                                                    }
                                                    if (TextUtils.isEmpty(unit_product_sell_price_with_discount)) {
                                                        unit_product_sell_price_with_discount = "0";
                                                    }
                                                    int token = 126;
                                                    if (final_vaoture_check == 0 && final_check == 0) {
                                                        imgdata = "";
                                                        imgdata_vaoture = "";
                                                        token = 0;
                                                        //both product image and vaoture image will be not edited
                                                    } else if (final_vaoture_check == 0 && final_check == 1) {
                                                        imgdata = imgToString(bitmap);
                                                        imgdata_vaoture = "x";
                                                        token = 2;
                                                        //only product image will be edited
                                                    } else if (final_vaoture_check == 1 && final_check == 0) {
                                                        imgdata = "x";
                                                        imgdata_vaoture = imgToString(vaoture_bitmap);
                                                        token = 1;
                                                        //only vaoture image will be edited
                                                    } else if (final_vaoture_check == 1 && final_check == 1) {
                                                        imgdata = imgToString(bitmap);
                                                        imgdata_vaoture = imgToString(vaoture_bitmap);
                                                        token = 3;
                                                        //both product image and vaoture image will be edited
                                                    }
                                                    Update_product update_product = new ViewModelProvider(getActivity()).get(Update_product.class);
                                                    update_product.getData(product_id, product_name, unit, sell_price, buy_price, product_discount, product_sell_price_with_discount, sell_profit, product_stock_amount, imgdata, unit_product_sell_price, unit_product_sell_price_with_discount, productdescription, productvaoture_no, imgdata_vaoture, token).observe(getViewLifecycleOwner(), new Observer<update_product_response>() {
                                                        @Override
                                                        public void onChanged(update_product_response update_product_response) {
                                                            if (update_product_response.getMessage().equals("Product edited successfully")) {
                                                                check = 0;
                                                                vaoture_check = 0;
                                                                final_check = 0;
                                                                final_vaoture_check = 0;
                                                                offerAlert.dismiss();
                                                                submit_alert.cancel();
                                                                alert.cancel();
                                                                refreshFragment();
                                                                Toast.makeText(getActivity(), update_product_response.getMessage(), Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getActivity(), update_product_response.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                    });
                                                }
                                            });
                                       /* if (TextUtils.isEmpty(buy_price)) {
                                            buy_price = "blank";
                                        }
                                        if (TextUtils.isEmpty(sell_profit)) {
                                            sell_profit = "blank";
                                        }
                                        if (TextUtils.isEmpty(product_discount)) {
                                            product_discount = "blank";
                                        }
                                        if (TextUtils.isEmpty(productdescription)) {
                                            productdescription = "blank";
                                        }
                                        if (TextUtils.isEmpty(productvaoture_no)) {
                                            productvaoture_no = "blank";
                                        }
                                        if (TextUtils.isEmpty(product_sell_price_with_discount)) {
                                            product_sell_price_with_discount = "blank";
                                        }
                                        if (TextUtils.isEmpty(unit_product_sell_price_with_discount)) {
                                            unit_product_sell_price_with_discount = "blank";
                                        }
                                        int token = 126;
                                        if (final_vaoture_check == 0 && final_check == 0) {
                                            imgdata = "x";
                                            imgdata_vaoture = "x";
                                            token = 0;
                                            //both product image and vaoture image will be not edited
                                        } else if (final_vaoture_check == 0 && final_check == 1) {
                                            imgdata = imgToString(bitmap);
                                            imgdata_vaoture = "x";
                                            token = 2;
                                            //only product image will be edited
                                        } else if (final_vaoture_check == 1 && final_check == 0) {
                                            imgdata = "x";
                                            imgdata_vaoture = imgToString(vaoture_bitmap);
                                            token = 1;
                                            //only vaoture image will be edited
                                        } else if (final_vaoture_check == 1 && final_check == 1) {
                                            imgdata = imgToString(bitmap);
                                            imgdata_vaoture = imgToString(vaoture_bitmap);
                                            token = 3;
                                            //both product image and vaoture image will be edited
                                        }
                                        Update_product update_product = new ViewModelProvider(getActivity()).get(Update_product.class);
                                        update_product.getData(product_id, product_name, unit, sell_price, buy_price, product_discount, product_sell_price_with_discount, sell_profit, product_stock_amount, imgdata, unit_product_sell_price, unit_product_sell_price_with_discount, productdescription, productvaoture_no, imgdata_vaoture, token).observe(getViewLifecycleOwner(), new Observer<update_product_response>() {
                                            @Override
                                            public void onChanged(update_product_response update_product_response) {
                                                if (update_product_response.getMessage().equals("Product edited successfully")) {
                                                    check = 0;
                                                    vaoture_check = 0;
                                                    final_check = 0;
                                                    final_vaoture_check = 0;
                                                    submit_alert.cancel();
                                                    alert.cancel();
                                                    refreshFragment();
                                                    Toast.makeText(getActivity(), update_product_response.getMessage(), Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(getActivity(), update_product_response.getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });*/


                                        }
                                    }
                                });
                                closeButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        submit_alert.cancel();
                                    }
                                });

                            } else {
                                // error show code
                            }

                        }


                    }
                });

                product_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                        imageselect();
                    }
                });
                Image_vaoture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                        vaoture_imageselect();
                    }
                });


            }
        });
        //show all images
        final boolean[] showImageState = {false};
        showImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showImageState[0] == false) {
                    showImageLayout.setVisibility(View.GONE);
                    showImageState[0] = true;
                } else {
                    showImageLayout.setVisibility(View.VISIBLE);
                    showImageState[0] = false;
                }

            }
        });

    }

    private void get_offer_for_edit() {
        get_product_offer.getdata(product_id).observe(getViewLifecycleOwner(), new Observer<List<get_product_offer_response>>() {
            @Override
            public void onChanged(List<get_product_offer_response> get_product_offer_responses) {
                offerList = new ArrayList<>();
                offerList = get_product_offer_responses;
                offer_edit_adapter = new product_offer_edit_adapter(offerList, sell_price);
                offer_edit_adapter.setOnClickListener(Product_details_fragment.this::OnItemOfferEdit, Product_details_fragment.this::OnItemOfferDelete);
                offerView.setAdapter(offer_edit_adapter);

            }
        });
    }

    public void product_edit_alert() {

        get_product_type.getdata(product_id).observe(getViewLifecycleOwner(), new Observer<List<get_product_type_response>>() {
            @Override
            public void onChanged(List<get_product_type_response> get_product_type_responses) {
                edit_adapter = new product_type_edit_adapter(get_product_type_responses);
                data = get_product_type_responses;

                edit_adapter.setOnClickListener(Product_details_fragment.this::OnItemClick, Product_details_fragment.this::OnItemEdit, Product_details_fragment.this::OnItemDelete);
                recyclerView.setAdapter(edit_adapter);
                count_item = 0.0;
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getCount().equals("0.0")) {
                        data.get(i).setCount("0");
                    }
                    count_item += Double.parseDouble(data.get(i).getCount());
                }


            }


        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(product_details_fragment, container, false);
        checkConnection();

        recyclerView = view.findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        layoutmanager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutmanager);
        layoutManager_offer = new LinearLayoutManager(view.getContext());
        recyclerView_offer = view.findViewById(R.id.recyclerViewOfferID);
        recyclerView_offer.setHasFixedSize(true);
        recyclerView_offer.setLayoutManager(layoutManager_offer);
        checkBox = (CheckBox) view.findViewById(R.id.showDetailsID);
        hideLayout = (LinearLayout) view.findViewById(R.id.hideLayoutID);
        productImage = (ImageView) view.findViewById(R.id.productImage);
        productName = (TextView) view.findViewById(R.id.productlabelID);
        product_buyPrice = (TextView) view.findViewById(R.id.buypriceID);
        product_sellProfit = (TextView) view.findViewById(R.id.profitID);
        product_sellPrice = (TextView) view.findViewById(R.id.priceID);
        productDiscount = (TextView) view.findViewById(R.id.discountID);
        productUnit = (TextView) view.findViewById(R.id.unitID);
        productStock_amount = (TextView) view.findViewById(R.id.amountID);
        pricewithDiscount = (TextView) view.findViewById(R.id.afterDiscountID);
        productName = (TextView) view.findViewById(R.id.productlabelID);
        unit_sellPrice = (TextView) view.findViewById(R.id.unitPriceID);
        unit_discountPrice = (TextView) view.findViewById(R.id.SellinDiscountOneID);
        total_product_profit = (TextView) view.findViewById(R.id.totalProfitID);
        total_product_discountprofit = (TextView) view.findViewById(R.id.totalProfitafterDiscountID);
        product_description = (TextView) view.findViewById(R.id.descriptionID);
        product_vaoture_no = (TextView) view.findViewById(R.id.vaoture_noID);
        vaotureImage = (ImageView) view.findViewById(R.id.vaoture_ImageID);
        deleteImage = (ImageView) view.findViewById(R.id.deleteButtonID);
        editImage = (ImageView) view.findViewById(R.id.editButtonID);
        LinearLayout addMoreImageButton = (LinearLayout) view.findViewById(R.id.addMoreImageID);

        addMoreImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Add More Image", Toast.LENGTH_SHORT).show();
                multipleImageSelect();
            }
        });

        showImages = (LinearLayout) view.findViewById(R.id.showImagesID);
        showImageLayout = (LinearLayout) view.findViewById(R.id.showImageLayoutID);
        showImageRecycleView = (RecyclerView) view.findViewById(R.id.showMoreImageRecycleviewID);
        showImageRecycleView.setHasFixedSize(true);


        return view;
    }

    private void multipleImageSelect() {
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

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Image"), IMAGE_REQUEST_CODE);

                } else if (items[i].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void showtypecount() {
        get_product_type = new ViewModelProvider(getActivity()).get(Get_product_type.class);
        data = new ArrayList<>();
        get_product_type.getdata(product_id).observe(getViewLifecycleOwner(), new Observer<List<get_product_type_response>>() {
            @Override
            public void onChanged(List<get_product_type_response> get_product_type_responses) {
                adapter = new get_product_type_adapter(get_product_type_responses);
                data = get_product_type_responses;
                adapter.setOnClickListener(Product_details_fragment.this::OnItemClick);
                recyclerView.setAdapter(adapter);
            }
        });

    }


    @Override
    public void OnItemClick(int position) {

    }

    @Override
    public void OnItemEdit(int position) {
        get_product_type_response clickItem = data.get(position);
        String type = clickItem.getType();
        String count = clickItem.getCount();
        String id = clickItem.getId();
        Dialog alert = new Dialog(getActivity());
        alert.setContentView(R.layout.edit_type_form);
        alert.show();
        ImageView closeButton = (ImageView) alert.findViewById(R.id.closeID);
        TextInputLayout typeError = (TextInputLayout) alert.findViewById(R.id.typeError);
        TextInputEditText Type = (TextInputEditText) alert.findViewById(R.id.typeID);
        TextInputLayout countError = (TextInputLayout) alert.findViewById(R.id.countError);
        TextInputEditText Count = (TextInputEditText) alert.findViewById(R.id.countID);
        TextView save = (TextView) alert.findViewById(R.id.save_ID);
        Type.setText(type);
        Count.setText(count);
        temp_count_item = count_item - (Double.parseDouble(count));

        //edit

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String type = Type.getText().toString().trim();
                String count = Count.getText().toString().trim();
                temp_count_item += Double.parseDouble(count);
                if (!(temp_count_item > Double.parseDouble(amount.getText().toString().trim()))) {
                    typeError.setErrorEnabled(false);
                    countError.setErrorEnabled(false);
                    if (TextUtils.isEmpty(type)) {
                        typeError.setError(" ");
                    } else if (TextUtils.isEmpty(count)) {
                        countError.setError(" ");
                    } else {
                        Edit_type_count edit_type_count;
                        edit_type_count = new ViewModelProvider(getActivity()).get(Edit_type_count.class);
                        edit_type_count.getmessage(type, count, id).observe(getViewLifecycleOwner(), new Observer<edit_type_count_response>() {
                            @Override
                            public void onChanged(edit_type_count_response type_count_edit_response) {
                                if (type_count_edit_response.getMessage().equals("edited successfully")) {

                                    Toast toast = Toast.makeText(getActivity(), type_count_edit_response.getMessage(), Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    alert.cancel();
                                    product_edit_alert();
                                } else {
                                    Toast toast = Toast.makeText(getActivity(), type_count_edit_response.getMessage(), Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                            }
                        });
                    }

                } else {
                    temp_count_item -= Double.parseDouble(count);
                    Toast toast = Toast.makeText(getActivity(), "enter type_count_properly", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }

        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.cancel();
            }
        });
    }

    @Override
    public void OnItemDelete(int position) {
        get_product_type_response clickItem = data.get(position);

        String type_count_id = clickItem.getId();

        Dialog alert = new Dialog(getActivity());
        alert.setContentView(R.layout.delete_alert);
        alert.show();

        TextView yesButton = alert.findViewById(R.id.yesButton);
        TextView noButton = alert.findViewById(R.id.noButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete_type_count delete_type_count = new ViewModelProvider(getActivity()).get(Delete_type_count.class);
                delete_type_count.getdata(type_count_id).observe(getViewLifecycleOwner(), new Observer<delete_type_count_response>() {
                    @Override
                    public void onChanged(delete_type_count_response delete_type_count_response) {
                        if (delete_type_count_response.getMessage().equals("deleted successfully")) {

                            Toast toast = Toast.makeText(getActivity(), delete_type_count_response.getMessage(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            alert.cancel();
                            product_edit_alert();
                        } else {
                            Toast toast = Toast.makeText(getActivity(), delete_type_count_response.getMessage(), Toast.LENGTH_SHORT);
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

    public void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
        //adapter.notifyDataSetChanged();
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
                    product_image.setImageBitmap(bitmap);
                }
                if (vaoture_check == 1) {
                    final_vaoture_check = 1;
                    vaoture_check = 0;
                    vaoture_bitmap = (Bitmap) bundle.get("data");
                    vaotureImage.setImageBitmap(vaoture_bitmap);
                }

            } else if (requestCode == IMAGE_REQUEST_CODE) {
                if (check == 0 && vaoture_check == 0) {
                    if (data.getClipData() != null) {
                        Uri imageUri;
                        int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                        for (int i = 0; i < count; i++) {
                            imageUri = data.getClipData().getItemAt(i).getUri();
                            Bitmap bitmap_multiple_image;
                            try {
                                InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                                bitmap_multiple_image = BitmapFactory.decodeStream(inputStream);
                                String image_string = imgToString(bitmap_multiple_image);
                                sendimagetoserver(image_string);

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            //do something with the image (save it to some directory or whatever you need to do with it here)
                        }
                        refreshFragment();
                    } else if (data.getData() != null) {
                        Uri imageUri;
                        imageUri = data.getData();
                        Bitmap bitmap_multiple_image;
                        try {
                            InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                            bitmap_multiple_image = BitmapFactory.decodeStream(inputStream);
                            String image_string = imgToString(bitmap_multiple_image);
                            sendimagetoserver(image_string);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        refreshFragment();

                    }
                } else {
                    filepath = data.getData();
                    try {
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);

                        if (check == 1) {
                            check = 0;
                            final_check = 1;
                            bitmap = BitmapFactory.decodeStream(inputStream);
                            product_image.setImageBitmap(bitmap);
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

    String message;

    public void sendimagetoserver(String image) {

        Product_imagetoserver product_imagetoserver;
        product_imagetoserver = new ViewModelProvider(getActivity()).get(Product_imagetoserver.class);
        product_imagetoserver.getData(image, product_id).observe(getViewLifecycleOwner(), new Observer<Imagetoserver_response>() {
            @Override
            public void onChanged(Imagetoserver_response imagetoserver_response) {
                message = imagetoserver_response.getMessage();

            }
        });

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

                    //refreshFragment();
                }
            });

        }
    }


    @Override
    public void OnItemClick1(int position) {
        get_product_multiple_image_response item = image_data.get(position);
        String image = item.getImage();

        Dialog imageDialog = new Dialog(getActivity());
        imageDialog.setContentView(R.layout.multiple_image_show_alert);
        imageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        imageDialog.show();
        imageDialog.setCancelable(false);

        ImageView individualImage = (ImageView) imageDialog.findViewById(R.id.individualImageID);
        ImageView closeButton = (ImageView) imageDialog.findViewById(R.id.closeID);
        ImageView deleteImage = (ImageView) imageDialog.findViewById(R.id.individualDeleteID);
        Picasso.get().load(image).into(individualImage);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDialog.cancel();
            }
        });

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete_product_image delete_product_image;
                delete_product_image = new ViewModelProvider(getActivity()).get(Delete_product_image.class);
                delete_product_image.getData(item.getId()).observe(getViewLifecycleOwner(), new Observer<delete_product_image_response>() {
                    @Override
                    public void onChanged(delete_product_image_response delete_product_image_response) {
                        if (delete_product_image_response.getMessage().equals("Image deleted successfully")) {
                            imageDialog.cancel();
                            Toast.makeText(getActivity(), delete_product_image_response.getMessage(), Toast.LENGTH_SHORT).show();
                            main();
                            // refreshFragment();
                        } else {
                            Toast.makeText(getActivity(), delete_product_image_response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    @Override
    public void OnItemOfferEdit(int position) {
        get_product_offer_response offer = offerList.get(position);
        String offer_id = offer.getId();
        String amount = offer.getAmount();
        String percentage = offer.getPrice();
        Dialog offerEditAlert = new Dialog(getActivity());
        offerEditAlert.setContentView(R.layout.edit_offer_form);
        offerEditAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        offerEditAlert.setCancelable(false);
        offerEditAlert.show();

        ImageView closeButton = (ImageView) offerEditAlert.findViewById(R.id.closeID);
        TextView price = (TextView) offerEditAlert.findViewById(R.id.priceID);
        TextView saveButton = (TextView) offerEditAlert.findViewById(R.id.save_ID);

        TextInputEditText packageText = (TextInputEditText) offerEditAlert.findViewById(R.id.packageTextID);
        TextInputEditText percentageText = (TextInputEditText) offerEditAlert.findViewById(R.id.percentageTextID);

        TextInputLayout packageError = (TextInputLayout) offerEditAlert.findViewById(R.id.packageErrorID);
        TextInputLayout percentageError = (TextInputLayout) offerEditAlert.findViewById(R.id.percentageErrorID);
        packageText.setText(amount);
        percentageText.setText(percentage);
        Double sell = Double.parseDouble(sell_price);
        Double package_amount = Double.parseDouble(packageText.getText().toString().trim());
        Double offer_percentage = Double.parseDouble(percentageText.getText().toString().trim());
        Double net_price = package_amount * (sell - sell * (offer_percentage / 100));
        price.setText(String.valueOf(net_price));
        packageText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(packageText.getText().toString().trim().isEmpty() || percentageText.getText().toString().trim().isEmpty())) {
                    Double sell = Double.parseDouble(sell_price);
                    Double amount = Double.parseDouble(packageText.getText().toString().trim());
                    Double offer = Double.parseDouble(percentageText.getText().toString().trim());
                    Double net_price = amount * (sell - sell * (offer / 100));
                    price.setText(String.valueOf(net_price));
                }
                if (packageText.getText().toString().trim().isEmpty()) {
                    price.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        percentageText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(packageText.getText().toString().trim().isEmpty() || percentageText.getText().toString().trim().isEmpty())) {
                    Double sell = Double.parseDouble(sell_price);
                    Double amount = Double.parseDouble(packageText.getText().toString().trim());
                    Double offer = Double.parseDouble(percentageText.getText().toString().trim());
                    Double net_price = amount * (sell - sell * (offer / 100));
                    price.setText(String.valueOf(net_price));
                }
                if (percentageText.getText().toString().trim().isEmpty()) {
                    price.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageamount = packageText.getText().toString().trim();
                String percentage_data = percentageText.getText().toString().trim();

                packageError.setErrorEnabled(false);
                percentageError.setErrorEnabled(false);

                if (TextUtils.isEmpty(packageamount) || (TextUtils.isEmpty(percentage_data))) {
                    if (TextUtils.isEmpty(packageamount)) {
                        packageError.setError(" ");
                    } else if (TextUtils.isEmpty(percentage_data)) {
                        percentageError.setError(" ");
                    }
                } else {
                    product_offer_edit_delete = new ViewModelProvider(getActivity()).get(Product_offer_edit_delete.class);
                    product_offer_edit_delete.getEditResponse(offer_id, packageText.getText().toString().trim(), percentageText.getText().toString().trim()).observe(getViewLifecycleOwner(), new Observer<product_offer_edit_delete_response>() {
                        @Override
                        public void onChanged(product_offer_edit_delete_response product_offer_edit_delete_response) {
                            if (product_offer_edit_delete_response.getMessage().equals("edited successfully")) {
                                offerEditAlert.dismiss();
                                get_offer_for_edit();
                            } else {
                                Toast.makeText(getActivity(), product_offer_edit_delete_response.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerEditAlert.dismiss();
            }
        });
    }


    @Override
    public void OnItemOfferDelete(int position) {
        get_product_offer_response offer = offerList.get(position);
        String offer_id = offer.getId();

        Dialog alert = new Dialog(getActivity());
        alert.setContentView(R.layout.delete_alert);
        alert.show();

        TextView yesButton = alert.findViewById(R.id.yesButton);
        TextView noButton = alert.findViewById(R.id.noButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_offer_edit_delete = new ViewModelProvider(getActivity()).get(Product_offer_edit_delete.class);

                product_offer_edit_delete.getDeleteResponse(offer_id).observe(getViewLifecycleOwner(), new Observer<product_offer_edit_delete_response>() {
                    @Override
                    public void onChanged(product_offer_edit_delete_response product_offer_edit_delete_response) {
                        if (product_offer_edit_delete_response.getMessage().equals("deleted successfully")) {

                            alert.dismiss();
                            get_offer_for_edit();
                        } else {
                            Toast.makeText(getActivity(), product_offer_edit_delete_response.getMessage(), Toast.LENGTH_SHORT).show();
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
}
