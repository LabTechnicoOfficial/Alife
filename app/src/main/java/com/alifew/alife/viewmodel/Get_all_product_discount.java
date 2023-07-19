package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.get_all_product_discount_repositories;

import com.alifew.alife.model.get_all_product_discount_response;

public class Get_all_product_discount extends ViewModel {
    get_all_product_discount_repositories repositories;

    public LiveData<get_all_product_discount_response> getData(String shop_id) {
        //repositories=new get_all_product_discount_repositories(shop_id);
        //return repositories.getData();
        return get_all_product_discount_repositories.getInstance().getData(shop_id);
    }
}
