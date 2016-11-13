package com.carloseduardo.movie.search.data.source.remote;

import com.carloseduardo.movie.search.data.model.MoviesContent;
import com.carloseduardo.movie.search.data.source.remote.network.MovieNetwork;

import io.reactivex.Observable;

public class MoviesRemoteDataSource {

    private MovieNetwork movieNetwork;

    public MoviesRemoteDataSource(MovieNetwork movieNetwork) {

        this.movieNetwork = movieNetwork;
    }

    public Observable<MoviesContent> listMovies() {

        return movieNetwork.listMovies(1);
    }
}