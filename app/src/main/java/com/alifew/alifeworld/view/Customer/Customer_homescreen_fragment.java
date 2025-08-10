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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.adapter.Instruction_adapter;
import com.alifew.alifeworld.adapter.Slider.CustomerSliderViewAdapter;
import com.alifew.alifeworld.model.slider.Customer_slider_response;
import com.alifew.alifeworld.model.user_instruction_response;
import com.alifew.alifeworld.viewmodel.EarningViewModel;
import com.alifew.alifeworld.session.SessionManagement;
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
/*import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;*/

import java.util.ArrayList;
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

    //SliderView imageSliderView;
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
        MobileAds.initialize(requireActivity(), initializationStatus -> loadAd());

        instruction_func();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.customer_homescreen_fragment, container, false);

        initView(view);
        loadSlider();

        shopListButton.setOnClickListener(v -> fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.cus_frame_container, new Customer_shopList_fragment(customer_id)).addToBackStack(null).commit());

        dueListButton.setOnClickListener(v -> fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.cus_frame_container, new Customer_due_list_fragment(customer_id)).addToBackStack(null).commit());

        dueShopsButton.setOnClickListener(v -> fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.cus_frame_container, new Customer_due_shop_fragment(customer_id)).addToBackStack(null).commit());

        couponButton.setOnClickListener(v -> fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.cus_frame_container, new Customer_coupon_fragment(customer_id)).addToBackStack(null).commit());

        referButton.setOnClickListener(v ->
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.cus_frame_container, new Customer_refer_shop_fragment(customer_id)).addToBackStack(null).commit()
        );

        return view;
    }

    private void initView(View view) {
        sessionManagement = new SessionManagement(requireActivity());
        sliderViewModel = new ViewModelProvider(requireActivity()).get(SliderViewModel.class);
        //imageSliderView = view.findViewById(R.id.imageSliderView);

        dueListButton = view.findViewById(R.id.dueListButtonID);
        shopListButton = view.findViewById(R.id.ShopListButtonID);
        dueShopsButton = view.findViewById(R.id.dueShopsLayoutID);
        earnMoneyButton = view.findViewById(R.id.earnMoneyButtonID);
        couponButton = view.findViewById(R.id.couponButtonID);

        earningViewModel = new ViewModelProvider(this).get(EarningViewModel.class);

        fragmentManager = getFragmentManager();
        //banner add
        mAdManagerAdView = view.findViewById(R.id.adManagerAdView);


        customer_id = String.valueOf(sessionManagement.getUserID());

        userInstruction = new ViewModelProvider(requireActivity()).get(User_instruction.class);

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


        userInstruction.getInstruction("user").observe(getViewLifecycleOwner(), new Observer<List<user_instruction_response>>() {
            @Override
            public void onChanged(List<user_instruction_response> user_instruction_responses) {

                if (!user_instruction_responses.isEmpty()) {

                    instructionList = new ArrayList<>();
                    instructionList = user_instruction_responses;
                    instructionAdapter = new Instruction_adapter(instructionList);
                    instructionAdapter.setOnClickListener(Customer_homescreen_fragment.this);
                    instructionView.setAdapter(instructionAdapter);
                }
            }
        });
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
        InterstitialAd.load(requireActivity(), "ca-app-pub-9914022847917901/8396202139", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                InterstitialAd = interstitialAd;
                Log.i("msg", "onAdLoaded");
                //showInterstitial();
                //Toast.makeText(getActivity(), "onAdLoaded()", Toast.LENGTH_SHORT).show();
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        InterstitialAd = null;
                        Log.d("TAG", "The ad was dismissed.");
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
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

    private void loadSlider() {

        String latitude = sessionManagement.getLatitude();
        String longitude = sessionManagement.getLongitude();
        sliderViewModel.getSliderListByLatLong(latitude, longitude).observe(getViewLifecycleOwner(), new Observer<>() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onChanged(List<Customer_slider_response> customer_slider_responses) {
                bannerList = new ArrayList<>();

                for (int i = 0; i < customer_slider_responses.size(); i++) {
                    bannerList.addAll(customer_slider_responses.get(i).sliders);
                }

                if (bannerList.isEmpty()) {
                    carouselView.setVisibility(GONE);
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

            }
        });

    }

    private void stopAutoScroll() {
        autoScrollHandler.removeCallbacks(autoScrollRunnable);
    }

    private void startAutoScroll() {
        autoScrollHandler.postDelayed(autoScrollRunnable, AUTO_SCROLL_INTERVAL);
    }

    final Runnable autoScrollRunnable = new Runnable() {
        @Override
        public void run() {
            if (customerSliderViewAdapter.getItemCount() == 0) return;
            currentIndex = (currentIndex + 1) % customerSliderViewAdapter.getItemCount();
            carouselView.smoothScrollToPosition(currentIndex);

            autoScrollHandler.postDelayed(this, AUTO_SCROLL_INTERVAL);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopAutoScroll(); // Prevent handler leak
    }

    @Override
    public void onResume() {
        super.onResume();
        startAutoScroll();
    }
}
