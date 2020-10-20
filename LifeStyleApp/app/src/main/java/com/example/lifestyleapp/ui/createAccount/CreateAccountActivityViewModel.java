package com.example.lifestyleapp.ui.createAccount;

import android.app.Application;
import androidx.lifecycle.ViewModel;

import com.example.lifestyleapp.models.User;
import com.example.lifestyleapp.repositories.CreateAccountActivityRepository;

public class CreateAccountActivityViewModel extends ViewModel {
    private CreateAccountActivityRepository UserRepo;

    public void init(Application application, User user){
        UserRepo = CreateAccountActivityRepository.getInstance(application);
        UserRepo.insert(user);
    }

    public boolean checkFields() {
        return false;
    }
}
