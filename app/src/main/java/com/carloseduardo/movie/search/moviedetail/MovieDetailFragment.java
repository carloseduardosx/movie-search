package com.carloseduardo.movie.search.moviedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carloseduardo.movie.search.base.BaseFragment;

public class MovieDetailFragment extends BaseFragment implements MovieDetailContract.View {

    private MovieDetailContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void inject() {
        getComponent().inject(this);
    }

    @Override
    public void setPresenter(MovieDetailContract.Presenter presenter) {

        this.presenter = presenter;
    }
}