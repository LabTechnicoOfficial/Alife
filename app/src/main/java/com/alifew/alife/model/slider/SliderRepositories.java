package com.alifew.alife.model.slider;

import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SliderRepositories {
    private MutableLiveData<List<SliderResponse>> banner = new MutableLiveData<>();
    private SliderApi sliderApi = ApiUtilize.bannerApi();
    private static SliderRepositories sliderRepositories;

    public synchronized static SliderRepositories getInstance() {

        if (sliderRepositories == null) {
            return new SliderRepositories();
        }
        return sliderRepositories;
    }

    public MutableLiveData<List<SliderResponse>> getBannerList(String id) {
        Call<List<SliderResponse>> call = sliderApi.getSliderList(id);

        call.enqueue(new Callback<List<SliderResponse>>() {
            @Override
            public void onResponse(Call<List<SliderResponse>> call, Response<List<SliderResponse>> response) {
                if (response.isSuccessful()) {
                    banner.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<SliderResponse>> call, Throwable t) {

            }
        });
        return banner;
    }

}
