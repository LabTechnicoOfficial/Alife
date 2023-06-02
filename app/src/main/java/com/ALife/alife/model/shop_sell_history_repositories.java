package com.ALife.alife.model;

import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class shop_sell_history_repositories {
    private shop_sell_history_api sell_history;
    private MutableLiveData<List<shop_sell_history_list_response>> dataList;
    private MutableLiveData<shop_sell_history_summary_response> dataSummary;
    private static shop_sell_history_repositories sell_history_repositories;
    private shop_sell_history_repositories()
    {
        sell_history= ApiUtilize.shop_sell_history();
        dataList=new MutableLiveData<>();
        dataSummary=new MutableLiveData<>();
    }
    public synchronized static shop_sell_history_repositories getInstance()
    {
        if(sell_history_repositories==null)
            return new shop_sell_history_repositories();
        return sell_history_repositories;
    }
    public MutableLiveData<shop_sell_history_summary_response> getAllSummary(String shop_id)
    {
        Call<shop_sell_history_summary_response> call=sell_history.getAllSummary(shop_id);
        call.enqueue(new Callback<shop_sell_history_summary_response>() {
            @Override
            public void onResponse(Call<shop_sell_history_summary_response> call, Response<shop_sell_history_summary_response> response) {
                if(response.isSuccessful())
                {
                    dataSummary.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<shop_sell_history_summary_response> call, Throwable t) {

            }
        });
        return dataSummary;
    }
    public MutableLiveData<shop_sell_history_summary_response> getDailySummary(String shop_id,String date)
    {
        Call<shop_sell_history_summary_response> call=sell_history.getDailySummary(shop_id,date);
        call.enqueue(new Callback<shop_sell_history_summary_response>() {
            @Override
            public void onResponse(Call<shop_sell_history_summary_response> call, Response<shop_sell_history_summary_response> response) {
                if(response.isSuccessful())
                {
                    dataSummary.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<shop_sell_history_summary_response> call, Throwable t) {

            }
        });
        return dataSummary;
    }
    public MutableLiveData<shop_sell_history_summary_response> getSelectedSummary(String shop_id,String date1,String date2)
    {
        Call<shop_sell_history_summary_response> call=sell_history.getSelectedSummary(shop_id,date1,date2);
        call.enqueue(new Callback<shop_sell_history_summary_response>() {
            @Override
            public void onResponse(Call<shop_sell_history_summary_response> call, Response<shop_sell_history_summary_response> response) {
                if(response.isSuccessful())
                {
                    dataSummary.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<shop_sell_history_summary_response> call, Throwable t) {

            }
        });
        return dataSummary;
    }
    public MutableLiveData<List<shop_sell_history_list_response>> getAllList(String shop_id,int page,int limit)
    {
        Call<List<shop_sell_history_list_response>> call=sell_history.getAllList(shop_id,page,limit);
        call.enqueue(new Callback<List<shop_sell_history_list_response>>() {
            @Override
            public void onResponse(Call<List<shop_sell_history_list_response>> call, Response<List<shop_sell_history_list_response>> response) {
                if(response.isSuccessful())
                    dataList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<shop_sell_history_list_response>> call, Throwable t) {

            }
        });
        return dataList;
    }
    public MutableLiveData<List<shop_sell_history_list_response>> getDailyList(String shop_id,String date,int page,int limit)
    {
        Call<List<shop_sell_history_list_response>> call=sell_history.getDailyList(shop_id,date,page,limit);
        call.enqueue(new Callback<List<shop_sell_history_list_response>>() {
            @Override
            public void onResponse(Call<List<shop_sell_history_list_response>> call, Response<List<shop_sell_history_list_response>> response) {
                if(response.isSuccessful())
                    dataList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<shop_sell_history_list_response>> call, Throwable t) {

            }
        });
        return dataList;
    }

    public MutableLiveData<List<shop_sell_history_list_response>> getSelectedList(String shop_id,String date1,String date2,int page,int limit)
    {
        Call<List<shop_sell_history_list_response>> call=sell_history.getSelectedList(shop_id,date1,date2,page,limit);
        call.enqueue(new Callback<List<shop_sell_history_list_response>>() {
            @Override
            public void onResponse(Call<List<shop_sell_history_list_response>> call, Response<List<shop_sell_history_list_response>> response) {
                if(response.isSuccessful())
                    dataList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<shop_sell_history_list_response>> call, Throwable t) {

            }
        });
        return dataList;
    }
}
