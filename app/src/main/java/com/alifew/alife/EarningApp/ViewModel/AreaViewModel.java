package com.alifew.alife.EarningApp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.alifew.alife.EarningApp.Model.Registration.Area_repositories;
import com.alifew.alife.EarningApp.Model.Registration.Area_response;

import java.util.List;

public class AreaViewModel extends ViewModel {
    /*public AreaViewModel(@NonNull Application application) {
        super(application);
    }*/
    public LiveData<List<Area_response>> getData() {

        return Area_repositories.getInstance().getMessage();

    }
}
