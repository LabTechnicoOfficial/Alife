package com.alifew.alifeworld.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface shop_sell_history_api {
    @GET("shop_total_product_sell_historyList.php")
    Call<List<shop_sell_history_list_response>> getAllList(@Query("shop_id")String shop_id,@Query("page") int page,@Query("limit") int limit);

    @GET("shop_daily_product_sell_historyList.php")
    Call<List<shop_sell_history_list_response>> getDailyList(@Query("shop_id")String shop_id,@Query("date") String date,@Query("page") int page,@Query("limit") int limit);

    @GET("shop_selected_days_product_sell_historyList.php")
    Call<List<shop_sell_history_list_response>> getSelectedList(@Query("shop_id")String shop_id,@Query("date1") String date1,@Query("date2") String date2,@Query("page") int page,@Query("limit") int limit);

    @GET("shop_total_product_sell_historySummary.php")
    Call<shop_sell_history_summary_response> getAllSummary(@Query("shop_id")String shop_id);

    @GET("shop_daily_product_sell_historySummary.php")
    Call<shop_sell_history_summary_response> getDailySummary(@Query("shop_id")String shop_id,@Query("date") String date);

    @GET("shop_selected_days_product_sell_historySummary.php")
    Call<shop_sell_history_summary_response> getSelectedSummary(@Query("shop_id")String shop_id,@Query("date1") String date1,@Query("date2") String date2);
}
