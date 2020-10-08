package com.example.lifestyleapp.repositories;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.lifestyleapp.MainDrawerActivity;
import com.example.lifestyleapp.models.AppDatabase;
import com.example.lifestyleapp.models.User;
import com.example.lifestyleapp.models.UserDao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;

public class UserProfileRepository {

    private MutableLiveData<User> mUser = new MutableLiveData<User>();
    private static UserDao mUserDao;
    private static Bitmap profilePic;

    public UserProfileRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        mUserDao = db.userDao();

        loadData();
    }

    public MutableLiveData<User> getData() {return mUser;}

    private void loadData() {
        new getUserAsyncTask(this).execute(MainDrawerActivity.userPrimaryKey);
    }

    private static class getUserAsyncTask extends AsyncTask<Long, Void, User>{
        private WeakReference<UserProfileRepository> mRepoWReference;

        getUserAsyncTask(UserProfileRepository repo)
        {
            mRepoWReference = new WeakReference<UserProfileRepository>(repo);
        }

        @Override
        protected User doInBackground(Long... longs) {
            return mUserDao.getUser(longs[0]);
        }

        @Override
        protected void onPostExecute(User returnedUser){
            UserProfileRepository localWRvar = mRepoWReference.get();
            new getProfilePicAsyncTask().execute(returnedUser.getProfilePicFP());
            localWRvar.mUser.setValue(returnedUser);
        }
    }

    public void updateUser(User user){
        mUserDao.updateUser(user);
    }

    public Bitmap getProfilePic(){ return profilePic; }

    private static class getProfilePicAsyncTask extends AsyncTask<String, Void, Bitmap>{
//        private WeakReference<UserProfileRepository> mRepoWReference;
//
//        getProfilePicAsyncTask(Bitmap pic)
//        {
//            mRepoWReference = new WeakReference<UserProfileRepository>(pic);
//        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap picBit = null;
            try {
                File f = new File(strings[0], "profile.jpg");
                picBit = BitmapFactory.decodeStream(new FileInputStream(f));
//            mIvPic = this.getActivity().findViewById(R.id.imageView_profile);
//            mIvPic.setImageBitmap(b);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            return picBit;
        }

        @Override
        protected void onPostExecute(Bitmap returnedPic){
//            UserProfileRepository localWRvar = mRepoWReference.get();
            profilePic = returnedPic;
        }
    }
}
