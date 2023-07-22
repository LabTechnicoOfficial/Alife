package com.alifew.alife.model.slider;

import com.alifew.alife.model.CommonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SliderApi {
    @GET("get_shop_banner.php")
    Call<List<SliderResponse>> getSliderList(@Query("id") String id);

    @POST("add_shop_banner.php")
    Call<CommonResponse> uploadSlider(@Field("shop_id") String shopID,
                                      @Field("image") String image);

    @POST("update_shop_banner_status.php")
    Call<CommonResponse> updateSliderStatus(@Field("shop_id") String shopID,
                                            @Field("banner_id") String bannerID,
                                            @Field("status") String status);

}
