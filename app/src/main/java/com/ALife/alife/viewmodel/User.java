package com.ALife.alife.viewmodel;

public class User {
    String id;
    String type;
    String phone;

    public User(String id, String type,String phone) {
        this.id = id;
        this.type = type;
        this.phone=phone;
    }

    public int getId() {
        return Integer.parseInt(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
