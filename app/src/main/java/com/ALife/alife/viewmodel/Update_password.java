package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.update_customer_password_repositories;
import com.ALife.alife.model.update_password_response;
import com.ALife.alife.model.update_shop_password_repositories;

public class Update_password extends ViewModel {
    update_shop_password_repositories shop_password;
    update_customer_password_repositories customer_password;

    public LiveData<update_password_response> shop(String shop_id, String password) {
        // shop_password=new update_shop_password_repositories(shop_id, password);
        // return shop_password.getData();
        return update_shop_password_repositories.getInstance().getData(shop_id, password);
    }

    public LiveData<update_password_response> customer(String customer_id, String password) {
        // customer_password=new update_customer_password_repositories(customer_id, password);
        // return customer_password.getData();
        return update_customer_password_repositories.getInstance().getData(customer_id,password);
    }
}
