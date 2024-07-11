package com.project.moviebuddy.ui.myfilm;

import static com.project.moviebuddy.ui.detail.DetailActivity.EXTRA_FILM;
import static com.project.moviebuddy.ui.detail.DetailActivity.IS_EDIT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.project.moviebuddy.R;
import com.project.moviebuddy.data.model.Film;
import com.project.moviebuddy.databinding.ActivityMyFilmBinding;
import com.project.moviebuddy.ui.adapters.FilmAdapter;
import com.project.moviebuddy.ui.detail.DetailActivity;
import com.project.moviebuddy.ui.main.ui.addfilm.AddFilmViewModel;
import com.project.moviebuddy.ui.main.ui.addfilm.AddFilmViewModelFactory;

import java.util.ArrayList;

public class MyFilmActivity extends AppCompatActivity {
    private MyFilmViewModel viewModel;

    private ActivityMyFilmBinding binding;

    private int userID = -1;

    private final FilmAdapter filmAdapter = new FilmAdapter();

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    setRecyclerView();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyFilmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setViewModel();
        setRecyclerView();
        setListeners();
    }

    private void setRecyclerView() {
        ArrayList<Film> filmArrayList = viewModel.getMyFilms(String.valueOf(userID));
        filmAdapter.setFilmList(filmArrayList);
        binding.rvFilms.setAdapter(filmAdapter);
        binding.rvFilms.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void setListeners() {
        binding.toolbar.setNavigationOnClickListener(view -> finish());

        filmAdapter.setOnFilmClickListener(film -> {
            Intent iDetailEdit = new Intent(MyFilmActivity.this, DetailActivity.class);
            iDetailEdit.putExtra(IS_EDIT, true);
            iDetailEdit.putExtra(EXTRA_FILM, film);
            startForResult.launch(iDetailEdit);
        });
    }

    private void setViewModel() {
        MyFilmViewModelFactory factory = MyFilmViewModelFactory.getInstance(this);
        viewModel = new ViewModelProvider(this, factory)
                .get(MyFilmViewModel.class);

        userID = viewModel.getUserID();
    }
}