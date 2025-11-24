package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.get_all_product_offer_response;
import com.alifew.bcopay.model.get_product_offer_repositories;
import com.alifew.bcopay.model.get_product_offer_response;

import java.util.List;

public class Get_product_offer extends ViewModel {
    get_product_offer_repositories repositories;


    public LiveData<List<get_product_offer_response>> getdata(String id)
    {

       // repositories=new get_product_offer_repositories(id);
       //return repositories.getdata();
        return get_product_offer_repositories.getInstance().getdata(id);
    }
    public LiveData<List<get_all_product_offer_response>> get_all_data(String shop_id)
    {
        //repositories=new get_product_offer_repositories(shop_id);
        //lreturn repositories.get_all_data();
        return get_product_offer_repositories.getInstance().get_all_data(shop_id);
    }
}
