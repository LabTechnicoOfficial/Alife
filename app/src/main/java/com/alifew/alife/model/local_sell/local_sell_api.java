package com.alifew.alife.model.local_sell;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface local_sell_api {
    @FormUrlEncoded
    @POST("locall_sell/add_local_sell_details.php")
    Call<add_local_sell_details_response> add_local_sell_details(@Field("sell_id") String sell_id, @Field("description") String description);


    @FormUrlEncoded
    @POST("locall_sell/add_local_sell_image.php")
    Call<add_local_sell_image_response> add_local_sell_image(@Field("id") String id, @Field("image") String image,@Field("check") int check);

    @FormUrlEncoded
    @POST("locall_sell/add_local_sell_product.php")
    Call<add_local_sell_product_response> add_local_sell_product(@Field("shop_id") String shop_id, @Field("product_details") String product_details, @Field("price") String price,@Field("buyprice") String buyprice, @Field("image") String image);

    @FormUrlEncoded
    @POST("locall_sell/edit_local_sell_product.php")
    Call<delete_local_sell_product_response> edit_local_sell_product(@Field("product_id") String product_id,@Field("product_details") String product_details,@Field("price") String price,@Field("buy_price") String buy_price,@Field("image") String image);

    @FormUrlEncoded
    @POST("locall_sell/delete_local_sell_product.php")
    Call<delete_local_sell_product_response> delete_local_sell_product(@Field("id") String product_id );

    @GET("locall_sell/get_local_sell_product.php")
    Call<List<get_local_sell_product_response>> getlocal_sell_product(@Query("id") String shop_id);

    @GET("locall_sell/get_local_sell_product_bySearch.php")
    Call<List<get_local_sell_product_response>> getlocal_sell_product_bySearch(@Query("id") String shop_id,@Query("search") String search);

    @GET("locall_sell/get_local_sell_details.php")
    Call<get_local_sell_details_response> get_local_sell_details(@Query("sell_id") String sell_id);

    @GET("locall_sell/get_product_by_bar_code.php")
    Call<get_product_by_bar_code_response> get_product(@Query("bar_code") String bar_code,@Query("shop_id") String shop_id);

    @GET("locall_sell/get_customer_phone.php")
    Call<List<customer_phone_response>> get_customer(@Query("shop_id") String shop_id,@Query("page") int page,@Query("limit") int limit);


    //local sell history

    @GET("locall_sell/shop_all_local_sell_history.php")
    Call<List<local_sell_history_response>> getAllList(@Query("shop_id")String shop_id, @Query("page") int page, @Query("limit") int limit);

    @GET("locall_sell/shop_daily_local_sell_history.php")
    Call<List<local_sell_history_response>> getDailyList(@Query("shop_id")String shop_id,@Query("date") String date,@Query("page") int page,@Query("limit") int limit);

    @GET("locall_sell/shop_selected_days_local_sell_history.php")
    Call<List<local_sell_history_response>> getSelectedList(@Query("shop_id")String shop_id,@Query("date1") String date1,@Query("date2") String date2,@Query("page") int page,@Query("limit") int limit);

    @GET("locall_sell/shop_all_local_sell_summary.php")
    Call<local_sell_summary_response> getAllSummary(@Query("shop_id")String shop_id);

    @GET("locall_sell/shop_daily_local_sell_summary.php")
    Call<local_sell_summary_response> getDailySummary(@Query("shop_id")String shop_id,@Query("date") String date);

    @GET("locall_sell/shop_selected_days_local_sell_summary.php")
    Call<local_sell_summary_response> getSelectedSummary(@Query("shop_id")String shop_id,@Query("date1") String date1,@Query("date2") String date2);

}
