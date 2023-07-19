package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.phone_verification_customer_repositories;
import com.alifew.alife.model.phone_verification_response;
import com.alifew.alife.model.phone_verification_shop_repositories;

public class Phone_verification extends ViewModel {
    phone_verification_shop_repositories shop;
    phone_verification_customer_repositories customer;

    public LiveData<phone_verification_response> shop_phone(String phone) {
        // shop=new phone_verification_shop_repositories(phone);
        // return shop.getData();
        return phone_verification_shop_repositories.getInstance().getData(phone);
    }

    public LiveData<phone_verification_response> customer_phone(String phone) {
        // customer=new phone_verification_customer_repositories(phone);
        // return customer.getData();
        return phone_verification_customer_repositories.getInstance().getData(phone);
    }
}
