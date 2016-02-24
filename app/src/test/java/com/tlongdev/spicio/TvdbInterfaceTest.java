package com.tlongdev.spicio;

import com.tlongdev.spicio.api.TvdbInterface;
import com.tlongdev.spicio.model.Episode;
import com.tlongdev.spicio.model.EpisodeData;
import com.tlongdev.spicio.model.Series;
import com.tlongdev.spicio.model.SeriesData;
import com.tlongdev.spicio.network.TestInterceptor;
import com.tlongdev.spicio.util.TestUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Long on 2016. 02. 24..
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class TvdbInterfaceTest {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetSeriesRecordParser() throws IOException {

        Assert.assertNotNull(RuntimeEnvironment.application);

        InputStream is = getClass().getClassLoader().getResourceAsStream("get_series_mock.xml");
        Assert.assertNotNull(is);

        String dummyResponse = TestUtils.convertStreamToString(is);
        Assert.assertNotNull(dummyResponse);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new TestInterceptor(dummyResponse, "xml"))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RuntimeEnvironment.application.getString(R.string.api_tvdb_link))
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(client)
                .build();

        TvdbInterface service = retrofit.create(TvdbInterface.class);

        Response<SeriesData> response = service.getSeriesRecord("", 0).execute();

        SeriesData seriesData = response.body();
        Assert.assertNotNull(seriesData);

        Series series = seriesData.getSeries().get(0);
        Assert.assertNotNull(series);

        Assert.assertEquals(121361, series.getId());
        Assert.assertEquals("|Emilia Clarke|Peter Dinklage|Kit Harington|Nikolaj Coster-Waldau|Lena Headey|Sophie Turner|Maisie Williams|Liam Cunningham|Gwendoline Christie|Aidan Gillen|Iain Glen|Charles Dance|Jerome Flynn|Alfie Allen|Isaac Hempstead-Wright|Carice van Houten|Natalie Dormer|John Bradley|Conleth Hill|Michelle Fairley|Anton Lesser|Brenock O'Connor|Tobias Menzies|Kristofer Hivju|Julian Glover|Diana Rigg|Ian McElhinney|Ed Skrein|Jonathan Pryce|Alexander Siddig|Nathalie Emmanuel|Tom Wlaschiha|Iwan Rheon|Jacob Anderson|Gemma Whelan|Mark Addy|Indira Varma|Michiel Huisman|Nonso Anozie|Nell Tiger Free|Stephen Dillane|Rory McCann|Finn Jones|Ellie Kendrick|James Cosmo|Sibel Kekilli|Paul Kaye|Ciarán Hinds|Thomas Sangster|Harry Lloyd|Sean Bean|Jason Momoa|Richard Madden|Jack Gleeson|Rose Leslie|Pedro Pascal|Dean-Charles Chapman|Kerry Ingram|Michael McElhatton|Art Parkinson|Mackenzie Crook|Hannah Murray|Amrita Acharia|Aimee Richardson|Eugene Simon|Ron Donachie|Lino Facioli|Oona Chaplin|Kate Dickie|Peter Vaughan|Patrick Malahide|Natalia Tena|Joe Dempsie|Kristian Nairn|Donald Sumpter|Esmé Bianco|Gethin Anthony|", series.getActors());
        Assert.assertEquals("Sunday", series.getAirsDayOfWeek());
        Assert.assertEquals("9:00 PM", series.getAirsTime());
        Assert.assertEquals("TV-MA", series.getContentRating());
        Assert.assertEquals("2011-04-17", series.getFirstAired());
        Assert.assertEquals("|Adventure|Drama|Fantasy|", series.getGenres());
        Assert.assertEquals("tt0944947", series.getImdbId());
        Assert.assertEquals("HBO", series.getNetWork());
        Assert.assertEquals("Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and the icy horrors beyond.", series.getOverView());
        Assert.assertEquals(9.5, series.getTvdbRating(), 0);
        Assert.assertEquals(1260, series.getTvdbRatingCount());
        Assert.assertEquals(60, series.getRunTime());
        Assert.assertEquals("Game of Thrones", series.getName());
        Assert.assertEquals("Continuing", series.getStatus());
        Assert.assertEquals("graphical/121361-g37.jpg", series.getBannerPath());
        Assert.assertEquals("posters/121361-34.jpg", series.getPoster());
        Assert.assertEquals(null, series.getZapt2itId());
    }

    @Test
    public void testGetEpisodeParser() throws IOException {

        Assert.assertNotNull(RuntimeEnvironment.application);

        InputStream is = getClass().getClassLoader().getResourceAsStream("get_episode_mock.xml");
        Assert.assertNotNull(is);

        String dummyResponse = TestUtils.convertStreamToString(is);
        Assert.assertNotNull(dummyResponse);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new TestInterceptor(dummyResponse, "xml"))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RuntimeEnvironment.application.getString(R.string.api_tvdb_link))
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(client)
                .build();

        TvdbInterface service = retrofit.create(TvdbInterface.class);

        Response<EpisodeData> response = service.getEpisode("", 0, 0).execute();

        EpisodeData episodeData = response.body();
        Assert.assertNotNull(episodeData);

        Episode episode = episodeData.getEpisode();
        Assert.assertNotNull(episode);

        Assert.assertEquals(3254641, episode.getId());
        Assert.assertEquals(364731, episode.getSeasonId());
        Assert.assertEquals(1, episode.getEpisodeNumber());
        Assert.assertEquals("Winter Is Coming", episode.getEpisodeName());
        Assert.assertEquals("2011-04-17", episode.getFirstAired());
        Assert.assertEquals("|Donald Sumpter|Jamie Sives|Ron Donachie|Joseph Mawle|Roger Allam|Dar Salim|Esmé Bianco|Susan Brown|Bronson Webb|John Standing|Rob Ostlere|Dermot Keaney|Art Parkinson|Callum Wharry|Aimee Richardson|Kristian Nairn|Rania Zouari|Ian Whyte|Spencer Wilding|", episode.getGuestStars());
        Assert.assertEquals("Tim Van Patten", episode.getDirector());
        Assert.assertEquals("|David Benioff|D. B. Weiss|", episode.getWriters());
        Assert.assertEquals("Ned Stark, Lord of Winterfell learns that his mentor, Jon Arryn, has died and that King Robert is on his way north to offer Ned Arryn’s position as the King’s Hand. Across the Narrow Sea in Pentos, Viserys Targaryen plans to wed his sister Daenerys to the nomadic Dothraki warrior leader, Khal Drogo to forge an alliance to take the throne.", episode.getOverView());
        Assert.assertEquals(1, episode.getAbsoluteNumber());
        Assert.assertEquals("episodes/121361/3254641.jpg", episode.getFileName());
        Assert.assertEquals(121361, episode.getSeriesId());
        Assert.assertEquals("tt1480055", episode.getImdbId());
        Assert.assertEquals(7.9, episode.getTvdbRating(), 0);
        Assert.assertEquals(1, episode.getSeasonNumber());
    }

    @Test
    public void testSearchEpisodeParser() throws IOException {

        Assert.assertNotNull(RuntimeEnvironment.application);

        InputStream is = getClass().getClassLoader().getResourceAsStream("search_series_mock.xml");
        Assert.assertNotNull(is);

        String dummyResponse = TestUtils.convertStreamToString(is);
        Assert.assertNotNull(dummyResponse);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new TestInterceptor(dummyResponse, "xml"))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RuntimeEnvironment.application.getString(R.string.api_tvdb_link))
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(client)
                .build();

        TvdbInterface service = retrofit.create(TvdbInterface.class);

        Response<SeriesData> response = service.getSeries("thrones").execute();

        SeriesData seriesData = response.body();
        Assert.assertNotNull(seriesData);

        Series series = seriesData.getSeries().get(0);
        Assert.assertNotNull(series);

        Assert.assertEquals(273385, series.getId());
        Assert.assertEquals(null, series.getFirstAired());
        Assert.assertEquals("HBO", series.getNetWork());
        Assert.assertEquals("KING OF THRONES follows Hoxie Homes and Remodeling, a northern Minnesota-based crew of contractors, carpenters and designers who’ve built a “crap-load” of high-end bathrooms featuring giant flat-screen TVs, heated toilets, body dryers and even shower jets for a dog. Led by Jeff Hoxie and his partner Dave Koob, the team will stop at nothing to meet their clients’ imaginative needs.", series.getOverView());
        Assert.assertEquals("King of Thrones", series.getName());
        Assert.assertEquals("graphical/273385-g.jpg", series.getBannerPath());
        Assert.assertEquals(null, series.getImdbId());

        series = seriesData.getSeries().get(1);
        Assert.assertNotNull(series);

        Assert.assertEquals(121361, series.getId());
        Assert.assertEquals("2011-04-17", series.getFirstAired());
        Assert.assertEquals("HBO", series.getNetWork());
        Assert.assertEquals("Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and the icy horrors beyond.", series.getOverView());
        Assert.assertEquals("Game of Thrones", series.getName());
        Assert.assertEquals("graphical/121361-g37.jpg", series.getBannerPath());
        Assert.assertEquals("tt0944947", series.getImdbId());

        series = seriesData.getSeries().get(2);
        Assert.assertNotNull(series);

        Assert.assertEquals(268310, series.getId());
        Assert.assertEquals("2013-03-10", series.getFirstAired());
        Assert.assertEquals("YouTube", series.getNetWork());
        Assert.assertEquals("Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and the icy horrors beyond.", series.getOverView());
        Assert.assertEquals("School of Thrones", series.getName());
        Assert.assertEquals("graphical/268310-g.jpg", series.getBannerPath());
        Assert.assertEquals("tt2781552", series.getImdbId());
    }
}
