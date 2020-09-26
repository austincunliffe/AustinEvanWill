package com.example.lifestyleapp;

import com.example.lifestyleapp.ui.weather.WeatherFragment;
import com.example.lifestyleapp.ui.weather.Weather;
import com.example.lifestyleapp.ui.weather.Location;

import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class WeatherTest {

    @Test
    public void kelvinToFahrenheitTest1() {
        int kelvin = 300;

        double expected = 80;
        double output = Weather.kelvinToFahrenheit(kelvin);

        assertEquals(expected, output, .05);
    }

    @Test
    public void kelvinToFahrenheitTest2() {
        int kelvin = 0;

        double expected = -459;
        double output = Weather.kelvinToFahrenheit(kelvin);

        assertEquals(expected, output, .05);
    }

    @Test
    public void kelvinToFahrenheitTest3() {
        int kelvin = -100;

        double expected = -639;
        double output = Weather.kelvinToFahrenheit(kelvin);

        assertEquals(expected, output, .05);
    }

//    @Test
//    public void weatherAPITest() throws IOException, JSONException {
//        Location loc = new Location("Montreal", "Canada");
//
//        Weather w = WeatherFragment.getWeather(loc);
//
//        assertNotEquals("--", w.conditions);
//        assertNotEquals(0, w.temp);
//    }
}