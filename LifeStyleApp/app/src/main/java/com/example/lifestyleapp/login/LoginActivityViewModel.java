package com.example.lifestyleapp.login;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.lifestyleapp.repositories.LoginActivityRepository;

public class LoginActivityViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> userVerified;
    private LoginActivityRepository mRepo;

    public LoginActivityViewModel(Application application){
        super(application);
        mRepo = new LoginActivityRepository(application);
        userVerified = mRepo.getData("RICKY", "BOBBY");
    }

    public void verifyLogin(String name, String password) {
        userVerified = mRepo.getData(name, password);
    }

    public LiveData<Boolean> isVerified(){
        return userVerified;
    }
}
