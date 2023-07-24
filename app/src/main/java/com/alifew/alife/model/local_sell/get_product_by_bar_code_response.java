package com.alifew.alife.model.local_sell;

import com.google.gson.annotations.SerializedName;

public class get_product_by_bar_code_response {
    @SerializedName("product_id")
    private String product_id;
    @SerializedName("stock_amount")
    private String stock_amount;
    @SerializedName("all_discount")
    private String all_discount;


    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getStock_amount() {
        return stock_amount;
    }

    public void setStock_amount(String stock_amount) {
        this.stock_amount = stock_amount;
    }

    public String getAll_discount() {
        return all_discount;
    }

    public void setAll_discount(String all_discount) {
        this.all_discount = all_discount;
    }
}
