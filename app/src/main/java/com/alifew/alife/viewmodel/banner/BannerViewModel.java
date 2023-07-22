package com.alifew.alife.viewmodel.banner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.slider.SliderRepositories;
import com.alifew.alife.model.slider.SliderResponse;

import java.util.List;

public class BannerViewModel extends ViewModel {
    public LiveData<List<SliderResponse>> getBannerList(String id) {
        return SliderRepositories.getInstance().getBannerList(id);
    }
}
