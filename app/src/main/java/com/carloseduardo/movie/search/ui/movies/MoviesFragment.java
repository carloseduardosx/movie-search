package com.carloseduardo.movie.search.ui.movies;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.carloseduardo.movie.search.R;
import com.carloseduardo.movie.search.base.BaseFragment;
import com.carloseduardo.movie.search.data.model.Movie;
import com.carloseduardo.movie.search.data.source.MoviesRepository;
import com.carloseduardo.movie.search.helper.RealmHelper;
import com.carloseduardo.movie.search.ui.movies.adapter.MoviesAdapter;
import com.carloseduardo.movie.search.ui.movies.listener.EndlessScrollListener;
import com.carloseduardo.movie.search.ui.movies.listener.NetworkStateChangeListener;
import com.carloseduardo.movie.search.ui.movies.observer.NetworkChangeObserver;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

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

    @BindView(R.id.empty_results)
    LinearLayout emptyResultsLayout;

    @BindView(R.id.top_navigation_fab)
    FloatingActionButton fab;

    private Unbinder unbinder;
    private SearchView searchView;
    private MoviesContract.Presenter presenter;
    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.movies_fragment, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        NetworkChangeObserver.getInstance().addListener(this);
        presenter.listMovies(false);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.movies_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.movie_search_menu);

        if (searchItem != null) {

            searchView = (SearchView) searchItem.getActionView();
        }

        if (searchView != null) {

            configureSearchView();
        }
        super.onCreateOptionsMenu(menu, inflater);
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
        recycler.setOnScrollListener(new EndlessScrollListener(linearLayoutManager, fab) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, final RecyclerView view) {

                presenter.loadNextPage(page, view, this);
            }
        });
        configureTopNavigation(linearLayoutManager);
    }

    @Override
    public void showSearchedMovies(List<Movie> movies) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        MoviesAdapter moviesAdapter = new MoviesAdapter(movies);

        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(moviesAdapter);
        recycler.setOnScrollListener(new EndlessScrollListener(linearLayoutManager, fab) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, final RecyclerView view) {

                presenter.loadNextSearchPage(page, view, this, searchView.getQuery().toString());
            }
        });
        configureTopNavigation(linearLayoutManager);
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
    public void hideEmptyResults() {

        emptyResultsLayout.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyResults() {

        emptyResultsLayout.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);
    }

    @Override
    public void stateChanged() {

        Realm realm = RealmHelper.getInstance().getRealmInstance();
        RealmResults<Movie> movies = realm.where(Movie.class).findAll();

        if (movies.isEmpty()) {
            presenter.listMovies(false);
        }
        realm.close();
    }

    private void configureSearchView() {

        final SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint(getString(R.string.movie_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String search) {

                if (search != null && !search.isEmpty()) {

                    if (scheduledThreadPoolExecutor == null) {

                        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
                        scheduledThreadPoolExecutor.execute(searchMovies(search.trim()));
                    } else {

                        scheduledThreadPoolExecutor.shutdownNow();
                        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
                        scheduledThreadPoolExecutor.execute(searchMovies(search.trim()));
                    }
                }
                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                presenter.listMovies(true);
                return false;
            }
        });
    }

    private void configureTopNavigation(final RecyclerView.LayoutManager layoutManager) {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutManager.scrollToPosition(0);
                fab.hide();
            }
        });
    }

    private Runnable searchMovies(final String search) {

        return new Runnable() {
            @Override
            public void run() {

                presenter.searchMovies(search);
            }
        };
    }
}