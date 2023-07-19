package com.ALife.alife.model.banner;

import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.API.ApiUtilize;
import com.ALife.alife.model.cupon.cupon_repositories;
import com.google.android.gms.common.api.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BannerRepositories {
    private MutableLiveData<List<BannerResponse>> banner = new MutableLiveData<>();
    private BannerApi bannerApi = ApiUtilize.bannerApi();
    private static BannerRepositories bannerRepositories;

    public synchronized static BannerRepositories getInstance() {

        if (bannerRepositories == null) {
            return new BannerRepositories();
        }
        return bannerRepositories;
    }

    public MutableLiveData<List<BannerResponse>> getBannerList(String id) {
        Call<List<BannerResponse>> call = bannerApi.getBannerList(id);

        call.enqueue(new Callback<List<BannerResponse>>() {
            @Override
            public void onResponse(Call<List<BannerResponse>> call, Response<List<BannerResponse>> response) {
                if (response.isSuccessful()) {
                    banner.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<BannerResponse>> call, Throwable t) {

            }
        });
        return banner;
    }

}
