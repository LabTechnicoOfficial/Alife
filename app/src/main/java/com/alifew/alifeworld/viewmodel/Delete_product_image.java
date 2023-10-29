package com.alifew.alifeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.delete_product_image_repositories;
import com.alifew.alifeworld.model.delete_product_image_response;

public class Delete_product_image extends ViewModel {
    delete_product_image_repositories repositories;
    public LiveData<delete_product_image_response> getData(String id)
    {
       // repositories=new delete_product_image_repositories(id);
        //return  repositories.getData();
        return delete_product_image_repositories.getInstance().getData(id);
    }
}
