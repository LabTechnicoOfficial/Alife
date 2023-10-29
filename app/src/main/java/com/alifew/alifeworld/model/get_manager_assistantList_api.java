package com.alifew.alifeworld.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_manager_assistantList_api {
    @GET("fetch_manager_assistantList.php")
    Call<List<fetch_shop_admin_response>> fetch_manager_assistant(@Query("id") String manager_id);
}
