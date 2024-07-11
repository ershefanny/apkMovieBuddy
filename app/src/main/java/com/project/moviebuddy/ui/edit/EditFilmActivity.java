package com.project.moviebuddy.ui.edit;

import static com.project.moviebuddy.ui.detail.DetailActivity.EXTRA_FILM;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.project.moviebuddy.R;
import com.project.moviebuddy.data.local.DatabaseContract;
import com.project.moviebuddy.data.model.Film;
import com.project.moviebuddy.databinding.ActivityEditFilmBinding;
import com.project.moviebuddy.ui.main.ui.addfilm.AddFilmViewModel;
import com.project.moviebuddy.ui.main.ui.addfilm.AddFilmViewModelFactory;

import java.util.Objects;

public class EditFilmActivity extends AppCompatActivity {
    private EditFilmViewModel viewModel;

    private ActivityEditFilmBinding binding;

    private ActivityResultLauncher<PickVisualMediaRequest> galleryLauncher;

    private int userID = -1;
    private String imageUriPath;

    private Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditFilmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        film = (Film) Objects.requireNonNull(getIntent().getExtras()).getSerializable(EXTRA_FILM);

        setViewModel();

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.PickVisualMedia(),
                uri -> {
                    if (uri != null) {
                        try {
                            this.grantUriPermission(this.getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        } catch (SecurityException e) {
                            Log.e("FTEST", e.toString());
                        }

                        binding.ivFilmImage.setImageURI(uri);
                        imageUriPath = uri.toString();
                    } else {
                        Log.d("Photo Picker", "No media selected");
                    }
                }
        );

        setContents();
        setListeners();
    }

    private void setContents() {
        imageUriPath = film.getImagePath();
        binding.ivFilmImage.setImageURI(Uri.parse(imageUriPath));
        binding.edJudul.setText(film.getFilmName());
        binding.edDeskripsi.setText(film.getFilmDesc());
        binding.edRating.setText(String.valueOf(film.getFilmRating()));
    }

    private void setListeners() {
        binding.toolbar.setNavigationOnClickListener(view -> finish());

        binding.btnAddImage.setOnClickListener(view -> {
            galleryLauncher.launch(new PickVisualMediaRequest());
        });

        binding.btnSubmit.setOnClickListener(view -> {
            if (isValid()) {
                String judul = Objects.requireNonNull(binding.edJudul.getText()).toString();
                String deskripsi = Objects.requireNonNull(binding.edDeskripsi.getText()).toString();
                float rating = Float.parseFloat(Objects.requireNonNull(binding.edRating.getText()).toString());

                ContentValues values = new ContentValues();
                values.put(DatabaseContract.FilmColumns.USER_ID, userID);
                values.put(DatabaseContract.FilmColumns.IMAGE_PATH, imageUriPath);
                values.put(DatabaseContract.FilmColumns.NAME, judul);
                values.put(DatabaseContract.FilmColumns.DESC, deskripsi);
                values.put(DatabaseContract.FilmColumns.RATING, rating);

                film.setImagePath(imageUriPath);
                film.setFilmDesc(deskripsi);
                film.setFilmName(judul);
                film.setFilmRating(rating);

                android.util.Log.e("FTEST", "judul -> " + film.getFilmName());

                int result = viewModel.updateFilm(String.valueOf(film.getFilmID()), String.valueOf(userID), values);
                if (result > -1) {
                    binding.ivFilmImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.placeholder_add_image));
                    binding.edJudul.setText("");
                    binding.edDeskripsi.setText("");
                    binding.edRating.setText("");

                    showToast("Successfully updated Film!");
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_FILM, film);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    showToast("Failed to update film!");
                }
            }
        });
    }

    private boolean isValid() {
        if (imageUriPath == null) {
            showToast("Fill image first!");
            return false;
        } else if (Objects.requireNonNull(binding.edJudul.getText()).toString().isEmpty()) {
            showToast("Invalid Judul!");
            return false;
        } else if (Objects.requireNonNull(binding.edDeskripsi.getText()).toString().isEmpty()) {
            showToast("Invalid Deskripsi!");
            return false;
        } else if (Objects.requireNonNull(binding.edRating.getText()).toString().isEmpty()) {
            showToast("Invalid Judul!");
            return false;
        } else {
            return true;
        }
    }

    private void setViewModel() {
        EditFilmViewModelFactory factory = EditFilmViewModelFactory.getInstance(this);
        viewModel = new ViewModelProvider(this, factory)
                .get(EditFilmViewModel.class);

        userID = viewModel.getUserID();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}