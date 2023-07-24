package com.alifew.alife.EarningApp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.alifew.alife.EarningApp.Model.CashOut.AddAmount_response;
import com.alifew.alife.EarningApp.Model.CashOut.Commission_response;
import com.alifew.alife.EarningApp.Model.CashOut.Message_response;
import com.alifew.alife.EarningApp.Model.CashOut.Method_response;
import com.alifew.alife.EarningApp.Model.CashOut.cashOut_repositories;
import com.alifew.alife.EarningApp.Model.CashOut.cashOut_request_response;
import com.alifew.alife.EarningApp.Model.CashOut.pending_cashOut_repositories;
import com.alifew.alife.EarningApp.Model.CashOut.pending_cashOut_response;

import java.util.List;

public class CashOut extends ViewModel {
    public LiveData<cashOut_request_response> getCashOutResponse(String userID, String username, String phone, String amount, String balance, String date, String method) {
        return cashOut_repositories.getInstance().getData(userID, username, phone, amount, balance, date, method);
    }

    public LiveData<List<pending_cashOut_response>> getpendingRequest(String user_id, String token) {
        return pending_cashOut_repositories.getInstance().getData(user_id, token);
    }

    public LiveData<Commission_response> getCommission() {
        return cashOut_repositories.getInstance().getCommission();
    }

    public LiveData<Message_response> getMessage(String balance) {
        return cashOut_repositories.getInstance().getMessage(balance);
    }

    public LiveData<List<Method_response>> getMethod() {
        return cashOut_repositories.getInstance().getMethod();
    }

    public LiveData<List<AddAmount_response>> getAmounts() {
        return cashOut_repositories.getInstance().getAddAmounts();
    }
}
