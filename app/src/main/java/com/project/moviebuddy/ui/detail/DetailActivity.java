package com.project.moviebuddy.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.project.moviebuddy.R;
import com.project.moviebuddy.data.model.Film;
import com.project.moviebuddy.databinding.ActivityDetailBinding;
import com.project.moviebuddy.ui.adapters.FilmAdapter;
import com.project.moviebuddy.ui.edit.EditFilmActivity;

import java.util.ArrayList;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    private DetailViewModel viewModel;

    private ActivityDetailBinding binding;

    private int userID = -1;

    private boolean isEdit = false;
    private Film film;

    private final FilmAdapter filmAdapter = new FilmAdapter();

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Film film = (Film) Objects.requireNonNull(Objects.requireNonNull(result.getData()).getExtras()).getSerializable(EXTRA_FILM);
                    if (film != null) {
                        android.util.Log.e("FTEST", "judul gantie -> " + film.getFilmName());

                        setContents(film);
                        setRecyclerView();
                    }

                    setResult(RESULT_OK);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setViewModel();

        isEdit = getIntent().getBooleanExtra(IS_EDIT, false);
        film = (Film) Objects.requireNonNull(getIntent().getExtras()).getSerializable(EXTRA_FILM);

        if (film != null) {
            setContents(film);
        }

        setRecyclerView();
        setToolbar();

        setListeners();
    }

    private void setContents(Film film) {
        binding.ivBackdrop.setImageURI(Uri.parse(film.getImagePath()));
        binding.ivFilmImage.setImageURI(Uri.parse(film.getImagePath()));

        binding.tvFilmName.setText(film.getFilmName());
        binding.tvFilmRating.setText(String.valueOf(film.getFilmRating()));
        binding.tvDesc.setText(film.getFilmDesc());
    }

    private void setToolbar() {
        if (isEdit) {
            binding.toolbar.inflateMenu(R.menu.detail_menu);
            binding.toolbar.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_edit) {
                    Intent iEdit = new Intent(DetailActivity.this, EditFilmActivity.class);
                    iEdit.putExtra(EXTRA_FILM, film);
                    startForResult.launch(iEdit);
                } else if (item.getItemId() == R.id.menu_delete) {
                    viewModel.deleteFilm(String.valueOf(film.getFilmID()), String.valueOf(userID));
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
                return true;
            });
        }
    }

    private void setRecyclerView() {
        ArrayList<Film> filmArrayList = viewModel.getFilms();
        filmAdapter.setFilmList(filmArrayList);
        binding.rvFilms.setAdapter(filmAdapter);
        binding.rvFilms.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void setListeners() {
        binding.toolbar.setNavigationOnClickListener(view -> finish());

        filmAdapter.setOnFilmClickListener(film -> {
            Intent iDetailEdit = new Intent(DetailActivity.this, DetailActivity.class);
            iDetailEdit.putExtra(EXTRA_FILM, film);
            startActivity(iDetailEdit);
        });
    }

    private void setViewModel() {
        DetailViewModelFactory factory = DetailViewModelFactory.getInstance(this);
        viewModel = new ViewModelProvider(this, factory)
                .get(DetailViewModel.class);

        userID = viewModel.getUserID();
    }

    public static final String EXTRA_FILM = "extra_film";
    public static final String IS_EDIT = "is_edit";
}