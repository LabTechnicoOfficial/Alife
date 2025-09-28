package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.Earning_repositories;
import com.alifew.bcopay.model.Earning_response;

public class EarningViewModel extends ViewModel {

    public LiveData<Earning_response> getData(String userID, String type){
        return Earning_repositories.getInstance().getData(userID, type);
    }
}
