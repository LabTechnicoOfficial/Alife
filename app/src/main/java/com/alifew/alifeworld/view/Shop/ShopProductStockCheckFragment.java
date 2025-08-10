package com.alifew.alifeworld.view.Shop;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alifew.alifeworld.DB.AppDatabase;
import com.alifew.alifeworld.DB.dao.ProductDao;
import com.alifew.alifeworld.DB.entity.Products;
import com.alifew.alifeworld.R;
import com.alifew.alifeworld.StockAvailibityPrintActivity;
import com.alifew.alifeworld.Utils.Constants;
import com.alifew.alifeworld.Utils.ImageHelper;
import com.alifew.alifeworld.adapter.stock.ShopProductStockCheckSearchAdapter;
import com.alifew.alifeworld.adapter.stock.ShopProductStockCheckTypeAdapter;
import com.alifew.alifeworld.databinding.FragmentShopProductStockCheckBinding;
import com.alifew.alifeworld.model.Get_product_response;
import com.alifew.alifeworld.session.SessionManagement;
import com.alifew.alifeworld.viewmodel.Get_all_shop_product;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ShopProductStockCheckFragment extends Fragment implements ShopProductStockCheckSearchAdapter.OnItemClickListener {

    FragmentShopProductStockCheckBinding binding;
    ProductDao productDao;
    SessionManagement sessionManagement;
    Get_all_shop_product getAllShopProduct;
    String shopID;

    Dialog loader;


    List<Products> productList = new ArrayList<>();
    ShopProductStockCheckSearchAdapter adapter;
    RecyclerView itemView;
    Dialog productSearchAlert;

    List<Products> typeList;

    String confirmID;
    ShopProductStockCheckTypeAdapter shopProductStockCheckTypeAdapter;

    TextView barcodeText;
    SurfaceView surfaceView;
    BarcodeDetector barcodeDetector;
    String barcodeData;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentShopProductStockCheckBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        initView(view);

        loadProducts();

        binding.searchEditText.setOnClickListener(v -> {

            productSearchAlert.show();

            ImageView closeButton = productSearchAlert.findViewById(R.id.closeButton);
            closeButton.setOnClickListener(v1 -> {
                productSearchAlert.dismiss();
            });

            itemView = productSearchAlert.findViewById(R.id.itemView);
            itemView.setHasFixedSize(true);
            itemView.setLayoutManager(new LinearLayoutManager(getActivity()));

            EditText searchEditText = productSearchAlert.findViewById(R.id.searchEditText);
            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    productList = productDao.getSearchedProductsList(s.toString().trim());
                    setUpAdapter(productList);
                }
            });

            get_products();
        });

        binding.printButton.setOnClickListener(v -> {
            getActivity().startActivity(new Intent(getActivity(), StockAvailibityPrintActivity.class));
        });

        binding.barCodeButton.setOnClickListener(v -> {
            Dialog barCodeAlert = new Dialog(getActivity());
            barCodeAlert.setContentView(R.layout.barcode_scan_alert);
            barCodeAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            barCodeAlert.setCancelable(false);
            barCodeAlert.show();

            Window window = barCodeAlert.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);

            AppCompatButton okButton = barCodeAlert.findViewById(R.id.ok);
            AppCompatButton reScanButton = barCodeAlert.findViewById(R.id.reScanButton);
            ImageView closeButton = barCodeAlert.findViewById(R.id.closeButton);
            barcodeText = barCodeAlert.findViewById(R.id.barcode_text);
            surfaceView = barCodeAlert.findViewById(R.id.surface_view);
            okButton.setOnClickListener(view1 -> {

                if (barcodeData.isEmpty()) {
                    Toast.makeText(getActivity(), "no barcode detected", Toast.LENGTH_SHORT).show();
                } else {

                    getActivity().runOnUiThread(() -> {
                        //loader.show();
                        List<Products> productsListByBarCode = productDao.getProductsByBarCode(barcodeData);
                        if (productsListByBarCode.isEmpty()) {
                            Toast.makeText(getActivity(), "no products found", Toast.LENGTH_SHORT).show();
                        } else {
                            //  Toast.makeText(getActivity(), String.valueOf(productsListByBarCode.size()), Toast.LENGTH_SHORT).show();
                            Products products = productDao.getProductsByBarCode(barcodeData).get(0);
                            setDataInProductsDetailsCard(products.getProductID(), products.getName(), products.getPrice(), products.getImage(), products.getStock(), products.getId(), products.getStockAvailable());
                            barCodeAlert.dismiss();
                        }


                    });

                }


            });

            reScanButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initialiseDetectorsAndSources();
                }
            });

            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    barCodeAlert.dismiss();
                }
            });

            initialiseDetectorsAndSources();
        });

        return view;
    }

    private void initialiseDetectorsAndSources() {
        barcodeData = "";
        barcodeText.setText(barcodeData);

        barcodeDetector = new BarcodeDetector.Builder(getActivity())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(getActivity(), barcodeDetector)
                .setRequestedPreviewSize(1080, 1080)
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
                // Toast.makeText(getActivity(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
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
                                // toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                                // barcodeDetector.release();
                            } else {

                                barcodeData = barcodes.valueAt(0).displayValue;
                                barcodeText.setText(barcodeData);
                                // toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);

                            }
                        }
                    });

                }
            }
        });
    }

    private void initView(View view) {
        AppDatabase db = AppDatabase.getDatabase(getActivity());
        productDao = db.productDao();
        productDao.clearProducts();
        productDao.resetPrimaryKeySequence("tblProducts");

        sessionManagement = new SessionManagement(getActivity());
        getAllShopProduct = new ViewModelProvider(getActivity()).get(Get_all_shop_product.class);
        shopID = String.valueOf(sessionManagement.getUserID());

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        productSearchAlert = new Dialog(getActivity());
        productSearchAlert.setContentView(R.layout.search_product_with_type_alert);
        productSearchAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        productSearchAlert.setCancelable(false);

        Window window = productSearchAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        binding.productDetailsCard.setVisibility(View.GONE);

        binding.typeItemView.setHasFixedSize(true);
        binding.typeItemView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void loadProducts() {
        loader.show();
        getAllShopProduct.getAllProductWithOutPagination(shopID).observe(getViewLifecycleOwner(), new Observer<List<Get_product_response>>() {
            @Override
            public void onChanged(List<Get_product_response> get_product_responses) {
                loader.dismiss();
                for (int i = 0; i < get_product_responses.size(); i++) {
                    String printCheck = "0";
                    Get_product_response response = get_product_responses.get(i);
                    productDao.insertProducts(new Products(response.getProduct_id(), response.getProduct_name(), printCheck, response.getProduct_image(), response.getCode(), response.getStock_amount(), response.getSelling_price(), response.getProduct_unit(), response.getType(), "0"));
                }

                new Handler().postDelayed(() -> {
                    //get_products();
                }, 1000);


            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    private void get_products() {
        productList = productDao.getSearchedProductsList("");
        setUpAdapter(productList);

    }

    private void setUpAdapter(List<Products> productList) {
        adapter = new ShopProductStockCheckSearchAdapter(productList, productDao);
        adapter.setOnItemClickListener(ShopProductStockCheckFragment.this);
        itemView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Products products = productList.get(position);
        setDataInProductsDetailsCard(products.getProductID(), products.getName(), products.getPrice(), products.getImage(), products.getStock(), products.getId(), products.getStockAvailable());
    }

    private void setDataInProductsDetailsCard(String productID, String name, String price, String image, String stock, int id, String stockAvailable) {
        binding.productDetailsCard.setVisibility(View.VISIBLE);
        confirmID = String.valueOf(id);
        productSearchAlert.dismiss();
        binding.titleText.setText("Title: " + name);
        binding.priceText.setText("Price: " + price + Constants.TAKA_SYMBOL);
        binding.stockFoundText.setText("Stock Found: " + stockAvailable);
        ImageHelper.imageLoader(getActivity(), binding.productImage, image);
        binding.stockText.setText("Stock: " + stock);

        typeList = productDao.getProductsTypes(productID);

        if (typeList.size() > 1) {
            binding.stockText.setVisibility(View.GONE);
            binding.typeLayout.setVisibility(View.VISIBLE);
            binding.stockFoundEditText.setVisibility(View.GONE);
            binding.stockFoundText.setVisibility(View.GONE);
            confirmID = "";

            shopProductStockCheckTypeAdapter = new ShopProductStockCheckTypeAdapter(typeList, ShopProductStockCheckFragment.this);
            binding.typeItemView.setAdapter(shopProductStockCheckTypeAdapter);

        } else {
            binding.stockText.setVisibility(View.VISIBLE);
            binding.stockFoundText.setVisibility(View.VISIBLE);
            binding.stockFoundEditText.setVisibility(View.VISIBLE);
            binding.typeLayout.setVisibility(View.GONE);
        }

        binding.confirmButton.setOnClickListener(v -> {

            if (confirmID.isEmpty()) {

                for (int i = 0; i < typeList.size(); i++) {
                    //Toast.makeText(getActivity(), String.valueOf(typeList.get(i).getStockAvailable()), Toast.LENGTH_SHORT).show();

                    Double stockAmount = Double.parseDouble(typeList.get(i).getStockAvailable());
                    if (stockAmount > (Double.parseDouble(typeList.get(i).getStock()) - Double.parseDouble(typeList.get(i).getStockAvailable()))) {
                        Toast.makeText(getActivity(), typeList.get(i).getType() + " value can't be larger than stock", Toast.LENGTH_SHORT).show();
                        //return;
                    } else {
                        productDao.updateProductsStockAvailability(String.valueOf(typeList.get(i).id), String.valueOf(stockAmount));
                        binding.productDetailsCard.setVisibility(View.GONE);
                        binding.stockFoundEditText.setText("");
                    }

                }
            } else {
                //Toast.makeText(getActivity(), confirmID, Toast.LENGTH_SHORT).show();

                if (binding.stockFoundEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "empty field", Toast.LENGTH_SHORT).show();
                } else {
                    Double stockAmount = Double.parseDouble(binding.stockFoundEditText.getText().toString().trim());
                    if (stockAmount > (Double.parseDouble(stock) - Double.parseDouble(stockAvailable))) {
                        Toast.makeText(getActivity(), "input value can't be larger than stock", Toast.LENGTH_SHORT).show();
                    } else {
                        productDao.updateProductsStockAvailability(confirmID, String.valueOf(stockAmount));
                        binding.productDetailsCard.setVisibility(View.GONE);
                        binding.stockFoundEditText.setText("");
                    }

                }
            }
        });
    }

    public void updateTypeItemValue(int position, String availableItem) {

        /*for(Products item : typeList) {
            if (String.valueOf(position).equals(item.getStockItemID())){
                item.setStockItemQuantityAvailable(qty);
            }
        }*/
        Products products = typeList.get(position);
        products.setStockAvailable(availableItem);
        typeList.set(position, products);
    }


}