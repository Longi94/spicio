package com.tlongdev.spicio.storage;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.google.gson.Gson;
import com.tlongdev.spicio.BuildConfig;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerStorageComponent;
import com.tlongdev.spicio.component.StorageComponent;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.domain.model.Watched;
import com.tlongdev.spicio.module.FakeAppModule;
import com.tlongdev.spicio.module.FakeStorageModule;
import com.tlongdev.spicio.network.converter.TraktModelConverter;
import com.tlongdev.spicio.network.model.trakt.TraktEpisode;
import com.tlongdev.spicio.storage.DatabaseContract.EpisodesEntry;
import com.tlongdev.spicio.storage.dao.EpisodeDao;
import com.tlongdev.spicio.storage.dao.impl.EpisodeDaoImpl;
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
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Long
 * @since 2016. 03. 07.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class EpisodeDaoTest {

    private ContentResolver mContentResolver;
    private ShadowContentResolver mShadowContentResolver;

    private EpisodeDao mEpisodeDao;

    private Episode mEpisode;
    private Episode mAnotherEpisode;

    @Before
    public void setUp() throws Exception {
        DatabaseProvider provider = new DatabaseProvider();
        mContentResolver = RuntimeEnvironment.application.getContentResolver();
        ShadowContentResolver.registerProvider(DatabaseContract.CONTENT_AUTHORITY, provider);
        provider.onCreate();
        mShadowContentResolver = Shadows.shadowOf(mContentResolver);

        deleteAllRecords(EpisodesEntry.CONTENT_URI);

        StorageComponent storageComponent = DaggerStorageComponent.builder()
                .spicioAppModule(new FakeAppModule(RuntimeEnvironment.application))
                .storageModule(new FakeStorageModule())
                .build();

        ((SpicioApplication) RuntimeEnvironment.application).setStorageComponent(storageComponent);

        mEpisodeDao = new EpisodeDaoImpl((SpicioApplication) RuntimeEnvironment.application);

        InputStream is = getClass().getClassLoader().getResourceAsStream("trakt_episode_detail_mock.json");
        String dummyResponse = TestUtils.convertStreamToString(is);
        Gson gson = new Gson();

        TraktEpisode traktEpisode = gson.fromJson(dummyResponse, TraktEpisode.class);
        mEpisode = TraktModelConverter.convertToEpisode(traktEpisode);


        is = getClass().getClassLoader().getResourceAsStream("trakt_episode_detail_mock2.json");
        dummyResponse = TestUtils.convertStreamToString(is);

        traktEpisode = gson.fromJson(dummyResponse, TraktEpisode.class);
        mAnotherEpisode = TraktModelConverter.convertToEpisode(traktEpisode);

        List<Episode> episodes = new LinkedList<>();

        episodes.add(mEpisode);
        episodes.add(mAnotherEpisode);

        int rowsInserted = mEpisodeDao.insertAllEpisodes(episodes);
        assertEquals(2, rowsInserted);

        Season season = new Season();
        season.setSeriesId(mEpisode.getSeriesId());
        season.setNumber(mEpisode.getSeason());

        List<Season> seasons = new LinkedList<>();
        seasons.add(season);

        rowsInserted = mEpisodeDao.insertAllSeasons(seasons);
        assertEquals(1, rowsInserted);
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
        Episode episode = mEpisodeDao.getEpisode(mEpisode.getTraktId());
        assertNotNull(episode);

        assertEquals(mEpisode.getSeason(), episode.getSeason());
        assertEquals(mEpisode.getNumber(), episode.getNumber());
        assertEquals(mEpisode.getTitle(), episode.getTitle());
        assertEquals(mEpisode.getTraktId(), episode.getTraktId());
        assertEquals(mEpisode.getTvdbId(), episode.getTvdbId());
        assertEquals(mEpisode.getImdbId(), episode.getImdbId());
        assertEquals(mEpisode.getTmdbId(), episode.getTmdbId());
        assertEquals(mEpisode.getTvRageId(), episode.getTvRageId());
        assertEquals(mEpisode.getAbsoluteNumber(), episode.getAbsoluteNumber());
        assertEquals(mEpisode.getOverview(), episode.getOverview());
        assertEquals(mEpisode.getTraktRating(), episode.getTraktRating(), 0);
        assertEquals(mEpisode.getTraktRatingCount(), episode.getTraktRatingCount());
        assertEquals(mEpisode.getFirstAired().getMillis(), episode.getFirstAired().getMillis());
        assertEquals(Watched.NONE, episode.isWatched());
        assertFalse(episode.isLiked());
    }

    @Test
    public void testQueryAll() {
        List<Episode> episodes = mEpisodeDao.getAllEpisodes();
        assertNotNull(episodes);

        Episode episode = episodes.get(0);
        assertNotNull(episode);

        assertEquals(mEpisode.getSeason(), episode.getSeason());
        assertEquals(mEpisode.getNumber(), episode.getNumber());
        assertEquals(mEpisode.getTitle(), episode.getTitle());
        assertEquals(mEpisode.getTraktId(), episode.getTraktId());
        assertEquals(mEpisode.getTvdbId(), episode.getTvdbId());
        assertEquals(mEpisode.getImdbId(), episode.getImdbId());
        assertEquals(mEpisode.getTmdbId(), episode.getTmdbId());
        assertEquals(mEpisode.getTvRageId(), episode.getTvRageId());
        assertEquals(mEpisode.getAbsoluteNumber(), episode.getAbsoluteNumber());
        assertEquals(mEpisode.getOverview(), episode.getOverview());
        assertEquals(mEpisode.getTraktRating(), episode.getTraktRating(), 0);
        assertEquals(mEpisode.getTraktRatingCount(), episode.getTraktRatingCount());
        assertEquals(mEpisode.getFirstAired().getMillis(), episode.getFirstAired().getMillis());
        assertEquals(Watched.NONE, episode.isWatched());
        assertFalse(episode.isLiked());

        episode = episodes.get(1);
        assertNotNull(episode);

        assertEquals(mAnotherEpisode.getSeason(), episode.getSeason());
        assertEquals(mAnotherEpisode.getNumber(), episode.getNumber());
        assertEquals(mAnotherEpisode.getTitle(), episode.getTitle());
        assertEquals(mAnotherEpisode.getTraktId(), episode.getTraktId());
        assertEquals(mAnotherEpisode.getTvdbId(), episode.getTvdbId());
        assertEquals(mAnotherEpisode.getImdbId(), episode.getImdbId());
        assertEquals(mAnotherEpisode.getTmdbId(), episode.getTmdbId());
        assertEquals(mAnotherEpisode.getTvRageId(), episode.getTvRageId());
        assertEquals(mAnotherEpisode.getAbsoluteNumber(), episode.getAbsoluteNumber());
        assertEquals(mAnotherEpisode.getOverview(), episode.getOverview());
        assertEquals(mAnotherEpisode.getTraktRating(), episode.getTraktRating(), 0);
        assertEquals(mAnotherEpisode.getTraktRatingCount(), episode.getTraktRatingCount());
        assertEquals(mAnotherEpisode.getFirstAired().getMillis(), episode.getFirstAired().getMillis());
        assertEquals(Watched.NONE, episode.isWatched());
        assertFalse(episode.isLiked());

    }

    @Test
    public void testWatched() {
        assertTrue(mEpisodeDao.setWatched(mEpisode.getTraktId(), true));
        assertTrue(mEpisodeDao.isWatched(mEpisode.getTraktId()));

        Episode episode = mEpisodeDao.getEpisode(mEpisode.getTraktId());
        assertEquals(Watched.WATCHED, episode.isWatched());
    }

    @Test
    public void testLiked() {
        int rowsUpdated = mEpisodeDao.setLiked(mEpisode.getTraktId(), true);
        assertEquals(rowsUpdated, 1);

        Episode episode = mEpisodeDao.getEpisode(mEpisode.getTraktId());
        assertTrue(episode.isLiked());
    }

    @Test
    public void testSkipped() {
        assertTrue(mEpisodeDao.setWatched(mEpisode.getTraktId(), true));

        Episode episode = mEpisodeDao.getEpisode(mEpisode.getTraktId());
        assertEquals(Watched.SKIPPED, episode.isWatched());
    }

    @Test
    public void testGetSeasons() {
        List<Season> seasons = mEpisodeDao.getAllSeasons(mEpisode.getSeriesId());
        assertNotNull(seasons);
        assertEquals(1, seasons.size());

        Season season = seasons.get(0);
        assertNotNull(season);
        assertEquals(mEpisode.getSeriesId(), season.getSeriesId());
        assertEquals(mEpisode.getSeason(), season.getNumber());
        assertEquals(0, season.getWatchCount());
        assertEquals(0, season.getSkipCount());
    }

    @Test
    public void testGetSeasonsWithWatched() {

        testWatched();

        List<Season> seasons = mEpisodeDao.getAllSeasons(mEpisode.getSeriesId());
        assertNotNull(seasons);
        assertEquals(1, seasons.size());

        Season season = seasons.get(0);
        assertNotNull(season);
        assertEquals(mEpisode.getSeriesId(), season.getSeriesId());
        assertEquals(mEpisode.getSeason(), season.getNumber());
        assertEquals(1, season.getWatchCount());
        assertEquals(0, season.getSkipCount());
    }

    @Test
    public void testGetSeasonsWithSkipped() {

        testSkipped();

        List<Season> seasons = mEpisodeDao.getAllSeasons(mEpisode.getSeriesId());
        assertNotNull(seasons);
        assertEquals(1, seasons.size());

        Season season = seasons.get(0);
        assertNotNull(season);
        assertEquals(mEpisode.getSeriesId(), season.getSeriesId());
        assertEquals(mEpisode.getSeason(), season.getNumber());
        assertEquals(0, season.getWatchCount());
        assertEquals(1, season.getSkipCount());
    }

    @Test
    public void testRetainCheck() {
        testWatched();

        List<Episode> episodes = new LinkedList<>();

        episodes.add(mEpisode);
        episodes.add(mAnotherEpisode);

        int rowsInserted = mEpisodeDao.insertAllEpisodes(episodes);
        assertEquals(2, rowsInserted);

        Episode episode = mEpisodeDao.getEpisode(mEpisode.getTraktId());
        assertNotNull(episode);
        assertEquals(Watched.WATCHED, episode.isWatched());
    }
}
