package com.alifew.bcopay.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.alifew.bcopay.DB.dao.CustomerDao;
import com.alifew.bcopay.DB.dao.LocalSellProductsDao;
import com.alifew.bcopay.DB.dao.ProductDao;
import com.alifew.bcopay.DB.entity.Customer;
import com.alifew.bcopay.DB.entity.LocalSellProducts;
import com.alifew.bcopay.DB.entity.Products;
import com.alifew.bcopay.Utils.Constants;

@Database(entities = {Products.class, Customer.class, LocalSellProducts.class}, version = Constants.DB_VERSION)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();

    public abstract CustomerDao customerDao();

    public abstract LocalSellProductsDao localSellProductsDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    Constants.DB_NAME
                            )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            //.addMigrations(MIGRATION_1_2)
                            .build();
                }
            }

        }

        return INSTANCE;
    }
}
