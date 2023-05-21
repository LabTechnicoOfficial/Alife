package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.add_remove_shop_customer_repositories;
import com.ALife.alife.model.add_remove_shop_customer_response;

public class Add_remove_shop_customer extends ViewModel {
    add_remove_shop_customer_repositories repositories;
    public LiveData<add_remove_shop_customer_response> getDataAdd(String shop_id,String customer_id)
    {
        //repositories=new add_remove_shop_customer_repositories(shop_id,customer_id);
        //return repositories.getDataAdd();
        return add_remove_shop_customer_repositories.getInstance().getDataAdd(shop_id, customer_id);

    }
    public LiveData<add_remove_shop_customer_response> getDataRemove(String shop_id,String customer_id)
    {
        //repositories=new add_remove_shop_customer_repositories(shop_id,customer_id);
        //return repositories.getDataRemove();
        return add_remove_shop_customer_repositories.getInstance().getDataRemove(shop_id, customer_id);
    }
    public LiveData<add_remove_shop_customer_response> getDatamanually(String shop_id,String customer_id)
    {
        //repositories=new add_remove_shop_customer_repositories(shop_id,customer_id);
        //return repositories.getData_Manually();
        return add_remove_shop_customer_repositories.getInstance().getData_Manually(shop_id, customer_id);
    }
}
