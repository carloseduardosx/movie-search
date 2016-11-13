package com.carloseduardo.movie.search.dagger.module;

import android.content.Context;

import com.carloseduardo.movie.search.application.MovieSearchApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final MovieSearchApplication movieSearchApplication;

    public ApplicationModule(MovieSearchApplication movieSearchApplication) {

        this.movieSearchApplication = movieSearchApplication;
    }

    @Provides
    @Singleton
    public Context context() {

        return movieSearchApplication;
    }
}
