package com.carloseduardo.movie.search.data.source.local;

import com.carloseduardo.movie.search.data.model.Movie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class MoviesLocalDataSourceTest {

    @Test
    public void paginationShouldReturnTenResultsForFirstPage() {

        List<Movie> movies = getMovies(100);
        MoviesLocalDataSource moviesLocalDataSource = new MoviesLocalDataSource();
        moviesLocalDataSource = spy(moviesLocalDataSource);

        doReturn(movies).when(moviesLocalDataSource).getSortedMovies();

        List<Movie> firstPage = moviesLocalDataSource.pagination(0);
        assertEquals(10, firstPage.size());
    }

    @Test
    public void paginationShouldReturnEightResultsForFirstPage() {

        List<Movie> movies = getMovies(8);
        MoviesLocalDataSource moviesLocalDataSource = new MoviesLocalDataSource();
        moviesLocalDataSource = spy(moviesLocalDataSource);

        doReturn(movies).when(moviesLocalDataSource).getSortedMovies();

        List<Movie> firstPage = moviesLocalDataSource.pagination(0);
        assertEquals(8, firstPage.size());
    }

    @Test
    public void paginationShouldReturnTwoResultsForSecondPage() {

        List<Movie> movies = getMovies(12);
        MoviesLocalDataSource moviesLocalDataSource = new MoviesLocalDataSource();
        moviesLocalDataSource = spy(moviesLocalDataSource);

        doReturn(movies).when(moviesLocalDataSource).getSortedMovies();

        List<Movie> firstPage = moviesLocalDataSource.pagination(1);
        assertEquals(2, firstPage.size());
    }

    @Test
    public void paginationShouldReturnEmptyResultsForThirdPageWhenStoredMoviesIsLessThanOrEqualTwenty() {

        List<Movie> movies = getMovies(12);
        MoviesLocalDataSource moviesLocalDataSource = new MoviesLocalDataSource();
        moviesLocalDataSource = spy(moviesLocalDataSource);

        doReturn(movies).when(moviesLocalDataSource).getSortedMovies();

        List<Movie> firstPage = moviesLocalDataSource.pagination(2);
        assertEquals(0, firstPage.size());
    }

    private List<Movie> getMovies(int size) {

        List<Movie> movies = new ArrayList<>();

        for (int i = 0; i < size; i++) {

            movies.add(mock(Movie.class));
        }
        return movies;
    }
}