package com.alifew.alifeworld.DB.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.alifew.alifeworld.DB.entity.LocalSellProducts;

import java.util.List;


@Dao
public interface LocalSellProductsDao {
    @Insert
    void insertProducts(LocalSellProducts products);

    @Query("SELECT * from tblLocalSellProducts WHERE name LIKE '%' || :key || '%'")
    List<LocalSellProducts> getLocalSellProducts(String key);

    @Query("DELETE from tblLocalSellProducts")
    void deleteAllProducts();

    @Query("DELETE FROM sqlite_sequence WHERE name = :tableName")
    void resetPrimaryKeySequence(String tableName);
}
