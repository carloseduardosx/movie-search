package com.carloseduardo.movie.search.application;

import android.app.Application;

import com.carloseduardo.movie.search.data.model.realm.SchemaMigration;
import com.carloseduardo.movie.search.injector.component.ApplicationComponent;
import com.carloseduardo.movie.search.injector.component.DaggerApplicationComponent;
import com.carloseduardo.movie.search.injector.module.ApplicationModule;

import net.danlew.android.joda.JodaTimeAndroid;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MovieSearchApplication extends Application {

    public static final int DB_VERSION = 1;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initRealm();
        initComponent();
        initJodaTime();
    }

    private void initJodaTime() {

        JodaTimeAndroid.init(this);
    }

    private void initRealm() {

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .migration(new SchemaMigration())
                .schemaVersion(DB_VERSION)
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }

    private void initComponent() {

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {

        return applicationComponent;
    }
}