package com.carloseduardo.movie.search.ui.movies;

import android.support.v7.widget.RecyclerView;

import com.carloseduardo.movie.search.base.BasePresenter;
import com.carloseduardo.movie.search.base.BaseView;
import com.carloseduardo.movie.search.data.model.Movie;
import com.carloseduardo.movie.search.ui.movies.listener.EndlessScrollListener;

import java.util.List;

public interface MoviesContract {

    interface View extends BaseView<Presenter> {

        void showMovies(List<Movie> movies);

        void showSearchedMovies(List<Movie> movies);

        void hideWithoutNetwork();

        void showWithoutNetwork();

        void hideEmptyResults();

        void showEmptyResults();

        void hideRefreshingLayout();

        void hideTopNavigation();
    }

    interface Presenter extends BasePresenter {

        void listMovies(final boolean isToRemoveAll);

        void searchMovies(String search);

        void loadNextPage(int page, final RecyclerView view, final EndlessScrollListener endlessScrollListener);

        void loadNextSearchPage(int page, final RecyclerView view,
                                final EndlessScrollListener endlessScrollListener, String search);
    }
}