package com.alifew.alife.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.addMessageResponse;
import com.alifew.alife.model.addMessageRepositories;

public class AddMessagetoHistory extends ViewModel {
    public MutableLiveData<addMessageResponse> getResponse(String shop_id, String shop_name, String shop_phone, String customer_id, String customer_name, String customer_phone, String message) {
        return addMessageRepositories.getInstance().getResponse(shop_id, shop_name, shop_phone, customer_id, customer_name, customer_phone, message);
    }
}
