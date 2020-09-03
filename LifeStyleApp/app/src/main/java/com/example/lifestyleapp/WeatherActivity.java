package com.example.lifestyleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        try {
            getWeather();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // whatever information necessary to receive weather hardcode to work
    // 63e730362b278faf6db7254c1f3837d8
    public void getWeather() throws IOException {
        String cityName = "London";
//        String stateCode ="UT";
        String apiKey= "&appid=63e730362b278faf6db7254c1f3837d8";
        String urlBuild = "https://api.openweathermap.org/data/2.5/weather?q=";
//        urlBuild+=cityName+stateCode+apiKey;
        urlBuild+=cityName+apiKey;
        System.out.println(urlBuild);
        URL url = new URL(urlBuild);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            System.out.println((in));
        } finally {
            urlConnection.disconnect();
        }
    }



    private void readStream(InputStream in) {

    }


}