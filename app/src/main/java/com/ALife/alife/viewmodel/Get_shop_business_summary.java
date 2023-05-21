package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.get_shop_business_summary_details_response;
import com.ALife.alife.model.get_shop_business_summary_repositories;
import com.ALife.alife.model.get_shop_business_summary_response;

import java.util.List;

public class Get_shop_business_summary extends ViewModel {
    private get_shop_business_summary_repositories repositories;

    public LiveData<List<get_shop_business_summary_response>> getData(String shop_id, int page, int limit) {
        //repositories=new get_shop_business_summary_repositories(shop_id,page,limit);
        //return repositories.getData();
        return get_shop_business_summary_repositories.getInstance().getData(shop_id, page, limit);
    }

    public LiveData<get_shop_business_summary_details_response> getDetails(String shop_id) {
        //repositories=new get_shop_business_summary_repositories(shop_id);
        ///return repositories.getDetails();
        return get_shop_business_summary_repositories.getInstance().getDetails(shop_id);
    }

}
