package com.tlongdev.spicio.module;

import android.app.Application;

import com.tlongdev.spicio.storage.dao.SeriesDao;

/**
 * @author Long
 * @since 2016. 03. 07.
 */
public class FakeDaoModule extends DaoModule {

    private SeriesDao mSeriesDao;

    @Override
    SeriesDao provideSeriesDao(Application application) {
        return mSeriesDao;
    }

    public void setSeriesDao(SeriesDao seriesDao) {
        this.mSeriesDao = seriesDao;
    }
}
