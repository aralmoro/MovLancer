package com.aralmoro.android.movlancer.http;

import com.aralmoro.android.movlancer.http.model.MoviesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by angelaalmoro on 16/08/2018.
 */

public interface MoviesApi {
    @GET("movie/popular")
    Observable<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey,
                                                @Query("page") int page);
}
