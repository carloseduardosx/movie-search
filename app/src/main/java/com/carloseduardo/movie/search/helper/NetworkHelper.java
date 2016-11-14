package com.carloseduardo.movie.search.helper;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkHelper {

    private final ConnectivityManager connectivityManager;

    public NetworkHelper(ConnectivityManager connectivityManager) {

        this.connectivityManager = connectivityManager;
    }

    public boolean hasNetwork() {

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}