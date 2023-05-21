package com.ALife.alife.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class get_shop_business_summary_response {

    @SerializedName("image")
    public String image;
    @SerializedName("description")
    public String description;
    @SerializedName("credit_in")
    public String credit_in;
    @SerializedName("credit_out")
    public String credit_out;
    @SerializedName("total_invest")
    public String total_invest;
    @SerializedName("date")
    public String date;
    @SerializedName("time")
    public String time;
    @SerializedName("summary_image")
    public List<image> summary_image;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCredit_in() {
        return credit_in;
    }

    public void setCredit_in(String credit_in) {
        this.credit_in = credit_in;
    }

    public String getCredit_out() {
        return credit_out;
    }

    public void setCredit_out(String credit_out) {
        this.credit_out = credit_out;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotal_invest() {
        return total_invest;
    }

    public void setTotal_invest(String total_invest) {
        this.total_invest = total_invest;
    }

    public List<image> getSummary_image() {
        return summary_image;
    }

    public void setSummary_image(List<image> summary_image) {
        this.summary_image = summary_image;
    }
}
