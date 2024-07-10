package com.project.moviebuddy.ui.adapters;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.project.moviebuddy.data.model.Film;
import com.project.moviebuddy.databinding.ItemMovieGridBinding;

import java.util.ArrayList;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.MainViewHolder> {

    private List<Film> filmList = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setFilmList(List<Film> filmList) {
        this.filmList = filmList;
        notifyDataSetChanged();
    }


    public interface OnFilmClick {
        void onFilmClick(Film film);
    }

    private OnFilmClick onFilmClick;

    public void setOnFilmClickListener(OnFilmClick listener) {
        this.onFilmClick = listener;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        private final ItemMovieGridBinding itemBinding;

        public MainViewHolder(ItemMovieGridBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        public void bindItem(Film film) {
            itemBinding.ivFilmImage.setImageURI(Uri.parse(film.getImagePath()));
            itemBinding.tvFilmName.setText(film.getFilmName());
            itemBinding.tvFilmRating.setText(String.valueOf(film.getFilmRating()));

            itemBinding.cardLayout.setOnClickListener(view -> {
                if (onFilmClick != null) {
                    onFilmClick.onFilmClick(film);
                }
            });
        }
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(
                ItemMovieGridBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        Film film = filmList.get(position);
        holder.bindItem(film);
    }
}