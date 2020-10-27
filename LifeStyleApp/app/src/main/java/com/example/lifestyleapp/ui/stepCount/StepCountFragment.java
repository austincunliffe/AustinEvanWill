package com.example.lifestyleapp.ui.stepCount;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        Toast.makeText(this.getActivity(), "Swipe right to start and left to stop the step count!", Toast.LENGTH_SHORT).show();

        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                mViewModel.storeData();

                            }
//                            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
//                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                                mViewModel.registerSensor();
//                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        getView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onPause() {
        super.onPause();
        mViewModel.unregisterSensor();
        mViewModel.storeCurrentSteps();
    }

    Observer<Long> stepsObserver = new Observer<Long>() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onChanged(Long aLong) {
            stepsTV.setText(aLong.toString());
        }
    };
}