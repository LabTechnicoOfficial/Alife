package com.alifew.alifeworld.viewmodel.cuponViewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.cupon.CustomerFor_cupon_response;
import com.alifew.alifeworld.model.cupon.customerFor_cupon_repositories;
import java.util.List;

public class CustomerFor_cupon extends ViewModel {
    public LiveData<List<CustomerFor_cupon_response>> getData(String shop_id, String date1, String date2)
    {
        return customerFor_cupon_repositories.getInstance().getData(shop_id, date1, date2);
    }
}
