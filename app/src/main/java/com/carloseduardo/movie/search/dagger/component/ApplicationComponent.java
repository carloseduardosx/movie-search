package com.carloseduardo.movie.search.dagger.component;

import android.content.Context;

import com.carloseduardo.movie.search.activity.BaseActivity;
import com.carloseduardo.movie.search.activity.MovieListActivity;
import com.carloseduardo.movie.search.dagger.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);
    void inject(MovieListActivity movieListActivity);

    Context context();
}