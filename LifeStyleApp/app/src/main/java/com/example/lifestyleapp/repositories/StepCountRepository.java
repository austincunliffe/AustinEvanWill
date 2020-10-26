package com.example.lifestyleapp.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.example.lifestyleapp.models.AppDatabase;
import com.example.lifestyleapp.models.StepCount;
import com.example.lifestyleapp.models.StepDao;


import java.lang.ref.WeakReference;

public class StepCountRepository {

    private static StepDao mStepDao;

    public StepCountRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mStepDao = db.stepDao();
    }

    public void setData(StepCount stepCount) {
        new setStepCountAsyncTask(this).execute(stepCount);
    }

    private static class setStepCountAsyncTask extends AsyncTask<StepCount, Void, Boolean> {
        private WeakReference<StepCountRepository> mRepoWReference;

        setStepCountAsyncTask(StepCountRepository repo) {
            mRepoWReference = new WeakReference<StepCountRepository>(repo);
        }

        @Override
        protected Boolean doInBackground(StepCount... stepCounts) {
            try {
                mStepDao.insert(stepCounts[0]);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }

}
