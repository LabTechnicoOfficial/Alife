package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.fetch_customer_join_request_repositories;
import com.alifew.alife.model.Get_shop_customer_response;

import java.util.List;

public class Fetch_customer_join_request extends ViewModel {
    fetch_customer_join_request_repositories repositories;
    public LiveData<List<Get_shop_customer_response>> getData(String shop_id)
    {
       // repositories=new fetch_customer_join_request_repositories(shop_id);
       // return repositories.getData();
        return fetch_customer_join_request_repositories.getInstance().getData(shop_id);
    }
}
