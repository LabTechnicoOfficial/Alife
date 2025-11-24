package com.alifew.bcopay.model;

import com.google.gson.annotations.SerializedName;

public class get_shop_admin_information_response {
    @SerializedName("agent_name")
   private String agent_name;
    @SerializedName("agent_phone")
    private String agent_phone;
    @SerializedName("agent_password")
    private String agent_password;
    @SerializedName("agent_shop_id")
    private String agent_shop_id;
    @SerializedName("status")
    private String status;
    @SerializedName("agent_image")
    private String agent_image;
    @SerializedName("agent_access")
    private String agent_access;

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getAgent_phone() {
        return agent_phone;
    }

    public void setAgent_phone(String agent_phone) {
        this.agent_phone = agent_phone;
    }

    public String getAgent_password() {
        return agent_password;
    }

    public void setAgent_password(String agent_password) {
        this.agent_password = agent_password;
    }

    public String getAgent_shop_id() {
        return agent_shop_id;
    }

    public void setAgent_shop_id(String agent_shop_id) {
        this.agent_shop_id = agent_shop_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAgent_image() {
        return agent_image;
    }

    public void setAgent_image(String agent_image) {
        this.agent_image = agent_image;
    }

    public String getAgent_access() {
        return agent_access;
    }

    public void setAgent_access(String agent_access) {
        this.agent_access = agent_access;
    }
}
