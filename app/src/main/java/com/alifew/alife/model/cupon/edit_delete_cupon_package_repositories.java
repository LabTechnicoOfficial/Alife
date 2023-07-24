package com.alifew.alife.model.cupon;

import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class edit_delete_cupon_package_repositories {
    private MutableLiveData<edit_delete_response> data;
    private cupon_api api;
    private static edit_delete_cupon_package_repositories edit_delete_cupon_package_repositories;

    private edit_delete_cupon_package_repositories() {
        data = new MutableLiveData<>();
        api = ApiUtilize.cupon_response();
    }

    public synchronized static edit_delete_cupon_package_repositories getInstance() {
        if (edit_delete_cupon_package_repositories == null)
            return new edit_delete_cupon_package_repositories();
        return edit_delete_cupon_package_repositories;
    }

    public MutableLiveData<edit_delete_response> deleteCupon(String shop_id, String cupon_id) {
        Call<edit_delete_response> call = api.deleteCupon(shop_id, cupon_id);
        call.enqueue(new Callback<edit_delete_response>() {
            @Override
            public void onResponse(Call<edit_delete_response> call, Response<edit_delete_response> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<edit_delete_response> call, Throwable throwable) {

            }
        });
        return data;
    }

    public MutableLiveData<edit_delete_response> deletePackage(String package_id) {
        Call<edit_delete_response> call = api.deletePackage(package_id);
        call.enqueue(new Callback<edit_delete_response>() {
            @Override
            public void onResponse(Call<edit_delete_response> call, Response<edit_delete_response> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<edit_delete_response> call, Throwable throwable) {

            }
        });
        return data;
    }

    public MutableLiveData<edit_delete_response> editPackage(String package_id, String package_name, String packageSell_amount) {
        Call<edit_delete_response> call = api.updatePackage(package_id, package_name, packageSell_amount);
        call.enqueue(new Callback<edit_delete_response>() {
            @Override
            public void onResponse(Call<edit_delete_response> call, Response<edit_delete_response> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<edit_delete_response> call, Throwable throwable) {

            }
        });
        return data;
    }
}
