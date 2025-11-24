package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.get_sell_details_response;
import com.alifew.bcopay.model.shop_all_tally_khata_repositories;
import com.alifew.bcopay.model.shop_tally_khata_response;

import java.util.List;

public class Shop_all_tally extends ViewModel {
    private shop_all_tally_khata_repositories repositories;

    public LiveData<List<shop_tally_khata_response>> getTally(String shop_id, int page, int limit) {
        //repositories=new shop_all_tally_khata_repositories(shop_id,page,limit);
        // return repositories.getTally_data();
        return shop_all_tally_khata_repositories.getInstance().getTally_data(shop_id, page, limit);
    }

    public LiveData<get_sell_details_response> getDetails(String shop_id) {
        //repositories=new shop_all_tally_khata_repositories(shop_id);
        // return repositories.getDetails_data();
        return shop_all_tally_khata_repositories.getInstance().getDetails_data(shop_id);
    }
}
