package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.add_business_summary_image_repositories;
import com.alifew.bcopay.model.add_business_summary_image_response;
import com.alifew.bcopay.model.add_shop_business_summary_repositories;
import com.alifew.bcopay.model.add_shop_business_summary_response;

public class Add_shop_business_summary extends ViewModel {
    private add_shop_business_summary_repositories repositories;
    private add_business_summary_image_repositories image_repositories;
    public LiveData<add_shop_business_summary_response> getData(String shop_id,String description,String credit_in,String credit_out,String date,String time,String image)
    {
       // repositories=new add_shop_business_summary_repositories(shop_id,description, credit_in, credit_out, date, time, image);
        //return repositories.getData();
        return add_shop_business_summary_repositories.getInstance().getData(shop_id, description, credit_in, credit_out, date, time, image);
    }
    public LiveData<add_business_summary_image_response> get_response(String summary_id,String image)
    {
        //image_repositories=new add_business_summary_image_repositories(summary_id, image);
        //return image_repositories.getData();
        return add_business_summary_image_repositories.getInstance().getData(summary_id, image);
    }
}
