package com.alifew.bcopay.model.points;

import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;
import com.alifew.bcopay.model.CommonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Shop_local_sell_points_repository {
    private Shop_local_sell_points_api shopLocalSellPointsApi;
    private static Shop_local_sell_points_repository shopLocalSellPointsRepository;
    MutableLiveData<List<Shop_local_sell_point_response>> localSellPointsList;
    MutableLiveData<CommonResponse> commonResponse;

    public Shop_local_sell_points_repository() {
        shopLocalSellPointsApi = ApiUtilize.shopLocalSellPointsApi();
        localSellPointsList = new MutableLiveData<>();
        commonResponse = new MutableLiveData<>();
    }

    public synchronized static Shop_local_sell_points_repository getInstance() {
        if (shopLocalSellPointsRepository == null) {
            return new Shop_local_sell_points_repository();
        }
        return shopLocalSellPointsRepository;
    }

    public MutableLiveData<List<Shop_local_sell_point_response>> getLocalSellPointsApi(String shopID) {
        Call<List<Shop_local_sell_point_response>> call = shopLocalSellPointsApi.getShopLocalSellPoints(shopID);
        call.enqueue(new Callback<List<Shop_local_sell_point_response>>() {
            @Override
            public void onResponse(Call<List<Shop_local_sell_point_response>> call, Response<List<Shop_local_sell_point_response>> response) {
                if (response.isSuccessful()) {
                    localSellPointsList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Shop_local_sell_point_response>> call, Throwable t) {

            }
        });
        return localSellPointsList;
    }

    public MutableLiveData<CommonResponse> addLocalSellPoint(String shopID, String amount, String points) {

        Call<CommonResponse> call = shopLocalSellPointsApi.addLocalSellPoint(shopID, amount, points);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful()) {
                    commonResponse.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

            }
        });

        return commonResponse;

    }

    public MutableLiveData<CommonResponse> deleteLocalSellPoint(String id) {

        Call<CommonResponse> call = shopLocalSellPointsApi.deleteLocalSellPoint(id);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful()) {
                    commonResponse.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

            }
        });

        return commonResponse;

    }

    public MutableLiveData<CommonResponse> addReferralPointForLocalSell(String shopID, String amount, String points) {

        Call<CommonResponse> call = shopLocalSellPointsApi.addReferralPointForLocalSell(shopID, amount, points);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.isSuccessful()){
                    commonResponse.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

            }
        });

        return commonResponse;
    }

    public MutableLiveData<List<Shop_local_sell_point_response>> getShopLocalSellReferPoints(String shopID) {
        Call<List<Shop_local_sell_point_response>> call = shopLocalSellPointsApi.getShopLocalSellReferPoints(shopID);
        call.enqueue(new Callback<List<Shop_local_sell_point_response>>() {
            @Override
            public void onResponse(Call<List<Shop_local_sell_point_response>> call, Response<List<Shop_local_sell_point_response>> response) {
                if (response.isSuccessful()) {
                    localSellPointsList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Shop_local_sell_point_response>> call, Throwable t) {

            }
        });
        return localSellPointsList;
    }

    public MutableLiveData<CommonResponse> deleteLocalSellReferPoint(String id) {
        Call<CommonResponse> call = shopLocalSellPointsApi.deleteLocalSellReferPoint(id);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful()) {
                    commonResponse.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

            }
        });

        return commonResponse;
    }
}
