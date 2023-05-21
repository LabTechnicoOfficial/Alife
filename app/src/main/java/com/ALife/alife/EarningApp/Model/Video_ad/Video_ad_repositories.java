package com.ALife.alife.EarningApp.Model.Video_ad;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.EarningApp.Model.APIUtilize;
import com.ALife.alife.EarningApp.Model.Team.Team_API;
import com.ALife.alife.EarningApp.Model.Team.Team_repositories;
import com.ALife.alife.EarningApp.Model.Team.Team_response;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Video_ad_repositories {
    private static Video_ad_repositories videoAdRepositories;
    Video_ad_api videoAdApi;
    MutableLiveData<Video_ad_response> message;

    private Video_ad_repositories() {
        videoAdApi = APIUtilize.videoAdApi();
    }

    public synchronized static Video_ad_repositories getInstance() {
        if (videoAdRepositories == null) {
            return new Video_ad_repositories();
        }
        return videoAdRepositories;
    }

    public @NonNull
    MutableLiveData<Video_ad_response> getVideoAD() {

        if (message == null) {
            message = new MutableLiveData<>();
        }

        Call<Video_ad_response> call = videoAdApi.getVideoAD();

        call.enqueue(new Callback<Video_ad_response>() {
            @Override
            public void onResponse(Call<Video_ad_response> call, Response<Video_ad_response> response) {

                if (response.isSuccessful()) {
                    Video_ad_response video_ad_response = response.body();
                    message.postValue(video_ad_response);
                }
            }

            @Override
            public void onFailure(Call<Video_ad_response> call, Throwable t) {
                Video_ad_response response = new Video_ad_response();
                message.postValue(response);
            }
        });
        return message;
    }
}
