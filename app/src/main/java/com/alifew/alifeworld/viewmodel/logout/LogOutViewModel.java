package com.alifew.alifeworld.viewmodel.logout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.CommonResponse;
import com.alifew.alifeworld.model.logout.LogOutRepositories;

public class LogOutViewModel extends ViewModel {
    public LiveData<CommonResponse> customerLogout(String id) {
        return LogOutRepositories.getInstance().customerLogout(id);
    }

    public LiveData<CommonResponse> shopLogout(String id) {
        return LogOutRepositories.getInstance().shopLogout(id);
    }
}
