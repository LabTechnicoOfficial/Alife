package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.add_shop_all_product_offer_response;
import com.alifew.bcopay.model.delete_shop_all_product_offer_response;
import com.alifew.bcopay.model.get_shop_all_product_offer_response;
import com.alifew.bcopay.model.shop_all_product_offer_repositories;

import java.util.List;

public class Shop_all_product_offer extends ViewModel {
    private shop_all_product_offer_repositories repositories;

    public LiveData<add_shop_all_product_offer_response> add_offer(String minimum_amount, String minimum_price, String offer_percentage, String shop_id) {
        //repositories = new shop_all_product_offer_repositories(minimum_amount, minimum_price, offer_percentage, shop_id);
        //return repositories.add_offer();
        return shop_all_product_offer_repositories.getInstance().add_offer(minimum_amount, minimum_price, offer_percentage, shop_id);
    }

    public LiveData<List<get_shop_all_product_offer_response>> get_offer(String shop_id) {
        //repositories = new shop_all_product_offer_repositories(shop_id);
        //return repositories.getData_offer();
        return shop_all_product_offer_repositories.getInstance().getData_offer(shop_id);
    }

    public LiveData<delete_shop_all_product_offer_response> delete_offer(String offer_id) {
        //repositories = new shop_all_product_offer_repositories(offer_id);
        //return repositories.delete_offer();
        return shop_all_product_offer_repositories.getInstance().delete_offer(offer_id);
    }
}
