package com.carloseduardo.movie.search.application;

import android.app.Application;

import com.carloseduardo.movie.search.injector.component.ApplicationComponent;
import com.carloseduardo.movie.search.injector.component.DaggerApplicationComponent;
import com.carloseduardo.movie.search.injector.module.ApplicationModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MovieSearchApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initRealm();
        initComponent();
    }

    private void initRealm() {

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
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