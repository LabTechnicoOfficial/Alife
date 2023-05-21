package com.ALife.alife.EarningApp.View.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ALife.alife.EarningApp.Adapter.Notice_adapter;
import com.ALife.alife.EarningApp.Model.AddLimit.addLimit_response;
import com.ALife.alife.EarningApp.Model.AddLimit.update_addLimit_response;
import com.ALife.alife.EarningApp.Model.Notice.Notice_response;
import com.ALife.alife.EarningApp.Model.Profile.Profile_response;
import com.ALife.alife.EarningApp.Model.Reward.Message_response;
import com.ALife.alife.EarningApp.Model.Earning_Session_Management;
import com.ALife.alife.EarningApp.Model.Video_ad.Video_ad_response;
import com.ALife.alife.EarningApp.Model.addRequest.Add_request;
import com.ALife.alife.EarningApp.Model.addRequest.add_request_response;
import com.ALife.alife.EarningApp.View.Activity.MainActivity;
import com.ALife.alife.EarningApp.ViewModel.AddLimit;
import com.ALife.alife.EarningApp.ViewModel.NoticeViewModel;
import com.ALife.alife.EarningApp.ViewModel.ProfileViewModel;
import com.ALife.alife.EarningApp.ViewModel.RewardViewModel;
import com.ALife.alife.EarningApp.ViewModel.VideoADViewModel;
import com.ALife.alife.R;
import com.ALife.alife.view.Customer.Customer_main_activity;
import com.ALife.alife.view.Shop.Shop_main_activity;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.navigation.NavigationView;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.startapp.sdk.adsbase.adlisteners.VideoListener;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.UnityBanners;


