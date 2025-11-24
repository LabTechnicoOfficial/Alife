package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.add_sell_payment_cash_response;
import com.alifew.bcopay.model.add_payment_transaction_response;
import com.alifew.bcopay.model.product_sell_payment_repositories;

public class Product_sell_payment extends ViewModel {
    private product_sell_payment_repositories repositories;

    public LiveData<add_sell_payment_cash_response> get_cash(String sell_id, String payment_system, String payment_amount, String date) {
        //repositories = new product_sell_payment_repositories(sell_id, payment_system, payment_amount,date);
        //return repositories.getData_cash();
        return product_sell_payment_repositories.getInstance().getData_cash(sell_id, payment_system, payment_amount, date);
    }

    public LiveData<add_payment_transaction_response> get_transaction(String sell_id, String shop_id, String customer_id,String customer_phone, String payment_type, String payment_amount, String payment_method, String date) {
        // repositories = new product_sell_payment_repositories(sell_id,shop_id,customer_id,payment_type, payment_amount,payment_method,date);
        //return repositories.getData_due();
        return product_sell_payment_repositories.getInstance().getData_due(sell_id, shop_id, customer_id,customer_phone, payment_type, payment_amount, payment_method, date);

    }
}
