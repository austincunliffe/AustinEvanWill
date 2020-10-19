package com.example.lifestyleapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    private long uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "sex")
    private String sex;

    @ColumnInfo(name = "sex_idx")
    private int sex_idx;

    @ColumnInfo(name = "birthday")
    private String birthday;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "country")
    private String country;

    @ColumnInfo(name = "country_idx")
    private int country_idx;

    @ColumnInfo(name = "height")
    private int height;

    @ColumnInfo(name = "weight")
    private int weight;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "profilePicFP")
    private String profilePicFP;

    // added by Evan

//    @ColumnInfo(name = "activeOrSedentary")
//    private String activeOrSedentary;
//
//    @ColumnInfo(name = "goal")
//    private String goal;
//
//    @ColumnInfo(name = "weightChange")
//    private int weightChange;

    public long getUid() {return this.uid;}
    public void setUid(long uid){this.uid = uid;}

    public String getPassword() {return this.password;}
    public void setPassword(String password) {this.password = password;}

    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}

    public String getSex() {return this.sex;}
    public void setSex(String sex) {this.sex = sex;}

    public int getSex_idx() {return this.sex_idx;}
    public void setSex_idx(int idx) {this.sex_idx = idx;}

    public String getBirthday() {return this.birthday;}
    public void setBirthday(String dob) {this.birthday = dob;}

    public String getCity() {return this.city;}
    public void setCity(String city) {this.city = city;}

    public String getCountry() {return this.country;}
    public void setCountry(String country) {this.country = country;}

    public int getCountry_idx() {return this.country_idx;}
    public void setCountry_idx(int idx) {this.country_idx = idx;}

    public int getHeight() {return this.height;}
    public void setHeight(int height) {this.height = height;}

    public int getWeight() {return this.weight;}
    public void setWeight(int weight) {this.weight = weight;}

    public String getProfilePicFP() {return this.profilePicFP;}
    public void setProfilePicFP(String fp) {this.profilePicFP = fp;}

    // added by Evan

//    public String getActiveOrSedentary() {return this.activeOrSedentary;}
//    public void setActiveOrSedentary(String aOS) {this.activeOrSedentary = aOS;}
//
//    public String getGoal() {return this.goal;}
//    public void setGoal(String goal) {this.goal = goal;}
//
//    public int getWeightChange() {return this.weightChange;}
//    public void setWeightChange(int weightChange) {this.weightChange = weightChange;}
}
