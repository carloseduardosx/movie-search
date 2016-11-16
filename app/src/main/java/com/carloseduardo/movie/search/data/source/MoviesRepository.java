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
import io.realm.RealmResults;

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
    public Movie getMovie(int id) {

        return moviesLocalDataSource.getMovie(id);
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

                                                subscriber.onNext(lastMovies.subList(0, 10));
                                            } else {

                                                subscriber.onNext(lastMovies);
                                            }
                                        }
                                    });
                        } else {

                            subscriber.onNext(Collections.<Movie>emptyList());
                        }
                    } else {

                        subscriber.onNext(moviesLocalDataSource.extractFromRealm(realm, movies));
                    }
                } finally {

                    realm.close();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Movie>> loadNextSearchPage(final int page, final String search) {

        return Observable.create(new ObservableOnSubscribe<List<Movie>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<Movie>> subscriber) throws Exception {

                final Realm realm = RealmHelper.getInstance().getRealmInstance();

                List<Movie> movies = moviesLocalDataSource.pagination(page);

                try {

                    if (movies.isEmpty()) {

                        if (networkHelper.hasNetwork()) {

                            moviesRemoteDataSource.search(search)
                                    .subscribe(new Consumer<MoviesContent>() {
                                        @Override
                                        public void accept(MoviesContent moviesContent) throws Exception {

                                            List<Movie> lastMovies = moviesContent.getMovies();

                                            save(moviesContent);
                                            if (lastMovies.size() >= 10) {

                                                subscriber.onNext(lastMovies.subList(0, 10));
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
    public Observable<List<Movie>> listMovies(final boolean isToRemoveAll) {

        return Observable.create(new ObservableOnSubscribe<List<Movie>>() {

            @Override
            public void subscribe(final ObservableEmitter<List<Movie>> subscribe) throws Exception {

                MoviesContent moviesContent;

                if (isToRemoveAll) {

                    removeAll();
                    moviesContent = new MoviesContent();
                } else {

                    moviesContent = moviesLocalDataSource.listMovies();
                }

                RealmList<Movie> movies = moviesContent.getMovies();

                if (movies == null || movies.isEmpty()) {

                    if (networkHelper.hasNetwork()) {

                        moviesRemoteDataSource.listMovies()
                                .subscribe(new Consumer<MoviesContent>() {
                                    @Override
                                    public void accept(MoviesContent moviesContent) throws Exception {

                                        RealmList<Movie> movies = moviesContent.getMovies();
                                        Log.d(TAG, "Fetching data from internet");

                                        save(moviesContent);

                                        if (movies.size() >= 10) {

                                            subscribe.onNext(movies.subList(0, 10));
                                        } else {

                                            subscribe.onNext(movies);
                                        }
                                    }
                                });
                    } else {

                        subscribe.onNext(Collections.<Movie>emptyList());
                    }
                } else {

                    subscribe.onNext(movies.subList(0, movies.size() >= 10 ? 10 : movies.size() - 1));
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Movie>> searchMovies(final String search) {

        return Observable.create(new ObservableOnSubscribe<List<Movie>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<Movie>> subscribe) throws Exception {

                removeAll();

                if (networkHelper.hasNetwork()) {

                    moviesRemoteDataSource.search(search)
                            .subscribe(new Consumer<MoviesContent>() {
                                @Override
                                public void accept(MoviesContent moviesContent) throws Exception {

                                    RealmList<Movie> movies = moviesContent.getMovies();

                                    Log.d(TAG, "Fetching data from internet");

                                    save(moviesContent);

                                    if (movies.size() >= 10) {

                                        subscribe.onNext(movies.subList(0, 10));
                                    } else {

                                        subscribe.onNext(movies);
                                    }
                                }
                            });
                } else {

                    subscribe.onNext(Collections.<Movie>emptyList());
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void removeAll() {

        Realm realm = RealmHelper.getInstance().getRealmInstance();

        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    RealmResults<MoviesContent> moviesContent = realm.where(MoviesContent.class).findAll();

                    for (MoviesContent movieContent : moviesContent) {

                        movieContent.getMovies().deleteAllFromRealm();
                        movieContent.deleteFromRealm();
                    }
                }
            });
        } finally {

            realm.close();
        }
    }
}