package com.aralmoro.android.movlancer.movies;

import com.aralmoro.android.movlancer.constants.ApiConstants;
import com.aralmoro.android.movlancer.http.MoviesApiService;
import com.aralmoro.android.movlancer.http.model.MoviesResponse;

import io.reactivex.Observable;

/**
 * Created by angelaalmoro on 20/08/2018.
 */

public class MovieListModel {

    public MovieListModel() {

    }

    public Observable<MoviesResponse> getMovies(int page) {
        return MoviesApiService.createMoviesApiServiceInstance()
                .getPopularMovies(ApiConstants.API_KEY, page);
    }
}
