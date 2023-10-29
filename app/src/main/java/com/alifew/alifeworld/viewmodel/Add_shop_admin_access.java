package com.alifew.alifeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.add_shop_admin_access_repositories;
import com.alifew.alifeworld.model.add_shop_admin_access_response;

public class Add_shop_admin_access extends ViewModel {
    add_shop_admin_access_repositories repositories;
    public LiveData<add_shop_admin_access_response> getData(String agent_id,String category_id)
    {
      //  repositories=new add_shop_admin_access_repositories(agent_id,category_id);
       // return  repositories.getData();
        return add_shop_admin_access_repositories.getInstance().getData(agent_id, category_id);
    }
}
