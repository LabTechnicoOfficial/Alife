package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.customer_exist_check_response;
import com.alifew.bcopay.model.customer_exist_check_repositories;
public class Customer_exist_check extends ViewModel {
    public LiveData<customer_exist_check_response> getData(String phone)
    {
        return customer_exist_check_repositories.getInstance().getData(phone);
    }
}
