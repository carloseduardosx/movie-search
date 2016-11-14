package com.carloseduardo.movie.search.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.carloseduardo.movie.search.data.model.Movie;
import com.carloseduardo.movie.search.data.source.MoviesRepository;
import com.carloseduardo.movie.search.movies.adapter.MoviesAdapter;

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
    public void listMovies() {

        moviesRepository.listMovies()
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {

                        view.showMovies(movies);
                    }
                });
    }

    @Override
    public void loadNextPage(int page, final RecyclerView view) {

        moviesRepository.loadNextPage(page)
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {

                        MoviesAdapter adapter = (MoviesAdapter) view.getAdapter();
                        List<Movie> currentMovies = adapter.getMovies();
                        int lastMoviesListSize = currentMovies.size();

                        currentMovies.addAll(movies);
                        adapter.notifyItemRangeChanged(lastMoviesListSize, lastMoviesListSize + 9);
                    }
                });
    }
}