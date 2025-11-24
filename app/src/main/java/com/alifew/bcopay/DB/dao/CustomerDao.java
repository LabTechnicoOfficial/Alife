package com.alifew.bcopay.DB.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.alifew.bcopay.DB.entity.Customer;

import java.util.List;

@Dao
public interface CustomerDao {
    @Insert
    void insertCustomers(Customer customer);

    @Query("DELETE from tblCustomer")
    void deleteAllCustomer();

    @Query("DELETE FROM sqlite_sequence WHERE name = :tableName")
    void resetPrimaryKeySequence(String tableName);

    @Query("SELECT * From tblCustomer WHERE phone LIKE '%' || :key || '%' OR name LIKE '%' || :key || '%'")
    List<Customer> getAllCustomer(String key);

}
