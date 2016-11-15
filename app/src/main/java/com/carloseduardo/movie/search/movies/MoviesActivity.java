package com.carloseduardo.movie.search.movies;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.carloseduardo.movie.search.R;
import com.carloseduardo.movie.search.base.BaseActivity;
import com.carloseduardo.movie.search.data.source.MoviesRepository;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MoviesActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    MoviesRepository moviesRepository;

    private MoviesContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_activity);
        ButterKnife.bind(this);
        Realm.init(getApplicationContext());
        setSupportActionBar(toolbar);

        configureMoviesFragment();
    }

    private void configureMoviesFragment() {

        MoviesFragment moviesFragment = new MoviesFragment();
        presenter = new MoviesPresenter(moviesFragment, moviesRepository);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.movies_coordinator, moviesFragment)
                .commit();

    }

    @Override
    protected void inject() {

        getComponent().inject(this);
    }
}