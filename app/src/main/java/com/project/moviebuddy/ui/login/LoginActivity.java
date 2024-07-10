package com.project.moviebuddy.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.project.moviebuddy.databinding.ActivityLoginBinding;
import com.project.moviebuddy.ui.main.BottomMainActivity;
import com.project.moviebuddy.ui.register.RegisterActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel viewModel;

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setViewModel();

        setListeners();
    }

    private void setListeners() {
        binding.btnLogin.setOnClickListener(view -> {
            if (isValid()) {
                String username = Objects.requireNonNull(binding.edUsername.getText()).toString();
                String password = Objects.requireNonNull(binding.edPassword.getText()).toString();

                boolean result = viewModel.loginUser(username, password);

                if (result) {
                    startActivity(new Intent(LoginActivity.this, BottomMainActivity.class));
                    finishAffinity();
                } else {
                    showToast("Invalid Credentials!");
                }
            }
        });

        binding.tvSignInRedirect.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private boolean isValid() {
        if (Objects.requireNonNull(binding.edUsername.getText()).toString().isEmpty()) {
            showToast("Invalid Username!");
            return false;
        } else if (binding.edPassword.getError() != null ||
                Objects.requireNonNull(binding.edPassword.getText()).toString().isEmpty()) {
            showToast("Invalid Password!");
            return false;
        } else {
            return true;
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setViewModel() {
        LoginViewModelFactory factory = LoginViewModelFactory.getInstance(this);
        viewModel = new ViewModelProvider(this, factory)
                .get(LoginViewModel.class);
    }
}