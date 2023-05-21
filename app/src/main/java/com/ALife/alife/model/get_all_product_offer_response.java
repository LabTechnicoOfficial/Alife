package com.ALife.alife.model;

import com.google.gson.annotations.SerializedName;

public class get_all_product_offer_response {
    @SerializedName("id")
    private String id;
    @SerializedName("minimum_amount")
    private String minimum_amount;
    @SerializedName("minimum_price")
    private String minimum_price;
    @SerializedName("offer_percentage")
    private String offer_percentage;
    @SerializedName("shop_id")
    private String shop_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMinimum_amount() {
        return minimum_amount;
    }

    public void setMinimum_amount(String minimum_amount) {
        this.minimum_amount = minimum_amount;
    }

    public String getMinimum_price() {
        return minimum_price;
    }

    public void setMinimum_price(String minimum_price) {
        this.minimum_price = minimum_price;
    }

    public String getOffer_percentage() {
        return offer_percentage;
    }

    public void setOffer_percentage(String offer_percentage) {
        this.offer_percentage = offer_percentage;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }
}
