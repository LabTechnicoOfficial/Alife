package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.customer_profile_repositories;
import com.alifew.alife.model.customer_profile_response;

public class Customer_profile extends ViewModel {
    customer_profile_repositories repositories;

    public LiveData<customer_profile_response> getData(String customer_id) {

        return customer_profile_repositories.getInstance().getdata(customer_id);
    }
}
