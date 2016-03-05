package com.tlongdev.spicio.module;

import android.content.ContentResolver;

import com.tlongdev.spicio.SpicioApplication;

import dagger.Module;
import dagger.Provides;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
@Module
public class StorageModule {

    @Provides
    ContentResolver provideContentResolver(SpicioApplication application) {
        return application.getContentResolver();
    }
}
