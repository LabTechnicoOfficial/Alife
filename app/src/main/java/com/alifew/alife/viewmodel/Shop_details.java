package com.alifew.alife.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alifew.alife.model.Shop_details_repositories;
import com.alifew.alife.model.Shop_response;

public class Shop_details extends AndroidViewModel {
    Shop_details_repositories repositories;
    public Shop_details(@NonNull Application application) {
        super(application);
    }
    public LiveData<Shop_response> getdata(String id)
    {
      //  repositories=new Shop_details_repositories(id);
       // return repositories.getdata();
        return Shop_details_repositories.getInstance().getdata(id);
    }
}
