package com.carloseduardo.movie.search.ui.movies.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.carloseduardo.movie.search.ui.movies.observer.NetworkChangeObserver;

public class NetworkStateChangeReceiver extends BroadcastReceiver {

    private NetworkChangeObserver networkChangeObserver = NetworkChangeObserver.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {

        networkChangeObserver.notifyListener();
    }
}
