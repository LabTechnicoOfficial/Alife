package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.shop_registration_repositoris;

public class Shop_registration extends ViewModel {
    shop_registration_repositoris repositoris;


    public LiveData<String> getvarification(String phone) {
        //repositoris = new shop_registration_repositoris(phone);
        //return repositoris.getVarefication();
        return shop_registration_repositoris.getInstance().getVarefication(phone);
    }

    public LiveData<String> getmessage(String name, String owner, String location, String phone, String password, String image, String token, String latitude, String longitude) {
        //repositoris = new shop_registration_repositoris(name, owner, location, phone, password, image,token);
        //return repositoris.getMessage();
        return shop_registration_repositoris.getInstance().getMessage(name, owner, location, phone, password, image, token, latitude, longitude);
    }
}
