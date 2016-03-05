package com.tlongdev.spicio.storage.dao;

import android.net.Uri;

import com.tlongdev.spicio.domain.model.Series;

import java.util.List;

/**
 * Middle Layer, Converter.
 *
 * @author Long
 * @since 2016. 02. 29.
 */
public interface SeriesDao {

    /**
     * Get a single series from the database.
     *
     * @param seriesId the id of the series
     * @return the series
     */
    Series getSeries(int seriesId);

    /**
     * Get all the series from the database.
     *
     * @return all the series
     */
    List<Series> getAllSeries();

    /**
     * Inserts a series into the database.
     *
     * @param series the series to delete
     */
    Uri insertSeries(Series series);

    /**
     * Deletes a series from the database
     *
     * @param seriesId the id of the series
     */
    void deleteSeries(int seriesId);

    /**
     * Deletes all the series records from the database.
     */
    void deleteAllSeries();
}
