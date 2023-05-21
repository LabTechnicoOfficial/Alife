package com.ALife.alife.EarningApp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.EarningApp.Model.Login.Login_repositories;
import com.ALife.alife.EarningApp.Model.Login.Login_response;


public class LoginViewModel extends ViewModel {
    /*public LoginViewModel(@NonNull Application application) {
        super(application);
    }*/

    public LiveData<Login_response> getMessage(String phone, String password) {

        return Login_repositories.getInstance().getMessage(phone, password);

    }
}
