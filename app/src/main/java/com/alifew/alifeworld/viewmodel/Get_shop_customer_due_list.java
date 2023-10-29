package com.alifew.alifeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.get_shop_customer_due_list_repositories;
import com.alifew.alifeworld.model.get_shop_customer_due_list_response;

import java.util.List;

public class Get_shop_customer_due_list extends ViewModel {
    get_shop_customer_due_list_repositories repositories;

    public LiveData<List<get_shop_customer_due_list_response>> getData(String shop_id, String customer_id,String customer_phone, int page,int limit) {
        //repositories = new get_shop_customer_due_list_repositories(shop_id, customer_id);

        //return repositories.getData();
        return get_shop_customer_due_list_repositories.getInstance().getData(shop_id, customer_id,customer_phone,page,limit);
    }
}
