package com.alifew.alifeworld.model.cupon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;
import com.alifew.alifeworld.model.CommonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cupon_repositories {
    private MutableLiveData<add_response> add_response;
    private MutableLiveData<List<cupon_response>> data;
    private MutableLiveData<notify_response> data2;

    private MutableLiveData<List<CustomerFor_cupon_response>> customerList;
    private MutableLiveData<CommonResponse> commonResponse;
    private MutableLiveData<ShopCouponCustomerResponse> couponCustomerResponse;
    private cupon_api api;
    private static cupon_repositories cupon_repositories;

    private cupon_repositories() {
        data = new MutableLiveData<>();
        data2 = new MutableLiveData<>();
        add_response = new MutableLiveData<>();
        api = ApiUtilize.cupon_response();
        customerList = new MutableLiveData<>();
        commonResponse = new MutableLiveData<>();
        couponCustomerResponse = new MutableLiveData<>();
    }

    public synchronized static cupon_repositories getInstance() {
        if (cupon_repositories == null) {
            return new cupon_repositories();
        }
        return cupon_repositories;
    }

    public MutableLiveData<add_response> addCupon(String shop_id, String cupon_name, String time_range, String create_date, String end_date, String description) {

        Call<add_response> call = api.add_cupon(shop_id, cupon_name, time_range, create_date, end_date, description);
        call.enqueue(new Callback<com.alifew.alifeworld.model.cupon.add_response>() {
            @Override
            public void onResponse(Call<com.alifew.alifeworld.model.cupon.add_response> call, Response<com.alifew.alifeworld.model.cupon.add_response> response) {
                if (response.isSuccessful())
                    add_response.postValue(response.body());
            }

            @Override
            public void onFailure(Call<com.alifew.alifeworld.model.cupon.add_response> call, Throwable throwable) {
                add_response response = new add_response();
                response.setMessage(throwable.getMessage());
                add_response.postValue(response);
            }
        });
        return add_response;
    }

    public MutableLiveData<List<cupon_response>> getData(String shop_id) {
        Call<List<cupon_response>> call = api.fetch_cupon(shop_id);
        call.enqueue(new Callback<List<cupon_response>>() {
            @Override
            public void onResponse(Call<List<cupon_response>> call, Response<List<cupon_response>> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<cupon_response>> call, Throwable throwable) {

            }
        });
        return data;
    }

    public MutableLiveData<notify_response> getNotify(String shop_id, String message) {
        Call<notify_response> call = api.notifyCoupon(shop_id, message);
        call.enqueue(new Callback<notify_response>() {
            @Override
            public void onResponse(Call<notify_response> call, Response<notify_response> response) {
                if (response.isSuccessful())
                    data2.postValue(response.body());
            }

            @Override
            public void onFailure(Call<notify_response> call, Throwable throwable) {

            }
        });
        return data2;
    }

    //getCustomerListForCoupon
    public MutableLiveData<List<CustomerFor_cupon_response>> getCustomerListForCoupon(String shopID, String packageID) {
        Call<List<CustomerFor_cupon_response>> call = api.getCustomerListForCoupon(shopID, packageID);
        call.enqueue(new Callback<List<CustomerFor_cupon_response>>() {
            @Override
            public void onResponse(Call<List<CustomerFor_cupon_response>> call, Response<List<CustomerFor_cupon_response>> response) {

                if (response.isSuccessful()) {
                    customerList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<CustomerFor_cupon_response>> call, Throwable t) {

            }
        });

        return customerList;
    }

    public MutableLiveData<CommonResponse> addCustomerReferGift(String packageID, String customerPhone, String sellAmount, String points, int shopID, String pos, String giftName) {

        Call<CommonResponse> call = api.addCustomerReferGift(packageID, customerPhone, sellAmount, points, shopID, pos, giftName);
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

    public MutableLiveData<ShopCouponCustomerResponse> getCouponPackageCustomerResultList(String packageID) {
        Call<ShopCouponCustomerResponse> call = api.getCouponPackageCustomerResultList(packageID);
        call.enqueue(new Callback<ShopCouponCustomerResponse>() {
            @Override
            public void onResponse(Call<ShopCouponCustomerResponse> call, Response<ShopCouponCustomerResponse> response) {
                if (response.isSuccessful()) {
                    couponCustomerResponse.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ShopCouponCustomerResponse> call, Throwable t) {

            }
        });

        return couponCustomerResponse;
    }

    public MutableLiveData<CommonResponse> deleteCouponPackageCustomerResultItem(String id) {
        Call<CommonResponse> call = api.deleteCouponPackageCustomerResultItem(id);
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
