package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.push_notification_all_product_discount_repositories;
import com.ALife.alife.model.push_notification_response;
import com.ALife.alife.model.push_notification_sell_customer_repositories;
import com.ALife.alife.model.push_notification_sell_shop_repositories;

public class Push_notification extends ViewModel {
    push_notification_all_product_discount_repositories all_discount_notification;
    push_notification_sell_shop_repositories sell_shop_notification;
    push_notification_sell_customer_repositories sell_customer_notification;

    public LiveData<push_notification_response> all_discount_notification(String shop_id, String all_discount) {
        //all_discount_notification = new push_notification_all_product_discount_repositories(shop_id, all_discount);
        //return all_discount_notification.getData();
        return push_notification_all_product_discount_repositories.getInstance().getData(shop_id, all_discount);
    }

    public LiveData<push_notification_response> sell_notification_shop(String shop_id, String customer_name, String price, String due) {
        // sell_shop_notification = new push_notification_sell_shop_repositories(shop_id, customer_name, price, due);
        //return sell_shop_notification.getData();
        return push_notification_sell_shop_repositories.getInstance().getData(shop_id, customer_name, price, due);
    }

    public LiveData<push_notification_response> sell_notification_customer(String shop_id, String customer_id, String price, String due) {
        // sell_customer_notification = new push_notification_sell_customer_repositories(shop_id, customer_id, price, due);
        //return sell_customer_notification.getData();
        return push_notification_sell_customer_repositories.getInstance().getData(shop_id, customer_id, price, due);
    }
}
