package com.carloseduardo.movie.search.injector.component;

import com.carloseduardo.movie.search.base.BaseActivity;
import com.carloseduardo.movie.search.injector.module.ApplicationModule;
import com.carloseduardo.movie.search.moviedetail.MovieDetailActivity;
import com.carloseduardo.movie.search.moviedetail.MovieDetailFragment;
import com.carloseduardo.movie.search.movies.MoviesActivity;
import com.carloseduardo.movie.search.movies.MoviesFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);
    void inject(MoviesActivity moviesActivity);
    void inject(MovieDetailActivity movieDetailActivity);
    void inject(MoviesFragment moviesFragment);
    void inject(MovieDetailFragment movieDetailFragment);
}