package com.alifew.alife.viewmodel.Shop_notification;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.shop_notification.shop_notification_response;
import com.alifew.alife.model.shop_notification.shop_notification_repositories;

public class Shop_notification extends ViewModel {
    public LiveData<shop_notification_response> getData(String shop_id, String message) {
        return shop_notification_repositories.getInstance().getData(shop_id, message);
    }
}
