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
import com.tlongdev.spicio.storage.DatabaseContract.SeriesEntry;
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

    private Series mSeries;
    private Series mAnotherSeries;

    @Before
    public void setUp() throws Exception {
        DatabaseProvider provider = new DatabaseProvider();
        mContentResolver = RuntimeEnvironment.application.getContentResolver();
        ShadowContentResolver.registerProvider(DatabaseContract.CONTENT_AUTHORITY, provider);
        provider.onCreate();
        mShadowContentResolver = Shadows.shadowOf(mContentResolver);

        deleteAllRecords(SeriesEntry.CONTENT_URI);

        Application mockApplication = mock(Application.class);
        when(mockApplication.getContentResolver()).thenReturn(mContentResolver);

        StorageComponent storageComponent = DaggerStorageComponent.builder()
                .spicioAppModule(new FakeAppModule(mockApplication))
                .storageModule(new FakeStorageModule())
                .build();

        ((SpicioApplication) RuntimeEnvironment.application).setStorageComponent(storageComponent);

        mSeriesDao = new SeriesDaoImpl((SpicioApplication) RuntimeEnvironment.application);


        Images images = new Images();
        images.setPoster(new Image());
        images.getPoster().setFull("test_poster_full");
        images.getPoster().setThumb("test_poster_thumb");
        images.setThumb(new Image());
        images.getThumb().setFull("test_thumb_full");

        InputStream is = getClass().getClassLoader().getResourceAsStream("trakt_series_details_mock.json");
        String dummyResponse = TestUtils.convertStreamToString(is);
        Gson gson = new Gson();
        TraktSeries traktSeries = gson.fromJson(dummyResponse, TraktSeries.class);
        mSeries = TraktModelConverter.convertToSeries(traktSeries);
        mSeries.setImages(images);

        Uri uri = mSeriesDao.insertSeries(mSeries);
        assertNotNull(uri);


        is = getClass().getClassLoader().getResourceAsStream("trakt_series_details_mock2.json");
        dummyResponse = TestUtils.convertStreamToString(is);
        traktSeries = gson.fromJson(dummyResponse, TraktSeries.class);
        mAnotherSeries = TraktModelConverter.convertToSeries(traktSeries);
        mAnotherSeries.setImages(images);

        uri = mSeriesDao.insertSeries(mAnotherSeries);
        assertNotNull(uri);
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

        Series newSeries = mSeriesDao.getSeries(mSeries.getTraktId());

        assertNotNull(newSeries);
        assertEquals(mSeries.getTitle(), newSeries.getTitle());
        assertEquals(mSeries.getYear(), newSeries.getYear());
        assertEquals(mSeries.getOverview(), newSeries.getOverview());

        assertEquals(mSeries.getFirstAired().getMillis(), newSeries.getFirstAired().getMillis());
        assertEquals(mSeries.getRuntime(), newSeries.getRuntime());
        assertEquals(mSeries.getCertification(), newSeries.getCertification());
        assertEquals(mSeries.getNetwork(), newSeries.getNetwork());
        assertEquals(mSeries.getTrailer(), newSeries.getTrailer());
        assertEquals(mSeries.getStatus(), newSeries.getStatus());
        assertEquals(mSeries.getTraktRating(), newSeries.getTraktRating(), 0);
        assertEquals(mSeries.getTraktRatingCount(), newSeries.getTraktRatingCount());

        assertEquals(mSeries.getDayOfAiring(), newSeries.getDayOfAiring());
        assertEquals(mSeries.getTimeOfAiring().getMillisOfDay(), newSeries.getTimeOfAiring().getMillisOfDay());
        assertEquals(mSeries.getAirTimeZone().getID(), newSeries.getAirTimeZone().getID());

        assertEquals(mSeries.getTraktId(), newSeries.getTraktId());
        assertEquals(mSeries.getSlugName(), newSeries.getSlugName());
        assertEquals(mSeries.getTvdbId(), newSeries.getTvdbId());
        assertEquals(mSeries.getImdbId(), newSeries.getImdbId());
        assertEquals(mSeries.getTmdbId(), newSeries.getTmdbId());
        assertEquals(mSeries.getTvRageId(), newSeries.getTvRageId());

        assertEquals(mSeries.getImages().getPoster().getFull(), newSeries.getImages().getPoster().getFull());
        assertEquals(mSeries.getImages().getPoster().getThumb(), newSeries.getImages().getPoster().getThumb());
        assertEquals(mSeries.getImages().getThumb().getFull(), newSeries.getImages().getThumb().getFull());

        assertArrayEquals(mSeries.getGenres(), newSeries.getGenres());
    }

    @Test
    public void testMultipleQuery() {

        List<Series> seriesList = mSeriesDao.getAllSeries();
        assertNotNull(seriesList);

        Series newSeries = seriesList.get(0);

        assertNotNull(newSeries);
        assertEquals(mSeries.getTitle(), newSeries.getTitle());
        assertEquals(mSeries.getYear(), newSeries.getYear());
        assertEquals(mSeries.getOverview(), newSeries.getOverview());

        assertEquals(mSeries.getFirstAired().getMillis(), newSeries.getFirstAired().getMillis());
        assertEquals(mSeries.getRuntime(), newSeries.getRuntime());
        assertEquals(mSeries.getCertification(), newSeries.getCertification());
        assertEquals(mSeries.getNetwork(), newSeries.getNetwork());
        assertEquals(mSeries.getTrailer(), newSeries.getTrailer());
        assertEquals(mSeries.getStatus(), newSeries.getStatus());
        assertEquals(mSeries.getTraktRating(), newSeries.getTraktRating(), 0);
        assertEquals(mSeries.getTraktRatingCount(), newSeries.getTraktRatingCount());

        assertEquals(mSeries.getDayOfAiring(), newSeries.getDayOfAiring());
        assertEquals(mSeries.getTimeOfAiring().getMillisOfDay(), newSeries.getTimeOfAiring().getMillisOfDay());
        assertEquals(mSeries.getAirTimeZone().getID(), newSeries.getAirTimeZone().getID());

        assertEquals(mSeries.getTraktId(), newSeries.getTraktId());
        assertEquals(mSeries.getSlugName(), newSeries.getSlugName());
        assertEquals(mSeries.getTvdbId(), newSeries.getTvdbId());
        assertEquals(mSeries.getImdbId(), newSeries.getImdbId());
        assertEquals(mSeries.getTmdbId(), newSeries.getTmdbId());
        assertEquals(mSeries.getTvRageId(), newSeries.getTvRageId());

        assertEquals(mSeries.getImages().getPoster().getFull(), newSeries.getImages().getPoster().getFull());
        assertEquals(mSeries.getImages().getPoster().getThumb(), newSeries.getImages().getPoster().getThumb());
        assertEquals(mSeries.getImages().getThumb().getFull(), newSeries.getImages().getThumb().getFull());

        assertArrayEquals(mSeries.getGenres(), newSeries.getGenres());

        newSeries = seriesList.get(1);

        assertNotNull(newSeries);
        assertEquals(mAnotherSeries.getTitle(), newSeries.getTitle());
        assertEquals(mAnotherSeries.getYear(), newSeries.getYear());
        assertEquals(mAnotherSeries.getOverview(), newSeries.getOverview());

        assertEquals(mAnotherSeries.getFirstAired().getMillis(), newSeries.getFirstAired().getMillis());
        assertEquals(mAnotherSeries.getRuntime(), newSeries.getRuntime());
        assertEquals(mAnotherSeries.getCertification(), newSeries.getCertification());
        assertEquals(mAnotherSeries.getNetwork(), newSeries.getNetwork());
        assertEquals(mAnotherSeries.getTrailer(), newSeries.getTrailer());
        assertEquals(mAnotherSeries.getStatus(), newSeries.getStatus());
        assertEquals(mAnotherSeries.getTraktRating(), newSeries.getTraktRating(), 0);
        assertEquals(mAnotherSeries.getTraktRatingCount(), newSeries.getTraktRatingCount());

        assertEquals(mAnotherSeries.getDayOfAiring(), newSeries.getDayOfAiring());
        assertEquals(mAnotherSeries.getTimeOfAiring().getMillisOfDay(), newSeries.getTimeOfAiring().getMillisOfDay());
        assertEquals(mAnotherSeries.getAirTimeZone().getID(), newSeries.getAirTimeZone().getID());

        assertEquals(mAnotherSeries.getTraktId(), newSeries.getTraktId());
        assertEquals(mAnotherSeries.getSlugName(), newSeries.getSlugName());
        assertEquals(mAnotherSeries.getTvdbId(), newSeries.getTvdbId());
        assertEquals(mAnotherSeries.getImdbId(), newSeries.getImdbId());
        assertEquals(mAnotherSeries.getTmdbId(), newSeries.getTmdbId());
        assertEquals(mAnotherSeries.getTvRageId(), newSeries.getTvRageId());

        assertEquals(mAnotherSeries.getImages().getPoster().getFull(), newSeries.getImages().getPoster().getFull());
        assertEquals(mAnotherSeries.getImages().getPoster().getThumb(), newSeries.getImages().getPoster().getThumb());
        assertEquals(mAnotherSeries.getImages().getThumb().getFull(), newSeries.getImages().getThumb().getFull());

        assertArrayEquals(mAnotherSeries.getGenres(), newSeries.getGenres());
    }
}
