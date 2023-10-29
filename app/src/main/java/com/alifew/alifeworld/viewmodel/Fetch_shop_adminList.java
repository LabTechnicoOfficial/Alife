package com.alifew.alifeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.fetch_shop_adminList_repositories;
import com.alifew.alifeworld.model.fetch_shop_admin_response;

import java.util.List;

public class Fetch_shop_adminList extends ViewModel {
    fetch_shop_adminList_repositories repositories;

    public LiveData<List<fetch_shop_admin_response>> getData(String shop_id) {
        // repositories=new fetch_shop_adminList_repositories(shop_id);
        // return repositories.getData();
        return fetch_shop_adminList_repositories.getInstance().getData(shop_id);
    }
}
