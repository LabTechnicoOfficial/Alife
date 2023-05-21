package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.accept_cancle_customer_join_request_repositories;
import com.ALife.alife.model.accept_cancle_customer_join_request_response;

public class Accept_cancle_customer_join_request extends ViewModel {
    accept_cancle_customer_join_request_repositories repositories;
    public LiveData<accept_cancle_customer_join_request_response> getData1(String shop_id, String customer_id)
    {
        //repositories=new accept_cancle_customer_join_request_repositories(shop_id,customer_id);
        //return repositories.getData1();
        return accept_cancle_customer_join_request_repositories.getInstance().getData1(shop_id, customer_id);

    }
    public LiveData<accept_cancle_customer_join_request_response> getData2(String shop_id, String customer_id)
    {
       // repositories=new accept_cancle_customer_join_request_repositories(shop_id,customer_id);
       // return repositories.getData2();
        return accept_cancle_customer_join_request_repositories.getInstance().getData2(shop_id, customer_id);
    }
}
