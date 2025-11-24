package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.edit_category_repositories;
import com.alifew.bcopay.model.edit_category_response;

public class Edit_category extends ViewModel {
    private edit_category_repositories repositories;

    public LiveData<edit_category_response> getdata(String logo, String name, String id, int token) {

        //repositories = new edit_category_repositories(logo, name, id);
        if (token == 1) {
            //return repositories.getdata2();
            return edit_category_repositories.getInstance().getdata2(logo, name, id);
        } else {
            //return repositories.getdata();
            return edit_category_repositories.getInstance().getdata(name, id);
        }
    }
}
