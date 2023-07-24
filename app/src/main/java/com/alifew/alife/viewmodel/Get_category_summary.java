package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.get_category_summary_repositories;
import com.alifew.alife.model.get_shop_products_summary_response;

public class Get_category_summary extends ViewModel {
    private get_category_summary_repositories repositories;

    public LiveData<get_shop_products_summary_response> get_summaryShop(String shop_id) {
        // repositories=new get_category_summary_repositories(shop_id);
        //return repositories.getSummary_shop();
        return get_category_summary_repositories.getInstance().getSummary_shop(shop_id);
    }

    public LiveData<get_shop_products_summary_response> get_SummaryAgent(String shop_id, String agent_id) {
        //repositories=new get_category_summary_repositories(shop_id, agent_id);
        //return repositories.getSummary_Agent();
        return get_category_summary_repositories.getInstance().getSummary_Agent(shop_id, agent_id);
    }
}
