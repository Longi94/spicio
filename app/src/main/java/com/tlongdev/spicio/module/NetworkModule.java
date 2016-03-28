package com.tlongdev.spicio.module;

import com.google.gson.Gson;
import com.tlongdev.spicio.BuildConfig;
import com.tlongdev.spicio.network.SpicioInterface;
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
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    SimpleXmlConverterFactory provideXmlConverterFactory() {
        return SimpleXmlConverterFactory.create();
    }

    @Provides
    @Named("spicio")
    @Singleton
    Retrofit provideSpicioRetrofit(@Named("non_intercepted") OkHttpClient okHttpClient, GsonConverterFactory factory) {
        return new Retrofit.Builder()
                .addConverterFactory(factory)
                .baseUrl(SpicioInterface.BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Named("trakt")
    @Singleton
    Retrofit provideTraktRetrofit(@Named("intercepted") OkHttpClient okHttpClient, GsonConverterFactory factory) {
        return new Retrofit.Builder()
                .addConverterFactory(factory)
                .baseUrl(TraktApiInterface.BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Named("tvdb")
    @Singleton
    Retrofit provideTvdbRetrofit(@Named("non_intercepted") OkHttpClient okHttpClient, SimpleXmlConverterFactory factory) {
        return new Retrofit.Builder()
                .addConverterFactory(factory)
                .baseUrl(TvdbInterface.BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    SpicioInterface provideSpicioInterface(@Named("spicio") Retrofit retrofit) {
        return  retrofit.create(SpicioInterface.class);
    }

    @Provides
    @Singleton
    TraktApiInterface provideTraktApiInterface(@Named("trakt") Retrofit retrofit) {
        return  retrofit.create(TraktApiInterface.class);
    }

    @Provides
    @Singleton
    TvdbInterface provideTvdbInterface(@Named("tvdb") Retrofit retrofit) {
        return  retrofit.create(TvdbInterface.class);
    }
}
