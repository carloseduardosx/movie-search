package com.carloseduardo.movie.search.movies;

import android.os.Bundle;

import com.carloseduardo.movie.search.R;
import com.carloseduardo.movie.search.base.BaseActivity;

public class MoviesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies);
    }

    @Override
    protected void inject() {

        getComponent().inject(this);
    }
}