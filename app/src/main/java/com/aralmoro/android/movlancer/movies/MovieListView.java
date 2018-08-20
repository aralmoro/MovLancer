package com.aralmoro.android.movlancer.movies;

import com.aralmoro.android.movlancer.http.model.MoviesResponse;
import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by angelaalmoro on 16/08/2018.
 */

public interface MovieListView extends MvpView {
    void displayMovies(MoviesResponse moviesResponse);
    void onFailGetMovies();
}
