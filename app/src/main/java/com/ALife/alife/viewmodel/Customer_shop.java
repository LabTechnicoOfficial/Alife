package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.customer_due_shop_list_repositories;
import com.ALife.alife.model.customer_due_shop_list_response;
import com.ALife.alife.model.get_customer_all_due_details_repositories;
import com.ALife.alife.model.get_customer_all_due_details_response;

import java.util.List;

public class Customer_shop extends ViewModel {
    private get_customer_all_due_details_repositories repositories;
    private customer_due_shop_list_repositories due_shop_list_repositories;
    public LiveData<List<get_customer_all_due_details_response>> getDue_details(String customer_id,int page,int limit)
    {
       // repositories=new get_customer_all_due_details_repositories(customer_id,page,limit);
       // return repositories.getData();
        return  get_customer_all_due_details_repositories.getInstance().getData(customer_id, page, limit);
    }
    public LiveData<List<get_customer_all_due_details_response>> getDaily_due_details(String customer_id,String date,int page,int limit)
    {
       // repositories=new get_customer_all_due_details_repositories(customer_id,date,page,limit);
        //return repositories.get_daily_details();
        return get_customer_all_due_details_repositories.getInstance().get_daily_details(customer_id, date, page, limit);
    }
    public LiveData<List<get_customer_all_due_details_response>> getSelected_due_details(String customer_id,String date1,String date2,int page,int limit)
    {
        //repositories=new get_customer_all_due_details_repositories(customer_id,date1,date2,page,limit);
        //return repositories.get_selected_details();
        return get_customer_all_due_details_repositories.getInstance().get_selected_details(customer_id, date1, date2, page, limit);
    }
    public LiveData<List<customer_due_shop_list_response>> getDueShop(String customer_id)
    {
        //due_shop_list_repositories=new customer_due_shop_list_repositories(customer_id);
        //return due_shop_list_repositories.getData();
        return customer_due_shop_list_repositories.getInstance().getData(customer_id);
    }
}
