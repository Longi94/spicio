package com.tlongdev.spicio.module;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.repository.TvdbRepository;
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
    public TvdbRepository provideTvdbRepository(SpicioApplication application) {
        return new TvdbRepositoryImpl(application.getNetworkComponent());
    }
}
