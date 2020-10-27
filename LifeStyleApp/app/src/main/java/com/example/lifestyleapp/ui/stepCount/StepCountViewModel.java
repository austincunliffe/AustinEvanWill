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
    Sensor sensor;
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
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
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
            if (sensor != null) {
            sensorManager.registerListener(this, sensor, Sensor.TYPE_STEP_COUNTER);
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
    public void unregisterSensor() {
        if (sensor != null) {
        sensorManager.unregisterListener(this);

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

        } else {
            System.out.println("NULL SENSOR");
        }
    }


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
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Toast.makeText(getApplication().getApplicationContext(), "Accuracy changed!", Toast.LENGTH_SHORT).show();
    }

}