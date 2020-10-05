//package com.example.lifestyleapp.ui.mapHikes;
//import android.Manifest;
//import android.app.Application;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationManager;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.lifecycle.AndroidViewModel;
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
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.net.ssl.HttpsURLConnection;
//
//public class MapsHikeViewModel extends AndroidViewModel implements ActivityCompat.OnRequestPermissionsResultCallback {
//
//    SharedPreferences pref;
//
//    Location location;
//    double lat;
//    double lon;
//    ArrayList<Trail> trails;
//
//
//    public MapsHikeViewModel(@NonNull Application application) throws IOException, JSONException {
//        super(application);
//        application.getClass()
//        loadData();
//    }
//
//
//    void loadUserLocation(){
//         pref = getApplication().getSharedPreferences("com.example.lifestyleapp",
//                Context.MODE_PRIVATE);
//        location = getLastKnownLocation();
//        lon = location.getLongitude();
//        lat = location.getLatitude();
//    }
//
//    void loadNearbyHikes() throws IOException, JSONException {
//        URL url = null;
//        try {
//            url = buildHikingProjectAPIURL();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        InputStream in = sendAPIHTTPRequest(url);
//        System.out.println(in);
//        String hikeData = readInputStream(in);
//        System.out.println(hikeData);
//        trails = JsonToHikesList(hikeData);
//    }
//
//    void loadData() throws IOException, JSONException {
//        loadUserLocation();
//        loadNearbyHikes();
//    }
//
//
//    private URL buildHikingProjectAPIURL() throws MalformedURLException {
//
//        String urlBuild = "https://www.hikingproject.com/data/get-trails";
//        String userLat = "?lat=" + lat;
//        String userLon = "&lon=" + lon;
//        String maxDistance = "&maxDistance=30";
//        String apiKey = "&key=200914155-a56f977584ebf7887423123ad38c1a82";
//
//        urlBuild += userLat + userLon + maxDistance + apiKey;
//        URL url = new URL(urlBuild);
//        System.out.println(url);
//        return url;
//    }
//
//    private InputStream sendAPIHTTPRequest(URL APIRequest) throws IOException {
//        HttpsURLConnection urlConnection = (HttpsURLConnection) APIRequest.openConnection();
//        InputStream in;
//        try {
//            in = new BufferedInputStream(urlConnection.getInputStream());
//            return in;
//        } catch (IOException e) {
//            System.out.println(e);
//            String failureToGetResponse = "--";
//            in = new ByteArrayInputStream(failureToGetResponse.getBytes());
//            return in;
//        }
//    }
//
//    private String readInputStream(InputStream in) throws IOException {
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
//    ArrayList<Trail> JsonToHikesList(String hikeData) throws JSONException {
//        JSONObject hikeDataJSON = new JSONObject(hikeData);
//        JSONArray trailsArr = hikeDataJSON.getJSONArray("trails");
//        ArrayList<Trail> trailsNearMe = new ArrayList<>();
//        for (int i = 0; i < trailsArr.length(); i++) {
//            JSONObject trail = (JSONObject) trailsArr.get(i);
//            String name = trail.getString("name");
//            String lat = trail.getString("latitude");
//            String lon = trail.getString("longitude");
//
//            trailsNearMe.add(new Trail(name, Float.parseFloat(lon), Float.parseFloat(lat)));
//        }
//        return trailsNearMe;
//    }
//
//
//    ArrayList<Trail> getNearByHikes() throws IOException, JSONException {
////        URL url = null;
////        try {
////            url = buildHikingProjectAPIURL();
////        } catch (MalformedURLException e) {
////            e.printStackTrace();
////        }
////
////        InputStream in = sendAPIHTTPRequest(url);
////        System.out.println(in);
////        String hikeData = readInputStream(in);
////        System.out.println(hikeData);
////        ArrayList<Trail> trails = JsonToHikesList(hikeData);
//        return trails;
//    }
//
//    double getLat(){
//        return lat;
//    }
//    double getLon(){
//        return lon;
//    }
//
//    public Location getLastKnownLocation() {
//        LocationManager mLocationManager;
//        mLocationManager = (LocationManager)getApplication().getSystemService(Context.LOCATION_SERVICE);
//        List<String> providers = mLocationManager.getProviders(true);
//        Location bestLocation = null;
//
//        if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//
//        }
//
//        for (String provider : providers) {
//            Location l = mLocationManager.getLastKnownLocation(provider);
//            if (l == null) {
//                continue;
//            }
//            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
//                bestLocation = l;
//            }
//        }
//        if (bestLocation == null){
//            bestLocation = new Location("Default");
//            bestLocation.setLongitude(-75);
//            bestLocation.setLatitude(39);
//        }
//        return bestLocation;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//    }
//}
