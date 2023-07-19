package com.alifew.alife.EarningApp.View.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alifew.alife.EarningApp.Model.Earning_Session_Management;
import com.alifew.alife.EarningApp.Model.Profile.Profile_response;
import com.alifew.alife.EarningApp.Model.SendMoney.sendMoney_response;
import com.alifew.alife.EarningApp.Model.UserValidation.userValidation_response;
import com.alifew.alife.EarningApp.Model.VerifyPassword.verifyPassword_response;
import com.alifew.alife.EarningApp.ViewModel.ProfileViewModel;
import com.alifew.alife.EarningApp.ViewModel.SendMoneyViewModel;
import com.alifew.alife.EarningApp.ViewModel.UserValidation;
import com.alifew.alife.EarningApp.ViewModel.Verifypassword;
import com.alifew.alife.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Send_money_fragment extends Fragment {

    ImageView backButton;
    String userID, baseId, type;
    TextView dateText;
    Button sendButton;
    TextInputEditText phoneText, amountText, passwordText;
    TextInputLayout phoneError, amountTError, passwordError;
    ProfileViewModel profileViewModel;
    Verifypassword verifypassword;
    UserValidation userValidation;
    SendMoneyViewModel sendMoneyViewModel;
    Dialog loaderDialog;
    Earning_Session_Management earning_session_management;
    public Send_money_fragment(String userID) {
        this.userID = userID;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.earning_send_money_fragment, container, false);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        verifypassword = new ViewModelProvider(this).get(Verifypassword.class);
        userValidation = new ViewModelProvider(this).get(UserValidation.class);
        sendMoneyViewModel = new ViewModelProvider(this).get(SendMoneyViewModel.class);
        earning_session_management = new Earning_Session_Management(getActivity());
        type = earning_session_management.getType();
        baseId=earning_session_management.getBaseid();
        backButton = (ImageView) view.findViewById(R.id.backButtonID);
        backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.fade_in,  // enter
                R.anim.fade_out// popExit
        ).replace(R.id.frame_container, new Home_fragment(userID)).commit());

        dateText = (TextView) view.findViewById(R.id.dateTextID);
        phoneText = (TextInputEditText) view.findViewById(R.id.phoneTextID);
        amountText = (TextInputEditText) view.findViewById(R.id.amountTextID);
        passwordText = view.findViewById(R.id.passwordTextID);
        phoneError = (TextInputLayout) view.findViewById(R.id.phoneErrorID);
        amountTError = (TextInputLayout) view.findViewById(R.id.amountErrorID);
        passwordError = (TextInputLayout) view.findViewById(R.id.passwordErrorID);
        sendButton = (Button) view.findViewById(R.id.sendButtonID);

        String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        dateText.setText(timeStamp);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phoneText.getText().toString().trim();
                String amount = amountText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                phoneError.setErrorEnabled(false);
                amountTError.setErrorEnabled(false);
                passwordError.setErrorEnabled(false);

                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(amount) || TextUtils.isEmpty(password)) {
                    if (TextUtils.isEmpty(phone)) {
                        phoneError.setError(" ");
                    } else if (TextUtils.isEmpty(amount)) {
                        amountTError.setError(" ");
                    } else if (TextUtils.isEmpty(password)) {
                        passwordError.setError(" ");
                    }
                } else {
                    verifypassword.getResponse(baseId, password, type).observe(getViewLifecycleOwner(), new Observer<verifyPassword_response>() {
                        @Override
                        public void onChanged(verifyPassword_response verifyPassword_response) {
                            if (verifyPassword_response.getMessage().equals("successfull")) {
                                profileViewModel.getData(userID).observe(getViewLifecycleOwner(), new Observer<Profile_response>() {
                                    @Override
                                    public void onChanged(Profile_response profile_response) {
                                        String p_balance = profile_response.getTotalBalance();

                                        String p_phone = profile_response.getPhone();
                                        String username = profile_response.getName();


                                        if (Double.parseDouble(amount) <= Double.parseDouble(p_balance)) {
                                            user_number_validation(phone, p_phone, p_balance, username, amount);
                                        } else {
                                            amountTError.setError("Insufficient balance");
                                        }

                                    }
                                });
                            } else {
                                passwordError.setError("Wrong password");
                            }
                        }
                    });


                }
            }
        });

        loaderDialog = new Dialog(getActivity());
        loaderDialog.setContentView(R.layout.earning_loader_alert);
        loaderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loaderDialog.setCancelable(false);
        return view;
    }

    private void user_number_validation(String rec_phone, String user_phone, String user_balance, String user_name, String amount) {
        String date = dateText.getText().toString().trim();

        userValidation.getResponse(rec_phone, "abc##def").observe(getViewLifecycleOwner(), new Observer<userValidation_response>() {
            @Override
            public void onChanged(userValidation_response userValidation_response) {
                String rec_id = userValidation_response.getUser_id();
                String rec_name = userValidation_response.getUser_name();
                String rec_balance = userValidation_response.getUser_balance();
                //Toast.makeText(getActivity(), rec_id, Toast.LENGTH_SHORT).show();
                if (!rec_id.equals("0")) {
                    send_money_func(userID, user_name, user_phone, rec_id, rec_name, rec_phone, amount, date, rec_balance, user_balance);
                } else {
                    phoneError.setError("User not found");
                }
            }
        });
    }

    private void send_money_func(String userID, String user_name, String user_phone, String rec_id, String rec_name, String rec_phone, String amount, String date, String rec_balance, String user_balance) {
        Double u_balance = Double.parseDouble(user_balance) - Double.parseDouble(amount);
        //Toast.makeText(getActivity(), String.valueOf("User "+u_balance), Toast.LENGTH_SHORT).show();
        Double r_balance = Double.parseDouble(rec_balance) + Double.parseDouble(amount);
        //Toast.makeText(getActivity(), String.valueOf("Receiver "+r_balance), Toast.LENGTH_SHORT).show();
        loaderDialog.show();
        sendMoneyViewModel.getResponse( rec_id, rec_name, rec_phone,userID, user_name, user_phone, amount, date, String.valueOf(r_balance), String.valueOf(u_balance)).observe(getViewLifecycleOwner(), new Observer<sendMoney_response>() {
            @Override
            public void onChanged(sendMoney_response sendMoney_response) {
                String message = sendMoney_response.getMessage();

                loaderDialog.dismiss();
                if (message.equals("Successfully")) {
                    Toast.makeText(getActivity(), "Transfer successful", Toast.LENGTH_SHORT).show();

                    getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                            R.anim.fade_in,  // enter
                            R.anim.fade_out// popExit
                    ).replace(R.id.frame_container, new Home_fragment(userID)).commit();
                } else {
                    Toast.makeText(getActivity(), R.string.something_wrong, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}