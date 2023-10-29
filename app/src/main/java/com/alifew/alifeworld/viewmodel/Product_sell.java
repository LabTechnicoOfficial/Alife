package com.alifew.alifeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.add_product_sell_response;
import com.alifew.alifeworld.model.add_sell_details_response;
import com.alifew.alifeworld.model.product_sell_repositories;
import com.alifew.alifeworld.model.update_product_stock_by_sell_response;
import com.alifew.alifeworld.model.update_product_type_by_sell_response;

public class Product_sell extends ViewModel {
    private product_sell_repositories repositories;

    public LiveData<add_product_sell_response> sell(String shop_id, String customer_id, String customer_name, String customer_phone, String price, String buy_price, String duePrice, String points, String selled_by, String sell_type, String date, Boolean dueCheck) {
        //repositories=new product_sell_repositories(shop_id,customer_id,customer_name,customer_phone,price,buy_price,selled_by,sell_type,date);
        //return repositories.getData_sell_product();
        return product_sell_repositories.getInstance().getData_sell_product(shop_id, customer_id, customer_name, customer_phone, price, buy_price, duePrice, points, selled_by, sell_type, date,  dueCheck);
    }

    public LiveData<add_sell_details_response> sell_details(String sell_id, String product_id, String product_name, String product_image, String type_id, String product_amount, String price, String buy_price) {
        //repositories=new product_sell_repositories(sell_id,product_id,type_id,product_amount,price,buy_price);
        //return repositories.getData_sell_details();
        return product_sell_repositories.getInstance().getData_sell_details(sell_id, product_id, product_name, product_image, type_id, product_amount, price, buy_price);
    }

    public LiveData<update_product_stock_by_sell_response> stock_update(String stock, String product_id) {
        //repositories=new product_sell_repositories(product_id,stock);
        //return repositories.getData_stock();
        return product_sell_repositories.getInstance().getData_stock(stock, product_id);
    }

    public LiveData<update_product_type_by_sell_response> type_update(String type_count, String type_id) {
        //repositories=new product_sell_repositories(type_count,type_id,"abcd");
        //return repositories.getData_type();
        return product_sell_repositories.getInstance().getData_type(type_count, type_id, "xyz");
    }
}
