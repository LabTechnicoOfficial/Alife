package com.alifew.alifeworld.model.cupon;

import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cuponShop_repositories {
    private MutableLiveData<List<cuponShop_response>> data;
    private cupon_api api;
    private static cuponShop_repositories cuponShop_repositories;

    private cuponShop_repositories() {
        api = ApiUtilize.cupon_response();
        data = new MutableLiveData<>();
    }

    public synchronized static cuponShop_repositories getInstance() {
        if (cuponShop_repositories == null)
            return new cuponShop_repositories();
        return cuponShop_repositories;
    }

    public MutableLiveData<List<cuponShop_response>> getData(int page,int limit) {
        Call<List<cuponShop_response>> call = api.fetch_cuponShop(page,limit);
        call.enqueue(new Callback<List<cuponShop_response>>() {
            @Override
            public void onResponse(Call<List<cuponShop_response>> call, Response<List<cuponShop_response>> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<cuponShop_response>> call, Throwable throwable) {

            }
        });
        return data;
    }
}
