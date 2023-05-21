package com.ALife.alife.view.Shop;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ALife.alife.Custom_Type.ProductSell;
import com.ALife.alife.R;
import com.ALife.alife.model.local_sell.get_product_by_bar_code_response;
import com.ALife.alife.model.shop_profile_response;
import com.ALife.alife.viewmodel.Local_sell.Get_local_sell;
import com.ALife.alife.viewmodel.Shop_profile;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bar_code_fragment extends Fragment {
    private String shop_id;
    private List<ProductSell> productSellList;
    private FragmentManager fragmentManager;
    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private ToneGenerator toneGen1;
    private TextView barcodeText;
    Button submit;
    private String barcodeData, barcode;
    Get_local_sell get_local_sell;

    public Bar_code_fragment(String shop_id) {
        // Required empty public constructor
        this.shop_id = shop_id;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.barcode_scanner, container, false);
        fragmentManager = getFragmentManager();
        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        surfaceView = view.findViewById(R.id.surface_view);
        barcodeText = view.findViewById(R.id.barcode_text);
        submit = view.findViewById(R.id.submit);
        initialiseDetectorsAndSources();
        get_local_sell = new ViewModelProvider(this).get(Get_local_sell.class);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                barcode = barcodeText.getText().toString();

                if (barcode.isEmpty()) {
                    Log.d("mesba", "No Barcode Available");
                    Toast.makeText(getActivity(), "No Barcode Available", Toast.LENGTH_SHORT).show();
                } else {
                    get_local_sell.getProduct(barcode, shop_id).observe(getViewLifecycleOwner(), new Observer<get_product_by_bar_code_response>() {
                        @Override
                        public void onChanged(get_product_by_bar_code_response get_product_by_bar_code_response) {
                            if (!get_product_by_bar_code_response.getProduct_id().equals("0")) {
                                if (Double.parseDouble(get_product_by_bar_code_response.getStock_amount()) > 0) {
                                    Shop_profile shop_profile = new ViewModelProvider(getActivity()).get(Shop_profile.class);

                                    // product_discount_all = shop_profile_response.getAll_discount();
                                    //allDiscountText.setText(shop_profile_response.getAll_discount());
                                    //get_product();
                                    productSellList = new ArrayList<>();
                                    fragmentManager.beginTransaction().setCustomAnimations(
                                            R.anim.slide_in,  // enter
                                            R.anim.fade_out,  // exit
                                            R.anim.fade_in,   // popEnter
                                            R.anim.slide_out  // popExit
                                    ).replace(R.id.frame_container, new Shop_local_sell_barcode_product_selected_fragment(shop_id, get_product_by_bar_code_response.getProduct_id(), productSellList, get_product_by_bar_code_response.getAll_discount())).addToBackStack(null).commit();

                                } else {
                                    Toast.makeText(getActivity(), "Stock Out", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                Toast.makeText(getActivity(), "No Product Available for This Barcode", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
        return view;
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
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                            } else {

                                barcodeData = barcodes.valueAt(0).displayValue;
                                barcodeText.setText(barcodeData);
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);

                            }
                        }
                    });

                }
            }
        });
    }

}