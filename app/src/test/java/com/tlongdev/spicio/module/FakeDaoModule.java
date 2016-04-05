package com.tlongdev.spicio.module;

import android.app.Application;

import com.tlongdev.spicio.storage.dao.EpisodeDao;
import com.tlongdev.spicio.storage.dao.SeriesDao;
import com.tlongdev.spicio.storage.dao.UserDao;

/**
 * @author Long
 * @since 2016. 03. 07.
 */
public class FakeDaoModule extends DaoModule {

    private SeriesDao mSeriesDao;
    private EpisodeDao mEpisodeDao;
    private UserDao mUserDao;

    @Override
    SeriesDao provideSeriesDao(Application application) {
        return mSeriesDao;
    }

    @Override
    EpisodeDao provideEpisodeDao(Application application) {
        return mEpisodeDao;
    }

    @Override
    UserDao provideUserDao(Application application) {
        return mUserDao;
    }

    public void setSeriesDao(SeriesDao seriesDao) {
        this.mSeriesDao = seriesDao;
    }

    public void setEpisodeDao(EpisodeDao episodeDao) {
        this.mEpisodeDao = episodeDao;
    }

    public void setUserDao(UserDao userDao) {
        mUserDao = userDao;
    }
}
