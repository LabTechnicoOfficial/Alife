package com.ALife.alife.EarningApp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;


import com.ALife.alife.EarningApp.Model.Team.Team_repositories;
import com.ALife.alife.EarningApp.Model.Team.Team_response;

import java.util.List;

public class TeamViewModel extends ViewModel {
    /*public TeamViewModel(@NonNull Application application) {
        super(application);
    }*/
    public LiveData<List<Team_response>> getData(String userID) {

        return Team_repositories.getInstance().getMessage(userID);

    }
}
