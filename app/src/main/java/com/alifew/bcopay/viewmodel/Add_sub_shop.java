package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.add_sub_shop_repositories;
import com.alifew.bcopay.model.add_sub_shop_response;

public class Add_sub_shop extends ViewModel {
    add_sub_shop_repositories repositories;
    public LiveData<add_sub_shop_response> getData(String parent_id,String child_id){
        //repositories=new add_sub_shop_repositories(parent_id,child_id);
        //return  repositories.getData();
        return add_sub_shop_repositories.getInstance().getData(parent_id,child_id);
    }
}
