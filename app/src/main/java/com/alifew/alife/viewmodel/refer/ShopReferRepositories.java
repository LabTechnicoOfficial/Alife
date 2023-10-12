package com.alifew.alife.viewmodel.refer;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;
import com.alifew.alife.model.refer.ReferApi;
import com.alifew.alife.model.refer.ReferResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopReferRepositories {
    private static ShopReferRepositories referRepositories;
    private ReferApi referApi;
    MutableLiveData<List<ReferResponse>> referList;

    public ShopReferRepositories() {
        referApi = ApiUtilize.referApi();
        referList = new MutableLiveData<>();
    }

    public synchronized static ShopReferRepositories getInstance() {
        if (referRepositories == null) {
            return new ShopReferRepositories();
        }
        return referRepositories;
    }

    MutableLiveData<List<ReferResponse>> getReferList(String shopID){
        Call<List<ReferResponse>> call = referApi.getReferList(shopID);
        call.enqueue(new Callback<List<ReferResponse>>() {
            @Override
            public void onResponse(Call<List<ReferResponse>> call, Response<List<ReferResponse>> response) {
                if (response.isSuccessful()){
                    referList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ReferResponse>> call, Throwable t) {
                Log.d("dataxx", "onFailure: "+t.getMessage());
            }
        });

        return referList;
    }
}
