package com.alifew.alifeworld.model;

import com.google.gson.annotations.SerializedName;

public class Unit_response {
    @SerializedName("name")
   public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
