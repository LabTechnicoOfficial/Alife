package com.alifew.alife.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alifew.alife.model.Shop_login_repositories;
import com.alifew.alife.model.Shop_login_response;

public class Shop_login extends AndroidViewModel {
    //Shop_login_repositories repositories;
    public Shop_login(@NonNull Application application) {
        super(application);
    }

    public LiveData<Shop_login_response> getmessage(String phone, String password)
    {
        //repositories=new Shop_login_repositories(phone,password);
       // return repositories.getIdMessage();
        return Shop_login_repositories.getInstance().getIdMessage(phone, password);
    }

}
