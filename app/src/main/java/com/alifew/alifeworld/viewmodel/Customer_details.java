package com.alifew.alifeworld.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alifew.alifeworld.model.CommonResponse;
import com.alifew.alifeworld.model.Customer_details_repositories;
import com.alifew.alifeworld.model.Customer_response;

public class Customer_details extends AndroidViewModel {
    Customer_details_repositories repositories;

    public Customer_details(@NonNull Application application) {
        super(application);
    }

    public LiveData<Customer_response> getdata(String id) {
        //repositories=new Customer_details_repositories(id);
        //return repositories.getdata();
        return Customer_details_repositories.getInstance().getdata(id);
    }

    public LiveData<CommonResponse> addReferCode(String userID, String referCode) {
        return Customer_details_repositories.getInstance().addReferCode(userID, referCode);
    }
}
