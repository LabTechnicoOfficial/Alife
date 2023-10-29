package com.alifew.alifeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.Update_product_status_repositories;
import com.alifew.alifeworld.model.Update_product_status_response;

public class Update_product_status extends ViewModel {
    Update_product_status_repositories repositories;

    public LiveData<Update_product_status_response> getData(String id, String value) {
        //repositories=new Update_product_status_repositories(id,value);
        //return  repositories.getData();
        return Update_product_status_repositories.getInstance().getData(id, value);
    }
}
