package com.example.lifestyleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateAccountActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    Spinner spin_country, spin_sex;
    String country, sex;

    NumberPicker picker_weight, picker_height;
    int weight, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        getSupportActionBar().hide();

        // Set up pickers.
        picker_weight = findViewById(R.id.np_weight);
        initializeWeightPicker();
        picker_height = findViewById(R.id.np_height);
        initializeHeightPicker();

        // Set up spinners.
        spin_country = findViewById(R.id.spinner_country);
        ArrayAdapter<CharSequence> country_adapter = ArrayAdapter.createFromResource(this,
                R.array.countries_array, R.layout.spinner_item);
        country_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spin_country.setAdapter(country_adapter);
        spin_country.setOnItemSelectedListener(this);

        spin_sex = findViewById(R.id.spinner_sex);
        ArrayAdapter<CharSequence> sex_adapter = ArrayAdapter.createFromResource(this,
                R.array.sex_array, R.layout.spinner_item);
        country_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spin_sex.setAdapter(sex_adapter);
        spin_sex.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (view.getId()) {

            case R.id.spinner_country: {
                country = (String) parent.getItemAtPosition(position);
            }

            case R.id.spinner_sex: {
                sex = (String) parent.getItemAtPosition(position);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void initializeWeightPicker(){
        picker_weight.setMaxValue(600);
        picker_weight.setMinValue(50);
        picker_weight.setValue(130);

        picker_weight.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                weight = picker_weight.getValue();
            }
        });
    }

    public void initializeHeightPicker(){
        picker_height.setMaxValue(96);
        picker_height.setMinValue(24);
        picker_height.setValue(65);

        picker_height.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                height = picker_height.getValue();
            }
        });
    }
}