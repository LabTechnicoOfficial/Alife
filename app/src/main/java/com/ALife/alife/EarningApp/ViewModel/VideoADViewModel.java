package com.ALife.alife.EarningApp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.EarningApp.Model.VerifyPassword.verifyPassword_repositories;
import com.ALife.alife.EarningApp.Model.VerifyPassword.verifyPassword_response;
import com.ALife.alife.EarningApp.Model.Video_ad.Video_ad_repositories;
import com.ALife.alife.EarningApp.Model.Video_ad.Video_ad_response;

public class VideoADViewModel extends ViewModel {

    public LiveData<Video_ad_response> getVideoAD() {
        return Video_ad_repositories.getInstance().getVideoAD();
    }
}
