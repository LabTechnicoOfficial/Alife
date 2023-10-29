package com.alifew.alifeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.Earning_repositories;
import com.alifew.alifeworld.model.Earning_response;

public class EarningViewModel extends ViewModel {

    public LiveData<Earning_response> getData(String userID, String type){
        return Earning_repositories.getInstance().getData(userID, type);
    }
}
