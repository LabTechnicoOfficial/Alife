package com.alifew.alife.EarningApp.Model.Reward;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.EarningApp.Model.APIUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reward_repositories {
    private static Reward_repositories reward_repositories;
    Reward_API rewardApi;
    MutableLiveData<Message_response> message;

    private Reward_repositories() {
        rewardApi = APIUtilize.rewardApi();
    }

    public synchronized static Reward_repositories getInstance() {
        if (reward_repositories == null) {
            return new Reward_repositories();
        }
        return reward_repositories;
    }

    public @NonNull
    MutableLiveData<Message_response> getReward(@NonNull String userID, @NonNull String refferalID) {

        if (message == null) {
            message = new MutableLiveData<>();
        }

        Call<Message_response> call = rewardApi.getReward(userID, refferalID);

        call.enqueue(new Callback<Message_response>() {
            @Override
            public void onResponse(Call<Message_response> call, Response<Message_response> response) {

                if (response.isSuccessful()) {
                    Message_response message_response = response.body();
                    message.postValue(message_response);
                } else {
                    Message_response response1 = new Message_response();
                    response1.setMessage("-1");
                    message.postValue(response1);
                }

            }

            @Override
            public void onFailure(Call<Message_response> call, Throwable t) {
                Message_response response = new Message_response();
                message.postValue(response);

                Log.d("ErrorXXX", t.getMessage());
            }
        });
        return message;
    }
}
