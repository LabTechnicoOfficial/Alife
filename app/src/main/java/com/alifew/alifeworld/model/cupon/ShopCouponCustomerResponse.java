package com.alifew.alifeworld.model.cupon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShopCouponCustomerResponse {


    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("cupon_id")
    @Expose
    public String cuponId;
    @SerializedName("package_name")
    @Expose
    public String packageName;
    @SerializedName("packageSellAmount")
    @Expose
    public String packageSellAmount;
    @SerializedName("maximum_package_owner")
    @Expose
    public String maximumPackageOwner;
    @SerializedName("winner")
    @Expose
    public String winner;
    @SerializedName("gift")
    @Expose
    public String gift;
    @SerializedName("customer_list")
    @Expose
    public List<Customer> customerList;

    public class Customer {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("pkg_id")
        @Expose
        public String pkgId;
        @SerializedName("phone")
        @Expose
        public String phone;
        @SerializedName("amount")
        @Expose
        public String amount;
        @SerializedName("point")
        @Expose
        public String point;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("gift_menu")
        @Expose
        public String giftMenu;
        @SerializedName("shop_id")
        @Expose
        public String shopId;
        @SerializedName("position")
        @Expose
        public String position;

    }
}
