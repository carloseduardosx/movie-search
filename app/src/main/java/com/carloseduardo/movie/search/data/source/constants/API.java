package com.carloseduardo.movie.search.data.source.constants;

import com.carloseduardo.movie.search.BuildConfig;

public interface API {

    String ENDPOINT = "https://api.themoviedb.org";
    String IMG_ENDPOINT = "https://image.tmdb.org/t/p/w1280";
    String KEY = BuildConfig.TMD_API_KEY;
}