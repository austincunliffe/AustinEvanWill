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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;


import com.example.lifestyleapp.R;
import com.example.lifestyleapp.ui.userProfile.UserProfileViewModel;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

public
class BMIFragment extends Fragment {

    private BMIViewModel model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_b_m_i, container, false);

        model = new BMIViewModel(this.requireActivity().getApplication());
        String userBMIString = model.getBMI();

        TextView mTvBMI = root.findViewById(R.id.bmi);
        mTvBMI.setText(userBMIString);

        return root;
    }
}