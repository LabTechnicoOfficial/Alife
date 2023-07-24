package com.alifew.alife.EarningApp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.EarningApp.Model.Video_ad.Video_ad_repositories;
import com.alifew.alife.EarningApp.Model.Video_ad.Video_ad_response;

public class VideoADViewModel extends ViewModel {

    public LiveData<Video_ad_response> getVideoAD() {
        return Video_ad_repositories.getInstance().getVideoAD();
    }
}
