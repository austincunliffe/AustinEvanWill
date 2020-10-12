package com.example.lifestyleapp.ui.weather;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lifestyleapp.repositories.WeatherRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WeatherViewModel extends AndroidViewModel {

    private MutableLiveData<String> city;
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