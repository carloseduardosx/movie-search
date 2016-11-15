package com.carloseduardo.movie.search.data.source.remote.network;

import com.carloseduardo.movie.search.data.model.MoviesContent;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieNetwork {

    @GET(value = "/3/discover/movie?sort_by=popularity.desc")
    Observable<MoviesContent> listMovies(@Query(value = "page") int page);

    @GET(value = "/3/search/movie")
    Observable<MoviesContent> searchMovies(@Query(value = "page") int page,
                                           @Query(value = "query") String search);
}