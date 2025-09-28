package com.alifew.bcopay.model.local_sell;

import com.alifew.bcopay.model.image;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class get_local_sell_details_response {
    @SerializedName("description")
    private String description;
    @SerializedName("image")
    private List<com.alifew.bcopay.model.image> image;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<image> getImage() {
        return image;
    }

    public void setImage(List<image> image) {
        this.image = image;
    }
}
