package com.alifew.alife.EarningApp.Model.CashOut;

import com.google.gson.annotations.SerializedName;

public class Commission_response {

    @SerializedName("comission")
    String comission;

    public String getComission() {
        return comission;
    }

    public void setComission(String comission) {
        this.comission = comission;
    }
}
