package com.alifew.alifeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.Customer_registration_repositoris;
import com.alifew.alifeworld.model.add_shop_due_customer_repositories;
import com.alifew.alifeworld.model.add_shop_due_customer_response;
import com.alifew.alifeworld.model.customer_registration_response;
import com.alifew.alifeworld.model.update_shop_customer_record_repositories;
import com.alifew.alifeworld.model.update_shop_customer_record_response;

public class Customer_registration extends ViewModel {
    Customer_registration_repositoris repositoris;
    add_shop_due_customer_repositories repositories1;

    //public Customer_registration(@NonNull Application application) {
    // super(application);
    // }

    public LiveData<String> getvarification(String phone) {
        // repositoris = new Customer_registration_repositoris(phone);
        //return repositoris.getVarefication();
        return Customer_registration_repositoris.getInstance().getVarefication(phone);
    }

    public LiveData<customer_registration_response> getmessage(String name, String location, String phone, String password, String image, String token) {
        //repositoris = new Customer_registration_repositoris(name, location, phone, password, image,token);
        //return repositoris.getMessage();
        return Customer_registration_repositoris.getInstance().getMessage(name, location, phone, password, image, token);
    }
    public LiveData<add_shop_due_customer_response> get_add_due_customer_response(String shop_id,String customer_id,String phone)
    {
        //repositories1=new add_shop_due_customer_repositories(shop_id, customer_id);
        //return repositories1.getData();
        return add_shop_due_customer_repositories.getInstance().getData(shop_id, customer_id,phone);
    }
    public LiveData<update_shop_customer_record_response> getData(String customer_id,String customer_phone)
    {
        return update_shop_customer_record_repositories.getInstance().getData(customer_id, customer_phone);
    }
}
