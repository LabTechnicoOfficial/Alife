package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.get_all_shop_product_repositories;
import com.ALife.alife.model.get_product_response;

import java.util.List;

public class Get_all_shop_product extends ViewModel {
    get_all_shop_product_repositories repositories;

    public LiveData<List<get_product_response>> getData(String shop_id, int page, int limit) {
        //repositories=new get_all_shop_product_repositories(shop_id,page,limit);
        //return repositories.getData();
        return get_all_shop_product_repositories.getInstance().getData(shop_id, page, limit);
    }

    public LiveData<List<get_product_response>> getSearchData(String shop_id) {
        // repositories=new get_all_shop_product_repositories(shop_id);
        //return repositories.getSearchData();
        return get_all_shop_product_repositories.getInstance().getSearchData(shop_id);
    }
}
