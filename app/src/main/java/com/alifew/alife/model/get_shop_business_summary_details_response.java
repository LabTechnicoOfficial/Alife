package com.alifew.alife.model;

import com.google.gson.annotations.SerializedName;

public class get_shop_business_summary_details_response {
    @SerializedName("credit_in")
    private Double credit_in;
    @SerializedName("credit_out")
    private Double credit_out;
    @SerializedName("total_invest")
    private Double total_invest;

    public Double getCredit_in() {
        return credit_in;
    }

    public void setCredit_in(Double credit_in) {
        this.credit_in = credit_in;
    }

    public Double getCredit_out() {
        return credit_out;
    }

    public void setCredit_out(Double credit_out) {
        this.credit_out = credit_out;
    }

    public Double getTotal_invest() {
        return total_invest;
    }

    public void setTotal_invest(Double total_invest) {
        this.total_invest = total_invest;
    }
}
