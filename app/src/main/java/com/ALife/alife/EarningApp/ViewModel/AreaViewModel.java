package com.ALife.alife.EarningApp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.ALife.alife.EarningApp.Model.Registration.Area_repositories;
import com.ALife.alife.EarningApp.Model.Registration.Area_response;

import java.util.List;

public class AreaViewModel extends ViewModel {
    /*public AreaViewModel(@NonNull Application application) {
        super(application);
    }*/
    public LiveData<List<Area_response>> getData() {

        return Area_repositories.getInstance().getMessage();

    }
}
