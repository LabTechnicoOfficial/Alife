package com.alifew.alife.viewmodel.refer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.refer.ReferResponse;

import java.util.List;

public class ShopReferViewModel extends ViewModel {

    public LiveData<List<ReferResponse>> getReferList(String shopID) {
        return ShopReferRepositories.getInstance().getReferList(shopID);
    }

}
