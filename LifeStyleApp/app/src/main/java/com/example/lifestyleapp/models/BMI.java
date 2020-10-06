package com.example.lifestyleapp.models;

public class BMI {
    private int height, weight;

    public BMI(int height, int weight) {
        this.height = height;
        this.weight = weight;
    }

    public int getHeight() {return this.height;}
    public void setHeight(int height) {this.height = height;}

    public int getWeight() {return this.weight;}
    public void setWeight(int weight) {this.weight = weight;}
}
