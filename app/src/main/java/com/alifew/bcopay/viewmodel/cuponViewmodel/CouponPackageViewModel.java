package com.alifew.bcopay.viewmodel.cuponViewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.cupon.add_response;
import com.alifew.bcopay.model.cupon.package_repositories;
import com.alifew.bcopay.model.cupon.Package_response;

import java.util.List;

public class CouponPackageViewModel extends ViewModel {
    public LiveData<add_response> addPackage(String cupon_id, String package_name, String packageSell_amount, String winner, String gift) {
        return package_repositories.getInstance().add_package(cupon_id, package_name, packageSell_amount, winner, gift);
    }

    public LiveData<List<Package_response>> getPackage(String couponID, String shopID, String phone, String createdDate, String endDate) {
        return package_repositories.getInstance().getData(couponID, shopID, phone, createdDate, endDate);
    }
}
