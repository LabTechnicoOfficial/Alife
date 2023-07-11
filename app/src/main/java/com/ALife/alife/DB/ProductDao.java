package com.ALife.alife.DB;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {


    @Query("SELECT * From tblProducts")
    List<Products> getProductsList();

    @Query("SELECT * From tblProducts WHERE print_check = '1'")
    List<Products> getMarkedProductList();

    @Query("SELECT * from tblProducts where  name  LIKE '%' || :key || '%'")
    List<Products> getSearchedProductsList(String key);

    @Insert
    void insertProducts(Products products);

    @Query("DELETE From tblProducts")
    void clearProducts();

    @Query("UPDATE tblProducts  SET print_check = :printCheck WHERE product_id =:product_id")
    void updatePrintCheck(String product_id, String printCheck);
}
