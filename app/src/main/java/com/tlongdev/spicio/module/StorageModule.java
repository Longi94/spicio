package com.tlongdev.spicio.module;

import android.app.Application;
import android.content.ContentResolver;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.storage.dao.EpisodeDao;
import com.tlongdev.spicio.storage.dao.SeriesDao;
import com.tlongdev.spicio.storage.dao.impl.SeriesDaoImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
@Module
public class StorageModule {

    @Provides
    @Singleton
    ContentResolver provideContentResolver(Application application) {
        return application.getContentResolver();
    }
    
    @Provides
    @Singleton
    SeriesDao provideSeriesDao(Application application) {
        return new SeriesDaoImpl((SpicioApplication) application);
    }

    @Provides
    @Singleton
    EpisodeDao provideEpisodeDao(Application application) {
        return null; // TODO: 2016. 03. 06.  
    }
}
