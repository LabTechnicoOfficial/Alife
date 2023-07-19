package com.ALife.alife.model.banner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BannerApi {
    @GET("get_shop_banner.php")
    Call<List<BannerResponse>> getBannerList(@Query("id") String id);
}
