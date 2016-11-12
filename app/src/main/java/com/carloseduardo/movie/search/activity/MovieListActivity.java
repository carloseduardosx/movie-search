package com.carloseduardo.movie.search.activity;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.carloseduardo.movie.search.R;

import javax.inject.Inject;

public class MovieListActivity extends BaseActivity {

    @Inject
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list);

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        Toast.makeText(context, wifiManager.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void injectMember() {

        getComponent().inject(this);
    }
}