package com.carloseduardo.movie.search.ui.movies.observer;

import com.carloseduardo.movie.search.ui.movies.listener.NetworkStateChangeListener;

import java.util.ArrayList;
import java.util.List;

public class NetworkChangeObserver {

    private List<NetworkStateChangeListener> networkStateChangeListenerList = new ArrayList<>();
    private static NetworkChangeObserver networkChangeObserver;

    private NetworkChangeObserver() {
    }

    public void notifyListener() {

        for (NetworkStateChangeListener listener : networkStateChangeListenerList) {

            listener.stateChanged();
        }
    }

    public void addListener(NetworkStateChangeListener networkStateChangeListener) {

        networkStateChangeListenerList.add(networkStateChangeListener);
    }

    public static NetworkChangeObserver getInstance() {

        if (networkChangeObserver == null) {

            networkChangeObserver = new NetworkChangeObserver();
        }
        return networkChangeObserver;
    }
}
