package com.example.lifestyleapp.ui.weather;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.lifestyleapp.R;

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

public class WeatherFragment extends Fragment {

    private WeatherViewModel weatherViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        weatherViewModel =
                ViewModelProviders.of(this).get(WeatherViewModel.class);
        View root = inflater.inflate(R.layout.fragment_weather, container, false);
        SharedPreferences prefs = this.getActivity().getSharedPreferences(
                "com.example.lifestyleapp", Context.MODE_PRIVATE);
        String city = prefs.getString("city",null);
        String country = prefs.getString("country",null);
        Location userLocation = new Location(city, country);
        Weather userLocationWeather;
        try {
            userLocationWeather = getWeather(userLocation);
        } catch (IOException | JSONException e) {
            userLocationWeather = new Weather("--",0);
        }

        TextView weatherCity = root.findViewById(R.id.weatherCity);
        weatherCity.setText(userLocation.city);

        TextView weatherConditions = root.findViewById(R.id.weatherConditions);
        weatherConditions.setText(userLocationWeather.conditions);

        TextView weatherTemperature = root.findViewById(R.id.temperature);
        if(userLocationWeather.conditions=="--")
            weatherTemperature.setText("--");
        else {
            weatherTemperature.setText(String.valueOf(userLocationWeather.temp));
        }
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
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
    public static Weather getWeather(Location userLocation) throws IOException, JSONException {
        URL url = buildOpenWeatherAPIURL(userLocation.city);

        InputStream in = sendAPIHTTPRequest(url);

        String weatherData = readInputStream(in);

        if (weatherData.length() <= 2)
            return new Weather("--", 0);
        Weather userLocationWeather = JSONToWeather(weatherData);
        return userLocationWeather;
    }

}