package com.example.lifestyleapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stepCount")
public class StepCount {

    @PrimaryKey (autoGenerate = true)
    private long CID;


    @ColumnInfo(name = "time")
    private String time;

    //    @PrimaryKey
    @ColumnInfo(name = "count")
    private long count;


    public long getCID(){return this.CID;}

    public void setCID(long cid){this.CID = cid;}

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
