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

//    public MutableLiveData<User> loginUser(String name, String password){
//        // Get info from DB and set the member variable data above to it.
//        user = mUserDao.getByLogin(name, password);
//
//        MutableLiveData<User> userData = new MutableLiveData<>();
//        userData.setValue(user);
//
//        return userData;
//    }

//    public void createUser(final User user){
//
//        final long[] primary_key = new long[1];
//        Executor myExecutor = Executors.newSingleThreadExecutor();
//        myExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                primary_key[0] = mUserDao.insert(user);
//            }
//        });
//
//        MutableLiveData<User> userData = new MutableLiveData<>();
//        userData.setValue(user);
//
//        // Put the current user primary key into shared preferences so we can use it to query later.
//        SharedPreferences prefs = context.getSharedPreferences(
//                "com.example.lifestyleapp", Context.MODE_PRIVATE);
//        System.out.println("PKUser = " + primary_key[0]);
//        prefs.edit().putLong("pk_user", primary_key[0]).apply();
//    }

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
