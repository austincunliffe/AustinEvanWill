package com.example.lifestyleapp.login;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lifestyleapp.models.User;
import com.example.lifestyleapp.repositories.LoginActivityRepository;
import com.example.lifestyleapp.repositories.UserProfileRepository;

public class LoginActivityViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> userVerified;
    private LoginActivityRepository mRepo;

    public LoginActivityViewModel(Application application){
        super(application);
        mRepo = new LoginActivityRepository(application);
    }

    public void verifyLogin(String name, String password) {
        mRepo.verifyLogin(name, password);
    }

    public MutableLiveData<Boolean> isVerified(){
        return userVerified;
    }
}
