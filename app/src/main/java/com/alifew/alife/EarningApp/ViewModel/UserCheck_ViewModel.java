package com.alifew.alife.EarningApp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.EarningApp.Model.Registration.UserCheck_repositories;


public class UserCheck_ViewModel extends ViewModel {
    /*public UserCheck_ViewModel(@NonNull Application application) {
        super(application);
    }*/

    public LiveData<String> userCheck(String phone) {

        return UserCheck_repositories.getInstance().userCheck(phone);

    }
}
