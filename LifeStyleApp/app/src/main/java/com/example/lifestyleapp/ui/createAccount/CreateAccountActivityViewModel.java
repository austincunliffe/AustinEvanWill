package com.example.lifestyleapp.ui.createAccount;

import android.app.Application;
import androidx.lifecycle.ViewModel;

import com.example.lifestyleapp.models.User;
import com.example.lifestyleapp.repositories.UserRepository;

public class CreateAccountActivityViewModel extends ViewModel {
    private UserRepository UserRepo;

    public void init(Application application, User user){
        UserRepo = UserRepository.getInstance(application);
        UserRepo.insert(user);
    }

    public boolean checkFields() {
        return false;
    }
}
