package com.carloseduardo.movie.search.data.source;

import android.util.Log;

import com.carloseduardo.movie.search.data.model.Movie;
import com.carloseduardo.movie.search.data.model.MoviesContent;
import com.carloseduardo.movie.search.data.source.local.MoviesLocalDataSource;
import com.carloseduardo.movie.search.data.source.remote.MoviesRemoteDataSource;
import com.carloseduardo.movie.search.data.source.remote.network.MovieNetwork;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmList;

public class MoviesRepository implements MoviesDataSource {

    private final String TAG = "MoviesRepository";
    private final MoviesLocalDataSource moviesLocalDataSource;
    private final MoviesRemoteDataSource moviesRemoteDataSource;

    public MoviesRepository(MovieNetwork movieNetwork) {

        moviesLocalDataSource = new MoviesLocalDataSource();
        moviesRemoteDataSource =  new MoviesRemoteDataSource(movieNetwork);
    }

    @Override
    public void save(MoviesContent moviesContent) {

        moviesLocalDataSource.save(moviesContent);
    }

    @Override
    public Observable<Movie> getMovie() {
        return null;
    }

    @Override
    public Observable<List<Movie>> listMovies() {

        return Observable.create(new ObservableOnSubscribe<List<Movie>>() {

            @Override
            public void subscribe(final ObservableEmitter<List<Movie>> subscribe) throws Exception {

                moviesLocalDataSource.listMovies()
                        .subscribe(new Consumer<MoviesContent>() {
                            @Override
                            public void accept(MoviesContent moviesContent) throws Exception {

                                RealmList<Movie> movies = moviesContent.getMovies();

                                if (movies == null || movies.isEmpty()) {

                                    moviesRemoteDataSource.listMovies()
                                            .subscribe(new Consumer<MoviesContent>() {
                                                @Override
                                                public void accept(MoviesContent moviesContent) throws Exception {

                                                    Log.d(TAG, "Fetching data from internet");

                                                    MoviesContent moviesContentToSave = new MoviesContent();

                                                    moviesContentToSave.setId(moviesContent.getId());
                                                    moviesContentToSave.setMovies(moviesContent.getMovies());
                                                    moviesContentToSave.setTotalPages(moviesContent.getTotalPages());
                                                    moviesContentToSave.setTotalResults(moviesContent.getTotalResults());

                                                    save(moviesContentToSave);
                                                    subscribe.onNext(moviesContent.getMovies());
                                                }
                                            });
                                } else {

                                    subscribe.onNext(movies);
                                }
                            }
                        });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void remove(Movie movie) {

    }

    @Override
    public void remove(List<Movie> movies) {

    }
}