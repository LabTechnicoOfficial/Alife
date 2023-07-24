package com.alifew.alife.EarningApp.Model.CashOut;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.EarningApp.Model.APIUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cashOut_repositories {
    private MutableLiveData<cashOut_request_response> data;
    private MutableLiveData<Commission_response> commission;
    private MutableLiveData<Message_response> message;
    private MutableLiveData<List<Method_response>> methods;
    private MutableLiveData<List<AddAmount_response>> addAmounts;
    private static cashOut_repositories cashOut_repositories;
    private cashOut_api cashOut;

    public cashOut_repositories() {
        cashOut = APIUtilize.cashOut_response();
        data = new MutableLiveData<>();
        commission = new MutableLiveData<>();
        message = new MutableLiveData<>();
    }

    public synchronized static cashOut_repositories getInstance() {
        if (cashOut_repositories == null)
            return new cashOut_repositories();
        return cashOut_repositories;
    }

    public MutableLiveData<cashOut_request_response> getData(String userID, String username, String phone, String amount, String balance, String date, String method) {
        Call<cashOut_request_response> call = cashOut.request(userID, username, phone, amount, balance, date, method);
        call.enqueue(new Callback<cashOut_request_response>() {
            @Override
            public void onResponse(Call<cashOut_request_response> call, Response<cashOut_request_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                    // Log.d("xxxx", "yess");
                }
                //Log.d("xxxx", "no");
            }

            @Override
            public void onFailure(Call<cashOut_request_response> call, Throwable t) {
                Log.d("xxxx", t.getMessage() + balance);
            }
        });
        return data;
    }

    public MutableLiveData<Commission_response> getCommission() {
        Call<Commission_response> call = cashOut.getCommission();
        call.enqueue(new Callback<Commission_response>() {
            @Override
            public void onResponse(Call<Commission_response> call, Response<Commission_response> response) {
                if (response.isSuccessful()) {
                    commission.postValue(response.body());
                }else {
                    Commission_response response1 = new Commission_response();
                    response1.setComission("-1");
                    commission.postValue(response1);
                }

            }

            @Override
            public void onFailure(Call<Commission_response> call, Throwable t) {

            }
        });
        return commission;
    }

    public MutableLiveData<Message_response> getMessage(String balance) {
        Call<Message_response> call = cashOut.getMessage(balance);
        call.enqueue(new Callback<Message_response>() {
            @Override
            public void onResponse(Call<Message_response> call, Response<Message_response> response) {
                if (response.isSuccessful()) {
                    message.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<Message_response> call, Throwable t) {

            }
        });
        return message;
    }

    public MutableLiveData<List<Method_response>> getMethod() {
        if (methods == null) {
            methods = new MutableLiveData<>();
        }

        Call<List<Method_response>> call = cashOut.getMethod();
        call.enqueue(new Callback<List<Method_response>>() {
            @Override
            public void onResponse(Call<List<Method_response>> call, Response<List<Method_response>> response) {
                if (response.isSuccessful()) {
                    methods.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Method_response>> call, Throwable t) {

            }
        });
        return methods;
    }

    public MutableLiveData<List<AddAmount_response>> getAddAmounts() {
        if (addAmounts == null) {
            addAmounts = new MutableLiveData<>();
        }

        Call<List<AddAmount_response>> call = cashOut.getAmount();
        call.enqueue(new Callback<List<AddAmount_response>>() {
            @Override
            public void onResponse(Call<List<AddAmount_response>> call, Response<List<AddAmount_response>> response) {
                if (response.isSuccessful()) {
                    addAmounts.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<AddAmount_response>> call, Throwable t) {

            }
        });
        return addAmounts;
    }

}
