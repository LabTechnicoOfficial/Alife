package com.alifew.alife.viewmodel.banner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.CommonResponse;
import com.alifew.alife.model.slider.SliderRepositories;
import com.alifew.alife.model.slider.SliderResponse;

import java.util.List;

public class SliderViewModel extends ViewModel {
    public LiveData<List<SliderResponse>> getBannerList(String id) {
        return SliderRepositories.getInstance().getBannerList(id);
    }

    public LiveData<CommonResponse> updateBannerStatus(String shopID, String bannerID,String status){
        return SliderRepositories.getInstance().updateBannerStatus(shopID, bannerID, status);
    }
}
