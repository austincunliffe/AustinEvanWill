package com.example.lifestyleapp.ui.bmi;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lifestyleapp.models.BMI;
import com.example.lifestyleapp.repositories.BMIRepository;

import java.math.BigDecimal;
import java.math.MathContext;

public class BMIViewModel extends ViewModel {

    private MutableLiveData<BMI> mBMI;
    private BMIRepository BMIRepo;


//    public void init(){
//        if(mBMI != null){
//            return;
//        }
//        BMIRepo = BMIRepository.getInstance();
//        mBMI = BMIRepo.getBMI();
//    }

    BMIViewModel(){
        BMIRepo = BMIRepository.getInstance();
    }

    public LiveData<BMI> getBMI(){
        return mBMI;
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
//    void loadUserData() {
//
//        SharedPreferences pref = application.getSharedPreferences("com.example.lifestyleapp",
//                Context.MODE_PRIVATE);
//
//        int mHeightInt = pref.getInt("height", 0);
//        int mWeightInt = pref.getInt("weight", 0);
//
//        double userBMI = calculateBMI(mWeightInt, mHeightInt);
//
//        userBMIString = formatBMI(userBMI);
//        System.out.println(userBMIString);
//    }
//
//
//    public static double calculateBMI(int userWeight, int userHeight) {
//        // this is the formula for calculating BMI in imperial units
//        double bmi = (703 * (userWeight / Math.pow(userHeight, 2)));
//        return bmi;
//    }
//
//    public String formatBMI(Double userBMI) {
//        //rounding the bmi to one 3 significant digits
//        BigDecimal bd = new BigDecimal(userBMI);
//        bd = bd.round(new MathContext(3));
//        double roundedUserBMI = bd.doubleValue();
//        return Double.toString(roundedUserBMI);
//    }
//
//    public String getBMI() {
//        return userBMIString;
//    }
}


