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

    private MutableLiveData<BMI> mBMI;
    private BMIRepository BMIRepo;
    private MutableLiveData<Integer> height;
    private MutableLiveData<Integer> weight;
    private MutableLiveData<String> userBMIString;



//    public void init(){
//        if(mBMI != null){
//            return;
//        }
//        BMIRepo = BMIRepository.getInstance();
//        mBMI = BMIRepo.getBMI();
//    }

    BMIViewModel(@NonNull Application application){
        super(application);
        BMIRepo = new BMIRepository(application);
        loadUserData(application);
//        height = BMIRepo.getHeight();
//        weight = BMIRepo.getWeight();
    }

    public LiveData<String> getBMI(){

        return userBMIString;
    }

//    private Application application;
//
//    String userBMIString;
//
//
//    BMIViewModel(Application application) {
//        this.application = application;
//        loadUserData();
//    }
//
    void loadUserData(Application application) {
        BMIRepo = new BMIRepository(application);
//        height = BMIRepo.getHeight();
//        weight = BMIRepo.getWeight();
        BMIRepo.getBMI().observeForever(bmiObserver);
//
//        double userBMI = calculateBMI(weight.getValue(), height.getValue());
//
//        userBMIString = formatBMI(userBMI);
//        System.out.println(userBMIString);
    }

    Observer<BMI> bmiObserver = new Observer<BMI>() {
        @Override
        public void onChanged(BMI bmi) {

            double userBMI = calculateBMI(bmi.getWeight(), bmi.getHeight());
            userBMIString.setValue( formatBMI(userBMI));
        }
    };


    public static double calculateBMI(int userWeight, int userHeight) {
        // this is the formula for calculating BMI in imperial units
        double bmi = (703 * (userWeight / Math.pow(userHeight, 2)));
        return bmi;
    }

    public String formatBMI(Double userBMI) {
        //rounding the bmi to one 3 significant digits
        BigDecimal bd = new BigDecimal(userBMI);
        bd = bd.round(new MathContext(3));
        double roundedUserBMI = bd.doubleValue();
        return Double.toString(roundedUserBMI);
    }

//    public String getBMI() {
//        return userBMIString;
//    }
}


