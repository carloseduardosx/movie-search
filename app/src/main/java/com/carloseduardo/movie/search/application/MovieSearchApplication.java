package com.carloseduardo.movie.search.application;

import android.app.Application;

import com.carloseduardo.movie.search.dagger.component.ApplicationComponent;
import com.carloseduardo.movie.search.dagger.component.DaggerApplicationComponent;
import com.carloseduardo.movie.search.dagger.module.ApplicationModule;

public class MovieSearchApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initComponent();
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