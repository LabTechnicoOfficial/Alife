package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.last_logintime_response;
import com.alifew.alife.model.last_logintime_repositories;
import com.alifew.alife.model.update_last_logintime_response;

public class Last_logintime extends ViewModel {
    public LiveData<last_logintime_response> getTime(String user_id,String type)
    {
        return last_logintime_repositories.getInstance().getData(user_id, type);
    }
    public LiveData<update_last_logintime_response> getUpdate(String user_id,String logintime,String user_type)
    {
        return last_logintime_repositories.getInstance().getUpdate_response(user_id, logintime, user_type);
    }
}
