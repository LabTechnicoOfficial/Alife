package com.alifew.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface shop_due_customer_by_search {
    @GET("fetch_shop_due_customer_search_value.php")
    Call<List<shop_due_customer_response>> getcustomer(@Query("id") String id,@Query("search") String search);
}
