package com.carloseduardo.movie.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.carloseduardo.movie.search.movies.MoviesActivity;

public class MovieSearchSplashActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_search_splash);

        final int splashScreenTimeout = 2000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainActivityIntent = new Intent(getApplicationContext(), MoviesActivity.class);

                mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainActivityIntent);
                finish();
            }
        }, splashScreenTimeout);
    }
}
