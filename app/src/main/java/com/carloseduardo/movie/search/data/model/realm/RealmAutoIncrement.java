package com.carloseduardo.movie.search.data.model.realm;

import android.support.annotation.NonNull;
import android.util.Log;

import com.carloseduardo.movie.search.data.model.AutoIncrement;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;

public class RealmAutoIncrement {

    private final String TAG = "RealmAutoIncrement";
    private static RealmAutoIncrement autoIncrementMap;

    private RealmAutoIncrement() {

        createAutoIncrementEntityIfNotExist();
    }

    /**
     * Search in AutoIncrement for the last saved id from model passed and return the next one
     *
     * @param clazz Model which should get the next id
     * @return The next id which can be saved in database
     */
    public Integer getNextIdFromModel(Class<? extends RealmObject> clazz) {

        if (isValidMethodCall()) {

            Integer id = updateIdByClassName(clazz);
            Log.d(TAG, "getNextIdFromModel: " + id);
            return id;
        }
        Log.d(TAG, "getNextIdFromModel is called by a not valid method");
        return null;
    }

    private Integer updateIdByClassName(final Class<? extends RealmObject> clazz) {

        Realm realm = getRealm();
        final AutoIncrement autoIncrement = realm.where(AutoIncrement.class).findFirst();

        if (realm.isInTransaction()) {

            autoIncrement.incrementByClassName(clazz.getSimpleName());
            realm.copyToRealmOrUpdate(autoIncrement);
        } else {

            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {

                    autoIncrement.incrementByClassName(clazz.getSimpleName());
                    realm.copyToRealmOrUpdate(autoIncrement);
                }
            });
        }
        return autoIncrement.findByClassName(clazz.getSimpleName());
    }

    /**
     * Utility method to validate if the method is called from reflection,
     * in this case is considered a not valid call otherwise is a valid call
     *
     * @return The boolean which define if the method call is valid or not
     */
    private boolean isValidMethodCall() {

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        for (StackTraceElement stackTraceElement : stackTraceElements) {

            if (stackTraceElement.getMethodName().equals("newInstance")) {

                return false;
            }
        }
        return true;
    }

    private void createAutoIncrementEntityIfNotExist() {

        AutoIncrement autoIncrement = getRealm().where(AutoIncrement.class).findFirst();

        if (autoIncrement == null) {

            createAutoIncrementEntity();
        }
    }

    private void createAutoIncrementEntity() {

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                AutoIncrement autoIncrement = new AutoIncrement();
                realm.copyToRealm(autoIncrement);
            }
        });
    }

    private Realm getRealm() {
        return Realm.getInstance(getRealmConfiguration());
    }

    @NonNull
    private RealmConfiguration getRealmConfiguration() {

        return new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    public static RealmAutoIncrement getInstance() {

        if (autoIncrementMap == null) {

            autoIncrementMap = new RealmAutoIncrement();
        }
        return autoIncrementMap;
    }
}