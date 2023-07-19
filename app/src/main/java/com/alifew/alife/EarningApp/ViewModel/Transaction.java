package com.alifew.alife.EarningApp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.alifew.alife.EarningApp.Model.Transaction.transactionHistory_repositories;
import com.alifew.alife.EarningApp.Model.Transaction.transactionHistory_response;

import java.util.List;

public class Transaction extends ViewModel {
    public LiveData<List<transactionHistory_response>> getHistory(String user_id, String token) {
        return transactionHistory_repositories.getInstance().getData(user_id, token);
    }
}
