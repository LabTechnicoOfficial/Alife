package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.fetch_sub_shop_repositories;
import com.alifew.bcopay.model.fetch_sub_shop_response;

import java.util.List;

public class Fetch_sub_shop extends ViewModel {
    fetch_sub_shop_repositories repositories;

    public LiveData<List<fetch_sub_shop_response>> getData(String shop_id) {
        //repositories=new fetch_sub_shop_repositories(shop_id);
        //return repositories.getData();
        return fetch_sub_shop_repositories.getInstance().getData(shop_id);
    }
}
