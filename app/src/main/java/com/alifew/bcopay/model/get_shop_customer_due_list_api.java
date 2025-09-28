package com.alifew.bcopay.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_shop_customer_due_list_api {

    @GET("get_shop_customer_transaction1.php")
   // @GET("test.php")
    Call<List<get_shop_customer_due_list_response>> shop_customer_due_list(@Query("shop_id") String shop_id, @Query("customer_id") String customer_id,@Query("customer_phone") String customer_phone,@Query("page") int page,@Query("limit") int limit);

}
