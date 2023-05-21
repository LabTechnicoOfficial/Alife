package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.delete_type_count_repositories;
import com.ALife.alife.model.delete_type_count_response;

public class Delete_type_count extends ViewModel {
    private delete_type_count_repositories repositories;

    public LiveData<delete_type_count_response> getdata(String id) {

        //repositories=new delete_type_count_repositories(id);
        //return repositories.getdata();
        return delete_type_count_repositories.getInstance().getdata(id);
    }
}
