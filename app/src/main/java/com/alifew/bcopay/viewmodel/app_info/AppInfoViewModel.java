package com.alifew.bcopay.viewmodel.app_info;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.app_info.AppInfoRepositories;
import com.alifew.bcopay.model.app_info.AppInfoResponse;

public class AppInfoViewModel extends ViewModel {
    public LiveData<AppInfoResponse> getAppInfo(){
        return AppInfoRepositories.getInstance().getAppInfo();
    }
}
