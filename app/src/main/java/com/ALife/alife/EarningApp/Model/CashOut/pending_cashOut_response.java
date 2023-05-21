package com.ALife.alife.EarningApp.Model.CashOut;

import com.google.gson.annotations.SerializedName;

public class pending_cashOut_response {
    @SerializedName("amount")
    private String amount;
    @SerializedName("date")
    private String date;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
