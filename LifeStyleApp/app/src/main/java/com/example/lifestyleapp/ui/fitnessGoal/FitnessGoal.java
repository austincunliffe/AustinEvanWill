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
    Button bt_setGoal;
    String goal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_fitness_goal, container, false);
        spin_goal = root.findViewById(R.id.spinner_goal);
        ArrayAdapter<CharSequence> goal_adapter = ArrayAdapter.createFromResource(root.getContext(),
                R.array.goal_array, R.layout.spinner_item);
        goal_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spin_goal.setAdapter(goal_adapter);
        spin_goal.setOnItemSelectedListener(this);
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

    @SuppressLint("CommitPrefEdits") //Not sure what this @Supress is
    @Override
    public void onClick(View v) {
        goal = spin_goal.getSelectedItem().toString();
        SharedPreferences prefs = v.getContext().getSharedPreferences(
                "com.example.lifestyleapp", Context.MODE_PRIVATE);
        prefs.edit().putString("goal", goal);
//        prefs.getString("goal",goal);
        System.out.println("Goal SavedR");
    }
}