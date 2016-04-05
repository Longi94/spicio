package com.tlongdev.spicio.module;

import android.app.Application;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.storage.dao.EpisodeDao;
import com.tlongdev.spicio.storage.dao.SeriesDao;
import com.tlongdev.spicio.storage.dao.UserDao;
import com.tlongdev.spicio.storage.dao.impl.EpisodeDaoImpl;
import com.tlongdev.spicio.storage.dao.impl.SeriesDaoImpl;
import com.tlongdev.spicio.storage.dao.impl.UserDaoImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Long
 * @since 2016. 03. 07.
 */
@Module
public class DaoModule {

    @Provides
    @Singleton
    SeriesDao provideSeriesDao(Application application) {
        return new SeriesDaoImpl((SpicioApplication) application);
    }

    @Provides
    @Singleton
    EpisodeDao provideEpisodeDao(Application application) {
        return new EpisodeDaoImpl((SpicioApplication) application);
    }

    @Provides
    @Singleton
    UserDao provideUserDao(Application application) {
        return new UserDaoImpl((SpicioApplication) application);
    }
}
