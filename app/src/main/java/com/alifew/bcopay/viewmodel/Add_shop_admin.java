package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.add_shop_admin_repositories;
import com.alifew.bcopay.model.add_shop_admin_response;

public class Add_shop_admin extends ViewModel {
    add_shop_admin_repositories repositories;
    public LiveData<add_shop_admin_response> getData(String name,String phone,String password,String image,String shop_id,String access)
    {
       // repositories=new add_shop_admin_repositories(name,phone,password,image,shop_id,access);
       // return repositories.getData();
        return add_shop_admin_repositories.getInstance().getData(name, phone, password, image, shop_id, access);
    }
}
