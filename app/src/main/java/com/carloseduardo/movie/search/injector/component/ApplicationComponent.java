package com.carloseduardo.movie.search.injector.component;

import android.content.Context;

import com.carloseduardo.movie.search.base.BaseActivity;
import com.carloseduardo.movie.search.moviedetail.MovieDetailActivity;
import com.carloseduardo.movie.search.movies.MoviesActivity;
import com.carloseduardo.movie.search.injector.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);
    void inject(MoviesActivity moviesActivity);
    void inject(MovieDetailActivity movieDetailActivity);

    Context context();
}