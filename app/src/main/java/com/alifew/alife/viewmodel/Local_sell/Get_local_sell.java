package com.alifew.alife.viewmodel.Local_sell;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.local_sell.customer_phone_response;
import com.alifew.alife.model.local_sell.get_local_sell_details_response;
import com.alifew.alife.model.local_sell.Get_local_sell_product_response;
import com.alifew.alife.model.local_sell.get_local_sell_repositories;
import com.alifew.alife.model.local_sell.get_product_by_bar_code_response;
import com.alifew.alife.model.local_sell.local_sell_history_response;
import com.alifew.alife.model.local_sell.local_sell_summary_response;

import java.util.List;

public class Get_local_sell extends ViewModel {
    public LiveData<List<Get_local_sell_product_response>> getData_product(String shop_id) {
        return get_local_sell_repositories.getInstance().getData_product(shop_id);
    }

    public LiveData<List<Get_local_sell_product_response>> getData_product_bySearch(String shop_id, String search) {
        return get_local_sell_repositories.getInstance().getData_product_bySearch(shop_id, search);
    }


    public LiveData<get_local_sell_details_response> getDetails(String sell_id) {
        return get_local_sell_repositories.getInstance().getSell_details(sell_id);
    }

    public LiveData<get_product_by_bar_code_response> getProduct(String bar_code, String shop_id) {
        return get_local_sell_repositories.getInstance().getProduct(bar_code, shop_id);
    }

    public LiveData<List<customer_phone_response>> getCustomer(String shop_id, int page, int limit) {
        return get_local_sell_repositories.getInstance().getCustomer(shop_id, page, limit);
    }

    //local sell history

    public LiveData<List<local_sell_history_response>> allHistory(String shop_id, int page, int limit) {
        return get_local_sell_repositories.getInstance().allHistory(shop_id, page, limit);
    }

    public LiveData<List<local_sell_history_response>> dailyHistory(String shop_id, String date, int page, int limit) {
        return get_local_sell_repositories.getInstance().dailyHistory(shop_id, date, page, limit);
    }

    public LiveData<List<local_sell_history_response>> selectedHistory(String shop_id, String date1, String date2, int page, int limit) {
        return get_local_sell_repositories.getInstance().selectedHistory(shop_id, date1, date2, page, limit);
    }

    public LiveData<local_sell_summary_response> allSummary(String shop_id) {
        return get_local_sell_repositories.getInstance().allSummary(shop_id);
    }

    public LiveData<local_sell_summary_response> dailySummary(String shop_id, String date) {
        return get_local_sell_repositories.getInstance().dailySummary(shop_id, date);
    }

    public LiveData<local_sell_summary_response> selectedSummary(String shop_id, String date1, String date2) {
        return get_local_sell_repositories.getInstance().selectedSummary(shop_id, date1, date2);
    }

}
