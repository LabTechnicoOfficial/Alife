package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.add_normal_product_image_repositories;
import com.alifew.bcopay.model.add_normal_product_image_response;
import com.alifew.bcopay.model.add_normal_sell_repositories;
import com.alifew.bcopay.model.add_normal_sell_response;

public class Normal_sell extends ViewModel {
    add_normal_sell_repositories repositories1;
    add_normal_product_image_repositories repositories2;

    public LiveData<add_normal_sell_response> getData(String sell_id, String description) {
        //repositories1=new add_normal_sell_repositories(sell_id,description);
        // return repositories1.getData();
        return add_normal_sell_repositories.getInstance().getData(sell_id, description);
    }

    public LiveData<add_normal_product_image_response> add_image(String id, String image) {
        //repositories2=new add_normal_product_image_repositories(id, image);
        //return repositories2.getData();
        return add_normal_product_image_repositories.getInstance().getData(id, image);
    }
}
