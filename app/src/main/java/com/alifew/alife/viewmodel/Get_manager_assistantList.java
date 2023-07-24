package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.fetch_shop_admin_response;
import com.alifew.alife.model.get_manager_assistantList_repositories;

import java.util.List;

public class Get_manager_assistantList extends ViewModel {
    get_manager_assistantList_repositories repositories;

    public LiveData<List<fetch_shop_admin_response>> getData(String manager_id) {
        //repositories=new get_manager_assistantList_repositories(manager_id);
        //return repositories.getData();
        return get_manager_assistantList_repositories.getInstance().getData(manager_id);
    }
}
