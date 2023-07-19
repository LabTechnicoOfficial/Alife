package com.alifew.alife.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Products.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}
