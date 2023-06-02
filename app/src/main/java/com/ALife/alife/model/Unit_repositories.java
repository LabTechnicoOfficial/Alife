package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Unit_repositories {
    Unit_api unit;
    MutableLiveData<List<Unit_response>> data;
    String value;
    private static Unit_repositories unit_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public Unit_repositories() {
        unit = ApiUtilize.get_unit();
        data = new MutableLiveData<>();
        //this.value = value;

    }

    public synchronized static Unit_repositories getInstance() {
        if (unit_repositories == null) {
            return new Unit_repositories();
        }
        return unit_repositories;
    }

    public @NonNull
    MutableLiveData<List<Unit_response>> getdata(@NonNull String value) {

        Call<List<Unit_response>> call = unit.getUnit(value);
        call.enqueue(new Callback<List<Unit_response>>() {
            @Override
            public void onResponse(Call<List<Unit_response>> call, Response<List<Unit_response>> response) {

                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<Unit_response>> call, Throwable t) {
                //Toast.makeText(Shop_details_repositories.this,"something error.Try again",Toast.LENGTH_SHORT).show();

                // idMessage.setValue(t.getMessage());

            }


        });
        return data;
    }
}
