package com.example.lifestyleapp.ui.userProfile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import com.example.lifestyleapp.R;

public class UserProfileFragment extends Fragment {

    private UserProfileViewModel userProfileViewModel;

    private Button mButtonEdit;

    //Create the variable for the ImageView that holds the profile pic
    ImageView mIvPic;

    //Define a request code for the camera
    static final int REQUEST_IMAGE_CAPTURE = 1;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userProfileViewModel =
                ViewModelProviders.of(this).get(UserProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        SharedPreferences pref = this.getActivity().getSharedPreferences("com.example.lifestyleapp",
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
        TextView mTvName = root.findViewById(R.id.tv_name_big);
        TextView mTvAge = root.findViewById(R.id.tv_age_big);
        TextView mTvCity = root.findViewById(R.id.tv_city_big);
        TextView mTvCountry = root.findViewById(R.id.tv_country_big);
        TextView mTvHeight = root.findViewById(R.id.tv_height_big);
        TextView mTvWeight = root.findViewById(R.id.tv_weight_big);
        TextView mTvSex = root.findViewById(R.id.tv_sex_big);

        //setting the text views
        mTvName.setText(mName);
        mTvAge.setText(mAge);
        mTvCity.setText(mCity);
        mTvCountry.setText(mCountry);
        mTvSex.setText(mSex);
        mTvHeight.setText(mHeight);
        mTvWeight.setText(mWeight);

        //get button
        Button mButtonCamera = root.findViewById(R.id.button_take_pic);
//
//        //set button
//        mButtonCamera.setOnClickListener((View.OnClickListener) this.getActivity());
        return root;
    }

//    public void takePhoto(View v) {
//        System.out.println("hello");
//        switch(v.getId()) {
//            case R.id.button_take_pic: {
//                //The button press should open a camera
//                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if(cameraIntent.resolveActivity(this.getActivity().getPackageManager())!=null){
//                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
//                }
//            }
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == this.getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap thumbnailImage = (Bitmap) extras.get("data");
            mIvPic = (ImageView) this.getActivity().findViewById(R.id.imageView_profile);
            mIvPic.setImageBitmap(thumbnailImage);
        }
    }


}