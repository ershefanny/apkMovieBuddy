package com.project.moviebuddy.ui.register;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.project.moviebuddy.data.local.DatabaseContract;
import com.project.moviebuddy.databinding.ActivityRegisterBinding;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private RegisterViewModel viewModel;

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setViewModel();

        setListeners();
    }

    private void setListeners() {
        binding.btnRegister.setOnClickListener(view -> {
            if (isValid()) {
                ContentValues values = new ContentValues();
                values.put(DatabaseContract.UserColumns.USERNAME, Objects.requireNonNull(binding.etRegisterName.getText()).toString());
                values.put(DatabaseContract.UserColumns.EMAIL, Objects.requireNonNull(binding.etRegisterEmail.getText()).toString());
                values.put(DatabaseContract.UserColumns.PASSWORD, Objects.requireNonNull(binding.etRegisterPassword.getText()).toString());

                long result = viewModel.registerUser(values);
                if (result > -1) {
                    showToast("Successfully registered User! Please log in");
                    finish();
                } else {
                    showToast("Failed to register User!");
                }
            }
        });

        binding.tvLogInRedirect.setOnClickListener(view -> {
            finish();
        });
    }

    private boolean isValid() {
        if (Objects.requireNonNull(binding.etRegisterName.getText()).toString().isEmpty()) {
            showToast("Invalid Username!");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Objects.requireNonNull(binding.etRegisterEmail.getText()).toString()).matches()
                && !binding.etRegisterEmail.getText().toString().isEmpty()) {
            showToast("Invalid Email!");
            return false;
        } else if (binding.etRegisterPassword.getError() != null ||
                Objects.requireNonNull(binding.etRegisterPassword.getText()).toString().isEmpty()) {
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
        RegisterViewModelFactory factory = RegisterViewModelFactory.getInstance(this);
        viewModel = new ViewModelProvider(this, factory)
                .get(RegisterViewModel.class);
    }
}