package com.tlongdev.spicio.module;

import com.tlongdev.spicio.util.Logger;
import com.tlongdev.spicio.util.AndroidLogger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
@Module
public class ReportingModule {

    @Provides
    @Singleton
    Logger provideLogger() {
        return new AndroidLogger();
    }
}
