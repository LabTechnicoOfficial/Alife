package com.alifew.bcopay.model;

import com.google.gson.annotations.SerializedName;

public class get_version_response {
    @SerializedName("version_name")
    private String version_name;
    @SerializedName("version_code")
    private String version_code;

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }
}
