package com.example.lifestyleapp.ui.userProfile;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class UserProfileFragment extends Fragment {

    private UserProfileViewModel userProfileViewModel;

    private Button mButtonEdit, mButtonCamera;

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

        final String picPath = pref.getString("profile_pic", null);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                loadProfilePic(picPath);
            }
        });


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

        mButtonCamera = root.findViewById(R.id.button_take_pic);
        initializeCamera();

        return root;
    }

    public void initializeCamera() {
        mButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(v);
            }
        });
    }

    public void takePhoto(View v) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(this.getActivity().getPackageManager())!=null){
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == this.getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap thumbnailImage = (Bitmap) extras.get("data");

            saveToInternalStorage(thumbnailImage);
            mIvPic = (ImageView) this.getActivity().findViewById(R.id.imageView_profile);
            mIvPic.setImageBitmap(thumbnailImage);
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(requireActivity().getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String path = directory.getAbsolutePath();

        SharedPreferences pref = this.getActivity().getSharedPreferences("com.example.lifestyleapp",
                Context.MODE_PRIVATE);
        pref.edit().putString("profile_pic", path).apply();

        return path;
    }

    private void loadProfilePic(String path)
    {
        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            mIvPic = this.getActivity().findViewById(R.id.imageView_profile);
            mIvPic.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}