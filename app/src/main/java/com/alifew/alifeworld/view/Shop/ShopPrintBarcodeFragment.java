package com.alifew.alifeworld.view.Shop;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.alifew.alifeworld.DB.AppDatabase;
import com.alifew.alifeworld.DB.dao.ProductDao;
import com.alifew.alifeworld.DB.entity.Products;
import com.alifew.alifeworld.PrintActivity;
import com.alifew.alifeworld.R;
import com.alifew.alifeworld.Utils.Helpers;
import com.alifew.alifeworld.adapter.Barcode.Barcode_view_adapter;
import com.alifew.alifeworld.adapter.Barcode.Shop_product_barcode_print_adapter;
import com.alifew.alifeworld.model.Get_product_response;
import com.alifew.alifeworld.session.SessionManagement;
import com.alifew.alifeworld.viewmodel.Get_all_shop_product;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ShopPrintBarcodeFragment extends Fragment implements Shop_product_barcode_print_adapter.OnCheckBoxClickListener, Shop_product_barcode_print_adapter.MarkAllClickListener {

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

    ImageView printButton, barCodeButton;
    View barCodeLayout;
    TextView barcodeText;
    SurfaceView surfaceView;
    BarcodeDetector barcodeDetector;
    String barcodeData;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;

    ImageView reloadButton;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shop_print_barcode, container, false);

        init_view(view);


        loadProducts();


        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Products> markedProductList = new ArrayList<>();
                markedProductList.addAll(productDao.getMarkedProductList());
//
//                //Toast.makeText(getActivity(), String.valueOf(markedProductList.size()), Toast.LENGTH_SHORT).show();
                if (markedProductList.size() > 0) {
                    // barCodeGeneratePrint(markedProductList);
                    Intent intent = new Intent(getActivity(), PrintActivity.class);
                    intent.putParcelableArrayListExtra(
                            "BARCODELIST", (ArrayList<? extends Parcelable>) markedProductList);
                    getActivity().startActivity(intent);

                } else {
                    Toast.makeText(getActivity(), "No product selected", Toast.LENGTH_SHORT).show();
                }

                //getActivity().startActivity(new Intent(getActivity(), PrintActivity.class));


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

        barCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBarcodeDialog();

            }
        });

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_products();
            }
        });


        return view;
    }

    private void openBarcodeDialog() {
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
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (barcodeData.isEmpty()) {
                    Toast.makeText(getActivity(), "no barcode detected", Toast.LENGTH_SHORT).show();
                } else {
                    //do code here
                    barCodeAlert.dismiss();
                    Log.d("dataxx", "barcode: " + barcodeData);
                    progressBar.setVisibility(View.VISIBLE);
                    productList = productDao.getProductsByBarCode(barcodeData);

                    setUpAdapter(productList);
                }


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
                Barcode_view_adapter barcodeViewAdapter = new Barcode_view_adapter(markedProductList, sessionManagement.getSaveShopName(), item);
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
//                Helpers.createPDF(barCodeLayout, getActivity(), "bcp");
//                Log.d("dataxx", "onClick: " + String.valueOf(barCodeLayout.getHeight()) + " " + String.valueOf(barCodeLayout.getWidth()));

                createPDF(barCodeLayout, barCodeLayout.getWidth(), barCodeLayout.getHeight());
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
                    productDao.insertProducts(new Products(response.getProduct_id(), response.getProduct_name(), printCheck, response.getProduct_image(), response.getCode(), response.getStock_amount(), response.getSelling_price(), response.getProduct_unit(), response.getType(), "0"));
                }

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

        progressBar.setVisibility(View.GONE);
        productAdapter = new Shop_product_barcode_print_adapter(productList, productDao);
        productAdapter.notifyDataSetChanged();
        productAdapter.setOnClickListener(ShopPrintBarcodeFragment.this::onCheckBoxClick, ShopPrintBarcodeFragment.this::onMarkAllClick);
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

        barCodeButton = view.findViewById(R.id.barCodeButton);


        AppDatabase db = AppDatabase.getDatabase(getActivity());
        productDao = db.productDao();
        productDao.clearProducts();
        productDao.resetPrimaryKeySequence("tblProducts");

        printButton = view.findViewById(R.id.printButton);
        reloadButton = view.findViewById(R.id.reloadButton);

    }

    @Override
    public void onCheckBoxClick(int position, boolean state) {
        Products response = productList.get(position);
        productDao.updatePrintCheck(String.valueOf(response.getId()), state ? "1" : "0");

    }

    @Override
    public void onMarkAllClick(int position) {
        Products response = productList.get(position);

        List<Products> typeList = new ArrayList<>();
        typeList = productDao.getProductsTypes(response.getProductID());

        for (int i = 0; i < typeList.size(); i++) {
            String id = String.valueOf(typeList.get(i).getId());
            productDao.updatePrintCheck(id, "1");
        }

        get_products();
    }

    @SuppressLint("SdCardPath")
    private void createPDF(View barCodeLayout, int width, int height) {
        Log.d("dataxx", "size: " + String.valueOf(width) + " " + String.valueOf(height));
        Bitmap bitmap = Helpers.loadBitmap(barCodeLayout);
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int convertedWidth = (int) displayMetrics.widthPixels;
        int convertedHeight = (int) displayMetrics.heightPixels;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertedWidth, convertedHeight, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        canvas.drawPaint(paint);
        bitmap = Bitmap.createScaledBitmap(bitmap, convertedWidth, convertedHeight, true);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);


        String folderName = "Alife";


        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), folderName);

        if (!dir.exists()) {
            dir.mkdir();

        }

        File file = new File(dir, Helpers.generateFileName("bar") + ".pdf");

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                document.writeTo(Files.newOutputStream(file.toPath()));
            }

            Helpers.openPdf(file.getPath().toString(), getActivity());

            Log.d("dataxx", "path: " + file.getPath().toString());
        } catch (Exception e) {
            Log.d("dataxx", "createPDF: " + e.getMessage());
            Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
            Log.d("dataxx", "epath: " + file.getPath().toString());
            document.close();

            //openPdf(file.getPath().toString());
//            Toast.makeText(getActivity(), "PDF", Toast.LENGTH_SHORT).show();
        }
    }


}