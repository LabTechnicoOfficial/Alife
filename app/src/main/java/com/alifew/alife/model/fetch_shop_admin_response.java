package com.alifew.alife.model;

import com.google.gson.annotations.SerializedName;

public class fetch_shop_admin_response {
    @SerializedName("agent_id")
    private String agent_id;
    @SerializedName("agent_name")
    private String agent_name;
    @SerializedName("agent_phone")
    private String agent_phone;
    @SerializedName("agent_password")
    private String agent_password;
    @SerializedName("agent_image")
    private String agent_image;
    @SerializedName("agent_access")
    private String agent_access;

    public String getStatus() {
        return status;
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

    public String getAgent_access() {
        return agent_access;
    }

    public void setAgent_access(String agent_access) {
        this.agent_access = agent_access;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getAgent_image() {
        return agent_image;
    }

    public void setAgent_image(String agent_image) {
        this.agent_image = agent_image;
    }
}
