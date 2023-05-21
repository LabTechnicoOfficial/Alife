package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.get_shop_all_due_details_repositories;
import com.ALife.alife.model.get_shop_all_due_details_response;
import com.ALife.alife.model.get_shop_customer_repositories;
import com.ALife.alife.model.get_shop_customer_response;
import com.ALife.alife.model.shop_due_customer_repositories;
import com.ALife.alife.model.shop_due_customer_response;

import java.util.List;

public class Shop_customer extends ViewModel {
    get_shop_customer_repositories repositories;
    get_shop_all_due_details_repositories due_repositories;

    shop_due_customer_repositories due_customer_repositories;

    public LiveData<List<get_shop_customer_response>> getData(String shop_id, int page, int limit) {
        // repositories = new get_shop_customer_repositories(shop_id, page, limit);
        // return repositories.getData();
        return get_shop_customer_repositories.getInstance().getData(shop_id, page, limit);
    }

    public LiveData<List<get_shop_customer_response>> getSearchData(String shop_id, String search) {
        //repositories = new get_shop_customer_repositories(shop_id, search);
        //return repositories.getSearchData();
        return get_shop_customer_repositories.getInstance().getSearchData(shop_id, search);
    }

    public LiveData<List<get_shop_customer_response>> get_selected_customer(String shop_id) {
        //repositories = new get_shop_customer_repositories(shop_id);
        //return repositories.get_selected_customer();
        return get_shop_customer_repositories.getInstance().get_selected_customer(shop_id);
    }

    public LiveData<List<get_shop_all_due_details_response>> get_dueList(String shop_id, int page, int limit) {
        // due_repositories = new get_shop_all_due_details_repositories(shop_id, page, limit);
        //return due_repositories.getData();
        return get_shop_all_due_details_repositories.getInstance().getData(shop_id, page, limit);
    }

    public LiveData<List<get_shop_all_due_details_response>> get_daily_due_details(String shop_id, String date, int page, int limit) {
        // due_repositories = new get_shop_all_due_details_repositories(shop_id, date, page, limit);
        // return due_repositories.get_daily_details();
        return get_shop_all_due_details_repositories.getInstance().get_daily_details(shop_id, date, page, limit);
    }

    public LiveData<List<get_shop_all_due_details_response>> get_selected_days_due_details(String shop_id, String date1, String date2, int page, int limit) {
        //due_repositories = new get_shop_all_due_details_repositories(shop_id, date1, date2, page, limit);
        //return due_repositories.get_selected_days_details();
        return get_shop_all_due_details_repositories.getInstance().get_selected_days_details(shop_id, date1, date2, page, limit);
    }

    public LiveData<List<shop_due_customer_response>> get_due_customer(String shop_id) {
        //due_customer_repositories = new shop_due_customer_repositories(shop_id);
        //return due_customer_repositories.getData();
        return shop_due_customer_repositories.getInstance().getData(shop_id);
    }

    public LiveData<List<shop_due_customer_response>> get_due_customer_by_search(String shop_id, String search) {
        // due_customer_repositories = new shop_due_customer_repositories(shop_id, search);
        //return due_customer_repositories.getSearchData();
        return shop_due_customer_repositories.getInstance().getSearchData(shop_id, search);
    }
}
