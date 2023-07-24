package com.alifew.alife.EarningApp.View.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.alifew.alife.EarningApp.Model.APIUtilize;
import com.alifew.alife.EarningApp.Model.Login.Login_API;
import com.alifew.alife.EarningApp.Model.Login.Login_response;
import com.alifew.alife.EarningApp.Model.Earning_Session_Management;
import com.alifew.alife.EarningApp.ViewModel.LoginViewModel;
import com.alifew.alife.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class Login_fragment extends Fragment {

    TextView registerButton;
    AppCompatButton signInButton;
    TextInputEditText phoneText, passwordText;
    TextInputLayout phoneError, passwordError;
    LoginViewModel loginViewModel;
    Login_API loginApi;
    Earning_Session_Management session_management;
    Dialog loaderDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.earning_login_fragment, container, false);

        session_management = new Earning_Session_Management(getActivity());
        String userID = session_management.getSession();

        if (!userID.equals("-1")) {
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                    R.anim.fade_in,  // enter
                    R.anim.fade_out// popExit
            ).replace(R.id.frame_container, new Home_fragment(userID)).commit();
        }

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginApi = APIUtilize.loginApi();

        registerButton = (TextView) view.findViewById(R.id.registerButton);
        signInButton = (AppCompatButton) view.findViewById(R.id.signInButtonID);
        phoneText = (TextInputEditText) view.findViewById(R.id.contactText);
        passwordText = (TextInputEditText) view.findViewById(R.id.passwordText);

        phoneError = (TextInputLayout) view.findViewById(R.id.phoneErrorID);
        passwordError = (TextInputLayout) view.findViewById(R.id.passwordErrorID);

        loaderDialog = new Dialog(getActivity());
        loaderDialog.setContentView(R.layout.earning_loader_alert);
        loaderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loaderDialog.setCancelable(false);

        /*registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Registration_fragment()).addToBackStack(null).commit();
            }
        });*/

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                phoneError.setErrorEnabled(false);
                passwordError.setErrorEnabled(false);

                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
                    if (TextUtils.isEmpty(phone)) {
                        phoneError.setError(" ");
                    } else if (TextUtils.isEmpty(password)) {
                        passwordError.setError(" ");
                    }
                } else {
                    sendData(phone, password);
                }
            }
        });

        return view;
    }

    private void sendData(String phone, String password) {

        loaderDialog.show();
        loginViewModel.getMessage(phone, password).observe(getViewLifecycleOwner(), new Observer<Login_response>() {
            @Override
            public void onChanged(Login_response login_response) {
                loaderDialog.dismiss();
                String id = login_response.getUserID();

                if (!id.equals("-1")) {
                    getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                            R.anim.fade_in,  // enter
                            R.anim.fade_out// popExit
                    ).replace(R.id.frame_container, new Home_fragment(id)).commit();

                    session_management.saveSession(id);

                } else {
                    Toast.makeText(getActivity(), "Invalid phone/password", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}