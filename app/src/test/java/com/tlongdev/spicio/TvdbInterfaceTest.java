package com.tlongdev.spicio;

import com.tlongdev.spicio.api.TvdbInterface;
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

        Series series = seriesData.getSeries();
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
}
