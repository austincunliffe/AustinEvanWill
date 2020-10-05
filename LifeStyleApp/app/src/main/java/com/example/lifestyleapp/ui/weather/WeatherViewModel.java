//package com.example.lifestyleapp.ui.weather;
//import android.Manifest;
//
//import android.app.Application;
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.ViewModel;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedInputStream;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//import javax.net.ssl.HttpsURLConnection;
//
//public class WeatherViewModel extends ViewModel {
//
//    private MutableLiveData<String> mText;
//    private String city;
//
//
//
//    private String country;
//    private Location userLocation;
//    private Weather userLocationWeather;
//
//
//
//
//    public WeatherViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");
//        loadData();
//    }
//
//    void loadData(){
//        SharedPreferences prefs = getSharedPreferences(
//                "com.example.lifestyleapp", Context.MODE_PRIVATE);
//         city = prefs.getString("city",null);
//         country = prefs.getString("country",null);
//         userLocation = new Location(city, country);
//        try {
//            userLocationWeather = getWeather(userLocation);
//        } catch (IOException | JSONException e) {
//            userLocationWeather = new Weather("--",0);
//        }
//
//    }
//
//    public LiveData<String> getText() {
//        return mText;
//    }
//
//    public String getCity() {
//        return city;
//    }
//    public String getCountry() {
//        return country;
//    }
//
//    public Weather getUserLocationWeather(){
//        return userLocationWeather;
//    }
//
//
//
//
//    private static URL buildOpenWeatherAPIURL(String cityName) throws MalformedURLException {
//        String apiKey = "&appid=63e730362b278faf6db7254c1f3837d8";
//        String urlBuild = "https://api.openweathermap.org/data/2.5/weather?q=";
//        urlBuild += cityName + apiKey;
//        URL url = new URL(urlBuild);
//        System.out.println(url);
//        return url;
//    }
//
//    private static InputStream sendAPIHTTPRequest(URL openWeatherFormattedURL) throws IOException {
//        HttpsURLConnection urlConnection = (HttpsURLConnection) openWeatherFormattedURL.openConnection();
//        InputStream in;
//        try {
//            in = new BufferedInputStream(urlConnection.getInputStream());
//            return in;
//        } catch (IOException e) {
//            System.out.println(e);
//            String failureToGetWeather = "--";
//            in = new ByteArrayInputStream(failureToGetWeather.getBytes());
//            return in;
//        } finally {
//            urlConnection.disconnect();
//        }
//    }
//
//    private static String readInputStream(InputStream in) throws IOException {
//        int i;
//        char c;
//        String inString = "";
//        while ((i = in.read()) != -1) {
//            c = (char) i;
//            inString += c;
//        }
//
//        return inString;
//    }
//
//    private static Weather JSONToWeather(String weatherData) throws JSONException {
//        JSONObject weatherDataJSON = new JSONObject(weatherData);
//
//        JSONArray weatherArr = weatherDataJSON.getJSONArray("weather");
//        JSONObject weatherObjects = (JSONObject) weatherArr.get(0);
//        String conditions = weatherObjects.getString("description");
//
//        JSONObject main = weatherDataJSON.getJSONObject("main");
//        String temp = main.getString("temp");
//        float tempKelvin = Float.parseFloat(temp);
//        Weather userLocationWeather = new Weather(conditions, (int) tempKelvin);
//        return userLocationWeather;
//    }
//
//
//    // 63e730362b278faf6db7254c1f3837d8
//    private static Weather getWeather(Location userLocation) throws IOException, JSONException {
//        URL url = buildOpenWeatherAPIURL(userLocation.city);
//
//        InputStream in = sendAPIHTTPRequest(url);
//
//        String weatherData = readInputStream(in);
//
//        if (weatherData.length() <= 2)
//            return new Weather("--", 0);
//        Weather userLocationWeather = JSONToWeather(weatherData);
//        return userLocationWeather;
//    }
//}