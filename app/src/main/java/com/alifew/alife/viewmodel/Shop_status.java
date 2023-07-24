package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.shop_status_response;
import com.alifew.alife.model.shop_status_repositories;

public class Shop_status extends ViewModel {
    public LiveData<shop_status_response> getStatus(String id)
    {
        return shop_status_repositories.getInstance().getData(id);
    }
}
