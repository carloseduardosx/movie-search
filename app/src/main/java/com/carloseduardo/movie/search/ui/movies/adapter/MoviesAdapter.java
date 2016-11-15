package com.carloseduardo.movie.search.ui.movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.carloseduardo.movie.search.R;
import com.carloseduardo.movie.search.ui.constants.BundleKey;
import com.carloseduardo.movie.search.data.model.Movie;
import com.carloseduardo.movie.search.data.source.constants.API;
import com.carloseduardo.movie.search.ui.moviedetail.MovieDetailActivity;
import com.lid.lib.LabelImageView;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Movie movie = movies.get(position);
        Date releaseDate = movie.getReleaseDate();
        String endpoint = API.IMG_ENDPOINT;
        String imagePath = movie.getBackdropPath();
        Calendar movieDate = Calendar.getInstance();
        movieDate.setTime(releaseDate == null ? new Date() : releaseDate);

        Picasso.with(holder.movieImage.getContext())
                .load(endpoint + imagePath)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(holder.movieImage);

        configureListener(holder, movie);
        holder.overview.setText(movie.getOverview());
        holder.title.setText(movie.getTitle());
        holder.movieImage.setLabelText(String.valueOf(releaseDate == null ? "" : movieDate.get(Calendar.YEAR)));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public List<Movie> getMovies() {

        return movies;
    }

    private void configureListener(final ViewHolder holder, final Movie movie) {

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = holder.rootView.getContext();
                Intent movieDetailActivityIntent = new Intent(context, MovieDetailActivity.class);

                movieDetailActivityIntent.putExtra(BundleKey.MOVIE_ID, movie.getId());
                context.startActivity(movieDetailActivityIntent);
            }
        });
    }

    public void addMovies(@NonNull List<Movie> movies) {

        this.movies.addAll(movies);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView rootView;
        LinearLayout parentView;
        RelativeLayout parentTextView;
        LabelImageView movieImage;
        TextView overview;
        TextView title;

        ViewHolder(View rootView) {
            super(rootView);
            this.rootView = (CardView) rootView;
            parentView = (LinearLayout) rootView.findViewById(R.id.card_container);
            movieImage = (LabelImageView) parentView.findViewById(R.id.movie_img);
            parentTextView = (RelativeLayout) parentView.findViewById(R.id.card_text_container);
            overview = (TextView) parentTextView.findViewById(R.id.movie_overview);
            title = (TextView) parentTextView.findViewById(R.id.movie_title);
        }
    }
}