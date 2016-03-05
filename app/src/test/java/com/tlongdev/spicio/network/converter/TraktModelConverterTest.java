package com.tlongdev.spicio.network.converter;

import com.google.gson.Gson;
import com.tlongdev.spicio.BuildConfig;
import com.tlongdev.spicio.domain.model.Day;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.model.Status;
import com.tlongdev.spicio.network.model.TraktSeries;
import com.tlongdev.spicio.util.TestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class TraktModelConverterTest {

    @Test
    public void testConvertSeries() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("trakt_series_details_mock.json");
        assertNotNull(is);

        String dummyResponse = TestUtils.convertStreamToString(is);
        assertNotNull(dummyResponse);

        Gson gson = new Gson();
        TraktSeries traktSeries = gson.fromJson(dummyResponse, TraktSeries.class);
        assertNotNull(traktSeries);

        Series series = TraktModelConverter.convertToSeries(traktSeries);

        assertNotNull(series);
        assertEquals("Game of Thrones", series.getTitle());
        assertEquals(2011, series.getYear());
        assertEquals("Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and the icy horrors beyond.", series.getOverview());

        assertEquals(1303023600000L, series.getFirstAired().getMillis());
        assertEquals(60, series.getRuntime());
        assertEquals("TV-MA", series.getCertification());
        assertEquals("HBO", series.getNetwork());
        assertEquals("http://youtube.com/watch?v=F9Bo89m2f6g", series.getTrailer());
        assertEquals(Status.CONTINUING, series.getStatus());
        assertEquals(9.38346, series.getTraktRating(), 0);
        assertEquals(43726, series.getTraktRatingCount());

        assertEquals(Day.SUNDAY, series.getDayOfAiring());
        assertEquals(75600000L, series.getTimeOfAiring().getMillisOfDay());
        assertEquals("America/New_York", series.getAirTimeZone().getID());

        assertEquals(1390, series.getTraktId());
        assertEquals("game-of-thrones", series.getSlugName());
        assertEquals(121361, series.getTvdbId());
        assertEquals("tt0944947", series.getImdbId());
        assertEquals(1399, series.getTmdbId());
        assertEquals(24493, series.getTvRageId());

        List<String> genres = series.getGenres();
        assertArrayEquals(new String[]{
                "drama",
                "fantasy",
                "science-fiction",
                "history",
                "adventure",
                "action"
        }, genres.toArray(new String[genres.size()]));
    }
}
