package com.example.lifestyleapp;

import com.example.lifestyleapp.ui.weather.WeatherFragment;
import com.example.lifestyleapp.ui.weather.Weather;

import org.junit.Test;

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
}