package com.example.lifestyleapp.ui.weather;

public class Weather {
    String conditions;
    int temp;

    public Weather(String conditions, int temp) {
        this.conditions = conditions;
        this.temp = kelvinToFahrenheit(temp);
    }

    public static int kelvinToFahrenheit(int kelvinTemp) {
        int fahrenheitTemp = (int) ((kelvinTemp - 273.15) * 9 / 5 + 32);
        return fahrenheitTemp;
    }
}
