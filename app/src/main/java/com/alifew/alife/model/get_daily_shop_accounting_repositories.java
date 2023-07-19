package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_daily_shop_accounting_repositories {
    private String id, date;
    private int page, limit;
    private systemetic_sell_details_api getSellProductlistApi;
    private get_daily_sell_details_api getDailySellDetailsApi;
    private get_daily_sell_cash_api getDailySellCashApi;
    private get_daily_sell_due_api getDailySellDueApi;
    private shop_daily_tally_khata_api get_sell_list;
    private MutableLiveData<List<systemetic_sell_details_response>> productlist;
    private MutableLiveData<List<get_daily_sell_cash_response>> sell_cash;
    private MutableLiveData<List<get_daily_due_sell_response>> sell_due;
    private MutableLiveData<get_sell_details_response> sell_details;
    private MutableLiveData<List<shop_tally_khata_response>> sell_list;
    private static get_daily_shop_accounting_repositories get_daily_shop_accounting_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public get_daily_shop_accounting_repositories() {
        //this.id = id;
        //this.date=date;
        getSellProductlistApi = ApiUtilize.get_sell_productlist_response();
        getDailySellCashApi = ApiUtilize.get_daily_sell_cash_response();
        getDailySellDueApi = ApiUtilize.get_daily_sell_due_response();
        getDailySellDetailsApi = ApiUtilize.get_daily_sell_details_response();
        get_sell_list = ApiUtilize.get_sell_list_response();
        sell_list = new MutableLiveData<>();
        productlist = new MutableLiveData<>();
        sell_cash = new MutableLiveData<>();
        sell_due = new MutableLiveData<>();
        sell_details = new MutableLiveData<>();

    }

    public synchronized static get_daily_shop_accounting_repositories getInstance() {
        if (get_daily_shop_accounting_repositories == null) {
            return new get_daily_shop_accounting_repositories();
        }
        return get_daily_shop_accounting_repositories;
    }


    @NonNull
    public MutableLiveData<List<systemetic_sell_details_response>> getProductlist(@NonNull String id) {
        Call<List<systemetic_sell_details_response>> call = getSellProductlistApi.get_sell_productlist(id);
        call.enqueue(new Callback<List<systemetic_sell_details_response>>() {
            @Override
            public void onResponse(Call<List<systemetic_sell_details_response>> call, Response<List<systemetic_sell_details_response>> response) {
                if (response.isSuccessful()) {
                    productlist.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<systemetic_sell_details_response>> call, Throwable t) {

            }
        });
        return productlist;
    }

    @NonNull
    public MutableLiveData<List<get_daily_sell_cash_response>> getSell_cash(@NonNull String id, @NonNull String date) {
        Call<List<get_daily_sell_cash_response>> call = getDailySellCashApi.get_daily_sell_cash(id, date);
        call.enqueue(new Callback<List<get_daily_sell_cash_response>>() {
            @Override
            public void onResponse(Call<List<get_daily_sell_cash_response>> call, Response<List<get_daily_sell_cash_response>> response) {
                if (response.isSuccessful()) {
                    sell_cash.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_daily_sell_cash_response>> call, Throwable t) {

            }
        });
        return sell_cash;
    }

    @NonNull
    public MutableLiveData<List<get_daily_due_sell_response>> getSell_due(@NonNull String id, @NonNull String date) {
        Call<List<get_daily_due_sell_response>> call = getDailySellDueApi.get_daily_sell_due(id, date);
        call.enqueue(new Callback<List<get_daily_due_sell_response>>() {
            @Override
            public void onResponse(Call<List<get_daily_due_sell_response>> call, Response<List<get_daily_due_sell_response>> response) {
                if (response.isSuccessful()) {
                    sell_due.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_daily_due_sell_response>> call, Throwable t) {

            }
        });
        return sell_due;
    }

    @NonNull
    public MutableLiveData<get_sell_details_response> getSell_details(@NonNull String id, @NonNull String date) {
        Call<get_sell_details_response> call = getDailySellDetailsApi.get_daily_sell_summary(id, date);
        call.enqueue(new Callback<get_sell_details_response>() {
            @Override
            public void onResponse(Call<get_sell_details_response> call, Response<get_sell_details_response> response) {
                if (response.isSuccessful()) {
                    sell_details.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<get_sell_details_response> call, Throwable t) {

            }
        });
        return sell_details;
    }

    @NonNull
    public MutableLiveData<List<shop_tally_khata_response>> getSell_list(@NonNull String id, @NonNull String date, @NonNull int page, @NonNull int limit) {
        Call<List<shop_tally_khata_response>> call = get_sell_list.get_daily_sell_list(id, date, page, limit);
        call.enqueue(new Callback<List<shop_tally_khata_response>>() {
            @Override
            public void onResponse(Call<List<shop_tally_khata_response>> call, Response<List<shop_tally_khata_response>> response) {
                if (response.isSuccessful()) {
                    sell_list.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<shop_tally_khata_response>> call, Throwable t) {

            }
        });
        return sell_list;
    }
}
