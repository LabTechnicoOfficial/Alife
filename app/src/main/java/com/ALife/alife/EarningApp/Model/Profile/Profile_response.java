package com.ALife.alife.EarningApp.Model.Profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile_response {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("mail")
    @Expose
    public String mail;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("refferal")
    @Expose
    public String refferal;
    @SerializedName("total_balance")
    @Expose
    public String totalBalance;
    @SerializedName("s_coin")
    @Expose
    public String sCoin;
    @SerializedName("account_status")
    @Expose
    public String accountStatus;
    @SerializedName("refer_status")
    @Expose
    public String referStatus;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRefferal() {
        return refferal;
    }

    public void setRefferal(String refferal) {
        this.refferal = refferal;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getsCoin() {
        return sCoin;
    }

    public void setsCoin(String sCoin) {
        this.sCoin = sCoin;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getReferStatus() {
        return referStatus;
    }

    public void setReferStatus(String referStatus) {
        this.referStatus = referStatus;
    }
}
