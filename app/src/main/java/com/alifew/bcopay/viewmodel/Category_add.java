package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.Category_add_repositories;
import com.alifew.bcopay.model.Category_add_response;

public class Category_add extends ViewModel {
    private Category_add_repositories repositories;
    public LiveData<Category_add_response> getdata(String logo,String name,String unit, String id)
    {

       // repositories=new Category_add_repositories(logo,name,unit,id);
        //return repositories.getdata();
        return Category_add_repositories.getInstance().getdata(logo, name, unit, id);
    }
}
