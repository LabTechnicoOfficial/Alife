package com.ALife.alife.viewmodel.Local_sell;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.local_sell.add_local_sell_details_response;
import com.ALife.alife.model.local_sell.add_local_sell_image_response;
import com.ALife.alife.model.local_sell.add_local_sell_product_response;
import com.ALife.alife.model.local_sell.add_local_sell_repositories;
import com.ALife.alife.model.local_sell.delete_local_sell_product_response;

public class Add_local_sell extends ViewModel {
    public LiveData<add_local_sell_details_response> addDetails(String sell_id, String description) {
        return add_local_sell_repositories.getInstance().getAdd_details(sell_id, description);
    }

    public LiveData<add_local_sell_image_response> addImage(String sell_id, String image, int check) {
        return add_local_sell_repositories.getInstance().getAdd_image(sell_id, image, check);
    }

    public LiveData<add_local_sell_product_response> addProduct(String shop_id, String product_details, String price, String buyPrice, String image) {
        return add_local_sell_repositories.getInstance().getAdd_product(shop_id, product_details, price, buyPrice, image);
    }

    //delete product
    public LiveData<delete_local_sell_product_response> deleteProduct(String product_id) {
        return add_local_sell_repositories.getInstance().deleteProduct(product_id);
    }

    //edit product
    public LiveData<delete_local_sell_product_response> editProduct(String product_id, String product_details, String price, String buy_price, String image) {
        return add_local_sell_repositories.getInstance().editProduct(product_id, product_details, price, buy_price, image);
    }
}
