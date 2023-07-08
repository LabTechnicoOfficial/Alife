package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.get_operator_all_product_repositories;
import com.ALife.alife.model.Get_product_response;

import java.util.List;

public class Get_operator_product extends ViewModel {
    private get_operator_all_product_repositories repositories;

    public LiveData<List<Get_product_response>> getData(String agent_id, int page, int limit) {
        //repositories=new get_operator_all_product_repositories(agent_id,page,limit);
        //return repositories.getData();
        return get_operator_all_product_repositories.getInstance().getData(agent_id, page, limit);
    }

    public LiveData<List<Get_product_response>> getSearchData(String agent_id) {
        //repositories=new get_operator_all_product_repositories(agent_id);
        //return repositories.getSearchData();
        return get_operator_all_product_repositories.getInstance().getSearchData(agent_id);
    }
}
