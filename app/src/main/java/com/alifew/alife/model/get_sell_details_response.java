package com.alifew.alife.model;

import com.google.gson.annotations.SerializedName;

public class get_sell_details_response {
    @SerializedName("total_price")
    private String total_price;
    @SerializedName("total_profit")
    private String total_profit;
    @SerializedName("total_cash_price")
    private String total_cash_price;
    @SerializedName("total_due_price")
    private String total_due_price;

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getTotal_profit() {
        return total_profit;
    }

    public void setTotal_profit(String total_profit) {
        this.total_profit = total_profit;
    }

    public String getTotal_cash_price() {
        return total_cash_price;
    }

    public void setTotal_cash_price(String total_cash_price) {
        this.total_cash_price = total_cash_price;
    }

    public String getTotal_due_price() {
        return total_due_price;
    }

    public void setTotal_due_price(String total_due_price) {
        this.total_due_price = total_due_price;
    }
}
