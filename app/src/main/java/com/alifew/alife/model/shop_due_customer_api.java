package com.alifew.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface shop_due_customer_api {
   // @GET("new_shop_due_customer.php")
   @GET("shop_due_customer_test.php")
    Call<List<shop_due_customer_response>> getcustomer(@Query("id") String id);
}
