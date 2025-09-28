package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.add_product_repositories;
import com.alifew.bcopay.model.add_product_response;

public class Add_product extends ViewModel {
    add_product_repositories repositories;

    public LiveData<add_product_response> getmessage(String name, String id1, String id2, String unit, String price, String discount, String buy_price, String sell_profit, String stock_amount, String image, String description, String vaoture_no, String vaoture_image, String brand, String productCode, String added_by) {

       // repositories = new add_product_repositories(name, id1, id2, unit, price, discount, buy_price, sell_profit, stock_amount, image, description, vaoture_no, vaoture_image, brand, productCode, added_by);
       // return repositories.getMessage();
        return add_product_repositories.getInstance().getMessage(name, id1, id2, unit, price, discount, buy_price, sell_profit, stock_amount, image, description, vaoture_no, vaoture_image, brand, productCode, added_by);
    }
}
