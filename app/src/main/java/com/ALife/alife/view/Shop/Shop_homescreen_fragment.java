package com.ALife.alife.view.Shop;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ALife.alife.Custom_Type.ProductSell;
import com.ALife.alife.EarningApp.Model.AddInterval.addInterval_response;
import com.ALife.alife.EarningApp.Model.Earning_Session_Management;
import com.ALife.alife.EarningApp.View.Activity.MainActivity;
import com.ALife.alife.EarningApp.View.Fragment.Home_fragment;
import com.ALife.alife.EarningApp.ViewModel.AddInterval;
import com.ALife.alife.R;
import com.ALife.alife.adapter.Instruction_adapter;
import com.ALife.alife.model.Earning_response;
import com.ALife.alife.model.user_instruction_response;
import com.ALife.alife.viewmodel.EarningViewModel;
import com.ALife.alife.viewmodel.SessionManagment;
import com.ALife.alife.viewmodel.User_instruction;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Shop_homescreen_fragment extends Fragment implements Instruction_adapter.OnItemClickListener {
    LinearLayout dailyAccountButton, dueListButton, customerListButton;
    LinearLayout sellProductButton, addProductButton, allProductButton;
    LinearLayout dueCustomerButton, businessAccountButton, localPageButton;
    LinearLayout sellHistoryButton, couponButton, localSellButton;
    LinearLayout sendNotificationButton;
    private String shop_id;

    FragmentManager fragmentManager;
    User_instruction userInstruction;
    private List<user_instruction_response> instructionList;
    Instruction_adapter instructionAdapter;
    private int timeLimit_UseriNSTRUCTION = 0;
    EarningViewModel earningViewModel;
    //private AdManagerAdView mAdManagerAdView;
    private AddInterval addInterval;
    private InterstitialAd InterstitialAd;

    @SuppressLint("MissingPermission")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

                //loadAd();
            }
        });
        AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
  /*      mAdManagerAdView.loadAd(adRequest);
        mAdManagerAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                // Toast.makeText(Shop_main_activity.this,"fiinsh",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
*/
        main();
        //instruction_func();
    }

    private void instruction_func() {

        userInstruction = new ViewModelProvider(getActivity()).get(User_instruction.class);
        userInstruction.getInstruction("user").observe(getViewLifecycleOwner(), new Observer<List<user_instruction_response>>() {
            @Override
            public void onChanged(List<user_instruction_response> user_instruction_responses) {
                int leng = user_instruction_responses.size();
                instructionList = new ArrayList<>();

                instructionList = user_instruction_responses;
                instructionAdapter = new Instruction_adapter(instructionList);

                if (leng > 0) {
                    Dialog instructionAlert = new Dialog(getActivity());
                    instructionAlert.setContentView(R.layout.user_instruction_alert);
                    instructionAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    instructionAlert.setCancelable(true);
                    instructionAlert.show();

                    Window window = instructionAlert.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();

                    wlp.gravity = Gravity.BOTTOM;
                    wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    wlp.windowAnimations = R.style.DialogAnimation;
                    //wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
                    // wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(wlp);

                    ImageView closeButton = instructionAlert.findViewById(R.id.closeID);
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            instructionAlert.dismiss();

                        }
                    });

                    RecyclerView instructionView = (RecyclerView) instructionAlert.findViewById(R.id.instructionViewID);
                    instructionView.setHasFixedSize(true);
                    instructionView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    instructionAdapter.setOnClickListener(Shop_homescreen_fragment.this::OnItemClick);
                    instructionView.setAdapter(instructionAdapter);
                }
            }
        });
    }

    private void main() {


    }

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_homescreen_fragment, container, false);

        dailyAccountButton = (LinearLayout) view.findViewById(R.id.dailyAccountButtonID);
        dueListButton = (LinearLayout) view.findViewById(R.id.dueListButtonID);
        customerListButton = (LinearLayout) view.findViewById(R.id.customerListButtonID);
        sellProductButton = (LinearLayout) view.findViewById(R.id.sellProductsButtonID);
        addProductButton = (LinearLayout) view.findViewById(R.id.addProductsButtonID);
        allProductButton = (LinearLayout) view.findViewById(R.id.allProductsButtonID);
        dueCustomerButton = (LinearLayout) view.findViewById(R.id.duecustomerListButtonID);
        businessAccountButton = (LinearLayout) view.findViewById(R.id.businessAccountButtonID);
        localPageButton = (LinearLayout) view.findViewById(R.id.localPageButtonID);
        sellHistoryButton = (LinearLayout) view.findViewById(R.id.sellHistoryButtonID);
        //earnMoneyButton = (LinearLayout) view.findViewById(R.id.earnMoneyButtonID);
        couponButton = (LinearLayout) view.findViewById(R.id.couponButtonID);
        localSellButton = (LinearLayout) view.findViewById(R.id.localSellButtonID);
        sendNotificationButton = (LinearLayout) view.findViewById(R.id.sendNotificationButtonID);

        earningViewModel = new ViewModelProvider(this).get(EarningViewModel.class);

        //banner add
        //mAdManagerAdView = (AdManagerAdView) view.findViewById(R.id.adManagerAdView);

        fragmentManager = getFragmentManager();

        SessionManagment sessionManagment = new SessionManagment(getActivity());
        int userId = sessionManagment.getSession();

        shop_id = String.valueOf(userId);

        localSellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_local_sell_fragment(shop_id, 1)).addToBackStack(null).commit();
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
                ).replace(R.id.frame_container, new Tali_khata_fragment(shop_id)).addToBackStack(null).commit();
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

   /*     earnMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInterval = new ViewModelProvider(getActivity()).get(AddInterval.class);
                SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
                String currentDate = (String) android.text.format.DateFormat.format("yy/MM/dd HH:mm:ss", new java.util.Date());

                String type = "shop";
                earningViewModel.getData(String.valueOf(userId), type).observe(getViewLifecycleOwner(), new Observer<Earning_response>() {
                    @Override
                    public void onChanged(Earning_response earning_response) {
                        //Toast.makeText(getActivity(), earning_response.getEarning_id(), Toast.LENGTH_SHORT).show();

                        Earning_Session_Management earning_session_management = new Earning_Session_Management(getActivity());

                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        earning_session_management.saveSession(earning_response.getEarning_id());
                        earning_session_management.saveType(type);
                        earning_session_management.saveBaseId(String.valueOf(userId));
                        startActivity(intent);

                           *//* addInterval.getResponse(earning_response.getEarning_id(), currentDate, 1).observe(getViewLifecycleOwner(), new Observer<addInterval_response>() {

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
                            });*//*


                        *//*fragmentManager.beginTransaction().setCustomAnimations(
                                R.anim.slide_in,  // enter
                                R.anim.fade_out,  // exit
                                R.anim.fade_in,   // popEnter
                                R.anim.slide_out  // popExit
                        ).replace(R.id.frame_container, new Home_fragment(earning_response.getEarning_id())).addToBackStack(null).commit();
                    *//*
                    }
                });

            }
        });
*/
        couponButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_coupon_fragment(shop_id)).addToBackStack(null).commit();
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

        return view;
    }

    @Override
    public void OnItemClick(int position) {
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
}