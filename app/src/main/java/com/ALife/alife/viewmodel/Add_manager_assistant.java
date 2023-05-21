package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.add_manager_assistant_repositories;
import com.ALife.alife.model.add_manager_assistant_response;

public class Add_manager_assistant extends ViewModel {
    add_manager_assistant_repositories repositories;
    public LiveData<add_manager_assistant_response> getData(String manager_id,String assistant_id)
    {
      //  repositories=new add_manager_assistant_repositories(manager_id,assistant_id);
       // return repositories.getData();
        return add_manager_assistant_repositories.getInstance().getData(manager_id, assistant_id);
    }
}
