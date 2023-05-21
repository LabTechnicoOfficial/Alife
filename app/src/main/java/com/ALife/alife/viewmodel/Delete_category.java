package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.delete_category_repositories;
import com.ALife.alife.model.delete_category_response;

public class Delete_category extends ViewModel {
    private delete_category_repositories repositories;

    public LiveData<delete_category_response> getdata( String id)
    {

        //repositories=new delete_category_repositories(id);
        //return repositories.getdata();
        return delete_category_repositories.getInstance().getdata(id);
    }
    public LiveData<delete_category_response> getdelete_product( String id)
    {

       // repositories=new delete_category_repositories(id);
        //return repositories.getproduct_delete();
        return delete_category_repositories.getInstance().getproduct_delete(id);
    }
}
