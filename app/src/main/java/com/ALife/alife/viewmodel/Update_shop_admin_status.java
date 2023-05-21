package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.update_shop_admin_status_repositories;
import com.ALife.alife.model.update_shop_admin_status_response;

public class Update_shop_admin_status extends ViewModel {
    update_shop_admin_status_repositories repositories;

    public LiveData<update_shop_admin_status_response> getData(String agent_id, String value) {
        //repositories=new update_shop_admin_status_repositories(agent_id,value);
        //return repositories.getData();
        return update_shop_admin_status_repositories.getInstance().getData(agent_id, value);
    }
}
