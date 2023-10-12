package com.alifew.alife.viewmodel.refer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.CommonResponse;
import com.alifew.alife.model.cupon.add_response;
import com.alifew.alife.model.refer.ReferResponse;

import java.util.List;

public class ShopReferViewModel extends ViewModel {

    public LiveData<List<ReferResponse>> getReferList(String shopID) {
        return ShopReferRepositories.getInstance().getReferList(shopID);
    }

    public LiveData<CommonResponse> addRefer(int shopID, String couponName, String createdAt, String endAt, String description) {
        return ShopReferRepositories.getInstance().addRefer(shopID, couponName, createdAt, endAt, description);
    }

    public LiveData<CommonResponse> deleteRefer(String id) {
        return ShopReferRepositories.getInstance().deleteRefer(id);
    }
}
