package com.alifew.alifeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.OTP_repositories;
import com.alifew.alifeworld.model.OTP_response;

public class OTP extends ViewModel {
    OTP_repositories repositories;

    public LiveData<OTP_response> getStatus(String to, String text) {
        //repositories = new OTP_repositories(to, text);
        //return repositories.getStatus();
        return OTP_repositories.getInstance().getStatus(to, text);
    }
}
