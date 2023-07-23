package com.alifew.alife.DB;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {


    @Query("SELECT * From tblProducts GROUP BY product_id")
    List<Products> getProductsList();

    @Query("SELECT * From tblProducts WHERE print_check = '1'")
    List<Products> getMarkedProductList();

    @Query("SELECT * from tblProducts where  name  LIKE '%' || :key || '%' GROUP BY product_id")
    List<Products> getSearchedProductsList(String key);

    @Insert
    void insertProducts(Products products);

    @Query("DELETE From tblProducts")
    void clearProducts();

    @Query("UPDATE tblProducts  SET print_check = :printCheck WHERE id =:id")
    void updatePrintCheck(String id, String printCheck);

    @Query("SELECT * From tblProducts WHERE product_id= :product_id")
    List<Products> getProductsTypes(String product_id);

    @Query("SELECT COUNT(*) from tblProducts WHERE product_id = :product_id")
    int getProductCount(String product_id);

    @Query("SELECT * from tblProducts WHERE barcode = :barcode GROUP BY product_id")
    List<Products> getProductsByBarCode(String barcode);
}
