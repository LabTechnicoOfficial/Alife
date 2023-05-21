package com.ALife.alife.model;

import com.google.gson.annotations.SerializedName;

public class get_local_business_subtitle_response {
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private String id;
    @SerializedName("total_number")
    private String total_number;
    @SerializedName("total_price")
    private String total_price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotal_number() {
        return total_number;
    }

    public void setTotal_number(String total_number) {
        this.total_number = total_number;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
}
