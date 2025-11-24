package com.alifew.bcopay.model;

import com.google.gson.annotations.SerializedName;

public class get_product_type_response {
    @SerializedName("Id")
    private String Id;
    @SerializedName("type")
    private String type;
    @SerializedName("count")
    private String count;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
