package com.alifew.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.model.remove_manager_assistant_repositories;
import com.alifew.alife.model.remove_manager_assistant_response;

public class Remove_manager_assistant extends ViewModel {
    remove_manager_assistant_repositories repositories;

    public LiveData<remove_manager_assistant_response> getData(String manager_id, String assistant_id) {
        // repositories=new remove_manager_assistant_repositories(manager_id,assistant_id);
        //return repositories.getData();
        return remove_manager_assistant_repositories.getInstance().getData(manager_id, assistant_id);
    }
}
