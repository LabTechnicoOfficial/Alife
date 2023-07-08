package com.ALife.alife.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.google.android.gms.tasks.Task;

@Database(entities = {Products.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}
