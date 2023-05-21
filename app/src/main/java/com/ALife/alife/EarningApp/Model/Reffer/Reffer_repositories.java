package com.ALife.alife.EarningApp.Model.Reffer;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.EarningApp.Model.APIUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reffer_repositories {
    private static Reffer_repositories reffer_repositories;
    Reffer_API refferApi;
    MutableLiveData<String> message;

    private Reffer_repositories() {
        refferApi = APIUtilize.refferApi();
    }

    public synchronized static Reffer_repositories getInstance() {
        if (reffer_repositories == null) {
            return new Reffer_repositories();
        }
        return reffer_repositories;
    }

    public @NonNull
    MutableLiveData<String> getMessage(@NonNull String id, @NonNull String referID) {

        if (message == null) {
            message = new MutableLiveData<>();
        }

        Call<String> call = refferApi.getRefer( referID,id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    String notice_responses = response.body();
                    message.postValue(notice_responses);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        return message;
    }
}
