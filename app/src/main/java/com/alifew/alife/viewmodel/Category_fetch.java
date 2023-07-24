package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.Category_response;
import com.alifew.alife.model.Fetch_category_repositories;

import java.util.List;

public class Category_fetch extends ViewModel {
    Fetch_category_repositories repositories;
   // public Category_fetch(@NonNull Application application) {
       // super(application);
    //}
    public LiveData<List<Category_response>> getdata(String id,int page,int limit)
    {

        //repositories=new Fetch_category_repositories(id,page,limit);
        //return repositories.getdata();
        return Fetch_category_repositories.getInstance().getdata(id, page, limit);
    }
    public LiveData<List<Category_response>> getCategory(String id,String search)
    {
       // repositories=new Fetch_category_repositories(id,search);
        //return repositories.getCategory();
        return Fetch_category_repositories.getInstance().getCategory(id, search);
    }

}
