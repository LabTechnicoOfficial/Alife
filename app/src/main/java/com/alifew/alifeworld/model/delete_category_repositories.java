package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class delete_category_repositories {
    String id;

    delete_category_api delete_category;
    delete_product_api delete_product;
    MutableLiveData<delete_category_response> data;
    private static delete_category_repositories delete_category_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public delete_category_repositories() {
        //this.id = id;

        data = new MutableLiveData<>();
        delete_category = ApiUtilize.delete_category_response();
        delete_product = ApiUtilize.delete_product_response();
    }

    public synchronized static delete_category_repositories getInstance() {
        if (delete_category_repositories == null) {
            return new delete_category_repositories();
        }
        return delete_category_repositories;
    }

    public @NonNull
    MutableLiveData<delete_category_response> getdata(@NonNull String id) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        Call<delete_category_response> call = delete_category.getdata(id);
        call.enqueue(new Callback<delete_category_response>() {
            @Override
            public void onResponse(Call<delete_category_response> call, Response<delete_category_response> response) {
                if (response.isSuccessful()) {
                    delete_category_response showresponse = response.body();
                    data.postValue(showresponse);
                }

            }

            @Override
            public void onFailure(Call<delete_category_response> call, Throwable t) {
                //Toast.makeText(Shop_details_repositories.this,"something error.Try again",Toast.LENGTH_SHORT).show();

                // idMessage.setValue(t.getMessage());
                delete_category_response showresponse = new delete_category_response();
                showresponse.setMessage("Failed to delete");
                data.postValue(showresponse);
            }


        });
        return data;
    }

    public @NonNull
    MutableLiveData<delete_category_response> getproduct_delete(@NonNull String id) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        Call<delete_category_response> call = delete_product.getdata(id);
        call.enqueue(new Callback<delete_category_response>() {
            @Override
            public void onResponse(Call<delete_category_response> call, Response<delete_category_response> response) {
                if (response.isSuccessful()) {
                    delete_category_response showresponse = response.body();
                    data.postValue(showresponse);
                }

            }

            @Override
            public void onFailure(Call<delete_category_response> call, Throwable t) {
                //Toast.makeText(Shop_details_repositories.this,"something error.Try again",Toast.LENGTH_SHORT).show();

                // idMessage.setValue(t.getMessage());
                delete_category_response showresponse = new delete_category_response();
                showresponse.setMessage("Failed to delete");
                data.postValue(showresponse);
            }


        });
        return data;
    }
}
