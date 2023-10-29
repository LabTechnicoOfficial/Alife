package com.alifew.alifeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.customer_token_update_repositories;
import com.alifew.alifeworld.model.shop_token_update_repositories;
import com.alifew.alifeworld.model.token_update_response;

public class Token_update extends ViewModel {
    private customer_token_update_repositories customer_token;
    private shop_token_update_repositories shop_token;

    public LiveData<token_update_response> customer_token_update(String customer_id, String token) {
        //customer_token=new customer_token_update_repositories(customer_id, token);
        //return customer_token.getData();
        return customer_token_update_repositories.getInstance().getData(customer_id, token);
    }

    public LiveData<token_update_response> shop_token_update(String shop_id, String token) {
        // shop_token=new shop_token_update_repositories(shop_id, token);
        //return shop_token.getData();
        return shop_token_update_repositories.getInstance().getData(shop_id, token);
    }
}
