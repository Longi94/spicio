package com.tlongdev.spicio.module;

import android.app.Application;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.domain.repository.TraktRepository;
import com.tlongdev.spicio.domain.repository.TvdbRepository;
import com.tlongdev.spicio.domain.repository.impl.SpicioRepositoryImpl;
import com.tlongdev.spicio.domain.repository.impl.TraktRepositoryImpl;
import com.tlongdev.spicio.domain.repository.impl.TvdbRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
@Module
public class NetworkRepositoryModule {

    @Provides
    @Singleton
    TvdbRepository provideTvdbRepository(Application application) {
        return new TvdbRepositoryImpl((SpicioApplication) application);
    }

    @Provides
    @Singleton
    TraktRepository provideTraktRepository(Application application) {
        return new TraktRepositoryImpl((SpicioApplication) application);
    }

    @Provides
    @Singleton
    SpicioRepository provideSpicioRepository(Application application) {
        return new SpicioRepositoryImpl((SpicioApplication) application);
    }
}
