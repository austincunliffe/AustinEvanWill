package com.example.lifestyleapp.ui.stepCount;

import android.app.Application;
import android.content.Context;
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

import com.example.lifestyleapp.repositories.StepCountRepository;

public class StepCountViewModel extends AndroidViewModel implements SensorEventListener {

    StepCountRepository repository;
    Sensor sensor;
    long stepCount;
    SensorManager sensorManager;
    MutableLiveData<Long> steps = new MutableLiveData<>();


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    StepCountViewModel(@NonNull Application application) {
        super(application);
        stepCount = 0;
        sensorManager = (SensorManager) this.getApplication().getSystemService(Context.SENSOR_SERVICE);
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
//        repository = new StepCountRepository();
//         repository.getData().observe();
    }

    public MutableLiveData<Long> getData() {
        return steps;
    }

    public MutableLiveData<Long> setData() {
        return steps;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void registerSensor() {
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, Sensor.TYPE_STEP_COUNTER);
        } else {
            System.out.println("NULL SENSOR");
        }
    }

    public void unregisterSensor() {
        if (sensor != null) {
            sensorManager.unregisterListener(this);
        } else {
            System.out.println("NULL SENSOR");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        System.out.println(event.timestamp);
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            System.out.println(event.values[0]);
            stepCount++;
            steps.setValue(stepCount);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Toast.makeText(getApplication().getApplicationContext(), "Accuracy changed!", Toast.LENGTH_SHORT).show();
    }

}