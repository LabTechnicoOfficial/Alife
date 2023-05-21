package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.delete_product_image_repositories;
import com.ALife.alife.model.delete_product_image_response;

public class Delete_product_image extends ViewModel {
    delete_product_image_repositories repositories;
    public LiveData<delete_product_image_response> getData(String id)
    {
       // repositories=new delete_product_image_repositories(id);
        //return  repositories.getData();
        return delete_product_image_repositories.getInstance().getData(id);
    }
}
