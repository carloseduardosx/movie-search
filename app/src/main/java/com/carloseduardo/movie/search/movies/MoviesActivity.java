package com.carloseduardo.movie.search.movies;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.carloseduardo.movie.search.R;
import com.carloseduardo.movie.search.base.BaseActivity;
import com.carloseduardo.movie.search.data.model.Movie;
import com.carloseduardo.movie.search.data.source.MoviesRepository;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class MoviesActivity extends BaseActivity {

    @Inject
    MoviesRepository moviesRepository;

    @BindView(R.id.list_movies)
    Button listMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies);
        ButterKnife.bind(this);

        listMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listMovies();
            }
        });
    }

    private void listMovies() {

        moviesRepository.listMovies()
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {

                        Toast.makeText(MoviesActivity.this, "Movies Size: " + movies.size(), Toast.LENGTH_LONG)
                                .show();
                    }
                });
    }

    @Override
    protected void inject() {

        getComponent().inject(this);
    }
}