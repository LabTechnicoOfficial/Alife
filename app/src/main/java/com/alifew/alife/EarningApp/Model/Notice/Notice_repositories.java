package com.alifew.alife.EarningApp.Model.Notice;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;


import com.alifew.alife.EarningApp.Model.APIUtilize;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notice_repositories {

    private static Notice_repositories notice_repositories;
    Notice_API noticeApi;
    MutableLiveData<List<Notice_response>> message;

    private Notice_repositories() {
        noticeApi = APIUtilize.noticeApi();
    }

    public synchronized static Notice_repositories getInstance() {
        if (notice_repositories == null) {
            return new Notice_repositories();
        }
        return notice_repositories;
    }

    public @NonNull
    MutableLiveData<List<Notice_response>> getMessage() {

        if (message == null) {
            message = new MutableLiveData<>();
        }

        Call<List<Notice_response>> call = noticeApi.getResponse();

        call.enqueue(new Callback<List<Notice_response>>() {
            @Override
            public void onResponse(Call<List<Notice_response>> call, Response<List<Notice_response>> response) {

                if (response.isSuccessful()) {
                    List<Notice_response> notice_responses = response.body();
                    message.postValue(notice_responses);
                }
            }

            @Override
            public void onFailure(Call<List<Notice_response>> call, Throwable t) {
                List<Notice_response> response = new ArrayList<>();
                message.postValue(response);
            }
        });
        return message;
    }

}
