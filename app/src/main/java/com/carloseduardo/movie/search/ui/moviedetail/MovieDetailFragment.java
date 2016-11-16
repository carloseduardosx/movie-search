package com.carloseduardo.movie.search.ui.moviedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.carloseduardo.movie.search.R;
import com.carloseduardo.movie.search.base.BaseFragment;
import com.carloseduardo.movie.search.ui.constants.BundleKey;
import com.carloseduardo.movie.search.data.model.Movie;
import com.carloseduardo.movie.search.data.source.constants.API;
import com.squareup.picasso.Picasso;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieDetailFragment extends BaseFragment implements MovieDetailContract.View {

    @BindView(R.id.original_title_content)
    AppCompatTextView originalTitle;

    @BindView(R.id.original_language_content)
    AppCompatTextView originalLanguage;

    @BindView(R.id.adult_content)
    AppCompatCheckBox adultContent;

    @BindView(R.id.release_date_content)
    AppCompatTextView releaseDate;

    @BindView(R.id.rating_content)
    AppCompatRatingBar rating;

    @BindView(R.id.overview_content)
    AppCompatTextView overview;

    @BindView(R.id.movie_detail_layout)
    RelativeLayout movieDetailLayout;

    @BindView(R.id.movie_not_found_layout)
    LinearLayout movieNotFoundLayout;

    private MovieDetailContract.Presenter presenter;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.movie_detail_fragment, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        int movieId = getActivity().getIntent().getIntExtra(BundleKey.MOVIE_ID, 0);

        if (movieId != 0) {

            configureMovieDetail(movieId);
        } else {

            configureMovieNotFound();
        }
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
    public void setPresenter(MovieDetailContract.Presenter presenter) {

        this.presenter = presenter;
    }

    @Override
    public void showMovie(Movie movie) {

        if (movie == null) {

            showNoResultFound();
        } else {

            LocalDate movieReleaseDate = LocalDate.fromDateFields(movie.getReleaseDate());
            CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsing_toolbar);
            ImageView movieImageView = (ImageView) collapsingToolbarLayout.findViewById(R.id.movie_background_img);

            Picasso.with(movieImageView.getContext())
                    .load(API.IMG_ENDPOINT + movie.getBackdropPath())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(movieImageView);

            collapsingToolbarLayout.setTitle(movie.getTitle());
            originalTitle.setText(movie.getOriginalTitle());
            originalLanguage.setText(movie.getOriginalLanguage());
            adultContent.setChecked(movie.getAdult());
            releaseDate.setText(movieReleaseDate.toString(DateTimeFormat.mediumDate()));
            rating.setRating(movie.getVoteAverage() / 2);
            overview.setText(movie.getOverview());
        }
    }

    @Override
    public void showNoResultFound() {

        movieDetailLayout.setVisibility(View.GONE);
        movieNotFoundLayout.setVisibility(View.VISIBLE);
    }

    private void configureMovieNotFound() {

        showNoResultFound();
    }

    private void configureMovieDetail(int movieId) {

        Movie movie = presenter.getMovie(movieId);
        showMovie(movie);
    }
}