package com.alifew.alife.viewmodel.refer;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;
import com.alifew.alife.model.CommonResponse;
import com.alifew.alife.model.refer.ReferApi;
import com.alifew.alife.model.refer.ReferPackageCustomerResponse;
import com.alifew.alife.model.refer.ReferPackageResponse;
import com.alifew.alife.model.refer.ReferResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopReferRepositories {
    private static ShopReferRepositories referRepositories;
    private ReferApi referApi;
    MutableLiveData<List<ReferResponse>> referList;
    MutableLiveData<List<ReferPackageResponse>> referPackageList;
    MutableLiveData<List<ReferPackageCustomerResponse>> customerList;
    MutableLiveData<CommonResponse> commonResponse;

    public ShopReferRepositories() {
        referApi = ApiUtilize.referApi();
        referList = new MutableLiveData<>();
        commonResponse = new MutableLiveData<>();
        referPackageList = new MutableLiveData<>();
        customerList = new MutableLiveData<>();
    }

    public synchronized static ShopReferRepositories getInstance() {
        if (referRepositories == null) {
            return new ShopReferRepositories();
        }
        return referRepositories;
    }

    MutableLiveData<List<ReferResponse>> getReferList(String shopID) {
        Call<List<ReferResponse>> call = referApi.getReferList(shopID);
        call.enqueue(new Callback<List<ReferResponse>>() {
            @Override
            public void onResponse(Call<List<ReferResponse>> call, Response<List<ReferResponse>> response) {
                if (response.isSuccessful()) {
                    referList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ReferResponse>> call, Throwable t) {
                Log.d("dataxx", "onFailure: " + t.getMessage());
            }
        });

        return referList;
    }


    public MutableLiveData<CommonResponse> addRefer(int shopID, String name, String createdAt, String endAt, String description) {
        Call<CommonResponse> call = referApi.addRefer(String.valueOf(shopID), name, createdAt, endAt, description);
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

    public MutableLiveData<CommonResponse> deleteRefer(String referID) {
        Call<CommonResponse> call = referApi.deleteRefer(String.valueOf(referID));
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

    public MutableLiveData<CommonResponse> addReferPackage(int shopID, String referID, String name, String packageAmount, String winnerAmount, String giftName) {
        Call<CommonResponse> call = referApi.addReferPackage(String.valueOf(shopID), referID, name, packageAmount, winnerAmount, giftName);
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


    public LiveData<List<ReferPackageResponse>> getReferPackageList(String referID) {

        Call<List<ReferPackageResponse>> call = referApi.getReferPackageList(referID);
        call.enqueue(new Callback<List<ReferPackageResponse>>() {
            @Override
            public void onResponse(Call<List<ReferPackageResponse>> call, Response<List<ReferPackageResponse>> response) {
                if (response.isSuccessful()) {
                    referPackageList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ReferPackageResponse>> call, Throwable t) {

            }
        });

        return referPackageList;
    }

    public MutableLiveData<CommonResponse> deleteReferPackage(String referID) {
        Call<CommonResponse> call = referApi.deleteReferPackage(referID);
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

    public MutableLiveData<List<ReferPackageCustomerResponse>> getReferPackageCustomer(String shopID, String packageID) {
        Call<List<ReferPackageCustomerResponse>> call = referApi.getReferPackageCustomer(shopID, packageID);
        call.enqueue(new Callback<List<ReferPackageCustomerResponse>>() {
            @Override
            public void onResponse(Call<List<ReferPackageCustomerResponse>> call, Response<List<ReferPackageCustomerResponse>> response) {
                if (response.isSuccessful()) {
                    customerList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ReferPackageCustomerResponse>> call, Throwable t) {

            }
        });

        return customerList;
    }
}
