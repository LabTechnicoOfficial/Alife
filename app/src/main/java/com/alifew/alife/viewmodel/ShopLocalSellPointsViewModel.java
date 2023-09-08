package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.CommonResponse;
import com.alifew.alife.model.points.Shop_local_sell_point_response;
import com.alifew.alife.model.points.Shop_local_sell_points_repository;

import java.util.List;

public class ShopLocalSellPointsViewModel extends ViewModel {
    public LiveData<List<Shop_local_sell_point_response>> getShopLocalSellPoints(String shopID) {
        return Shop_local_sell_points_repository.getInstance().getLocalSellPointsApi(shopID);
    }

    public LiveData<CommonResponse> addLocalSellPoint(String shopID, String amount, String points) {
        return Shop_local_sell_points_repository.getInstance().addLocalSellPoint(shopID, amount, points);
    }

    public LiveData<CommonResponse> deleteLocalSellPoint(String id) {
        return Shop_local_sell_points_repository.getInstance().deleteLocalSellPoint(id);
    }

    public LiveData<CommonResponse> addReferralPointForLocalSell(String shopID, String amount, String points){
        return Shop_local_sell_points_repository.getInstance().addReferralPointForLocalSell(shopID, amount, points);
    }

    public LiveData<List<Shop_local_sell_point_response>> getShopLocalSellReferPoints(String shopID) {
        return Shop_local_sell_points_repository.getInstance().getShopLocalSellReferPoints(shopID);
    }

    public LiveData<CommonResponse> deleteLocalSellReferPoint(String id) {
        return Shop_local_sell_points_repository.getInstance().deleteLocalSellReferPoint(id);
    }
}
