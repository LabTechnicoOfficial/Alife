package com.alifew.alifeworld.model.cupon;

import com.google.gson.annotations.SerializedName;

public class cupon_response {
    @SerializedName("id")
    private String id;
    @SerializedName("shop_id")
    private String shop_id;
    @SerializedName("cupon_name")
    private String cupon_name;
    @SerializedName("time_range")
    private String time_range;
    @SerializedName("creation_date")
    private String creation_date;
    @SerializedName("end_date")
    private String end_date;
    @SerializedName("description")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getCupon_name() {
        return cupon_name;
    }

    public void setCupon_name(String cupon_name) {
        this.cupon_name = cupon_name;
    }

    public String getTime_range() {
        return time_range;
    }

    public void setTime_range(String time_range) {
        this.time_range = time_range;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
