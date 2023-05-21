package com.ALife.alife.viewmodel.cuponViewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.cupon.customerFor_cupon_response;
import com.ALife.alife.model.cupon.customerFor_cupon_repositories;
import java.util.List;

public class CustomerFor_cupon extends ViewModel {
    public LiveData<List<customerFor_cupon_response>> getData(String shop_id,String date1,String date2)
    {
        return customerFor_cupon_repositories.getInstance().getData(shop_id, date1, date2);
    }
}
