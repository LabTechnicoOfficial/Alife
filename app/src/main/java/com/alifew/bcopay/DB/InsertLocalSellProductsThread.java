package com.alifew.bcopay.DB;

import android.content.Context;

import com.alifew.bcopay.DB.dao.LocalSellProductsDao;
import com.alifew.bcopay.DB.entity.LocalSellProducts;

public class InsertLocalSellProductsThread extends Thread {
    String productID, name, image, sellPrice, buyPrice;
    Context context;

    public InsertLocalSellProductsThread(String productID, String name, String image, String sellPrice, String buyPrice, Context context) {
        this.productID = productID;
        this.name = name;
        this.image = image;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.context = context;
    }

    public void run() {
        AppDatabase db = AppDatabase.getDatabase(context);
        LocalSellProductsDao localSellProductsDao = db.localSellProductsDao();
        localSellProductsDao.insertProducts(new LocalSellProducts(productID, name, sellPrice, buyPrice, image));
    }
}
