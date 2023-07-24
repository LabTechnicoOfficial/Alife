package com.alifew.alife.EarningApp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.EarningApp.Model.SendMoney.sendMoney_repositories;
import com.alifew.alife.EarningApp.Model.SendMoney.sendMoney_response;

public class SendMoneyViewModel extends ViewModel {
    public LiveData<sendMoney_response> getResponse(String toUser_id, String toUser_name, String toUser_phone, String fromUser_id, String fromUser_name, String fromUser_phone, String amount, String date, String toUser_balance, String fromUser_balance) {
        return sendMoney_repositories.getInstance().getResponse(toUser_id, toUser_name, toUser_phone, fromUser_id, fromUser_name, fromUser_phone, amount, date, toUser_balance, fromUser_balance);
    }
}
