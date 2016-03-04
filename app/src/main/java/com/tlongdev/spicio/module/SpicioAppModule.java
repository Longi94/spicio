package com.tlongdev.spicio.module;

import com.tlongdev.spicio.SpicioApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Long
 * @since 2016. 02. 25.
 */
@Module
public class SpicioAppModule {

    private SpicioApplication mApplication;

    public SpicioAppModule(SpicioApplication application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    SpicioApplication provideApplication() {
        return mApplication;
    }
}
