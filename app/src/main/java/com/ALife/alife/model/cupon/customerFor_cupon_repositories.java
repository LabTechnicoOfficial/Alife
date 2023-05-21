package com.ALife.alife.model.cupon;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.model.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class customerFor_cupon_repositories {
    private MutableLiveData<List<customerFor_cupon_response>> data;
    private cupon_api api;
    private static customerFor_cupon_repositories customerFor_cupon_repositories;

    private customerFor_cupon_repositories() {
        data = new MutableLiveData<>();
        api = ApiUtilize.cupon_response();

    }

    public synchronized static customerFor_cupon_repositories getInstance() {
        if (customerFor_cupon_repositories == null)
            return new customerFor_cupon_repositories();
        return customerFor_cupon_repositories;
    }

    public MutableLiveData<List<customerFor_cupon_response>> getData(String shop_id, String date1, String date2) {
        Call<List<customerFor_cupon_response>> call = api.fetch_cuponCustomer(shop_id, date1, date2);
        call.enqueue(new Callback<List<customerFor_cupon_response>>() {
            @Override
            public void onResponse(Call<List<customerFor_cupon_response>> call, Response<List<customerFor_cupon_response>> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<customerFor_cupon_response>> call, Throwable throwable) {
                Log.d("mesba",throwable.getMessage());
            }
        });
        return data;
    }

}
