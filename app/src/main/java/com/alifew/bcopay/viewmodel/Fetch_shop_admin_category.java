package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.Category_response;
import com.alifew.bcopay.model.fetch_shop_admin_category_repositories;

import java.util.List;

public class Fetch_shop_admin_category extends ViewModel {
    fetch_shop_admin_category_repositories repositories;

    public LiveData<List<Category_response>> getData(String agent_id, int page, int limit) {
        //repositories=new fetch_shop_admin_category_repositories(agent_id,page,limit);
        //return repositories.getData();
        return fetch_shop_admin_category_repositories.getInstance().getData(agent_id, page, limit);
    }

    public LiveData<List<Category_response>> getSearchData(String agent_id, String search) {
        //  repositories=new fetch_shop_admin_category_repositories(agent_id,search);
        //  return repositories.getSearchData();
        return fetch_shop_admin_category_repositories.getInstance().getSearchData(agent_id, search);
    }
}
