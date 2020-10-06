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

    private MutableLiveData<String> mText;
    private String city;


    private String country;
    private Location userLocation;
    private MutableLiveData<Weather> userLocationWeather;


    public WeatherViewModel(@NonNull Application application) {
        super(application);
        loadData();
    }


    @SuppressLint("StaticFieldLeak")
    void loadData() {
        SharedPreferences prefs = getApplication().getSharedPreferences(
                "com.example.lifestyleapp", Context.MODE_PRIVATE);
        city = prefs.getString("city", null);
        country = prefs.getString("country", null);
        userLocation = new Location(city, country);
        
        userLocationWeather = new MutableLiveData<>();

        new AsyncTask<Location, Void, Weather>() {

            @Override
            protected Weather doInBackground(Location... locations) {
                Location location = locations[0];

                try {
                    return getWeather(location);
                } catch (IOException | JSONException e) {
                    return new Weather("--", 0);
                }
            }


            @Override
            protected void onPostExecute(Weather weather) {
                super.onPostExecute(weather);
                try {
                    userLocationWeather.setValue(weather);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute(userLocation);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<String> getCity() {
        return new MutableLiveData<>(city);
    }

    public MutableLiveData<String> getCountry() {
        return new MutableLiveData<>(country);
    }

    public MutableLiveData<Weather> getUserLocationWeather() {
        return userLocationWeather;
    }


    private static URL buildOpenWeatherAPIURL(String cityName) throws MalformedURLException {
        String apiKey = "&appid=63e730362b278faf6db7254c1f3837d8";
        String urlBuild = "https://api.openweathermap.org/data/2.5/weather?q=";
        urlBuild += cityName + apiKey;
        URL url = new URL(urlBuild);
        System.out.println(url);
        return url;
    }

    private static InputStream sendAPIHTTPRequest(URL openWeatherFormattedURL) throws IOException {
        HttpsURLConnection urlConnection = (HttpsURLConnection) openWeatherFormattedURL.openConnection();
        InputStream in;
        try {
            in = new BufferedInputStream(urlConnection.getInputStream());
            return in;
        } catch (IOException e) {
            System.out.println(e);
            String failureToGetWeather = "--";
            in = new ByteArrayInputStream(failureToGetWeather.getBytes());
            return in;
        } finally {
            urlConnection.disconnect();
        }
    }

    private static String readInputStream(InputStream in) throws IOException {
        int i;
        char c;
        String inString = "";
        while ((i = in.read()) != -1) {
            c = (char) i;
            inString += c;
        }

        return inString;
    }

    private static Weather JSONToWeather(String weatherData) throws JSONException {
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
    private static Weather getWeather(Location userLocation) throws IOException, JSONException {
        URL url = buildOpenWeatherAPIURL(userLocation.city);

        InputStream in = sendAPIHTTPRequest(url);

        String weatherData = readInputStream(in);

        if (weatherData.length() <= 2)
            return new Weather("--", 0);
        Weather userLocationWeather = JSONToWeather(weatherData);
        return userLocationWeather;
    }
}