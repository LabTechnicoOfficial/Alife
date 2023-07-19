package com.alifew.alife.EarningApp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.EarningApp.Model.Reward.Message_response;
import com.alifew.alife.EarningApp.Model.Reward.Reward_repositories;


public class RewardViewModel extends ViewModel {
    /*public RewardViewModel(@NonNull Application application) {
        super(application);
    }*/

    public LiveData<Message_response> getData(String userID, String refferalID) {

        return Reward_repositories.getInstance().getReward(userID, refferalID);

    }
}
