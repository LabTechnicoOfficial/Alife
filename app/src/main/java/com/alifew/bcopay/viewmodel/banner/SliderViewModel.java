package com.alifew.bcopay.viewmodel.banner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.CommonResponse;
import com.alifew.bcopay.model.slider.Customer_slider_response;
import com.alifew.bcopay.model.slider.SliderRepositories;
import com.alifew.bcopay.model.slider.SliderResponse;

import java.util.List;

public class SliderViewModel extends ViewModel {
    public LiveData<List<SliderResponse>> getBannerList(String id) {
        return SliderRepositories.getInstance().getBannerList(id);
    }

    public LiveData<CommonResponse> updateBannerStatus(String shopID, String bannerID, String status) {
        return SliderRepositories.getInstance().updateBannerStatus(shopID, bannerID, status);
    }

    public LiveData<CommonResponse> uploadSlider(String shopID, String image) {
        return SliderRepositories.getInstance().uploadSlider(shopID, image);
    }

    public LiveData<List<Customer_slider_response>> getSliderListByLatLong(String latitude, String longitude) {
        return SliderRepositories.getInstance().getSliderListByLatLong(latitude, longitude);
    }
}
