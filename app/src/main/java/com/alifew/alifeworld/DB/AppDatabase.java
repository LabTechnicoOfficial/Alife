package com.alifew.alifeworld.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.alifew.alifeworld.DB.dao.CustomerDao;
import com.alifew.alifeworld.DB.dao.LocalSellProductsDao;
import com.alifew.alifeworld.DB.dao.ProductDao;
import com.alifew.alifeworld.DB.entity.Customer;
import com.alifew.alifeworld.DB.entity.LocalSellProducts;
import com.alifew.alifeworld.DB.entity.Products;
import com.alifew.alifeworld.Utils.Constants;

import io.reactivex.rxjava3.annotations.NonNull;

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
