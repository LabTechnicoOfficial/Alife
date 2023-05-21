package com.ALife.alife.model;

import com.google.gson.annotations.SerializedName;

public class user_instruction_response {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("link")
    private String link;

    @SerializedName("image")
    private String image;

    @SerializedName("status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
