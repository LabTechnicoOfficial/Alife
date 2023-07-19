package com.alifew.alife.EarningApp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.EarningApp.Model.Profile.Profile_repositories;
import com.alifew.alife.EarningApp.Model.Profile.Profile_response;


public class ProfileViewModel extends ViewModel {
    /*public ProfileViewModel(@NonNull Application application) {
        super(application);
    }*/

    public LiveData<Profile_response> getData(String userID) {

        return Profile_repositories.getInstance().getData(userID);

    }
}
