package com.example.lifestyleapp.repositories;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.lifestyleapp.MainDrawerActivity;
import com.example.lifestyleapp.models.AppDatabase;
import com.example.lifestyleapp.models.BMI;
import com.example.lifestyleapp.models.User;
import com.example.lifestyleapp.models.UserDao;
import com.example.lifestyleapp.ui.weather.Weather;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class BMIRepository {

    private MutableLiveData<User> mUser;
    private static UserDao mUserDao;
    private BMI bmi;
    private MutableLiveData<BMI> BMIData;


    public BMIRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mUserDao = db.userDao();
        loadData();
    }

    void loadData() {
        BMIData = new MutableLiveData<>();
        mUser = new MutableLiveData<>();
        bmi = new BMI(0, 0);
        BMIData.setValue(bmi);
        new getUserAsyncTask(this).execute(MainDrawerActivity.userPrimaryKey);
    }

    public MutableLiveData<BMI> getBMI() {
        return BMIData;
    }

    private static class getUserAsyncTask extends AsyncTask<Long, Void, User> {
        private WeakReference<BMIRepository> mRepoWReference;

        getUserAsyncTask(BMIRepository repo) {
            mRepoWReference = new WeakReference<BMIRepository>(repo);
        }

        @Override
        protected User doInBackground(Long... longs) {
            return mUserDao.getUser(longs[0]);
        }

        @SuppressLint("StaticFieldLeak")
        @Override
        protected void onPostExecute(User returnedUser) {
            BMIRepository localWRvar = mRepoWReference.get();
            localWRvar.mUser.setValue(returnedUser);
            System.out.println(returnedUser.getHeight());
            localWRvar.BMIData.setValue(new BMI(returnedUser.getHeight(), returnedUser.getWeight()));
        }
    }
}
