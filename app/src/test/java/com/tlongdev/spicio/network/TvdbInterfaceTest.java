package com.tlongdev.spicio.network;

import com.tlongdev.spicio.network.model.TvdbEpisode;
import com.tlongdev.spicio.network.model.TvdbEpisodePayload;
import com.tlongdev.spicio.network.model.TvdbSeries;
import com.tlongdev.spicio.network.model.TvdbSeriesPayload;
import com.tlongdev.spicio.util.TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Tests for the TVDb api interface.
 *
 * @author Long
 * @since 2016. 02. 24.
 */
@RunWith(MockitoJUnitRunner.class)
public class TvdbInterfaceTest {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetSeriesRecordParser() throws IOException {

        InputStream is = getClass().getClassLoader().getResourceAsStream("get_series_mock.xml");
        assertNotNull(is);

        String dummyResponse = TestUtils.convertStreamToString(is);
        assertNotNull(dummyResponse);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new FakeInterceptor(dummyResponse, "xml"))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TvdbInterface.BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(client)
                .build();

        TvdbInterface service = retrofit.create(TvdbInterface.class);

        Response<TvdbSeriesPayload> response = service.getSeriesRecord("", 0).execute();

        TvdbSeriesPayload tvdbSeriesPayload = response.body();
        assertNotNull(tvdbSeriesPayload);

        TvdbSeries series = tvdbSeriesPayload.getSeries().get(0);
        assertNotNull(series);

