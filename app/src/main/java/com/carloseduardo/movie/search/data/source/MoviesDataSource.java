package com.carloseduardo.movie.search.data.source;

import com.carloseduardo.movie.search.data.model.Movie;
import com.carloseduardo.movie.search.data.model.MoviesContent;

import java.util.List;

import io.reactivex.Observable;

public interface MoviesDataSource {

    void save(MoviesContent moviesContent);

    Movie getMovie(int id);

    Observable<List<Movie>> loadNextPage(final int page);

    Observable<List<Movie>> loadNextSearchPage(final int page, String search);

    Observable<List<Movie>> listMovies(final boolean isToRemoveAll);

    Observable<List<Movie>> searchMovies(String search);

    void removeAll();
}