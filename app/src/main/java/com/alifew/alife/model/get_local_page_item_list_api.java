package com.alifew.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_local_page_item_list_api {
    @GET("get_local_page_title_details.php")
    Call<List<shop_local_page_item_list_response>> getitem(@Query("id") String id,@Query("page") int page,@Query("limit") int limit);

}
