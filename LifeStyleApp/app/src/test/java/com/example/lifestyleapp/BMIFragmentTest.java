package com.example.lifestyleapp;

import com.example.lifestyleapp.ui.bmi.BMIFragment;

import org.junit.Test;

import static org.junit.Assert.*;

public class BMIFragmentTest {

    @Test
    public void getBMITest1() {
        int height = 72;
        int weight = 185;

        double expected = 25.1;
        double output = BMIFragment.getBMI(weight, height);

        assertEquals(expected, output, .05);
    }

    @Test
    public void getBMITest2() {
        int height = 84;
        int weight = 300;

        double expected = 29.9;
        double output = BMIFragment.getBMI(weight, height);

        assertEquals(expected, output, .05);
    }
}