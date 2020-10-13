package com.example.lifestyleapp.ui.mapHikes;

import android.content.Context;
import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.lifestyleapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MapsHikeFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {

    Location location;
    ArrayList<Trail> trailsNearBy;
    boolean apiThreadCompleted;
    SupportMapFragment mapFragment;


    MapsHikeViewModel model;

    private OnMapReadyCallback callbackThreadStart = new OnMapReadyCallback() {

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
        }
    };

    private OnMapReadyCallback callbackThreadCompleted = new OnMapReadyCallback() {

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
            trailsNearBy = model.getTrails();
            for (Trail el : trailsNearBy) {
                System.out.println("Name: " + el.name + " Lon: " + el.lon + " Lat: " + el.lat);
                LatLng hike = new LatLng(el.lat, el.lon);
                googleMap.addMarker(new MarkerOptions().position(hike).title(el.name));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(hike));
            }


//            LatLng myLocation = new LatLng(model.getLat(), model.getLon());
            LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());


            googleMap.addMarker(new

                    MarkerOptions().

                    position(myLocation).

                    title("Current Location"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,9));
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        model = ViewModelProviders.of(this).get(MapsHikeViewModel.class);
        location = getLastKnownLocation();
        try {
            model.setUserLocation(location);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        model.getThreadCompleted().observe(getViewLifecycleOwner(), apiThreadObserver);

        return inflater.inflate(R.layout.fragment_maps_hike, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment =

                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment.getMapAsync(callbackThreadStart);
        }
    }


    final Observer<Boolean> apiThreadObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean aBoolean) {
            apiThreadCompleted = aBoolean;
            if (aBoolean) {
                mapFragment.getMapAsync(callbackThreadCompleted);
            }
        }
    };


    public Location getLastKnownLocation() {
        LocationManager mLocationManager;
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }

        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            bestLocation = new Location("Default");
            bestLocation.setLongitude(-75);
            bestLocation.setLatitude(39);
        }
        return bestLocation;
    }
}