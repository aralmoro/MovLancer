package com.aralmoro.android.movlancer.listener;

import android.view.View;

import com.aralmoro.android.movlancer.http.model.Movie;

/**
 * Created by angelaalmoro on 19/08/2018.
 */

public interface MovieListListener {
    void onMovieSelected(Movie movie, View sharedView);
}
