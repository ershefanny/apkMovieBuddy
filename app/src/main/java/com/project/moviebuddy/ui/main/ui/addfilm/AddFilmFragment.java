package com.project.moviebuddy.ui.main.ui.addfilm;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.project.moviebuddy.R;
import com.project.moviebuddy.data.local.DatabaseContract;
import com.project.moviebuddy.databinding.FragmentAddFilmBinding;

import java.util.Objects;

public class AddFilmFragment extends Fragment {
    private AddFilmViewModel viewModel;

    private FragmentAddFilmBinding binding;

    private ActivityResultLauncher<PickVisualMediaRequest> galleryLauncher;

    private int userID = -1;
    private String imageUriPath;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddFilmBinding.inflate(inflater, container, false);
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.PickVisualMedia(),
                uri -> {
                    if (uri != null) {
                        try {
                            requireActivity().grantUriPermission(requireActivity().getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            requireContext().getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
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

        setViewModel();

        setListeners();

        return binding.getRoot();
    }

    private void setListeners() {
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

                long result = viewModel.addFilm(values);
                if (result > -1) {
                    binding.ivFilmImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.placeholder_add_image));
                    binding.edJudul.setText("");
                    binding.edDeskripsi.setText("");
                    binding.edRating.setText("");

                    showToast("Successfully added Film!");
                } else {
                    showToast("Failed to add film!");
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
        AddFilmViewModelFactory factory = AddFilmViewModelFactory.getInstance(requireContext());
        viewModel = new ViewModelProvider(this, factory)
                .get(AddFilmViewModel.class);

        userID = viewModel.getUserID();
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}