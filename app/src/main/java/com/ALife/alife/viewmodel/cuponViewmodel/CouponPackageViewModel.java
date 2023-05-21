package com.ALife.alife.viewmodel.cuponViewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.cupon.add_response;
import com.ALife.alife.model.cupon.package_repositories;
import com.ALife.alife.model.cupon.package_response;

import java.util.List;

public class CouponPackageViewModel extends ViewModel {
    public LiveData<add_response> addPackage(String cupon_id,String package_name,String packageSell_amount,String winner,String gift)
    {
        return package_repositories.getInstance().add_package(cupon_id,package_name,packageSell_amount,winner,gift);
    }
    public LiveData<List<package_response>> getPackage(String cupon_id)
    {
        return package_repositories.getInstance().getData(cupon_id);
    }
}
