package com.tlongdev.spicio.storage;

import android.app.Application;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.google.gson.Gson;
import com.tlongdev.spicio.BuildConfig;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerStorageComponent;
import com.tlongdev.spicio.component.StorageComponent;
import com.tlongdev.spicio.domain.model.Image;
import com.tlongdev.spicio.domain.model.Images;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.module.FakeAppModule;
import com.tlongdev.spicio.module.FakeStorageModule;
import com.tlongdev.spicio.network.converter.TraktModelConverter;
import com.tlongdev.spicio.network.model.TraktSeries;
import com.tlongdev.spicio.storage.dao.SeriesDao;
import com.tlongdev.spicio.storage.dao.impl.SeriesDaoImpl;
import com.tlongdev.spicio.util.TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class SeriesDaoTest {

    private ContentResolver mContentResolver;
    private ShadowContentResolver mShadowContentResolver;

    private SeriesDao mSeriesDao;

    @Before
    public void setUp() throws Exception {
        DatabaseProvider provider = new DatabaseProvider();
        mContentResolver = RuntimeEnvironment.application.getContentResolver();
        ShadowContentResolver.registerProvider(DatabaseContract.CONTENT_AUTHORITY, provider);
        provider.onCreate();
        mShadowContentResolver = Shadows.shadowOf(mContentResolver);

        deleteAllRecords(DatabaseContract.SeriesEntry.CONTENT_URI);

        Application mockApplication = mock(Application.class);
        when(mockApplication.getContentResolver()).thenReturn(mContentResolver);

        StorageComponent storageComponent = DaggerStorageComponent.builder()
                .spicioAppModule(new FakeAppModule(mockApplication))
                .storageModule(new FakeStorageModule())
                .build();

        ((SpicioApplication) RuntimeEnvironment.application).setStorageComponent(storageComponent);

        mSeriesDao = new SeriesDaoImpl((SpicioApplication) RuntimeEnvironment.application);
    }

    public void deleteAllRecords(Uri contentUri) {
        mContentResolver.delete(contentUri, null, null);

        Cursor cursor = mContentResolver.query(
                contentUri, null, null, null, null
        );

        assertNotNull("Error: Query returned null during delete", cursor);
        assertEquals("Error: Records not deleted from Repos table during delete", 0, cursor.getCount());

        cursor.close();
    }

    @Test
    public void testBasicQuery() {

        InputStream is = getClass().getClassLoader().getResourceAsStream("trakt_series_details_mock.json");
        String dummyResponse = TestUtils.convertStreamToString(is);
        Gson gson = new Gson();
        TraktSeries traktSeries = gson.fromJson(dummyResponse, TraktSeries.class);
        Series series = TraktModelConverter.convertToSeries(traktSeries);
        series.setImages(new Images());
        series.getImages().setPoster(new Image());
        series.getImages().getPoster().setFull("test_poster_full");
        series.getImages().getPoster().setThumb("test_poster_thumb");
        series.getImages().setThumb(new Image());
        series.getImages().getThumb().setFull("test_thumb_full");

        Uri uri = mSeriesDao.insertSeries(series);
        assertNotNull(uri);

        Series newSeries = mSeriesDao.getSeries(series.getTraktId());

        assertNotNull(newSeries);
        assertEquals(series.getTitle(), newSeries.getTitle());
        assertEquals(series.getYear(), newSeries.getYear());
        assertEquals(series.getOverview(), newSeries.getOverview());

        assertEquals(series.getFirstAired().getMillis(), newSeries.getFirstAired().getMillis());
        assertEquals(series.getRuntime(), newSeries.getRuntime());
        assertEquals(series.getCertification(), newSeries.getCertification());
        assertEquals(series.getNetwork(), newSeries.getNetwork());
        assertEquals(series.getTrailer(), newSeries.getTrailer());
        assertEquals(series.getStatus(), newSeries.getStatus());
        assertEquals(series.getTraktRating(), newSeries.getTraktRating(), 0);
        assertEquals(series.getTraktRatingCount(), newSeries.getTraktRatingCount());

        assertEquals(series.getDayOfAiring(), newSeries.getDayOfAiring());
        assertEquals(series.getTimeOfAiring().getMillisOfDay(), newSeries.getTimeOfAiring().getMillisOfDay());
        assertEquals(series.getAirTimeZone().getID(), newSeries.getAirTimeZone().getID());

        assertEquals(series.getTraktId(), newSeries.getTraktId());
        assertEquals(series.getSlugName(), newSeries.getSlugName());
        assertEquals(series.getTvdbId(), newSeries.getTvdbId());
        assertEquals(series.getImdbId(), newSeries.getImdbId());
        assertEquals(series.getTmdbId(), newSeries.getTmdbId());
        assertEquals(series.getTvRageId(), newSeries.getTvRageId());

        assertEquals(series.getImages().getPoster().getFull(), newSeries.getImages().getPoster().getFull());
        assertEquals(series.getImages().getPoster().getThumb(), newSeries.getImages().getPoster().getThumb());
        assertEquals(series.getImages().getThumb().getFull(), newSeries.getImages().getThumb().getFull());

        assertArrayEquals(series.getGenres(), newSeries.getGenres());
    }

    @Test
    public void testMultipleQuery() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("trakt_series_details_mock.json");
        String dummyResponse = TestUtils.convertStreamToString(is);
        Gson gson = new Gson();
        TraktSeries traktSeries = gson.fromJson(dummyResponse, TraktSeries.class);
        Series series = TraktModelConverter.convertToSeries(traktSeries);
        Series anotherSeries = TraktModelConverter.convertToSeries(traktSeries);

        Images images = new Images();
        images.setPoster(new Image());
        images.getPoster().setFull("test_poster_full");
        images.getPoster().setThumb("test_poster_thumb");
        images.setThumb(new Image());
        images.getThumb().setFull("test_thumb_full");

        series.setImages(images);
        anotherSeries.setImages(images);
        anotherSeries.setTraktId(0);

        Uri uri = mSeriesDao.insertSeries(series);
        assertNotNull(uri);
        uri = mSeriesDao.insertSeries(anotherSeries);
        assertNotNull(uri);

        List<Series> seriesList = mSeriesDao.getAllSeries();
        assertNotNull(seriesList);

        Series newSeries = seriesList.get(0);

        assertNotNull(newSeries);
        assertEquals(series.getTitle(), newSeries.getTitle());
        assertEquals(series.getYear(), newSeries.getYear());
        assertEquals(series.getOverview(), newSeries.getOverview());

        assertEquals(series.getFirstAired().getMillis(), newSeries.getFirstAired().getMillis());
        assertEquals(series.getRuntime(), newSeries.getRuntime());
        assertEquals(series.getCertification(), newSeries.getCertification());
        assertEquals(series.getNetwork(), newSeries.getNetwork());
        assertEquals(series.getTrailer(), newSeries.getTrailer());
        assertEquals(series.getStatus(), newSeries.getStatus());
        assertEquals(series.getTraktRating(), newSeries.getTraktRating(), 0);
        assertEquals(series.getTraktRatingCount(), newSeries.getTraktRatingCount());

        assertEquals(series.getDayOfAiring(), newSeries.getDayOfAiring());
        assertEquals(series.getTimeOfAiring().getMillisOfDay(), newSeries.getTimeOfAiring().getMillisOfDay());
        assertEquals(series.getAirTimeZone().getID(), newSeries.getAirTimeZone().getID());

        assertEquals(series.getTraktId(), newSeries.getTraktId());
        assertEquals(series.getSlugName(), newSeries.getSlugName());
        assertEquals(series.getTvdbId(), newSeries.getTvdbId());
        assertEquals(series.getImdbId(), newSeries.getImdbId());
        assertEquals(series.getTmdbId(), newSeries.getTmdbId());
        assertEquals(series.getTvRageId(), newSeries.getTvRageId());

        assertEquals(series.getImages().getPoster().getFull(), newSeries.getImages().getPoster().getFull());
        assertEquals(series.getImages().getPoster().getThumb(), newSeries.getImages().getPoster().getThumb());
        assertEquals(series.getImages().getThumb().getFull(), newSeries.getImages().getThumb().getFull());

        assertArrayEquals(series.getGenres(), newSeries.getGenres());

        newSeries = seriesList.get(1);

        assertNotNull(newSeries);
        assertEquals(anotherSeries.getTitle(), newSeries.getTitle());
        assertEquals(anotherSeries.getYear(), newSeries.getYear());
        assertEquals(anotherSeries.getOverview(), newSeries.getOverview());

        assertEquals(anotherSeries.getFirstAired().getMillis(), newSeries.getFirstAired().getMillis());
        assertEquals(anotherSeries.getRuntime(), newSeries.getRuntime());
        assertEquals(anotherSeries.getCertification(), newSeries.getCertification());
        assertEquals(anotherSeries.getNetwork(), newSeries.getNetwork());
        assertEquals(anotherSeries.getTrailer(), newSeries.getTrailer());
        assertEquals(anotherSeries.getStatus(), newSeries.getStatus());
        assertEquals(anotherSeries.getTraktRating(), newSeries.getTraktRating(), 0);
        assertEquals(anotherSeries.getTraktRatingCount(), newSeries.getTraktRatingCount());

        assertEquals(anotherSeries.getDayOfAiring(), newSeries.getDayOfAiring());
        assertEquals(anotherSeries.getTimeOfAiring().getMillisOfDay(), newSeries.getTimeOfAiring().getMillisOfDay());
        assertEquals(anotherSeries.getAirTimeZone().getID(), newSeries.getAirTimeZone().getID());

        assertEquals(anotherSeries.getTraktId(), newSeries.getTraktId());
        assertEquals(anotherSeries.getSlugName(), newSeries.getSlugName());
        assertEquals(anotherSeries.getTvdbId(), newSeries.getTvdbId());
        assertEquals(anotherSeries.getImdbId(), newSeries.getImdbId());
        assertEquals(anotherSeries.getTmdbId(), newSeries.getTmdbId());
        assertEquals(anotherSeries.getTvRageId(), newSeries.getTvRageId());

        assertEquals(anotherSeries.getImages().getPoster().getFull(), newSeries.getImages().getPoster().getFull());
        assertEquals(anotherSeries.getImages().getPoster().getThumb(), newSeries.getImages().getPoster().getThumb());
        assertEquals(anotherSeries.getImages().getThumb().getFull(), newSeries.getImages().getThumb().getFull());

        assertArrayEquals(anotherSeries.getGenres(), newSeries.getGenres());
    }
}
