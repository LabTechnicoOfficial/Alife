package com.alifew.alifeworld.model.cupon;

import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cuponShop_repositories {
    private MutableLiveData<List<ShopResponse>> data;
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

    public MutableLiveData<List<ShopResponse>> getData(int page, int limit) {
        Call<List<ShopResponse>> call = api.fetch_cuponShop(page,limit);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<ShopResponse>> call, Response<List<ShopResponse>> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ShopResponse>> call, Throwable throwable) {

            }
        });
        return data;
    }
}
