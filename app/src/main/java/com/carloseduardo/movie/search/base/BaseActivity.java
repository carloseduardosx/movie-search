package com.carloseduardo.movie.search.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.carloseduardo.movie.search.application.MovieSearchApplication;
import com.carloseduardo.movie.search.injector.component.ApplicationComponent;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getComponent().inject(this);
        inject();
    }

    protected MovieSearchApplication getMovieSearchApplication() {

        return (MovieSearchApplication) getApplication();
    }

    protected ApplicationComponent getComponent() {

        return getMovieSearchApplication().getComponent();
    }

    abstract protected void inject();
}