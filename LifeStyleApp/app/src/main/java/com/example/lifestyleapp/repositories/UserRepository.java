package com.example.lifestyleapp.repositories;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.lifestyleapp.models.AppDatabase;
import com.example.lifestyleapp.models.BMI;
import com.example.lifestyleapp.models.User;
import com.example.lifestyleapp.models.UserDao;

public class UserRepository {

    private static UserRepository instance;
    private User user;
    private UserDao mUserDao;

    UserRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        mUserDao = db.userDao();
    }

    public static UserRepository getInstance(Application application){
        if(instance == null){
            instance = new UserRepository(application);
        }
        return instance;
    }

    public MutableLiveData<User> loginUser(String name, String password){
        // Get info from DB and set the member variable data above to it.
        user = mUserDao.getByLogin(name, password);

        MutableLiveData<User> userData = new MutableLiveData<>();
        userData.setValue(user);

        return userData;
    }

    public void createUser(User user){
        mUserDao.insert(user);

        MutableLiveData<User> userData = new MutableLiveData<>();
        userData.setValue(user);
    }
}
