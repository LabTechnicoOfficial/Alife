package com.alifew.alifeworld.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface user_instraction_api {
    @GET("get_user_instraction.php")
    Call<List<user_instruction_response>> getInstraction(@Query("token") String token);

}
