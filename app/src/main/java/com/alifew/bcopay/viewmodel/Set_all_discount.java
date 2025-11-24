package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.set_all_discount_repositories;
import com.alifew.bcopay.model.set_all_discount_response;

public class Set_all_discount extends ViewModel {
    private set_all_discount_repositories repositories;

    public LiveData<set_all_discount_response> getData(String shop_id, String discount) {
        // repositories=new set_all_discount_repositories(shop_id,discount);
        //return repositories.getData();
        return set_all_discount_repositories.getInstance().getData(shop_id, discount);
    }
}
