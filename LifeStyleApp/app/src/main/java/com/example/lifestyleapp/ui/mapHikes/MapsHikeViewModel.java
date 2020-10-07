package com.example.lifestyleapp.ui.mapHikes;

import android.annotation.SuppressLint;
import android.app.Application;
import android.location.Location;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MapsHikeViewModel extends AndroidViewModel {

    double lat;
    double lon;
    ArrayList<Trail> trails;


    public MapsHikeViewModel(@NonNull Application application) throws IOException, JSONException {
        super(application);
    }

    @SuppressLint("StaticFieldLeak")
    void setUserLocation(Location location) throws IOException, JSONException {
        lat = location.getLatitude();
        lon = location.getLongitude();

        new AsyncTask<Location, Void, ArrayList<Trail>>() {
            @Override
            protected ArrayList<Trail> doInBackground(Location... locations) {
                Location location = locations[0];

                ArrayList<Trail> nearbyHikes = new ArrayList<>();
                try {
                    nearbyHikes = getNearByHikes(location.getLatitude(), location.getLongitude());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return nearbyHikes;
            }

            @Override
            protected void onPostExecute(ArrayList<Trail> nearbyHikes) {
                super.onPostExecute(nearbyHikes);
                for (Trail el : nearbyHikes) {
                }
                trails = nearbyHikes;
            }
        }.execute(location);
    }

    ArrayList<Trail> getTrails() {
        return trails;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }


    private URL buildHikingProjectAPIURL(double lat, double lon) throws MalformedURLException {

        String urlBuild = "https://www.hikingproject.com/data/get-trails";
        String userLat = "?lat=" + lat;
        String userLon = "&lon=" + lon;
        String maxDistance = "&maxDistance=30";
        String apiKey = "&key=200914155-a56f977584ebf7887423123ad38c1a82";

        urlBuild += userLat + userLon + maxDistance + apiKey;
        URL url = new URL(urlBuild);
        System.out.println(url);
        return url;
    }

    private InputStream sendAPIHTTPRequest(URL APIRequest) throws IOException {
        HttpsURLConnection urlConnection = (HttpsURLConnection) APIRequest.openConnection();
        InputStream in;
        try {
            in = new BufferedInputStream(urlConnection.getInputStream());
            return in;
        } catch (IOException e) {
            System.out.println(e);
            String failureToGetResponse = "--";
            in = new ByteArrayInputStream(failureToGetResponse.getBytes());
            return in;
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

    ArrayList<Trail> JsonToHikesList(String hikeData) throws JSONException {
        JSONObject hikeDataJSON = new JSONObject(hikeData);
        JSONArray trailsArr = hikeDataJSON.getJSONArray("trails");
        ArrayList<Trail> trailsNearMe = new ArrayList<>();
        for (int i = 0; i < trailsArr.length(); i++) {
            JSONObject trail = (JSONObject) trailsArr.get(i);
            String name = trail.getString("name");
            String lat = trail.getString("latitude");
            String lon = trail.getString("longitude");

            trailsNearMe.add(new Trail(name, Float.parseFloat(lon), Float.parseFloat(lat)));
        }
        return trailsNearMe;
    }


    ArrayList<Trail> getNearByHikes(double lat, double lon) throws IOException, JSONException {
        URL url = null;
        try {
            url = buildHikingProjectAPIURL(lat, lon);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        InputStream in = sendAPIHTTPRequest(url);
        System.out.println(in);
        String hikeData = readInputStream(in);
        System.out.println(hikeData);
        trails = JsonToHikesList(hikeData);
        return trails;
    }
}
