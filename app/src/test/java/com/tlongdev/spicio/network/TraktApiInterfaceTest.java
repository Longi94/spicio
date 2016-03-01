package com.tlongdev.spicio.network;

import com.tlongdev.spicio.network.model.TraktIds;
import com.tlongdev.spicio.network.model.TraktImage;
import com.tlongdev.spicio.network.model.TraktImages;
import com.tlongdev.spicio.network.model.TraktSearchResult;
import com.tlongdev.spicio.network.model.TraktSeries;
import com.tlongdev.spicio.util.TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Long
 * @since 2016. 03. 01.
 */
@RunWith(MockitoJUnitRunner.class)
public class TraktApiInterfaceTest {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSearchByTextParsing() throws IOException {

        InputStream is = getClass().getClassLoader().getResourceAsStream("trakt_search_series_mock.json");
        assertNotNull(is);

        String dummyResponse = TestUtils.convertStreamToString(is);
        assertNotNull(dummyResponse);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new FakeInterceptor(dummyResponse, "json"))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TraktApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        TraktApiInterface service = retrofit.create(TraktApiInterface.class);

        Response<List<TraktSearchResult>> response = service.searchByText("thrones", "show").execute();

        List<TraktSearchResult> searchResults = response.body();
        assertNotNull(searchResults);
        assertEquals(10, searchResults.size());

        TraktSearchResult result = searchResults.get(0);
        assertNotNull(result);
        assertEquals("show", result.getType());
        assertEquals(58.853703, result.getScore(), 0);

        TraktSeries series = result.getSeries();
        assertNotNull(series);
        assertEquals("Game of Thrones", series.getTitle());
        assertEquals("Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and the icy horrors beyond.", series.getOverview());
        assertEquals(2011, series.getYear());
        assertEquals("returning series", series.getStatus());

        TraktImages images = series.getImages();
        assertNotNull(images);

        TraktImage poster = images.getPoster();
        assertNotNull(poster);
        assertEquals("https://walter.trakt.us/images/shows/000/001/390/posters/original/93df9cd612.jpg", poster.getFull());
        assertEquals("https://walter.trakt.us/images/shows/000/001/390/posters/medium/93df9cd612.jpg", poster.getMedium());
        assertEquals("https://walter.trakt.us/images/shows/000/001/390/posters/thumb/93df9cd612.jpg", poster.getThumb());

        TraktImage fanart = images.getFanart();
        assertNotNull(fanart);
        assertEquals("https://walter.trakt.us/images/shows/000/001/390/fanarts/original/76d5df8aed.jpg", fanart.getFull());
        assertEquals("https://walter.trakt.us/images/shows/000/001/390/fanarts/medium/76d5df8aed.jpg", fanart.getMedium());
        assertEquals("https://walter.trakt.us/images/shows/000/001/390/fanarts/thumb/76d5df8aed.jpg", fanart.getThumb());

