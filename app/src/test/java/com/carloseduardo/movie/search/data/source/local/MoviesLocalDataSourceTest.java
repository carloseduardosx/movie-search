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
    public void shouldReturnNineMoviesWithPagination() {

        List<Movie> movies = getMovies();
        MoviesLocalDataSource moviesLocalDataSource = new MoviesLocalDataSource();
        moviesLocalDataSource = spy(moviesLocalDataSource);

        doReturn(movies).when(moviesLocalDataSource).getSortedMovies();

        List<Movie> firstPage = moviesLocalDataSource.pagination(0);
        assertEquals(10, firstPage.size());
    }

    private List<Movie> getMovies() {

        List<Movie> movies = new ArrayList<>();

        for (int i = 0; i < 100; i++) {

            movies.add(mock(Movie.class));
        }
        return movies;
    }
}