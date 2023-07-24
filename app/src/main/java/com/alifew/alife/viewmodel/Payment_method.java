package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.payment_method_response;
import com.alifew.alife.model.payment_method_repositories;
import java.util.List;

public class Payment_method extends ViewModel {
    public LiveData<List<payment_method_response>> getData(String token)
    {
        //Log.d("kjkjkjk:","yess");
        return payment_method_repositories.getInstance().getData(token);
    }
}
