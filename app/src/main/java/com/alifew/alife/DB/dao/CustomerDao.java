package com.alifew.alife.DB.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.alifew.alife.DB.entity.Customer;

import java.util.List;

@Dao
public interface CustomerDao {
    @Insert
    void insertCustomers(Customer customer);

    @Query("DELETE from tblCustomer")
    void deleteAllCustomer();

    @Query("DELETE FROM sqlite_sequence WHERE name = :tableName")
    void resetPrimaryKeySequence(String tableName);

    @Query("SELECT * From tblCustomer")
    List<Customer> getAllCustomer();

}
