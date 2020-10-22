package com.example.lifestyleapp.ui.stepCount;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;

import com.example.lifestyleapp.repositories.StepCountRepository;

import static androidx.core.content.ContextCompat.getSystemService;

public class StepCountViewModel extends AndroidViewModel implements SensorEventListener {

    StepCountRepository repository;
    public static final int TYPE_STEP_COUNTER = 0;
    Sensor sensor;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    StepCountViewModel(@NonNull Application application) {
        super(application);
        SensorManager sensorManager = (SensorManager) application.getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        repository = new StepCountRepository();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}