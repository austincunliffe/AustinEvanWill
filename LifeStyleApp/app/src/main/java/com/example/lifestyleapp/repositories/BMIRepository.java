package com.example.lifestyleapp.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.lifestyleapp.models.BMI;

public class BMIRepository {

    private static BMIRepository instance;
    private BMI data;

    public static BMIRepository getInstance(){
        if(instance == null){
            instance = new BMIRepository();
        }
        return instance;
    }

    public MutableLiveData<BMI> getBMI(){
        // Get info from DB and set the member variable data above to it.

        MutableLiveData<BMI> BMIdata = new MutableLiveData<>();
        BMIdata.setValue(data);

        return BMIdata;
    }
}
