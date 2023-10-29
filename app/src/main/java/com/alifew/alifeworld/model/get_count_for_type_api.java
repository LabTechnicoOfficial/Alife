package com.alifew.alifeworld.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_count_for_type_api {
    @GET("get_count_for_type.php")
    Call<get_count_for_type_response> getcount(@Query("id") String id);

}
