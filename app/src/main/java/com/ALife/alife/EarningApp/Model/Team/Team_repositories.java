package com.ALife.alife.EarningApp.Model.Team;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;


import com.ALife.alife.EarningApp.Model.APIUtilize;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Team_repositories {

    private static Team_repositories team_repositories;
    Team_API teamApi;
    MutableLiveData<List<Team_response>> message;

    private Team_repositories() {
        teamApi = APIUtilize.teamApi();
    }

    public synchronized static Team_repositories getInstance() {
        if (team_repositories == null) {
            return new Team_repositories();
        }
        return team_repositories;
    }

    public @NonNull
    MutableLiveData<List<Team_response>> getMessage(@NonNull String userID) {

        if (message == null) {
            message = new MutableLiveData<>();
        }

        Call<List<Team_response>> call = teamApi.getResponse(userID);

        call.enqueue(new Callback<List<Team_response>>() {
            @Override
            public void onResponse(Call<List<Team_response>> call, Response<List<Team_response>> response) {

                if (response.isSuccessful()) {
                    List<Team_response> team_responses = response.body();
                    message.postValue(team_responses);
                }
            }

            @Override
            public void onFailure(Call<List<Team_response>> call, Throwable t) {
                List<Team_response> response = new ArrayList<>();
                message.postValue(response);
            }
        });
        return message;
    }
}
