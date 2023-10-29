package com.alifew.alifeworld.DB.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tblCustomer")
public class Customer {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "customer_id")
    private String customerID;

    @ColumnInfo(name = "name")
    private String customerName;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "image")
    private String image;

    public Customer( String customerID, String customerName, String address, String phone, String image) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.phone = phone;
        this.image = image;
    }


    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
