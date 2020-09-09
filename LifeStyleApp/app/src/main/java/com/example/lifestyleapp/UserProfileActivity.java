package com.example.lifestyleapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity implements  View.OnClickListener{

    private Button mButtonEdit;

    //Create the variable for the ImageView that holds the profile pic
    ImageView mIvPic;

    //Define a request code for the camera
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        System.out.println("test test");


        SharedPreferences pref = this.getSharedPreferences("com.example.lifestyleapp",
                Context.MODE_PRIVATE);

        //Create variables to hold the three strings
        String mName = pref.getString("username", null);
        String mAge = pref.getString("dob", null);
        String mCity = pref.getString("city", null);
        String mCountry = pref.getString("country", "test");
//        converting height and weight to strings
        int mHeightInt = pref.getInt("height", 0);
        String mHeight = Integer.toString(mHeightInt);
        int mWeightInt = pref.getInt("weight", 0);
        String mWeight = Integer.toString(mWeightInt);
        String mSex = pref.getString("sex", null);

        //Get the text views where we will display names
        //Create variables for the UI elements that we need to control
        TextView mTvName = findViewById(R.id.tv_name_big);
        TextView mTvAge = findViewById(R.id.tv_age_big);
        TextView mTvCity = findViewById(R.id.tv_city_big);
        TextView mTvCountry = findViewById(R.id.tv_country_big);
        TextView mTvHeight = findViewById(R.id.tv_height_big);
        TextView mTvWeight = findViewById(R.id.tv_weight_big);
        TextView mTvSex = findViewById(R.id.tv_sex_big);

        //setting the text views
        mTvName.setText(mName);
        mTvAge.setText(mAge);
        mTvCity.setText(mCity);
        mTvCountry.setText(mCountry);
        mTvSex.setText(mSex);
        mTvHeight.setText(mHeight);
        mTvWeight.setText(mWeight);

        //get button
        Button mButtonCamera = findViewById(R.id.button_take_pic);

        //set button
        mButtonCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        System.out.println("hello");
        switch(v.getId()) {
            case R.id.button_take_pic: {
                //The button press should open a camera
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(cameraIntent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap thumbnailImage = (Bitmap) extras.get("data");
            mIvPic = (ImageView) findViewById(R.id.imageView_profile);
            mIvPic.setImageBitmap(thumbnailImage);
        }
    }
}

