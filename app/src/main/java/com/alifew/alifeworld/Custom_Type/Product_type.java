package com.alifew.alifeworld.Custom_Type;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product_type {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("id")
    @Expose
    private int id;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
