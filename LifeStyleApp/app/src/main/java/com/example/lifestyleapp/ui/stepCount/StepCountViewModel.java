package com.example.lifestyleapp.ui.stepCount;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.SystemClock;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.lifestyleapp.models.StepCount;
import com.example.lifestyleapp.repositories.StepCountRepository;

import java.time.LocalDateTime;

public class StepCountViewModel extends AndroidViewModel implements SensorEventListener {

    StepCountRepository repository;

    Sensor linearAccelerometer;
    double mThreshold = 2.0;
    Sensor stepCounterSensor;
    long lastTime;
    private double last_x, last_y, last_z;
    private double now_x, now_y, now_z;
    private boolean mNotFirstTime;

    long stepCount;
    long sensorHistory;
    SensorManager sensorManager;
    MutableLiveData<Long> steps = new MutableLiveData<>();
    SharedPreferences preferences;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    StepCountViewModel(@NonNull Application application) {
        super(application);

        preferences = getApplication().getSharedPreferences("com.example.lifestyleapp",
                Context.MODE_PRIVATE);
        long sensorHistory = preferences.getLong("sensorHistory", 0);
        if (sensorHistory == 0) {
            stepCount = 0;
        } else {
            stepCount = preferences.getLong("initialCount", 0);
        }
        steps.setValue(stepCount);
        sensorManager = (SensorManager) this.getApplication().getSystemService(Context.SENSOR_SERVICE);
        this.stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        this.linearAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        repository = new StepCountRepository(application);
    }

    public MutableLiveData<Long> getData() {
        return steps;
    }

    @SuppressLint("CommitPrefEdits")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void registerSensor() {
        if (!preferences.getBoolean("registered", false)) {
            System.out.println("Swipe Left to right");
            if (stepCounterSensor != null) {
                sensorManager.registerListener(this, stepCounterSensor, Sensor.TYPE_STEP_COUNTER);
                preferences.edit().putBoolean("registered", true).apply();
                System.out.println(preferences.getBoolean("registered", false));
            } else {
                System.out.println("NULL SENSOR");
            }
        }
        Toast.makeText(getApplication(), "Step Counter is active.", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("CommitPrefEdits")
    public void storeCurrentSteps() {
        preferences.edit().putLong("initialCount", stepCount).apply();
    }

    @SuppressLint("CommitPrefEdits")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void storeData() {
        unregisterSensor();
        StepCount currentCount = new StepCount();
        String currentTime = LocalDateTime.now().toString();
        System.out.println(currentTime);
        System.out.println(stepCount);
        currentCount.setTime(currentTime);
        currentCount.setCount(this.stepCount);
        repository.setData(currentCount);
        preferences.edit().remove("sensorHistory").apply();
        preferences.edit().remove("initialCount").apply();
        preferences.edit().remove("registered").apply();
    }

    void unregisterSensor() {
        if (stepCounterSensor != null) {
            sensorManager.unregisterListener(this);
        } else {
            System.out.println("NULL SENSOR");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("CommitPrefEdits")
    @Override
    public void onSensorChanged(SensorEvent event) {
        System.out.println(event.timestamp);
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {

            long sensorCount = (long) event.values[0];
            if (sensorHistory == 0) {
                sensorHistory = sensorCount;
                preferences.edit().putLong("countHistory", sensorHistory).apply();
            }

            stepCount = sensorCount - sensorHistory;

            this.steps.setValue(stepCount);
        } else if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            System.out.println("Shake");

            //Get the acceleration rates along the y and z axes
            now_x = event.values[0];
            now_y = event.values[1];
            now_z = event.values[2];

            long currentTime = SystemClock.currentThreadTimeMillis();
            if (mNotFirstTime && currentTime - lastTime < 2000) {
                double dx = Math.abs(last_x - now_x);
                double dy = Math.abs(last_y - now_y);
                double dz = Math.abs(last_z - now_z);

                //Check if the values of acceleration have changed on any pair of axes
                if ((dx > mThreshold && dy > mThreshold) ||
                        (dx > mThreshold && dz > mThreshold) ||
                        (dy > mThreshold && dz > mThreshold)) {
                    registerSensor();
                }
            }
            last_x = now_x;
            last_y = now_y;
            last_z = now_z;
            lastTime = SystemClock.currentThreadTimeMillis();
            mNotFirstTime = true;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Toast.makeText(getApplication().getApplicationContext(), "Accuracy changed!", Toast.LENGTH_SHORT).show();
    }

}