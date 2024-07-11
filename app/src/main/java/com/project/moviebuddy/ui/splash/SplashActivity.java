package com.project.moviebuddy.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.project.moviebuddy.data.model.User;
import com.project.moviebuddy.databinding.ActivitySplashBinding;
import com.project.moviebuddy.di.Injection;
import com.project.moviebuddy.ui.login.LoginActivity;
import com.project.moviebuddy.ui.main.BottomMainActivity;
import com.project.moviebuddy.utils.SharedPreferencesManager;

public class SplashActivity extends AppCompatActivity {
    private SplashViewModel viewModel;

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setViewModel();

        SharedPreferencesManager preferencesManager = Injection.getSharedPreferenceManagerInstance();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (viewModel.isUserLoggedIn()) {
                startActivity(new Intent(SplashActivity.this, BottomMainActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finishAffinity();
        }, 1500L);
    }

    private void setViewModel() {
        SplashViewModelFactory factory = SplashViewModelFactory.getInstance(this);
        viewModel = new ViewModelProvider(this, factory)
                .get(SplashViewModel.class);
    }
}