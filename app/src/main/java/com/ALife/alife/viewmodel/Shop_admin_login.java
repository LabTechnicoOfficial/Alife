package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.shop_admin_login_repositories;
import com.ALife.alife.model.shop_admin_login_response;

public class Shop_admin_login extends ViewModel {
    shop_admin_login_repositories repositories;
    public LiveData<shop_admin_login_response> getData(String phone,String password,String shop_id)
    {
        //repositories=new shop_admin_login_repositories(phone,password,shop_id);
        //return repositories.getData();
        return shop_admin_login_repositories.getInstance().getData(phone, password, shop_id);
    }
}
