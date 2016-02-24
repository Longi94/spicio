package com.tlongdev.spicio;

import com.tlongdev.spicio.api.TvdbInterface;
import com.tlongdev.spicio.model.Series;

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

        Response<Series> response = service.getSeriesRecord("", 0).execute();
        Series series = response.body();

        // TODO: 2016. 02. 24. Assert object
    }
}
