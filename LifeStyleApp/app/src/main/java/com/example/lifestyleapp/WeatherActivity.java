package com.example.lifestyleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

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

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Location userLocation = new Location("Salt Lake City", "US");
        Weather userLocationWeather;
        try {
            userLocationWeather = getWeather(userLocation);
        } catch (IOException | JSONException e) {
            userLocationWeather = new Weather("--",0);
        }

        TextView weatherCity = findViewById(R.id.weatherCity);
        weatherCity.setText(userLocation.city);

        TextView weatherConditions = findViewById(R.id.weatherConditions);
        weatherConditions.setText(userLocationWeather.conditions);

        TextView weatherTemperature = findViewById(R.id.temperature);
        if(userLocationWeather.conditions=="--")
            weatherTemperature.setText("--");
        else {
            weatherTemperature.setText(String.valueOf(userLocationWeather.temp));
        }
    }


    private URL buildOpenWeatherAPIURL(String cityName) throws MalformedURLException {
        String apiKey = "&appid=63e730362b278faf6db7254c1f3837d8";
        String urlBuild = "https://api.openweathermap.org/data/2.5/weather?q=";
        urlBuild += cityName + apiKey;
        URL url = new URL(urlBuild);
        System.out.println(url);
        return url;
    }

    private InputStream sendAPIHTTPRequest(URL openWeatherFormattedURL) throws IOException {
        HttpsURLConnection urlConnection = (HttpsURLConnection) openWeatherFormattedURL.openConnection();
        InputStream in;
        try {
            in = new BufferedInputStream(urlConnection.getInputStream());
            return in;
        } catch (IOException e) {
            String failureToGetWeather = "--";
            in = new ByteArrayInputStream(failureToGetWeather.getBytes());
            return in;
        } finally {
            urlConnection.disconnect();
        }
    }

    private String readInputStream(InputStream in) throws IOException {
        int i;
        char c;
        String inString = "";
        while ((i = in.read()) != -1) {
            c = (char) i;
            inString += c;
        }

        return inString;
    }

    private Weather JSONToWeather(String weatherData) throws JSONException {
        JSONObject weatherDataJSON = new JSONObject(weatherData);

        JSONArray weatherArr = weatherDataJSON.getJSONArray("weather");
        JSONObject weatherObjects = (JSONObject) weatherArr.get(0);
        String conditions = weatherObjects.getString("description");

        JSONObject main = weatherDataJSON.getJSONObject("main");
        String temp = main.getString("temp");
        float tempKelvin = Float.parseFloat(temp);
        Weather userLocationWeather = new Weather(conditions, (int) tempKelvin);
        return userLocationWeather;
    }


    // 63e730362b278faf6db7254c1f3837d8
    private Weather getWeather(Location userLocation) throws IOException, JSONException {
        URL url = buildOpenWeatherAPIURL(userLocation.city);

        InputStream in = sendAPIHTTPRequest(url);

        String weatherData = readInputStream(in);

        if (weatherData.length() <= 2)
            return new Weather("--", 0);
        Weather userLocationWeather = JSONToWeather(weatherData);
        return userLocationWeather;
    }


}