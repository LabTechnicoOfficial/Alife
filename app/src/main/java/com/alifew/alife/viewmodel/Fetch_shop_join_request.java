package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.fetch_shop_join_request_repositories;
import com.alifew.alife.model.fetch_shop_response;

import java.util.List;

public class Fetch_shop_join_request extends ViewModel {
    fetch_shop_join_request_repositories repositories;

    public LiveData<List<fetch_shop_response>> getData(String customer_id) {
        //repositories=new fetch_shop_join_request_repositories(customer_id);
        //return repositories.getData();
        return fetch_shop_join_request_repositories.getInstance().getData(customer_id);
    }
}
