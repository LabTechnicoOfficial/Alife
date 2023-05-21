package com.ALife.alife.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ALife.alife.model.get_product_repositories;
import com.ALife.alife.model.get_product_response;

import java.util.List;

public class Get_product extends ViewModel {
    get_product_repositories repositories;
    public LiveData<List<get_product_response>> getdata(String id,int page,int limit)
    {

        //repositories=new get_product_repositories(id,page,limit);
        //return repositories.getdata();
        return get_product_repositories.getInstance().getdata(id, page, limit);
    }
    public LiveData<List<get_product_response>> getCategoryProduct(String id)
    {
        //repositories=new get_product_repositories(id);
        //return repositories.getCategoryProduct();
        return get_product_repositories.getInstance().getCategoryProduct(id);
    }
    public LiveData<get_product_response> getsingle_product(String id)
    {

        //repositories=new get_product_repositories(id);
        //return repositories.getproduct();
        return get_product_repositories.getInstance().getproduct(id);
    }
}
