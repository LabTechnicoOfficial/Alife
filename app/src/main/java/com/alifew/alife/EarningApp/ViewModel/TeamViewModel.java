package com.alifew.alife.EarningApp.ViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;


import com.alifew.alife.EarningApp.Model.Team.Team_repositories;
import com.alifew.alife.EarningApp.Model.Team.Team_response;

import java.util.List;

public class TeamViewModel extends ViewModel {
    /*public TeamViewModel(@NonNull Application application) {
        super(application);
    }*/
    public LiveData<List<Team_response>> getData(String userID) {

        return Team_repositories.getInstance().getMessage(userID);

    }
}
