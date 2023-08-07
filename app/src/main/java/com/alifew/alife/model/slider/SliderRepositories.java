package com.alifew.alife.model.slider;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;
import com.alifew.alife.model.CommonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SliderRepositories {
    private MutableLiveData<List<SliderResponse>> slidersList = new MutableLiveData<>();
    private MutableLiveData<List<Customer_slider_response>> sliderListByLatLong = new MutableLiveData<>();
    private SliderApi sliderApi = ApiUtilize.bannerApi();
    private static SliderRepositories sliderRepositories;

    private MutableLiveData<CommonResponse> commonResponse = new MutableLiveData<>();

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
                    slidersList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<SliderResponse>> call, Throwable t) {

            }
        });
        return slidersList;
    }

    public MutableLiveData<CommonResponse> updateBannerStatus(String shopID, String bannerID, String status) {

        Call<CommonResponse> call = sliderApi.updateSliderStatus(shopID, bannerID, status);
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

    public MutableLiveData<CommonResponse> uploadSlider(String shopID, String image) {
        Call<CommonResponse> call = sliderApi.uploadSlider(shopID, image);
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

    public MutableLiveData<List<Customer_slider_response>> getSliderListByLatLong(String latitude, String longitude) {
        Call<List<Customer_slider_response>> call = sliderApi.getSliderByLatLong(latitude, longitude);
        call.enqueue(new Callback<List<Customer_slider_response>>() {
            @Override
            public void onResponse(Call<List<Customer_slider_response>> call, Response<List<Customer_slider_response>> response) {
                if (response.isSuccessful()) {
                    sliderListByLatLong.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Customer_slider_response>> call, Throwable t) {

            }
        });

        return sliderListByLatLong;
    }
}
