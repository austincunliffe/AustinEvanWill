package com.example.lifestyleapp.ui.mapHikes;
import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.lifestyleapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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


@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MapsHikeFragment extends Fragment {
    double lat;
    double lon;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            // Set Hikes
            try {
                ArrayList<Trail> trailsNearBy = getNearByHikes();
                for (Trail el: trailsNearBy){
                    System.out.println("Name: "+el.name+" Lon: "+el.lon+" Lat: "+el.lat);
                    LatLng hike = new LatLng(el.lat, el.lon);
                    googleMap.addMarker(new MarkerOptions().position(hike).title(el.name));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(hike));
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }


//            LatLng myLocation = new LatLng(currentLocation  .get(0), currentLocation.get(1));
//            googleMap.addMarker(new MarkerOptions().position(myLocation).title("Current Location"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
//         lat = getArguments().getDouble("lat");
//         lon = getArguments().getDouble("lon");
        return inflater.inflate(R.layout.fragment_maps_hike, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private ArrayList<Float> getMyLocationLonLat(){
        return new ArrayList<>();
    }


    private URL buildHikingProjectAPIURL() throws MalformedURLException {

        String urlBuild = "https://www.hikingproject.com/data/get-trails";
        String userLat = "?lat=-34";
        String userLon = "&lon=151";
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


    private ArrayList<Trail> getNearByHikes() throws IOException, JSONException {
        URL url = null;
        try {
            url = buildHikingProjectAPIURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        InputStream in = sendAPIHTTPRequest(url);
        System.out.println(in);
        String hikeData = readInputStream(in);
        System.out.println(hikeData);
        ArrayList<Trail> trails = JsonToHikesList(hikeData);
        return trails;

    }
}