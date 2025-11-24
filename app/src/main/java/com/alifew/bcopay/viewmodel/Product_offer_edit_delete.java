package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.product_offer_edit_delete_repositories;
import com.alifew.bcopay.model.product_offer_edit_delete_response;

public class Product_offer_edit_delete extends ViewModel {
    private product_offer_edit_delete_repositories repositories;

    public LiveData<product_offer_edit_delete_response> getEditResponse(String offer_id, String amount, String percentage) {
        //repositories=new product_offer_edit_delete_repositories(offer_id, amount, percentage);
        //return repositories.getEditResponse();
        return product_offer_edit_delete_repositories.getInstance().getEditResponse(offer_id, amount, percentage);
    }

    public LiveData<product_offer_edit_delete_response> getDeleteResponse(String offer_id) {
        // repositories=new product_offer_edit_delete_repositories(offer_id);
        // return repositories.getDeleteResponse();
        return product_offer_edit_delete_repositories.getInstance().getDeleteResponse(offer_id);
    }
}
