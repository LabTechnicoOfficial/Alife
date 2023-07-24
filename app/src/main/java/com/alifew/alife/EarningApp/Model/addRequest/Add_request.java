package com.alifew.alife.EarningApp.Model.addRequest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class Add_request extends ViewModel {
    public LiveData<add_request_response> getData(String user_id) {
        return add_request_repositories.getInstance().getData(user_id);
    }

    public LiveData<add_request_response> updateData(String user_id) {
        return add_request_repositories.getInstance().updateData(user_id);
    }
}
