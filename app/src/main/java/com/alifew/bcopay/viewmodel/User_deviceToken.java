package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.getUser_deviceToken_response;
import com.alifew.bcopay.model.getUser_deviceToken_repositories;

public class User_deviceToken extends ViewModel {
    public LiveData<getUser_deviceToken_response> getToken(String user_id, String user_type) {
        return getUser_deviceToken_repositories.getInstance().getData(user_id, user_type);
    }
    public LiveData<getUser_deviceToken_response> getMessage(String user_id, String user_type,String token) {
        return getUser_deviceToken_repositories.getInstance().getMessage(user_id, user_type,token);
    }
}
