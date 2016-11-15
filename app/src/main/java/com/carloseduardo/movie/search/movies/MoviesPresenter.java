package com.carloseduardo.movie.search.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.carloseduardo.movie.search.data.model.Movie;
import com.carloseduardo.movie.search.data.source.MoviesRepository;
import com.carloseduardo.movie.search.movies.adapter.MoviesAdapter;
import com.carloseduardo.movie.search.movies.listener.EndlessScrollListener;

import java.util.List;

import io.reactivex.functions.Consumer;

public class MoviesPresenter implements MoviesContract.Presenter {

    private MoviesContract.View view;
    private MoviesRepository moviesRepository;

    public MoviesPresenter(@NonNull MoviesContract.View view,
                            @NonNull MoviesRepository moviesRepository) {

        this.moviesRepository = moviesRepository;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void listMovies(final boolean isToRemoveAll) {

        moviesRepository.listMovies(isToRemoveAll)
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {

                        if (movies.isEmpty()) {

                            view.showWithoutNetwork();
                        } else {

                            view.hideWithoutNetwork();
                            view.hideEmptyResults();
                            view.showMovies(movies);
                        }
                    }
                });
    }

    @Override
    public void searchMovies(String search) {

        moviesRepository.searchMovies(search)
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {

                        if (movies.isEmpty()) {

                            view.showEmptyResults();
                        } else {

                            view.hideEmptyResults();
                            view.showSearchedMovies(movies);
                        }
                    }
                });
    }

    @Override
    public void loadNextPage(int page, final RecyclerView view,
                             final EndlessScrollListener endlessScrollListener) {

        moviesRepository.loadNextPage(page)
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {

                        if (!movies.isEmpty()) {

                            MoviesAdapter adapter = (MoviesAdapter) view.getAdapter();
                            List<Movie> currentMovies = adapter.getMovies();
                            int lastMoviesListSize = currentMovies.size();

                            currentMovies.addAll(movies);
                            adapter.notifyItemRangeChanged(lastMoviesListSize, lastMoviesListSize + 9);
                        } else {

                            endlessScrollListener.backPage();
                        }
                    }
                });
    }

    @Override
    public void loadNextSearchPage(int page, final RecyclerView view,
                                   final EndlessScrollListener endlessScrollListener, String search) {

        moviesRepository.loadNextSearchPage(page, search)
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {

                        if (!movies.isEmpty()) {

                            MoviesAdapter adapter = (MoviesAdapter) view.getAdapter();
                            List<Movie> currentMovies = adapter.getMovies();
                            int lastMoviesListSize = currentMovies.size();

                            currentMovies.addAll(movies);
                            adapter.notifyItemRangeChanged(lastMoviesListSize, lastMoviesListSize + 9);
                        } else {

                            endlessScrollListener.backPage();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        MoviesPresenter.this.view.showEmptyResults();
                    }
                });
    }
}