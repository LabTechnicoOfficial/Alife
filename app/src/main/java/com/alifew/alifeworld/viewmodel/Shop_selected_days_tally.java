package com.alifew.alifeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.get_sell_details_response;
import com.alifew.alifeworld.model.shop_selected_days_tally_khata_repositories;
import com.alifew.alifeworld.model.shop_tally_khata_response;

import java.util.List;

public class Shop_selected_days_tally extends ViewModel {
    private shop_selected_days_tally_khata_repositories repositories;

    public LiveData<List<shop_tally_khata_response>> getTally(String shop_id, String date1, String date2, int page, int limit) {
        //repositories=new shop_selected_days_tally_khata_repositories(shop_id, date1, date2,page,limit);
        //return repositories.getTally_data();
        return shop_selected_days_tally_khata_repositories.getInstance().getTally_data(shop_id, date1, date2, page, limit);
    }

    public LiveData<get_sell_details_response> getDetails(String shop_id, String date1, String date2) {
        //repositories=new shop_selected_days_tally_khata_repositories(shop_id, date1, date2);
        //return repositories.getDetails_data();
        return shop_selected_days_tally_khata_repositories.getInstance().getDetails_data(shop_id, date1, date2);
    }
}
