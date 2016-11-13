package com.carloseduardo.movie.search.helper;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmHelper {

    public Realm getInstance() {

        return Realm.getInstance(getRealmConfiguration());
    }

    private RealmConfiguration getRealmConfiguration() {

        return new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
    }
}