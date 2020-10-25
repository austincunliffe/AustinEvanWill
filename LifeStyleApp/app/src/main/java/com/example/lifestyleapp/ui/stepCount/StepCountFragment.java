package com.example.lifestyleapp.ui.stepCount;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lifestyleapp.R;

public class StepCountFragment extends Fragment {

    private StepCountViewModel mViewModel;
    TextView stepsTV;

    public static StepCountFragment newInstance() {
        return new StepCountFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.step_count_fragment, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new StepCountViewModel(this.getActivity().getApplication());
        mViewModel.registerSensor();
        mViewModel.getData().observe(getViewLifecycleOwner(), stepsObserver);
        stepsTV = getView().findViewById(R.id.steps);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        mViewModel.registerSensor();
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewModel.unregisterSensor();
    }

    Observer<Long> stepsObserver = new Observer<Long>() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onChanged(Long aLong) {
            stepsTV.setText(aLong.toString());
        }
    };
}