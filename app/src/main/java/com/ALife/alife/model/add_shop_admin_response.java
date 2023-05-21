package com.ALife.alife.model;

import com.google.gson.annotations.SerializedName;

public class add_shop_admin_response {
    @SerializedName("message")
    private String message;
    @SerializedName("agent_id")
    private String agent_id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }
}
