package com.alifew.alife.EarningApp.Model.Team;

import com.google.gson.annotations.SerializedName;

public class Team_response {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("phone")
    private String phone;
    @SerializedName("mail")
    private String mail;
    @SerializedName("password")
    private String password;
    @SerializedName("refferal")
    private String refferal;
    @SerializedName("total_balance")
    private String totalBalance;
    @SerializedName("s_coin")
    private String sCoin;

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
}
