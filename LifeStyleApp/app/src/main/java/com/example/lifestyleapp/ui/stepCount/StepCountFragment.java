package com.example.lifestyleapp.ui.stepCount;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StepCountViewModel.class);
        //mViewModel.getData().observe(this,stepsObserver); getData

        stepsTV = getView().findViewById(R.id.steps);
    }

    Observer<Long> stepsObserver = new Observer<Long>() {
        @Override
        public void onChanged(Long aLong) {
            stepsTV.setText(aLong.toString());
        }
    };
}