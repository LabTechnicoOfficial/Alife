package com.alifew.alife.EarningApp.Model.SendMoney;

import androidx.lifecycle.MutableLiveData;


import com.alifew.alife.EarningApp.Model.APIUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sendMoney_repositories {
    private sendMoney_api sendMoney;
    private MutableLiveData<sendMoney_response> data;
    private static sendMoney_repositories sendMoney_repositories;

    public sendMoney_repositories() {
        sendMoney = APIUtilize.sendMoney();
        data = new MutableLiveData<>();
    }

    public synchronized static sendMoney_repositories getInstance() {
        if (sendMoney_repositories == null)
            return new sendMoney_repositories();
        return sendMoney_repositories;
    }

    public MutableLiveData<sendMoney_response> getResponse(String toUser_id, String toUser_name, String toUser_phone, String fromUser_id, String fromUser_name, String fromUser_phone, String amount, String date, String toUser_balance, String fromUser_balance) {
        Call<sendMoney_response> call = sendMoney.sendMoney(toUser_id, toUser_name, toUser_phone, fromUser_id, fromUser_name, fromUser_phone, amount, date, toUser_balance, fromUser_balance);
        call.enqueue(new Callback<sendMoney_response>() {
            @Override
            public void onResponse(Call<sendMoney_response> call, Response<sendMoney_response> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<sendMoney_response> call, Throwable t) {

            }
        });
        return data;
    }
}
