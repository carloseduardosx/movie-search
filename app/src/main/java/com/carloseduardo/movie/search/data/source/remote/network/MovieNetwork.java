package com.carloseduardo.movie.search.data.source.remote.network;

import com.carloseduardo.movie.search.data.model.Movie;
import com.carloseduardo.movie.search.data.model.MoviesContent;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieNetwork {

    @GET(value = "/3/discover/movie?sort_by=popularity.desc")
    Observable<MoviesContent> listMovies(@Query(value = "page") int page);

    @GET(value = "/3/search/movie")
    Observable<List<Movie>> searchMovies(@Query(value = "query") String search);
}