package com.ALife.alife.viewmodel.cuponViewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.cupon.notify_response;
import com.ALife.alife.model.cupon.sendPackageCustomer_notification_repositories;

public class sendPackageCustomer_notification extends ViewModel {
    public LiveData<notify_response> getData(String customer_id, String message) {
        return sendPackageCustomer_notification_repositories.getInstance().getData(customer_id, message);
    }
}
