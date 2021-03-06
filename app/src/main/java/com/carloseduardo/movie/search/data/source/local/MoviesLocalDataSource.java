package com.carloseduardo.movie.search.data.source.local;

import android.support.annotation.NonNull;
import android.util.Log;

import com.carloseduardo.movie.search.data.model.Movie;
import com.carloseduardo.movie.search.data.model.MoviesContent;
import com.carloseduardo.movie.search.helper.RealmHelper;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

public class MoviesLocalDataSource {

    private final String TAG = "MoviesLocalDataSource";

    private RealmHelper realmHelper = RealmHelper.getInstance();

    public Movie getMovie(int id) {

        Realm realm = realmHelper.getRealmInstance();

        try {

            Movie movie = realm.where(Movie.class)
                    .equalTo(Movie.ID, id)
                    .findFirst();

            return movie == null ? null : realm.copyFromRealm(movie);
        } finally {

            realm.close();
        }
    }

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
                Log.e(TAG, e.getMessage(), e);
                if (isRealmNotClosed(realm)) {

                    realm.close();
                }
                realm = realmHelper.getRealmInstance();

                moviesContent = realm.where(MoviesContent.class)
                        .findFirst();
            }

            return moviesContent == null ? new MoviesContent() : extractFromRealm(realm, moviesContent);
        } finally {

            if (isRealmNotClosed(realm)) {

                realm.close();
            }
        }
    }

    private MoviesContent extractFromRealm(Realm realm, MoviesContent moviesContent) {

        try {

            return realm.copyFromRealm(moviesContent);
        } catch (IllegalStateException e) {

            // Work around needed because realm in rare cases cannot get the latest version of data
            Log.e(TAG, e.getMessage(), e);
            return new MoviesContent();
        }
    }

    public List<Movie> extractFromRealm(Realm realm, List<Movie> movies) {

        try {

            return realm.copyFromRealm(movies);
        } catch (IllegalStateException e) {

            // Work around needed because realm in rare cases cannot get the latest version of data
            Log.e(TAG, e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private boolean isRealmNotClosed(Realm realm) {

        return realm != null && !realm.isClosed();
    }

    public List<Movie> pagination(int page) throws IndexOutOfBoundsException {


        int firstPosition = page * 10;
        int lastPosition = firstPosition + 10;

        List<Movie> results = getSortedMovies();

        if ((lastPosition > results.size() && results.isEmpty()) || firstPosition > results.size() - 1) {

            return Collections.emptyList();
        } else {

            lastPosition = results.size();
        }

        if (page == 0 && results.size() >= 10) {

            return results.subList(0, 10);
        } else {

            return results.subList(firstPosition, lastPosition);
        }
    }

    @NonNull
    public List<Movie> getSortedMovies() {

        Realm realm = realmHelper.getRealmInstance();

        try {

            return realm.where(Movie.class)
                    .findAll()
                    .sort(Movie.POPULARITY, Sort.DESCENDING);
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

                    try {
                        realm.copyToRealmOrUpdate(moviesContent);
                    } catch (NullPointerException e) {
                        //Realm lost reference to BaseRealm, should do nothing in this case
                        Log.e(TAG, e.getMessage(), e);
                    }
                }
            });
        } finally {

            realm.close();
        }
    }
}