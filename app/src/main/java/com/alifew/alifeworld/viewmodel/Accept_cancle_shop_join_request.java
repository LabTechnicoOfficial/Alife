package com.alifew.alifeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.accept_cancle_shop_join_request_repositories;
import com.alifew.alifeworld.model.accept_cancle_shop_join_request_response;

public class Accept_cancle_shop_join_request extends ViewModel {
    accept_cancle_shop_join_request_repositories repositories;
    public LiveData<accept_cancle_shop_join_request_response> getData1(String customer_id, String shop_id)
    {
        //repositories=new accept_cancle_shop_join_request_repositories(customer_id,shop_id);
        //return repositories.getData1();
        return accept_cancle_shop_join_request_repositories.getInstance().getData1(customer_id, shop_id);
    }
    public LiveData<accept_cancle_shop_join_request_response> getData2(String customer_id, String shop_id)
    {
        //repositories=new accept_cancle_shop_join_request_repositories(customer_id,shop_id);
        //return repositories.getData2();
        return accept_cancle_shop_join_request_repositories.getInstance().getData2(customer_id, shop_id);
    }
}
