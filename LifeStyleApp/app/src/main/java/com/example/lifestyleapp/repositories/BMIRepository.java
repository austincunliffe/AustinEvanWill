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

    private MutableLiveData<User> mUser = new MutableLiveData<>();
    private static UserDao mUserDao;
    private BMI bmi;
    private MutableLiveData<BMI> BMIData;
    private MutableLiveData<Integer> height;
    private MutableLiveData<Integer> weight;


    public BMIRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mUserDao = db.userDao();
        loadData();
    }

    void loadData() {
        new BMIRepository.getUserAsyncTask(this).execute(MainDrawerActivity.userPrimaryKey);
    }

    public MutableLiveData<BMI> getBMI() {
        // Get info from DB and set the member variable data above to it.

        MutableLiveData<BMI> BMIdata = new MutableLiveData<>();
        BMIdata.setValue(bmi);
        return BMIdata;
    }

//
//    public MutableLiveData<Integer> getHeight() {
//        return weight;
//    }
//
//    public MutableLiveData<Integer> getWeight() {
//        return height;
//    }

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
            localWRvar.bmi.setHeight(returnedUser.getHeight());
            localWRvar.bmi.setWeight(returnedUser.getWeight());
//            localWRvar.height.setValue(returnedUser.getHeight());
//            localWRvar.weight.setValue(returnedUser.getWeight());
        }
    }
}
