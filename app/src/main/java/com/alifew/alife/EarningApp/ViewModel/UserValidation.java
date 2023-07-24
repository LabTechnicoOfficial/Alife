package com.alifew.alife.EarningApp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.EarningApp.Model.UserValidation.userValidation_repositories;
import com.alifew.alife.EarningApp.Model.UserValidation.userValidation_response;


public class UserValidation extends ViewModel {
    public LiveData<userValidation_response> getResponse(String phone, String token)
    {
        return userValidation_repositories.getInstance().getData(phone, token);
    }
}
