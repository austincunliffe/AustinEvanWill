package com.example.lifestyleapp.ui.mapHikes;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.lifestyleapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
            LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            try {
                getNearByHikes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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

    private ArrayList<String> getNearByHikes() throws IOException {
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

        return new ArrayList<>();

    }

//https://www.hikingproject.com/data/get-trails?lat=40.0274&lon=-105.2519&maxDistance=10&key=200914155-a56f977584ebf7887423123ad38c1a82
    private URL buildHikingProjectAPIURL() throws MalformedURLException {

        String urlBuild = "https://www.hikingproject.com/data/get-trails";
        String userLat = "?lat=-34";
        String userLon = "&lon=151";
        String maxDistance="&maxDistance=30";
        String apiKey = "&key=200914155-a56f977584ebf7887423123ad38c1a82";

        urlBuild += userLat+userLon+maxDistance+apiKey;
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

    String hikingProjectAPIKey = "200914155-a56f977584ebf7887423123ad38c1a82";


}