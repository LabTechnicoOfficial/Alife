package com.ALife.alife.model.local_sell;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.model.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_local_sell_repositories {
    private static get_local_sell_repositories get_local_sell_repositories;
    private MutableLiveData<List<get_local_sell_product_response>> data_product;
    private MutableLiveData<get_local_sell_details_response> sell_details;
    private MutableLiveData<get_product_by_bar_code_response> product;
    private MutableLiveData<List<customer_phone_response>> customer;
    private MutableLiveData<List<local_sell_history_response>> sell_history;
    private MutableLiveData<local_sell_summary_response> sell_summary;
    private local_sell_api api;

    private get_local_sell_repositories() {
        data_product = new MutableLiveData<>();
        sell_details = new MutableLiveData<>();
        product = new MutableLiveData<>();
        customer = new MutableLiveData<>();
        sell_history = new MutableLiveData<>();
        sell_summary = new MutableLiveData<>();
        api = ApiUtilize.local_sell_api();
    }

    public synchronized static get_local_sell_repositories getInstance() {
        if (get_local_sell_repositories == null)
            return new get_local_sell_repositories();
        return get_local_sell_repositories;
    }

    public MutableLiveData<List<get_local_sell_product_response>> getData_product(String shop_id) {
        Call<List<get_local_sell_product_response>> call = api.getlocal_sell_product(shop_id);
        call.enqueue(new Callback<List<get_local_sell_product_response>>() {
            @Override
            public void onResponse(Call<List<get_local_sell_product_response>> call, Response<List<get_local_sell_product_response>> response) {
                if (response.isSuccessful()) {
                    data_product.postValue(response.body());
                   // Log.d("sizexx", String.valueOf(response.body().size()));
                }
            }

            @Override
            public void onFailure(Call<List<get_local_sell_product_response>> call, Throwable throwable) {
                Log.d("sizexx", throwable.getMessage());
            }
        });
        return data_product;
    }

    public MutableLiveData<List<get_local_sell_product_response>> getData_product_bySearch(String shop_id,String search) {
        Call<List<get_local_sell_product_response>> call = api.getlocal_sell_product_bySearch(shop_id,search);
        call.enqueue(new Callback<List<get_local_sell_product_response>>() {
            @Override
            public void onResponse(Call<List<get_local_sell_product_response>> call, Response<List<get_local_sell_product_response>> response) {
                if (response.isSuccessful()) {
                    data_product.postValue(response.body());
                    // Log.d("sizexx", String.valueOf(response.body().size()));
                }
            }

            @Override
            public void onFailure(Call<List<get_local_sell_product_response>> call, Throwable throwable) {
                Log.d("sizexx", throwable.getMessage());
            }
        });
        return data_product;
    }



    public MutableLiveData<get_local_sell_details_response> getSell_details(String sell_id) {
        Call<get_local_sell_details_response> call = api.get_local_sell_details(sell_id);
        call.enqueue(new Callback<get_local_sell_details_response>() {
            @Override
            public void onResponse(Call<get_local_sell_details_response> call, Response<get_local_sell_details_response> response) {
                if (response.isSuccessful())
                    sell_details.postValue(response.body());
            }

            @Override
            public void onFailure(Call<get_local_sell_details_response> call, Throwable throwable) {

            }
        });
        return sell_details;
    }

    public MutableLiveData<get_product_by_bar_code_response> getProduct(String bar_code, String shop_id) {
        Call<get_product_by_bar_code_response> call = api.get_product(bar_code, shop_id);
        call.enqueue(new Callback<get_product_by_bar_code_response>() {
            @Override
            public void onResponse(Call<get_product_by_bar_code_response> call, Response<get_product_by_bar_code_response> response) {
                if (response.isSuccessful())
                    product.postValue(response.body());
            }

            @Override
            public void onFailure(Call<get_product_by_bar_code_response> call, Throwable t) {

            }
        });

        return product;
    }

    public MutableLiveData<List<customer_phone_response>> getCustomer(String shop_id, int page, int linit) {
        Call<List<customer_phone_response>> call = api.get_customer(shop_id, page, linit);
        call.enqueue(new Callback<List<customer_phone_response>>() {
            @Override
            public void onResponse(Call<List<customer_phone_response>> call, Response<List<customer_phone_response>> response) {
                if (response.isSuccessful())
                    customer.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<customer_phone_response>> call, Throwable t) {

            }
        });
        return customer;
    }

    //local sell history
    public MutableLiveData<List<local_sell_history_response>> allHistory(String shop_id, int page, int limit) {
        Call<List<local_sell_history_response>> call = api.getAllList(shop_id, page, limit);
        call.enqueue(new Callback<List<local_sell_history_response>>() {
            @Override
            public void onResponse(Call<List<local_sell_history_response>> call, Response<List<local_sell_history_response>> response) {
                if (response.isSuccessful())
                    sell_history.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<local_sell_history_response>> call, Throwable t) {

            }
        });
        return sell_history;

    }

    public MutableLiveData<List<local_sell_history_response>> dailyHistory(String shop_id, String date, int page, int limit) {
        Call<List<local_sell_history_response>> call = api.getDailyList(shop_id, date, page, limit);
        call.enqueue(new Callback<List<local_sell_history_response>>() {
            @Override
            public void onResponse(Call<List<local_sell_history_response>> call, Response<List<local_sell_history_response>> response) {
                if (response.isSuccessful())
                    sell_history.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<local_sell_history_response>> call, Throwable t) {

            }
        });
        return sell_history;
    }

    public MutableLiveData<List<local_sell_history_response>> selectedHistory(String shop_id, String date1, String date2, int page, int limit) {
        Call<List<local_sell_history_response>> call = api.getSelectedList(shop_id, date1, date2, page, limit);
        call.enqueue(new Callback<List<local_sell_history_response>>() {
            @Override
            public void onResponse(Call<List<local_sell_history_response>> call, Response<List<local_sell_history_response>> response) {
                if (response.isSuccessful())
                    sell_history.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<local_sell_history_response>> call, Throwable t) {

            }
        });
        return sell_history;
    }

    public MutableLiveData<local_sell_summary_response> allSummary(String shop_id) {
        Call<local_sell_summary_response> call = api.getAllSummary(shop_id);
        call.enqueue(new Callback<local_sell_summary_response>() {
            @Override
            public void onResponse(Call<local_sell_summary_response> call, Response<local_sell_summary_response> response) {
                if (response.isSuccessful())
                    sell_summary.postValue(response.body());
            }

            @Override
            public void onFailure(Call<local_sell_summary_response> call, Throwable t) {

            }
        });
        return sell_summary;
    }

    public MutableLiveData<local_sell_summary_response> dailySummary(String shop_id, String date) {
        Call<local_sell_summary_response> call = api.getDailySummary(shop_id, date);
        call.enqueue(new Callback<local_sell_summary_response>() {
            @Override
            public void onResponse(Call<local_sell_summary_response> call, Response<local_sell_summary_response> response) {
                if (response.isSuccessful())
                    sell_summary.postValue(response.body());
            }

            @Override
            public void onFailure(Call<local_sell_summary_response> call, Throwable t) {

            }
        });
        return sell_summary;
    }

    public MutableLiveData<local_sell_summary_response> selectedSummary(String shop_id, String date1, String date2) {
        Call<local_sell_summary_response> call = api.getSelectedSummary(shop_id, date1, date2);
        call.enqueue(new Callback<local_sell_summary_response>() {
            @Override
            public void onResponse(Call<local_sell_summary_response> call, Response<local_sell_summary_response> response) {
                if (response.isSuccessful())
                    sell_summary.postValue(response.body());
            }

            @Override
            public void onFailure(Call<local_sell_summary_response> call, Throwable t) {

            }
        });
        return sell_summary;
    }
}
