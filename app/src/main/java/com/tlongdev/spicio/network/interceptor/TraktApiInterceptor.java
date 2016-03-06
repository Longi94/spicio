package com.tlongdev.spicio.network.interceptor;

import android.support.annotation.NonNull;

import com.tlongdev.spicio.util.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Long
 * @since 2016. 03. 01.
 */
public class TraktApiInterceptor implements Interceptor {

    private static final String LOG_TAG = TraktApiInterceptor.class.getSimpleName();

    private String apiKey;
    private Logger logger;

    public TraktApiInterceptor(Logger logger, @NonNull String apiKey) {
        this.apiKey = apiKey;
        this.logger = logger;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request newRequest = originalRequest.newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("trakt-api-version", "2")
                .addHeader("trakt-api-key", apiKey)
                .build();

        logger.verbose(LOG_TAG, "intercepted call " + originalRequest.url());
        logger.verbose(LOG_TAG, "added headers:\n" + newRequest.headers().toString());
        return chain.proceed(newRequest);
    }
}
