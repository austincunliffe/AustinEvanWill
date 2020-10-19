package com.example.lifestyleapp.ui.userProfile;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lifestyleapp.R;
import com.example.lifestyleapp.models.User;
import com.example.lifestyleapp.repositories.UserProfileRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class UserProfileViewModel extends AndroidViewModel {

    private MutableLiveData<User> mUser;
    private UserProfileRepository mRepo;

    public UserProfileViewModel(Application application){
        super(application);
        mRepo = new UserProfileRepository(application);
        mUser = mRepo.getData();
    }

    public LiveData<User> getData(){
        return mUser;
    }

    public void updateUser(User user) throws ExecutionException, InterruptedException {
        mRepo.updateUser(user);
    }

    public SimpleDateFormat createDobLabel(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf;
    }

    public String saveToInternalStorage(Application app, Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(app);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File profilePicFP = new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(profilePicFP);
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

        return directory.getAbsolutePath();
    }

    public Bitmap getProfilePic(){
        return mRepo.getProfilePic();
    }
}