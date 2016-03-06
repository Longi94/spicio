package com.tlongdev.spicio.module;

import com.tlongdev.spicio.BuildConfig;
import com.tlongdev.spicio.network.TraktApiInterface;
import com.tlongdev.spicio.network.TvdbInterface;
import com.tlongdev.spicio.network.interceptor.TraktApiInterceptor;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * @author Long
 * @since 2016. 02. 25.
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    TraktApiInterceptor provideTraktApiInterceptor(Logger logger) {
        return new TraktApiInterceptor(logger, BuildConfig.TRAKT_API_KEY);
    }

    @Provides
    @Named("non_intercepted")
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @Named("intercepted")
    @Singleton
    OkHttpClient provideInterceptedOkHttpClient(TraktApiInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Named("tvdb")
    @Singleton
    Retrofit provideTvdbRetrofit(@Named("non_intercepted") OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .baseUrl(TvdbInterface.BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Named("trakt")
    @Singleton
    Retrofit provideTraktRetrofit(@Named("intercepted") OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(TraktApiInterface.BASE_URL)
                .client(okHttpClient)
                .build();
    }
}
