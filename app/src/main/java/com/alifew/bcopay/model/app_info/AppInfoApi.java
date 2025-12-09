package com.alifew.bcopay.model.app_info;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AppInfoApi {
    @GET("app_info.php")
    Call<AppInfoResponse> getAppInfo();
}
