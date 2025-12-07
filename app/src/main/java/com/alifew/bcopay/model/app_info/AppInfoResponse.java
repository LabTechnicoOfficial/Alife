package com.alifew.bcopay.model.app_info;

import com.google.gson.annotations.SerializedName;

public class AppInfoResponse {
    @SerializedName("id")
    public String id;
    @SerializedName("app_version")
    public String app_version;
    @SerializedName("app_link")
    public String app_link;
}
