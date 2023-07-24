package com.alifew.alife.viewmodel.cuponViewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.cupon.cuponShop_response;
import com.alifew.alife.model.cupon.cuponShop_repositories;

import java.util.List;

public class CuponShopList extends ViewModel {
    public LiveData<List<cuponShop_response>> getData(int page,int limit) {
        return cuponShop_repositories.getInstance().getData(page,limit);
    }
}
