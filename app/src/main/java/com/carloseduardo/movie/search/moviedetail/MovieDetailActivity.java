package com.carloseduardo.movie.search.moviedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.carloseduardo.movie.search.R;
import com.carloseduardo.movie.search.base.BaseActivity;

public class MovieDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
    }

    @Override
    protected void inject() {
        getComponent().inject(this);
    }
}
