package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.shop_sell_history_summary_response;
import com.alifew.bcopay.model.shop_sell_history_list_response;
import com.alifew.bcopay.model.shop_sell_history_repositories;

import java.util.List;

public class Shop_sell_history extends ViewModel {
    public LiveData<shop_sell_history_summary_response> getAllSummary(String shop_id) {
        return shop_sell_history_repositories.getInstance().getAllSummary(shop_id);
    }

    public LiveData<shop_sell_history_summary_response> getDailySummary(String shop_id, String date) {
        return shop_sell_history_repositories.getInstance().getDailySummary(shop_id, date);
    }

    public LiveData<shop_sell_history_summary_response> getSelectedSummary(String shop_id, String date1, String date2) {
        return shop_sell_history_repositories.getInstance().getSelectedSummary(shop_id, date1, date2);
    }

    public LiveData<List<shop_sell_history_list_response>> getAlllist(String shop_id, int page, int limit) {
        return shop_sell_history_repositories.getInstance().getAllList(shop_id, page, limit);
    }

    public LiveData<List<shop_sell_history_list_response>> getDailylist(String shop_id, String date, int page, int limit) {
        return shop_sell_history_repositories.getInstance().getDailyList(shop_id, date, page, limit);
    }

    public LiveData<List<shop_sell_history_list_response>> getSelectedlist(String shop_id, String date1, String date2, int page, int limit) {
        return shop_sell_history_repositories.getInstance().getSelectedList(shop_id, date1, date2, page, limit);
    }

}
