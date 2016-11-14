package com.carloseduardo.movie.search.data.source.local;

import com.carloseduardo.movie.search.data.model.Movie;
import com.carloseduardo.movie.search.data.model.MoviesContent;
import com.carloseduardo.movie.search.helper.RealmHelper;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MoviesLocalDataSource {

    private RealmHelper realmHelper = RealmHelper.getInstance();

    public MoviesContent listMovies() {

        Realm realm = realmHelper.getRealmInstance();

        try {

            MoviesContent moviesContent;
            try {

                moviesContent = realm.where(MoviesContent.class)
                        .findFirst();
            } catch (NullPointerException e) {

                /*
                Work around needed because of a bug at Realm querying
                Realm doesn't get the reference for the MoviesContent table version
                Until the realm instance is closed and reopened again
                Should be resolved at later release
                 */
                if (isRealmNotClosed(realm)) {

                    realm.close();
                }
                realm = realmHelper.getRealmInstance();

                moviesContent = realm.where(MoviesContent.class)
                        .findFirst();
            }

            return moviesContent == null ? new MoviesContent() : realm.copyFromRealm(moviesContent);
        } finally {

            if (isRealmNotClosed(realm)) {

                realm.close();
            }
        }
    }

    private boolean isRealmNotClosed(Realm realm) {

        return realm != null && !realm.isClosed();
    }

    public List<Movie> pagination(int page) throws IndexOutOfBoundsException {

        Realm realm = realmHelper.getRealmInstance();

        try {
            int firstPosition = page * 10;
            int lastPosition = firstPosition + 9;

            RealmResults<Movie> results = realm.where(Movie.class)
                    .findAll()
                    .sort(Movie.POPULARITY, Sort.DESCENDING);

            if (lastPosition > results.size() - 1) {

                return Collections.emptyList();
            }

            if (page == 0) {

                return results.subList(0, 9);
            } else {

                return results.subList(firstPosition, lastPosition);
            }
        } finally {

            realm.close();
        }
    }

    public void save(final MoviesContent moviesContent) {

        Realm realm = realmHelper.getRealmInstance();

        try {

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    realm.copyToRealmOrUpdate(moviesContent);
                }
            });
        } finally {

            realm.close();
        }
    }
}