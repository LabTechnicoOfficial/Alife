package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.get_version_repositories;
import com.ALife.alife.model.get_version_response;

public class Get_version extends ViewModel {
    private get_version_repositories repositories;

    public LiveData<get_version_response> getData() {
        //repositories=new get_version_repositories();
        //return repositories.getData();
        return get_version_repositories.getInstance().getData();
    }
}
