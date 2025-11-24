package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.fetch_customer_join_request_repositories;
import com.alifew.bcopay.model.Get_shop_customer_response;

import java.util.List;

public class Fetch_customer_join_request extends ViewModel {
    fetch_customer_join_request_repositories repositories;
    public LiveData<List<Get_shop_customer_response>> getData(String shop_id)
    {
        return fetch_customer_join_request_repositories.getInstance().getData(shop_id);
    }
}
