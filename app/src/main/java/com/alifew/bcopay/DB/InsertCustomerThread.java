package com.alifew.bcopay.DB;

import android.content.Context;

import com.alifew.bcopay.DB.dao.CustomerDao;
import com.alifew.bcopay.DB.entity.Customer;
import com.alifew.bcopay.model.Get_shop_customer_response;

public class InsertCustomerThread extends Thread {
    String customerID, name, address, phone, image;
    Context context;
//    Get_shop_customer_response response;

    public InsertCustomerThread(Get_shop_customer_response response, Context context) {
        customerID = response.getCustomer01r_id();
        name = response.getCustomer01r_name();
        address = response.getCustomer01r_address();
        phone = response.getCustomer01r_phone();
        image = response.getCustomer01r_image();
        this.context = context;
    }

    public void run() {
        AppDatabase db = AppDatabase.getDatabase(context);
        CustomerDao customerDao = db.customerDao();
        customerDao.insertCustomers(new Customer(customerID, name, address, phone, image));
    }
}
