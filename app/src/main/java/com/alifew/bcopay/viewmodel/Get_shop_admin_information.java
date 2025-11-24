package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.get_shop_admin_information_repositories;
import com.alifew.bcopay.model.get_shop_admin_information_response;

public class Get_shop_admin_information extends ViewModel {
    get_shop_admin_information_repositories repositories;

    public LiveData<get_shop_admin_information_response> getData(String agent_id) {
        // repositories=new get_shop_admin_information_repositories(agent_id);
        // return repositories.getData();
        return get_shop_admin_information_repositories.getInstance().getData(agent_id);
    }
}
