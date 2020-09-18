package com.example.lifestyleapp.ui.bmi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import com.example.lifestyleapp.R;
import com.example.lifestyleapp.ui.userProfile.UserProfileViewModel;

import java.math.BigDecimal;
import java.math.MathContext;

public
class BMIFragment extends Fragment {

    private UserProfileViewModel userProfileViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        userProfileViewModel =
                ViewModelProviders.of(this).get(UserProfileViewModel.class);

        View root = inflater.inflate(R.layout.fragment_b_m_i, container, false);

        SharedPreferences pref = this.getActivity().getSharedPreferences("com.example.lifestyleapp",
                Context.MODE_PRIVATE);

        int mHeightInt = pref.getInt("height", 0);
        int mWeightInt = pref.getInt("weight", 0);

        double userBMI = getBMI(mWeightInt, mHeightInt);

        //rounding the bmi to one 3 significant digits
        BigDecimal bd = new BigDecimal(userBMI);
        bd = bd.round(new MathContext(3));
        double roundedUserBMI = bd.doubleValue();


        String userBMIString = Double.toString(roundedUserBMI);
        System.out.println(userBMIString);
        System.out.println("Hello this is a test!");

        //Get the text views where we will display names
        //Create variables for the UI elements that we need to control
        TextView mTvBMI = root.findViewById(R.id.bmi);

        mTvBMI.setText(userBMIString);

        return root;
    }
    private double getBMI(int userWeight, int userHeight){
        // this is the formula for calculating BMI in imperial units
        double bmi = (703 * (userWeight/Math.pow(userHeight,2)));
        return bmi;
    }
}