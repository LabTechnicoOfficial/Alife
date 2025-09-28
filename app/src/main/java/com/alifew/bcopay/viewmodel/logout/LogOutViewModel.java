package com.alifew.bcopay.viewmodel.logout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.CommonResponse;
import com.alifew.bcopay.model.logout.LogOutRepositories;

public class LogOutViewModel extends ViewModel {
    public LiveData<CommonResponse> customerLogout(String id) {
        return LogOutRepositories.getInstance().customerLogout(id);
    }

    public LiveData<CommonResponse> shopLogout(String id) {
        return LogOutRepositories.getInstance().shopLogout(id);
    }
}
