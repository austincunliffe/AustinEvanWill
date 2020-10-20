package com.example.lifestyleapp.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE uid = :uid LIMIT 1")
    User getUser(long uid);

    @Query("SELECT uid FROM user WHERE name LIKE :name AND " +
            "password LIKE :password LIMIT 1")
    Long getByLogin(String name, String password);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(User user);

    @Update
    public void updateUser(User user);
}
