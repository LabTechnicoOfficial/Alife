package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.shop_profile_repositories;
import com.alifew.alife.model.shop_profile_response;

public class Shop_profile extends ViewModel {

    shop_profile_repositories shop_profile;


    public LiveData<shop_profile_response> getData(String shop_id) {
        //shop_profile=new shop_profile_repositories(shop_id);
        //return  shop_profile.getdata();
        return shop_profile_repositories.getInstance().getdata(shop_id);
    }
}
