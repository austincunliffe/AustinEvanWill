package com.example.lifestyleapp;

import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

public class LifestyleApp extends android.app.Application {
    public void onCreate() {
        super.onCreate();

        try {
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());

            Log.i("Lifestyle App", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("Lifestyle App", "Could not initialize Amplify", error);
        }
    }
}
