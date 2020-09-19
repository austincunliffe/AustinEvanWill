package com.example.lifestyleapp.ui.fitnessGoal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.lifestyleapp.R;
import com.example.lifestyleapp.ui.userProfile.UserProfileViewModel;

import java.util.prefs.Preferences;

public class FitnessGoal extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner spin_goal;
    Spinner spin_sedentary_active;
    Button bt_setGoal;
    NumberPicker picker_weight_change;
    String goal;
    String activeOrSedentary;
    String userDOB;
    String userGender;
    int weightChange;
    int userWeight;
    int userHeight;
    int testAge = 26;
    double BMR;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_fitness_goal, container, false);
        //finding the spinners
        spin_goal = root.findViewById(R.id.spinner_goal);
        spin_sedentary_active = root.findViewById(R.id.spinner_active);
        ArrayAdapter<CharSequence> goal_adapter = ArrayAdapter.createFromResource(root.getContext(),
                R.array.goal_array, R.layout.spinner_item);
        //setting the active adapter
        ArrayAdapter<CharSequence> active_adapter = ArrayAdapter.createFromResource(root.getContext(),
                R.array.active_array, R.layout.spinner_item);
        goal_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        active_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        //setting up spinners
        spin_goal.setAdapter(goal_adapter);
        spin_sedentary_active.setAdapter(active_adapter);
        spin_goal.setOnItemSelectedListener(this);
        spin_sedentary_active.setOnItemSelectedListener(this);

        //setting up picker
        picker_weight_change = root.findViewById(R.id.np_weight_change);
        initialWeightChangePicker();

        //setting up button
        bt_setGoal = root.findViewById(R.id.bt_setGoal);
        bt_setGoal.setOnClickListener(this);
        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void initialWeightChangePicker() {
        picker_weight_change.setMaxValue(5);
        picker_weight_change.setMinValue(0);
        picker_weight_change.setValue(1);
    }

    public double getBMRMenFormula(int age, int userWeight, int userHeight, String gender){
        // following the Harris-Benedict BMR formula
        if (gender=="Male"){
            return 66 + (6.2 * (double) userWeight) + (12.7 * (double) userHeight) - (6.76 * (double) age);
        }
        // female
        else {
            return 655.1 + (4.35 * (double) userWeight) + (4.7 * (double) userHeight) - (4.7 * (double) age);
        }
    }

    public int getUserAge(String birthYear){
        birthYear = birthYear.replaceFirst(".*/(\\w+).*", "$1");
        int userAge = Integer.parseInt(birthYear);

        if (userAge < 21){
            //20 as in 2020
            userAge = 20 - userAge;
        } else {
            userAge = (100-userAge) + 20;
        }
        return userAge;
    }

    @SuppressLint("CommitPrefEdits") //Not sure what this @Supress is
    @Override
    public void onClick(View v) {
        goal = spin_goal.getSelectedItem().toString();
        activeOrSedentary = spin_sedentary_active.getSelectedItem().toString();
        SharedPreferences prefs = v.getContext().getSharedPreferences(
                "com.example.lifestyleapp", Context.MODE_PRIVATE);
        prefs.edit().putString("goal", goal);
        prefs.getString("goal",goal);
        prefs.edit().putString("activeOrSedentary", activeOrSedentary);

        // getting the age
        userDOB = prefs.getString("dob", null);
        // getting the date after the second slash
        userDOB = userDOB.replaceFirst(".*/(\\w+).*", "$1");
        int userAge = getUserAge(userDOB);

        //getting the weight
        userWeight = prefs.getInt("weight", 0);

        //getting the height
        userHeight = prefs.getInt("height", 0);

        //getting gender
        userGender = prefs.getString("sex", null);

        //getting BMR
        BMR = getBMRMenFormula(userAge, userWeight, userHeight, userGender);



        //testing
        weightChange = picker_weight_change.getValue();
        System.out.println("Goal Saved");
        System.out.println(goal);
        System.out.println("test");
        System.out.println(userAge);
        System.out.println(userWeight);
        System.out.println(userHeight);
        System.out.println(userGender);
        System.out.println(BMR);
        System.out.println(activeOrSedentary);
        System.out.println(weightChange);
    }
}