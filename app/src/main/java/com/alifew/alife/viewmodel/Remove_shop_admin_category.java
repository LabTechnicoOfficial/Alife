package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.remove_shop_admin_category_repositories;
import com.alifew.alife.model.remove_shop_admin_category_response;

public class Remove_shop_admin_category extends ViewModel {
    remove_shop_admin_category_repositories repositories;

    public LiveData<remove_shop_admin_category_response> getData(String agent_id, String category_id) {
        //repositories=new remove_shop_admin_category_repositories(agent_id,category_id);
        //return repositories.getData();
        return remove_shop_admin_category_repositories.getInstance().getData(agent_id, category_id);
    }
}
