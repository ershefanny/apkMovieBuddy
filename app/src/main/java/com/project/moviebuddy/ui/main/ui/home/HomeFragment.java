package com.project.moviebuddy.ui.main.ui.home;

import static com.project.moviebuddy.ui.detail.DetailActivity.EXTRA_FILM;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.project.moviebuddy.data.model.Film;
import com.project.moviebuddy.data.model.User;
import com.project.moviebuddy.databinding.FragmentHomeBinding;
import com.project.moviebuddy.di.Injection;
import com.project.moviebuddy.ui.adapters.FilmAdapter;
import com.project.moviebuddy.ui.detail.DetailActivity;
import com.project.moviebuddy.ui.myfilm.MyFilmViewModel;
import com.project.moviebuddy.ui.myfilm.MyFilmViewModelFactory;
import com.project.moviebuddy.utils.SharedPreferencesManager;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private HomeViewModel viewModel;

    private FragmentHomeBinding binding;

    private final FilmAdapter filmAdapter = new FilmAdapter();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        setViewModel();
        setUserInfo();

        setRecyclerView();
        setListeners();

        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void setUserInfo() {
        binding.tvUsername.setText(" " + viewModel.getSavedUser().getUsername() + "!");
    }

    private void setRecyclerView() {
        ArrayList<Film> filmArrayList = viewModel.getFilms();
        filmAdapter.setFilmList(filmArrayList);
        binding.rvFilms.setAdapter(filmAdapter);
        binding.rvFilms.setLayoutManager(new GridLayoutManager(requireContext(), 2));
    }

    private void setListeners() {

        filmAdapter.setOnFilmClickListener(film -> {
            Intent iDetailEdit = new Intent(requireActivity(), DetailActivity.class);
            iDetailEdit.putExtra(EXTRA_FILM, film);
            startActivity(iDetailEdit);
        });
    }

    private void setViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(requireContext());
        viewModel = new ViewModelProvider(this, factory)
                .get(HomeViewModel.class);
    }
}