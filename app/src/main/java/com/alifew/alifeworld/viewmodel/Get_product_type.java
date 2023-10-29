package com.alifew.alifeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.get_count_for_type_repositories;
import com.alifew.alifeworld.model.get_count_for_type_response;
import com.alifew.alifeworld.model.get_product_type_repositories;
import com.alifew.alifeworld.model.get_product_type_response;

import java.util.List;

public class Get_product_type extends ViewModel {
    get_product_type_repositories repositories;
    get_count_for_type_repositories repositories_count;

    public LiveData<List<get_product_type_response>> getdata(String id) {

        //repositories=new get_product_type_repositories(id);
        //return repositories.getdata();
        return get_product_type_repositories.getInstance().getdata(id);
    }

    public LiveData<get_count_for_type_response> getCount(String id) {
        //repositories_count=new get_count_for_type_repositories(id);
        // return repositories_count.getData();
        return get_count_for_type_repositories.getInstance().getData(id);
    }
}
