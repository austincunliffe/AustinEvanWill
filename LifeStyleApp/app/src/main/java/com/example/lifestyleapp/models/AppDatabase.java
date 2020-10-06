package com.example.lifestyleapp.models;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public static AppDatabase getInstance(Application application) {
        AppDatabase db = Room.databaseBuilder(application,
                AppDatabase.class, "lifestyle-db").build();
        return db;
    }
}