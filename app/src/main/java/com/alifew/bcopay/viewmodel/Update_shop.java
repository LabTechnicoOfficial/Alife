package com.alifew.bcopay.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.update_shop_repositories;
import com.alifew.bcopay.model.update_shop_response;

public class Update_shop extends ViewModel {
    update_shop_repositories repositories;

    public LiveData<update_shop_response> getData(String shop_id, String shop_name, String shop_owner, String shop_location, String shop_image, int token) {


        // repositories=new update_shop_repositories(shop_id,shop_name,shop_owner,shop_location,shop_image);
        if (token == 0) {
            //return repositories.getData2();
            return update_shop_repositories.getInstance().getData2(shop_id, shop_name, shop_owner, shop_location);
        } else {
            //return repositories.getData1();
            return update_shop_repositories.getInstance().getData1(shop_id, shop_name, shop_owner, shop_location, shop_image);
        }


    }
}
