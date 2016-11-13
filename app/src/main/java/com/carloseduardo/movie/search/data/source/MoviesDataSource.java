package com.carloseduardo.movie.search.data.source;

import com.carloseduardo.movie.search.data.model.Movie;
import com.carloseduardo.movie.search.data.model.MoviesContent;

import java.util.List;

import io.reactivex.Observable;

public interface MoviesDataSource {

    void save(MoviesContent moviesContent);

    Observable<Movie> getMovie();

    Observable<List<Movie>> listMovies();

    void remove(Movie movie);

    void remove(List<Movie> movies);
}
