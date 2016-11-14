package com.carloseduardo.movie.search.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.carloseduardo.movie.search.R;
import com.carloseduardo.movie.search.base.BaseFragment;
import com.carloseduardo.movie.search.data.model.Movie;
import com.carloseduardo.movie.search.data.source.MoviesRepository;
import com.carloseduardo.movie.search.movies.observer.NetworkChangeObserver;
import com.carloseduardo.movie.search.helper.RealmHelper;
import com.carloseduardo.movie.search.movies.adapter.MoviesAdapter;
import com.carloseduardo.movie.search.movies.listener.EndlessScrollListener;
import com.carloseduardo.movie.search.movies.listener.NetworkStateChangeListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;

public class MoviesFragment extends BaseFragment implements MoviesContract.View, NetworkStateChangeListener {

    @Inject
    MoviesRepository moviesRepository;

    @BindView(R.id.list_movies)
    SuperRecyclerView recycler;

    @BindView(R.id.without_network)
    LinearLayout withoutNetworkLayout;

    private Unbinder unbinder;
    private MoviesContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.movies_fragment, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        NetworkChangeObserver.getInstance().addListener(this);
        presenter.listMovies();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void inject() {

        getComponent().inject(this);
    }

    @Override
    public void setPresenter(MoviesContract.Presenter presenter) {

        this.presenter = presenter;
    }

    @Override
    public void showMovies(List<Movie> movies) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        MoviesAdapter moviesAdapter = new MoviesAdapter(movies);

        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(moviesAdapter);
        recycler.setOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, final RecyclerView view) {

                presenter.loadNextPage(page, view, this);
            }
        });
    }

    @Override
    public void hideWithoutNetwork() {

        withoutNetworkLayout.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void showWithoutNetwork() {

        withoutNetworkLayout.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);
    }

    @Override
    public void stateChanged() {

        Realm realm = RealmHelper.getInstance().getRealmInstance();
        RealmResults<Movie> movies = realm.where(Movie.class).findAll();

        if (movies.isEmpty()) {
            presenter.listMovies();
        }
        realm.close();
    }
}