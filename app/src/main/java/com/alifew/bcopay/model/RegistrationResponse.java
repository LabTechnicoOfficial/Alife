package com.alifew.bcopay.model;

public class RegistrationResponse {
    String name, owner, phone, type, location, password, image, otp, task_type;

    public RegistrationResponse(String name, String owner, String phone, String type, String location, String password, String image, String otp, String task_type) {
        this.name = name;
        this.owner = owner;
        this.phone = phone;
        this.type = type;
        this.location = location;
        this.password = password;
        this.image = image;
        this.otp = otp;
        this.task_type = task_type;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    public String getOtp() {
        return otp;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public String getPassword() {
        return password;
    }

    public String getImage() {
        return image;
    }
}
