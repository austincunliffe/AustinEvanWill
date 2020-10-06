package com.example.lifestyleapp.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE name LIKE :name AND " +
            "password LIKE :password LIMIT 1")
    User getByLogin(String name, String password);

    @Query("SELECT * FROM ")

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    public void updateUser(User user);
}
