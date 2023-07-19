package com.ALife.alife.viewmodel.banner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.banner.BannerRepositories;
import com.ALife.alife.model.banner.BannerResponse;

import java.util.List;

public class BannerViewModel extends ViewModel {
    public LiveData<List<BannerResponse>> getBannerList(String id) {
        return BannerRepositories.getInstance().getBannerList(id);
    }
}
