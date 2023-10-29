package com.alifew.alifeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.Unit_repositories;
import com.alifew.alifeworld.model.Unit_response;

import java.util.List;

public class Unit extends ViewModel {
    Unit_repositories repositories;

    public LiveData<List<Unit_response>> getdata(String value) {

        //repositories=new Unit_repositories(value);
        //return repositories.getdata();
        return Unit_repositories.getInstance().getdata(value);
    }
}
