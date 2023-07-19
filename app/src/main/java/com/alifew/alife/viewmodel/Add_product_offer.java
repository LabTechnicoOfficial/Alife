package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.add_product_offer_repositories;
import com.alifew.alife.model.add_product_offer_response;

public class Add_product_offer extends ViewModel {
    add_product_offer_repositories repositories;
    public LiveData<add_product_offer_response> getmessage(String amount, String price, String id)
    {

        //repositories=new add_product_offer_repositories(amount,price,id);
       // return repositories.getdata();
        return add_product_offer_repositories.getInstance().getdata(amount, price, id);

    }
}
