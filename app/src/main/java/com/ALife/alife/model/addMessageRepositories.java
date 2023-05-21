package com.ALife.alife.model;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addMessageRepositories {
    private addMessageApi addMessageApi;
    private MutableLiveData<addMessageResponse> data;
    private static addMessageRepositories addMessageRepositories;

    public addMessageRepositories() {
        addMessageApi = ApiUtilize.addMessageApi();
        data = new MutableLiveData<>();
    }

    public synchronized static addMessageRepositories getInstance() {
        if (addMessageRepositories == null) {
            return new addMessageRepositories();
        }
        return addMessageRepositories;
    }

    public MutableLiveData<addMessageResponse> getResponse(String shop_id, String shop_name, String shop_phone, String customer_id, String customer_name, String customer_phone, String message) {
        Call<addMessageResponse> call = addMessageApi.add_message(shop_id, shop_name, shop_phone, customer_id, customer_name, customer_phone, message);
        call.enqueue(new Callback<addMessageResponse>() {
            @Override
            public void onResponse(Call<addMessageResponse> call, Response<addMessageResponse> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<addMessageResponse> call, Throwable t) {

            }
        });
        return data;
    }
}
