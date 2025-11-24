package com.alifew.bcopay.viewmodel.cuponViewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.cupon.ShopResponse;
import com.alifew.bcopay.model.cupon.cuponShop_repositories;

import java.util.List;

public class CuponShopList extends ViewModel {
    public LiveData<List<ShopResponse>> getData(int page, int limit) {
        return cuponShop_repositories.getInstance().getData(page,limit);
    }
}
