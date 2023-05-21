package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.get_shop_products_summary_repositories;
import com.ALife.alife.model.get_shop_products_summary_response;

public class Shop_products_summary extends ViewModel {
    private get_shop_products_summary_repositories repositories;

    public LiveData<get_shop_products_summary_response> getData_category(String category_id, String shop_id) {
        //repositories=new get_shop_products_summary_repositories(category_id, shop_id);
        //return repositories.getData_category();
        return get_shop_products_summary_repositories.getInstance().getData_category(category_id, shop_id);
    }

    public LiveData<get_shop_products_summary_response> getData_all(String shop_id) {
        //repositories=new get_shop_products_summary_repositories(shop_id);
        //return repositories.getData_all();
        return get_shop_products_summary_repositories.getInstance().getData_all(shop_id);
    }
}
