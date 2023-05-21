package com.ALife.alife.EarningApp.ViewModel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.EarningApp.Model.Reward.Message_response;
import com.ALife.alife.EarningApp.Model.Reward.Reward_repositories;


public class RewardViewModel extends ViewModel {
    /*public RewardViewModel(@NonNull Application application) {
        super(application);
    }*/

    public LiveData<Message_response> getData(String userID, String refferalID) {

        return Reward_repositories.getInstance().getReward(userID, refferalID);

    }
}
