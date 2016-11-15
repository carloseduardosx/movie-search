package com.carloseduardo.movie.search.ui.moviedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.carloseduardo.movie.search.R;
import com.carloseduardo.movie.search.base.BaseActivity;
import com.carloseduardo.movie.search.data.source.MoviesRepository;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends BaseActivity {

    @Inject
    MoviesRepository moviesRepository;

    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;

    private MovieDetailContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {

            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
        configureMovieDetailFragment();
    }

    private void configureMovieDetailFragment() {

        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        presenter = new MovieDetailPresenter(movieDetailFragment, moviesRepository);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.movie_detail_container, movieDetailFragment)
                .commit();
    }

    @Override
    protected void inject() {
        getComponent().inject(this);
    }
}