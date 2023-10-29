package com.alifew.alifeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.fetch_all_customer_repositories;
import com.alifew.alifeworld.model.Get_shop_customer_response;

import java.util.List;

public class Fetch_all_customer extends ViewModel {
    fetch_all_customer_repositories repositories;
    public LiveData<List<Get_shop_customer_response>> getData()
    {
        //repositories=new fetch_all_customer_repositories();
        //return  repositories.getData();
        return fetch_all_customer_repositories.getInstance().getData();
    }
}
