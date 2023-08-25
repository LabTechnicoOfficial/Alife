package com.alifew.alife.model.points;

import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;
import com.alifew.alife.model.add_shop_business_summary_repositories;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Shop_local_sell_points_repository {
   private Shop_local_sell_points_api shopLocalSellPointsApi;
    private static Shop_local_sell_points_repository shopLocalSellPointsRepository;
    MutableLiveData<List<Shop_local_sell_point_response>> localSellPointsList;

    public Shop_local_sell_points_repository() {
        shopLocalSellPointsApi = ApiUtilize.shopLocalSellPointsApi();
        localSellPointsList = new MutableLiveData<>();
    }

    public synchronized static Shop_local_sell_points_repository getInstance() {
        if (shopLocalSellPointsRepository == null) {
            return new Shop_local_sell_points_repository();
        }
        return shopLocalSellPointsRepository;
    }

    public MutableLiveData<List<Shop_local_sell_point_response>> getLocalSellPointsApi(String shopID){
        Call<List<Shop_local_sell_point_response>> call = shopLocalSellPointsApi.getShopLocalSellPoints(shopID);
        call.enqueue(new Callback<List<Shop_local_sell_point_response>>() {
            @Override
            public void onResponse(Call<List<Shop_local_sell_point_response>> call, Response<List<Shop_local_sell_point_response>> response) {
                if(response.isSuccessful()){
                    localSellPointsList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Shop_local_sell_point_response>> call, Throwable t) {

            }
        });
        return localSellPointsList;
    }
}
