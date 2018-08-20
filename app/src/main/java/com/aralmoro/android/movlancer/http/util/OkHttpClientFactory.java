package com.aralmoro.android.movlancer.http.util;

import android.util.Log;

import com.aralmoro.android.movlancer.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by angelaalmoro on 16/08/2018.
 */

public final class OkHttpClientFactory {
    private OkHttpClientFactory() {

    }

    public static OkHttpClient.Builder createOkHttpClient(int numOfRetries,
                                                          long connTimeoutMillis,
                                                          long readTimeoutMillis) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if (connTimeoutMillis > 0) {
            clientBuilder.connectTimeout(connTimeoutMillis, TimeUnit.MILLISECONDS);
        }
        if (readTimeoutMillis > 0) {
            clientBuilder.readTimeout(readTimeoutMillis, TimeUnit.MILLISECONDS);
        }
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(
                    createLoggingInterceptor(HttpLoggingInterceptor.Level.BODY));
        }
        if (numOfRetries > 0) {
            clientBuilder.addInterceptor(createRetryInterceptor(numOfRetries));
        }

        return clientBuilder;
    }

    private static Interceptor createLoggingInterceptor(HttpLoggingInterceptor.Level logLevel) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(logLevel);
        return interceptor;
    }

    private static Interceptor createRetryInterceptor(final int numOfRetries) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                // try the request
                Response response = chain.proceed(request);

                int tryCount = 0;
                while (!response.isSuccessful() && tryCount < numOfRetries) {

                    Log.d("intercept", "Request is not successful - " + tryCount);

                    tryCount++;

                    // retry the request
                    response = chain.proceed(request);
                }

                // otherwise just pass the original response on
                return response;
            }
        };
    }
}
