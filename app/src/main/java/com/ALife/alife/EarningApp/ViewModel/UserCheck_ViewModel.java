package com.ALife.alife.EarningApp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.EarningApp.Model.Registration.UserCheck_repositories;


public class UserCheck_ViewModel extends ViewModel {
    /*public UserCheck_ViewModel(@NonNull Application application) {
        super(application);
    }*/

    public LiveData<String> userCheck(String phone) {

        return UserCheck_repositories.getInstance().userCheck(phone);

    }
}
