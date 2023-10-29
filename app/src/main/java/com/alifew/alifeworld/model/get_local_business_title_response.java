package com.alifew.alifeworld.model;

import com.google.gson.annotations.SerializedName;

public class get_local_business_title_response {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("shop_id")
    private String shop_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }
}
