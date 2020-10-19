package com.example.lifestyleapp.ui.weather;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.lifestyleapp.repositories.WeatherRepository;


public class WeatherViewModel extends AndroidViewModel {

    private MutableLiveData<String> city = new MutableLiveData<>();
    private MutableLiveData<Weather> userLocationWeather;
    private WeatherRepository weatherRepository;

    public WeatherViewModel(@NonNull Application application) {
        super(application);

        weatherRepository = new WeatherRepository(application);
        userLocationWeather = weatherRepository.getUserLocationWeather();
        city = weatherRepository.getUserCity();
    }

    public MutableLiveData<String> getCity() {
        return city;
    }

    public MutableLiveData<Weather> getUserLocationWeather() {
        return userLocationWeather;
    }
}