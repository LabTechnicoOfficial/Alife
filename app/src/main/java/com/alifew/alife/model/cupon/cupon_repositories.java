package com.alifew.alife.model.cupon;

import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cupon_repositories {
    private MutableLiveData<add_response> add_response;
    private MutableLiveData<List<cupon_response>> data;
    private MutableLiveData<notify_response> data2;
    private cupon_api api;
    private static cupon_repositories cupon_repositories;

    private cupon_repositories() {
        data = new MutableLiveData<>();
        data2=new MutableLiveData<>();
        add_response = new MutableLiveData<>();
        api = ApiUtilize.cupon_response();

    }

    public synchronized static cupon_repositories getInstance() {
        if (cupon_repositories == null) {
            return new cupon_repositories();
        }
        return cupon_repositories;
    }

    public MutableLiveData<add_response> addCupon(String shop_id, String cupon_name, String time_range, String create_date, String end_date, String description) {

        Call<add_response> call = api.add_cupon(shop_id, cupon_name, time_range, create_date, end_date, description);
        call.enqueue(new Callback<com.alifew.alife.model.cupon.add_response>() {
            @Override
            public void onResponse(Call<com.alifew.alife.model.cupon.add_response> call, Response<com.alifew.alife.model.cupon.add_response> response) {
                if (response.isSuccessful())
                    add_response.postValue(response.body());
            }

            @Override
            public void onFailure(Call<com.alifew.alife.model.cupon.add_response> call, Throwable throwable) {
                add_response response = new add_response();
                response.setMessage(throwable.getMessage());
                add_response.postValue(response);
            }
        });
        return add_response;
    }

    public MutableLiveData<List<cupon_response>> getData(String shop_id) {
        Call<List<cupon_response>> call = api.fetch_cupon(shop_id);
        call.enqueue(new Callback<List<cupon_response>>() {
            @Override
            public void onResponse(Call<List<cupon_response>> call, Response<List<cupon_response>> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<cupon_response>> call, Throwable throwable) {

            }
        });
        return data;
    }

    public MutableLiveData<notify_response> getNotify(String shop_id, String message) {
        Call<notify_response> call = api.notifyCoupon(shop_id, message);
        call.enqueue(new Callback<notify_response>() {
            @Override
            public void onResponse(Call<notify_response> call, Response<notify_response> response) {
                if (response.isSuccessful())
                    data2.postValue(response.body());
            }

            @Override
            public void onFailure(Call<notify_response> call, Throwable throwable) {

            }
        });
        return data2;
    }
}
