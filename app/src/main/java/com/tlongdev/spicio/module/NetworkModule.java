package com.tlongdev.spicio.module;

import android.content.Context;

import com.tlongdev.spicio.network.TvdbInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * @author Long
 * @since 2016. 02. 25.
 */
@Module
public class NetworkModule {

    private Context mContext;

    // Constructor needs one parameter to instantiate.
    public NetworkModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @Singleton
    SimpleXmlConverterFactory provideXmlConverter() {
        return SimpleXmlConverterFactory.create();
    }

    @Provides
    @Singleton
    TvdbInterface provideTvdbInterface(SimpleXmlConverterFactory xmlConverter, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(xmlConverter)
                .baseUrl(TvdbInterface.BASE_URL)
                .client(okHttpClient)
                .build();

        return retrofit.create(TvdbInterface.class);
    }
}
