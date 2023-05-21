package com.ALife.alife.EarningApp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.EarningApp.Model.AddLimit.addLimit_response;
import com.ALife.alife.EarningApp.Model.AddLimit.addLimit_repositories;
import com.ALife.alife.EarningApp.Model.AddLimit.update_addLimit_response;
import com.ALife.alife.EarningApp.Model.AddLimit.update_addLimit_repositories;

public class AddLimit extends ViewModel {
    public LiveData<addLimit_response> getCount(String user_id, String date) {
        return addLimit_repositories.getInstance().getData(user_id, date);
    }

    public LiveData<update_addLimit_response> getResponse(String user_id) {
        return update_addLimit_repositories.getInstance().getData(user_id);
    }
}
