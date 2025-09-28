package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.normal_sell_details_repositories;
import com.alifew.bcopay.model.normal_sell_details_response;
import com.alifew.bcopay.model.systemetic_sell_details_repositories;
import com.alifew.bcopay.model.systemetic_sell_details_response;

import java.util.List;

public class Sell_details extends ViewModel {
    systemetic_sell_details_repositories repositories1;
    normal_sell_details_repositories repositories2;

    public LiveData<List<systemetic_sell_details_response>> systemetic_sell_details(String sell_id) {
        //repositories1=new systemetic_sell_details_repositories(sell_id);
        //return repositories1.getData();
        return systemetic_sell_details_repositories.getInstance().getData(sell_id);
    }

    public LiveData<normal_sell_details_response> normal_sell_details(String sell_id) {
        // repositories2=new normal_sell_details_repositories(sell_id);
        //return repositories2.getData();
        return normal_sell_details_repositories.getInstance().getData(sell_id);
    }
}
