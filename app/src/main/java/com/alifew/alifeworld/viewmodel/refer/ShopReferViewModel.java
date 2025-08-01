package com.alifew.alifeworld.viewmodel.refer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.CommonResponse;
import com.alifew.alifeworld.model.cupon.ShopResponse;
import com.alifew.alifeworld.model.refer.CustomerReferPackageCustomer;
import com.alifew.alifeworld.model.refer.CustomerShopReferPackageResponse;
import com.alifew.alifeworld.model.refer.CustomerShopReferResponse;
import com.alifew.alifeworld.model.refer.ReferPackageCustomerResponse;
import com.alifew.alifeworld.model.refer.ReferPackageResponse;
import com.alifew.alifeworld.model.refer.ReferResponse;
import com.alifew.alifeworld.model.refer.ReferResultCustomerResponse;

import java.util.List;

public class ShopReferViewModel extends ViewModel {

    public LiveData<List<ReferResponse>> getReferList(String shopID) {
        return ShopReferRepositories.getInstance().getReferList(shopID);
    }

    public LiveData<CommonResponse> addRefer(int shopID, String name, String createdAt, String endAt, String description) {
        return ShopReferRepositories.getInstance().addRefer(shopID, name, createdAt, endAt, description);
    }

    public LiveData<CommonResponse> deleteRefer(String id) {
        return ShopReferRepositories.getInstance().deleteRefer(id);
    }


    public LiveData<CommonResponse> addReferPackage(int shopID, String referID, String name, String minReferPackagePoint, String winnerAmount, String giftName) {
        return ShopReferRepositories.getInstance().addReferPackage(shopID, referID, name, minReferPackagePoint, winnerAmount, giftName);
    }

    public LiveData<List<ReferPackageResponse>> getReferPackageList(String referID) {
        return ShopReferRepositories.getInstance().getReferPackageList(referID);
    }

    public LiveData<CommonResponse> deleteReferPackage(String referID) {
        return ShopReferRepositories.getInstance().deleteReferPackage(referID);
    }

    public LiveData<List<ReferPackageCustomerResponse>> getReferPackageCustomer(String shopID, String packageID) {
        return ShopReferRepositories.getInstance().getReferPackageCustomer(shopID, packageID);
    }

    public LiveData<CommonResponse> addCustomerReferGift(String referPackageID, int shopID, String phone, String points, String position, String giftName) {
        return ShopReferRepositories.getInstance().addCustomerReferGift(referPackageID, String.valueOf(shopID), phone, points, position, giftName);
    }

    public LiveData<ReferResultCustomerResponse> getResultCustomerList(String referPackageID) {
        return ShopReferRepositories.getInstance().getResultCustomerList(referPackageID);
    }

    public LiveData<CommonResponse> deleteReferCustomerResult(String id) {
        return ShopReferRepositories.getInstance().deleteReferCustomerResult(id);
    }

    public LiveData<CommonResponse> updateReferCustomerResultStatus(String id, String status) {
        return ShopReferRepositories.getInstance().updateReferCustomerResultStatus(id, status);
    }

    public LiveData<List<ShopResponse>> getReferShopList(int page, int limit) {
        return ShopReferRepositories.getInstance().getShopList(page, limit);
    }

    public LiveData<List<CustomerShopReferResponse>> getCustomerShopReferList(String shopID) {
        return ShopReferRepositories.getInstance().getCustomerShopReferList(shopID);
    }


    public LiveData<List<CustomerShopReferPackageResponse>> getCustomerShopReferPackageList(String shopID, String referID){
        return ShopReferRepositories.getInstance().getCustomerShopReferPackageList(shopID, referID);
    }

    public LiveData<List<CustomerReferPackageCustomer>> getCustomerShopReferCustomerList(String shopID, String referID, String packageID){
        return ShopReferRepositories.getInstance().getCustomerShopReferCustomerList(shopID, referID, packageID);
    }
}
