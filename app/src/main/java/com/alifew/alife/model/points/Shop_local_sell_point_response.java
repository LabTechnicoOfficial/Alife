package com.alifew.alife.model.points;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shop_local_sell_point_response {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("shop_id")
    @Expose
    public String shopID;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("points")
    @Expose
    public String points;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("create_at")
    @Expose
    public String createAt;
    @SerializedName("update_at")
    @Expose
    public String updateAt;


}
