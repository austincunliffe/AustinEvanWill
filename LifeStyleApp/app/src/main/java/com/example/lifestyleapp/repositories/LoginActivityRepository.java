package com.example.lifestyleapp.repositories;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lifestyleapp.MainDrawerActivity;
import com.example.lifestyleapp.models.AppDatabase;
import com.example.lifestyleapp.models.User;
import com.example.lifestyleapp.models.UserDao;

import java.lang.ref.WeakReference;

public class LoginActivityRepository {

    private static UserDao mUserDao;
    private MutableLiveData<Boolean> userVerified = new MutableLiveData<>();

    public LoginActivityRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        mUserDao = db.userDao();
    }

    public MutableLiveData<Boolean> getData(String name, String password) {
        verifyLogin(name, password);
        return userVerified;
    }

    private void verifyLogin(String name, String password) {
        String[] credentials = new String[] {name, password};
        new verifyLoginAsyncTask(this).execute(credentials);
    }

    private static class verifyLoginAsyncTask extends AsyncTask<String, Void, Long> {
        private WeakReference<LoginActivityRepository> mRepoWReference;

        verifyLoginAsyncTask(LoginActivityRepository repo)
        {
            mRepoWReference = new WeakReference<LoginActivityRepository>(repo);
        }

        @Override
        protected Long doInBackground(String... strings) {
            return mUserDao.getByLogin(strings[0], strings[1]);

        }

        @Override
        protected void onPostExecute(Long uid){
            LoginActivityRepository localWRvar = mRepoWReference.get();
            if(uid != null){
                System.out.println("USER COUNT: " + uid);
                localWRvar.userVerified.setValue(true);
                MainDrawerActivity.userPrimaryKey = uid;
            } else {
                localWRvar.userVerified.setValue(false);
            }
        }
    }
}
