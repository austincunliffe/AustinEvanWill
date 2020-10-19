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
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;


import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.lifestyleapp.R;
import com.example.lifestyleapp.models.User;
import com.example.lifestyleapp.ui.userProfile.UserProfileViewModel;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.prefs.Preferences;

public class FitnessGoal extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private FitnessGoalViewModel FitnessGoalViewModel;
    private User currentUser;


    Spinner spin_goal;
    Spinner spin_sedentary_active;
    Button bt_setGoal;
    NumberPicker picker_weight_change;
    TextView tvBMR;
    TextView tvCalorieGoal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_fitness_goal, container, false);
        //finding the spinners
        spin_goal = root.findViewById(R.id.spinner_goal);
        spin_sedentary_active = root.findViewById(R.id.spinner_active);

        //setting up picker
        picker_weight_change = root.findViewById(R.id.np_weight_change);
        initialWeightChangePicker();

        //setting up button
        bt_setGoal = root.findViewById(R.id.bt_setGoal);
        bt_setGoal.setOnClickListener(this);

        //setting up textviewBMR
        tvBMR = root.findViewById(R.id.bmr_big);

        //setting up textviewCalorie
        tvCalorieGoal = root.findViewById(R.id.tv_calorie_intake);

        // Create the view model
        FitnessGoalViewModel = ViewModelProviders.of(this).get(FitnessGoalViewModel.class);

        // Set the observer
        FitnessGoalViewModel.getData().observe(getViewLifecycleOwner(), userObserver);

        return root;
    }

    //create an observer that watches the LiveData<User> object
   final Observer<User> userObserver = new Observer<User>() {
        @Override
        public void onChanged(User user) {
            System.out.println("WITHIN ONCHANGED");
            if(user!=null) {
                System.out.println("USER NOT NULL");
                currentUser = user;
                initializeSpinners(user);
                initialWeightChangePicker();

            }
            System.out.println("USER IS NULL");
        }
    };

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initializeSpinners(User user) {
        ArrayAdapter<CharSequence> goal_adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.goal_array, R.layout.spinner_item);
        goal_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spin_goal.setAdapter(goal_adapter);
        spin_goal.setOnItemSelectedListener(this);



        //setting the active adapter
        ArrayAdapter<CharSequence> active_adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.active_array, R.layout.spinner_item);
        active_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spin_sedentary_active.setAdapter(active_adapter);
        spin_sedentary_active.setOnItemSelectedListener(this);
    }

    public void initialWeightChangePicker() {
        picker_weight_change.setMaxValue(5);
        picker_weight_change.setMinValue(0);
        picker_weight_change.setValue(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @SuppressLint({"CommitPrefEdits", "SetTextI18n"}) //Not sure what this @Supress is
    @Override
    public void onClick(View v) {

        if (spin_goal.getSelectedItem().toString().equals("Maintain Weight") || picker_weight_change.getValue() == 0) {
            picker_weight_change.setValue(0);
        } else if (spin_goal.getSelectedItem().toString().equals("Lose Weight")) {
            if (picker_weight_change.getValue() > 2) {
                Toast toast = Toast.makeText(getActivity(),
                        "Losing more than 2 pounds per week is extreme and can be dangerous. Please use caution.",
                        Toast.LENGTH_LONG);

                toast.show();
            } if (currentUser.getSex().equals("Male") && FitnessGoalViewModel.getCaloriesToEatDouble(FitnessGoalViewModel.getBMRFormula(),
                    picker_weight_change.getValue(), spin_goal.getSelectedItem().toString(), currentUser.getSex(), spin_sedentary_active.getSelectedItem().toString()) < 1200) {
                Toast toast = Toast.makeText(getActivity(),
                        "Eating less than 1200 calories/day for men can be dangerous.",
                        Toast.LENGTH_LONG);

                toast.show();
            } else if (currentUser.getSex().equals("Female") && FitnessGoalViewModel.getCaloriesToEatDouble(FitnessGoalViewModel.getBMRFormula(),
                    picker_weight_change.getValue(), spin_goal.getSelectedItem().toString(), currentUser.getSex(), spin_sedentary_active.getSelectedItem().toString()) < 1000) {
                Toast toast = Toast.makeText(getActivity(),
                        "Eating less than 1000 calories/day for women can be dangerous.",
                        Toast.LENGTH_LONG);

                toast.show();
            }
        } else if (spin_goal.getSelectedItem().toString().equals("Gain Weight") && picker_weight_change.getValue() > 2) {
            Toast toast = Toast.makeText(getActivity(),
                    "Gaining more than 2 pounds per week is extreme and can be dangerous. Please use caution.",
                    Toast.LENGTH_LONG);

            toast.show();
        }


        //setting text view to display BMR
        tvBMR.setText(FitnessGoalViewModel.getBMRString() + " calories/day");

        //setting text view to display calories need to maintain weight
        if (spin_goal.getSelectedItem().toString().equals("Maintain Weight")) {
            String calorieGoal = FitnessGoalViewModel.getCaloriesToEat(FitnessGoalViewModel.getBMRFormula(),
                    picker_weight_change.getValue(), spin_goal.getSelectedItem().toString(), currentUser.getSex(), spin_sedentary_active.getSelectedItem().toString());
            tvCalorieGoal.setText(("You need to eat " + calorieGoal + " calories/day to maintain your weight."));
        }

        //setting text view to display calories needed to lose weight
        if (spin_goal.getSelectedItem().toString().equals("Lose Weight")){
            String calorieGoal = FitnessGoalViewModel.getCaloriesToEat(FitnessGoalViewModel.getBMRFormula(),
                    picker_weight_change.getValue(), spin_goal.getSelectedItem().toString(), currentUser.getSex(), spin_sedentary_active.getSelectedItem().toString());

            tvCalorieGoal.setText(("You need to eat " + calorieGoal + " calories/day to lose your goal lbs/week"));
        }

        //setting text view to display calories needed to gain weight
        if (spin_goal.getSelectedItem().toString().equals("Gain Weight")){
            String calorieGoal = FitnessGoalViewModel.getCaloriesToEat(FitnessGoalViewModel.getBMRFormula(),
                    picker_weight_change.getValue(), spin_goal.getSelectedItem().toString(), currentUser.getSex(), spin_sedentary_active.getSelectedItem().toString());
            tvCalorieGoal.setText(("You need to eat " + calorieGoal + " calories/day to gain your goal lbs/week"));
        }
    }
}