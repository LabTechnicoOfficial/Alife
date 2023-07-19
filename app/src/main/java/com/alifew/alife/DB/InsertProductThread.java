package com.alifew.alife.DB;

import android.content.Context;

import androidx.room.Room;

public class InsertProductThread extends Thread {
    String productID, name, image, barcode, printCheck, price, stock, unit, type;
    Context context;

    public InsertProductThread(String productID, String name, String printCheck, String image, String barcode, String stock, String price, String unit, String type, Context context) {
        this.productID = productID;
        this.name = name;
        this.image = image;
        this.barcode = barcode;
        this.context = context;
        this.printCheck = printCheck;
        this.stock = stock;
        this.price = price;
        this.unit = unit;
        this.type = type;
    }

    public void run() {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "alifeDB").fallbackToDestructiveMigration().build();

        ProductDao productDao = db.productDao();
        productDao.insertProducts(new Products(productID, name, printCheck, image, barcode, stock, price, unit, type));
    }
}
