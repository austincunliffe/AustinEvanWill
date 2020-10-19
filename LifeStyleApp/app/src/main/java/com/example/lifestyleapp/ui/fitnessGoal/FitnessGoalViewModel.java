package com.example.lifestyleapp.ui.fitnessGoal;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.lifestyleapp.models.User;
import com.example.lifestyleapp.repositories.UserProfileRepository;

import java.math.BigDecimal;
import java.math.MathContext;

public class FitnessGoalViewModel extends AndroidViewModel {

    private MutableLiveData<User> fitnessGoalUser;
    private UserProfileRepository fitnessRepo;

    private String stringTDEE;
    private String stringCaloriesWeightGoal;

    public FitnessGoalViewModel(@NonNull Application application) {
        super(application);
        fitnessRepo = new UserProfileRepository(application);
        fitnessGoalUser = fitnessRepo.getData();
    }

    public LiveData<User> getData(){
        return fitnessGoalUser;
    }

//    Observer<User> fitnessGoalObserver = new Observer<User>() {
//        @Override
//        public void onChanged(User user) {
//            String activeOrSedentary = user.getActiveOrSedentary();
//            // getting the age
//
//            int userWeight = user.getWeight();
//            int userHeight = user.getHeight();
//            String userGender = user.getSex();
//            int weightChange = user.getWeightChange();
//            String goal = user.getGoal();
//
            // getting BMR
//            double BMR = getBMRMenFormula(userAge, userWeight, userHeight, userGender);
//            BigDecimal bdBMR = new BigDecimal(BMR);
//            bdBMR = bdBMR.round(new MathContext(5));
//            double roundedBMR = bdBMR.doubleValue();
//            String stringBMR = Double.toString(roundedBMR);
////
//
//
//
//
//        }
//    };

    public String getBMRString() {
        double BMR = getBMRFormula();
        BigDecimal bdBMR = new BigDecimal(BMR);
        bdBMR = bdBMR.round(new MathContext(5));
        double roundedBMR = bdBMR.doubleValue();
        return Double.toString(roundedBMR);
    }

    public double getBMRFormula(){
        int age = getUserAge();
        String userGender = fitnessGoalUser.getValue().getSex();
        int userWeight = fitnessGoalUser.getValue().getWeight();
        int userHeight = fitnessGoalUser.getValue().getHeight();
        // following the Harris-Benedict BMR formula
        if (userGender.equals("Male")){
            return 66 + (6.2 * (double) userWeight) + (12.7 * (double) userHeight) - (6.76 * (double) age);
        }
        // female
        else {
            return 655.1 + (4.35 * (double) userWeight) + (4.7 * (double) userHeight) - (4.7 * (double) age);
        }
    }

    public int getUserAge(){
        String userDOB = fitnessGoalUser.getValue().getBirthday();
//            // getting the date after the second slash
        userDOB = userDOB.replaceFirst(".*/(\\w+).*", "$1");

//        birthYear = birthYear.replaceFirst(".*/(\\w+).*", "$1");
        int userAge = Integer.parseInt(userDOB);

        if (userAge < 21){
            //20 as in 2020
            userAge = 20 - userAge;
        } else {
            userAge = (100-userAge) + 20;
        }
        return userAge;
    }

    // calculate Total Daily Energy Expenditure
    public double getTDEE(double userBMR, String activeOrSedentary){
        if (activeOrSedentary.equals("Sedentary") ){
            return userBMR * 1.2;
        } else if (activeOrSedentary.equals("Lightly Active")){
            return userBMR * 1.375;
        } else if (activeOrSedentary.equals("Moderately Active")){
            return userBMR * 1.55;
        } else if (activeOrSedentary.equals("Very Active")){
            return userBMR * 1.752;
        } else if (activeOrSedentary.equals("Extra Active")){
            return userBMR * 1.9;
        } else{
            // if value is incorrect default value will be moderately active
            return userBMR * 1.375;
        }
    }

    //getting how many calories user needs to eat to get lose/gain/maintain weight
    public String getCaloriesToEat(double userBMR, int weightChange, String goal, String userGender, String activeOrSedentary){
//        weightChange = picker_weight_change.getValue();

        if (goal.equals("Maintain Weight") || weightChange == 0) {
            //setting np value to 0 if we are maintaining weight
//            picker_weight_change.setValue(0);

            Double tdee = getTDEE(userBMR, activeOrSedentary);
            BigDecimal bdTDEE = new BigDecimal(tdee);
            bdTDEE = bdTDEE.round(new MathContext(5));
            double roundedTDEE = bdTDEE.doubleValue();
            return stringTDEE = Double.toString(roundedTDEE);
        } else if (goal.equals("Lose Weight")){
            //setting a toast if weight loss or gain is greater than 2 pounds per week
            if (weightChange > 2){
//                Toast toast = Toast.makeText(getActivity(),
//                        "Losing more than 2 pounds per week is extreme and can be dangerous. Please use caution.",
//                        Toast.LENGTH_LONG);
//
//                toast.show();
            }
            Double tdee = getTDEE(userBMR, activeOrSedentary);
            //the mayo clinic suggests that you need to add/subtract 500 calories per day to gain/lose
            //1 pound per week
            Double caloriesWeightGoal = tdee - 500*weightChange;

            //setting a toast if daily calorie intake is less than 1200 or 1000
            if (userGender.equals("Male") && caloriesWeightGoal < 1200){
//                Toast toast = Toast.makeText(getActivity(),
//                        "Eating less than 1200 calories/day for men can be dangerous.",
//                        Toast.LENGTH_LONG);
//
//                toast.show();
            }
            else if (userGender.equals("Female") && caloriesWeightGoal < 1000){
//                Toast toast = Toast.makeText(getActivity(),
//                        "Eating less than 1000 calories/day for women can be dangerous.",
//                        Toast.LENGTH_LONG);
//
//                toast.show();
            }

            BigDecimal bdCaloriesWeightGoal = new BigDecimal(caloriesWeightGoal);
            bdCaloriesWeightGoal = bdCaloriesWeightGoal.round(new MathContext(5));
            double roundedCaloriesWeightGoal = bdCaloriesWeightGoal.doubleValue();
            return stringCaloriesWeightGoal = Double.toString(roundedCaloriesWeightGoal);
        } else if (goal.equals("Gain Weight")){
            //setting a toast if weight loss or gain is greater than 2 pounds per week
            if (weightChange > 2){
//                Toast toast = Toast.makeText(getActivity(),
//                        "Gaining more than 2 pounds per week is extreme and can be dangerous. Please use caution.",
//                        Toast.LENGTH_LONG);
//
//                toast.show();
            }
            Double tdee = getTDEE(userBMR, activeOrSedentary);
            //the mayo clinic suggests that you need to add/subtract 500 calories per day to gain/lose
            //1 pound per week
            Double caloriesWeightGoal = tdee + 500*weightChange;
            BigDecimal bdCaloriesWeightGoal = new BigDecimal(caloriesWeightGoal);
            bdCaloriesWeightGoal = bdCaloriesWeightGoal.round(new MathContext(5));
            double roundedCaloriesWeightGoal = bdCaloriesWeightGoal.doubleValue();
            return stringCaloriesWeightGoal = Double.toString(roundedCaloriesWeightGoal);
        } else {
            return null;
        }
    }


}
