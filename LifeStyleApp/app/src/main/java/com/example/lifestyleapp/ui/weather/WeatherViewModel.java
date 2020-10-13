package com.example.lifestyleapp.ui.weather;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.lifestyleapp.models.User;
import com.example.lifestyleapp.repositories.WeatherRepository;
import com.example.lifestyleapp.ui.userProfile.UserProfileViewModel;

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
    private UserProfileViewModel userProfileViewModel;
    private MutableLiveData<User> user;


    public WeatherViewModel(@NonNull Application application) {
        super(application);
//        userProfileViewModel = new UserProfileViewModel(application);
//        user = new MutableLiveData<>();
//        user = userProfileViewModel.getData().observe(this,userObserver);
        weatherRepository = new WeatherRepository(application);
        user = weatherRepository.getUser();
        userLocationWeather = weatherRepository.getUserLocationWeather();
        city = weatherRepository.getUserCity();
    }

//    final Observer<User> userObserver = new Observer<User>() {
//
//    };

    public MutableLiveData<String> getCity() {
        return city;
    }

    public MutableLiveData<Weather> getUserLocationWeather() {
        return userLocationWeather;
    }
}