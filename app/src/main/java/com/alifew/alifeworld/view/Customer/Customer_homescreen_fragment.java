package com.alifew.alifeworld.view.Customer;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.Utils.TestData;
import com.alifew.alifeworld.adapter.Instruction_adapter;
import com.alifew.alifeworld.adapter.Slider.CustomerSliderViewAdapter;
import com.alifew.alifeworld.model.slider.Customer_slider_response;
import com.alifew.alifeworld.model.user_instruction_response;
import com.alifew.alifeworld.session.SessionManagement;
import com.alifew.alifeworld.viewmodel.EarningViewModel;
import com.alifew.alifeworld.viewmodel.User_instruction;
import com.alifew.alifeworld.viewmodel.banner.SliderViewModel;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.carousel.CarouselLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Customer_homescreen_fragment extends Fragment implements Instruction_adapter.OnItemClickListener {

    LinearLayout dueListButton, shopListButton, dueShopsButton, earnMoneyButton, couponButton, referButton;
    private String customer_id;
    EarningViewModel earningViewModel;
    FragmentManager fragmentManager;
    User_instruction userInstruction;
    private List<user_instruction_response> instructionList;
    Instruction_adapter instructionAdapter;
    private AdManagerAdView mAdManagerAdView;

    private InterstitialAd InterstitialAd;
    SliderViewModel sliderViewModel;
    List<Customer_slider_response.Slider> bannerList;
    SessionManagement sessionManagement;
    RecyclerView carouselView;

    LinearLayout instructionLayout;
    RecyclerView instructionView;
    private int currentIndex = 0;
    private final Handler autoScrollHandler = new Handler();
    private final int AUTO_SCROLL_INTERVAL = 1500;
    CustomerSliderViewAdapter customerSliderViewAdapter;

    @SuppressLint("MissingPermission")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        MobileAds.initialize(getActivity(), initializationStatus -> loadAd());
        instruction_func();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_homescreen_fragment, container, false);
        initView(view);
        loadSlider();

        shopListButton.setOnClickListener(v -> fragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out
        ).replace(R.id.cus_frame_container, new Customer_shopList_fragment(customer_id)).addToBackStack(null).commit());

        dueListButton.setOnClickListener(v -> fragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out
        ).replace(R.id.cus_frame_container, new Customer_due_list_fragment(customer_id)).addToBackStack(null).commit());

        dueShopsButton.setOnClickListener(v -> fragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out
        ).replace(R.id.cus_frame_container, new Customer_due_shop_fragment(customer_id)).addToBackStack(null).commit());

        couponButton.setOnClickListener(v -> fragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out
        ).replace(R.id.cus_frame_container, new Customer_coupon_fragment(customer_id)).addToBackStack(null).commit());

        referButton.setOnClickListener(v -> fragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out
        ).replace(R.id.cus_frame_container, new Customer_refer_shop_fragment(customer_id)).addToBackStack(null).commit());

        return view;
    }

    private void initView(View view) {
        sessionManagement = new SessionManagement(getActivity());
        sliderViewModel = new ViewModelProvider(getActivity()).get(SliderViewModel.class);

        dueListButton = view.findViewById(R.id.dueListButtonID);
        shopListButton = view.findViewById(R.id.ShopListButtonID);
        dueShopsButton = view.findViewById(R.id.dueShopsLayoutID);
        earnMoneyButton = view.findViewById(R.id.earnMoneyButtonID);
        couponButton = view.findViewById(R.id.couponButtonID);
        earningViewModel = new ViewModelProvider(this).get(EarningViewModel.class);
        fragmentManager = getFragmentManager();
        mAdManagerAdView = view.findViewById(R.id.adManagerAdView);
        customer_id = String.valueOf(sessionManagement.getUserID());
        userInstruction = new ViewModelProvider(getActivity()).get(User_instruction.class);

        instructionLayout = view.findViewById(R.id.instructorLayout);
        referButton = view.findViewById(R.id.referButtonID);

        instructionView = view.findViewById(R.id.instructionView);
        instructionView.setHasFixedSize(true);
        instructionView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        CarouselLayoutManager carouselLayoutManager = new CarouselLayoutManager();
        carouselView = view.findViewById(R.id.carouselView);
        carouselView.setHasFixedSize(true);
        carouselView.setLayoutManager(carouselLayoutManager);
    }

    private void instruction_func() {
        userInstruction.getInstruction("user").observe(getViewLifecycleOwner(), user_instruction_responses -> {
            if (!user_instruction_responses.isEmpty()) {
                instructionList = new ArrayList<>(user_instruction_responses);
                instructionAdapter = new Instruction_adapter(instructionList);
                instructionAdapter.setOnClickListener(Customer_homescreen_fragment.this);
                instructionView.setAdapter(instructionAdapter);
            }
        });
    }

    @Override
    public void OnInstructorItemClick(int position) {
        user_instruction_response response = instructionList.get(position);
        String link = response.getLink();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);
    }

    private void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(getContext(), "ca-app-pub-9914022847917901/8396202139", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        InterstitialAd = interstitialAd;
                        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                InterstitialAd = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                InterstitialAd = null;
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        loadAd();
                    }
                });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void loadSlider() {
        String latitude = sessionManagement.getLatitude();
        String longitude = sessionManagement.getLongitude();

        sliderViewModel.getSliderListByLatLong(latitude, longitude)
                .observe(getViewLifecycleOwner(), customer_slider_responses -> {
                    bannerList = new ArrayList<>();
                    for (Customer_slider_response response : customer_slider_responses) {
                        bannerList.addAll(response.sliders);
                    }

                    //bannerList.addAll(TestData.bannerList);

                    if (bannerList.isEmpty()) {
                        carouselView.setVisibility(GONE);
                        customerSliderViewAdapter = new CustomerSliderViewAdapter(new ArrayList<>());
                        carouselView.setAdapter(customerSliderViewAdapter);
                        stopAutoScroll();
                        return;
                    } else {
                        carouselView.setVisibility(VISIBLE);
                    }

                    customerSliderViewAdapter = new CustomerSliderViewAdapter(bannerList);
                    carouselView.setAdapter(customerSliderViewAdapter);

                    SnapHelper snapHelper = new LinearSnapHelper();
                    snapHelper.attachToRecyclerView(carouselView);

                    carouselView.setOnTouchListener((v, event) -> {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                stopAutoScroll();
                                break;
                            case MotionEvent.ACTION_UP:
                            case MotionEvent.ACTION_CANCEL:
                                startAutoScroll();
                                break;
                        }
                        return false;
                    });

                    if (customerSliderViewAdapter.getItemCount() > 0) {
                        startAutoScroll();
                    }
                });
    }

    private void stopAutoScroll() {
        autoScrollHandler.removeCallbacks(autoScrollRunnable);
    }

    private void startAutoScroll() {
        stopAutoScroll(); // avoid duplicates
        autoScrollHandler.postDelayed(autoScrollRunnable, AUTO_SCROLL_INTERVAL);
    }

    final Runnable autoScrollRunnable = new Runnable() {
        @Override
        public void run() {
            if (customerSliderViewAdapter == null || customerSliderViewAdapter.getItemCount() == 0) {
                return;
            }
            currentIndex = (currentIndex + 1) % customerSliderViewAdapter.getItemCount();
            carouselView.smoothScrollToPosition(currentIndex);
            autoScrollHandler.postDelayed(this, AUTO_SCROLL_INTERVAL);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (customerSliderViewAdapter != null && customerSliderViewAdapter.getItemCount() > 0) {
            startAutoScroll();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAutoScroll();
    }
}
