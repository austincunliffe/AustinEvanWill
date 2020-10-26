package com.example.lifestyleapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "stepCount")
public class StepCount {


    @ColumnInfo(name = "time")
    private String time;

//    @ColumnInfo(name = "count")
    @PrimaryKey
    private long count;


    public long getCount() {
        return this.count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
