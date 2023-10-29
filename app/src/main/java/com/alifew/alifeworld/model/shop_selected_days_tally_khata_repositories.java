package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class shop_selected_days_tally_khata_repositories {
    private shop_selected_days_tally_khata_api tally_khata;
    private shop_selected_days_sell_details_api sell_details;
    private String shop_id, date1, date2;
    private int page, limit;
    private MutableLiveData<get_sell_details_response> details_data;
    private MutableLiveData<List<shop_tally_khata_response>> tally_data;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    private static shop_selected_days_tally_khata_repositories shop_selected_days_tally_khata_repositories;

    public shop_selected_days_tally_khata_repositories() {
        /*this.shop_id = shop_id;
        this.date1 = date1;
        this.date2 = date2;*/

        sell_details = ApiUtilize.shop_selected_days_sell_details_response();
        tally_khata = ApiUtilize.shop_selected_days_tally_khata_response();
        tally_data = new MutableLiveData<>();
        details_data = new MutableLiveData<>();

    }

    public synchronized static shop_selected_days_tally_khata_repositories getInstance() {
        if (shop_selected_days_tally_khata_repositories == null) {
            return new shop_selected_days_tally_khata_repositories();
        }
        return shop_selected_days_tally_khata_repositories;
    }


    public @NonNull
    MutableLiveData<List<shop_tally_khata_response>> getTally_data(@NonNull String shop_id, @NonNull String date1, @NonNull String date2, @NonNull int page, @NonNull int limit) {
        Call<List<shop_tally_khata_response>> call = tally_khata.get_tally(shop_id, date1, date2, page, limit);
        call.enqueue(new Callback<List<shop_tally_khata_response>>() {
            @Override
            public void onResponse(Call<List<shop_tally_khata_response>> call, Response<List<shop_tally_khata_response>> response) {
                if (response.isSuccessful()) {
                    tally_data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<shop_tally_khata_response>> call, Throwable t) {

            }
        });
        return tally_data;
    }

    public @NonNull
    MutableLiveData<get_sell_details_response> getDetails_data(@NonNull String shop_id, @NonNull String date1, @NonNull String date2) {
        Call<get_sell_details_response> call = sell_details.sell_summary(shop_id, date1, date2);
        call.enqueue(new Callback<get_sell_details_response>() {
            @Override
            public void onResponse(Call<get_sell_details_response> call, Response<get_sell_details_response> response) {
                if (response.isSuccessful()) {
                    details_data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<get_sell_details_response> call, Throwable t) {

            }
        });
        return details_data;
    }
}
