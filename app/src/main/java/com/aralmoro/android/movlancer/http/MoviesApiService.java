package com.aralmoro.android.movlancer.http;

import com.aralmoro.android.movlancer.constants.ApiConstants;
import com.aralmoro.android.movlancer.http.model.MoviesResponse;
import com.aralmoro.android.movlancer.http.util.OkHttpClientFactory;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by angelaalmoro on 16/08/2018.
 */

public class MoviesApiService extends BaseApiService {
    private static MoviesApiService moviesApiService;

    private MoviesApi moviesApi;

    private MoviesApiService() {
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiConstants.BASE_URL)
                .client(OkHttpClientFactory.createOkHttpClient(0,
                        CONN_TIMEOUT_MS, READ_TIMEOUT_MS).build())
                .build();
        moviesApi = retrofit.create(MoviesApi.class);
    }

    public static MoviesApiService createMoviesApiServiceInstance() {
        if (moviesApiService == null) {
            moviesApiService = new MoviesApiService();
        }
        return moviesApiService;
    }

    public Observable<MoviesResponse> getPopularMovies(final String apiKey,
                                                       final int page) {
        return moviesApi.getPopularMovies(apiKey, page);
    }

}
