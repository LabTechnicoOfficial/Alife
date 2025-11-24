package com.alifew.bcopay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.add_local_business_details_repositories;
import com.alifew.bcopay.model.add_local_business_response;
import com.alifew.bcopay.model.add_local_business_subtitle_repositories;
import com.alifew.bcopay.model.add_local_business_title_repositories;
import com.alifew.bcopay.model.get_local_business_details_response;
import com.alifew.bcopay.model.get_local_business_repositories;
import com.alifew.bcopay.model.get_local_business_subtitle_response;
import com.alifew.bcopay.model.get_local_business_title_response;
import com.alifew.bcopay.model.shop_local_page_item_list_response;

import java.util.List;

public class Local_business extends ViewModel {
    private add_local_business_title_repositories add_title;
    private add_local_business_subtitle_repositories add_subtitle;
    private add_local_business_details_repositories add_details;
    private get_local_business_repositories repositories;

    public LiveData<add_local_business_response> add_title(String title, String id) {
        //add_title=new add_local_business_title_repositories(title, id);
        //return add_title.getData();
        return add_local_business_title_repositories.getInstance().getData(title, id);
    }

    public LiveData<add_local_business_response> add_subtitle(String subtitle, String id) {
        // add_subtitle=new add_local_business_subtitle_repositories(subtitle, id);
        //return add_subtitle.getData();
        return add_local_business_subtitle_repositories.getInstance().getData(subtitle, id);
    }

    public LiveData<add_local_business_response> add_details(String details, String amount, String price, String id) {
        //add_details=new add_local_business_details_repositories(details, amount, price, id);
        //return add_details.getData();
        return add_local_business_details_repositories.getInstance().getData(details, amount, price, id);
    }

    public LiveData<List<get_local_business_title_response>> get_title(String id, int page, int limit) {
        // repositories=new get_local_business_repositories(id, page, limit);
        // return repositories.get_tile();
        return get_local_business_repositories.getInstance().get_tile(id, page, limit);
    }

    public LiveData<List<get_local_business_subtitle_response>> get_subtitle(String id, int page, int limit) {
        //repositories=new get_local_business_repositories(id, page, limit);
        //return repositories.get_subtitle();
        return get_local_business_repositories.getInstance().get_subtitle(id, page, limit);
    }
    public LiveData<List<shop_local_page_item_list_response>> getItem(String id,int page, int limit)
    {
        return get_local_business_repositories.getInstance().getItemList(id,page,limit);
    }

    public LiveData<List<get_local_business_details_response>> get_details(String id) {
        //repositories=new get_local_business_repositories(id);
        //return repositories.get_details();
        return get_local_business_repositories.getInstance().get_details(id);

    }

}
