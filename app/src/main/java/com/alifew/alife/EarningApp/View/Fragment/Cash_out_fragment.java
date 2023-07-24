package com.alifew.alife.EarningApp.View.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alifew.alife.EarningApp.Model.CashOut.AddAmount_response;
import com.alifew.alife.EarningApp.Model.CashOut.Commission_response;
import com.alifew.alife.EarningApp.Model.CashOut.Message_response;
import com.alifew.alife.EarningApp.Model.CashOut.Method_response;
import com.alifew.alife.EarningApp.Model.CashOut.cashOut_request_response;
import com.alifew.alife.EarningApp.ViewModel.CashOut;
import com.alifew.alife.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Cash_out_fragment extends Fragment implements AdapterView.OnItemSelectedListener {

    ImageView backButton;
    String userID, balance, username;
    TextView balanceText;
    TextInputEditText phoneText;
    TextInputLayout phoneError;
    TextView dateText;
    AppCompatButton submitButton;
    CashOut cashOut;
    Dialog loaderDialog;
    Spinner methodSpinner, amountSpinner;
    //String[] methods = {"bKash", "Rocket", "Nagad"};
    String paymethod_method;
    private List<Method_response> methodList;
    private List<AddAmount_response> amountList;
    String amount;
    private static final String AD_UNIT_ID =" ca-app-pub-9914022847917901/8146509442";
    private InterstitialAd InterstitialAd;
    public Cash_out_fragment(String userID, String balance, String username) {
        this.userID = userID;
        this.balance = balance;
        this.username = username;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                loadAd();
            }
        });
        showInterstitial();



    }

    private void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                getActivity(),
                AD_UNIT_ID,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        InterstitialAd = interstitialAd;
                        Log.i("msg", "onAdLoaded");
                        showInterstitial();
                       // Toast.makeText(getActivity(), "onAdLoaded()", Toast.LENGTH_SHORT).show();
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
    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (InterstitialAd != null) {
            InterstitialAd.show(getActivity());
            method_func();
            amount_func();
        } else {
            //Toast.makeText(getActivity(), "Ad did not load", Toast.LENGTH_SHORT).show();
            method_func();
            amount_func();
            loadAd();
           //startGame();
        }
    }
    private void amount_func() {

        cashOut.getAmounts().observe(getViewLifecycleOwner(), new Observer<List<AddAmount_response>>() {
            @Override
            public void onChanged(List<AddAmount_response> addAmount_responses) {
                amountList = new ArrayList<>();
                amountList = addAmount_responses;

                String[] amounts = new String[amountList.size()];

                for (int i = 0; i < amountList.size(); i++) {
                    amounts[i] = addAmount_responses.get(i).getAmount();
                }

                ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, amounts);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                amountSpinner.setAdapter(adapter);
            }
        });
    }

    private void method_func() {

        cashOut.getMethod().observe(getViewLifecycleOwner(), new Observer<List<Method_response>>() {
            @Override
            public void onChanged(List<Method_response> method_responses) {
                methodList = new ArrayList<>();
                methodList = method_responses;

                String[] methods = new String[methodList.size()];

                for (int i = 0; i < methodList.size(); i++) {
                    methods[i] = method_responses.get(i).getMethod();
                }

                ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, methods);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                methodSpinner.setAdapter(adapter);
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.earning_cash_out_fragment, container, false);
        cashOut = new ViewModelProvider(this).get(CashOut.class);

        backButton = (ImageView) view.findViewById(R.id.backButtonID);
        backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.fade_in,  // enter
                R.anim.fade_out// popExit
        ).replace(R.id.frame_container, new Home_fragment(userID)).commit());

        balanceText = (TextView) view.findViewById(R.id.balanceTextID);
        dateText = (TextView) view.findViewById(R.id.dateTextID);

        phoneText = (TextInputEditText) view.findViewById(R.id.contactText);

        phoneError = (TextInputLayout) view.findViewById(R.id.phoneErrorID);
        submitButton = (AppCompatButton) view.findViewById(R.id.submitButtonID);

        loaderDialog = new Dialog(getActivity());
        loaderDialog.setContentView(R.layout.earning_loader_alert);
        loaderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loaderDialog.setCancelable(false);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        dateText.setText(formattedDate);
        balanceText.setText(balance);

        methodSpinner = (Spinner) view.findViewById(R.id.methodSpinnerID);
        amountSpinner = (Spinner) view.findViewById(R.id.amountSpinnerID);
        methodSpinner.setOnItemSelectedListener(this);
        amountSpinner.setOnItemSelectedListener(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = phoneText.getText().toString().trim();
                String date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                phoneError.setErrorEnabled(false);
//                amountError.setErrorEnabled(false);

                if (TextUtils.isEmpty(phone)) {
                    phoneError.setError(" ");
                } else {
                    //Toast.makeText(getActivity(), paymethod_method + " " + amount, Toast.LENGTH_SHORT).show();
                    Double value = Double.parseDouble(balanceText.getText().toString().trim()) - Double.parseDouble(amount);

                    if (value < 0) {
                        Toast.makeText(getActivity(), "Amount exceed balance", Toast.LENGTH_SHORT).show();
                    } else {
                        balanceText.setText(String.valueOf(value));

                        withdraw_func(userID, username, phone, amount, String.valueOf(value), date);
                    }

                }
            }
        });

        return view;
    }

    private void withdraw_func(String userID, String username, String phone, String amount, String balance, String date) {
        loaderDialog.show();

        cashOut.getCommission().observe(getViewLifecycleOwner(), new Observer<Commission_response>() {
            @Override
            public void onChanged(Commission_response commission_response) {
                loaderDialog.dismiss();

                if (!commission_response.getComission().equals("-1")) {
                    Double w_balance = Double.parseDouble(amount) - (Double.parseDouble(amount) * (Double.parseDouble(commission_response.getComission()) / 100));
                    Double a_balance = Double.parseDouble(amount) - w_balance;
                    cashOut.getCashOutResponse(userID, username, phone, String.valueOf(w_balance), balance, date, paymethod_method).observe(getViewLifecycleOwner(), new Observer<cashOut_request_response>() {
                        @Override
                        public void onChanged(cashOut_request_response cashOut_request_response) {

                            String message = cashOut_request_response.getMessage();

                            loaderDialog.dismiss();
                            if (message.equals("added successfully")) {

                                cashOut.getMessage(String.valueOf(a_balance)).observe(getViewLifecycleOwner(), new Observer<Message_response>() {
                                    @Override
                                    public void onChanged(Message_response message_response) {

                                        loaderDialog.dismiss();
                                        Toast.makeText(getActivity(), "Request sent for approval", Toast.LENGTH_SHORT).show();

                                        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                                                R.anim.fade_in,  // enter
                                                R.anim.fade_out// popExit
                                        ).replace(R.id.frame_container, new Home_fragment(userID)).commit();
                                    }
                                });
                            } else {
                                Toast.makeText(getActivity(), R.string.something_wrong, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.methodSpinnerID) {
            paymethod_method = String.valueOf(parent.getItemAtPosition(position));
        } else if (parent.getId() == R.id.amountSpinnerID) {
            amount = String.valueOf(parent.getItemAtPosition(position));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}