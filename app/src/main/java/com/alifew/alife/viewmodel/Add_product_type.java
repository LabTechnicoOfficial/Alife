package com.alifew.alife.viewmodel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.add_product_type_repositories;
import com.alifew.alife.model.add_product_type_response;

public class Add_product_type extends ViewModel {
    add_product_type_repositories repositories;
    public LiveData<add_product_type_response> getmessage(String type,String count,String id)
    {

       // repositories=new add_product_type_repositories(type,count,id);
        //return repositories.getdata();
        return add_product_type_repositories.getInstance().getdata(type, count, id);
    }
}
