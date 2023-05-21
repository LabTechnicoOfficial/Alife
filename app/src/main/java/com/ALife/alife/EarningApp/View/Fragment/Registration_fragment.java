package com.ALife.alife.EarningApp.View.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.ALife.alife.EarningApp.Model.APIUtilize;
import com.ALife.alife.EarningApp.Model.Registration.Area_response;
import com.ALife.alife.EarningApp.Model.Registration.Registration_API;
import com.ALife.alife.EarningApp.ViewModel.AreaViewModel;
import com.ALife.alife.EarningApp.ViewModel.RegistrationViewModel;
import com.ALife.alife.EarningApp.ViewModel.UserCheck_ViewModel;
import com.ALife.alife.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class Registration_fragment extends Fragment {
    ImageView backButton;
    TextInputEditText nameText, phoneText, mailText, passwordText, rePasswordText, referText;
    TextInputLayout nameError, phoneError, mailError, passwordError, rePasswordError, referError;
    AppCompatButton submitButton;
    Registration_API registration_interface;
    Dialog loaderDialog;
    RegistrationViewModel registrationViewModel;
    UserCheck_ViewModel userCheckViewModel;
    AreaViewModel areaViewModel;
    String userID, area;
    Spinner areaSpinner;
    List<Area_response> items;
    String areaItems[];

    public Registration_fragment(String userID) {
        this.userID = userID;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        areafunc();
    }

    private void areafunc() {
        areaViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<Area_response>>() {
            @Override
            public void onChanged(List<Area_response> area_responses) {
                items = new ArrayList<>();
                items = area_responses;
                areaItems = new String[items.size() + 1];
                areaItems[0] = "none";
                for (int i = 0; i < items.size(); i++) {
                    areaItems[i + 1] = items.get(i).getArea();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, areaItems);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(adapter);
            }
        });

        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                area = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.earning_registration_fragment, container, false);

        registration_interface = APIUtilize.registrationInterface();
        registrationViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        userCheckViewModel = new ViewModelProvider(this).get(UserCheck_ViewModel.class);
        areaViewModel = new ViewModelProvider(this).get(AreaViewModel.class);

        loaderDialog = new Dialog(getActivity());
        loaderDialog.setContentView(R.layout.earning_loader_alert);
        loaderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loaderDialog.setCancelable(false);

        backButton = (ImageView) view.findViewById(R.id.backButtonID);

        nameText = (TextInputEditText) view.findViewById(R.id.nameTextID);
        phoneText = (TextInputEditText) view.findViewById(R.id.phoneTextID);
        mailText = (TextInputEditText) view.findViewById(R.id.mailTextID);
        passwordText = (TextInputEditText) view.findViewById(R.id.passwordTextID);
        rePasswordText = (TextInputEditText) view.findViewById(R.id.retypePasswordTextID);
        referText = (TextInputEditText) view.findViewById(R.id.referTextID);

        nameError = (TextInputLayout) view.findViewById(R.id.nameErrorID);
        phoneError = (TextInputLayout) view.findViewById(R.id.phoneErrorID);
        mailError = (TextInputLayout) view.findViewById(R.id.mailErrorID);
        referError = (TextInputLayout) view.findViewById(R.id.referErrorID);
        passwordError = (TextInputLayout) view.findViewById(R.id.passwordErrorID);
        rePasswordError = (TextInputLayout) view.findViewById(R.id.retypePasswordErrorID);

        areaSpinner = (Spinner) view.findViewById(R.id.offerTypeSpinnerID);

        submitButton = (AppCompatButton) view.findViewById(R.id.submitButtonID);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString().trim();
                String phone = phoneText.getText().toString().trim();
                String mail = mailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                String rePassword = rePasswordText.getText().toString().trim();
                String referCode = referText.getText().toString().trim();

                validation(name, phone, mail, area, referCode, password, rePassword);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Login_fragment()).commit();
            }
        });

        return view;
    }

    private void validation(String name, String phone, String mail, String area, String referCode, String password, String rePassword) {
        nameError.setErrorEnabled(false);
        phoneError.setErrorEnabled(false);
        mailError.setErrorEnabled(false);
        referError.setErrorEnabled(false);
        passwordError.setErrorEnabled(false);
        rePasswordError.setErrorEnabled(false);

        if (TextUtils.isEmpty(name)) {
            nameError.setError(" ");
        } else if (TextUtils.isEmpty(phone)) {
            phoneError.setError(" ");
        } else if (TextUtils.isEmpty(mail)) {
            mailError.setError(" ");
        } else if (TextUtils.isEmpty(referCode)) {
            referError.setError(" ");
        } else if (area.equals("none")) {
            Toast.makeText(getActivity(), area, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password) || password.length() < 6) {

            if (TextUtils.isEmpty(password)) {
                passwordError.setError(" ");
            } else if (password.length() < 6) {
                passwordError.setError("Password must be 6 digit");
            }

        } else if (TextUtils.isEmpty(rePassword)) {
            rePasswordError.setError(" ");
        } else {

            if (!password.equals(rePassword)) {
                rePasswordError.setError("Not match");
            } else {
                loaderDialog.show();
                registration(name, phone, mail, area, referCode, password);
            }

        }
    }

    //usercheck
    private void userCheck(String name, String phone, String mail, String area, String referCode, String password) {

        userCheckViewModel.userCheck(phone).observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("1")) {
                    loaderDialog.dismiss();
                    Toast.makeText(getActivity(), "Already registered", Toast.LENGTH_LONG).show();
                } else if (s.equals("0")) {
                    registration(name, phone, mail, area, referCode, password);
                }
            }
        });

    }

    //registration
    private void registration(String name, String phone, String mail, String area, String referCode, String password) {

        registrationViewModel.getMessage(name, phone, mail, area, referCode, password).observe(Registration_fragment.this, new Observer<String>() {

            @Override
            public void onChanged(String s) {
                loaderDialog.dismiss();
                if (s.equals("1")) {
                    Toast.makeText(getActivity(), "Registered", Toast.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Login_fragment()).commit();
                } else {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}