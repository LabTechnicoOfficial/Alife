package com.ALife.alife.view.Shop;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.ALife.alife.DB.AppDatabase;
import com.ALife.alife.DB.InsertProductThread;
import com.ALife.alife.DB.ProductDao;
import com.ALife.alife.DB.Products;
import com.ALife.alife.R;
import com.ALife.alife.Utils.Helpers;
import com.ALife.alife.adapter.Barcode_view_adapter;
import com.ALife.alife.adapter.Shop_product_barcode_print_adapter;
import com.ALife.alife.model.Get_product_response;
import com.ALife.alife.session.SessionManagement;
import com.ALife.alife.viewmodel.Get_all_shop_product;

import java.util.ArrayList;
import java.util.List;

public class ShopPrintBarcodeFragment extends Fragment implements Shop_product_barcode_print_adapter.OnCheckBoxClickListener {

    RecyclerView productView;
    EditText searchEditText;
    SessionManagement sessionManagement;
    String shopID;

    Get_all_shop_product getAllShopProduct;


    int page = 1, limit = 10;

    Shop_product_barcode_print_adapter productAdapter;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;

    ProductDao productDao;
    List<Products> productList = new ArrayList<>();

    ImageView printButton;
    View barCodeLayout;

    List<Products> searchedProductList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_print_barcode, container, false);

        init_view(view);


        loadProducts();

//        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//                //offersButton.show();
//                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
//                    // in this method we are incrementing page number,
//                    // making progress bar visible and calling get data method.
//                    page++;
//                    loadProducts(page);
//
//                }
//            }
//        });

        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Products> markedProductList = new ArrayList<>();
                markedProductList.addAll(productDao.getMarkedProductList());

                //Toast.makeText(getActivity(), String.valueOf(markedProductList.size()), Toast.LENGTH_SHORT).show();
                if (markedProductList.size() > 0) {
                    barCodeGeneratePrint(markedProductList);
                } else {
                    Toast.makeText(getActivity(), "No product selected", Toast.LENGTH_SHORT).show();
                }

            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //productList.clear();
                if (s.length() != 0) {

                    progressBar.setVisibility(View.VISIBLE);
                    productList = productDao.getSearchedProductsList(s.toString());

                    setUpAdapter(productList);
                } else {

                    get_products();
                }

                //Log.d("dataxx", "afterTextChanged: " + String.valueOf(searchedProductList.size()));
            }
        });


        return view;
    }

    private void barCodeGeneratePrint(List<Products> markedProductList) {
        Dialog barcodeAlert = new Dialog(getActivity());
        barcodeAlert.setContentView(R.layout.barcode_generate_print_alert);
        barcodeAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        barcodeAlert.setCancelable(false);
        barcodeAlert.show();

        Window window = barcodeAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        ImageView closeButton = barcodeAlert.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barcodeAlert.dismiss();
            }
        });

        RecyclerView barCodeView = barcodeAlert.findViewById(R.id.barCodeView);
        barCodeView.setHasFixedSize(true);
        barCodeView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        Barcode_view_adapter barcodeViewAdapter = new Barcode_view_adapter(markedProductList);
        barCodeView.setAdapter(barcodeViewAdapter);


        List<String> items = new ArrayList<>();
        if (markedProductList.size() < 5) {
            for (int i = 0; i < markedProductList.size(); i++) {
                items.add(String.valueOf(i + 1));
            }
        } else {
            for (int i = 0; i < 5; i++) {
                items.add(String.valueOf(i + 1));
            }
        }
        Spinner itemSpinner = barcodeAlert.findViewById(R.id.itemSpinner);
        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(aa);

        itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int item = Integer.parseInt(parent.getItemAtPosition(position).toString());
                barCodeView.setLayoutManager(new GridLayoutManager(getActivity(), item));
                Barcode_view_adapter barcodeViewAdapter = new Barcode_view_adapter(markedProductList);
                barCodeView.setAdapter(barcodeViewAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ImageView printButton = barcodeAlert.findViewById(R.id.printButton);
        barCodeLayout = barcodeAlert.findViewById(R.id.barCodeLayout);
        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helpers.createPDF(barCodeLayout, getActivity(), "bcp");
            }
        });


    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void loadProducts() {
        progressBar.setVisibility(View.VISIBLE);
        getAllShopProduct.getAllProductWithOutPagination(shopID).observe(getViewLifecycleOwner(), new Observer<List<Get_product_response>>() {
            @Override
            public void onChanged(List<Get_product_response> get_product_responses) {

                for (int i = 0; i < get_product_responses.size(); i++) {
                    String printCheck = "0";
                    Get_product_response response = get_product_responses.get(i);
                    InsertProductThread insertProductThread = new InsertProductThread(response.getProduct_id(), response.getProduct_name(), printCheck, response.getProduct_image(), response.getCode(), getActivity());
                    insertProductThread.start();

                }

                // get_products();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        get_products();
                    }
                }, 1000);


            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    private void get_products() {

        productList = productDao.getProductsList();

        setUpAdapter(productList);

    }

    private void setUpAdapter(List<Products> productList) {
        //Log.d("dataxx", "setUpAdapter: "+String.valueOf(productList.size()));
        progressBar.setVisibility(View.GONE);
        productAdapter = new Shop_product_barcode_print_adapter(productList);
        productAdapter.notifyDataSetChanged();
        productAdapter.setOnClickListener(ShopPrintBarcodeFragment.this::onCheckBoxClick);
        productView.setAdapter(productAdapter);
    }

    private void init_view(View view) {
        getAllShopProduct = new ViewModelProvider(getActivity()).get(Get_all_shop_product.class);
        sessionManagement = new SessionManagement(getActivity());
        progressBar = view.findViewById(R.id.progressBar);
        nestedScrollView = view.findViewById(R.id.nestedRecyclerView);
        productView = view.findViewById(R.id.productView);
        productView.setHasFixedSize(true);
        productView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchEditText = view.findViewById(R.id.searchEditText);
        shopID = String.valueOf(sessionManagement.getSession());


        AppDatabase db = Room.databaseBuilder(getActivity(), AppDatabase.class, "alifeDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        productDao = db.productDao();
        productDao.clearProducts();

        printButton = view.findViewById(R.id.printButton);

    }

    @Override
    public void onCheckBoxClick(int position, boolean state) {
        Products response = productList.get(position);
        //Toast.makeText(getActivity(), response.getProductID() + " " + String.valueOf(state), Toast.LENGTH_SHORT).show();
        productDao.updatePrintCheck(response.getProductID(), state ? "1" : "0");
    }
}