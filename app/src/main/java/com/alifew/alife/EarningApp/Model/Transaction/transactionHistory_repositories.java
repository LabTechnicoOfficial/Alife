package com.alifew.alife.EarningApp.Model.Transaction;

import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.EarningApp.Model.APIUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class transactionHistory_repositories {
    private transactionHistory_api transactionHistory;
    private MutableLiveData<List<transactionHistory_response>> data;
    private static transactionHistory_repositories transactionHistory_repositories;

    public transactionHistory_repositories() {
        transactionHistory = APIUtilize.transactionHistory();
        data = new MutableLiveData<>();
    }

    public synchronized static transactionHistory_repositories getInstance() {
        if (transactionHistory_repositories == null)
            return new transactionHistory_repositories();
        return transactionHistory_repositories;
    }

    public MutableLiveData<List<transactionHistory_response>> getData(String user_id, String token) {
        Call<List<transactionHistory_response>> call = transactionHistory.getResponse(user_id, token);
        call.enqueue(new Callback<List<transactionHistory_response>>() {
            @Override
            public void onResponse(Call<List<transactionHistory_response>> call, Response<List<transactionHistory_response>> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<transactionHistory_response>> call, Throwable t) {

            }
        });
        return data;
    }
}
