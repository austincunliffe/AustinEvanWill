package com.example.lifestyleapp.ui.weather;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.lifestyleapp.R;


public class WeatherFragment extends Fragment {

    private WeatherViewModel weatherViewModel;
    TextView weatherCity;
    TextView weatherConditions;
    TextView weatherTemperature;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        View root = inflater.inflate(R.layout.fragment_weather, container, false);

        weatherCity = root.findViewById(R.id.weatherCity);
        weatherTemperature = root.findViewById(R.id.temperature);
        weatherConditions = root.findViewById(R.id.weatherConditions);

        weatherViewModel =
                ViewModelProviders.of(this).get(WeatherViewModel.class);
        weatherViewModel.getUserLocationWeather().observe(getViewLifecycleOwner(), weatherObserver);
        weatherViewModel.getCity().observe(getViewLifecycleOwner(),locationObserver);
        return root;
    }

    final Observer<Weather> weatherObserver = new Observer<Weather>() {
        @Override
        public void onChanged(Weather weather) {
            weatherConditions.setText(weather.conditions);
            if (weather.conditions == "--")
                weatherTemperature.setText("--");
            else {
                weatherTemperature.setText(String.valueOf(weather.temp));
            }
        }
    };

    final Observer<String> locationObserver = new Observer<String>() {
        @Override
        public void onChanged(String city) {
            weatherCity.setText(city);

        }
    };

}