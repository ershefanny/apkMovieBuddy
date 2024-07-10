package com.project.moviebuddy.ui.main.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.project.moviebuddy.data.model.User;
import com.project.moviebuddy.databinding.FragmentAccountBinding;
import com.project.moviebuddy.ui.myfilm.MyFilmActivity;
import com.project.moviebuddy.ui.splash.SplashActivity;

public class AccountFragment extends Fragment {
    private AccountViewModel viewModel;

    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);

        setViewModel();
        setProfileContents();
        setListeners();

        return binding.getRoot();
    }

    private void setProfileContents() {
        User user = viewModel.getUserData();
        binding.tvProfileName.setText(user.getUsername());
        binding.tvProfileEmail.setText(user.getEmail());
    }

    private void setListeners() {
        binding.btnMyFilm.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), MyFilmActivity.class));
        });

        binding.btnLogOut.setOnClickListener(view -> {
            viewModel.logout();
            requireActivity().startActivity(new Intent(requireActivity(), SplashActivity.class));
            requireActivity().finishAffinity();
        });
    }

    private void setViewModel() {
        AccountViewModelFactory factory = AccountViewModelFactory.getInstance(requireContext());
        viewModel = new ViewModelProvider(this, factory)
                .get(AccountViewModel.class);
    }
}