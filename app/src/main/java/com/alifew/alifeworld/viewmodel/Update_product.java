package com.alifew.alifeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.update_product_repositories;
import com.alifew.alifeworld.model.update_product_response;

public class Update_product extends ViewModel {
    update_product_repositories repositories;

    public LiveData<update_product_response> getData(String product_id, String product_name, String product_unit, String selling_price, String buy_price, String product_offer, String price_with_offer, String sell_profit, String stock_amount, String product_image, String unit_selling_price, String unit_price_with_offer, String product_description, String vaoture_no, String vaoture_image, int token) {


        //repositories=new update_product_repositories(product_id,product_name,product_unit,selling_price,buy_price,product_offer,price_with_offer,sell_profit,stock_amount,product_image,unit_selling_price,unit_price_with_offer,product_description,vaoture_no,vaoture_image);
        if (token == 0) {
            // return repositories.getData1();
            return update_product_repositories.getInstance().getData1(product_id, product_name, product_unit, selling_price, buy_price, product_offer, price_with_offer, sell_profit, stock_amount, product_image, unit_selling_price, unit_price_with_offer, product_description, vaoture_no, vaoture_image);
        } else if (token == 1) {
            //return repositories.getData4();
            return update_product_repositories.getInstance().getData4(product_id, product_name, product_unit, selling_price, buy_price, product_offer, price_with_offer, sell_profit, stock_amount, product_image, unit_selling_price, unit_price_with_offer, product_description, vaoture_no, vaoture_image);
        } else if (token == 2) {
            //return repositories.getData3();
            return update_product_repositories.getInstance().getData3(product_id, product_name, product_unit, selling_price, buy_price, product_offer, price_with_offer, sell_profit, stock_amount, product_image, unit_selling_price, unit_price_with_offer, product_description, vaoture_no, vaoture_image);
        } else {
            //return repositories.getData2();
            return update_product_repositories.getInstance().getData2(product_id, product_name, product_unit, selling_price, buy_price, product_offer, price_with_offer, sell_profit, stock_amount, product_image, unit_selling_price, unit_price_with_offer, product_description, vaoture_no, vaoture_image);
        }

    }
}
