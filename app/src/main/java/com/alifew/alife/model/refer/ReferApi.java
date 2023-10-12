package com.alifew.alife.model.refer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ReferApi {
    @GET("get_shop_refer_list.php")
    Call<List<ReferResponse>> getReferList(@Query("id") String shopID);
}
