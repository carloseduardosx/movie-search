package com.carloseduardo.movie.search.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.carloseduardo.movie.search.application.MovieSearchApplication;
import com.carloseduardo.movie.search.injector.component.ApplicationComponent;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
    }

    private MovieSearchApplication getMovieSearchApplication() {

        return (MovieSearchApplication) getActivity().getApplication();
    }

    public ApplicationComponent getComponent() {

        return getMovieSearchApplication().getComponent();
    }

    public abstract void inject();
}