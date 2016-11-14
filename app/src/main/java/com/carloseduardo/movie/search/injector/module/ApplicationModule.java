package com.carloseduardo.movie.search.injector.module;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import com.carloseduardo.movie.search.application.MovieSearchApplication;
import com.carloseduardo.movie.search.data.source.MoviesRepository;
import com.carloseduardo.movie.search.data.source.constants.API;
import com.carloseduardo.movie.search.data.source.remote.interceptor.AuthorizationInterceptor;
import com.carloseduardo.movie.search.data.source.remote.network.MovieNetwork;
import com.carloseduardo.movie.search.data.source.remote.serializer.GsonDateSerializer;
import com.carloseduardo.movie.search.helper.NetworkHelper;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmObject;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    private final MovieSearchApplication movieSearchApplication;

    public ApplicationModule(MovieSearchApplication movieSearchApplication) {

        this.movieSearchApplication = movieSearchApplication;
    }

    @Provides
    @Singleton
    public Context context() {

        return movieSearchApplication;
    }

    @Provides
    @Singleton
    public Gson gson() {

        return new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {

                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {

                        return false;
                    }
                })
                .registerTypeAdapter(Date.class, new GsonDateSerializer())
                .create();
    }

    @Provides
    @Singleton
    public OkHttpClient client() {

        return new OkHttpClient.Builder()
                .connectTimeout(60000, TimeUnit.MILLISECONDS)
                .writeTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .addInterceptor(new AuthorizationInterceptor())
                .build();
    }

    @Provides
    @Singleton
    public Retrofit retrofit(final OkHttpClient client, final Gson gson) {

        return new Retrofit.Builder()
                .baseUrl(API.ENDPOINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    public MovieNetwork movieNetwork(final Retrofit retrofit) {

        return retrofit.create(MovieNetwork.class);
    }

    @Provides
    @Singleton
    public ConnectivityManager wifiManager(final Context context) {

        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    @Singleton
    public MoviesRepository moviesRepository(final MovieNetwork movieNetwork,
                                             final ConnectivityManager connectivityManager) {

        return new MoviesRepository(movieNetwork, new NetworkHelper(connectivityManager));
    }
}