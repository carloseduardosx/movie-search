package com.carloseduardo.movie.search.movies.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.carloseduardo.movie.search.movies.observer.NetworkChangeObserver;

public class NetworkStateChangeReceiver extends BroadcastReceiver {

    private NetworkChangeObserver networkChangeObserver = NetworkChangeObserver.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {

        networkChangeObserver.notifyListener();
    }
}
