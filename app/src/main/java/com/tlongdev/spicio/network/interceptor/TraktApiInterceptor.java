package com.tlongdev.spicio.network.interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Long
 * @since 2016. 03. 01.
 */
public class TraktApiInterceptor implements Interceptor {

    private String apiKey;

    public TraktApiInterceptor(@NonNull String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request newRequest = originalRequest.newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("trakt-api-version", "2")
                .addHeader("trakt-api-key", apiKey)
                .build();

        return chain.proceed(newRequest);
    }
}
