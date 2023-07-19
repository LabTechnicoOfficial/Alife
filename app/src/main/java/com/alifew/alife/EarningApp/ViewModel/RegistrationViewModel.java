package com.alifew.alife.EarningApp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.EarningApp.Model.Registration.Registration_repositories;


public class RegistrationViewModel extends ViewModel {


    /*public RegistrationViewModel(@NonNull Application application) {
        super(application);
    }*/

    public LiveData<String> getMessage(String name, String phone, String mail, String area, String referCode, String password) {

        return Registration_repositories.getInstance().getMessage(name, phone, mail, area, referCode, password);

    }
}
