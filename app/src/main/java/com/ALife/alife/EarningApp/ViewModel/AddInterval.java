package com.ALife.alife.EarningApp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.EarningApp.Model.AddInterval.addInterval_response;
import com.ALife.alife.EarningApp.Model.AddLimit.addLimit_response;
import com.ALife.alife.EarningApp.Model.AddInterval.addInterval_repositories;
public class AddInterval extends ViewModel {
    public LiveData<addInterval_response> getResponse(String user_id, String date, int key)
    {
        return addInterval_repositories.getInstance().getData(user_id, date,key);
    }
}
