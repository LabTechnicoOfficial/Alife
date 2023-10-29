package com.alifew.alifeworld.model;

import com.google.gson.annotations.SerializedName;

public class get_local_business_details_response {
    @SerializedName("id")

    private String id;
    @SerializedName("details")

     private String details;
    @SerializedName("amount")
     private String amount;
    @SerializedName("price")
     private String price;
    @SerializedName("title_id")
     private String title_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle_id() {
        return title_id;
    }

    public void setTitle_id(String title_id) {
        this.title_id = title_id;
    }
}
