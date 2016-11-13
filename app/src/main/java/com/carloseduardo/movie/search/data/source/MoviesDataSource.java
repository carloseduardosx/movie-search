package com.carloseduardo.movie.search.data.source;

import com.carloseduardo.movie.search.data.model.Movie;

import java.util.List;

public interface MoviesDataSource {

    void save(Movie movie);

    void save(List<Movie> movies);

    Movie getMovie();

    List<Movie> getMovies();

    void remove(Movie movie);

    void remove(List<Movie> movies);
}
