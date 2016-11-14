package com.carloseduardo.movie.search.movies.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carloseduardo.movie.search.R;
import com.carloseduardo.movie.search.data.model.Movie;
import com.carloseduardo.movie.search.data.source.constants.API;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{

    private List<Movie> movies;

    public MoviesAdapter(List<Movie> movies) {

        this.movies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Movie movie = movies.get(position);
        String endpoint = API.IMG_ENDPOINT;
        String imagePath = movie.getPosterPath();

        holder.overview.setText(movie.getOverview());
        Picasso.with(holder.movieImage.getContext())
                .load(endpoint + imagePath)
                .into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public List<Movie> getMovies() {

        return movies;
    }

    public void addMovies(@NonNull List<Movie> movies) {

        this.movies.addAll(movies);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout parentView;
        ImageView movieImage;
        TextView overview;

        ViewHolder(View rootView) {
            super(rootView);
            parentView = (LinearLayout) rootView.findViewById(R.id.card_item_container);
            movieImage = (ImageView) parentView.findViewById(R.id.movie_img);
            overview = (TextView) parentView.findViewById(R.id.movie_overview);
        }
    }
}