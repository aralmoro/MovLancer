package com.aralmoro.android.movlancer.http;

import com.aralmoro.android.movlancer.constants.ApiConstants;
import com.aralmoro.android.movlancer.http.util.OkHttpClientFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by angelaalmoro on 16/08/2018.
 */

public abstract class BaseApiService {
    protected static final long CONN_TIMEOUT_MS = 32000;
    protected static final long READ_TIMEOUT_MS = 32000;

    protected Retrofit retrofit;

    public BaseApiService() {
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiConstants.BASE_URL)
                .client(OkHttpClientFactory.createOkHttpClient(0,
                        CONN_TIMEOUT_MS, READ_TIMEOUT_MS).build())
                .build();
    }

}
