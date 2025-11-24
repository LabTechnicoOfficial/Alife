package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.update_customer_repositories;
import com.alifew.bcopay.model.update_customer_response;

public class Update_customer extends ViewModel {
    update_customer_repositories repositories;

    public LiveData<update_customer_response> getData(String customer_id, String customer_name, String customer_location, String customer_image, int token) {


        //repositories = new update_customer_repositories(customer_id, customer_name, customer_location, customer_image);
        if (token == 0) {
            //return repositories.getData2();
            return update_customer_repositories.getInstance().getData2(customer_id, customer_name, customer_location);
        } else {
            //return repositories.getData1();
            return update_customer_repositories.getInstance().getData1(customer_id, customer_name, customer_location, customer_image);
        }


    }
}

