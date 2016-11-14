package com.carloseduardo.movie.search.movies;

import android.support.v7.widget.RecyclerView;

import com.carloseduardo.movie.search.base.BasePresenter;
import com.carloseduardo.movie.search.base.BaseView;
import com.carloseduardo.movie.search.data.model.Movie;

import java.util.List;

public interface MoviesContract {

    interface View extends BaseView<Presenter> {

        void showMovies(List<Movie> movies);
    }

    interface Presenter extends BasePresenter {

        void listMovies();

        void loadNextPage(int page, final RecyclerView view);
    }
}