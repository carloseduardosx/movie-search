package com.carloseduardo.movie.search.ui.moviedetail;

import com.carloseduardo.movie.search.base.BasePresenter;
import com.carloseduardo.movie.search.base.BaseView;
import com.carloseduardo.movie.search.data.model.Movie;

public interface MovieDetailContract {

    interface View extends BaseView<Presenter> {

        void showMovie(Movie movie);

        void showNoResultFound();
    }

    interface Presenter extends BasePresenter {

        Movie getMovie(int id);
    }
}