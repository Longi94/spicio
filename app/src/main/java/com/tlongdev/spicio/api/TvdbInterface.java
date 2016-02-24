package com.tlongdev.spicio.api;

import com.tlongdev.spicio.model.Episode;
import com.tlongdev.spicio.model.Series;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Long on 2016. 02. 24..
 */
public interface TvdbInterface {

    @GET("api/GetSeries.php")
    Call<List<Series>> getSeries(@Query("seriesname") String seriesName);

    @GET("api/{apiKey}/series/{id}")
    Call<Series> getSeriesRecord(@Path("apiKey") String apiKey, @Path("id") int seriesId);

    @GET("api/{apiKey}/series/{id}/default/{season}/{episode}")
    Call<Episode> getEpisode(@Path("apiKey") String apiKey, @Path("id") int seriesId,
                             @Path("season") int season, @Path("episode") int episode);

    @GET("api/{apiKey}/series/{id}/absolute/{episode}")
    Call<Episode> getEpisode(@Path("apiKey") String apiKey, @Path("id") int seriesId,
                             @Path("episode") int absoluteEpisode);
}