        TraktIds ids = series.getIds();
        assertNotNull(ids);
        assertEquals(1390, ids.getTrakt());
        assertEquals("game-of-thrones", ids.getSlug());
        assertEquals(121361, ids.getTvdb());
        assertEquals("tt0944947", ids.getImdb());
        assertEquals(1399, ids.getTmdb());
        assertEquals(24493, ids.getTvrage());
    }

    @Test
    public void testSeriesDetailsParsing() throws IOException {

        InputStream is = getClass().getClassLoader().getResourceAsStream("trakt_series_details_mock.json");
        assertNotNull(is);

        String dummyResponse = TestUtils.convertStreamToString(is);
        assertNotNull(dummyResponse);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new FakeInterceptor(dummyResponse, "json"))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TraktApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        TraktApiInterface service = retrofit.create(TraktApiInterface.class);

        Response<TraktSeries> response = service.getSeriesDetails("game-of-thrones").execute();

        TraktSeries series = response.body();
        assertNotNull(series);
        assertEquals("Game of Thrones", series.getTitle());
        assertEquals(2011, series.getYear());
        assertEquals("Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and the icy horrors beyond.", series.getOverview());
        assertEquals("2011-04-17T07:00:00.000Z", series.getFirstAired());
        assertEquals(60, series.getRuntime());
        assertEquals("TV-MA", series.getCertification());
        assertEquals("http://youtube.com/watch?v=F9Bo89m2f6g", series.getTrailer());
        assertEquals("http://www.hbo.com/game-of-thrones", series.getHomepage());
        assertEquals("returning series", series.getStatus());
        assertEquals(9.38346, series.getRating(), 0);
        assertEquals(43726, series.getVotes());
        assertEquals("2016-03-01T11:33:10.000Z", series.getUpdatedAt());
        assertEquals("en", series.getLanguage());

        TraktIds ids = series.getIds();
        assertNotNull(ids);
        assertEquals(1390, ids.getTrakt());
        assertEquals("game-of-thrones", ids.getSlug());
        assertEquals(121361, ids.getTvdb());
        assertEquals("tt0944947", ids.getImdb());
        assertEquals(1399, ids.getTmdb());
        assertEquals(24493, ids.getTvrage());

        List<String> availableTranslations = series.getAvailableTranslations();
        assertArrayEquals(new String[]{
                "en",
                "fr",
                "it",
                "de",
                "es",
                "ru",
                "hu",
                "el",
                "cs",
                "pt",
                "sk",
                "nl",
                "bg",
                "sv",
                "zh",
                "pl",
                "fa",
                "da",
                "tr",
                "he",
                "ro",
                "fi",
                "th",
                "ko",
                "uk",
                "is",
                "lt",
                "hr",
                "bs",
                "ar"
        }, availableTranslations.toArray(new String[availableTranslations.size()]));

        List<String> genres = series.getGenres();
        assertArrayEquals(new String[]{
                "drama",
                "fantasy",
                "science-fiction",
                "history",
                "adventure",
                "action"
        }, genres.toArray(new String[genres.size()]));

        assertEquals(50, series.getAiredEpisodes());
    }

    @Test
    public void testSeriesImagesParsing() throws IOException {

        InputStream is = getClass().getClassLoader().getResourceAsStream("trakt_series_images_mock.json");
        assertNotNull(is);

        String dummyResponse = TestUtils.convertStreamToString(is);
        assertNotNull(dummyResponse);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new FakeInterceptor(dummyResponse, "json"))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TraktApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        TraktApiInterface service = retrofit.create(TraktApiInterface.class);

        Response<TraktSeries> response = service.getSeriesImages("game-of-thrones").execute();

        TraktSeries series = response.body();
        assertNotNull(series);
        assertEquals("Game of Thrones", series.getTitle());
        assertEquals(2011, series.getYear());

        TraktIds ids = series.getIds();
        assertNotNull(ids);
        assertEquals(1390, ids.getTrakt());
        assertEquals("game-of-thrones", ids.getSlug());
        assertEquals(121361, ids.getTvdb());
        assertEquals("tt0944947", ids.getImdb());
        assertEquals(1399, ids.getTmdb());
        assertEquals(24493, ids.getTvrage());

        TraktImages images = series.getImages();
        assertNotNull(images);

        TraktImage poster = images.getPoster();
        assertNotNull(poster);
        assertEquals("https://walter.trakt.us/images/shows/000/001/390/posters/original/93df9cd612.jpg", poster.getFull());
        assertEquals("https://walter.trakt.us/images/shows/000/001/390/posters/medium/93df9cd612.jpg", poster.getMedium());
        assertEquals("https://walter.trakt.us/images/shows/000/001/390/posters/thumb/93df9cd612.jpg", poster.getThumb());

        TraktImage fanart = images.getFanart();
        assertNotNull(fanart);
        assertEquals("https://walter.trakt.us/images/shows/000/001/390/fanarts/original/76d5df8aed.jpg", fanart.getFull());
        assertEquals("https://walter.trakt.us/images/shows/000/001/390/fanarts/medium/76d5df8aed.jpg", fanart.getMedium());
        assertEquals("https://walter.trakt.us/images/shows/000/001/390/fanarts/thumb/76d5df8aed.jpg", fanart.getThumb());

        TraktImage logo = images.getLogo();
        assertNotNull(logo);
        assertEquals("https://walter.trakt.us/images/shows/000/001/390/logos/original/13b614ad43.png", logo.getFull());

        TraktImage clearart = images.getClearart();
        assertNotNull(clearart);
        assertEquals("https://walter.trakt.us/images/shows/000/001/390/cleararts/original/5cbde9e647.png", clearart.getFull());

        TraktImage banner = images.getBanner();
        assertNotNull(banner);
        assertEquals("https://walter.trakt.us/images/shows/000/001/390/banners/original/9fefff703d.jpg", banner.getFull());

        TraktImage thumb = images.getThumb();
        assertNotNull(thumb);
        assertEquals("https://walter.trakt.us/images/shows/000/001/390/thumbs/original/7beccbd5a1.jpg", thumb.getFull());
    }
}
