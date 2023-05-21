package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.customer_shopList_repositories;
import com.ALife.alife.model.customer_shopList_response;
import com.ALife.alife.model.get_customer_all_due_details_repositories;
import com.ALife.alife.model.get_customer_all_due_details_response;

import java.util.List;

public class Customer_shopList extends ViewModel {
    customer_shopList_repositories repositories;
    public LiveData<List<customer_shopList_response>> getData(String customer_id,int page,int limit)
    {
       // repositories=new customer_shopList_repositories(customer_id,page,limit);
        //return repositories.getData();
        return customer_shopList_repositories.getInstance().getData(customer_id, page, limit);
    }
    public LiveData<List<customer_shopList_response>> getSearchData(String customer_id,String search)
    {
        //repositories=new customer_shopList_repositories(customer_id,search);
        //return repositories.getSearchData();
        return customer_shopList_repositories.getInstance().getSearchData(customer_id, search);
    }
    private get_customer_all_due_details_repositories repositories1;
    public LiveData<List<get_customer_all_due_details_response>> getDue_details(String customer_id,int page,int limit)
    {
        //repositories1=new get_customer_all_due_details_repositories(customer_id,page,limit);
        //return repositories1.getData();
        return get_customer_all_due_details_repositories.getInstance().getData(customer_id, page, limit);
    }
}
