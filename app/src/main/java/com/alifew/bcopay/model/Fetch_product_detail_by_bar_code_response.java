package com.alifew.bcopay.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Fetch_product_detail_by_bar_code_response {
    @SerializedName("product_id")
    @Expose
    public String productId;
    @SerializedName("product_name")
    @Expose
    public String productName;
    @SerializedName("product_shop_id")
    @Expose
    public String productShopId;
    @SerializedName("product_catagory_id")
    @Expose
    public String productCatagoryId;
    @SerializedName("product_unit")
    @Expose
    public String productUnit;
    @SerializedName("selling_price")
    @Expose
    public String sellingPrice;
    @SerializedName("product_offer")
    @Expose
    public String productOffer;
    @SerializedName("buy_price")
    @Expose
    public String buyPrice;
    @SerializedName("sell_profit")
    @Expose
    public String sellProfit;
    @SerializedName("stock_amount")
    @Expose
    public String stockAmount;
    @SerializedName("product_image")
    @Expose
    public String productImage;
    @SerializedName("product_added_date")
    @Expose
    public String productAddedDate;
    @SerializedName("product_description")
    @Expose
    public String productDescription;
    @SerializedName("vaoture_no")
    @Expose
    public String vaotureNo;
    @SerializedName("vaoture_image")
    @Expose
    public String vaotureImage;
    @SerializedName("brand")
    @Expose
    public String brand;
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("added_by")
    @Expose
    public String addedBy;
    @SerializedName("category")
    @Expose
    public Category category;
    @SerializedName("type")
    @Expose
    public List<Type> type;

    public class Category {

        @SerializedName("catagory01y_id")
        @Expose
        public String catagory01yId;
        @SerializedName("catagory01y_logo")
        @Expose
        public String catagory01yLogo;
        @SerializedName("catagory01y_name")
        @Expose
        public String catagory01yName;
        @SerializedName("catagory01y_unit")
        @Expose
        public String catagory01yUnit;
        @SerializedName("under_store_id")
        @Expose
        public String underStoreId;

    }

    public class Type {

        @SerializedName("Id")
        @Expose
        public String id;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("count")
        @Expose
        public String count;
        @SerializedName("product_id")
        @Expose
        public String productId;

    }
}
