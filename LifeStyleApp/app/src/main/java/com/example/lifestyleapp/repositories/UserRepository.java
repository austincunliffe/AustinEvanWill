package com.example.lifestyleapp.repositories;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.lifestyleapp.MainDrawerActivity;
import com.example.lifestyleapp.models.AppDatabase;
import com.example.lifestyleapp.models.User;
import com.example.lifestyleapp.models.UserDao;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserRepository {

    private static UserRepository instance;
    private User user;
    private UserDao mUserDao;
    private Application context;

    UserRepository(Application application){
        context = application;
        AppDatabase db = AppDatabase.getInstance(application);
        mUserDao = db.userDao();
    }

    public static UserRepository getInstance(Application application){
        if(instance == null){
            instance = new UserRepository(application);
        }
        return instance;
    }

    public void insert(User user){
        new insertAsyncTask(mUserDao).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User,Void,Void> {
        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(User... users) {
            MainDrawerActivity.userPrimaryKey = mAsyncTaskDao.insert(users[0]);
            return null;
        }
    }
}
