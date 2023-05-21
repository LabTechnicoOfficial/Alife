package com.ALife.alife.EarningApp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.EarningApp.Model.VerifyPassword.verifyPassword_response;
import com.ALife.alife.EarningApp.Model.VerifyPassword.verifyPassword_repositories;

public class Verifypassword extends ViewModel {
    public LiveData<verifyPassword_response> getResponse(String id, String password, String type) {
        return verifyPassword_repositories.getInstance().getData(id, password, type);
    }
}
