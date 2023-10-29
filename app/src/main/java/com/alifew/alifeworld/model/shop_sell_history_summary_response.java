package com.alifew.alifeworld.model;

import com.google.gson.annotations.SerializedName;

public class shop_sell_history_summary_response {
    @SerializedName("total_sellPrice")
    private String total_sellPrice;
    @SerializedName("total_buyPrice")
    private String total_buyPrice;
    @SerializedName("total_profit")
    private String total_profit;

    public String getTotal_sellPrice() {
        return total_sellPrice;
    }

    public void setTotal_sellPrice(String total_sellPrice) {
        this.total_sellPrice = total_sellPrice;
    }

    public String getTotal_buyPrice() {
        return total_buyPrice;
    }

    public void setTotal_buyPrice(String total_buyPrice) {
        this.total_buyPrice = total_buyPrice;
    }

    public String getTotal_profit() {
        return total_profit;
    }

    public void setTotal_profit(String total_profit) {
        this.total_profit = total_profit;
    }
}
