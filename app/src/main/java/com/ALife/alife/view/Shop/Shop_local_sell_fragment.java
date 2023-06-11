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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.adapter.Shop_local_sell_image_list_adapter;
import com.ALife.alife.model.add_payment_transaction_response;
import com.ALife.alife.model.add_product_sell_response;
import com.ALife.alife.model.add_sell_payment_cash_response;
import com.ALife.alife.model.add_shop_due_customer_response;
import com.ALife.alife.model.customer_exist_check_response;
import com.ALife.alife.model.local_sell.LocalSell_property;
import com.ALife.alife.model.local_sell.add_local_sell_details_response;
import com.ALife.alife.model.local_sell.add_local_sell_image_response;
import com.ALife.alife.model.local_sell.get_local_sell_product_response;
import com.ALife.alife.model.push_notification_response;
import com.ALife.alife.model.shop_due_customer_response;
import com.ALife.alife.model.shop_profile_response;
import com.ALife.alife.viewmodel.Customer_exist_check;
import com.ALife.alife.viewmodel.Customer_registration;
import com.ALife.alife.viewmodel.Local_sell.Add_local_sell;
import com.ALife.alife.viewmodel.Local_sell.Get_local_sell;
import com.ALife.alife.viewmodel.Product_sell;
import com.ALife.alife.viewmodel.Product_sell_payment;
import com.ALife.alife.viewmodel.Push_notification;
import com.ALife.alife.viewmodel.Shop_customer;
import com.ALife.alife.viewmodel.Shop_profile;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Shop_local_sell_fragment extends Fragment implements AdapterView.OnItemSelectedListener {

    String shopID;

    String productName, productDetails, productPrice, paidPrice, phone, buyPrice;
    TextInputEditText productNameText, productPriceText, paidPriceText, phoneText, buyPriceText;
    TextInputLayout productNameError, productDetailsError, paidPriceError, phoneError;
    EditText productDetailsText;
    TextView choseImageButton, select_product, select_phone, profitText;
    LinearLayout bar_code_search;
    AppCompatButton sellButton;

    ImageView productImage;
    private Uri filepath;
    private Bitmap bitmap;
    Dialog loader;
    Shop_customer shop_customer;
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
    // Spinner productSpinner;
    //String[] products = {"Mouse", "Pad"};
    Get_local_sell get_local_sell;
    FragmentManager fragmentManager;
    private List<get_local_sell_product_response> productList;
    Shop_profile shop_profile;
    Bitmap bitmapPDF;
    LinearLayout historyButton;
    RecyclerView imageRecyclerView;
    private List<String> imageList;
    private int loopItem;
    LinearLayout selectImage, listImage;
    Shop_local_sell_image_list_adapter adapter;

    public Shop_local_sell_fragment(String shopID, int state) {
        this.shopID = shopID;
        this.state = state;

    }

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
        phoneText = view.findViewById(R.id.phoneTextID);
        selectImage = view.findViewById(R.id.selectImageId);
        listImage = view.findViewById(R.id.listImageId);

        historyButton = view.findViewById(R.id.historyButtonID);
//set field autimetically
        if (state == 1) {
            LocalSell_property.Product_description = "";
            LocalSell_property.SellProfit = "";
            LocalSell_property.Product_buePrice = "";
            LocalSell_property.Product_price = "";
            LocalSell_property.Customer_phone = "";
            LocalSell_property.Product_image = new ArrayList<>();
            imageList = new ArrayList<>();

        } else if (state == 2) {
            check = 2;
            imageList = new ArrayList<>();
            // Toast.makeText(getActivity(), String.valueOf(LocalSell_property.Product_image.size()), Toast.LENGTH_SHORT).show();
            for (int i = 0; i < LocalSell_property.Product_image.size(); i++) {
                imageList.add(LocalSell_property.Product_image.get(i));
            }
        }
        productDetailsText.setText(LocalSell_property.Product_description);
        productPriceText.setText(LocalSell_property.Product_price);
        buyPriceText.setText(LocalSell_property.Product_buePrice);
        profitText.setText(LocalSell_property.SellProfit);
        phoneText.setText(LocalSell_property.Customer_phone);

        productPriceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(productPriceText.getText().toString().trim()) && !TextUtils.isEmpty(buyPriceText.getText().toString().trim())) {
                    if (Double.parseDouble(buyPriceText.getText().toString().trim()) == 0.0) {
                        profitText.setText("0");
                    } else {
                        Double profit = Double.parseDouble(productPriceText.getText().toString().trim()) - Double.parseDouble(buyPriceText.getText().toString().trim());
                        profitText.setText(String.valueOf(new DecimalFormat("##.##").format(profit)));
                    }


                } else {
                    profitText.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        buyPriceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(productPriceText.getText().toString().trim()) && !TextUtils.isEmpty(buyPriceText.getText().toString().trim())) {
                    if (Double.parseDouble(buyPriceText.getText().toString().trim()) == 0.0) {
                        profitText.setText("0");
                    } else {
                        Double profit = Double.parseDouble(productPriceText.getText().toString().trim()) - Double.parseDouble(buyPriceText.getText().toString().trim());
                        profitText.setText(String.valueOf(new DecimalFormat("##.##").format(profit)));
                    }


                } else {
                    profitText.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //productNameError = view.findViewById(R.id.productNameErrorID);
        //productDetailsError = view.findViewById(R.id.productDetailsErrorID);
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

        if (state == 1) {
            listImage.setVisibility(View.GONE);
            selectImage.setVisibility(View.VISIBLE);
        } else if (state == 2) {
            selectImage.setVisibility(View.GONE);
            listImage.setVisibility(View.VISIBLE);
            adapter = new Shop_local_sell_image_list_adapter(imageList);
            imageRecyclerView.setAdapter(adapter);


        } else if (state == 3) {
            if (check == 2) {
                imageList = new ArrayList<>();
                Toast.makeText(getActivity(), String.valueOf(LocalSell_property.Product_image.size()), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < LocalSell_property.Product_image.size(); i++) {
                    imageList.add(LocalSell_property.Product_image.get(i));
                }
                selectImage.setVisibility(View.GONE);
                listImage.setVisibility(View.VISIBLE);
                adapter = new Shop_local_sell_image_list_adapter(imageList);
                imageRecyclerView.setAdapter(adapter);

            }
        }
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
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_local_sell_select_product_fragment(shopID)).addToBackStack(null).commit();

            }
        });
        select_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_local_sell_select_customer_phone_fragment(shopID)).addToBackStack(null).commit();


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
                productDetails = productDetailsText.getText().toString().trim();
                productPrice = productPriceText.getText().toString().trim();
                paidPrice = paidPriceText.getText().toString().trim();
                phone = phoneText.getText().toString().trim();
                buyPrice = buyPriceText.getText().toString().trim();


                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getActivity(), "Empty field", Toast.LENGTH_SHORT).show();
                } else {


                    //Toast.makeText(getActivity(), productDetails, Toast.LENGTH_SHORT).show();
                    if (!(TextUtils.isEmpty(productPrice) && TextUtils.isEmpty(paidPrice))) {
                        loader.show();
                        if (TextUtils.isEmpty(productPrice)) {

                            productPrice = "0";
                        } else if (TextUtils.isEmpty(paidPrice)) {

                            paidPrice = "0";
                        }
                        if (productPrice.equals(".")) {
                            productPrice = "0";
                        }
                        if (paidPrice.equals(".")) {
                            paidPrice = "0";
                        }
                        if (TextUtils.isEmpty(productDetails)) {
                            productDetails = "";
                        }
                        if (TextUtils.isEmpty(buyPrice)) {
                            buyPrice = "0";
                        }
                        customer_exist_check = new ViewModelProvider(getActivity()).get(Customer_exist_check.class);
                        shop_customer = new ViewModelProvider(getActivity()).get(Shop_customer.class);
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

                                                        sell(productDetails, productPrice, paidPrice, phone);

                                                    } else {
                                                        Toast.makeText(getActivity(), "Something Wrong", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else if (duecustomerCheck == 1) {
                                            sell(productDetails, productPrice, paidPrice, phone);
                                        }
                                    }
                                });

                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "Fill correctly", Toast.LENGTH_SHORT).show();
                        //productPriceError.setError(" ");
                        paidPriceError.setError(" ");

                    }

                    //code
                }
            }
        });

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        //productSpinner = view.findViewById(R.id.productSpinnerID);
        //productSpinner.setOnItemSelectedListener(this);

        get_local_sell.getData_product(shopID).observe(getViewLifecycleOwner(), new Observer<List<get_local_sell_product_response>>() {
            @Override
            public void onChanged(List<get_local_sell_product_response> get_local_sell_product_responses) {
                productList = new ArrayList<>();
                productList = get_local_sell_product_responses;

                String[] products = new String[productList.size()];

                for (int i = 0; i < productList.size(); i++) {
                    products[i] = productList.get(i).getProduct_details();
                }

                ArrayAdapter spinnerAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, products);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                //productSpinner.setAdapter(spinnerAdapter);
            }
        });

        return view;
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
        TextView shopNameText = memoAlert.findViewById(R.id.shopNameTextID);
        TextView productDetailsTextPDF = memoAlert.findViewById(R.id.productDetailsTextID);
        TextView totalPriceTextPDF = memoAlert.findViewById(R.id.totalPriceTextID);
        TextView paidPriceTextPDF = memoAlert.findViewById(R.id.paidPriceTextID);
        TextView phoneTextPDF = memoAlert.findViewById(R.id.phoneTextID);
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
        product_sell.sell(shopID, customer_id, customer_name, phone, productPrice, buyPrice, "0", "local", "cc").observe(getViewLifecycleOwner(), new Observer<add_product_sell_response>() {
            @Override
            public void onChanged(add_product_sell_response add_product_sell_response) {
                if (!(add_product_sell_response.getSell_id().equals("failed") || add_product_sell_response.equals(null))) {
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
                                                                                push_notification.sell_notification_shop(shopID, phone, productPrice, String.valueOf(Double.parseDouble(productPrice) - Double.parseDouble(paidPrice))).observe(getViewLifecycleOwner(), new Observer<com.ALife.alife.model.push_notification_response>() {
                                                                                    @Override
                                                                                    public void onChanged(com.ALife.alife.model.push_notification_response push_notification_response) {
                                                                                        loader.dismiss();
                                                                                        Toast.makeText(getActivity(), "Sell Successfully", Toast.LENGTH_SHORT).show();

                                                                                        convert_pdf();
                                                                                        phoneText.setText("");
                                                                                        paidPriceText.setText("");
                                                                                        productDetailsText.setText("");
                                                                                        productPriceText.setText("");
                                                                                        buyPriceText.setText("");


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
                                                                                            push_notification.sell_notification_shop(shopID, phone, productPrice, String.valueOf(Double.parseDouble(productPrice) - Double.parseDouble(paidPrice))).observe(getViewLifecycleOwner(), new Observer<com.ALife.alife.model.push_notification_response>() {
                                                                                                @Override
                                                                                                public void onChanged(com.ALife.alife.model.push_notification_response push_notification_response) {
                                                                                                    loader.dismiss();
                                                                                                    Toast.makeText(getActivity(), "Sell Successfully", Toast.LENGTH_SHORT).show();

                                                                                                    convert_pdf();
                                                                                                    phoneText.setText("");
                                                                                                    paidPriceText.setText("");
                                                                                                    productDetailsText.setText("");
                                                                                                    productPriceText.setText("");
                                                                                                    buyPriceText.setText("");
                                                                                                    imageList.clear();
                                                                                                    adapter.notifyDataSetChanged();


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
                                                                            push_notification.sell_notification_shop(shopID, phone, productPrice, String.valueOf(Double.parseDouble(productPrice) - Double.parseDouble(paidPrice))).observe(getViewLifecycleOwner(), new Observer<com.ALife.alife.model.push_notification_response>() {
                                                                                @Override
                                                                                public void onChanged(com.ALife.alife.model.push_notification_response push_notification_response) {
                                                                                    loader.dismiss();
                                                                                    Toast.makeText(getActivity(), "Sell Successfully", Toast.LENGTH_SHORT).show();

                                                                                    convert_pdf();
                                                                                    phoneText.setText("");
                                                                                    paidPriceText.setText("");
                                                                                    productDetailsText.setText("");
                                                                                    productPriceText.setText("");
                                                                                    buyPriceText.setText("");


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
                                                                    push_notification.sell_notification_shop(shopID, phone, productPrice, String.valueOf(Double.parseDouble(productPrice) - Double.parseDouble(paidPrice))).observe(getViewLifecycleOwner(), new Observer<com.ALife.alife.model.push_notification_response>() {
                                                                        @Override
                                                                        public void onChanged(com.ALife.alife.model.push_notification_response push_notification_response) {
                                                                            loader.dismiss();
                                                                            Toast.makeText(getActivity(), "Sell Successfully", Toast.LENGTH_SHORT).show();

                                                                            convert_pdf();
                                                                            phoneText.setText("");
                                                                            paidPriceText.setText("");
                                                                            productDetailsText.setText("");
                                                                            productPriceText.setText("");
                                                                            buyPriceText.setText("");


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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = String.valueOf(parent.getItemAtPosition(position));

        //Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
        ((TextView) view).setVisibility(View.GONE);
        productDetailsText.setText(item);
        productPriceText.setText(productList.get(position).getPrice());
        if (!productList.get(position).getImage().equals("")) {
            Picasso.get().load(productList.get(position).getImage()).into(productImage);
            check = 2;
            image = productList.get(position).getImage();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}