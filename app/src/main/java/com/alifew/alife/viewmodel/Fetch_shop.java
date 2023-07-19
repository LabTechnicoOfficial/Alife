package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.fetch_shop_repositories;
import com.alifew.alife.model.fetch_shop_response;

import java.util.List;

public class Fetch_shop extends ViewModel {
    fetch_shop_repositories repositories;
    public LiveData<List<fetch_shop_response>> getData()
    {
        //repositories=new fetch_shop_repositories();
        //return repositories.getData();
        return fetch_shop_repositories.getInstance().getData();
    }
}