import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Home_fragment extends Fragment {
    private int mCurrentPosition = 0;
    VideoView videoPlayerView;
    RewardedAd mRewardedAd;
    String userID, baseId, type, name, balance, phone, pass, refferalID;
    ProfileViewModel profileViewModel;
    NoticeViewModel noticeViewModel;
    TextView userNameText, phoneText, balanceText, referIDText;
    RecyclerView noticeView;
    List<Notice_response> noticeList;
    Notice_adapter adapter;
    LinearLayout myTeamButton, addMemberButton, cashOutButton, referButton, sendMoneyButton, historyButton;
    DrawerLayout drawerLayout;
    //NavigationView navigationView;
    Toolbar toolbar;
    //ActionBarDrawerToggle actionBarDrawerToggle;
    Earning_Session_Management session_management;
    LinearLayout headerView;
    private static String status;
    LinearLayout showAddButton, skipButton;
    //private RewardedAd mRewardedAd;
    private final String TAG = "getActivity";

    // private String GameID = "4147749";
    private String GameID = "4408877";
    private String interPlacement = "Interstitial_Android";
    private String rewardedPlacement = "Rewarded_Ad";
    private boolean testMode = false;
    AddLimit addLimit;
    RewardViewModel rewardViewModel;
    Add_request add_request;
    String dateCurrent, myFormat = "yyyy-MM-dd";
    ImageView backButton;

    private InterstitialAd InterstitialAd;

    VideoADViewModel videoADViewModel;
    Dialog videoAlert;

    public Home_fragment(String userID) {
        this.userID = userID;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                loadRewardAds();
                loadAd();
            }
        });
        UnityAds.initialize(getActivity(), GameID, testMode);

        IUnityAdsListener rewardListener = new IUnityAdsListener() {
            @Override
            public void onUnityAdsReady(String s) {

            }

            @Override
            public void onUnityAdsStart(String s) {

            }

            @Override
            public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {

                if (finishState.equals(UnityAds.FinishState.COMPLETED)) {
                    // do code
                    //Toast.makeText(getActivity(), "referID " + refferalID, Toast.LENGTH_SHORT).show();
                    rewardViewModel.getData(userID, refferalID).observe(getViewLifecycleOwner(), new Observer<Message_response>() {
                        @Override
                        public void onChanged(Message_response message_response) {
                            String message = message_response.getMessage();
                            //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            if (message.equals("ok")) {
                                Toast.makeText(getActivity(), "Reward added", Toast.LENGTH_SHORT).show();
                                profile();
                            } else {
                                Toast.makeText(getActivity(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                } else if (finishState == UnityAds.FinishState.SKIPPED) {
                    // Do not reward the user for skipping the ad.
                    Toast.makeText(getActivity(), "Skipped", Toast.LENGTH_SHORT).show();
                } else if (finishState == UnityAds.FinishState.ERROR) {
                    // Log an error.
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {

            }
        };

        UnityAds.setListener(rewardListener);
        UnityAds.load(interPlacement);
        main();
        profile();
        referIDText.setText(userID);
    }

    private void profile() {
        profileViewModel.getData(userID).observe(getViewLifecycleOwner(), new Observer<Profile_response>() {
            @Override
            public void onChanged(Profile_response profile_response) {
                status = profile_response.getReferStatus();
                name = profile_response.getName();
                userNameText.setText(profile_response.getName());
                phoneText.setText(profile_response.getPhone());
                balance = profile_response.getTotalBalance();
                balanceText.setText(balance);
                //myBalanceText.setText(balance);
                refferalID = profile_response.getRefferal();

            }
        });
    }

    private void main() {

        noticeViewModel = new ViewModelProvider(this).get(NoticeViewModel.class);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        rewardViewModel = new ViewModelProvider(this).get(RewardViewModel.class);
        addLimit = new ViewModelProvider(this).get(AddLimit.class);
        add_request = new ViewModelProvider(this).get(Add_request.class);
        noticeViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<Notice_response>>() {
            @Override
            public void onChanged(List<Notice_response> notice_responses) {
                noticeList = new ArrayList<>();
                noticeList = notice_responses;
                adapter = new Notice_adapter(noticeList);
                noticeView.setAdapter(adapter);

            }
        });


    }

    /*private void loadRewardAdd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(getActivity(), getString(R.string.reward_ads_id),
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d("TAG", loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d("TAG", "Ad was loaded.");
                    }
                });
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.earning_home_fragment, container, false);

        session_management = new Earning_Session_Management(getActivity());

        noticeViewModel = new ViewModelProvider(this).get(NoticeViewModel.class);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        rewardViewModel = new ViewModelProvider(this).get(RewardViewModel.class);
        addLimit = new ViewModelProvider(this).get(AddLimit.class);
        videoADViewModel = new ViewModelProvider(this).get(VideoADViewModel.class);

        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        //navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        headerView = (LinearLayout) view.findViewById(R.id.headerViewID);
        //View header = navigationView.inflateHeaderView(R.layout.earning_header);

        //navigationView.bringToFront();
        //navigationView.setNavigationItemSelectedListener(this);
        //ActionBar
        //actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        //drawerLayout.addDrawerListener(actionBarDrawerToggle);
        //actionBarDrawerToggle.syncState();

        userNameText = (TextView) view.findViewById(R.id.userNameTextID);
        phoneText = (TextView) view.findViewById(R.id.phoneTextID);
        balanceText = (TextView) view.findViewById(R.id.balanceTextID);
        referIDText = (TextView) view.findViewById(R.id.referIDTextID);
        //myBalanceText = (TextView) view.findViewById(R.id.myBalanceTextID);

        myTeamButton = (LinearLayout) view.findViewById(R.id.myTeamButtonID);
        addMemberButton = (LinearLayout) view.findViewById(R.id.addMemberID);
        cashOutButton = (LinearLayout) view.findViewById(R.id.cashOutButtonID);
        referButton = (LinearLayout) view.findViewById(R.id.referButtonID);
        sendMoneyButton = (LinearLayout) view.findViewById(R.id.sendMoneyButtonID);
        showAddButton = (LinearLayout) view.findViewById(R.id.showAddButtonID);
        historyButton = (LinearLayout) view.findViewById(R.id.historyButtonID);

        noticeView = (RecyclerView) view.findViewById(R.id.noticeViewID);
        noticeView.setHasFixedSize(true);
        noticeView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        backButton = (ImageView) view.findViewById(R.id.backButtonID);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Earning_Session_Management earning_session_management = new Earning_Session_Management(getActivity());
                type = earning_session_management.getType();
                if (type.equals("shop")) {
                    startActivity(new Intent(getActivity(), Shop_main_activity.class));
                } else if (type.equals("customer")) {
                    startActivity(new Intent(getActivity(), Customer_main_activity.class));

                }
            }
        });

        myTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.fade_in,  // enter
                        R.anim.fade_out// popExit
                ).replace(R.id.frame_container, new My_team_fragment(userID)).addToBackStack(null).commit();
            }
        });

        cashOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InterstitialAd != null) {
                    InterstitialAd.show(getActivity());
                    getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                            R.anim.fade_in,  // enter
                            R.anim.fade_out// popExit
                    ).replace(R.id.frame_container, new Cash_out_fragment(userID, balance, name)).addToBackStack(null).commit();
                } else {
                    //Toast.makeText(getActivity(), "Ad did not load", Toast.LENGTH_SHORT).show();

                    loadAd();
                    getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                            R.anim.fade_in,  // enter
                            R.anim.fade_out// popExit
                    ).replace(R.id.frame_container, new Cash_out_fragment(userID, balance, name)).addToBackStack(null).commit();
                }

                //startGame();
            }

        });

        referButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileViewModel.getData(userID).observe(getViewLifecycleOwner(), new Observer<Profile_response>() {
                    @Override
                    public void onChanged(Profile_response profile_response) {
                        status = profile_response.getReferStatus();
                        if (status.equals("1")) {
                            Toast.makeText(getActivity(), "Already referred", Toast.LENGTH_SHORT).show();
                        } else {
                            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                                    R.anim.fade_in,  // enter
                                    R.anim.fade_out// popExit
                            ).replace(R.id.frame_container, new Refer_fragment(userID)).addToBackStack(null).commit();

                        }
                    }
                });
            }
        });

        sendMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InterstitialAd != null) {
                    InterstitialAd.show(getActivity());
                    getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                            R.anim.fade_in,  // enter
                            R.anim.fade_out// popExit
                    ).replace(R.id.frame_container, new Send_money_fragment(userID)).addToBackStack(null).commit();


                } else {
                    //Toast.makeText(getActivity(), "Ad did not load", Toast.LENGTH_SHORT).show();

                    loadAd();
                    getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                            R.anim.fade_in,  // enter
                            R.anim.fade_out// popExit
                    ).replace(R.id.frame_container, new Send_money_fragment(userID)).addToBackStack(null).commit();


                    //startGame();
                }
            }
        });

        showAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customize_ad();

                //advertise_function();
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.fade_in,  // enter
                        R.anim.fade_out// popExit
                ).replace(R.id.frame_container, new History_frament(userID)).addToBackStack(null).commit();
            }
        });

        return view;
    }

    private void customize_ad() {

        videoAlert = new Dialog(getActivity());
        videoAlert.setContentView(R.layout.earning_video_player_alert);
        videoAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        videoAlert.setCancelable(false);
        videoAlert.show();

        skipButton = videoAlert.findViewById(R.id.skipButtonID);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rewardViewModel.getData(userID, refferalID).observe(getViewLifecycleOwner(), new Observer<Message_response>() {
                    @Override
                    public void onChanged(Message_response message_response) {
                        String message = message_response.getMessage();
                        //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        if (message.equals("ok")) {
                            addLimit.getResponse(userID).observe(getViewLifecycleOwner(), new Observer<update_addLimit_response>() {
                                @Override
                                public void onChanged(update_addLimit_response update_addLimit_response) {
                                    Toast.makeText(getActivity(), "Reward added", Toast.LENGTH_SHORT).show();
                                    videoAlert.dismiss();
                                    Earning_Session_Management earning_session_management = new Earning_Session_Management(getActivity());
                                    type = earning_session_management.getType();
                                    //Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();

                                    if (type.equals("shop")) {
                                        startActivity(new Intent(getActivity(), Shop_main_activity.class));
                                    } else if (type.equals("customer")) {
                                        startActivity(new Intent(getActivity(), Customer_main_activity.class));
                                    }

                                }
                            });

                        } else {
                            Toast.makeText(getActivity(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        videoPlayerView = videoAlert.findViewById(R.id.videoViewID);

        videoADViewModel.getVideoAD().observe(getViewLifecycleOwner(), new Observer<Video_ad_response>() {
            @Override
            public void onChanged(Video_ad_response video_ad_response) {
                String videoUrl = video_ad_response.getUrl();
                Uri uri = Uri.parse(videoUrl);
                initializePlayer(uri);
            }
        });

    }

    private void initializePlayer(Uri uri) {

        videoPlayerView.setVisibility(View.VISIBLE);
        if (uri != null) {
            videoPlayerView.setVideoURI(uri);

            MediaPlayer mp = MediaPlayer.create(getActivity(), uri);
            long duration = (long) (mp.getDuration()*.4);
            mp.release();

            //Toast.makeText(getActivity(), String.valueOf(duration), Toast.LENGTH_SHORT).show();

            new CountDownTimer(duration, 1000) {

                public void onTick(long millisUntilFinished) {
                    //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                    //Toast.makeText(getActivity(), String.valueOf(millisUntilFinished / 1000), Toast.LENGTH_SHORT).show();
                }

                public void onFinish() {
                    //mTextField.setText("done!");
                    skipButton.setVisibility(View.VISIBLE);
                }
            }.start();
        }


        videoPlayerView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

                if (mCurrentPosition > 0) {
                    videoPlayerView.seekTo(mCurrentPosition);
                } else {
                    // Skipping to 1 shows the first frame of the video.
                    videoPlayerView.seekTo(1);
                }
                videoPlayerView.start();

                // Hide buffering message.
                //mBufferingTextView.setVisibility(VideoView.INVISIBLE);


            }
        });
        // Listener for onCompletion() event (runs after media has finished
        // playing).
        videoPlayerView.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        //Toast.makeText(getActivity(), "The End", Toast.LENGTH_SHORT).show();
                        rewardViewModel.getData(userID, refferalID).observe(getViewLifecycleOwner(), new Observer<Message_response>() {
                            @Override
                            public void onChanged(Message_response message_response) {
                                String message = message_response.getMessage();
                                //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                if (message.equals("ok")) {
                                    addLimit.getResponse(userID).observe(getViewLifecycleOwner(), new Observer<update_addLimit_response>() {
                                        @Override
                                        public void onChanged(update_addLimit_response update_addLimit_response) {
                                            Toast.makeText(getActivity(), "Reward added", Toast.LENGTH_SHORT).show();
                                            videoAlert.dismiss();
                                            Earning_Session_Management earning_session_management = new Earning_Session_Management(getActivity());
                                            type = earning_session_management.getType();
                                            //Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();

                                            if (type.equals("shop")) {
                                                startActivity(new Intent(getActivity(), Shop_main_activity.class));
                                            } else if (type.equals("customer")) {
                                                startActivity(new Intent(getActivity(), Customer_main_activity.class));
                                            }

                                        }
                                    });

                                } else {
                                    Toast.makeText(getActivity(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        //videoAlert.dismiss();
                        // Return the video position to the start.
                        //videoPlayerView.seekTo(0);
                    }
                });
    }

    private void advertise_function() {
        //test

        //test end
        add_request.getData(userID).observe(getViewLifecycleOwner(), new Observer<add_request_response>() {
            @Override
            public void onChanged(add_request_response add_request_response) {
                Log.d("requestvalue", add_request_response.getMessage());
                if (add_request_response.getMessage().equals("success")) {

                    /*int int_random = ThreadLocalRandom.current().nextInt();
                    if (int_random % 2 == 0) {
                        //ast.makeText(getActivity(),String.valueOf(int_random),Toast.LENGTH_SHORT).show();

                        if (mRewardedAd != null) {
                            Activity activityContext = getActivity();
                            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                    // Handle the reward.
                                    rewardViewModel.getData(userID, refferalID).observe(getViewLifecycleOwner(), new Observer<Message_response>() {
                                        @Override
                                        public void onChanged(Message_response message_response) {
                                            String message = message_response.getMessage();
                                            //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                            if (message.equals("ok")) {
                                                Toast.makeText(getActivity(), "Reward added", Toast.LENGTH_SHORT).show();
                                                profile();
                                                addLimit.getResponse(userID).observe(getViewLifecycleOwner(), new Observer<update_addLimit_response>() {
                                                    @Override
                                                    public void onChanged(update_addLimit_response update_addLimit_response) {
                                                        profile();
                                                    }
                                                });

                                            } else {
                                                Toast.makeText(getActivity(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                        @Override
                                        public void onAdShowedFullScreenContent() {
                                            // Called when ad is shown.
                                            Log.d("TAG", "Ad was shown.");
                                        }

                                        @Override
                                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                                            // Called when ad fails to show.
                                            Log.d("TAG", "Ad failed to show.");
                                        }

                                        @Override
                                        public void onAdDismissedFullScreenContent() {
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    loadRewardAds();
                                                }
                                            }, 12000);

                                            mRewardedAd = null;
                                            super.onAdDismissedFullScreenContent();

                                            // Called when ad is dismissed.
                                            // Set the ad reference to null so you don't show the ad a second time.
                                            //Log.d("TAG", "Ad was dismissed.");

                                        }

                                    });
                                }
                            });
                        } else {
                            loadRewardAds();
                            //Toast.makeText(getActivity(), "ads is not available for this time", Toast.LENGTH_SHORT).show();


                        }

                    } else {
                        //Toast.makeText(getActivity(),String.valueOf(int_random),Toast.LENGTH_SHORT).show();

                        if (UnityAds.isReady(rewardedPlacement)) {
                            UnityAds.show(getActivity(), rewardedPlacement);
                        }


                    }*/

                    SimpleDateFormat objSDF = new SimpleDateFormat("yyyy/MM/dd");

                    String currentDate = (String) android.text.format.DateFormat.format("yyyy/MM/dd", new java.util.Date());
                    addLimit.getCount(userID, currentDate).observe(getViewLifecycleOwner(), new Observer<addLimit_response>() {
                        @Override
                        public void onChanged(addLimit_response addLimit_response) {
                            if (!addLimit_response.getCount().isEmpty()) {
                                if (Integer.parseInt(addLimit_response.getCount()) >= 100) {
                                    Toast.makeText(getActivity(), "Daily Add Show Limit Over", Toast.LENGTH_SHORT).show();
                                } else {
                                    start_app_ad();
                                }
                            }
                        }
                    });
//                if (UnityAds.isReady(rewardedPlacement)) {
//                    UnityAds.show(getActivity(), rewardedPlacement);
//                }

                } else {

                    // when add console busy
                    Toast.makeText(getActivity(), "waiting", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void start_app_ad() {
        final StartAppAd videoAdd = new StartAppAd(getActivity());
        videoAdd.setVideoListener(new VideoListener() {
            @Override
            public void onVideoCompleted() {
                rewardViewModel.getData(userID, refferalID).observe(getViewLifecycleOwner(), new Observer<Message_response>() {
                    @Override
                    public void onChanged(Message_response message_response) {
                        String message = message_response.getMessage();
                        //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        if (message.equals("ok")) {
                            addLimit.getResponse(userID).observe(getViewLifecycleOwner(), new Observer<update_addLimit_response>() {
                                @Override
                                public void onChanged(update_addLimit_response update_addLimit_response) {
                                    Toast.makeText(getActivity(), "Reward added", Toast.LENGTH_SHORT).show();
                                    add_request.updateData(userID).observe(getViewLifecycleOwner(), new Observer<com.ALife.alife.EarningApp.Model.addRequest.add_request_response>() {
                                        @Override
                                        public void onChanged(com.ALife.alife.EarningApp.Model.addRequest.add_request_response add_request_response) {
                                            // profile();
                                            //
                                            Earning_Session_Management earning_session_management = new Earning_Session_Management(getActivity());
                                            type = earning_session_management.getType();
                                            //Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();

                                            if (type.equals("shop")) {
                                                startActivity(new Intent(getActivity(), Shop_main_activity.class));
                                            } else if (type.equals("customer")) {
                                                startActivity(new Intent(getActivity(), Customer_main_activity.class));
                                            }


                                            //
                                        }
                                    });

                                }
                            });

                        } else {
                            Toast.makeText(getActivity(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                // Toast.makeText(getActivity(), "COmplete", Toast.LENGTH_SHORT).show();
            }
        });
        videoAdd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {
                videoAdd.showAd();

            }

            @Override
            public void onFailedToReceiveAd(Ad ad) {
                Toast.makeText(getActivity(), "Failed to Load", Toast.LENGTH_SHORT).show();
            }
        });
                               /* if (Integer.parseInt(addLimit_response.getCount()) < 5) {
                                    if (mRewardedAd != null) {
                                        Activity activityContext = getActivity();
                                        mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                            @Override
                                            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                // Handle the reward.
                                                rewardViewModel.getData(userID, refferalID).observe(getViewLifecycleOwner(), new Observer<Message_response>() {
                                                    @Override
                                                    public void onChanged(Message_response message_response) {
                                                        String message = message_response.getMessage();
                                                        //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                                        if (message.equals("ok")) {
                                                            Toast.makeText(getActivity(), "Reward added", Toast.LENGTH_SHORT).show();
                                                            addLimit.getResponse(userID).observe(getViewLifecycleOwner(), new Observer<update_addLimit_response>() {
                                                                @Override
                                                                public void onChanged(update_addLimit_response update_addLimit_response) {
                                                                    profile();
                                                                }
                                                            });

                                                        } else {
                                                            Toast.makeText(getActivity(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                                mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                    @Override
                                                    public void onAdShowedFullScreenContent() {
                                                        // Called when ad is shown.
                                                        Log.d("TAG", "Ad was shown.");
                                                    }

                                                    @Override
                                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                        // Called when ad fails to show.
                                                        Log.d("TAG", "Ad failed to show.");
                                                    }

                                                    @Override
                                                    public void onAdDismissedFullScreenContent() {
                                                        Handler handler = new Handler();
                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                loadRewardAds();
                                                            }
                                                        }, 12000);

                                                        mRewardedAd = null;
                                                        super.onAdDismissedFullScreenContent();

                                                        // Called when ad is dismissed.
                                                        // Set the ad reference to null so you don't show the ad a second time.
                                                        //Log.d("TAG", "Ad was dismissed.");

                                                    }
                                                });
                                            }
                                        });
                                    } else {
                                        // Toast.makeText(getActivity(), "ads is not available for this time", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    final StartAppAd videoAdd = new StartAppAd(getActivity());
                                    videoAdd.setVideoListener(new VideoListener() {
                                        @Override
                                        public void onVideoCompleted() {
                                            rewardViewModel.getData(userID, refferalID).observe(getViewLifecycleOwner(), new Observer<Message_response>() {
                                                @Override
                                                public void onChanged(Message_response message_response) {
                                                    String message = message_response.getMessage();
                                                    //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                                    if (message.equals("ok")) {
                                                        addLimit.getResponse(userID).observe(getViewLifecycleOwner(), new Observer<update_addLimit_response>() {
                                                            @Override
                                                            public void onChanged(update_addLimit_response update_addLimit_response) {
                                                                Toast.makeText(getActivity(), "Reward added", Toast.LENGTH_SHORT).show();

                                                                profile();
                                                            }
                                                        });

                                                    } else {
                                                        Toast.makeText(getActivity(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                           // Toast.makeText(getActivity(), "COmplete", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    videoAdd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                                        @Override
                                        public void onReceiveAd(Ad ad) {
                                            videoAdd.showAd();

                                        }

                                        @Override
                                        public void onFailedToReceiveAd(Ad ad) {
                                            Toast.makeText(getActivity(), "Failed to Load", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        UnityBanners.destroy();
    }

    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.log_out) {
            session_management.removeSession();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Login_fragment()).commit();
        } else if (item.getItemId() == R.id.nav_history) {

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }*/
    private void loadRewardAds() {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(getActivity(), "getString(R.string.reward_ads_id)",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d("TAG", loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d("TAG", "Ad was loaded.");
                    }
                });
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
                        loadAd();
                    }
                });
    }
}