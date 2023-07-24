package com.alifew.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface customer_due_shop_list_api {
    @GET("customer_due_shop_list.php")
    Call<List<customer_due_shop_list_response>> get_due_shop(@Query("id") String customer_id);
}
