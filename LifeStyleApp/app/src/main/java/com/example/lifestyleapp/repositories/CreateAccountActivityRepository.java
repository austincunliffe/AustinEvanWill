package com.example.lifestyleapp.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.example.lifestyleapp.MainDrawerActivity;
import com.example.lifestyleapp.models.AppDatabase;
import com.example.lifestyleapp.models.User;
import com.example.lifestyleapp.models.UserDao;

public class CreateAccountActivityRepository {

    private static CreateAccountActivityRepository instance;
    private User user;
    private UserDao mUserDao;
    private Application context;

    CreateAccountActivityRepository(Application application){
        context = application;
        AppDatabase db = AppDatabase.getInstance(application);
        mUserDao = db.userDao();
    }

    public static CreateAccountActivityRepository getInstance(Application application){
        if(instance == null){
            instance = new CreateAccountActivityRepository(application);
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
