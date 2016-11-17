package com.carloseduardo.movie.search.ui.movies.observer;

import com.carloseduardo.movie.search.ui.movies.listener.NetworkStateChangeListener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NetworkChangeObserverTest {

    @Test
    public void shouldNotifyListener() {

        NetworkChangeObserver networkChangeObserver = NetworkChangeObserver.getInstance();
        NetworkStateChangeListener networkStateChangeListener = mock(NetworkStateChangeListener.class);

        networkChangeObserver.addListener(networkStateChangeListener);
        networkChangeObserver.notifyListener();

        verify(networkStateChangeListener, times(1)).stateChanged();
    }

    @Test
    public void shouldNotifyAllListener() {

        NetworkChangeObserver networkChangeObserver = NetworkChangeObserver.getInstance();
        NetworkStateChangeListener networkStateChangeListener = mock(NetworkStateChangeListener.class);
        NetworkStateChangeListener secondNetworkStateChangeListener = mock(NetworkStateChangeListener.class);

        networkChangeObserver.addListener(networkStateChangeListener);
        networkChangeObserver.addListener(secondNetworkStateChangeListener);
        networkChangeObserver.notifyListener();

        verify(networkStateChangeListener, times(1)).stateChanged();
        verify(secondNetworkStateChangeListener, times(1)).stateChanged();
    }
}
