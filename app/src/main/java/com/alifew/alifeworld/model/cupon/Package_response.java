package com.alifew.alifeworld.model.cupon;

import com.google.gson.annotations.SerializedName;

public class Package_response {
    @SerializedName("id")
    private String id;
    @SerializedName("cupon_id")
    private String cupon_id;
    @SerializedName("package_name")
    private String package_name;
    @SerializedName("packageSellAmount")
    private String packageSellAmount;
    @SerializedName("maximum_package_owner")
    private String maximum_package_owner;
    @SerializedName("winner")
    private String winner;
    @SerializedName("gift")
    private String gift;

    @SerializedName("customer_count")
    private String customerCount;

    @SerializedName(("inPackage"))
    private Boolean inPackage;

    @SerializedName("minimum_package_point")
    private String minimum_package_point;


    public Boolean getInPackage() {
        return inPackage;
    }

    public void setInPackage(Boolean inPackage) {
        this.inPackage = inPackage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCupon_id() {
        return cupon_id;
    }

    public void setCupon_id(String cupon_id) {
        this.cupon_id = cupon_id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPackageSellAmount() {
        return packageSellAmount;
    }

    public void setPackageSellAmount(String packageSellAmount) {
        this.packageSellAmount = packageSellAmount;
    }

    public String getMaximum_package_owner() {
        return maximum_package_owner;
    }

    public void setMaximum_package_owner(String maximum_package_owner) {
        this.maximum_package_owner = maximum_package_owner;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public String getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(String customerCount) {
        this.customerCount = customerCount;
    }

    public String getMinimum_package_point() {
        return minimum_package_point;
    }

    public void setMinimum_package_point(String minimum_package_point) {
        this.minimum_package_point = minimum_package_point;
    }
}
