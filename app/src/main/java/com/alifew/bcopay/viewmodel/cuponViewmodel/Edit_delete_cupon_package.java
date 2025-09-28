package com.alifew.bcopay.viewmodel.cuponViewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.cupon.edit_delete_response;
import com.alifew.bcopay.model.cupon.edit_delete_cupon_package_repositories;

public class Edit_delete_cupon_package extends ViewModel {
    public LiveData<edit_delete_response> deleteCupon(String shop_id, String cupon_id) {
        return edit_delete_cupon_package_repositories.getInstance().deleteCupon(shop_id, cupon_id);
    }

    public LiveData<edit_delete_response> deletePackage(String package_id) {
        return edit_delete_cupon_package_repositories.getInstance().deletePackage(package_id);
    }

    public LiveData<edit_delete_response> edit_package(String package_id, String package_name, String packageSell_amount) {
        return edit_delete_cupon_package_repositories.getInstance().editPackage(package_id, package_name, packageSell_amount);
    }
}
