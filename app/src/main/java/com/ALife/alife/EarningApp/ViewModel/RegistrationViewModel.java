package com.ALife.alife.EarningApp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.EarningApp.Model.Registration.Registration_repositories;


public class RegistrationViewModel extends ViewModel {


    /*public RegistrationViewModel(@NonNull Application application) {
        super(application);
    }*/

    public LiveData<String> getMessage(String name, String phone, String mail, String area, String referCode, String password) {

        return Registration_repositories.getInstance().getMessage(name, phone, mail, area, referCode, password);

    }
}
