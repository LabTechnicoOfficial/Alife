package com.alifew.alife.DB.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "tblLocalSellProducts")
public class LocalSellProducts {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "product_id")
    private String productID;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "sell_price")
    String sellPrice;
    @ColumnInfo(name = "buy_price")
    private String buyPrice;
    @ColumnInfo(name = "image")
    private String image;

    public LocalSellProducts(String productID, String name, String sellPrice, String buyPrice, String image) {
        this.productID = productID;
        this.name = name;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.image = image;
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

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
