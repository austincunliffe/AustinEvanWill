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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;


import com.example.lifestyleapp.R;
import com.example.lifestyleapp.models.BMI;
import com.example.lifestyleapp.ui.userProfile.UserProfileViewModel;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;
import java.util.Observable;

public
class BMIFragment extends Fragment {

    private BMIViewModel model;
    private TextView mTvBMI;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_b_m_i, container, false);

        mTvBMI = root.findViewById(R.id.bmi);
        model = ViewModelProviders.of(this).get(BMIViewModel.class);
        model.getBMI().observe(getViewLifecycleOwner(), bmiObserver);

        return root;
    }

    Observer<String> bmiObserver = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            mTvBMI.setText(s);
        }
    };

}