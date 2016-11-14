package com.carloseduardo.movie.search.data.source.remote;

import com.carloseduardo.movie.search.data.model.MoviesContent;
import com.carloseduardo.movie.search.data.source.remote.network.MovieNetwork;
import com.carloseduardo.movie.search.helper.RealmHelper;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MoviesRemoteDataSource {

    private MovieNetwork movieNetwork;
    private RealmHelper realmHelper = RealmHelper.getInstance();

    public MoviesRemoteDataSource(MovieNetwork movieNetwork) {

        this.movieNetwork = movieNetwork;
    }

    public Observable<MoviesContent> listMovies() {

        Realm realm = realmHelper.getRealmInstance();

        try {

            RealmResults<MoviesContent> results = realm.where(MoviesContent.class)
                    .findAll();

            if (results.isEmpty()) {

                return movieNetwork.listMovies(1);
            } else {

                Integer lastPage = results.sort(MoviesContent.ID, Sort.ASCENDING)
                        .last()
                        .getId();

                return movieNetwork.listMovies(lastPage + 1);
            }
        } finally {

            realm.close();
        }
    }
}