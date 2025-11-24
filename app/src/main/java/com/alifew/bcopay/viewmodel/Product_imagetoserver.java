package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.Imagetoserver_repositories;
import com.alifew.bcopay.model.Imagetoserver_response;

public class Product_imagetoserver extends ViewModel {
    Imagetoserver_repositories repositories;

    public LiveData<Imagetoserver_response> getData(String image, String id) {
        // repositories=new Imagetoserver_repositories(id,image);
        //return  repositories.getData();
        return Imagetoserver_repositories.getInstance().getData(id, image);
    }
}
