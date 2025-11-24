package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.follow_customer_shop_repositories;
import com.alifew.bcopay.model.follow_customer_shop_response;

public class Follow_customer_shop extends ViewModel {
    follow_customer_shop_repositories repositories;

    public LiveData<follow_customer_shop_response> getData(String shop_id, String customer_id) {
        //repositories=new follow_customer_shop_repositories(shop_id,customer_id);
        //return  repositories.getData();
        return follow_customer_shop_repositories.getInstance().getData(shop_id, customer_id);
    }
}
