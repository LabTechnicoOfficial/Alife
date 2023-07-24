package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.get_product_multiple_image_repositories;
import com.alifew.alife.model.get_product_multiple_image_response;

import java.util.List;

public class Get_product_multiple_image extends ViewModel {
    get_product_multiple_image_repositories repositories;

    public LiveData<List<get_product_multiple_image_response>> getData(String product_id) {
        //  repositories=new get_product_multiple_image_repositories(product_id);
        //  return  repositories.getData();
        return get_product_multiple_image_repositories.getInstance().getData(product_id);
    }
}
