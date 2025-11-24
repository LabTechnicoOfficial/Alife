package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.delete_shop_admin_repositories;
import com.alifew.bcopay.model.delete_shop_admin_response;

public class Delete_shop_admin extends ViewModel {
    delete_shop_admin_repositories repositories;

    public LiveData<delete_shop_admin_response> getData(String agent_id) {
        //repositories=new delete_shop_admin_repositories(agent_id);
        //return repositories.getData();
        return delete_shop_admin_repositories.getInstance().getData(agent_id);
    }
}
