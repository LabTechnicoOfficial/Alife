package com.ALife.alife.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ALife.alife.model.Customer_login_repositories;
import com.ALife.alife.model.Shop_login_response;

public class Customer_login extends AndroidViewModel {
    Customer_login_repositories repositories;
    public Customer_login(@NonNull Application application) {
        super(application);
    }

    public LiveData<Shop_login_response> getmessage(String phone, String password)
    {
        //repositories=new Customer_login_repositories(phone,password);
        //return repositories.getIdMessage();
        return Customer_login_repositories.getInstance().getIdMessage(phone, password);
    }
}
