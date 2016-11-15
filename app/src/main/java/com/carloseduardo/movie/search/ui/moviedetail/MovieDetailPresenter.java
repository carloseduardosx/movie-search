package com.carloseduardo.movie.search.ui.moviedetail;

import android.support.annotation.NonNull;

import com.carloseduardo.movie.search.data.model.Movie;
import com.carloseduardo.movie.search.data.source.MoviesRepository;

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private MovieDetailContract.View view;
    private MoviesRepository moviesRepository;

    public MovieDetailPresenter(@NonNull MovieDetailContract.View view,
                                @NonNull MoviesRepository moviesRepository) {

        this.view = view;
        this.moviesRepository = moviesRepository;
        this.view.setPresenter(this);
    }


    @Override
    public Movie getMovie(int id) {

        return moviesRepository.getMovie(id);
    }
}