package com.alifew.alife.model.local_sell;

import com.alifew.alife.model.shop_sell_history_list_response;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class local_sell_history_response {

    @SerializedName("customer_name")
    private String customer_name;
    @SerializedName("customer_phone")
    private String customer_phone;
    @SerializedName("sell_price")
    private String sell_price;
    @SerializedName("points")
    private String points;
    @SerializedName("buy_price")
    private String buy_price;
    @SerializedName("profit")
    private String profit;
    @SerializedName("date")
    private String date;
    @SerializedName("prduct_description")
    private String product_description;

    @SerializedName("refer_info")
    public ReferInfo referInfo;
    @SerializedName("image")
    private List<local_sell_image> image;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getSell_price() {
        return sell_price;
    }

    public void setSell_price(String sell_price) {
        this.sell_price = sell_price;
    }

    public String getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(String buy_price) {
        this.buy_price = buy_price;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public List<local_sell_image> getImage() {
        return image;
    }

    public void setImage(List<local_sell_image> image) {
        this.image = image;
    }

    public class ReferInfo {

        @SerializedName("name")
        public String name;
        @SerializedName("phone")
        public String phone;
        @SerializedName("point")
        public String point;

    }
}
