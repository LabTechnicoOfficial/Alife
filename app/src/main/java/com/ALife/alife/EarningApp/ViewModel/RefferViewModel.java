package com.ALife.alife.EarningApp.ViewModel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.EarningApp.Model.Reffer.Reffer_repositories;

public class RefferViewModel extends ViewModel {
    /*public RefferViewModel(@NonNull Application application) {
        super(application);
    }*/

    public LiveData<String> getData(String userID, String referID) {

        return Reffer_repositories.getInstance().getMessage(userID, referID);

    }
}
