package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.points.Shop_local_sell_point_response;
import com.alifew.alife.model.points.Shop_local_sell_points_repository;

import java.util.List;

public class ShopLocalSellPointsViewModel extends ViewModel {
   public LiveData<List<Shop_local_sell_point_response>> getShopLocalSellPoints(String shopID) {
        return Shop_local_sell_points_repository.getInstance().getLocalSellPointsApi(shopID);
    }
}
