package com.alifew.alife.view.Customer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.adapter.Instruction_adapter;
import com.alifew.alife.model.user_instruction_response;
import com.alifew.alife.viewmodel.EarningViewModel;
import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.viewmodel.User_instruction;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;
import java.util.List;

public class Customer_homescreen_fragment extends Fragment implements Instruction_adapter.OnItemClickListener {
    LinearLayout dueListButton, shopListButton, dueShopsButton, earnMoneyButton, couponButton;
    private String customer_id;
    EarningViewModel earningViewModel;
    FragmentManager fragmentManager;
    User_instruction userInstruction;
    private List<user_instruction_response> instructionList;
    Instruction_adapter instructionAdapter;
    private AdManagerAdView mAdManagerAdView;

    private InterstitialAd InterstitialAd;
    @SuppressLint("MissingPermission")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

                loadAd();
            }
        });


        main();
       // instruction_func();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.customer_homescreen_fragment, container, false);

        dueListButton = (LinearLayout) view.findViewById(R.id.dueListButtonID);
        shopListButton = (LinearLayout) view.findViewById(R.id.ShopListButtonID);
        dueShopsButton = (LinearLayout) view.findViewById(R.id.dueShopsLayoutID);
        earnMoneyButton = (LinearLayout) view.findViewById(R.id.earnMoneyButtonID);
        couponButton = (LinearLayout) view.findViewById(R.id.couponButtonID);

        earningViewModel = new ViewModelProvider(this).get(EarningViewModel.class);

        fragmentManager = getFragmentManager();
        //banner add
        mAdManagerAdView =view.findViewById(R.id.adManagerAdView);

        SessionManagement sessionManagement = new SessionManagement(getActivity());
        int userId = sessionManagement.getSession();
        customer_id = String.valueOf(userId);

        shopListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.cus_frame_container, new Customer_shopList_fragment(customer_id)).addToBackStack(null).commit();
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
                ).replace(R.id.cus_frame_container, new Customer_due_list_fragment(customer_id)).addToBackStack(null).commit();
            }
        });

        dueShopsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.cus_frame_container, new Customer_due_shop_fragment(customer_id)).addToBackStack(null).commit();
            }
        });

        couponButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.cus_frame_container, new Customer_coupon_fragment(customer_id)).addToBackStack(null).commit();
            }
        });

        return view;
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
                    instructionAdapter.setOnClickListener(Customer_homescreen_fragment.this::OnItemClick);
                    instructionView.setAdapter(instructionAdapter);
                }
            }
        });
    }

    private void main() {

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
                        loadAd();
                    }
                });
    }
}
