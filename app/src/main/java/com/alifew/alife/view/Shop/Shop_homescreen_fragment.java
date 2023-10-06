package com.alifew.alife.view.Shop;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alifew.alife.Custom_Type.ProductSell;
import com.alifew.alife.R;
import com.alifew.alife.Utils.ImageHelper;
import com.alifew.alife.adapter.Instruction_adapter;
import com.alifew.alife.adapter.Shop_barcode_type_adapter;
import com.alifew.alife.model.Fetch_product_detail_by_bar_code_response;
import com.alifew.alife.model.user_instruction_response;
import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.viewmodel.Get_product;
import com.alifew.alife.viewmodel.User_instruction;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Shop_homescreen_fragment extends Fragment implements Instruction_adapter.OnItemClickListener {
    LinearLayout dailyAccountButton, dueListButton, customerListButton;
    LinearLayout sellProductButton, addProductButton, allProductButton;
    LinearLayout dueCustomerButton, businessAccountButton, localPageButton;
    LinearLayout sellHistoryButton, couponButton, localSellButton;
    LinearLayout sendNotificationButton, barcodeScanButton, printBarcodeButton;
    private String shop_id;

    FragmentManager fragmentManager;
    User_instruction userInstruction;
    private List<user_instruction_response> instructionList;
    Instruction_adapter instructionAdapter;
    private int timeLimit_UseriNSTRUCTION = 0;
    private AdManagerAdView mAdManagerAdView;
    private InterstitialAd InterstitialAd;

    TextView barcodeText;
    SurfaceView surfaceView;
    BarcodeDetector barcodeDetector;
    String barcodeData;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;

    Get_product getProductViewModel;

    RecyclerView instructionView;

    SessionManagement sessionManagement;
    LinearLayout instructorLayout;

    @SuppressLint("MissingPermission")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//
//                loadAd();
//            }
//        });

        main();
        instruction_func();
    }


    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_homescreen_fragment, container, false);

        initView(view);


        localSellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_local_sell_fragment()).addToBackStack(null).commit();
            }
        });

        dailyAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Tali_khata_fragment()).addToBackStack(null).commit();
            }
        });

        customerListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_customer_list_fragments(shop_id)).addToBackStack(null).commit();
            }
        });

        sellProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ProductSell> sellList;
                sellList = new ArrayList<>();
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_sellcategoriesORproducts_fragment(shop_id, sellList)).addToBackStack(null).commit();
            }
        });

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_categories_fragment(shop_id)).addToBackStack(null).commit();

            }

        });

        allProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_all_products_fragment(shop_id)).addToBackStack(null).commit();
            }
        });

        dueListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_due_list_fragment(shop_id)).addToBackStack(null).commit();
            }
        });

        dueCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_due_customer_fragment(shop_id)).addToBackStack(null).commit();
            }
        });

        businessAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_business_summary_fragment(shop_id)).addToBackStack(null).commit();
            }
        });

        localPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_local_page_fragment(shop_id)).addToBackStack(null).commit();
            }
        });

        sellHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_sell_history_fragment(shop_id)).addToBackStack(null).commit();
            }
        });



        /* addInterval.getResponse(earning_response.getEarning_id(), currentDate, 1).observe(getViewLifecycleOwner(), new Observer<addInterval_response>() {

                                @Override
                                public void onChanged(addInterval_response addInterval_response) {
                                    if (addInterval_response.getToken() == 0) {
                                        if (InterstitialAd != null) {
                                            InterstitialAd.show(getActivity());
                                            startActivity(intent);
                                        } else {
                                            loadAd();
                                            startActivity(intent);
                                        }


                                    } else {
                                        try {
                                            Date d1 = format.parse(currentDate);
                                            Date d2 = format.parse(addInterval_response.getTime());
                                            long difference_In_Time = d2.getTime() - d1.getTime();
                                            long difference_In_Minutes
                                                    = (difference_In_Time
                                                    / (1000 * 60))
                                                    % 60;
                                            if (difference_In_Minutes >= 2.0) {
                                                if (InterstitialAd != null) {
                                                    InterstitialAd.show(getActivity());
                                                    addInterval.getResponse(earning_response.getEarning_id(), currentDate, 2).observe(getViewLifecycleOwner(), new Observer<com.ALife.alife.EarningApp.Model.AddInterval.addInterval_response>() {
                                                        @Override
                                                        public void onChanged(com.ALife.alife.EarningApp.Model.AddInterval.addInterval_response addInterval_response) {
                                                            startActivity(intent);
                                                        }
                                                    });
                                                } else {
                                                    loadAd();
                                                    startActivity(intent);
                                                }


                                            } else {
                                                startActivity(intent);
                                            }
                                        } catch (Exception e) {

                                        }

                                    }
                                }
                            });*/

        couponButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_coupon_fragment()).addToBackStack(null).commit();
            }
        });

        sendNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_notification_fragment(shop_id)).addToBackStack(null).commit();
            }
        });

        barcodeScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                ImageView closeButton = barCodeAlert.findViewById(R.id.closeButtonID);
                barcodeText = barCodeAlert.findViewById(R.id.barcode_text);
                surfaceView = barCodeAlert.findViewById(R.id.surface_view);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (barcodeData.isEmpty()) {
                            Toast.makeText(getActivity(), "no barcode detected", Toast.LENGTH_SHORT).show();
                        } else {
                            getProductViewModel.fetch_product_detail_by_bar_code(shop_id, barcodeData).observe(getViewLifecycleOwner(), new Observer<Fetch_product_detail_by_bar_code_response>() {

                                @Override
                                public void onChanged(Fetch_product_detail_by_bar_code_response response) {

                                    if (response != null) {
                                        barCodeAlert.dismiss();
                                        vieProductDetails(response);
                                    } else {
                                        Toast.makeText(getActivity(), "কোন পণ্য পাওয়া যাইনি", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
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
        });


        printBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new ShopPrintBarcodeFragment()).addToBackStack(null).commit();
            }
        });

        LinearLayout pointsButton = view.findViewById(R.id.pointsButton);
        pointsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new ShopPointsFragment()).addToBackStack(null).commit();
            }
        });

        return view;
    }

    private void initView(View view) {
        instructionView = view.findViewById(R.id.instructionView);
        instructionView.setHasFixedSize(true);
        instructionView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        dailyAccountButton =  view.findViewById(R.id.dailyAccountButtonID);
        dueListButton =  view.findViewById(R.id.dueListButtonID);
        customerListButton =  view.findViewById(R.id.customerListButtonID);
        sellProductButton =  view.findViewById(R.id.sellProductsButtonID);
        addProductButton =  view.findViewById(R.id.addProductsButtonID);
        allProductButton =  view.findViewById(R.id.allProductsButtonID);
        dueCustomerButton =  view.findViewById(R.id.duecustomerListButtonID);
        businessAccountButton =  view.findViewById(R.id.businessAccountButtonID);
        localPageButton =  view.findViewById(R.id.localPageButtonID);
        sellHistoryButton =  view.findViewById(R.id.sellHistoryButtonID);

        couponButton =  view.findViewById(R.id.couponButtonID);
        localSellButton =  view.findViewById(R.id.localSellButtonID);
        sendNotificationButton =  view.findViewById(R.id.sendNotificationButtonID);
        barcodeScanButton = view.findViewById(R.id.barcodeScanButton);
        printBarcodeButton = view.findViewById(R.id.printBarcodeButton);

        instructorLayout = view.findViewById(R.id.instructorLayout);


        getProductViewModel = new ViewModelProvider(this).get(Get_product.class);
        userInstruction = new ViewModelProvider(getActivity()).get(User_instruction.class);

        //banner add
        mAdManagerAdView = (AdManagerAdView) view.findViewById(R.id.adManagerAdView);

        fragmentManager = getFragmentManager();

        sessionManagement = new SessionManagement(getActivity());
        shop_id = String.valueOf(sessionManagement.getSession());

    }

    private void instruction_func() {


        userInstruction.getInstruction("user").observe(getViewLifecycleOwner(), new Observer<List<user_instruction_response>>() {
            @Override
            public void onChanged(List<user_instruction_response> user_instruction_responses) {

                if (user_instruction_responses.size() > 0) {
                    instructorLayout.setVisibility(View.VISIBLE);
                    instructionList = new ArrayList<>();
                    instructionList = user_instruction_responses;
                    instructionAdapter = new Instruction_adapter(instructionList);
                    instructionAdapter.setOnClickListener(Shop_homescreen_fragment.this::OnInstructorItemClick);
                    instructionView.setAdapter(instructionAdapter);
                }else {
                    instructorLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void main() {


    }

    private void vieProductDetails(Fetch_product_detail_by_bar_code_response response) {
        Dialog productDetailsAlert = new Dialog(getActivity());
        productDetailsAlert.setContentView(R.layout.product_details_from_bar_code);
        productDetailsAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        productDetailsAlert.setCancelable(false);
        productDetailsAlert.show();

        Window window = productDetailsAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        ImageView closeButton = productDetailsAlert.findViewById(R.id.closeButton);
        ImageView productImage = productDetailsAlert.findViewById(R.id.productImage);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productDetailsAlert.dismiss();
            }
        });


        ImageHelper.imageLoader(getActivity(), productImage, response.productImage);

        TextView titleText = productDetailsAlert.findViewById(R.id.titleText);
        TextView categoryTitleText = productDetailsAlert.findViewById(R.id.categoryTitleText);
        TextView buyPriceText = productDetailsAlert.findViewById(R.id.buyPriceText);
        TextView sellPriceText = productDetailsAlert.findViewById(R.id.sellPriceText);
        TextView stockAmountText = productDetailsAlert.findViewById(R.id.stockAmountText);

        categoryTitleText.setText(Html.fromHtml("Category: " + "<b>" + response.category.catagory01yName + "<b>"));
        titleText.setText(response.productName);
        buyPriceText.setText(getActivity().getResources().getText(R.string.buy_price) + ": " + response.buyPrice + " tk");
        sellPriceText.setText(getActivity().getResources().getText(R.string.sell_price) + ": " + response.sellingPrice + " tk");
        stockAmountText.setText("Stock: " + response.stockAmount + " " + response.productUnit);

        ConstraintLayout typeLayout = productDetailsAlert.findViewById(R.id.typeLayout);
        RecyclerView typeView = productDetailsAlert.findViewById(R.id.typeView);
        typeView.setHasFixedSize(true);
        typeView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (response.type.size() > 0) {

            // Log.d("dataxx", "page: "+String.valueOf(response.type.size()));
            typeLayout.setVisibility(View.VISIBLE);
            Shop_barcode_type_adapter adapter = new Shop_barcode_type_adapter(response.type, response.productUnit);
            typeView.setAdapter(adapter);
        } else {
            typeLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void OnInstructorItemClick(int position) {
        user_instruction_response response = instructionList.get(position);
        String link = response.getLink();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        startActivity(intent);
    }

    private void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                getActivity(),
                "ca-app-pub-9914022847917901/8396202139",
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        InterstitialAd = interstitialAd;
                        Log.i("msg", "onAdLoaded");
                        //showInterstitial();
                        //Toast.makeText(getActivity(), "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        InterstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        InterstitialAd = null;
                                        Log.d("msg", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        //loadAd();
                    }
                });
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
}