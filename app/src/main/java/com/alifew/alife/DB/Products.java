package com.alifew.alife.DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tblProducts")
public class Products {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "product_id")
    private String productID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "print_check")
    private String printCheck;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "barcode")
    private String barcode;

    @ColumnInfo(name = "stock")
    private String stock;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "unit")
    private String unit;

    @ColumnInfo(name = "type")
    private String type;

    public Products(String productID, String name, String printCheck, String image, String barcode, String stock, String price, String unit, String type) {
        this.productID = productID;
        this.name = name;
        this.printCheck = printCheck;
        this.image = image;
        this.barcode = barcode;
        this.stock = stock;
        this.price = price;
        this.unit = unit;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getPrintCheck() {
        return printCheck;
    }

    public void setPrintCheck(String printCheck) {
        this.printCheck = printCheck;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
