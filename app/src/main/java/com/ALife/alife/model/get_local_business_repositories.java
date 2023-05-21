package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_local_business_repositories {
    private String id;
    private int page, limit;
    private get_local_business_title_api get_local_business_title;
    private get_local_business_subtitle_api get_local_business_subtitle;
    private get_local_business_details_api get_local_business_details;
    private get_local_page_item_list_api get_local_page_item_list;

    private MutableLiveData<List<get_local_business_title_response>> data_title;
    private MutableLiveData<List<get_local_business_subtitle_response>> data_subtitle;
    private MutableLiveData<List<get_local_business_details_response>> data_details;
    private MutableLiveData<List<shop_local_page_item_list_response>> itemList;
    private static get_local_business_repositories get_local_business_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public get_local_business_repositories() {
        /*this.id = id;
        this.page = page;
        this.limit = limit;*/
        get_local_business_title = ApiUtilize.get_local_business_title();
        get_local_business_subtitle = ApiUtilize.get_local_business_subtitle();

        data_title = new MutableLiveData<>();
        data_subtitle = new MutableLiveData<>();
        get_local_business_details = ApiUtilize.get_local_business_details();
        data_details = new MutableLiveData<>();
        get_local_page_item_list=ApiUtilize.get_local_page_item_list();
        itemList=new MutableLiveData<>();

    }

    public synchronized static get_local_business_repositories getInstance() {
        if (get_local_business_repositories == null) {
            return new get_local_business_repositories();
        }
        return get_local_business_repositories;
    }


    public @NonNull
    MutableLiveData<List<get_local_business_title_response>> get_tile(@NonNull String id, @NonNull int page, @NonNull int limit) {
        Call<List<get_local_business_title_response>> call = get_local_business_title.gettitle(id, page, limit);
        call.enqueue(new Callback<List<get_local_business_title_response>>() {
            @Override
            public void onResponse(Call<List<get_local_business_title_response>> call, Response<List<get_local_business_title_response>> response) {
                if (response.isSuccessful()) {
                    data_title.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_local_business_title_response>> call, Throwable t) {

            }
        });
        return data_title;
    }

    public @NonNull
    MutableLiveData<List<get_local_business_subtitle_response>> get_subtitle(@NonNull String id, @NonNull int page, @NonNull int limit) {
        Call<List<get_local_business_subtitle_response>> call = get_local_business_subtitle.gettitle(id, page, limit);
        call.enqueue(new Callback<List<get_local_business_subtitle_response>>() {
            @Override
            public void onResponse(Call<List<get_local_business_subtitle_response>> call, Response<List<get_local_business_subtitle_response>> response) {
                if (response.isSuccessful()) {
                    data_subtitle.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_local_business_subtitle_response>> call, Throwable t) {

            }
        });
        return data_subtitle;
    }

    public @NonNull
    MutableLiveData<List<get_local_business_details_response>> get_details(@NonNull String id) {
        Call<List<get_local_business_details_response>> call = get_local_business_details.getdetails(id);
        call.enqueue(new Callback<List<get_local_business_details_response>>() {
            @Override
            public void onResponse(Call<List<get_local_business_details_response>> call, Response<List<get_local_business_details_response>> response) {
                if (response.isSuccessful()) {
                    data_details.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_local_business_details_response>> call, Throwable t) {

            }
        });
        return data_details;
    }
    public @NonNull
    MutableLiveData<List<shop_local_page_item_list_response>> getItemList(@NonNull  String id, @NonNull int page, @NonNull int limit)
    {
        Call<List<shop_local_page_item_list_response>> call=get_local_page_item_list.getitem(id,page,limit);
        call.enqueue(new Callback<List<shop_local_page_item_list_response>>() {
            @Override
            public void onResponse(Call<List<shop_local_page_item_list_response>> call, Response<List<shop_local_page_item_list_response>> response) {
                if (response.isSuccessful()) {
                    itemList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<shop_local_page_item_list_response>> call, Throwable t) {

            }
        });
        return itemList;
    }
}
