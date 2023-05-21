package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.edit_type_count_repositories;
import com.ALife.alife.model.edit_type_count_response;

public class Edit_type_count extends ViewModel {
    edit_type_count_repositories repositories;
    public LiveData<edit_type_count_response> getmessage(String type, String count, String id)
    {

       // repositories=new edit_type_count_repositories(type,count,id);
        //return repositories.getdata();
        return edit_type_count_repositories.getInstance().getdata(type, count, id);
    }
}
