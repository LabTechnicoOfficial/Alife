package com.alifew.alife.EarningApp.Model.CashOut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Method_response {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("method")
    @Expose
    public String method;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
