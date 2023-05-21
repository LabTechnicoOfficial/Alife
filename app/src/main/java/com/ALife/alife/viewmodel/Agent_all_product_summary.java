package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.get_operator_product_summary_repositories;
import com.ALife.alife.model.get_shop_products_summary_response;

public class Agent_all_product_summary extends ViewModel {
    private get_operator_product_summary_repositories repositories;
    public LiveData<get_shop_products_summary_response> getSummary(String shop_id,String agent_id)
    {
        //repositories=new get_operator_product_summary_repositories(shop_id, agent_id);
        //return repositories.getData();
        return get_operator_product_summary_repositories.getInstance().getData(shop_id, agent_id);
    }
}
