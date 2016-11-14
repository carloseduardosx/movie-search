package com.carloseduardo.movie.search.data.source;

import android.util.Log;

import com.carloseduardo.movie.search.data.model.Movie;
import com.carloseduardo.movie.search.data.model.MoviesContent;
import com.carloseduardo.movie.search.data.source.local.MoviesLocalDataSource;
import com.carloseduardo.movie.search.data.source.remote.MoviesRemoteDataSource;
import com.carloseduardo.movie.search.data.source.remote.network.MovieNetwork;
import com.carloseduardo.movie.search.helper.NetworkHelper;
import com.carloseduardo.movie.search.helper.RealmHelper;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmList;

public class MoviesRepository implements MoviesDataSource {

    private final String TAG = "MoviesRepository";
    private final NetworkHelper networkHelper;
    private final MoviesLocalDataSource moviesLocalDataSource;
    private final MoviesRemoteDataSource moviesRemoteDataSource;

    public MoviesRepository(MovieNetwork movieNetwork, NetworkHelper networkHelper) {

        this.networkHelper = networkHelper;
        moviesLocalDataSource = new MoviesLocalDataSource();
        moviesRemoteDataSource = new MoviesRemoteDataSource(movieNetwork);
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
    public Observable<List<Movie>> loadNextPage(final int page) {

        return Observable.create(new ObservableOnSubscribe<List<Movie>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<Movie>> subscriber) throws Exception {

                final Realm realm = RealmHelper.getInstance().getRealmInstance();
                List<Movie> movies = moviesLocalDataSource.pagination(page);

                try {

                    if (movies.isEmpty()) {

                        if (networkHelper.hasNetwork()) {

                            moviesRemoteDataSource.listMovies()
                                    .subscribe(new Consumer<MoviesContent>() {
                                        @Override
                                        public void accept(MoviesContent moviesContent) throws Exception {

                                            List<Movie> lastMovies = moviesContent.getMovies();

                                            save(moviesContent);
                                            if (lastMovies.size() >= 10) {

                                                subscriber.onNext(lastMovies.subList(0, 9));
                                            } else {

                                                subscriber.onNext(lastMovies);
                                            }
                                        }
                                    });
                        } else {

                            subscriber.onNext(Collections.<Movie>emptyList());
                        }
                    } else {

                        subscriber.onNext(realm.copyFromRealm(movies));
                    }
                } finally {

                    realm.close();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Movie>> listMovies() {

        return Observable.create(new ObservableOnSubscribe<List<Movie>>() {

            @Override
            public void subscribe(final ObservableEmitter<List<Movie>> subscribe) throws Exception {

                MoviesContent moviesContent = moviesLocalDataSource.listMovies();

                RealmList<Movie> movies = moviesContent.getMovies();

                if (movies == null || movies.isEmpty()) {

                    if (networkHelper.hasNetwork()) {

                        moviesRemoteDataSource.listMovies()
                                .subscribe(new Consumer<MoviesContent>() {
                                    @Override
                                    public void accept(MoviesContent moviesContent) throws Exception {

                                        Log.d(TAG, "Fetching data from internet");

                                        save(moviesContent);
                                        subscribe.onNext(moviesContent.getMovies().subList(0, 9));
                                    }
                                });
                    } else {

                        subscribe.onNext(Collections.<Movie>emptyList());
                    }
                } else {

                    subscribe.onNext(movies.subList(0, 9));
                }
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