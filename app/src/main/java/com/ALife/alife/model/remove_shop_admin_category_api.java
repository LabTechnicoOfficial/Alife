package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface remove_shop_admin_category_api {
    @FormUrlEncoded
    @POST("remove_shop_agent_category.php")
    Call<remove_shop_admin_category_response> remove_category(@Field("agent_id") String shop, @Field("category_id") String customer);

}
