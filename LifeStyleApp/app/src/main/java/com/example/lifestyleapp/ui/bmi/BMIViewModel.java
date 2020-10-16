package com.example.lifestyleapp.ui.bmi;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.lifestyleapp.models.BMI;
import com.example.lifestyleapp.repositories.BMIRepository;
import com.example.lifestyleapp.repositories.WeatherRepository;
import com.example.lifestyleapp.ui.weather.Weather;

import java.math.BigDecimal;
import java.math.MathContext;


public class BMIViewModel extends AndroidViewModel {

    private BMIRepository BMIRepo;
    private MutableLiveData<String> userBMIString;

    public BMIViewModel(@NonNull Application application) {
        super(application);

        userBMIString = new MutableLiveData<>();
        BMIRepo = new BMIRepository(application);
        BMIRepo.getBMI().observeForever(bmiObserver);
    }

    public LiveData<String> getBMI() {

        return userBMIString;
    }

    Observer<BMI> bmiObserver = new Observer<BMI>() {
        @Override
        public void onChanged(BMI bmi) {
            System.out.println(bmi.getHeight());
            System.out.println(bmi.getWeight());
            double userBMI = calculateBMI(bmi.getWeight(), bmi.getHeight());
            if (userBMI == 0) {
                userBMIString.setValue("--");
            } else {
                userBMIString.setValue(formatBMI(userBMI));
            }
        }
    };


    public static double calculateBMI(int userWeight, int userHeight) {
        // this is the formula for calculating BMI in imperial units
        double bmi;
        if (userHeight == 0 || userWeight == 0) {
            bmi = 0;
        } else {
            bmi = (703 * (userWeight / Math.pow(userHeight, 2)));
        }
        System.out.println("bmi " + bmi);
        return bmi;
    }

    public String formatBMI(Double userBMI) {
        //rounding the bmi to one 3 significant digits
        BigDecimal bd = new BigDecimal(userBMI);
        bd = bd.round(new MathContext(3));
        double roundedUserBMI = bd.doubleValue();
        return Double.toString(roundedUserBMI);
    }
}


