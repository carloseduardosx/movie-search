package com.carloseduardo.movie.search.helper;

import com.carloseduardo.movie.search.application.MovieSearchApplication;
import com.carloseduardo.movie.search.data.model.realm.SchemaMigration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmHelper {

    private static RealmHelper realmHelper;

    private RealmHelper() {
    }

    public Realm getRealmInstance() {

        return Realm.getInstance(getRealmConfiguration());
    }

    private RealmConfiguration getRealmConfiguration() {

        return new RealmConfiguration.Builder()
                .migration(new SchemaMigration())
                .schemaVersion(MovieSearchApplication.DB_VERSION)
                .build();
    }

    public static RealmHelper getInstance() {

        if (realmHelper == null) {

            realmHelper = new RealmHelper();
        }
        return realmHelper;
    }
}