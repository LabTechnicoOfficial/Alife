package com.ALife.alife.model.cupon;

import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class package_repositories {
    private MutableLiveData<add_response> add_response;
    private MutableLiveData<List<Package_response>> data;
    private cupon_api api;
    private static package_repositories package_repositories;

    private package_repositories() {
        add_response = new MutableLiveData<>();
        data = new MutableLiveData<>();
        api = ApiUtilize.cupon_response();
    }

    public synchronized static package_repositories getInstance() {
        if (package_repositories == null)
            return new package_repositories();
        return package_repositories;
    }

    public MutableLiveData<add_response> add_package(String cupon_id, String package_name, String packageSell_amount, String winner, String gift) {
        Call<add_response> call = api.add_package(cupon_id, package_name, packageSell_amount, winner, gift);
        call.enqueue(new Callback<com.ALife.alife.model.cupon.add_response>() {
            @Override
            public void onResponse(Call<com.ALife.alife.model.cupon.add_response> call, Response<com.ALife.alife.model.cupon.add_response> response) {
                if (response.isSuccessful())
                    add_response.postValue(response.body());
            }

            @Override
            public void onFailure(Call<com.ALife.alife.model.cupon.add_response> call, Throwable throwable) {

            }
        });
        return add_response;
    }

    public MutableLiveData<List<Package_response>> getData(String couponID, String shopID, String phone, String createdDate, String endDate) {
        Call<List<Package_response>> call = api.fetch_package(couponID, shopID, phone, createdDate, endDate);
        call.enqueue(new Callback<List<Package_response>>() {
            @Override
            public void onResponse(Call<List<Package_response>> call, Response<List<Package_response>> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Package_response>> call, Throwable throwable) {

            }
        });
        return data;
    }

}
