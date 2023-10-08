package com.alifew.alife.viewmodel.cuponViewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.cupon.CustomerFor_cupon_response;
import com.alifew.alife.model.cupon.add_response;
import com.alifew.alife.model.cupon.cupon_repositories;
import com.alifew.alife.model.cupon.cupon_response;
import com.alifew.alife.model.cupon.notify_response;

import java.util.List;

public class CouponViewModel extends ViewModel {

    public LiveData<add_response> addCupon(String shop_id, String cupon_name, String time_range, String create_date, String end_date, String description) {
        return cupon_repositories.getInstance().addCupon(shop_id, cupon_name, time_range, create_date, end_date, description);
    }

    public LiveData<List<cupon_response>> getCupon(String shop_id) {
        return cupon_repositories.getInstance().getData(shop_id);
    }

    public LiveData<notify_response> getNotify(String shop_id, String message) {
        return cupon_repositories.getInstance().getNotify(shop_id, message);
    }

    public LiveData<List<CustomerFor_cupon_response>> getCustomerListForCoupon(String shopID, String packageID) {
        return cupon_repositories.getInstance().getCustomerListForCoupon(shopID, packageID);
    }
}
