package com.carloseduardo.movie.search.data.model.realm;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

public class SchemaMigration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        // For now anyone migration is needed!
    }

    /**
     * Work around needed because Realm make a wrong check of configuration objects equality
     *
     * @link https://github.com/realm/realm-java/issues/1919
     */
    @Override
    public boolean equals(Object o) {

        return o instanceof SchemaMigration;
    }
}
