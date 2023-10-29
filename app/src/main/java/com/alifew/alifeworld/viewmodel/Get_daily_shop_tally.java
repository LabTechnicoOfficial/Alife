package com.alifew.alifeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.get_daily_due_sell_response;
import com.alifew.alifeworld.model.get_daily_sell_cash_response;
import com.alifew.alifeworld.model.get_sell_details_response;
import com.alifew.alifeworld.model.get_daily_shop_accounting_repositories;
import com.alifew.alifeworld.model.shop_tally_khata_response;
import com.alifew.alifeworld.model.systemetic_sell_details_response;

import java.util.List;

public class Get_daily_shop_tally extends ViewModel {
    private get_daily_shop_accounting_repositories repositories;

    public LiveData<List<systemetic_sell_details_response>> get_productlist(String sell_id) {
        // repositories = new get_daily_shop_accounting_repositories(sell_id,"cbb");
        // return repositories.getProductlist();
        return get_daily_shop_accounting_repositories.getInstance().getProductlist(sell_id);
    }

    public LiveData<List<get_daily_sell_cash_response>> get_sell_cash(String shop_id, String date) {
        //repositories = new get_daily_shop_accounting_repositories(shop_id,date);
        //return repositories.getSell_cash();
        return get_daily_shop_accounting_repositories.getInstance().getSell_cash(shop_id, date);
    }

    public LiveData<List<get_daily_due_sell_response>> get_sell_due(String shop_id, String date) {
        //repositories = new get_daily_shop_accounting_repositories(shop_id,date);
        //return repositories.getSell_due();
        return get_daily_shop_accounting_repositories.getInstance().getSell_due(shop_id, date);
    }

    public LiveData<get_sell_details_response> get_sell_details(String shop_id, String date) {
        // repositories = new get_daily_shop_accounting_repositories(shop_id,date);
        //return repositories.getSell_details();
        return get_daily_shop_accounting_repositories.getInstance().getSell_details(shop_id, date);
    }

    public LiveData<List<shop_tally_khata_response>> getSell_list(String shop_id, String date, int page, int limit) {
        // repositories=new get_daily_shop_accounting_repositories(shop_id,date,page,limit);
        //return repositories.getSell_list();
        return get_daily_shop_accounting_repositories.getInstance().getSell_list(shop_id, date, page, limit);
    }
}