        assertEquals(121361, series.getId());
        assertEquals("|Emilia Clarke|Peter Dinklage|Kit Harington|Nikolaj Coster-Waldau|Lena Headey|Sophie Turner|Maisie Williams|Liam Cunningham|Gwendoline Christie|Aidan Gillen|Iain Glen|Charles Dance|Jerome Flynn|Alfie Allen|Isaac Hempstead-Wright|Carice van Houten|Natalie Dormer|John Bradley|Conleth Hill|Michelle Fairley|Anton Lesser|Brenock O'Connor|Tobias Menzies|Kristofer Hivju|Julian Glover|Diana Rigg|Ian McElhinney|Ed Skrein|Jonathan Pryce|Alexander Siddig|Nathalie Emmanuel|Tom Wlaschiha|Iwan Rheon|Jacob Anderson|Gemma Whelan|Mark Addy|Indira Varma|Michiel Huisman|Nonso Anozie|Nell Tiger Free|Stephen Dillane|Rory McCann|Finn Jones|Ellie Kendrick|James Cosmo|Sibel Kekilli|Paul Kaye|Ciarán Hinds|Thomas Sangster|Harry Lloyd|Sean Bean|Jason Momoa|Richard Madden|Jack Gleeson|Rose Leslie|Pedro Pascal|Dean-Charles Chapman|Kerry Ingram|Michael McElhatton|Art Parkinson|Mackenzie Crook|Hannah Murray|Amrita Acharia|Aimee Richardson|Eugene Simon|Ron Donachie|Lino Facioli|Oona Chaplin|Kate Dickie|Peter Vaughan|Patrick Malahide|Natalia Tena|Joe Dempsie|Kristian Nairn|Donald Sumpter|Esmé Bianco|Gethin Anthony|", series.getActors());
        assertEquals("Sunday", series.getAirsDayOfWeek());
        assertEquals("9:00 PM", series.getAirsTime());
        assertEquals("TV-MA", series.getContentRating());
        assertEquals("2011-04-17", series.getFirstAired());
        assertEquals("|Adventure|Drama|Fantasy|", series.getGenres());
        assertEquals("tt0944947", series.getImdbId());
        assertEquals("HBO", series.getNetWork());
        assertEquals("Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and the icy horrors beyond.", series.getOverView());
        assertEquals(9.5, series.getTvdbRating(), 0);
        assertEquals(1268, series.getTvdbRatingCount());
        assertEquals(60, series.getRunTime());
        assertEquals("Game of Thrones", series.getName());
        assertEquals("Continuing", series.getStatus());
        assertEquals("graphical/121361-g37.jpg", series.getBannerPath());
        assertEquals("posters/121361-34.jpg", series.getPosterPath());
        assertEquals(null, series.getZapt2itId());
    }

    @Test
    public void testGetEpisodeParser() throws IOException {

        InputStream is = getClass().getClassLoader().getResourceAsStream("get_episode_mock.xml");
        assertNotNull(is);

        String dummyResponse = TestUtils.convertStreamToString(is);
        assertNotNull(dummyResponse);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new FakeInterceptor(dummyResponse, "xml"))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TvdbInterface.BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(client)
                .build();

        TvdbInterface service = retrofit.create(TvdbInterface.class);

        Response<TvdbEpisodePayload> response = service.getEpisode("", 0, 0).execute();

        TvdbEpisodePayload tvdbEpisodePayload = response.body();
        assertNotNull(tvdbEpisodePayload);

        TvdbEpisode episode = tvdbEpisodePayload.getEpisode();
        assertNotNull(episode);

        assertEquals(3254641, episode.getId());
        assertEquals(364731, episode.getSeasonId());
        assertEquals(1, episode.getEpisodeNumber());
        assertEquals("Winter Is Coming", episode.getEpisodeName());
        assertEquals("2011-04-17", episode.getFirstAired());
        assertEquals("|Donald Sumpter|Jamie Sives|Ron Donachie|Joseph Mawle|Roger Allam|Dar Salim|Esmé Bianco|Susan Brown|Bronson Webb|John Standing|Rob Ostlere|Dermot Keaney|Art Parkinson|Callum Wharry|Aimee Richardson|Kristian Nairn|Rania Zouari|Ian Whyte|Spencer Wilding|", episode.getGuestStars());
        assertEquals("Tim Van Patten", episode.getDirector());
        assertEquals("|David Benioff|D. B. Weiss|", episode.getWriters());
        assertEquals("Ned Stark, Lord of Winterfell learns that his mentor, Jon Arryn, has died and that King Robert is on his way north to offer Ned Arryn’s position as the King’s Hand. Across the Narrow Sea in Pentos, Viserys Targaryen plans to wed his sister Daenerys to the nomadic Dothraki warrior leader, Khal Drogo to forge an alliance to take the throne.", episode.getOverView());
        assertEquals(1, episode.getAbsoluteNumber());
        assertEquals("episodes/121361/3254641.jpg", episode.getFileName());
        assertEquals(121361, episode.getSeriesId());
        assertEquals("tt1480055", episode.getImdbId());
        assertEquals(7.9, episode.getTvdbRating(), 0);
        assertEquals(1, episode.getSeasonNumber());
    }

    @Test
    public void testSearchEpisodeParser() throws IOException {

        InputStream is = getClass().getClassLoader().getResourceAsStream("search_series_mock.xml");
        assertNotNull(is);

        String dummyResponse = TestUtils.convertStreamToString(is);
        assertNotNull(dummyResponse);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new FakeInterceptor(dummyResponse, "xml"))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TvdbInterface.BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(client)
                .build();

        TvdbInterface service = retrofit.create(TvdbInterface.class);

        Response<TvdbSeriesPayload> response = service.getSeries("thrones").execute();

        TvdbSeriesPayload tvdbSeriesPayload = response.body();
        assertNotNull(tvdbSeriesPayload);

        TvdbSeries series = tvdbSeriesPayload.getSeries().get(0);
        assertNotNull(series);

        assertEquals(273385, series.getId());
        assertNull(series.getFirstAired());
        assertEquals("Destination America", series.getNetWork());
        assertEquals("KING OF THRONES follows Hoxie Homes and Remodeling, a northern Minnesota-based crew of contractors, carpenters and designers who’ve built a “crap-load” of high-end bathrooms featuring giant flat-screen TVs, heated toilets, body dryers and even shower jets for a dog. Led by Jeff Hoxie and his partner Dave Koob, the team will stop at nothing to meet their clients’ imaginative needs.", series.getOverView());
        assertEquals("King of Thrones", series.getName());
        assertEquals("graphical/273385-g.jpg", series.getBannerPath());
        assertNull(series.getImdbId());
        assertNull(series.getAliases());

        series = tvdbSeriesPayload.getSeries().get(1);
        assertNotNull(series);

        assertEquals(121361, series.getId());
        assertEquals("2011-04-17", series.getFirstAired());
        assertEquals("HBO", series.getNetWork());
        assertEquals("Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and the icy horrors beyond.", series.getOverView());
        assertEquals("Game of Thrones", series.getName());
        assertEquals("graphical/121361-g37.jpg", series.getBannerPath());
        assertEquals("tt0944947", series.getImdbId());
        assertNull(series.getAliases());

        series = tvdbSeriesPayload.getSeries().get(2);
        assertNotNull(series);

        assertEquals(268310, series.getId());
        assertEquals("2013-03-10", series.getFirstAired());
        assertEquals("YouTube", series.getNetWork());
        assertNull(series.getOverView());
        assertEquals("School of Thrones", series.getName());
        assertEquals("graphical/268310-g.jpg", series.getBannerPath());
        assertEquals("tt2781552", series.getImdbId());
        assertNull(series.getAliases());
    }
}
