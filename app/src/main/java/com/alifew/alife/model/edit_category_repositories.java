package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class edit_category_repositories {
    String id;
    String logo;
    String name;
    edit_category_api edit_category;
    edit_category_api2 edit_category2;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    MutableLiveData<edit_category_response> data;
    private static edit_category_repositories edit_category_repositories;

    public edit_category_repositories() {
//this.logo=logo;
        //this.name=name;
        //this.id = id;
        data = new MutableLiveData<>();
        edit_category = ApiUtilize.edit_category_response();
        edit_category2 = ApiUtilize.edit_category_response2();
    }

    public synchronized static edit_category_repositories getInstance() {
        if (edit_category_repositories == null) {
            return new edit_category_repositories();
        }
        return edit_category_repositories;
    }

    public @NonNull
    MutableLiveData<edit_category_response> getdata(@NonNull String name, @NonNull String id) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        Call<edit_category_response> call = edit_category.getdata(name, id);
        call.enqueue(new Callback<edit_category_response>() {
            @Override
            public void onResponse(Call<edit_category_response> call, Response<edit_category_response> response) {
                if (response.isSuccessful()) {
                    edit_category_response showresponse = response.body();
                    data.postValue(showresponse);
                }

            }

            @Override
            public void onFailure(Call<edit_category_response> call, Throwable t) {
                //Toast.makeText(Shop_details_repositories.this,"something error.Try again",Toast.LENGTH_SHORT).show();

                // idMessage.setValue(t.getMessage());
                edit_category_response showresponse = new edit_category_response();
                showresponse.setMessage("Failed");
                data.postValue(showresponse);
            }


        });
        return data;
    }

    public @NonNull
    MutableLiveData<edit_category_response> getdata2(@NonNull String logo, @NonNull String name, @NonNull String id) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        Call<edit_category_response> call = edit_category2.getdata(logo, name, id);
        call.enqueue(new Callback<edit_category_response>() {
            @Override
            public void onResponse(Call<edit_category_response> call, Response<edit_category_response> response) {
                if (response.isSuccessful()) {
                    edit_category_response showresponse = response.body();
                    data.postValue(showresponse);
                }

            }

            @Override
            public void onFailure(Call<edit_category_response> call, Throwable t) {
                //Toast.makeText(Shop_details_repositories.this,"something error.Try again",Toast.LENGTH_SHORT).show();

                // idMessage.setValue(t.getMessage());
                edit_category_response showresponse = new edit_category_response();
                showresponse.setMessage("Failed");
                data.postValue(showresponse);
            }


        });
        return data;
    }
}
